package com.loliclub.eater.services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.java_websocket.drafts.Draft_17;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.loliclub.dbhelper.database.DatabaseHelper;
import com.loliclub.dbhelper.database.DatabaseService;
import com.loliclub.dbhelper.util.AccessSharedPreferences;
import com.loliclub.dbhelper.util.BeanUtil;
import com.loliclub.dbhelper.util.MD5;
import com.loliclub.eater.R;
import com.loliclub.eater.bean.Account;
import com.loliclub.eater.bean.Eater;
import com.loliclub.eater.bean.EaterOrder;
import com.loliclub.eater.bean.Member;
import com.loliclub.eater.bean.Menu;
import com.loliclub.eater.bean.Restaurant;
import com.loliclub.eater.beanservices.AccountService;
import com.loliclub.eater.beanservices.EaterService;
import com.loliclub.eater.beanservices.MemberService;
import com.loliclub.eater.beanservices.MenuService;
import com.loliclub.eater.beanservices.EaterOrderService;
import com.loliclub.eater.beanservices.RestaurantService;
import com.loliclub.eater.properties.MyDatabaseHandler;
import com.loliclub.eater.properties.ServerAPI;
import com.loliclub.eater.properties.UrlBuilder;
import com.loliclub.eater.properties.WebSocketMessage;
import com.loliclub.network.http.HttpClientResponseHandler;

public class NetworkService extends Service {
	
	public static final String TAG = "NetworkService";

	/**
	 * 配置文件文件名
	 */
	public static final String CONFIG_FILE_NAME = "NetwordServiceConfig";
	
	/**
	 * 重连等待时间
	 */
	public static final long DELAY_RECONNECT = 10000L;

	/**
	 * 心跳指令发送间隔
	 */
	public static final long DELAY_ACTION_NOP = 50000L;
	
	/**
	 * 网络模块常量：应用名
	 */
	private static String APP_NAME;
	
	/**
	 * 网络模块常量：运营商
	 */
	private static String CARRIER;
	
	/**
	 * 登录用户名
	 */
	private static String USERNAME;
	
	/**
	 * 登录密码
	 */
	private static String PASSWORD;
	
	/**
	 * 网络模块常量：sessionId
	 */
	public static String SESSION_ID;
	
	/**
	 * 网络模块常量：accessToken
	 */
	public static String ACCESSTOKEN;

	/**
	 * 服务器登录标志
	 */
	public static boolean IS_SIGNIN_SERVER;

	/**
	 * WebSocket连接标志
	 */
	public static boolean IS_SIGNIN_WEBSOCKET;
	
	private ServiceBinder serviceBinder;
	private WebSocketService webSocketService;
	private WebSocketService.Callback webSocketCallback;
	private NetworkServiceCallback networkServiceCallback;

	private BroadcastReceiver netStatusReceiver;
	private Handler websocketHandler;
	private ThreadPoolExecutor threadPool;
	private Runnable patrolThread;
	private Runnable signinThread;
	private Runnable downloadThread;
	private WebSocketMessage nopMessage;
	private DatabaseHelper databaseHelper;

	private int networkStatus;
	private boolean isReconnectWebsocket;
	
	private String clientId;

	@Override
	public void onCreate() {
		// 初始化Service
		initProperties();
		initParams();
		initDatabase();
		initService();
		
		// 尝试登录
//		websocketHandler.post(signinThread);
	}

	/**
	 * 加载配置（如果有）
	 */
	private void initProperties() {
		/**
		 * 读取sharePreferences中的内容
		 */
		clientId = AccessSharedPreferences.readProperty(
				getApplicationContext(), CONFIG_FILE_NAME, "clientId",
				BeanUtil.generateId());

		/**
		 * 加载登录参数
		 */
		APP_NAME = getString(R.string.params_appname);
		CARRIER = getString(R.string.params_carrier);
		USERNAME = getString(R.string.params_username);
		PASSWORD = getString(R.string.params_password);
	}

	private void initParams() {
		// 初始化本服务参数
		IS_SIGNIN_SERVER = false;
		IS_SIGNIN_WEBSOCKET = false;
		isReconnectWebsocket = true;
		networkStatus = this.checkNetworkStatus();

		serviceBinder = new ServiceBinder();
		// 用户异步发送网络请求的处理器
		websocketHandler = new Handler();
		// 初始化心跳信息
		nopMessage = new WebSocketMessage();
		nopMessage.setAction(WebSocketMessage.ACTION_NOP);
		// 初始化网络状态监听广播
		netStatusReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				networkStatus = checkNetworkStatus();
				// 网络状态改变，
				// 如果网络断开了，则断开WebSocketService
				// 否则重新登陆或连接Websocket
				if (isNetworkConnected()) {
					if (IS_SIGNIN_SERVER)
						connectWebSocket();
					else{
						websocketHandler.post(signinThread);
					}
				} else {
					if (webSocketService != null)
						webSocketService.close();
				}
			}
		};
		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		this.registerReceiver(netStatusReceiver, filter);

		// 服务器处理线程池,单线程线程池
		threadPool = (ThreadPoolExecutor)Executors.newCachedThreadPool();
		// 发送心跳指令
		patrolThread = new Runnable() {
			@Override
			public void run() {
				Log.e(TAG, "post nop action to webSocket");
				nopMessage.setDatetime(BeanUtil.dateToString(new Date(System.currentTimeMillis())));
				webSocketService.send(nopMessage.toJSONObject().toString());
				websocketHandler.postDelayed(patrolThread, DELAY_ACTION_NOP);
			}
		};
		
		// 登录线程，由于后台登陆有可能出现错误，因此使用登录线程重复登陆
		// 登录间隔为10S
		signinThread = new Runnable() {
			@Override
			public void run() {
				if (!IS_SIGNIN_SERVER) {
					signIn();
					websocketHandler.postDelayed(signinThread, DELAY_RECONNECT);
				}
			}
		};
		// 下载数据线程
		downloadThread = new Runnable() {
			@Override
			public void run() {
				download();
			}
		};
	}
	
	/**
	 * 初始化数据库
	 */
	private void initDatabase() {
		// 初始化数据库信息，设置数据库名和数据库版本号
		MyDatabaseHandler dbHandler = MyDatabaseHandler.getInstance();
		databaseHelper = DatabaseService.connectDatabase(
				getApplicationContext(), dbHandler);
	}

	private void initService() {
		webSocketCallback = new WebSocketService.Callback() {

			@Override
			public void onOpen() {
				Log.e(TAG, "Websocket Open");
				// 连接成功后发送登录指令
				WebSocketMessage loginMessage = new WebSocketMessage();
				loginMessage.setAction(WebSocketMessage.ACTION_LOGIN);
				loginMessage.setDatetime(BeanUtil.dateToString(new Date(System.currentTimeMillis())));
				loginMessage.setSessionId(SESSION_ID);
				webSocketService.send(loginMessage.toJSONObject().toString());
				// 登录之后等待服务器的回应，登录成功之后发送心跳指令
			}

			@Override
			public void onClose(int code, String content, boolean flag) {
				Log.e(TAG, "Websocket Close");
				IS_SIGNIN_WEBSOCKET = false;
				// 关闭WebSocket，清除线程池的所有等待的线程
				websocketHandler.removeCallbacksAndMessages(null);
				// 如果WebSocket异常关闭，则重新连接
				if(isNetworkConnected() && IS_SIGNIN_SERVER && isReconnectWebsocket)
					websocketHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							connectWebSocket();
						}
					}, DELAY_RECONNECT);
			}

			@Override
			public void onError(Exception e) {
				Log.e(TAG, "Websocket Error");
				IS_SIGNIN_WEBSOCKET = false;
				e.printStackTrace();
				// 如果websock发生错误，尝试重新连接
				websocketHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						connectWebSocket();
					}
				}, DELAY_RECONNECT);
			}

			@Override
			public void onMessage(String msg) {
				Log.e(TAG, "Websocket Message is " + msg);
				if (WebSocketMessage.ACTION_NOP_SUCCESS.equals(msg)) {
					
				} else {
					WebSocketMessage websocketMessage = new WebSocketMessage(msg);
					switch (websocketMessage.getAction()) {
					case WebSocketMessage.ACTION_LOGIN_SUCCESS:
						IS_SIGNIN_WEBSOCKET = true;
						nopMessage.setSessionId(SESSION_ID);
						websocketHandler.post(patrolThread);
						break;
					case WebSocketMessage.ACTION_NEW_DATA:
						// 有数据更新，调用下载接口
						threadPool.execute(downloadThread);
						break;
					case WebSocketMessage.ACTION_TEST:
						
						break;
					default:
						break;
					}
				}
			}

		};
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return serviceBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(netStatusReceiver);
		isReconnectWebsocket = false;
		webSocketService.close();
		threadPool.shutdown();
		// 退出登录
		ServerAPI.logout(SESSION_ID, null);
		super.onDestroy();
	}

	/**
	 * ********************************************* 
	 *            NetworkService开放接口
	 *   以下为NetworkService开发给上层使用的接口部分。
	 * *********************************************
	 */

	/**
	 * 查询网络是否连通
	 * 
	 * @return 连通返回true，否则false
	 */
	public boolean isNetworkConnected() {
		return networkStatus != 0;
	}

	/**
	 * 设置网络回调接口
	 * 
	 * @return
	 */
	public NetworkServiceCallback getNetworkServiceCallback() {
		return this.networkServiceCallback;
	}

	/**
	 * 检查网络是否连通，该方法一般在网络状态改变是才使用，平时查询网络状态使用{@link #isNetworkConnected()}
	 * 
	 * @return 如果网络连通则返回true，否则false
	 */
	public int checkNetworkStatus() {
		networkStatus = 0;
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(networkInfo != null && State.CONNECTED == networkInfo.getState()) {
			networkStatus |= 0x01;
		} else {
			networkStatus |= 0x00;
		}
		networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if ((networkInfo != null && State.CONNECTED == networkInfo.getState())) {
			networkStatus |= 0x02;
		} else {
			networkStatus |= 0x00;
		}
		return networkStatus;
	}
	
	/**
	 * 登录服务器
	 */
	public void signIn() {
		// 首先检查网络状态
		if (!isNetworkConnected())
			return;
		ServerAPI.signin(USERNAME, MD5.makeMD5(PASSWORD, false), APP_NAME,
				CARRIER, 1, clientId, new HttpClientResponseHandler() {

					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						// 登录成功，初始化下载
						int responseCode = response.optInt(ServerAPI.RESPONSE_CODE);
						Log.e(TAG, "responseCode is " + responseCode);
						Log.e(TAG, "response is " + response.toString());
						if (responseCode == 200) {
							IS_SIGNIN_SERVER = true;
							SESSION_ID = response.optString(ServerAPI.RESPONSE_SESSIONID);
							ACCESSTOKEN = response.optString(ServerAPI.RESPONSE_ACCESSTOKEN);
							connectWebSocket();
							// 调用线程池下载。也可以使用sumbit()方法
							// 如果需要返回线程执行的结果，需要继承语Callable
							threadPool.execute(downloadThread);
						} else if (responseCode == 402){
							// TODO 用户名或密码错误

						} else {
							// TODO 其他情况

						}
					}

					@Override
					public void onFailure(Throwable e, JSONObject errorResponse) {
						// 由于系统异常而登录失败，会重新登陆
						Log.e(TAG, "response is " + errorResponse.toString());
					}

				});
	}
	
	/**
	 * 下载并解析数据
	 */
	public void download() {
		if (!isNetworkConnected())
			return;
		// 获取timestamp
		long timestamp = 0;
		final AccountService accountService = new AccountService(databaseHelper.getWritableDatabase());
		final Account account = accountService.queryAccount(USERNAME);
		if (account != null)
			timestamp = account.timestamp;
		Log.e(TAG, "timeStamp is " + timestamp);
		ServerAPI.download(ACCESSTOKEN, SESSION_ID,
				timestamp, new HttpClientResponseHandler() {

					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						Log.e(TAG, "response is " + response);
						int responseCode = response.optInt(ServerAPI.RESPONSE_CODE);
						if (responseCode == 200) {
							long nextTs = response.optLong(ServerAPI.RESPONSE_NEXTTS);
							// 保存nextTs
							String fileUrl = response.optString(ServerAPI.RESPONSE_FILEURL);
							// 下载并解析数据
							new Thread(new Runnable() {
								@Override
								public void run() {

								}
							}).start();
							try {
								// 下载
								File zipFile = downloadFile(new URL(fileUrl), getApplicationContext().getExternalCacheDir().getAbsolutePath());
								// 解压
								File destFile = EctractZipFile(zipFile, getApplicationContext().getExternalCacheDir().getAbsolutePath());
								if (zipFile != null)
									zipFile.delete();
								// 解析并保存

								JSONObject jsonString = readFileToJSONObject(destFile);
								if (jsonString != null) {
									Log.e(TAG, "downloadFile is " + jsonString.toString());
									resolveAccount(jsonString);
									resolveMember(jsonString);
//									resolveEater(jsonString);
//									resolveMenu(jsonString);
//									resolveOrder(jsonString);
//									resolveRestaurant(jsonString);
								}
								// 保存nextTs
								if (account == null) {
									Account newAccount = accountService.queryAccount(USERNAME);
									newAccount.timestamp = nextTs;
									accountService.replace(newAccount);
								} else {
									account.timestamp = nextTs;
									accountService.replace(account);
								}


								if (destFile != null)
									destFile.delete();
							} catch (MalformedURLException e) {
								e.printStackTrace();
							}
						}
					}

					@Override
					public void onFailure(Throwable e, JSONObject errorResponse) {
						Log.e(TAG, "download fail");
						e.printStackTrace();
					}

				});
	}

	/**
	 * 解析账号信息
	 * @param jsonObj
	 * @return
	 */
	protected boolean resolveAccount(JSONObject jsonObj) {
		if (jsonObj == null)
			return false;
		JSONObject accountJson = jsonObj.optJSONObject("account");
		if (accountJson == null)
			return false;
		Account account = new Account();
		account.setAttribute(accountJson, 0);
		Log.e(TAG, "account json is " + accountJson.toString());
		Log.e(TAG, "account is " + account);
		AccountService service = new AccountService(databaseHelper.getWritableDatabase());
		return service.replace(account);
	}

	/**
	 * 解析成员信息
	 * @param jsonObj
	 * @return
	 */
	protected boolean resolveMember(JSONObject jsonObj) {
		if (jsonObj == null)
			return false;
		JSONArray memberArray = jsonObj.optJSONArray("member");
		if (memberArray == null || memberArray.length() <= 0)
			return false;
		List<Member> list = new ArrayList<Member>();
		for (int i = memberArray.length() - 1; i >= 0; i--) {
			Member member = new Member();
			member.setAttribute(memberArray.optJSONObject(i), 0);
			Log.e(TAG, "member is " + member.toString());
			list.add(member);
		}
		MemberService service = new MemberService(databaseHelper.getWritableDatabase());
		return service.replace(list);
	}

	/**
	 * 解析吃货信息
	 * @param jsonObj
	 * @return
	 */
	protected boolean resolveEater(JSONObject jsonObj) {
		/**
		 * 使用Device表
		 * deviceType = 02
		 */
		if (jsonObj == null)
			return false;
		JSONArray eaterArray = jsonObj.optJSONArray("device");
		if (eaterArray == null || eaterArray.length() <= 0)
			return false;
		List<Eater> list = new ArrayList<>();
		for (int i = eaterArray.length() - 1; i >= 0; i--) {
			Eater eater = new Eater();
			eater.setAttribute(eaterArray.optJSONObject(i), 0);
			list.add(eater);
		}
		EaterService service = new EaterService(databaseHelper.getWritableDatabase());
		return service.replace(list);
	}

	/**
	 * 解析菜单信息
	 * @param jsonObj
	 * @return
	 */
	protected boolean resolveMenu(JSONObject jsonObj) {
		if (jsonObj == null)
			return false;
		JSONArray menuArray = jsonObj.optJSONArray("height");
		if (menuArray == null || menuArray.length() <= 0)
			return false;
		List<Menu> list = new ArrayList<Menu>();
		for (int i = menuArray.length() - 1; i >= 0; i--) {
			Menu menu = new Menu();
			menu.setAttribute(menuArray.optJSONObject(i), 0);
			list.add(menu);
		}
		MenuService service = new MenuService(databaseHelper.getWritableDatabase());
		return service.replace(list);
	}

	/**
	 * 解析订单信息
	 * @param jsonObj
	 * @return
	 */
	protected boolean resolveOrder (JSONObject jsonObj) {
		if (jsonObj == null)
			return false;
		JSONArray orderArray = jsonObj.optJSONArray("pedometer");
		if (orderArray == null || orderArray.length() <= 0)
			return false;
		List<EaterOrder> list = new ArrayList<EaterOrder>();
		for(int i = orderArray.length() - 1; i >= 0; i--) {
			EaterOrder order = new EaterOrder();
			order.setAttribute(orderArray.optJSONObject(i), 0);
			list.add(order);
		}
		EaterOrderService service = new EaterOrderService(databaseHelper.getWritableDatabase());
 		return service.replace(list);
	}

	protected  boolean resolveRestaurant (JSONObject jsonObj) {
		if (jsonObj == null)
			return false;
		JSONArray restaurantArray = jsonObj.optJSONArray("devices");
		if (restaurantArray == null || restaurantArray.length() <= 0)
			return false;
		List<Restaurant> list = new ArrayList<Restaurant>();
		for (int i = restaurantArray.length() - 1; i >= 0; i--) {
			Restaurant restaurant = new Restaurant();
			restaurant.setAttribute(restaurantArray.optJSONObject(i), 0);
			list.add(restaurant);
		}
		RestaurantService service = new RestaurantService(databaseHelper.getWritableDatabase());
		return service.replace(list);
	}
	
	/**
	 ********************************************** 
	 *      NetworkService内部接口
	 * 以下为NetworkService内部逻辑处理方法 
	 **********************************************
	 */
	
	/**
	 * 下载文件，该操作可能比较耗时，请放在线程中操作
	 * @param url
	 * @return
	 */
	protected File downloadFile(URL url, String path) {
		if (url == null || path == null)
			return null;
		BufferedInputStream inputStream = null;
		BufferedOutputStream outputStream = null;
		File file = null;
		// 打开下载链接
		try {
			URLConnection connection = url.openConnection();
			String fileName = new File(url.getFile()).getName();
			file = new File(path, fileName);
			if (!file.getParentFile().exists()) 
				file.mkdir();
			if (!file.exists())
				file.createNewFile();
			// 新建输入输出流
			byte[] buffer = new byte[1024];
			inputStream = new BufferedInputStream(connection.getInputStream());
			outputStream = new BufferedOutputStream(new FileOutputStream(file));
			int readFileCount = 0;
			while ((readFileCount = inputStream.read(buffer, 0, 1024)) != -1) {
				outputStream.write(buffer, 0, readFileCount);
			}
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	/**
	 * 解压缩文件，该操作比较耗时，请放在线程中执行
	 * @param sourceFile
	 * @param destPath
	 */
	protected File EctractZipFile(File sourceFile, String destPath) {
		if (sourceFile == null || destPath == null)
			return null;
		ZipInputStream zipInputStream = null;
		File destFile = null;
		try {
			zipInputStream = new ZipInputStream(new FileInputStream(sourceFile));
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			byte[] buffer = new byte[1024];
			if (zipEntry != null) {
				destFile = new File(destPath + zipEntry.getName());
				if (zipEntry.isDirectory()) {
					if (!destFile.exists())
						destFile.mkdir();
					zipInputStream.closeEntry();
				} else {
					File filePath = new File(destFile.getParentFile().getPath());
					if (!filePath.exists())
						filePath.mkdirs();
					FileOutputStream outputStream = new FileOutputStream(destFile);
					int length = 0;
					while ((length = zipInputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, length);
					}
					zipInputStream.closeEntry();
					outputStream.close();
				}
			}
			zipEntry = zipInputStream.getNextEntry();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (zipInputStream != null)
					zipInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return destFile;
	}
	
	/**
	 * 读取JSON文件，并且以JSON格式输出
	 * @param file
	 * @return
	 */
	public JSONObject readFileToJSONObject(File file) {
		if (file == null)
			return null;
		JSONObject jsonObject = null;
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			while((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			jsonObject = new JSONObject(buffer.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}
	
	/**
	 * 连接WebSocket
	 */
	private void connectWebSocket() {
		if (!isNetworkConnected() || !IS_SIGNIN_SERVER)
			return;
		// 每次连接WebSocket都必须重新建立一个WebSocket客户端
		String url = null;
		url = getWebSocketURI(UrlBuilder.WS_SERVER_URL);
		webSocketService = new WebSocketService(URI.create(url),
				new Draft_17(), webSocketCallback);
		// 如果连接成功，调用onOpen()方法
		webSocketService.connect();
	}
	
	/**
	 * 拼接WebSocket的Url
	 * 
	 * @param serverURL
	 * @return
	 */
	private String getWebSocketURI(String serverURL) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(serverURL);
		buffer.append("?");
		buffer.append("sessionId");
		buffer.append("=");
		buffer.append(SESSION_ID);
		buffer.append("&");
		buffer.append("appserver");
		buffer.append("=");
		buffer.append(UrlBuilder.APPSERVER);
		return buffer.toString();
	}

	/**
	 **************************************
	 *          Service固定代码部分 
	 * 这部分的代码在移植过程中也可以不改变
	 ***************************************
	 **/

	/**
	 * 用于连接NetworkService
	 */
	public class ServiceBinder extends Binder {

		/**
		 * 获取Service对象
		 * @return
		 */
		public Service getService() {
			return NetworkService.this;
		}

	}

}

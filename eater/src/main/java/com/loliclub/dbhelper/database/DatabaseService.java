package com.loliclub.dbhelper.database;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

/**
 * 数据库连接服务，负责管理数据库的连接。
 * 该服务内部建立了一个数据库连接池，可以在不同的数据库间快速切换。<br>
 * 该服务采用单例模式，直接使用{@link #connectDatabase(Context, DatabaseHandler)}
 * 方法即可连接目标数据库，无需其他操作。<br>
 * 不同的数据库对应不同的{@link DatabaseHandler}对象，有关内容请参考{@link DatabaseHandler}
 * 
 */
public class DatabaseService {

	public static final String TAG = "DatabaseService";

	/**
	 * 单例对象
	 */
	private static DatabaseService databaseService;

	/**
	 * 数据库连接池
	 */
	private static Map<String, DatabaseHelper> dbPool;

	/**
	 * 私有构造方法，防止外部创建连接服务
	 */
	private DatabaseService() {
		dbPool = new HashMap<String, DatabaseHelper>();
	}

	/**
	 * 初始化数据库连接服务，防止多线程下创建数据库错误。
	 */
	private static synchronized void syncInit() {
		if (databaseService == null)
			databaseService = new DatabaseService();
	}

	/**
	 * 连接至目标数据库
	 * 
	 * @param context
	 * @param databaseHandler
	 * @return
	 */
	public synchronized static DatabaseHelper connectDatabase(Context context,
			DatabaseHandler databaseHandler) {
		syncInit();
		DatabaseHelper database = null;
		database = dbPool.get(databaseHandler.getDatabaseName());
		if (database == null) {
			database = new DatabaseHelper(context, databaseHandler);
			dbPool.put(database.getDatabaseName(), database);
		}
		return database;
	}

	/**
	 * 关闭数据库，在关闭数据库的同时会清除连接池和handler
	 * 
	 * @param name
	 */
	protected static void closeDatabase(String name) {
		dbPool.remove(name);
	}
}

package com.loliclub.dbhelper.database;

import java.util.List;

import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.loliclub.dbhelper.standard.BeanStandard;
import com.loliclub.dbhelper.standard.IBeanStandard;
import com.loliclub.dbhelper.standard.IServiceStandard;
import com.loliclub.dbhelper.standard.ServiceStandard;

/**
 * 数据库处理对象，该对象保存了所有获取数据库信息的方法
 * <p>
 * 每一个数据库都对应了一个DatabaseHandler， 建议自己创建一个继承于DatabaseHandler 的类用于管理该数据库的配置信息
 * </p>
 * <p>
 * 例如：
 * </p>
 * 
 * <pre>
 * public class MyDatabaseHandler extends DatabaseHandler {
 * 
 * 	public MyDatabaseHandler(String databaseName, int version) {
 * 		super(databaseName, version);
 * 	}
 * 
 * public List<IBeanStandard> getBeanList() {
	// 在List中添加需要创建表的类
	}
 * 
 * }
 * </pre>
 * 使用该数据库的对象都必须遵循一定的规则，每一个数据库表都必须是一个实现了{@link IBeanStandard}接口的类
 * 或继承于{@link BeanStandard}的类。对数据库表的逻辑操作请放在实现了{@link IServiceStandard}接口的类
 * 或继承于{@link ServiceStandard}的类。
 */
public abstract class DatabaseHandler {

	private String databaseName;

	private int version;

	private CursorFactory cursorFactory;

	private List<IBeanStandard> beanList;

	/**
	 * 创建一个DatabaseHandler对象。
	 * 
	 * @param databaseName
	 *            数据库名
	 * @param version
	 *            数据库版本号
	 */
	public DatabaseHandler(String databaseName, int version) {
		this.databaseName = databaseName;
		this.version = version;
	}

	/**
	 * 获取数据库名
	 * 
	 * @return 数据库名
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * 设置数据库名
	 * 
	 * @param databaseName
	 *            数据库名
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * 获取数据库版本号
	 * 
	 * @return 数据库版本号
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * 设置数据库版本号
	 * 
	 * @param version
	 *            数据库版本号
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * 获取{@link CursorFactory}
	 * 
	 * @return cursorFactory
	 */
	public CursorFactory getCursorFactory() {
		return cursorFactory;
	}

	/**
	 * 设置{@link CursorFactory}
	 * 
	 * @param cursorFactory
	 */
	public void setCursorFactory(CursorFactory cursorFactory) {
		this.cursorFactory = cursorFactory;
	}

	/**
	 * 获取数据库表名列表
	 * 
	 * @return 数据库表名列表
	 */
	public List<IBeanStandard> getBeanList() {
		return beanList;
	}

	/**
	 * 设置数据库表名列表
	 * 
	 * @param beanList
	 *            数据库表名列表
	 */
	public void setBeanList(List<IBeanStandard> beanList) {
		this.beanList = beanList;
	}

	/**
	 * 关闭数据库
	 * 
	 * @param name
	 *            数据库名
	 */
	protected void closeDatabase(String name) {
		DatabaseService.closeDatabase(name);
	}

}

package com.loliclub.dbhelper.database;

import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.loliclub.dbhelper.standard.IBeanStandard;

/**
 * 数据库对象，继承于{@link SQLiteOpenHelper},负责管理数据库
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String TAG = "DatabaseHelper";
	private DatabaseHandler databaseHandler;

	protected DatabaseHelper(Context context, DatabaseHandler databaseHandler) {
		super(context, databaseHandler.getDatabaseName(), databaseHandler
				.getCursorFactory(), databaseHandler.getVersion());
		this.databaseHandler = databaseHandler;
	}

	@Override
	public synchronized void close() {
		this.databaseHandler.closeDatabase(getDatabaseName());
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		List<IBeanStandard> beanList = databaseHandler.getBeanList();
		if (beanList == null || beanList.size() <= 0)
			return;
		for (IBeanStandard bean : beanList) {
			try {
				if (bean.getCreateTableSQL() != null
						&& !"".equals(bean.getCreateTableSQL()))
					db.execSQL(bean.getCreateTableSQL());
				String[] advanceSqlArray = bean.getAdvancedSQL();
				if (advanceSqlArray != null && advanceSqlArray.length > 0)
					for (String sql : advanceSqlArray) {
						if (sql != null && !"".equals(sql))
							db.execSQL(sql);
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		List<IBeanStandard> beanList = databaseHandler.getBeanList();
		if (beanList == null || beanList.size() <= 0)
			return;
		for (IBeanStandard bean : beanList) {
			try {
				List<String> updateSQLList = bean.getUpdateTableSQL(oldVersion,
						newVersion);
				if (updateSQLList != null && updateSQLList.size() > 0) {
					for (String sql : updateSQLList)
						db.execSQL(sql);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除数据库表
	 * 
	 * @param db
	 *            数据库对象
	 */
	public void onDelete(SQLiteDatabase db) {
		List<IBeanStandard> beanList = databaseHandler.getBeanList();
		if (beanList == null || beanList.size() <= 0)
			return;
		for (IBeanStandard bean : beanList) {
			try {
				if (bean.getDeleteTableSQL() != null
						&& !"".equals(bean.getDeleteTableSQL()))
					db.execSQL(bean.getDeleteTableSQL());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}

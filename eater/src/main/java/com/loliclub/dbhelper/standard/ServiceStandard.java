package com.loliclub.dbhelper.standard;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.loliclub.dbhelper.database.DatabaseException;
import com.loliclub.dbhelper.standard.IBeanStandard.primaryKey;
import com.loliclub.dbhelper.standard.IBeanStandard.without;
import com.loliclub.dbhelper.util.BeanUtil;

/**
 * Service类标准抽象类。 <br>
 * 该类采用反射的机制自动完成{@link IServiceStandard}接口的操作。
 * <p>
 * PS.
 * </p>
 * <ul>
 * <li>1.请确认Bean类使用了@Id标注主键</li>
 * <li>2.请确认Bean类使用@without标注不在数据库创建字段的属性</li>
 * <li>3.继承于该类的子类不能混淆</li>
 * </ul>
 */
public abstract class ServiceStandard<T extends IBeanStandard> implements
		IServiceStandard<T> {

	public static final String TAG = "ServiceStandard";

	/**
	 * 数据库对象
	 */
	private SQLiteDatabase database;

	/**
	 * 泛型类
	 */
	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public ServiceStandard(SQLiteDatabase database) {
		this.database = database;
		// 由于java底层的原因，必须使用这个方法才可以获取泛型的对象
		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * 获取数据库对象
	 * @return
	 */
	protected SQLiteDatabase getDataBase() {
		return this.database;
	}

	@Override
	public boolean insert(T bean) {
		if (database == null) {
			new DatabaseException("The com.loliclub.dbhelper.database is null").printStackTrace();
			return false;
		}
		try {
			ContentValues contentValues = getContentValues(bean);
			if (database.insert(bean.getClass().getSimpleName(), null,
					contentValues) < 0)
				return false;
			else
				return true;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean insert(List<T> list) {
		if (database == null) {
			new DatabaseException("The com.loliclub.dbhelper.database is null").printStackTrace();
			return false;
		}
		if (list == null || list.size() <= 0) {
			new DatabaseException("The parameters is null").printStackTrace();
			return false;
		}
		database.beginTransaction();
		try {
			for (T bean : list) {
				ContentValues contentValues = getContentValues(bean);
				if (database.insert(bean.getClass().getSimpleName(), null,
						contentValues) < 0) {
					return false;
				}
			}
			database.setTransactionSuccessful();
			return true;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		} finally {
			database.endTransaction();
		}
	}

	@Override
	public boolean replace(T bean) {
		if (database == null) {
			new DatabaseException("The com.loliclub.dbhelper.database is null").printStackTrace();
			return false;
		}
		try {
			ContentValues contentValues = getContentValues(bean);
			if (database.replace(bean.getClass().getSimpleName(), null,
					contentValues) < 0)
				return false;
			else
				return true;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean replace(List<T> list) throws DatabaseException {
		if (database == null) {
			new DatabaseException("The com.loliclub.dbhelper.database is null").printStackTrace();
			return false;
		}
		if (list == null || list.size() <= 0) {
			new DatabaseException("The parameters is null").printStackTrace();
			return false;
		}
		database.beginTransaction();
		try {
			for (T bean : list) {
				ContentValues contentValues = getContentValues(bean);
				if (database.replace(bean.getClass().getSimpleName(), null,
						contentValues) < 0)
					return false;
			}
			database.setTransactionSuccessful();
			return true;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		} finally {
			database.endTransaction();
		}
	}

	@Override
	public boolean update(T bean) {
		if (database == null) {
			new DatabaseException("The com.loliclub.dbhelper.database is null").printStackTrace();
			return false;
		}
		String whereClause = null;
		Field[] fieldArray = bean.getClass().getFields();
		Field value = null;
		for (Field field : fieldArray) {
			if (field.isAnnotationPresent(primaryKey.class)) {
				whereClause = field.getName() + " = ?";
				value = field;
				break;
			}
		}
		if (whereClause == null || "".equals(whereClause)) {
			new DatabaseException("The " + bean.getClass().getSimpleName()
					+ " does not has primaryKey").printStackTrace();
			return false;
		}
		String[] whereArgs = new String[1];
		try {
			whereArgs[0] = String.valueOf(value.get(bean));
			ContentValues contentValues = getContentValuesWithoutId(bean);
			if (database.update(bean.getClass().getSimpleName(), contentValues,
					whereClause, whereArgs) <= 0)
				return false;
			else
				return true;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(List<T> list) {
		if (database == null) {
			new DatabaseException("The com.loliclub.dbhelper.database is null").printStackTrace();
			return false;
		}
		if (list == null || list.size() <= 0) {
			new DatabaseException("The parameters is null").printStackTrace();
			return false;
		}
		String whereClause = null;
		Field[] fieldArray = entityClass.getFields();
		Field value = null;
		for (Field field : fieldArray) {
			if (field.isAnnotationPresent(primaryKey.class)) {
				whereClause = field.getName() + " = ?";
				value = field;
				break;
			}
		}
		if (whereClause == null || "".equals(whereClause)) {
			new DatabaseException("The " + entityClass.getSimpleName()
					+ " does not has primaryKey").printStackTrace();
			return false;
		}
		String[] whereArgs = new String[1];
		database.beginTransaction();
		try {
			for (T bean : list) {
				whereArgs[0] = String.valueOf(value.get(bean));
				ContentValues contentValues = getContentValuesWithoutId(bean);
				if (database.update(entityClass.getSimpleName(), contentValues,
						whereClause, whereArgs) <= 0)
					return false;
			}
			database.setTransactionSuccessful();
			return true;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		} finally {
			database.endTransaction();
		}
	}

	@Override
	public boolean delete(T bean) {
		if (database == null) {
			new DatabaseException("The com.loliclub.dbhelper.database is null").printStackTrace();
			return false;
		}
		String whereClause = null;
		Field[] fieldArray = bean.getClass().getFields();
		Field value = null;
		for (Field field : fieldArray) {
			if (field.isAnnotationPresent(primaryKey.class)) {
				whereClause = field.getName() + " = ?";
				value = field;
				break;
			}
		}
		if (whereClause == null || "".equals(whereClause)) {
			new DatabaseException("The " + bean.getClass().getSimpleName()
					+ " does not has primaryKey").printStackTrace();
			return false;
		}
		String[] whereArgs = new String[1];
		try {
			whereArgs[0] = String.valueOf(value.get(bean));
			if (database.delete(bean.getClass().getSimpleName(), whereClause,
					whereArgs) == 0)
				return false;
			else
				return true;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(List<T> list) {
		if (database == null) {
			new DatabaseException("The com.loliclub.dbhelper.database is null").printStackTrace();
			return false;
		}
		if (list == null || list.size() <= 0) {
			new DatabaseException("The parameters is null").printStackTrace();
			return false;
		}
		String whereClause = null;
		Field[] fieldArray = entityClass.getFields();
		Field value = null;
		for (Field field : fieldArray) {
			if (field.isAnnotationPresent(primaryKey.class)) {
				whereClause = field.getName() + " = ?";
				value = field;
				break;
			}
		}
		if (whereClause == null || "".equals(whereClause)) {
			new DatabaseException("The " + entityClass.getSimpleName()
					+ " does not has primaryKey").printStackTrace();
			return false;
		}
		String[] whereArgs = new String[1];
		database.beginTransaction();
		try {
			for (T bean : list) {
				whereArgs[0] = String.valueOf(value.get(bean));
				if (database.delete(entityClass.getSimpleName(), whereClause,
						whereArgs) == 0)
					return false;
			}
			database.setTransactionSuccessful();
			return true;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		} finally {
			database.endTransaction();
		}
	}

	@Override
	public T queryByPrimaryKey(String primaryKey) {
		if (database == null) {
			new DatabaseException("the com.loliclub.dbhelper.database is null").printStackTrace();
			return null;
		}
		String sql = null;
		Field[] fieldArray = entityClass.getClass().getFields();
		for (Field field : fieldArray) {
			if (field.isAnnotationPresent(primaryKey.class)) {
				sql = "select * from " + entityClass.getClass().getSimpleName()
						+ " where " + field.getName() + " = ?";
				break;
			}
		}
		if (sql == null || "".equals(sql)) {
			new DatabaseException("The "
					+ entityClass.getClass().getSimpleName()
					+ " does not has primaryKey").printStackTrace();
			return null;
		}
		String[] selectionArgs = new String[] { primaryKey };
		Cursor cursor = database.rawQuery(sql, selectionArgs);
		if (cursor == null || cursor.getCount() <= 0)
			return null;
		if (cursor.moveToNext()) {
			try {
				T bean = entityClass.newInstance();
				bean.parse(cursor, 0);
				cursor.close();
				return bean;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		cursor.close();
		return null;
	}

	@Override
	public List<T> query(String where, String[] whereArgs) {
		if (database == null) {
			new DatabaseException("The com.loliclub.dbhelper.database is null").printStackTrace();
			return null;
		}
		if (where == null || "".equals(where)) {
			new DatabaseException("The parameters is null").printStackTrace();
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ");
		sql.append(entityClass.getSimpleName());
		sql.append(" where ");
		sql.append(where);
		Cursor cursor = database.rawQuery(sql.toString(), whereArgs);
		if (cursor == null || cursor.getCount() <= 0)
			return null;
		List<T> list = new ArrayList<T>();
		while (cursor.moveToNext()) {
			try {
				T bean = entityClass.newInstance();
				bean.parse(cursor, 0);
				list.add(bean);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		cursor.close();
		return list;
	}

	@Override
	public List<T> execQuery(String sql, String[] whereArgs) {
		if (database == null) {
			new DatabaseException("The com.loliclub.dbhelper.database is null").printStackTrace();
			return null;
		}
		if (sql == null || "".equals(sql)) {
			new DatabaseException("the parameters is null").printStackTrace();
			return null;
		}
		Cursor cursor = database.rawQuery(sql, whereArgs);
		if (cursor == null || cursor.getCount() <= 0)
			return null;
		List<T> list = new ArrayList<T>();
		while (cursor.moveToNext()) {
			try {
				T bean = entityClass.newInstance();
				bean.parse(cursor, 0);
				list.add(bean);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		cursor.close();
		return list;
	}

	@Override
	public void execSQL(String sql, String[] whereArgs) {
		if (database == null) {
			new DatabaseException("The com.loliclub.dbhelper.database is null").printStackTrace();
			return;
		}
		if (sql == null || "".equals(sql)) {
			new DatabaseException("The parameters is null").printStackTrace();
			return;
		}
		database.execSQL(sql, whereArgs);
	}

	/**
	 * 获取IBeanStandard对象除Id外的属性集合，该集合的实质是一个HashMap
	 * 
	 * @param bean
	 *            需要解析IBeanStandard对象
	 * @return
	 */
	protected ContentValues getContentValuesWithoutId(T bean)
			throws IllegalAccessException {
		if (bean == null)
			return null;
		ContentValues contentValues = new ContentValues();
		Field[] fieldArray = bean.getClass().getFields();
		for (Field field : fieldArray) {
			if (field.isAnnotationPresent(primaryKey.class))
				continue;
			else if (field.isAnnotationPresent(without.class))
				continue;
			else if (Modifier.isStatic(field.getModifiers())
					|| Modifier.isFinal(field.getModifiers())) {
				continue;
			} else {
				if (field.getModifiers() == Modifier.STATIC)
					continue;
				if ("boolean".equals(field.getType().getName())) {
					contentValues.put(field.getName(), field.getBoolean(bean));
				} else if ("byte".equals(field.getType().getName())) {
					contentValues.put(field.getName(), field.getByte(bean));
				} else if ("double".equals(field.getType().getName())) {
					contentValues.put(field.getName(), field.getDouble(bean));
				} else if ("float".equals(field.getType().getName())) {
					contentValues.put(field.getName(), field.getFloat(bean));
				} else if ("int".equals(field.getType().getName())) {
					contentValues.put(field.getName(), field.getInt(bean));
				} else if ("long".equals(field.getType().getName())) {
					contentValues.put(field.getName(), field.getLong(bean));
				} else if ("short".equals(field.getType().getName())) {
					contentValues.put(field.getName(), field.getShort(bean));
				} else if ("java.lang.String".equals(field.getType().getName())) {
					contentValues
							.put(field.getName(), (String) field.get(bean));
				} else if ("java.com.loliclub.dbhelper.util.Date".equals(field.getType().getName())) {
					contentValues.put(field.getName(),
							BeanUtil.dateToString((Date) field.get(bean)));
				} else {
					continue;
				}
			}
		}
		return contentValues;
	}

	/**
	 * 获取IBeanStandard对象的所有属性集合，该集合的实质是一个HashMap
	 * 
	 * @param bean
	 *            需要解析IBeanStandard对象
	 * @return
	 */
	protected ContentValues getContentValues(T bean)
			throws IllegalAccessException {
		if (bean == null)
			return null;
		ContentValues contentValues = new ContentValues();
		Field[] fieldArray = bean.getClass().getFields();
		for (Field field : fieldArray) {
			if (field.isAnnotationPresent(without.class))
				continue;
			else if (Modifier.isStatic(field.getModifiers())
					|| Modifier.isFinal(field.getModifiers())) {
				continue;
			} else {
				if ("boolean".equals(field.getType().getName())) {
					contentValues.put(field.getName(), field.getBoolean(bean));
				} else if ("byte".equals(field.getType().getName())) {
					contentValues.put(field.getName(), field.getByte(bean));
				} else if ("double".equals(field.getType().getName())) {
					contentValues.put(field.getName(), field.getDouble(bean));
				} else if ("float".equals(field.getType().getName())) {
					contentValues.put(field.getName(), field.getFloat(bean));
				} else if ("int".equals(field.getType().getName())) {
					contentValues.put(field.getName(), field.getInt(bean));
				} else if ("long".equals(field.getType().getName())) {
					contentValues.put(field.getName(), field.getLong(bean));
				} else if ("short".equals(field.getType().getName())) {
					contentValues.put(field.getName(), field.getShort(bean));
				} else if ("java.lang.String".equals(field.getType().getName())) {
					contentValues
							.put(field.getName(), (String) field.get(bean));
				} else if ("java.com.loliclub.dbhelper.util.Date".equals(field.getType().getName())) {
					contentValues.put(field.getName(),
							BeanUtil.dateToString((Date) field.get(bean)));
				} else {
					continue;
				}
			}
		}
		return contentValues;
	}

}

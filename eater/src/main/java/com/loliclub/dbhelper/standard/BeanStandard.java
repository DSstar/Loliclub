package com.loliclub.dbhelper.standard;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;

import com.loliclub.dbhelper.database.DatabaseException;
import com.loliclub.dbhelper.util.BeanUtil;
import com.loliclub.dbhelper.util.Mapping;

/**
 * Bean类标准抽象类。 该类采用反射的机制自动完成{@link IBeanStandard}接口的操作。
 * 该抽象类支持的类型有boolean,byte,double,float,int,long,short,
 * java.lang.String,java.com.loliclub.dbhelper.util.Date,{@link IBeanStandard}
 * 
 * <p>
 * PS.
 * </p>
 * <ul>
 * <li>1.请将需要在数据库中创建字段的属性设置为public</li>
 * <li>2.请使用@primaryKey标注主键</li>
 * <li>3.请使用@without标注不在数据库创建字段的属性</li>
 * <li>4.请使用@alert标注需要修改的属性</li>
 * <li>5.请按照规范编写所有IBeanStandard类</li>
 * <li>6.继承于该类的子类不能混淆</li>
 * </ul>
 */
public abstract class BeanStandard implements IBeanStandard {

	private static final long serialVersionUID = 1L;

	@Override
	public void parse(Cursor cursor, int type) {
		if (cursor == null)
			return;
		/**
		 * 获取IBeanStandard的所有public属性，排除static和final两种。
		 * 再根据属性名从cursor中获取对应的值，根据属性的类型转换后将值设置到对应属性中。
		 */
		Field[] fieldArray = this.getClass().getFields();
		try {
			for (Field field : fieldArray) {
				if (Modifier.isStatic(field.getModifiers())
						|| Modifier.isFinal(field.getModifiers()))
					continue;
				if ("boolean".equals(field.getType().getName())) {
					field.setBoolean(this, Boolean.parseBoolean(cursor
							.getString(cursor.getColumnIndex(field.getName()))));
				} else if ("byte".equals(field.getType().getName())) {
					field.setByte(this, Byte.parseByte(cursor.getString(cursor
							.getColumnIndex(field.getName()))));
				} else if ("char".equals(field.getType().getName())) {
					field.setChar(
							this,
							cursor.getString(
									cursor.getColumnIndex(field.getName()))
									.charAt(0));
				} else if ("double".equals(field.getType().getName())) {
					field.setDouble(field.getName(), cursor.getDouble(cursor
							.getColumnIndex(field.getName())));
				} else if ("float".equals(field.getType().getName())) {
					field.setFloat(this, cursor.getFloat(cursor
							.getColumnIndex(field.getName())));
				} else if ("int".equals(field.getType().getName())) {
					field.setInt(this, cursor.getInt(cursor
							.getColumnIndex(field.getName())));
				} else if ("long".equals(field.getType().getName())) {
					field.setLong(this, cursor.getLong(cursor
							.getColumnIndex(field.getName())));
				} else if ("short".equals(field.getType().getName())) {
					field.setShort(this, cursor.getShort(cursor
							.getColumnIndex(field.getName())));
				} else if ("java.lang.String".equals(field.getType().getName())) {
					field.set(this, cursor.getString(cursor
							.getColumnIndex(field.getName())));
				} else if ("java.com.loliclub.dbhelper.util.Date".equals(field.getType().getName())) {
					try {
						field.set(this, BeanUtil.stringToDate(cursor
								.getString(cursor.getColumnIndex(field
										.getName()))));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setAttribute(JSONObject jsonObj, int type) {
		if (jsonObj == null)
			return;
		/**
		 * 获取IBeanStandard的所有public属性，排除static和final两种。
		 * 再根据属性名从jsonObj中获取对应的值，根据属性的类型转换后将值设置到对应属性中。
		 */
		Field[] fieldArray = this.getClass().getFields();
		try {
			for (Field field : fieldArray) {
				if (Modifier.isStatic(field.getModifiers())
						|| Modifier.isFinal(field.getModifiers()))
					continue;
				// 如果该属性未出现在参数中，则不处理
				if (jsonObj.optString(field.getName()) == null
						|| "".equals(jsonObj.optString(field.getName())))
					continue;
				if ("boolean".equals(field.getType().getName())) {
					field.setBoolean(this, jsonObj.optBoolean(field.getName()));
				} else if ("byte".equals(field.getType().getName())) {
					field.setByte(this, Byte.valueOf(jsonObj.optString(field.getName())));
				} else if ("char".equals(field.getType().getName())) {
					field.setChar(this, jsonObj.optString(field.getName()).charAt(0));
				} else if ("double".equals(field.getType().getName())) {
					field.setDouble(this, jsonObj.optDouble(field.getName()));
				} else if ("float".equals(field.getType().getName())) {
					field.setFloat(this, Float.valueOf(jsonObj.optString(field.getName())));
				} else if ("int".equals(field.getType().getName())) {
					field.setInt(this, jsonObj.optInt(field.getName()));
				} else if ("long".equals(field.getType().getName())) {
					field.setLong(this, jsonObj.optLong(field.getName()));
				} else if ("short".equals(field.getType().getName())) {
					field.setShort(this, Short.valueOf(jsonObj.optString(field.getName())));
				} else if ("java.lang.String".equals(field.getType().getName())) {
					field.set(this, jsonObj.optString(field.getName()));
				} else if ("java.com.loliclub.dbhelper.util.Date".equals(field.getType().getName())) {
					try {
						field.set(this, BeanUtil.stringToDate(jsonObj
								.optString(field.getName())));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else if (field.get(this) instanceof IBeanStandard) {
					if (jsonObj.optJSONObject(field.getName()) != null) {
						IBeanStandard bean = (IBeanStandard) field.get(this);
						JSONObject jsonObject = jsonObj.optJSONObject(field
								.getName());
						if (bean == null) {
							Constructor<?>[] constructorArray = Class.forName(
									field.getType().getName())
									.getConstructors();
							if (constructorArray == null
									|| constructorArray.length <= 0)
								continue;
							else {
								bean = (IBeanStandard) constructorArray[0]
										.newInstance(new Object[] {});
							}
						}
						bean.setAttribute(jsonObject, type);
						field.set(this, bean);
					}
				} else {
					throw new DatabaseException("Can not reslve Type:"
							+ field.getType().getName());
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e2) {
			e2.printStackTrace();
		} catch (InvocationTargetException e3) {
			e3.printStackTrace();
		} catch (DatabaseException e4) {
			e4.printStackTrace();
		}
	}

	@Override
	public JSONObject toJSONObject(int type) {
		JSONObject jsonObj = new JSONObject();
		/**
		 * 获取IBeanStandard的所有public属性，排除static和final两种。 再根据属性类型将属性值放入jsonObj中
		 */
		Field[] fieldArray = this.getClass().getFields();
		try {
			for (Field field : fieldArray) {
				if (Modifier.isStatic(field.getModifiers())
						|| Modifier.isFinal(field.getModifiers()))
					continue;
				if ("boolean".equals(field.getType().getName())) {
					jsonObj.put(field.getName(), field.getBoolean(this));
				} else if ("byte".equals(field.getType().getName())) {
					jsonObj.put(field.getName(), field.getByte(this));
				} else if ("char".equals(field.getType().getName())) {
					jsonObj.put(field.getName(), field.getChar(this));
				} else if ("double".equals(field.getType().getName())) {
					jsonObj.put(field.getName(), field.getDouble(this));
				} else if ("float".equals(field.getType().getName())) {
					jsonObj.put(field.getName(), field.getFloat(this));
				} else if ("int".equals(field.getType().getName())) {
					jsonObj.put(field.getName(), field.getInt(this));
				} else if ("long".equals(field.getType().getName())) {
					jsonObj.put(field.getName(), field.getLong(this));
				} else if ("short".equals(field.getType().getName())) {
					jsonObj.put(field.getName(), field.getShort(this));
				} else if ("java.lang.String".equals(field.getType().getName())) {
					jsonObj.put(field.getName(), field.get(this));
				} else if ("java.com.loliclub.dbhelper.util.Date".equals(field.getType().getName())) {
					jsonObj.put(field.getName(),
							BeanUtil.dateToString((Date) field.get(this)));
				} else if (field.get(this) instanceof IBeanStandard) {
					jsonObj.put(field.getName(), ((IBeanStandard) field
							.get(this)).toJSONObject(type));
				} else {
					throw new DatabaseException("Can not reslve Type:"
							+ field.getType().getName());
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (DatabaseException e4) {
			e4.printStackTrace();
			return null;
		}
		return jsonObj;
	}

	@Override
	public void setDefault(int type) {

	};

	@Override
	public String getCreateTableSQL() {
		/**
		 * 通过Mapping对象解析该实体类，并生成对应的语句
		 */
		Mapping mapping = new Mapping();
		try {
			mapping.resolveEntity(this);
			return mapping.getCreateTableSQL();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String[] getAdvancedSQL() {
		/**
		 * 此处放置高级SQL语句，这些语句只在创建时运行
		 */
		return null;
	}

	@Override
	public String getDeleteTableSQL() {
		return "drop table if exists " + this.getClass().getSimpleName();
	}

	/**
	 * 目前该方法尚不完善，需要使用时建议重写该方法 该方法目前的运行方法是： 先判断实体类在当前版本中的行为，根据具体的行为执行对应的操作
	 */
	@Override
	public List<String> getUpdateTableSQL(int oldVersion, int newVersion) {
		if (this.getClass().isAnnotationPresent(delete.class)) {
			List<String> list = new ArrayList<String>();
			list.add(getDeleteTableSQL());
			return list;
		} else if (this.getClass().isAnnotationPresent(create.class)) {
			List<String> list = new ArrayList<String>();
			list.add(getCreateTableSQL());
			return list;
		} else if (this.getClass().isAnnotationPresent(update.class)) {
			List<String> list = new ArrayList<String>();
			Mapping mapping = new Mapping();
			try {
				/**
				 * 在修改该表之前，首先应该确认该表是否存在，然后再执行修改语句
				 */
				mapping.resolveEntity(this);
				list.add(getCreateTableSQL());
				List<String> updateSQLList = mapping.getUpdateTabelSQL();
				if (updateSQLList != null && updateSQLList.size() > 0) {
					for (String sql : updateSQLList) {
						list.add(sql);
					}
				}
				return list;
			} catch (DatabaseException e) {
				e.printStackTrace();
				return null;
			}
		} else
			return null;
	}
}

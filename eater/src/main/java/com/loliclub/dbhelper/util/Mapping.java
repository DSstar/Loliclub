package com.loliclub.dbhelper.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.loliclub.dbhelper.database.DatabaseException;
import com.loliclub.dbhelper.standard.IBeanStandard;
import com.loliclub.dbhelper.standard.IBeanStandard.alter;
import com.loliclub.dbhelper.standard.IBeanStandard.primaryKey;
import com.loliclub.dbhelper.standard.IBeanStandard.without;

/**
 * 实体映射类，作为实体类与数据库表之间的映射对象 <br>
 * 该对象仅为内部需要使用反射时使用，目前反射的方法尚不完善，因此不建议使用。
 */
public class Mapping {

	public static final String TAG = "Mapping";

	// 类名
	private String className;

	// 主键
	private String primaryKey;

	// 主键类型
	private String primaryKeyType;

	// 属性列表（不包括主键）
	private Map<String, String> propertyMap;

	// 不需要创建字段的属性列表
	private Map<String, String> withoutMap;

	// 需要修改的字段的属性列表
	private Map<String, String> alterMap;

	public Mapping() {

	}

	// 初始化对象的所有属性
	private void initMapping() {
		className = null;
		primaryKey = null;
		primaryKeyType = null;
		propertyMap = new HashMap<String, String>();
		withoutMap = new HashMap<String, String>();
		alterMap = new HashMap<String, String>();
	}

	/**
	 * 解析实体类
	 * 
	 * @param standardBean
	 * @throws DatabaseException
	 *             如果该类包含两个主键则抛出该异常
	 */
	public void resolveEntity(IBeanStandard standardBean)
			throws DatabaseException {
		if (standardBean == null)
			throw new DatabaseException("The parameters is null");
		initMapping();
		className = standardBean.getClass().getSimpleName();
		Field[] fieldArray = standardBean.getClass().getFields();
		for (Field field : fieldArray) {
			if (Modifier.isStatic(field.getModifiers())
					|| Modifier.isFinal(field.getModifiers())) {
				continue;
			} else if (field.isAnnotationPresent(primaryKey.class)) {
				if (primaryKey != null || primaryKeyType != null) {
					throw new DatabaseException(
							"There are more than one primaryKey");
				}
				primaryKey = field.getName();
				primaryKeyType = field.getType().getName();
			} else if (field.isAnnotationPresent(without.class)) {
				withoutMap.put(field.getName(), field.getType().getName());
			} else if (field.isAnnotationPresent(alter.class)) {
				alterMap.put(field.getName(), field.getType().getName());
			} else {
				propertyMap.put(field.getName(), field.getType().getName());
			}
		}
	}

	/**
	 * 获取创建数据库表的SQL语句
	 * 
	 * @return
	 */
	public String getCreateTableSQL() {
		if (primaryKey == null || primaryKeyType == null)
			return null;
		StringBuffer createSQL = new StringBuffer();
		createSQL.append("create table if not exists ");
		createSQL.append(className);
		createSQL.append("(");
		createSQL.append(primaryKey);
		createSQL.append(getParamsType(primaryKeyType));
		createSQL.append(" primary key not null ");
		Iterator<String> propertyIterator = propertyMap.keySet().iterator();
		while (propertyIterator.hasNext()) {
			String paramName = propertyIterator.next();
			createSQL.append(",");
			createSQL.append(paramName);
			createSQL.append(getParamsType(propertyMap.get(paramName)));
		}
		propertyIterator = alterMap.keySet().iterator();
		while (propertyIterator.hasNext()) {
			String paramName = propertyIterator.next();
			createSQL.append(",");
			createSQL.append(paramName);
			createSQL.append(getParamsType(alterMap.get(paramName)));
		}
		createSQL.append(");");
		return createSQL.toString();
	}

	/**
	 * 获取修改数据库表的SQL语句
	 * 
	 * @return
	 */
	public List<String> getUpdateTabelSQL() {
		if (alterMap == null || alterMap.size() <= 0)
			return null;
		List<String> alterSQLList = new ArrayList<String>();
		Iterator<String> propertyIterator = alterMap.keySet().iterator();
		while (propertyIterator.hasNext()) {
			StringBuffer alterSQL = new StringBuffer();
			alterSQL.append("alter table ");
			alterSQL.append(className);
			alterSQL.append(" add column ");
			String paramName = propertyIterator.next();
			alterSQL.append(paramName);
			alterSQL.append(" ");
			alterSQL.append(getParamsType(alterMap.get(paramName)));
			alterSQL.append(";");
			alterSQLList.add(alterSQL.toString());
		}
		return alterSQLList;
	}

	/**
	 * 获取删除数据库表的语句
	 * 
	 * @return
	 */
	public String getDeleteTableSQL() {
		return "drop table if exists " + className + ";";
	}

	/**
	 * 根据参数的类型获取对应的数据库类型
	 * 
	 * @param fieldTypeName
	 * @return
	 * @throws DatabaseException
	 */
	private String getParamsType(String fieldTypeName) {
		if (fieldTypeName == null)
			return null;
		String type = null;
		if ("boolean".equals(fieldTypeName)) {
			type = " varchar(10) ";
		} else if ("byte".equals(fieldTypeName)) {
			type = " varchar(10) ";
		} else if ("char".equals(fieldTypeName)) {
			type = " varchar(10) ";
		} else if ("double".equals(fieldTypeName)) {
			type = " double ";
		} else if ("float".equals(fieldTypeName)) {
			type = " double ";
		} else if ("int".equals(fieldTypeName)) {
			type = " integer ";
		} else if ("long".equals(fieldTypeName)) {
			type = " varchar(20) ";
		} else if ("short".equals(fieldTypeName)) {
			type = " varchar(10)";
		} else if ("java.lang.String".equals(fieldTypeName)) {
			type = " varchar(20) ";
		} else if ("java.com.loliclub.dbhelper.util.Date".equals(fieldTypeName)) {
			type = " date ";
		} else {
			try {
				throw new DatabaseException(
						"Can not resolve this parameters type : "
								+ fieldTypeName);
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		}
		return type;
	}

	public String getClassName() {
		return className;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public String getPrimaryKeyType() {
		return primaryKeyType;
	}

	public Map<String, String> getPropertyMap() {
		return propertyMap;
	}

	public Map<String, String> getWithoutMap() {
		return withoutMap;
	}

	public Map<String, String> getAlterMap() {
		return alterMap;
	}

}

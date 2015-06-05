package com.loliclub.dbhelper.standard;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.loliclub.dbhelper.database.DatabaseHelper;

import android.database.Cursor;

/**
 * Bean标准接口，继承于{@link Serializable}实现了该接口的类才可以使用{@link DatabaseHelper}
 * 
 */
public interface IBeanStandard extends Serializable {

	/**
	 * 数据库游标转换为BeanStandard对象
	 * 
	 * @param cursor
	 * @param type
	 */
	public void parse(Cursor cursor, int type);

	/**
	 * 用于解析服务器下载下来的JSON数据 反序列化 与其他模块交互的标准
	 * 
	 * @param jsonObj
	 * @param type
	 */
	public void setAttribute(JSONObject jsonObj, int type);

	/**
	 * 用于生成向服务器发送的的JSON语句或序列化或与其他模块交互的标准
	 * 
	 * @param type
	 * @return
	 * @throws JSONException
	 */
	public JSONObject toJSONObject(int type);

	/**
	 * 设置默认值
	 * 
	 * @param type
	 */
	public void setDefault(int type);

	/**
	 * 获取创建表的SQL语句
	 * 
	 * @return
	 */
	public String getCreateTableSQL();

	/**
	 * 获取其他高级的SQL语句，如创建触发器，索引等
	 * 
	 * @return
	 */
	public String[] getAdvancedSQL();

	/**
	 * 获取删除表的SQL语句
	 * 
	 * @return
	 */
	public String getDeleteTableSQL();

	/**
	 * 获取修改表的SQL语句
	 * 
	 * @param oldVersion
	 * @param newVersion
	 * @return
	 */
	public List<String> getUpdateTableSQL(int oldVersion, int newVersion);

	/**
	 * 当前版本下该表为需要新增加的表
	 */
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface create {

	}

	/**
	 * 当前版本下该表为需要修改的表
	 */
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface update {

	}

	/**
	 * 当前版本下该表为需要删除的表
	 */
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface delete {

	}

	/**
	 * 该字段为数据库表的主键
	 */
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface primaryKey {

	}

	/**
	 * 该属性为新增加的字段
	 */
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface alter {

	}

	/**
	 * 该属性不会在数据库中创建字段
	 */
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface without {

	}
}

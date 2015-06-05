package com.loliclub.dbhelper.standard;

import java.util.List;

/**
 * Bean类Serveice标准接口
 * 
 */
public interface IServiceStandard<T extends IBeanStandard> {

	/**
	 * 插入一个对象
	 * 
	 * @param bean
	 *            插入的对象
	 * @return 插入成功返回true,错误返回false
	 */
	public boolean insert(T bean);

	/**
	 * 插入一个对象列表
	 * 
	 * @param list
	 *            插入的列表
	 * @return 插入成功返回true,错误返回false
	 */
	public boolean insert(List<T> list);

	/**
	 * 替换一个对象
	 * 
	 * @param bean
	 *            替换的对象
	 * @return 替换成功返回true,错误返回false
	 */
	public boolean replace(T bean);

	/**
	 * 替换一个对象列表
	 * 
	 * @param list
	 *            替换的列表
	 * @return 替换成功返回true,错误返回false
	 */
	public boolean replace(List<T> list);

	/**
	 * 修改一个对象
	 * 
	 * @param bean
	 *            修改的对象
	 * @return 修改成功返回true,错误返回false
	 */
	public boolean update(T bean);

	/**
	 * 修改一个对象列表
	 * 
	 * @param list
	 *            修改的列表
	 * @return 修改成功返回true,错误返回false
	 */
	public boolean update(List<T> list);

	/**
	 * 删除一个对象
	 * 
	 * @param bean
	 *            删除的对象
	 * @return 删除成功返回true,错误返回false
	 */
	public boolean delete(T bean);

	/**
	 * 删除一个对象列表
	 * 
	 * @param list
	 *            删除的列表
	 * @return 删除成功返回true,错误返回false
	 */
	public boolean delete(List<T> list);

	/**
	 * 查询所有数据
	 * 
	 * @return
	 */
	public T queryByPrimaryKey(String primaryKey);

	/**
	 * 按照条件查询
	 * 
	 * @param where
	 *            where语句的内容，例如：id = ?
	 * @param whereArgs
	 *            where语句的参数
	 * @return
	 */
	public List<T> query(String where, String[] whereArgs);

	/**
	 * 执行SQL查询语句
	 * 
	 * @param SQL
	 * @param whereArgs
	 * @return
	 */
	public List<T> execQuery(String sql, String[] whereArgs);

	/**
	 * 执行SQL语句
	 * 
	 * @param SQL
	 * @param whereArgs
	 * @return
	 */
	public void execSQL(String sql, String[] whereArgs);
}

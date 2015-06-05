package com.loliclub.dbhelper.standard;

import java.util.List;

/**
 * 统计标准接口，定义了进行统计的方法
 */
public interface IStatisticsStandard<T extends IBeanStandard> {

	/**
	 * 根据参数队列统计数据
	 * 
	 * @param list
	 *            需要参与统计的数据集合
	 * @param type
	 *            类型，备用
	 */
	public void statistics(List<T> list, int type);

	/**
	 * 根据参数统计数据
	 * 
	 * @param bean
	 *            需要参与统计的数据对象
	 * @param type
	 *            类型，备用
	 */
	public void statistics(T bean, int type);

}

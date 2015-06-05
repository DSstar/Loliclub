package com.loliclub.eater.bean;

import com.loliclub.dbhelper.standard.BeanStandard;

public class EaterOrder extends BeanStandard {

	/**
	 * 与数据库版本号一致
	 */
	private static final long serialVersionUID = 1L;

	@primaryKey
	public String id;
	
	public String restaurantId;
	
	public String menuId;
	
	public String eaterId;
	
	public int count;
	
	public double price;

	@Override
	public String toString() {
		return "EaterOrder{" +
				"id='" + id + '\'' +
				", restaurantId='" + restaurantId + '\'' +
				", menuId='" + menuId + '\'' +
				", eaterId='" + eaterId + '\'' +
				", count=" + count +
				", price=" + price +
				'}';
	}
}

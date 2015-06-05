package com.loliclub.eater.bean;

import com.loliclub.dbhelper.standard.BeanStandard;

public class Menu extends BeanStandard {

	/**
	 * 与数据库版本号一致
	 */
	private static final long serialVersionUID = 1L;
	
	@primaryKey
	public String id;
	
	public String restaurantId;
	
	public String category;
	
	public String name;
	
	public double price;

	@Override
	public String toString() {
		return "Menu{" +
				"id='" + id + '\'' +
				", restaurantId='" + restaurantId + '\'' +
				", category='" + category + '\'' +
				", name='" + name + '\'' +
				", price=" + price +
				'}';
	}
}

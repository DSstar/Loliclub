package com.loliclub.eater.bean;

import com.loliclub.dbhelper.standard.BeanStandard;

public class Restaurant extends BeanStandard {

	/**
	 * 与数据库版本号一致
	 */
	private static final long serialVersionUID = 1L;
	
	@primaryKey
	public String id;
	
	public String name;
	
	public String phoneNumber;

	@Override
	public String toString() {
		return "Restaurant{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				'}';
	}
}

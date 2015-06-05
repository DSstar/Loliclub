package com.loliclub.eater.bean;

import com.loliclub.dbhelper.standard.BeanStandard;

public class Eater extends BeanStandard {

	/**
	 * 与数据库版本号一致
	 */
	private static final long serialVersionUID = 1L;
	
	@primaryKey
	public String id;
	
	public String name;
	
	public String password;

	@Override
	public String toString() {
		return "Eater{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}

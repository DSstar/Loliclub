package com.loliclub.eater.bean;

import com.loliclub.dbhelper.standard.BeanStandard;

public class Account extends BeanStandard {

	/**
	 * 与数据库版本号一致
	 */
	private static final long serialVersionUID = 1L;
	
	@primaryKey
	public String id;

	public String username;

	public String password;

	public String accountProfile;
	
	public long timestamp;

	@Override
	public String toString() {
		return "Account{" +
				"id='" + id + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", accountProfile='" + accountProfile + '\'' +
				", timestamp=" + timestamp +
				'}';
	}
}

package com.loliclub.eater.bean;

import com.loliclub.dbhelper.standard.BeanStandard;

public class Member extends BeanStandard {

	/**
	 * 与数据库版本号一致
	 */
	private static final long serialVersionUID = 1L;
	
	@primaryKey
	public String id;
	
	public String accountId;
	
	public String memberProfile;

	@Override
	public String toString() {
		return "Member{" +
				"id='" + id + '\'' +
				", accountId='" + accountId + '\'' +
				", memberProfile='" + memberProfile + '\'' +
				'}';
	}
}

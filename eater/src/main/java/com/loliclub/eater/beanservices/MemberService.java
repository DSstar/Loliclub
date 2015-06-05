package com.loliclub.eater.beanservices;

import android.database.sqlite.SQLiteDatabase;

import com.loliclub.dbhelper.standard.ServiceStandard;
import com.loliclub.eater.bean.Member;

public class MemberService extends ServiceStandard<Member> {

	public MemberService(SQLiteDatabase database) {
		super(database);
	}

}

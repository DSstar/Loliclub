package com.loliclub.eater.beanservices;

import com.loliclub.dbhelper.standard.ServiceStandard;
import com.loliclub.eater.bean.Account;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class AccountService extends ServiceStandard<Account> {

	public AccountService(SQLiteDatabase database) {
		super(database);
	}

	public Account queryAccount(String userName) {
		List<Account> list = this.query("username = ?", new String[]{userName});
		if (list == null || list.size() <= 0)
			return null;
		else
			return list.get(0);
	}

}

package com.loliclub.eater.properties;

import java.util.ArrayList;
import java.util.List;

import com.loliclub.dbhelper.database.DatabaseHandler;
import com.loliclub.dbhelper.standard.IBeanStandard;
import com.loliclub.eater.bean.Account;
import com.loliclub.eater.bean.Eater;
import com.loliclub.eater.bean.Member;
import com.loliclub.eater.bean.Menu;
import com.loliclub.eater.bean.EaterOrder;
import com.loliclub.eater.bean.Restaurant;

public class MyDatabaseHandler extends DatabaseHandler {

	public static final String TAG = "MyDatabaseHandler";
	
	public static final String DATABASE_NAME = "EaterDatabase";
	
	public static final int DATABASE_VERSION = 1;
	
	private static MyDatabaseHandler databaseHandler;

	private MyDatabaseHandler(String databaseName, int version) {
		super(databaseName, version);
	}
	
	private static synchronized void syncInit() {
		if(databaseHandler == null)
			databaseHandler = new MyDatabaseHandler(DATABASE_NAME, DATABASE_VERSION); 
	}
	
	public static synchronized MyDatabaseHandler getInstance() {
		syncInit();
		return databaseHandler;
	}
	
	@Override
	public List<IBeanStandard> getBeanList() {
		List<IBeanStandard> beanList = new ArrayList<IBeanStandard>();
		beanList.add(new Account());
		beanList.add(new Member());
		beanList.add(new Eater());
		beanList.add(new Menu());
		beanList.add(new EaterOrder());
		beanList.add(new Restaurant());
		return beanList;
	}

}

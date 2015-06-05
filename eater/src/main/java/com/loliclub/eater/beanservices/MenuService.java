package com.loliclub.eater.beanservices;

import android.database.sqlite.SQLiteDatabase;

import com.loliclub.dbhelper.standard.ServiceStandard;
import com.loliclub.eater.bean.Menu;

public class MenuService extends ServiceStandard<Menu> {

	public MenuService(SQLiteDatabase database) {
		super(database);
	}

}

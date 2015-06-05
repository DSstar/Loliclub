package com.loliclub.eater.beanservices;

import android.database.sqlite.SQLiteDatabase;

import com.loliclub.dbhelper.standard.ServiceStandard;
import com.loliclub.eater.bean.Restaurant;

public class RestaurantService extends ServiceStandard<Restaurant> {

	public RestaurantService(SQLiteDatabase database) {
		super(database);
	}

}

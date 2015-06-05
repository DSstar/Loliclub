package com.loliclub.eater.beanservices;

import android.database.sqlite.SQLiteDatabase;

import com.loliclub.dbhelper.standard.ServiceStandard;
import com.loliclub.eater.bean.EaterOrder;

public class EaterOrderService extends ServiceStandard<EaterOrder> {

	public EaterOrderService(SQLiteDatabase database) {
		super(database);
	}

}

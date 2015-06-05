package com.loliclub.eater.beanservices;

import android.database.sqlite.SQLiteDatabase;

import com.loliclub.dbhelper.standard.ServiceStandard;
import com.loliclub.eater.bean.Eater;

public class EaterService extends ServiceStandard<Eater> {

	public EaterService(SQLiteDatabase database) {
		super(database);
	}

}

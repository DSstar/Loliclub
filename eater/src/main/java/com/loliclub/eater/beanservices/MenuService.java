package com.loliclub.eater.beanservices;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.loliclub.dbhelper.standard.ServiceStandard;
import com.loliclub.eater.bean.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuService extends ServiceStandard<Menu> {

	public MenuService(SQLiteDatabase database) {
		super(database);
	}

	public List<String> queryCaregory(String restaurantId) {
		String sql = "select caregory from Menu where restaurantId = ?";
		Cursor cursor = getDataBase().rawQuery(sql, new String[]{restaurantId});
		if (cursor == null || cursor.getCount() <= 0)
			return null;
		List<String> list = new ArrayList<>();
		while (cursor.moveToNext())
			list.add(cursor.getString(0));
		return list;
	}

}

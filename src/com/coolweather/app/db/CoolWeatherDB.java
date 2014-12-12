package com.coolweather.app.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

public class CoolWeatherDB {

	/**
	 * 数据库名
	 */
	public static final String DB_NAME = "cool_weather";
	
	public static final String TABLE_PROVINCE = "Province";
	
	public static final String TABLE_CTIY = "City";
	
	public static final String TABLE_COUNTY = "County";
	
	/**
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	private CoolWeatherDB(Context context) {
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME,
				null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/**
	 * 获取CoolWeatherDB的实例
	 * @param context
	 * @return
	 */
	public synchronized static CoolWeatherDB getInstance (Context context) {
		if (context == null) {
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	/**
	 * 保存省信息到数据库中
	 * @param province
	 */
	public void saveProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert(TABLE_PROVINCE, null, values);
		}
	}
	
	/**
	 * 获取所有省信息
	 * @return
	 */
	public List<Province> loadProvinces() {
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query(TABLE_PROVINCE, null, null, null, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			}
			cursor.close();
		}
		return list;
	}
	
	/**
	 * 市信息保存到数据库
	 * @param city
	 */
	public void saveCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert(TABLE_CTIY, null, values);
		}
	}
	
	/**
	 * 获取某省的所有市信息
	 * @param provinceId
	 * @return
	 */
	public List<City> loadCities(int provinceId) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query(TABLE_CTIY, null, "province_id = ?", 
				new String[] {String.valueOf(provinceId)}, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
				list.add(city);
			}
			cursor.close();
		}
		return list;
	}
	
	/**
	 * 保存区县信息到数据库
	 * @param county
	 */
	public void saveCounty(County county) {
		if (county != null) {
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert(TABLE_COUNTY, null, values);
		}
	}
	
	/**
	 * 获取某市下的所有区县信息
	 * @param cityId
	 * @return
	 */
	public List<County> loadCounties(int cityId) {
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query(TABLE_COUNTY, null, "city_id = ?", 
				new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor != null) {
			while (cursor.moveToNext()) {
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
				list.add(county);
			}
			cursor.close();
		}
		return list;
	}
}
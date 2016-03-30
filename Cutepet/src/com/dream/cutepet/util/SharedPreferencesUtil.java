package com.dream.cutepet.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesUtil {

	/**
	 * 保存token值到本地
	 * 
	 * @param token
	 */
	public static void saveToken(Context context, String token) {
		Log.i("saveToken", "token！" + token);
		SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("token", token);
		editor.commit();
		Log.i("saveToken", "token保存成功！");
	}

	/**
	 * 获取数据
	 * 
	 * @return str[0]=tel;str[1]=token;
	 */
	public static String getData(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
		String data = preferences.getString("token", null);
		Log.i("getData", "token获取成功！");
		return data;
	}

	/**
	 * 删除token
	 * 
	 * @param context
	 */
	public static void deleteData(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
		// 删除token
		preferences.edit().clear().commit();
		Log.i("deleteData", "token删除成功！");
	}

}

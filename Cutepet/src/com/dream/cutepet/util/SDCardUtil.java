package com.dream.cutepet.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.storage.StorageManager;

/**
 * SD卡的操作类
 * 
 * @author 余飞
 * 
 */
public class SDCardUtil {

	/**
	 * 获取手机所有的sd卡根目录，第一条路径默认为手机内置内存路径
	 * 
	 * @param context
	 * @return
	 */
	private static String[] getAllSDCardPath(Context context) {
		StorageManager sm = (StorageManager) context
				.getSystemService(Context.STORAGE_SERVICE);
		// 获取sdcard的路径：外置和内置
		String[] paths = null;
		try {
			// 通过反射机制，拿取所有的SD卡路径数组
			paths = (String[]) sm.getClass().getMethod("getVolumePaths", null)
					.invoke(sm, null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return paths;
	}

	/**
	 * 获取所有SD卡的根目录File对象
	 * 
	 * @param context
	 * @return
	 */
	public static List<File> getAllSDcardFile(Context context) {
		String[] data = getAllSDCardPath(context);
		List<File> file = new ArrayList<File>();
		for (int i = 0; i < data.length; i++) {
			File f = new File(data[i]);
			file.add(f);
		}
		return file;
	}
}

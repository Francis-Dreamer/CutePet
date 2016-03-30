package com.dream.cutepet.util;

import java.io.File;
import java.util.List;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

/**
 * SD卡的图片处理类
 * 
 * @author 余飞
 * 
 */
public class SDCardAllPhotoUtil {

	/**
	 * 获取bitmap型图片
	 * 
	 * @param pathString
	 *            图片的sd卡地址
	 * @return
	 */
	public static Bitmap getDiskBitmap(String pathString) {
		Bitmap bitmap = null;
		try {
			File file = new File(pathString);
			if (file.exists()) {
				bitmap = BitmapFactory.decodeFile(pathString);
			}
		} catch (Exception e) {
		}
		return bitmap;
	}
	
	/**
	 * 获取本地的所有图片(jpg,png)
	 * 
	 * @param file
	 * @param data_img 
	 * @return
	 */
	@SuppressLint("DefaultLocale") 
	public static List<String> getAllFiles(File file, List<String> data_img) {
		List<String> data = data_img;
		if (hsdSdCard()) {
			File files[] = file.listFiles();
			if (files != null) {
				for (File f : files) {
					if (f.isDirectory()) {
						getAllFiles(f,data);
					} else {
						String path = f.getPath();
						int idx = path.lastIndexOf(".");
						if (idx <= 0) {
							continue;
						}
						String suffix = path.substring(idx);
						if (suffix.toLowerCase().equals(".jpg")
								|| suffix.toLowerCase().equals(".png")) {
							data.add(f.getPath());
						}
					}
				}
			}
		}
		return data;
	}

	/**
	 * 判断是否存在sd卡
	 * 
	 * @return 存在返回true
	 */
	public static boolean hsdSdCard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

}
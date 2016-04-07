package com.dream.cutepet.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.MediaStore;

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

	/**
	 * 获取指定文件夹中的所有图片(jpg,png)
	 * 
	 * @param file
	 * @param data_img
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static List<String> getAllFiles(File file, List<String> data_img) {
		List<String> data = data_img;
		File[] files = file.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					getAllFiles(f, data);
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

	/**
	 * 通过 ContentProvider 获取本地图片，并分类
	 * 
	 * <p>
	 * 因为 获取图片也是一个 耗时操作，建议开启子线程操作
	 * </p>
	 * 
	 * @param context
	 *            当前界面的activity对象上下文
	 * @return 返回所有的图片的map集合对象；没有图片返回null
	 */
	public static Map<String, List<String>> getImage(Context context) {
		Map<String, List<String>> img_data = new HashMap<String, List<String>>();
		List<String> img_list;

		// 通过ContentProvider来获取
		// 获取大图的URI
		Uri uri_img = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		// 获取缩略图的URI
		// Uri uri_img = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;

		// 获取ContentResolver
		ContentResolver resolver = context.getContentResolver();
		// 只查询jpeg和png的图片
		Cursor cursor = resolver.query(uri_img, null,
				MediaStore.Images.Media.MIME_TYPE + "=? or "
						+ MediaStore.Images.Media.MIME_TYPE + "=?",
				new String[] { "image/jpeg", "image/png" },
				MediaStore.Images.Media.DATE_MODIFIED);
		if (cursor == null) {
			return null;
		}

		while (cursor.moveToNext()) {
			// 获取图片的路径
			String path = cursor.getString(cursor
					.getColumnIndex(MediaStore.Images.Media.DATA));

			// 获取该图片的父路径名
			String parentName = new File(path).getParentFile().getName();

			// 根据父路径名将图片放入到mGruopMap中
			if (!img_data.containsKey(parentName)) {
				img_list = new ArrayList<String>();
				img_list.add(path);
				img_data.put(parentName, img_list);
			} else {
				img_data.get(parentName).add(path);
			}
		}

		String fileName = SDCardUtil.getAllSDcardFile(context).get(0).getPath()
				+ "/CameraImage/";
		File file = new File(fileName);
		List<String> data = new ArrayList<String>();
		data = getAllFiles(file, data);
		img_data.put(file.getName(), data);

		cursor.close();
		return img_data;
	}

}

package com.dream.cutepet.util;

import java.util.ArrayList;
import java.util.List;

public class MyArrayUtil {

	/**
	 * 根据指定的标识切割字符，并转换成list
	 * 
	 * @param str
	 * @param split
	 * @return
	 */
	public static List<String> changeStringToList(String str, String split) {
		List<String> data = new ArrayList<String>();
		if (str.indexOf(split) != -1) {// 判断是否只有一条数据
			String[] temp = str.split(split);
			for (String t : temp) {
				data.add(t);
			}
		} else {
			data.add(str);
		}
		return data;
	}
}

package com.dream.cutepet.model;

import java.util.ArrayList;
import java.util.List;

import com.dream.cutepet.R;

public class FusionModel {
	private String logo;
	private String time;
	private String content;
	private String[] picture;

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getPicture() {
		return picture;
	}

	public void setPicture(String[] picture) {
		this.picture = picture;
	}

	/**
	 * 获取测试数据
	 * 
	 * @return
	 */
	public static List<FusionModel> getData() {
		List<FusionModel> data = new ArrayList<FusionModel>();
		FusionModel model;

		model = new FusionModel();
		model.setLogo(R.drawable.fusion_logo_1 + "");
		model.setTime("2012.06.10");
		model.setContent("我是测试数据-------------------->1号数据");
		model.setPicture(new String[] { R.drawable.fusion_01_01 + "" });
		data.add(model);

		model = new FusionModel();
		model.setLogo(R.drawable.fusion_logo_2 + "");
		model.setTime("2012.08.10");
		model.setContent("我是测试数据-------------------->2号数据");
		model.setPicture(new String[] { R.drawable.fusion_02_01 + "",
				R.drawable.fusion_02_02 + "" });
		data.add(model);

		model = new FusionModel();
		model.setLogo(R.drawable.fusion_logo_3 + "");
		model.setTime("2013.05.10");
		model.setContent("我是测试数据-------------------->3号数据");
		model.setPicture(new String[] { R.drawable.fusion_03_01 + "",
				R.drawable.fusion_03_02 + "", R.drawable.fusion_03_03 + "" });
		data.add(model);

		model = new FusionModel();
		model.setLogo(R.drawable.fusion_logo_1 + "");
		model.setTime("2013.05.10");
		model.setContent("我是测试数据-------------------->4号数据");
		model.setPicture(new String[] { R.drawable.fusion_03_01 + "",
				R.drawable.fusion_03_01 + "", R.drawable.fusion_03_01 + "",
				R.drawable.fusion_03_01 + "" });
		data.add(model);

		model = new FusionModel();
		model.setLogo(R.drawable.fusion_logo_2 + "");
		model.setTime("2013.05.10");
		model.setContent("我是测试数据-------------------->5号数据");
		model.setPicture(new String[] { R.drawable.fusion_03_01 + "",
				R.drawable.fusion_03_01 + "", R.drawable.fusion_03_01 + "",
				R.drawable.fusion_03_01 + "", R.drawable.fusion_03_01 + "" });
		data.add(model);

		model = new FusionModel();
		model.setLogo(R.drawable.fusion_logo_2 + "");
		model.setTime("2013.05.10");
		model.setContent("我是测试数据-------------------->6号数据");
		model.setPicture(new String[] { R.drawable.fusion_03_01 + "",
				R.drawable.fusion_03_01 + "", R.drawable.fusion_03_01 + "",
				R.drawable.fusion_03_01 + "", R.drawable.fusion_03_01 + "",
				R.drawable.fusion_03_01 + "" });
		data.add(model);

		model = new FusionModel();
		model.setLogo(R.drawable.fusion_logo_3 + "");
		model.setTime("2013.05.10");
		model.setContent("我是测试数据-------------------->7号数据");
		model.setPicture(new String[] { R.drawable.fusion_03_01 + "",
				R.drawable.fusion_03_01 + "", R.drawable.fusion_03_01 + "",
				R.drawable.fusion_03_01 + "", R.drawable.fusion_03_01 + "",
				R.drawable.fusion_03_01 + "", R.drawable.fusion_03_01 + "" });
		data.add(model);

		return data;
	}
}

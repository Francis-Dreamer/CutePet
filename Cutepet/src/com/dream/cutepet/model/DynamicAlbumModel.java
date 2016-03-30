package com.dream.cutepet.model;

import java.util.ArrayList;
import java.util.List;

import com.dream.cutepet.R;

/**
 * 动态相册的数据model
 * 
 * @author 浅念丶往事如梦
 * 
 */
public class DynamicAlbumModel {
	private String logo;
	private String title;
	private PhotoAlbumModel album;

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PhotoAlbumModel getAlbum() {
		return album;
	}

	public void setAlbum(PhotoAlbumModel album) {
		this.album = album;
	}

	/**
	 * 获取测试数据
	 * 
	 * @return
	 */
	public static List<DynamicAlbumModel> getData() {
		List<DynamicAlbumModel> data = new ArrayList<DynamicAlbumModel>();
		DynamicAlbumModel model;

		model = new DynamicAlbumModel();
		model.setLogo(R.drawable.dynamic_album_01 + "");
		model.setTitle("相册->1");
		model.album = PhotoAlbumModel.getData();
		data.add(model);

		model = new DynamicAlbumModel();
		model.setLogo(R.drawable.dynamic_album_02 + "");
		model.setTitle("相册->2");
		model.album = PhotoAlbumModel.getData();
		data.add(model);

		model = new DynamicAlbumModel();
		model.setLogo(R.drawable.dynamic_album_03 + "");
		model.setTitle("相册->3");
		model.album = PhotoAlbumModel.getData();
		data.add(model);
		return data;
	}
}

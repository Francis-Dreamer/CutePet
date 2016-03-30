package com.dream.cutepet.model;

import java.util.ArrayList;
import java.util.List;

import com.dream.cutepet.R;

/**
 * 相册的数据对象
 * 
 * @author Administrator
 * 
 */
public class PhotoAlbumModel {
	private String title;
	private String logo;
	private List<PhotoModel> photo = new ArrayList<PhotoModel>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<PhotoModel> getPhoto() {
		return photo;
	}

	public void setPhoto(List<PhotoModel> photo) {
		this.photo = photo;
	}

	/**
	 * 获取测试数据
	 * 
	 * @return
	 */
	public static PhotoAlbumModel getData() {
		PhotoAlbumModel data = new PhotoAlbumModel();
		data.title = "测试数据标题";
		data.logo = R.drawable.photo_album_logo + "";

		PhotoModel model = new PhotoModel();
		model.setTime("2016年1月23日");
		model.setPath(new String[] { R.drawable.photo_album_pic1 + "",
				R.drawable.photo_album_pic2 + "",
				R.drawable.photo_album_pic3 + "" });
		data.photo.add(model);

		model = new PhotoModel();
		model.setTime("2015年4月5日");
		model.setPath(new String[] { R.drawable.photo_album_pic4 + "",
				R.drawable.photo_album_pic5 + "" });
		data.photo.add(model);
		return data;
	}
	
	
	public static PhotoAlbumModel setJson(String json){
		PhotoAlbumModel data = new PhotoAlbumModel();
		
		
		
		return data;
	}
}

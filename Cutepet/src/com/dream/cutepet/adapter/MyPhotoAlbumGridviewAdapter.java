package com.dream.cutepet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MyPhotoAlbumGridviewAdapter extends BaseAdapter {
	
	String[] data;
	Context context;
	
	public MyPhotoAlbumGridviewAdapter(){
		
	}
	
	public MyPhotoAlbumGridviewAdapter(Context context,String[] data){
		this.data = data;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(int position) {
		return data[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(context);
		imageView.setImageResource(Integer.parseInt(data[position]));
		imageView.setAdjustViewBounds(true);
		imageView.setScaleType(ScaleType.FIT_XY);
		return imageView;
	}

}

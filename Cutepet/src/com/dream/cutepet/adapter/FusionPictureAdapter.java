package com.dream.cutepet.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class FusionPictureAdapter extends BaseAdapter {

	String[] data;
	Context context;

	public FusionPictureAdapter() {

	}

	public FusionPictureAdapter(Context context, String[] data) {
		this.context = context;
		this.data = data;
		Log.i("FusionPictureAdapter", "size = "+data.length);
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
		Log.i("getView", getItem(position)+"");
		ImageView imageView = new ImageView(context);
		imageView.setImageResource(Integer.parseInt(data[position].toString()));
		imageView.setAdjustViewBounds(true);
		imageView.setScaleType(ScaleType.FIT_XY);
		return imageView;
	}

}

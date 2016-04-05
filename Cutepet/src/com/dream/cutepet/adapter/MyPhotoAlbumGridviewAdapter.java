package com.dream.cutepet.adapter;

import java.util.List;

import com.dream.cutepet.util.AsyncImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MyPhotoAlbumGridviewAdapter extends BaseAdapter {
	List<String> data;
	Context context;
	private String url_Top = "http://192.168.11.238";
	private AsyncImageLoader imageLoader;

	public MyPhotoAlbumGridviewAdapter() {

	}

	public MyPhotoAlbumGridviewAdapter(Context context, List<String> data) {
		this.data = data;
		this.context = context;
		imageLoader = new AsyncImageLoader(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(context);
		String img = data.get(position);
		if (!TextUtils.isEmpty(img) && !img.equals("null")) {
			String url_img = url_Top + img;
			imageView.setTag(url_img);
			Log.i("getView", "url_img=" + url_img);
			Bitmap bp = imageLoader.loadImage(imageView, url_img);
			Log.i("getView", "Bitmap=" + bp);
			if (bp != null) {
				imageView.setImageBitmap(bp);
			}
		}
		imageView.setAdjustViewBounds(true);
		imageView.setScaleType(ScaleType.FIT_XY);
		return imageView;
	}
}

package com.dream.cutepet.adapter;

import java.util.List;

import com.dream.cutepet.R;
import com.dream.cutepet.cache.AsyncImageLoader;
import com.dream.cutepet.cache.ImageCacheManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyPhotoAlbumGridviewAdapter extends BaseAdapter {
	List<String> data;
	Context context;
	private String url_Top = "http://192.168.1.106";
	private AsyncImageLoader imageLoader;

	public MyPhotoAlbumGridviewAdapter() {

	}

	public MyPhotoAlbumGridviewAdapter(Context context, List<String> data) {
		this.data = data;
		this.context = context;
		ImageCacheManager cacheMgr = new ImageCacheManager(context);
		imageLoader = new AsyncImageLoader(context, cacheMgr.getMemoryCache(),
				cacheMgr.getPlacardFileCache());
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
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(context);
		} else {
			imageView = (ImageView) convertView;
		}
		imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		
		String mImageUrl = url_Top + data.get(position);
		imageView.setTag(mImageUrl);
		Log.e("getView", "i=" + position + "......" + "mImageUrl = "
				+ mImageUrl);
		
		Bitmap bmp = imageLoader.loadBitmap(imageView, mImageUrl, true);
		if (bmp == null) {
			imageView.setImageResource(R.drawable.friends_sends_pictures_no);
		} else {
			imageView.setImageBitmap(bmp);
		}
		
		return imageView;
	}
}

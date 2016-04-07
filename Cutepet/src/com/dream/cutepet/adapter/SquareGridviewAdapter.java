package com.dream.cutepet.adapter;

import java.util.List;

import com.dream.cutepet.R;
import com.dream.cutepet.cache.AsyncImageLoader;
import com.dream.cutepet.cache.ImageCacheManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class SquareGridviewAdapter extends BaseAdapter {
	List<String> data;
	Context context;
	AsyncImageLoader imageLoader;

	public SquareGridviewAdapter() {

	}

	public SquareGridviewAdapter(Context context, List<String> data) {
		this.data = data;
		this.context = context;
		ImageCacheManager cacheManager = new ImageCacheManager(context);
		imageLoader = new AsyncImageLoader(context,
				cacheManager.getMemoryCache(),
				cacheManager.getPlacardFileCache());
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
		String path = "http://192.168.11.238" + getItem(position);
		imageView.setTag(path);
		Bitmap bitmap = imageLoader.loadBitmap(imageView, path, true);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			imageView.setImageResource(R.drawable.friends_sends_pictures_no);
		}
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		imageView.setLayoutParams(params);
		imageView.setScaleType(ScaleType.FIT_CENTER);
		return imageView;
	}
}

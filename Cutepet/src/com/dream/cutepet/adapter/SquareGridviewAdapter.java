package com.dream.cutepet.adapter;

import java.util.List;

import com.dream.cutepet.R;
import com.dream.cutepet.cache.AsyncImageLoader;
import com.dream.cutepet.cache.ImageCacheManager;
import com.dream.cutepet.view.MyAlbumImageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SquareGridviewAdapter extends BaseAdapter {
	List<String> data;
	Context context;
	AsyncImageLoader imageLoader;
	LayoutInflater inflater;
	private String url_top = "http://192.168.11.238";

	public SquareGridviewAdapter() {

	}

	public SquareGridviewAdapter(Context context, List<String> data) {
		this.data = data;
		this.context = context;
		inflater = LayoutInflater.from(context);
		ImageCacheManager cacheManager = new ImageCacheManager(context);
		imageLoader = new AsyncImageLoader(context,
				cacheManager.getMemoryCache(),
				cacheManager.getPlacardFileCache());
	}

	@Override
	public int getCount() {
		if (data != null) {
			return data.size();
		} else {
			return 0;
		}
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
		ViewHolder holder;
		String img = (String) getItem(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.set_peticon_item, null);
			holder.mImageView = (MyAlbumImageView) convertView
					.findViewById(R.id.iv_setPetIcon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (!TextUtils.isEmpty(img) && !img.equals("null")) {
			String pic_url = url_top + img;
			holder.mImageView.setTag(pic_url);
			Bitmap bitmap = imageLoader.loadBitmap(holder.mImageView, pic_url,
					true);
			if (bitmap != null) {
				holder.mImageView.setImageBitmap(bitmap);
			} else {
				holder.mImageView
						.setImageResource(R.drawable.friends_sends_pictures_no);
			}
		} else {
			holder.mImageView
					.setImageResource(R.drawable.friends_sends_pictures_no);
		}

		return convertView;
	}

	public class ViewHolder {
		MyAlbumImageView mImageView;
	}
}

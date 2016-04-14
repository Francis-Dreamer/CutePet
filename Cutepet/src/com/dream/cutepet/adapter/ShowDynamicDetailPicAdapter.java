package com.dream.cutepet.adapter;

import java.io.File;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dream.cutepet.R;
import com.dream.cutepet.cache.AsyncImageLoader;
import com.dream.cutepet.cache.ImageCacheManager;
import com.dream.cutepet.util.BitmapUtil;
import com.dream.cutepet.util.NativeImageLoader;
import com.dream.cutepet.util.NativeImageLoader.NativeImageCallBack;
import com.dream.cutepet.util.SDCardUtil;
import com.dream.cutepet.view.PhotoImageView;

public class ShowDynamicDetailPicAdapter extends BaseAdapter {
	List<String> data;
	Context context;
	AsyncImageLoader imageLoader;
	LayoutInflater inflater;
	private String url_top = "http://211.149.198.8:9805";

	public ShowDynamicDetailPicAdapter() {

	}

	public ShowDynamicDetailPicAdapter(Context context, List<String> data) {
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
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		String img = (String) getItem(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.set_peticon_item, null);
			holder.mImageView = (PhotoImageView) convertView
					.findViewById(R.id.iv_setPetIcon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		File f = SDCardUtil.getImagePath(url_top + img);
		Log.e("File", "path = "+f.getPath());
//		Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path,
//				new NativeImageCallBack() {
//					@Override
//					public void onImageLoader(Bitmap bitmap, String path) {
//
//					}
//				});

		return convertView;
	}

	public class ViewHolder {
		PhotoImageView mImageView;
	}

}

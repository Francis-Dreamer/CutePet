package com.dream.cutepet.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.dream.cutepet.R;
import com.dream.cutepet.util.NativeImageLoader;
import com.dream.cutepet.util.NativeImageLoader.NativeImageCallBack;

public class WriteTalkGridAdapter extends BaseAdapter {
	List<String> data;
	Context context;
	LayoutInflater inflater;
	private GridView mGridView;

	public WriteTalkGridAdapter() {

	}

	public WriteTalkGridAdapter(List<String> data, Context context,
			GridView mGridView) {
		this.data = data;
		this.context = context;
		this.mGridView = mGridView;
		inflater = LayoutInflater.from(context);
	}

	public void setData(List<String> data) {
		this.data = data;
		this.notifyDataSetChanged();
	}

	public int getCount() {
		if (data != null) {
			return data.size();
		} else {
			return 0;
		}
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater
					.inflate(R.layout.every_writetalk_image, null);
			holder.image = (ImageView) convertView.findViewById(R.id.oneimage);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String path = (String) getItem(position);
		holder.image.setTag(path);

		NativeImageLoader.getInstance().loadNativeImage(path,
				new NativeImageCallBack() {
					@Override
					public void onImageLoader(Bitmap bitmap, String path) {
						ImageView mImageView = (ImageView) mGridView
								.findViewWithTag(path);
						if (bitmap != null && mImageView != null) {
							mImageView.setImageBitmap(bitmap);
						}
					}
				});
		return convertView;
	}

	class ViewHolder {
		ImageView image;
	}
}

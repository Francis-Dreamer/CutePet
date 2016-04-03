package com.dream.cutepet.adapter;

import java.util.List;

import com.dream.cutepet.util.SDCardAllPhotoUtil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class SetPetIconAdapter extends BaseAdapter {
	private List<String> data;
	private Context context;

	public SetPetIconAdapter() {

	}

	public SetPetIconAdapter(Context context, List<String> data) {
		this.data = data;
		this.context = context;
	}

	public void setData(List<String> data) {
		this.data = data;
		this.notifyDataSetChanged();
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
		if (position == 0) {
			imageView.setImageResource(Integer.parseInt(data.get(0)));
		} else {
			imageView.setImageBitmap(SDCardAllPhotoUtil.getDiskBitmap(data
					.get(position)));
		}
		imageView.setScaleType(ScaleType.FIT_XY);
		return imageView;
	}
}

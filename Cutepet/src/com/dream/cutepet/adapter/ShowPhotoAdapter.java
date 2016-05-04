package com.dream.cutepet.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.dream.cutepet.R;
import com.dream.cutepet.util.NativeImageLoader;
import com.dream.cutepet.util.NativeImageLoader.NativeImageCallBack;
import com.dream.cutepet.view.MyAlbumImageView;
import com.dream.cutepet.view.MyAlbumImageView.OnMeasureListener;

public class ShowPhotoAdapter extends BaseAdapter {
	private Point mPoint = new Point(0, 0);// 用来封装ImageView的宽和高的对象
	/**
	 * 用来存储图片的选中情况
	 */
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, Boolean> mSelectMap = new HashMap<Integer, Boolean>();
	private GridView mGridView;
	private List<String> list;
	protected LayoutInflater mInflater;
	private OnItemCheckChangeListener onItemCheckChangeListener;
	private Context context;

	public ShowPhotoAdapter() {

	}

	public ShowPhotoAdapter(Context context, List<String> list,
			GridView mGridView,
			OnItemCheckChangeListener onItemCheckChangeListener) {
		this.list = list;
		this.mGridView = mGridView;
		this.context = context;
		this.onItemCheckChangeListener = onItemCheckChangeListener;
		mInflater = LayoutInflater.from(context);
	}

	public interface OnItemCheckChangeListener {
		public void onItemCheckChangeListener(View v);
	}

	public void setOnItemCheckChangeListener(
			OnItemCheckChangeListener onItemCheckChangeListener) {
		this.onItemCheckChangeListener = onItemCheckChangeListener;
	}

	/**
	 * 获取选中的Item的position
	 * 
	 * @return
	 */
	public List<Integer> getSelectItems() {
		List<Integer> list = new ArrayList<Integer>();
		for (Iterator<Map.Entry<Integer, Boolean>> it = mSelectMap.entrySet()
				.iterator(); it.hasNext();) {
			Map.Entry<Integer, Boolean> entry = it.next();
			if (entry.getValue()) {
				list.add(entry.getKey());
			}
		}
		return list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		String path = list.get(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.activity_photo_item, null);
			viewHolder = new ViewHolder();
			viewHolder.mImageView = (MyAlbumImageView) convertView
					.findViewById(R.id.child_image);
			viewHolder.mCheckBox = (CheckBox) convertView
					.findViewById(R.id.child_checkbox);

			// 用来监听ImageView的宽和高
			viewHolder.mImageView.setOnMeasureListener(new OnMeasureListener() {
				@Override
				public void onMeasureSize(int width, int height) {
					mPoint.set(width, height);
				}
			});

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.mImageView
					.setImageResource(R.drawable.friends_sends_pictures_no);
		}

		viewHolder.mImageView.setTag(path);
		viewHolder.mCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						mSelectMap.put(position, isChecked);
						if(getSelectItems().size() > 9){
							Toast.makeText(context, "最多只能选择9张图片",
									Toast.LENGTH_SHORT).show();
							viewHolder.mCheckBox.setChecked(false);
							mSelectMap.put(position, false);
						}
						if (onItemCheckChangeListener != null) {
							onItemCheckChangeListener
									.onItemCheckChangeListener(buttonView);
						}
					}
				});

		viewHolder.mCheckBox
				.setChecked(mSelectMap.containsKey(position) ? mSelectMap
						.get(position) : false);

		// 利用NativeImageLoader类加载本地图片
		Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path,
				mPoint, new NativeImageCallBack() {
					@Override
					public void onImageLoader(Bitmap bitmap, String path) {
						ImageView mImageView = (ImageView) mGridView
								.findViewWithTag(path);
						if (bitmap != null && mImageView != null) {
							mImageView.setImageBitmap(bitmap);
						}
					}
				});

		if (bitmap != null) {
			viewHolder.mImageView.setImageBitmap(bitmap);
		}
		return convertView;
	}

	public class ViewHolder {
		public MyAlbumImageView mImageView;
		public CheckBox mCheckBox;
	}
}

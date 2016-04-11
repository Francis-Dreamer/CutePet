package com.dream.cutepet.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.dream.cutepet.R;
import com.dream.cutepet.cache.AsyncImageLoader;
import com.dream.cutepet.cache.ImageCacheManager;
import com.dream.cutepet.model.FusionModel;
import com.dream.cutepet.util.BitmapUtil;
import com.dream.cutepet.util.MyListUtil;
import com.dream.cutepet.util.TimeUtil;
import com.dream.cutepet.view.MyGridView;

public class FusionAdapter extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	List<FusionModel> data;
	FusionPictureAdapter adapter;
	String url_Top = "http://211.149.198.8:9805";
	AsyncImageLoader imageLoader;
	List<String> picture;

	public FusionAdapter() {

	}

	public FusionAdapter(Context context, List<FusionModel> data) {
		this.data = data;
		this.context = context;
		inflater = LayoutInflater.from(context);
		ImageCacheManager cacheMgr = new ImageCacheManager(context);
		imageLoader = new AsyncImageLoader(context, cacheMgr.getMemoryCache(),
				cacheMgr.getPlacardFileCache());
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
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_fusion_item, null);
			holder = new ViewHolder();
			holder.iv_icon = (ImageView) convertView
					.findViewById(R.id.iv_fusion_item_icon);
			holder.tv_time = (TextView) convertView
					.findViewById(R.id.tv_fusion_item_time);
			holder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_fusion_item_content);
			holder.gridView = (MyGridView) convertView
					.findViewById(R.id.gv_fusion_item_picture);
			holder.view = convertView
					.findViewById(R.id.view_fusion_item_length);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final FusionModel temp = (FusionModel) getItem(position);

		String logoUrl = url_Top + temp.getLogo();
		holder.iv_icon.setTag(logoUrl);
		holder.iv_icon.setImageResource(R.drawable.icon_tx);
		Bitmap bt1 = imageLoader.loadBitmap(holder.iv_icon, logoUrl, true);
		if (bt1 != null) {
			holder.iv_icon.setImageBitmap(BitmapUtil.toRoundBitmap(bt1));
		} else {
			holder.iv_icon.setImageResource(R.drawable.icon_tx);
		}

		String time = temp.getTime();

		holder.tv_time.setText(TimeUtil.getTimeBy2(TimeUtil.changeTime(time)
				.getTime()));
		holder.tv_content.setText(temp.getContent());

		picture = new ArrayList<String>();
		picture = MyListUtil.changeStringToList(temp.getPicture(), ",");

		adapter = new FusionPictureAdapter(context, picture);
		if (picture.size() <= 1) {
			holder.gridView.setNumColumns(1);
		} else if (picture.size() == 2) {
			holder.gridView.setNumColumns(2);
		} else {
			holder.gridView.setNumColumns(3);
		}
		holder.gridView.setAdapter(adapter);

		int height = 0;
		if (picture.size() == 1) {
			height = 550;
		} else if (picture.size() == 2) {
			height = 258;
		} else {
			double si = picture.size();
			int le = (int) Math.ceil(si / 3.0);
			height = 181 * le;
		}
		int length = (int) Math.ceil(temp.getContent().length() / 18) + 30
				+ height + 50;
		LayoutParams layoutParams = new LayoutParams(2, length);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		holder.view.setLayoutParams(layoutParams);

		return convertView;
	}

	class ViewHolder {
		ImageView iv_icon;
		TextView tv_time, tv_content, tv_height;
		View view;
		MyGridView gridView;
	}

}

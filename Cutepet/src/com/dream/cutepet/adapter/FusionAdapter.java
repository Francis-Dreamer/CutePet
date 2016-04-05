package com.dream.cutepet.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dream.cutepet.R;
import com.dream.cutepet.model.FusionModel;
import com.dream.cutepet.util.AsyncImageLoader;
import com.dream.cutepet.util.BitmapUtil;
import com.dream.cutepet.util.MyArrayUtil;
import com.dream.cutepet.util.TimeUtil;

public class FusionAdapter extends BaseAdapter {

	Context context;
	LayoutInflater inflater;
	List<FusionModel> data;
	FusionPictureAdapter adapter;
	String url_Top = "http://192.168.11.238";
	AsyncImageLoader imageLoader;
	List<String> picture;

	public FusionAdapter() {

	}

	public FusionAdapter(Context context, List<FusionModel> data) {
		this.data = data;
		this.context = context;
		inflater = LayoutInflater.from(context);
		imageLoader=new AsyncImageLoader(context);
	}

	@Override
	public int getCount() {
		if(data!=null){
			return data.size();
		}else{
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_fusion_item, null);
			holder = new ViewHolder();
			holder.iv_icon = (ImageView) convertView
					.findViewById(R.id.iv_fusion_item_icon);
			holder.tv_time = (TextView) convertView
					.findViewById(R.id.tv_fusion_item_time);
			holder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_fusion_item_content);
			holder.rlayout = (RelativeLayout) convertView
					.findViewById(R.id.rlayout_fusion_item_picture);
			holder.llayout_2 = (LinearLayout) convertView
					.findViewById(R.id.llayout_fusion_item_picture_2);
			holder.llayout_3 = (LinearLayout) convertView
					.findViewById(R.id.llayout_fusion_item_picture_3);
			holder.llayout_pic1 = (LinearLayout) convertView
					.findViewById(R.id.llayout_fusion_item_p1);
			holder.llayout_pic2 = (LinearLayout) convertView
					.findViewById(R.id.llayout_fusion_item_p2);
			holder.llayout_pic3 = (LinearLayout) convertView
					.findViewById(R.id.llayout_fusion_item_p3);
			holder.view = convertView
					.findViewById(R.id.view_fusion_item_length);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		FusionModel temp = (FusionModel) getItem(position);
		
		String logo=temp.getLogo();
		if(!TextUtils.isEmpty(logo)&& !logo.equals("null")){
			String logoUrl=url_Top+logo;
			holder.iv_icon.setTag(logoUrl);
			holder.iv_icon.setImageResource(R.drawable.icon_tx);
			Bitmap bt1=imageLoader.loadImage(holder.iv_icon, logoUrl);
			if(bt1!=null){
				holder.iv_icon.setImageBitmap(BitmapUtil.toRoundBitmap(bt1));
			}
		}
		
		String time = temp.getTime();
		holder.tv_time.setText(TimeUtil.showTime(TimeUtil.changeTime(time)));
		holder.tv_content.setText(temp.getContent());

		picture=new ArrayList<String>();
		picture=MyArrayUtil.changeStringToList(temp.getPicture(), ",");
		
		holder.llayout_2.removeAllViews();
		holder.llayout_pic1.removeAllViews();
		holder.llayout_pic2.removeAllViews();
		holder.llayout_pic3.removeAllViews();
		if (picture.size() == 1) {
			ImageView imageView = new ImageView(context);
			if(!TextUtils.isEmpty(picture.get(0))&&!(picture.get(0).equals("null"))){
				String picUrl=url_Top+picture.get(0);
				imageView.setTag(picUrl);
				imageView.setImageResource(R.drawable.icon_tx);
				Bitmap bitmap=imageLoader.loadImage(imageView, picUrl);
				if(bitmap!=null){
					imageView.setImageBitmap(bitmap);
					holder.llayout_2.addView(imageView);
				}
			}
		} else if (picture.size() == 2) {
			for (int i = 0; i < picture.size(); i++) {
				ImageView imageView = new ImageView(context);
				if(!TextUtils.isEmpty(picture.get(0))&&!(picture.get(0).equals("null"))){
					String picUrl=url_Top+picture.get(i);
					imageView.setTag(picUrl);
					imageView.setImageResource(R.drawable.icon_tx);
					Bitmap bitmap=imageLoader.loadImage(imageView, picUrl);
					if(bitmap!=null){
						imageView.setImageBitmap(bitmap);
						imageView.setScaleType(ScaleType.FIT_XY);
						imageView.setAdjustViewBounds(true);
						LayoutParams params = new LayoutParams(
								LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
						params.setMargins(0, 0, 10, 0);
						imageView.setLayoutParams(params);
						holder.llayout_2.addView(imageView);
					}
				}
			}
		} else {
			for (int i = 0; i < picture.size(); i++) {
				ImageView imageView = new ImageView(context);
				if(!TextUtils.isEmpty(picture.get(0))&&!(picture.get(0).equals("null"))){
					String picUrl=url_Top+picture.get(i);
					imageView.setTag(picUrl);
					imageView.setImageResource(R.drawable.icon_tx);
					Bitmap bitmap=imageLoader.loadImage(imageView, picUrl);
					if(bitmap!=null){
						imageView.setImageBitmap(bitmap);
						imageView.setAdjustViewBounds(true);
						imageView.setScaleType(ScaleType.FIT_XY);
						LayoutParams params = new LayoutParams(
								LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
						params.setMargins(0, 0, 0, 10);
						imageView.setLayoutParams(params);
						if (i % 3 == 0) {
							holder.llayout_pic1.addView(imageView);
						} else if (i % 3 == 1) {
							holder.llayout_pic2.addView(imageView);
						} else {
							holder.llayout_pic3.addView(imageView);
						}
					}
				}
			}
		}
		int pic_height = holder.rlayout.getHeight();
		int height = holder.tv_content.getHeight() + pic_height + 80;
		Log.i("position = "+position, "tv_content="+holder.tv_content.getHeight());
		Log.i("position = "+position, "pic_height="+pic_height);
		Log.i("position = "+position, "height="+height);
		RelativeLayout.LayoutParams ma = new RelativeLayout.LayoutParams(1,
				height);
		ma.addRule(RelativeLayout.CENTER_HORIZONTAL);
		holder.view.setLayoutParams(ma);
		return convertView;
	}

	class ViewHolder {
		ImageView iv_icon;
		TextView tv_time, tv_content, tv_height;
		View view;
		RelativeLayout rlayout;
		LinearLayout llayout_2, llayout_3;
		LinearLayout llayout_pic1, llayout_pic2, llayout_pic3;
	}
	
	
}

package com.dream.cutepet.adapter;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.cutepet.R;
import com.dream.cutepet.model.SquareModel;
import com.dream.cutepet.util.AsyncImageLoader;
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.TimeUtil;
import com.dream.cutepet.util.HttpPost.OnSendListener;

public class SquareBaseAdapter extends BaseAdapter {
	List<SquareModel> data;
	Context context;
	LayoutInflater inflater;
	AsyncImageLoader imageLoader;
	String name = "+关注";
	SquareModel model;
	// String urlTop = "http://192.168.11.238";
	String urlTop = "http://192.168.11.238";

	public SquareBaseAdapter() {

	}

	public SquareBaseAdapter(List<SquareModel> data, Context context) {
		this.data = data;
		this.context = context;
		inflater = LayoutInflater.from(context);
		imageLoader = new AsyncImageLoader(context);
	}

	public void setData(List<SquareModel> data) {
		this.data = data;
		this.notifyDataSetChanged();
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.every_square, null);
			holder.square_portrait = (ImageView) convertView
					.findViewById(R.id.square_portrait);
			holder.square_neckname = (TextView) convertView
					.findViewById(R.id.square_nickname);
			holder.square_comment_content = (TextView) convertView
					.findViewById(R.id.square_content);
			holder.square_comment_time = (TextView) convertView
					.findViewById(R.id.square_time);
			holder.square_image = (ImageView) convertView
					.findViewById(R.id.square_image);
			holder.square_address = (TextView) convertView
					.findViewById(R.id.square_address);
			holder.square_comment_num = (TextView) convertView
					.findViewById(R.id.square_comment_num);
			holder.square_praise_num = (TextView) convertView
					.findViewById(R.id.square_praise_num);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		model = (SquareModel) getItem(position);
//		getAttention(position);
		holder.square_neckname.setText(model.getSquare_neckname());
		Date date = TimeUtil.changeTime(model.getSquare_comment_time());
		holder.square_comment_time.setText(TimeUtil.showTime(date));
		holder.square_comment_content
				.setText(model.getSquare_comment_content());
		holder.square_address.setText(model.getSquare_address());
		holder.square_praise_num.setText(model.getSquare_praise_num());
		holder.square_comment_num.setText(model.getSquare_comment_num());
		String img = model.getSquare_image();
		if (!TextUtils.isEmpty(img) && !img.equals("null")) {
			String imageUrl = urlTop + img;
			// 给imageview设置tag
			holder.square_image.setTag(imageUrl);
			// 异步加载图片
			Bitmap bitmap = imageLoader
					.loadImage(holder.square_image, imageUrl);
			if (bitmap != null) {
				holder.square_image.setImageBitmap(bitmap);
			}
		}

		String icon = model.getSquare_portrait();
		if (!TextUtils.isEmpty(icon) && !icon.equals("null")) {
			String portraitUrl = urlTop + icon;
			// 给imageview设置tag
			holder.square_portrait.setTag(portraitUrl);
			// 预设图
			holder.square_portrait.setImageResource(R.drawable.icon_tx);
			// 异步加载图片
			Bitmap bitmap = imageLoader.loadImage(holder.square_portrait,
					portraitUrl);
			if (bitmap != null) {
				holder.square_portrait.setImageBitmap(bitmap);
			}
		}
		return convertView;
	}

	/*private void getAttention(int position) {
		String url_send = "http://192.168.11.238/index.php/home/api/getIssuaTalk";
		try {
			HttpPost httpPost = HttpPost.parseUrl(url_send);
			Map<String, String> map = new HashMap<String, String>();
			map.put("issua_id", model.getSquare_id());
			httpPost.putMap(map);
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				@Override
				public void start() {
				}

				@Override
				public void end(String result) {
					try {
						JSONObject jsonObject = new JSONObject(result);
						int status = jsonObject.getInt("status");
						if (status == 1) {// 关注成功
							model.setSquare_comment("已关注");
						} else if (status == -1) {
							model.setSquare_comment("+关注");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}*/

	class ViewHolder {
		ImageView square_portrait;
		TextView square_neckname;
		TextView square_comment_content;
		TextView square_comment_time;
		ImageView square_image;
		TextView square_address;
		TextView square_comment_num;
		TextView square_praise_num;
	}
}

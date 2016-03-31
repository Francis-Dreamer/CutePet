package com.dream.cutepet;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dream.cutepet.adapter.DynamicDetailsBaseAdapter;
import com.dream.cutepet.model.DynamicDetailsModel;
import com.dream.cutepet.model.SquareModel;
import com.dream.cutepet.util.AsyncImageLoader;
import com.dream.cutepet.util.BitmapUtil;
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DynamicDetailsActivity extends Activity {
	List<SquareModel> squareData;
	DynamicDetailsModel dynamicDetailsData;
	
	DynamicDetailsBaseAdapter adapter;
	ListView listView;
	RadioGroup radioGroup_bottom;
	String str_edit;
	EditText dynamic_details_edit;
	String urlTop = "http://192.168.11.238";
	AsyncImageLoader imageLoader;
	TextView dynamic_details_nickname;
	TextView dynamic_details_time;
	TextView dynamic_details_address;
	TextView dynamic_details_content;
	TextView dynamic_details_like;
	ImageView dynamic_details_image;
	LinearLayout llayout_details_icon;
	private String username;
	private String id;
	private String uid;
	private String portraitUrl;
	private String imageUrl;
	private String theNickname;
	private String theTime;
	private String theAddress;
	private String theContent;

	List<String> data_icon = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_details);
		imageLoader = new AsyncImageLoader(this);

		// 把其他形状转化成圆形头像
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("theId");
		uid = bundle.getString("theUsername");
		portraitUrl = urlTop + bundle.getString("thePortrait");
		imageUrl = urlTop + bundle.getString("thePicture");
		theNickname = bundle.getString("theNickname");
		theTime = bundle.getString("theTime");
		theAddress = bundle.getString("theAddress");
		theContent = bundle.getString("theContent");
		username = bundle.getString("tel");
	}

	protected void onStart() {
		super.onStart();

		getData_icon();

		getData_comment();
	}

	@SuppressLint("InflateParams")
	private void initView() {
		ImageView back = (ImageView) findViewById(R.id.back);
		ImageView send = (ImageView) findViewById(R.id.send);
		dynamic_details_edit = (EditText) findViewById(R.id.dynamic_details_edit);
		listView = (ListView) findViewById(R.id.dynamic_details_listview);
		TextView title = (TextView) findViewById(R.id.title);
		title.setTextColor(Color.rgb(51, 51, 51));
		title.setText("详情");
		// 加上headerview
		LayoutInflater inflater = LayoutInflater.from(this);
		View dynamic_details_headerview = inflater.inflate(
				R.layout.dynamic_details_head_view, null);

		dynamic_details_nickname = (TextView) dynamic_details_headerview
				.findViewById(R.id.dynamic_details_nickname);
		dynamic_details_time = (TextView) dynamic_details_headerview
				.findViewById(R.id.dynamic_details_time);
		dynamic_details_address = (TextView) dynamic_details_headerview
				.findViewById(R.id.dynamic_details_address);
		dynamic_details_content = (TextView) dynamic_details_headerview
				.findViewById(R.id.dynamic_details_content);
		dynamic_details_image = (ImageView) dynamic_details_headerview
				.findViewById(R.id.dynamic_details_image);
		dynamic_details_like = (TextView) dynamic_details_headerview
				.findViewById(R.id.dynamic_details_praise_num);
		llayout_details_icon = (LinearLayout) dynamic_details_headerview
				.findViewById(R.id.llayout_details_icon);
		// 头像
		ImageView dynamic_details_portrait = (ImageView) dynamic_details_headerview
				.findViewById(R.id.dynamic_details_portrait);

		dynamic_details_nickname.setText(theNickname);
		dynamic_details_time.setText(theTime);
		dynamic_details_address.setText(theAddress);
		dynamic_details_content.setText(theContent);
		dynamic_details_like.setOnClickListener(clickListener);

		if (!TextUtils.isEmpty(imageUrl)) {
			dynamic_details_image.setTag(imageUrl);
			dynamic_details_image.setImageResource(R.drawable.icon_tx);
			Bitmap bt2 = imageLoader.loadImage(dynamic_details_image, imageUrl);
			if (bt2 != null) {
				dynamic_details_image.setImageBitmap(BitmapUtil
						.toRoundBitmap(bt2));
			}
		}
		if (!TextUtils.isEmpty(portraitUrl)) {
			dynamic_details_portrait.setTag(portraitUrl);
			dynamic_details_portrait.setImageResource(R.drawable.icon_tx);
			Bitmap bt1 = imageLoader.loadImage(dynamic_details_portrait,
					portraitUrl);
			if (bt1 != null) {
				dynamic_details_portrait.setImageBitmap(BitmapUtil
						.toRoundBitmap(bt1));
			}
		}

		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, 8, 0);
		for (int i = 0; i < data_icon.size(); i++) {
			ImageView child = new ImageView(this);
			Bitmap bt1 = imageLoader
					.loadImage(child, urlTop + data_icon.get(i));
			if (bt1 != null) {
				dynamic_details_portrait.setImageBitmap(bt1);
			}
			child.setLayoutParams(params);
			llayout_details_icon.addView(child);
		}

		listView.addHeaderView(dynamic_details_headerview);
		adapter = new DynamicDetailsBaseAdapter(dynamicDetailsData.getMessage(), this);
		listView.setAdapter(adapter);
		back.setOnClickListener(clickListener);
	}

	/**
	 * 点赞功能
	 * 
	 * @param position
	 */
	private void setParise() {
		String url = "http://192.168.11.238/index.php/home/api/uploadPraise_square";
		try {
			HttpPost httpPost = HttpPost.parseUrl(url);
			Map<String, String> map = new HashMap<String, String>();
			map.put("user_id", username);
			map.put("issue_id", id);
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
						Toast.makeText(getApplicationContext(),
								jsonObject.getString("message"),
								Toast.LENGTH_SHORT).show();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取点赞头像数据
	 */
	private void getData_icon() {
		String url = "http://192.168.11.238/index.php/home/api/getPraise_square_icon";
		try {
			HttpPost httpPost = HttpPost.parseUrl(url);
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				@Override
				public void start() {

				}

				@Override
				public void end(String result) {
					try {
						JSONObject jsonObject = new JSONObject(result);
						JSONArray arr = jsonObject.getJSONArray("message");
						for (int i = 0; i < arr.length(); i++) {
							data_icon.add(arr.getString(i));
						}
						initView();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取评论数据
	 */
	private void getData_comment() {
		String url = "http://192.168.11.238/index.php/home/api/getSquareComment";
		try {
			HttpPost httpPost = HttpPost.parseUrl(url);
			httpPost.putString("issue_id", id);
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				@Override
				public void start() {

				}

				@Override
				public void end(String result) {
					Log.e("getData_comment", result);
					dynamicDetailsData = DynamicDetailsModel.setJson(result);
					Log.e("getData_comment", "end = "+dynamicDetailsData.toString());
					adapter.setData(dynamicDetailsData.getMessage());
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				back();
				break;
			case R.id.send:

				break;
			case R.id.dynamic_details_praise_num:
				setParise();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 返回
	 */
	private void back() {
		finish();
	}
}

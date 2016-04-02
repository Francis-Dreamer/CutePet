package com.dream.cutepet;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
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
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;
import com.dream.cutepet.util.TimeUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
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
	DynamicDetailsModel dynamicDetailsData = new DynamicDetailsModel();

	DynamicDetailsBaseAdapter adapter;
	ListView listView;
	RadioGroup radioGroup_bottom;
	String str_edit;
	EditText dynamic_details_edit;
	String urlTop = "http://192.168.1.106";
	AsyncImageLoader imageLoader;
	TextView dynamic_details_nickname;
	TextView dynamic_details_time;
	TextView dynamic_details_address;
	TextView dynamic_details_content;
	TextView dynamic_details_like;
	TextView tv_add_attention;
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
	private String thePraise;
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
		portraitUrl = bundle.getString("thePortrait");
		imageUrl = bundle.getString("thePicture");
		theNickname = bundle.getString("theNickname");
		theTime = bundle.getString("theTime");
		theAddress = bundle.getString("theAddress");
		theContent = bundle.getString("theContent");
		username = bundle.getString("tel");
		thePraise = bundle.getString("praise");
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
		back.setOnClickListener(clickListener);
		send.setOnClickListener(clickListener);

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
		
		tv_add_attention = (TextView) dynamic_details_address
				.findViewById(R.id.add_attention);
		tv_add_attention.setOnClickListener(clickListener);
		// 头像
		ImageView dynamic_details_portrait = (ImageView) dynamic_details_headerview
				.findViewById(R.id.dynamic_details_portrait);

		dynamic_details_nickname.setText(theNickname);
		Date date = TimeUtil.changeTime(theTime);
		dynamic_details_time.setText(TimeUtil.showTime(date));
		dynamic_details_address.setText(theAddress);
		dynamic_details_content.setText(theContent);
		dynamic_details_like.setText(thePraise + "");
		dynamic_details_like.setOnClickListener(clickListener);

		if (!TextUtils.isEmpty(imageUrl) && !imageUrl.equals("null")) {
			String url_img = urlTop + imageUrl;
			dynamic_details_image.setTag(url_img);
			Bitmap bt = imageLoader.loadImage(dynamic_details_image, url_img);
			if (bt != null) {
				dynamic_details_image.setImageBitmap(bt);
			}
		}

		if (!TextUtils.isEmpty(portraitUrl) && !portraitUrl.equals("null")) {
			String url_portrait = urlTop + portraitUrl;
			dynamic_details_portrait.setTag(url_portrait);
			Bitmap bt = imageLoader.loadImage(dynamic_details_portrait,
					url_portrait);
			if (bt != null) {
				dynamic_details_portrait.setImageBitmap(bt);
			}
		}
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, 8, 0);
		for (int i = 0; i < data_icon.size(); i++) {
			ImageView child = new ImageView(this);
			String img = data_icon.get(i);
			if (!TextUtils.isEmpty(img) && !img.equals("null")) {
				Bitmap bt1 = imageLoader.loadImage(child, urlTop + img);
				if (bt1 != null) {
					dynamic_details_portrait.setImageBitmap(bt1);
				}
				child.setLayoutParams(params);
				llayout_details_icon.addView(child);
			}
		}
		listView.addHeaderView(dynamic_details_headerview);
		adapter = new DynamicDetailsBaseAdapter(
				dynamicDetailsData.getMessage(), this);
		listView.setAdapter(adapter);
	}

	/**
	 * 点赞功能
	 * 
	 * @param position
	 */
	private void setParise() {
		String url = "http://192.168.1.106/index.php/home/api/uploadPraise_square";
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
						getData_icon();
						getData_comment();
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
		String url = "http://192.168.1.106/index.php/home/api/getPraise_square_icon";
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
					try {
						JSONObject jsonObject = new JSONObject(result);
						if (jsonObject.getInt("status") == 1) {
							JSONArray arr = jsonObject.getJSONArray("message");
							for (int i = 0; i < arr.length(); i++) {
								data_icon.add(arr.getString(i));
							}
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
		String url = "http://192.168.1.106/index.php/home/api/getSquareComment";
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
					dynamicDetailsData = DynamicDetailsModel.setJson(result);
					adapter.setData(dynamicDetailsData.getMessage());
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送评论
	 */
	private void send() {
		String content = dynamic_details_edit.getText().toString().trim();
		String url_send = "http://192.168.1.106/index.php/home/api/uploadSquareComment";
		if (!TextUtils.isEmpty(content)) {
			try {
				HttpPost httpPost = HttpPost.parseUrl(url_send);
				Map<String, String> map = new HashMap<String, String>();
				map.put("uid", username);
				map.put("issue_id", id);
				map.put("content", content);
				map.put("create_time", new Date().toString());
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
		} else {
			Toast.makeText(getApplicationContext(), "评论内容不能为空！",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * 关注
	 */
	private void attention() {
		String content = dynamic_details_edit.getText().toString().trim();
		String url_send = "http://192.168.1.106/index.php/home/api/attention";
		if (!TextUtils.isEmpty(content)) {
			try {
				HttpPost httpPost = HttpPost.parseUrl(url_send);
				Map<String, String> map = new HashMap<String, String>();
				map.put("tel", username);
				map.put("friend_username", uid);
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
		} else {
			Toast.makeText(getApplicationContext(), "评论内容不能为空！",
					Toast.LENGTH_SHORT).show();
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
				send();
				break;
			case R.id.dynamic_details_praise_num:
				setParise();
				break;
			case R.id.add_attention:
				attention();
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

package com.dream.cutepet;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.cutepet.util.BitmapUtil;
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;

public class PetReleaseActivity extends Activity {
	TextView title, menu_hide;
	ImageView back, iv_pet_logo, iv_pet_select;
	EditText et_type, et_content;
	String view_address;
	File file;
	private String username;
//	private final String url = "http://192.168.11.238/index.php/home/api/uploadPersonal";
	private final String url = "http://192.168.11.238/index.php/home/api/uploadPersonal";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet_release);

		initData();

		initview();
	}

	private void initData() {
		username = getIntent().getStringExtra("tel");
	}

	/**
	 * 加载页面
	 */
	private void initview() {
		back = (ImageView) findViewById(R.id.back);
		title = (TextView) findViewById(R.id.title);
		menu_hide = (TextView) findViewById(R.id.menu_hide);
		menu_hide.setVisibility(View.VISIBLE);
		title.setText("发布");
		menu_hide.setText("完成");

		iv_pet_logo = (ImageView) findViewById(R.id.iv_pet_logo);
		iv_pet_select = (ImageView) findViewById(R.id.iv_pet_select);

		back.setOnClickListener(clickListener);
		menu_hide.setOnClickListener(clickListener);
		iv_pet_select.setOnClickListener(clickListener);

		et_type = (EditText) findViewById(R.id.ed_pet_breed);
		et_content = (EditText) findViewById(R.id.ed_pet_jiyu);
	}

	OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				finish();
				break;
			case R.id.menu_hide:
				String type = et_type.getText().toString().trim();
				String content = et_content.getText().toString().trim();
				if (file != null && type != null && !type.equals("")
						&& content != null && !content.equals("")) {
					upload(type, content);
				}
				break;
			case R.id.iv_pet_select:
				Intent intent = new Intent();
				intent.setClass(PetReleaseActivity.this,
						SelectPhotoActivity.class);
				startActivityForResult(intent, 77777);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 上传主人寄语
	 * 
	 * @param name
	 * @param type
	 * @param content
	 */
	private void upload(String type, String content) {
		try {
			HttpPost httpPost = HttpPost.parseUrl(url);
			Map<String, String> map = new HashMap<String, String>();
			map.put("tel", username);
			map.put("time", new Date().toString());
			map.put("content", content);
			map.put("image_name", type);
			map.put("address", "重庆市四小区");
			httpPost.putMap(map);
			httpPost.putFile("file", file, file.getName(), null);
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
						if (status == 1) {
							Toast.makeText(getApplicationContext(), "上传成功！",
									Toast.LENGTH_SHORT).show();
							finish();
						}else{
							Toast.makeText(getApplicationContext(), jsonObject.getString("message"),
									Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 77777:
			Bundle bundle = data.getExtras();
			view_address = bundle.getString("view_address");
			file = new File(view_address);
			iv_pet_logo.setImageBitmap(BitmapUtil
					.getDiskBitmap(view_address));
			break;

		default:
			break;
		}
	};

}

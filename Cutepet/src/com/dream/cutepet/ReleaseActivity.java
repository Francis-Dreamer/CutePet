package com.dream.cutepet;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;
import com.dream.cutepet.util.SDCardAllPhotoUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 发布页面
 * 
 * @author Administrator
 * 
 */
public class ReleaseActivity extends Activity {

	List<String> data_img;
	TextView title, menu_hide;
	ImageView iv_petStore_select, back, iv_petStore_logo;
	EditText ed_petStore_name, ed_petStore_address, ed_petStore_type;
	String storeName, storeAddress, storeType;
	String view_address;
	File file;
	String url = "http://192.168.11.238/index.php/home/api/uploadPetStore";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_release);
		initview();
	}

	/**
	 * 初始化控件
	 */
	private void initview() {
		int image_width, image_height;
		title = (TextView) findViewById(R.id.title);
		title.setText("发布");
		menu_hide = (TextView) findViewById(R.id.menu_hide);
		menu_hide.setText("完成");
		menu_hide.setVisibility(View.VISIBLE);
		back = (ImageView) findViewById(R.id.back);

		iv_petStore_select = (ImageView) findViewById(R.id.iv_petStore_select);
		iv_petStore_logo = (ImageView) findViewById(R.id.iv_petStore_logo);

		image_width = iv_petStore_select.getWidth();
		image_height = iv_petStore_select.getHeight();

		ed_petStore_name = (EditText) findViewById(R.id.ed_petStore_name);
		ed_petStore_address = (EditText) findViewById(R.id.ed_petStore_address);
		ed_petStore_type = (EditText) findViewById(R.id.ed_petStore_type);

		Log.i("width=" + image_width, "height=" + image_height);

		iv_petStore_select.setOnClickListener(clickListener);
		back.setOnClickListener(clickListener);
		menu_hide.setOnClickListener(clickListener);
	}

	/**
	 * iv_petStore_select的点击事件
	 */
	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_petStore_select:
				select_image();
				break;
			case R.id.back:
				back();
				break;
			case R.id.menu_hide:
				Log.i("address", view_address);
				if (file != null) {
					release(file);
				} else {
					Toast.makeText(getApplication(), "请选择图片",
							Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 上传方法
	 */
	private void release(File file) {
		storeName = ed_petStore_name.getText().toString();
		storeAddress = ed_petStore_address.getText().toString();
		storeType = ed_petStore_type.getText().toString();

		Map<String, String> map = new HashMap<String, String>();
		map.put("tel", "123321");
		map.put("title", storeName);
		map.put("address", storeAddress);
		map.put("type", storeType);
		try {
			HttpPost httpPost = HttpPost.parseUrl(url); // 调用封装的方法
			httpPost.putFile("file", file, file.getName(), null);
			httpPost.putMap(map);
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				public void start() {
				}

				public void end(String result) {
					try {
						JSONObject js = new JSONObject(result);
						int status = js.getInt("status");
						if (status == 1) {
							Toast.makeText(getApplicationContext(), "发布成功！",
									Toast.LENGTH_SHORT).show();
							back();
						} else {
							Toast.makeText(getApplicationContext(), "发布失败！",
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

	/**
	 * 选择图片跳转
	 */
	private void select_image() {
		Intent intent = new Intent(ReleaseActivity.this,
				SelectPhotoActivity.class);
		startActivityForResult(intent, 66666);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case 66666:
			Log.i("ReleaseActivity", "onActivityResult");
			Bundle bundle = data.getExtras();
			view_address = bundle.getString("view_address");
			file = new File(view_address);
			iv_petStore_logo.setImageBitmap(SDCardAllPhotoUtil
					.getDiskBitmap(view_address));
			break;

		default:
			break;
		}
	}

	/**
	 * 返回
	 */
	private void back() {
		finish();
	}
}

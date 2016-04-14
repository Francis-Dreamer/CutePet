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
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.cutepet.util.BitmapUtil;
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;
import com.dream.cutepet.util.NativeImageLoader;
import com.dream.cutepet.util.NativeImageLoader.NativeImageCallBack;
import com.dream.cutepet.util.SharedPreferencesUtil;

/**
 * 主人寄语发布界面
 * 
 * @author Administrator
 * 
 */
public class PersonalReleaseActivity extends Activity {
	TextView title, menu_hide;
	ImageView back, iv_pet_logo, iv_pet_select;
	EditText et_type, et_content;
	String view_address;
	File file;
	private String username;
	private final String url = "http://211.149.198.8:9805/index.php/home/api/uploadPersonal";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet_release);

		initData();

		initview();
	}

	private void initData() {
		username = SharedPreferencesUtil.getData(getApplicationContext())
				.split(",")[1];
		view_address = getIntent().getStringExtra("path");
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

		if (!TextUtils.isEmpty(view_address)) {
			Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(
					view_address, new NativeImageCallBack() {
						@Override
						public void onImageLoader(Bitmap bitmap, String path) {
							if (bitmap != null) {
								iv_pet_logo.setImageBitmap(BitmapUtil
										.compressImage(bitmap));
							}
						}
					});
			if (bitmap != null) {
				iv_pet_logo.setImageBitmap(bitmap);
			}
		}
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
				intent.setClass(PersonalReleaseActivity.this,
						SelectPhotoActivity.class);
				intent.putExtra("flog", 1);
				startActivityForResult(intent, 0);
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
			httpPost.putFile(file.getPath(), file, file.getName(), null);
			Log.e("upload", "file=" + file.getName());
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
						} else {
							Toast.makeText(getApplicationContext(),
									jsonObject.getString("message"),
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
}

package com.dream.cutepet.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.cutepet.R;
import com.dream.cutepet.adapter.SetPetIconAdapter;
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;
import com.dream.cutepet.util.SDCardAllPhotoUtil;
import com.dream.cutepet.util.SDCardUtil;

public class SetPetIconActivity extends Activity {
	GridView gridView;
	TextView tv_cancel;
	List<String> data_img;
	List<File> SDFile;
	SetPetIconAdapter adapter;
	int checkedNum = 0;// 记录选中的条目数量
	private String username;
	private String url = "http://192.168.1.106/index.php/home/api/uploadPetIcon";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_peticon);
		username = getIntent().getStringExtra("tel");

		initData();

		initView();

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 获取所有SD卡的根路径File集合
		SDFile = SDCardUtil.getAllSDcardFile(getApplicationContext());
		// 初始化图片路径集合
		data_img = new ArrayList<String>();
		// 添加第一张默认的拍照的背景图片
		data_img.add(R.drawable.photo + "");

		for (int i = 0; i < SDFile.size(); i++) {
			data_img = SDCardAllPhotoUtil.getAllFiles(SDFile.get(i), data_img);
		}
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		tv_cancel = (TextView) findViewById(R.id.tv_setPetIcon_cancel);
		tv_cancel.setOnClickListener(listener);

		gridView = (GridView) findViewById(R.id.gv_setPetIcon_photo);
		adapter = new SetPetIconAdapter(this, data_img);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(itemClickListener);
	}

	/**
	 * 刷新
	 */
	private void dataChanged() {
		adapter = new SetPetIconAdapter(this, data_img);
		gridView.setAdapter(adapter);
	}

	/**
	 * 上传头像
	 * 
	 * @param img
	 */
	private void updateIcon(String img) {
		File file = new File(img);
		try {
			HttpPost httpPost = HttpPost.parseUrl(url);
			httpPost.putFile("file", file, file.getName(), null);
			httpPost.putString("tel", username);
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				@Override
				public void start() {
				}

				@Override
				public void end(String result) {
					Log.i("result", result);
					try {
						JSONObject jb = new JSONObject(result);
						Toast.makeText(getApplicationContext(),
								jb.getString("message"), Toast.LENGTH_SHORT)
								.show();
						if (jb.getInt("status") == 1) {
							finish();
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

	@Override
	protected void onRestart() {
		super.onRestart();
		// 照相返回界面后刷新数据
		initData();
		// listview刷新数据
		dataChanged();
	}

	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_setPetIcon_cancel:
				finish();
				break;
			default:
				break;
			}
		}
	};

	OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (arg2 == 0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1);
			} else {
				String img = data_img.get(arg2);
				updateIcon(img);
			}
		}
	};

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 照相后，保存照片到指定的路径
		if (resultCode == Activity.RESULT_OK) {
			String sdStatus = Environment.getExternalStorageState();
			String fileName = SDFile.get(0) + "/myImage/";
			if (sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
				// 设置照相后的图片保存的名字
				String name = new SimpleDateFormat("yyyyMMdd_hhmmss")
						.format(new Date()) + ".jpg";
				Bundle bundle = data.getExtras();
				// 获取相机返回的数据，并转换为Bitmap图片格式
				Bitmap bitmap = (Bitmap) bundle.get("data");

				FileOutputStream b = null;
				File file = new File(fileName);
				if (!file.exists()) {
					// 创建文件夹
					file.mkdirs();
				}
				String filePath = fileName + name;

				try {
					b = new FileOutputStream(filePath);
					// 把数据写入文件
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}

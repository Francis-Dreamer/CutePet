package com.dream.cutepet.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.cutepet.R;
import com.dream.cutepet.adapter.UpPhotoAdapter;
import com.dream.cutepet.adapter.UpPhotoAdapter.ViewHolder;
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;
import com.dream.cutepet.util.SDCardAllPhotoUtil;
import com.dream.cutepet.util.SDCardUtil;

/**
 * 上传相片
 * 
 * @author 余飞
 * 
 */
public class UploadPhotoActivity extends Activity {
	// MyGridView gridView;
	GridView gridView;
	ImageView iv_return;
	TextView tv_cancel, tv_ok;
	List<String> data_img;
	List<File> SDFile;
	UpPhotoAdapter adapter;
	int checkedNum = 0;// 记录选中的条目数量
	String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_photo);

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

		title = getIntent().getExtras().getString("name");
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		tv_cancel = (TextView) findViewById(R.id.tv_uploadPhoto_cancel);
		tv_ok = (TextView) findViewById(R.id.tv_uploadPhoto_ok);

		tv_cancel.setOnClickListener(listener);
		tv_ok.setOnClickListener(listener);

		gridView = (GridView) findViewById(R.id.gv_upload_photo);
		adapter = new UpPhotoAdapter(this, data_img);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(itemClickListener);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// 照相返回界面后刷新数据
		initData();
		// 将统计的已选择的图片的数量清0
		checkedNum = 0;
		// listview刷新数据
		dataChanged();
	}

	/**
	 * 上传
	 */
	private void IsSure() {
		Map<Integer, Boolean> map = UpPhotoAdapter.getIsSelected();
		for (Iterator<Entry<Integer, Boolean>> it = map.entrySet().iterator(); it
				.hasNext();) {
			Map.Entry<Integer, Boolean> temp = it.next();
			if (temp.getValue()) {
				String path = data_img.get(temp.getKey());
				Log.i("IsSure", path);
				File file = new File(path);
				String actionUrl = "http://192.168.11.238/index.php/home/api/uploadPhoto";
//				String actionUrl = "http://127.0.0.1/index.php/home/api/uploadPhoto";
				String newName = file.getName();
				HttpPost httpPost;
				try {
					httpPost = HttpPost.parseUrl(actionUrl);
					httpPost.putFile("file", file, newName, null);
					Map<String, String> msg = new HashMap<String, String>();
					msg.put("tel", "1234");
					msg.put("albumname", "22222");
					msg.put("time", new Date().toString());
					httpPost.putMap(msg);
					httpPost.send();
					httpPost.setOnSendListener(new OnSendListener() {
						@Override
						public void start() {

						}

						@Override
						public void end(String result) {
							Log.i("result", "result = " + result);
							
							finish();
						}
					});
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 返回
	 */
	private void returnTolast() {
		this.finish();
	}

	/**
	 * 刷新
	 */
	private void dataChanged() {
		adapter = new UpPhotoAdapter(this, data_img);
		gridView.setAdapter(adapter);
		tv_ok.setText("确定(" + checkedNum + ")");
	}

	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_uploadPhoto_cancel:
				returnTolast();
				break;
			case R.id.tv_uploadPhoto_ok:
				IsSure();
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
				ViewHolder holder = (ViewHolder) arg1.getTag();
				// 改变checkbox的选择状态
				holder.cb_check.toggle();
				// 获取checkbox的选择的集合
				Map<Integer, Boolean> map = UpPhotoAdapter.getIsSelected();
				// 添加选择状态
				map.put(arg2, holder.cb_check.isChecked());
				// 更新选择的集合
				UpPhotoAdapter.setIsSelected(map);
				if (holder.cb_check.isChecked() == true) {
					checkedNum++;
				} else {
					checkedNum--;
				}
				tv_ok.setText("确定(" + checkedNum + ")");
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

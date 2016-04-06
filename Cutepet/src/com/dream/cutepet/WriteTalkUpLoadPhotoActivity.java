package com.dream.cutepet;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dream.cutepet.adapter.WriteTalkUpLoadAdapter;
import com.dream.cutepet.adapter.WriteTalkUpLoadAdapter.ViewHolder;
import com.dream.cutepet.util.SDCardAllPhotoUtil;
import com.dream.cutepet.util.SDCardUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class WriteTalkUpLoadPhotoActivity extends Activity {

	GridView gridView;
	TextView cancel;
	TextView ok;
	List<String> data_img;
	List<String> checked_path;
	List<File> sdFile;
	Map<Integer, Boolean> map;
	WriteTalkUpLoadAdapter adapter;
	int checkNum = 0;
	String username;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_photo);

		initData();

		initView();

	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		cancel = (TextView) findViewById(R.id.tv_uploadPhoto_cancel);
		ok = (TextView) findViewById(R.id.tv_uploadPhoto_ok);

		gridView = (GridView) findViewById(R.id.gv_upload_photo);
		adapter = new WriteTalkUpLoadAdapter(this, data_img);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(itemClickListener);

		cancel.setOnClickListener(clickListener);
		ok.setOnClickListener(clickListener);
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			ViewHolder holder = (ViewHolder) view.getTag();
			// 改变checkbox的选择状态
			holder.cb_check.toggle();
			// 获取checkbox的选择的集合
			Map<Integer, Boolean> map = WriteTalkUpLoadAdapter.getIsSelected();
			// 添加选择状态
			map.put(position, holder.cb_check.isChecked());
			// 更新选择的集合
			WriteTalkUpLoadAdapter.setIsSelected(map);
			if (checkNum <= 6) {
				if (holder.cb_check.isChecked() == true) {
					checkNum++;
				} else {
					checkNum--;
				}
			} else {
				Toast.makeText(getApplicationContext(), "最多选择6张图片上传",
						Toast.LENGTH_SHORT).show();
			}

			ok.setText("确定(" + checkNum + ")");

		}
	};

	OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_uploadPhoto_cancel:
				finish();
				break;
			case R.id.tv_uploadPhoto_ok:
				isSure();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 获取所有SD卡的根路径File集合
		sdFile = SDCardUtil.getAllSDcardFile(getApplicationContext());
		// 初始化图片路径集合
		data_img = new ArrayList<String>();

		for (int i = 0; i < sdFile.size(); i++) {
			data_img = SDCardAllPhotoUtil.getAllFiles(sdFile.get(i), data_img);
		}
	}

	/**
	 * 确定选中图片
	 */
	private void isSure() {
		map = WriteTalkUpLoadAdapter.getIsSelected();
		checked_path = new ArrayList<String>();

		for (Iterator<Entry<Integer, Boolean>> it = map.entrySet().iterator(); it
				.hasNext();) {
			Map.Entry<Integer, Boolean> temp = it.next();
			if (temp.getValue()) {
				String path = data_img.get(temp.getKey());
				checked_path.add(path);
			}
		}
		Intent intent = new Intent(WriteTalkUpLoadPhotoActivity.this,
				WriteTalkActivity.class);
		intent.putStringArrayListExtra("checked_path",
				(ArrayList<String>) checked_path);
		setResult(RESULT_OK, intent);
		finish();
	}

}

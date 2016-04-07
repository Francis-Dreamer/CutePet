package com.dream.cutepet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.dream.cutepet.adapter.SelectPhotoBaseAdapter;
import com.dream.cutepet.util.SDCardUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectPhotoActivity extends Activity {

	List<File> SDFile;
	List<String> data_img;
	TextView tv_selectPhoto_cancel;
	GridView gridView;
	SelectPhotoBaseAdapter adapter;
	String address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectphoto);

		initData();
		initview();
	}

	/**
	 * 初始化数据
	 */
	private void initData(){
		SDFile=SDCardUtil.getAllSDcardFile(getApplicationContext());
		data_img=new ArrayList<String>();
		
		for (int i = 0; i < SDFile.size(); i++) {
//			data_img = SDCardAllPhotoUtil.getAllFiles(SDFile.get(i), data_img);
		}

	}

	/**
	 * 初始化界面
	 */
	
	private void initview() {

		ImageView back=(ImageView) findViewById(R.id.back);
		back.setOnClickListener(clickListener);
		TextView title=(TextView) findViewById(R.id.title);
		title.setText("照片");
		gridView = (GridView) findViewById(R.id.gv_select_photo);

		adapter = new SelectPhotoBaseAdapter(data_img, this);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(itemClickListener);

	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// 把图片地址传过去
			address = data_img.get(position);
			Intent intentRelease = new Intent(SelectPhotoActivity.this, ReleaseActivity.class);
			Intent intentPetStrategy = new Intent(SelectPhotoActivity.this, PetStrategyCommentActivity.class);
			intentRelease.putExtra("view_address", address);
			intentPetStrategy.putExtra("view_address", address);
			setResult(RESULT_OK, intentRelease);
			setResult(RESULT_OK, intentPetStrategy);
			finish();
		}
	};
	
	OnClickListener clickListener=new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				finish();
				break;
			default:
				break;
			}
		}
	};

}

package com.dream.cutepet.ui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import com.dream.cutepet.R;
import com.dream.cutepet.adapter.FusionAdapter;
import com.dream.cutepet.model.FusionModel;

/**
 * 个人中心的时光轴界面
 * 
 * @author Administrator
 * 
 */
public class FusionActivity extends Activity {
	ImageView iv_back;
	ListView listView;
	List<FusionModel> data;
	FusionAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fusion);
		
		initData();
		
		initView();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		data = FusionModel.getData();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		iv_back = (ImageView) findViewById(R.id.iv_fusion_return);
		iv_back.setOnClickListener(listener);
		
		listView = (ListView) findViewById(R.id.lv_fusion_listview);
		adapter = new FusionAdapter(this,data);
		listView.setAdapter(adapter);
	}
	
	/**
	 * 返回
	 */
	private void back() {
		finish();
	}
	
	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_fusion_return:
				back();
				break;
			default:
				break;
			}
		}
	};
}

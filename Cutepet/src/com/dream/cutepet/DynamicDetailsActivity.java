package com.dream.cutepet;

import java.util.ArrayList;
import java.util.List;

import com.dream.cutepet.adapter.DynamicDetailsBaseAdapter;
import com.dream.cutepet.model.DynamicDetailsData;
import com.dream.cutepet.util.BitmapUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class DynamicDetailsActivity extends Activity {

	List<DynamicDetailsData> data;
	DynamicDetailsBaseAdapter adapter;
	ListView listView;
	RadioGroup radioGroup_bottom;
	String str_edit;
	EditText dynamic_details_edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_details);

		init();
	
	}

	@SuppressLint("InflateParams") private void init() {
		ImageView back=(ImageView) findViewById(R.id.back);
		ImageView send=(ImageView) findViewById(R.id.send);
		dynamic_details_edit=(EditText) findViewById(R.id.dynamic_details_edit);
		listView = (ListView) findViewById(R.id.dynamic_details_listview);
		TextView title = (TextView) findViewById(R.id.title);
		title.setTextColor(Color.rgb(51, 51, 51));
		title.setText("详情");

		// 加上headerview
		LayoutInflater inflater = LayoutInflater.from(this);
		View dynamic_details_headerview = inflater.inflate(R.layout.dynamic_details_head_view, null);

		// 头像
		ImageView dynamic_details_portrait = (ImageView) dynamic_details_headerview.findViewById(R.id.dynamic_details_portrait);
	
		//把其他形状转化成圆形头像
		Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.tuceng1010);
		dynamic_details_portrait.setImageBitmap(BitmapUtil.toRoundBitmap(bitmap));
		

		listView.addHeaderView(dynamic_details_headerview);
		
		getData();

		adapter = new DynamicDetailsBaseAdapter(data, this);
		listView.setAdapter(adapter);
		back.setOnClickListener(clickListener);
		send.setOnClickListener(clickListener);
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		data = new ArrayList<DynamicDetailsData>();
		for (int i = 0; i < 4; i++) {
			DynamicDetailsData dynamicDetailsData = new DynamicDetailsData();
			dynamicDetailsData
					.setDynamic_details_portrait(R.drawable.mid2);
			dynamicDetailsData.setDynamic_details_neckname("昵称" + i);
			dynamicDetailsData.setDynamic_details_comment_content("动态详情内容" + i);
			dynamicDetailsData.setDynamic_details_comment_time("时间" + i);
			data.add(dynamicDetailsData);
		}
	}
	
	OnClickListener clickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				back();
				break;
			case R.id.send:
				inputData();
				break;
			default:
				break;
			}
		}
	};
	
	/**
	 * 返回
	 */
	private void back(){
		finish();
	}
	
	/**
	 * 输入内容
	 */
	private void inputData(){
		str_edit=dynamic_details_edit.getText().toString();
	//	data=new ArrayList<DynamicDetailsData>();
		DynamicDetailsData dynamicDetailsData=new DynamicDetailsData();
		
		dynamicDetailsData.setDynamic_details_portrait(R.drawable.mid2);
		dynamicDetailsData.setDynamic_details_neckname("评论的昵称");
		dynamicDetailsData.setDynamic_details_comment_time("it's time");
		dynamicDetailsData.setDynamic_details_comment_content(str_edit);
		data.add(dynamicDetailsData);
		adapter.setData(data);
	}
	
}

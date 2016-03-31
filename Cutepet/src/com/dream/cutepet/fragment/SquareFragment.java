﻿package com.dream.cutepet.fragment;

import java.net.MalformedURLException;
import java.util.List;
import com.dream.cutepet.DynamicDetailsActivity;
import com.dream.cutepet.R;
import com.dream.cutepet.adapter.SquareBaseAdapter;
import com.dream.cutepet.model.SquareModel;
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.ViewFlipper;

/**
 * 广场的fragment
 * 
 * @author Administrator
 * 
 */
public class SquareFragment extends Fragment {
	ViewPager viewPager;
	List<SquareModel> data;
	SquareBaseAdapter adapter=new SquareBaseAdapter();
	ListView listView;
	ImageView square_image;
	ImageView square_mid_image_1;
	ImageView square_mid_image_2;
	ImageView square_mid_image_3;
	ImageView square_mid_image_4;
	RadioGroup group, group_top;
	View view;
	ViewFlipper viewFlipper;
	ImageView square_mid_more;
	GestureDetector gestureDetector;
	
	String squareId;
	String squareUsername;
	String squarePortrait;
	String squareNickname;
	String squareTime;
	String squareAddress;
	String squareContent;
	String squarePicture;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_square_dynamic, null);
		initView();
		
		return view;
	}
	
	public void onStart() {
		super.onStart();
		getData();
	}
	

	/**
	 * 初始化控件
	 */
	@SuppressLint({ "NewApi", "InflateParams" })
	private void initView() {

		listView = (ListView) view.findViewById(R.id.square_listview);

		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View squareHeaderView = inflater.inflate(R.layout.square_head_view,
				null);
		listView.addHeaderView(squareHeaderView, null, false);
		square_mid_more = (ImageView) squareHeaderView
				.findViewById(R.id.square_mid_more);
		viewFlipper = (ViewFlipper) squareHeaderView
				.findViewById(R.id.square_mid_view_flipper);
		adapter = new SquareBaseAdapter(data, getActivity());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(itemClickListener);
		square_mid_more.setOnClickListener(clickListener);
	}
	
	/*
	 * more的点击事件
	 */
	OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.square_mid_more:
				show_next();
				break;

			default:
				break;
			}
		}
	};
	
	/**
	 * 显示下一组图片的方法
	 */
	private void show_next(){
		viewFlipper.showNext();
	}
	
	

	/*
	 * listview点击事件
	 */
	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent();
			intent.setClass(getActivity(), DynamicDetailsActivity.class);
			squareId=data.get(position-1).getSquare_id();
			squareUsername=data.get(position-1).getSquare_username();
			squarePortrait=data.get(position-1).getSquare_portrait();
			squareNickname=data.get(position-1).getSquare_neckname();
			squareTime=data.get(position-1).getSquare_comment_time();
			squareAddress=data.get(position-1).getSquare_address();
			squareContent=data.get(position-1).getSquare_comment_content();
			squarePicture=data.get(position-1).getSquare_image();
			Bundle bundle=new Bundle();
			bundle.putString("theId", squareId);
			bundle.putString("theUsername", squareUsername);
			bundle.putString("thePortrait", squarePortrait);
			bundle.putString("theNickname", squareNickname);
			bundle.putString("theTime", squareTime);
			bundle.putString("theAddress", squareAddress);
			bundle.putString("theContent", squareContent);
			bundle.putString("thePicture", squarePicture);
			intent.putExtras(bundle);
			startActivityForResult(intent, 0);
		}
	};

	/**
	 * 获取数据
	 */
	private void getData() {
		String url = "http://192.168.11.238/index.php/home/api/getTalk";
//		String url = "http://192.168.1.107/index.php/home/api/getTalk";

		try {
			HttpPost httpPost=HttpPost.parseUrl(url);
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				
				public void start() {
					
				}
				
				public void end(String result) {
					data=SquareModel.setJson(result);
					adapter.setData(data);
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
}

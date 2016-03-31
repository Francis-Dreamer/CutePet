package com.dream.cutepet.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.mobileim.YWIMKit;
import com.dream.cutepet.R;
import com.dream.cutepet.ReleaseActivity;
import com.dream.cutepet.model.PetStoreModel;
import com.dream.cutepet.util.AsyncImageLoader;
import com.dream.cutepet.util.SharedPreferencesUtil;
import com.dream.cutpet.server.LoginSampleHelper;

/**
 * 宠物商店
 * 
 * @author 余飞
 * 
 */
public class PetStoreActivity extends Activity implements OnClickListener {
	ImageView back, iv_logo, iv_send;
	TextView menu_hide, tv_name, tv_address, tv_type, title;
	int index;
	PetStoreModel data;
	String userId;
	String password;
	String username;
	String address;
	String type;
	Bitmap bitmap;
	String icon;
	String name;
	RelativeLayout layout_petStore, layout_issue;
	AsyncImageLoader asyncImageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet_store);
		asyncImageLoader = new AsyncImageLoader(this);
		index = getIntent().getIntExtra("index", -1);
		username = getIntent().getStringExtra("username");
		address = getIntent().getStringExtra("address");
		type = getIntent().getStringExtra("type");
		icon = getIntent().getStringExtra("icon");
		name=getIntent().getStringExtra("name");
		initData();

		initView();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		if (index != -1) {
			data = PetStoreModel.getData().get(index);
		}
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		title = (TextView) findViewById(R.id.title);
		title.setText("店铺详情");
		back = (ImageView) findViewById(R.id.back);
		menu_hide = (TextView) findViewById(R.id.menu_hide);
		menu_hide.setText("发布");
		menu_hide.setVisibility(View.VISIBLE);
		iv_send = (ImageView) findViewById(R.id.iv_petStore_send);
		iv_send.setVisibility(View.VISIBLE);
		back.setOnClickListener(this);
		menu_hide.setOnClickListener(this);
		iv_send.setOnClickListener(this);

		iv_logo = (ImageView) findViewById(R.id.iv_petStore_logo);
		tv_name = (TextView) findViewById(R.id.tv_petStore_name);
		tv_address = (TextView) findViewById(R.id.tv_petStore_address);
		tv_type = (TextView) findViewById(R.id.tv_petStore_type);
		if (!TextUtils.isEmpty(icon)) {
			// 异步加载图片
			bitmap = asyncImageLoader.loadImage(iv_logo, icon);
			if (bitmap != null) {
				iv_logo.setImageBitmap(bitmap);
			}
		}
		tv_name.setText(name);
		tv_address.setText(address);
		tv_type.setText(type);

	}

	/**
	 * 发布跳转
	 */
	private void issue() {
		Intent intent = new Intent(PetStoreActivity.this, ReleaseActivity.class);
		startActivity(intent);
	}

	/**
	 * 返回
	 */
	private void back() {
		finish();
	}

	/**
	 * 跳转到聊天界面
	 */
	@SuppressWarnings("deprecation")
	private void returnToChat() {
		String token = SharedPreferencesUtil.getData(this
				.getApplicationContext());
		if (token != null && !token.equals("")) {// 判断获取的token值是否为空
			YWIMKit imKit = LoginSampleHelper.getInstance().getIMKit();
			String target = username;// 消息接收者ID
			Intent intent = imKit.getChattingActivityIntent(target);
			startActivity(intent);
		} else {
			Toast.makeText(this, "请先登陆", Toast.LENGTH_SHORT).show();
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			back();
			break;
		case R.id.menu_hide:
			issue();
			break;
		case R.id.iv_petStore_send:
			returnToChat();
			break;
		default:
			break;
		}
	}
}

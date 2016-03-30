package com.dream.cutepet;

import com.dream.cutepet.util.SharedPreferencesUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PetDetailsActivity extends Activity {

	TextView title, menu_hide;
	ImageView back;
	String username;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_petdetails);

		initview();

	}

	/**
	 * 获取本地账号
	 * 
	 * @return
	 */
	private boolean getUser() {
		String tok = SharedPreferencesUtil.getData(getApplicationContext());
		if (tok != null && !tok.equals("")) {
			username = tok.split(",")[1];
			return true;
		} else {
			Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
	}

	/**
	 * 加载页面
	 */
	private void initview() {
		title = (TextView) findViewById(R.id.title);
		menu_hide = (TextView) findViewById(R.id.menu_hide);
		back = (ImageView) findViewById(R.id.back);
		menu_hide.setVisibility(View.VISIBLE);
		title.setText("宠物详情");
		menu_hide.setText("发布");

		back.setOnClickListener(clickListener);
		menu_hide.setOnClickListener(clickListener);
	}

	/**
	 * 点击事件
	 */
	OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				finish();
				break;
			case R.id.menu_hide:
				if (getUser()) {
					Intent intent = new Intent(PetDetailsActivity.this,
							PetReleaseActivity.class);
					intent.putExtra("tel", username);
					startActivity(intent);
				}
				break;
			default:
				break;
			}
		}
	};

}

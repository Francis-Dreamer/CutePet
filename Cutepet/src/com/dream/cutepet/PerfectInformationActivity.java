package com.dream.cutepet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PerfectInformationActivity extends Activity{

	ImageView back;
	TextView title,menu_hide;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfect_information);
		
		initview();
	}
	
	/**
	 * 加载界面
	 */
	private void initview(){
		back=(ImageView) findViewById(R.id.back);
		title=(TextView) findViewById(R.id.title);
		title.setText("宠物信息");
		menu_hide=(TextView) findViewById(R.id.menu_hide);
		menu_hide.setText("保存");
		menu_hide.setVisibility(View.VISIBLE);
		back.setOnClickListener(clickListener);
	}
	
	
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

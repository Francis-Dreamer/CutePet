package com.dream.cutepet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 密保设置
 * @author Administrator
 *
 */
public class SecurityActivity extends Activity {

	ImageView back;
	TextView title,menu_hide;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security);
		back = (ImageView) findViewById(R.id.back);
		title=(TextView) findViewById(R.id.title);
		title.setText("密保设置");
		menu_hide=(TextView) findViewById(R.id.menu_hide);
		menu_hide.setText("保存");
		menu_hide.setVisibility(View.VISIBLE);
		
		back.setOnClickListener(ocl);
	}

	OnClickListener ocl = new OnClickListener() {
		@Override
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

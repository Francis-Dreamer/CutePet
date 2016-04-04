package com.dream.cutepet;

import java.net.MalformedURLException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.dream.cutepet.model.PetMessageModel;
import com.dream.cutepet.util.HttpPost;

public class PerfectInformationActivity extends Activity {
	ImageView back;
	TextView title, menu_hide;
	EditText et_name, et_age, et_type, et_content;
	RadioGroup radioGroup;
	int sex;
	PetMessageModel data;
//	String url = "http://192.168.1.106/index.php/home/api/uploadPetMessage";
	String url = "http://192.168.1.107/index.php/home/api/uploadPetMessage";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfect_information);

		initview();
	}

	@Override
	protected void onStart() {
		super.onStart();

		initData();
	}

	/**
	 * 获取网络数据
	 */
	private void initData() {
		try {
			HttpPost httpPost = HttpPost.parseUrl(url);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载界面
	 */
	private void initview() {
		back = (ImageView) findViewById(R.id.back);
		title = (TextView) findViewById(R.id.title);
		title.setText("宠物信息");
		menu_hide = (TextView) findViewById(R.id.menu_hide);
		menu_hide.setText("保存");
		menu_hide.setVisibility(View.VISIBLE);
		back.setOnClickListener(clickListener);

		radioGroup = (RadioGroup) findViewById(R.id.rgroup_setPet);
		radioGroup.setOnCheckedChangeListener(checkedChangeListener);
		et_name = (EditText) findViewById(R.id.input_nickname);
		et_age = (EditText) findViewById(R.id.input_age);
		et_content = (EditText) findViewById(R.id.input_yourstory);
		et_type = (EditText) findViewById(R.id.input_breed);
	}

	OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.woman:
				sex = -1;
				break;
			case R.id.man:
				sex = 1;
				break;
			default:
				break;
			}
		}
	};

	OnClickListener clickListener = new OnClickListener() {
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

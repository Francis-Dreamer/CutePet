package com.dream.cutepet.ui;

import com.dream.cutepet.R;
import com.dream.cutepet.model.PetInformationModel;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PetInformationActivity extends Activity implements OnClickListener {

	ImageView back, iv_logo;
	TextView tv_habit, tv_name,  tv_type;
	int index;
	PetInformationModel data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet_information);

		initData();

		initView();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);

		iv_logo = (ImageView) findViewById(R.id.iv_petInformation_logo);
		tv_name = (TextView) findViewById(R.id.tv_petInformation_name);
		tv_habit = (TextView) findViewById(R.id.tv_petInformation_habit);
		tv_type = (TextView) findViewById(R.id.tv_petInformation_type);

		iv_logo.setImageResource(Integer.parseInt(data.getLogo()));
		tv_name.setText("昵称：" + data.getName());
		tv_habit.setText("习性：" + data.getHabit());
		tv_type.setText("品种：" + data.getType());
		
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		data = PetInformationModel.getData().get(1);
	}

	/**
	 * 返回
	 */
	private void back() {
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			back();
			break;
		default:
			break;
		}
	}
}

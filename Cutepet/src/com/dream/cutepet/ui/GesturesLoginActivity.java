package com.dream.cutepet.ui;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.cutepet.LoginActivity;
import com.dream.cutepet.R;
import com.dream.cutepet.util.GesturesUtil;
import com.dream.cutepet.view.MyGesturesView;

public class GesturesLoginActivity extends Activity implements OnTouchListener, OnClickListener {
	private MyGesturesView gesturesView;
	private ImageView iv_icon;
	private TextView tv_toast, tv_errorToast_1, tv_errorToast_2, tv_byPwd;
	private LinearLayout layout;
	private List<Integer> data;
	private int count = 5;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gestures_view);

		initView();
	}

	@SuppressLint("HandlerLeak")
	private Handler myhandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 110:
				gesturesView.setPoint_num();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 初始化控件
	 */
	private void initView() {
		gesturesView = (MyGesturesView) findViewById(R.id.gestures_login);
		iv_icon = (ImageView) findViewById(R.id.gesture_login_icon);
		layout = (LinearLayout) findViewById(R.id.gesture_login_error_llayout);
		tv_toast = (TextView) findViewById(R.id.gesture_login_toast);
		tv_errorToast_1 = (TextView) findViewById(R.id.gesture_login_pwdError_toast);
		tv_errorToast_2 = (TextView) findViewById(R.id.gesture_login_error_toast);
		tv_byPwd = (TextView) findViewById(R.id.gesture_login_byPwd);

		gesturesView.setClickable(true);
		gesturesView.setOnTouchListener(this);

		layout.setVisibility(View.GONE);
		tv_toast.setVisibility(View.VISIBLE);
		tv_byPwd.setOnClickListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.gestures_login:
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				setMessage();
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}
		return false;
	}

	/**
	 * 设置
	 */
	private void setMessage() {
		data = gesturesView.getPoint_num();
		String psd = GesturesUtil.getPassword(data);

		if (GesturesUtil.ExaminePassword(psd, getApplicationContext())) {
			Toast.makeText(getApplication(), "手势密码正确！", Toast.LENGTH_SHORT)
					.show();
		} else if (psd != null && !psd.equals("")) {
			count--;
			layout.setVisibility(View.VISIBLE);
			tv_toast.setVisibility(View.GONE);
			tv_errorToast_1.setText("密码错误，还可以再试" + count + "次。");
			tv_errorToast_2.setText("5次失败后，需要重新登录。");
			Toast.makeText(getApplication(), "手势密码错误！", Toast.LENGTH_SHORT)
					.show();
		}
		myhandler.sendEmptyMessage(110);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gesture_login_byPwd:
			forgetPassword();
			break;
		default:
			break;
		}
	}

	/**
	 * 忘记手势密码，登陆后清空手势密码
	 */
	private void forgetPassword() {
		Intent intent = new Intent(GesturesLoginActivity.this,LoginActivity.class);
		startActivity(intent);
		//清空手势密码
		GesturesUtil.deleteGesture(getApplicationContext());
		finish();
	}
}

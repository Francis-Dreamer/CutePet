﻿package com.dream.cutepet;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.mobileim.YWChannel;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.utility.IMPrefsTools;
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;
import com.dream.cutepet.util.SharedPreferencesUtil;
import com.dream.cutpet.server.LoginSampleHelper;

/**
 * 登陆
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends Activity {
	private LoginSampleHelper loginHelper;
	Button button_login;
	EditText edit_tel;
	EditText edit_password;
	Button button_register;
	String tel;
	String password;
	private static final String USER_ID = "userId";
	private static final String PASSWORD = "password";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}

	/**
	 * 初始化控件
	 */
	public void initView() {
		button_login = (Button) findViewById(R.id.button_login);
		edit_tel = (EditText) findViewById(R.id.edit_tel);
		edit_password = (EditText) findViewById(R.id.edit_password);
		button_register = (Button) findViewById(R.id.button_register);
		chatDemo();
		loginHelper = LoginSampleHelper.getInstance();
		button_login.setOnClickListener(ocl);
		button_register.setOnClickListener(ocl);
	}

	/**
	 * 点击事件
	 */
	OnClickListener ocl = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.button_login:
				loginAndStarActivity();
				break;
			case R.id.button_register:
				intent.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 登陆功能
	 * 
	 * @param tel
	 * @param password
	 */
	private void login(final String tel, String password) {
		String httpHost = "http://192.168.1.106/index.php/home/api/login";
		try {
			HttpPost hp_login = HttpPost.parseUrl(httpHost);
			Map<String, String> map = new HashMap<String, String>();
			map.put("tel", tel);
			map.put("password", password);
			hp_login.putMap(map);
			hp_login.send();
			hp_login.setOnSendListener(new OnSendListener() {
				@Override
				public void start() {
				}

				@Override
				public void end(String result) {
					try {
						JSONObject jo = new JSONObject(result);
						if (jo.getInt("status") == 1) {
							String token1 = jo.getString("token");
							String token = token1 + "," + tel;
							SharedPreferencesUtil.saveToken(
									getApplicationContext(), token);
							Intent intent = new Intent(LoginActivity.this,
									AllPageActivity.class);
							Toast.makeText(getApplication(),
									jo.getString("message"), Toast.LENGTH_SHORT)
									.show();
							startActivity(intent);
						} else {
							Toast.makeText(getApplication(),
									jo.getString("message"), Toast.LENGTH_SHORT)
									.show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取登录成功后保存的用户名和密码
	 */

	private void chatDemo() {
		String localId = IMPrefsTools.getStringPrefs(LoginActivity.this,
				USER_ID, "");
		if (!TextUtils.isEmpty(localId)) {
			edit_tel.setText(localId);
			String localPassword = IMPrefsTools.getStringPrefs(
					LoginActivity.this, PASSWORD, "");
			if (!TextUtils.isEmpty(localPassword)) {
				edit_password.setText(localPassword);
			}
		}

		init(edit_tel.getText().toString());
		edit_tel.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (TextUtils.isEmpty(s)) {
					edit_password.setText("");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	/**
	 * 登陆到啊里云聊天
	 */
	@SuppressWarnings("deprecation")
	private void loginAndStarActivity() {
		if (YWChannel.getInstance().getNetWorkState().isNetWorkNull()) {
			Toast.makeText(LoginActivity.this, "网络已断开，请稍后再试哦",
					Toast.LENGTH_SHORT).show();
			return;
		}
		final Editable userId = edit_tel.getText();
		final Editable password = edit_password.getText();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edit_tel.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(edit_password.getWindowToken(), 0);
		init(userId.toString());
		loginHelper.login_Sample(userId.toString(), password.toString(),
				new IWxCallback() {

					@Override
					public void onSuccess(Object... arg0) {
						// TODO Auto-generated method stub
						saveIdPasswordToLocal(userId.toString(),
								password.toString());
						login(userId.toString(), password.toString());
					}

					@Override
					public void onProgress(int arg0) {

					}

					@Override
					public void onError(int arg0, String arg1) {

						// TODO Auto-generated method stub

						Toast.makeText(LoginActivity.this, "登录失败",

								Toast.LENGTH_SHORT).show();
					}
				});
	}

	/**
	 * 保存登录的用户名密码到本地
	 * 
	 * @param userId
	 * @param password
	 */
	private void saveIdPasswordToLocal(String userId, String password) {
		IMPrefsTools.setStringPrefs(LoginActivity.this, USER_ID, userId);
		IMPrefsTools.setStringPrefs(LoginActivity.this, PASSWORD, password);
	}

	/**
	 * 初始化imkit
	 * 
	 * @param userId
	 */
	private void init(String userId) {
		LoginSampleHelper.getInstance().initIMKit(userId, "23331616");
	}
}

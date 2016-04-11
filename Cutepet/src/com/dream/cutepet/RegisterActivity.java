package com.dream.cutepet;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;

/**
 * 注册页面
 * 
 * @author Administrator
 * 
 */
public class RegisterActivity extends Activity {

	ImageView back;
	TextView text_yanzhengma, title;
	EditText edit_phone;
	EditText edit_yanzhengma;
	EditText edit_password;
	Button button_add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		back = (ImageView) findViewById(R.id.back);
		text_yanzhengma = (TextView) findViewById(R.id.text_yanzhengma);
		title = (TextView) findViewById(R.id.title);
		title.setText("注册");
		edit_phone = (EditText) findViewById(R.id.edit_phone);
		button_add = (Button) findViewById(R.id.button_add);
		edit_yanzhengma = (EditText) findViewById(R.id.edit_yanzhengma);
		edit_password = (EditText) findViewById(R.id.edit_password);

		back.setOnClickListener(ocl);
		text_yanzhengma.setOnClickListener(ocl);
		button_add.setOnClickListener(ocl);
	}

	OnClickListener ocl = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				finish();
				break;
			case R.id.text_yanzhengma:
				String tel = edit_phone.getText().toString();
				if (tel != null) {
					Log.e("xxxxxxxx", "有手机号");
					yanzhengma(tel);
				} else {
					Log.e("xxxxxxxx", "无手机号");
					Toast.makeText(getApplication(), "请输入手机号", Toast.LENGTH_LONG)
							.show();
				}
				break;
			case R.id.button_add:
				String tel1 = edit_phone.getText().toString();
				String verify = edit_yanzhengma.getText().toString();
				String password = edit_password.getText().toString();
				register(tel1, verify, password);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 获取验证码
	 * 
	 * @param tel
	 */
	private void yanzhengma(String tel) {
	//	String httpHost = "http://211.149.198.8:9805/index.php/home/api/verify";
		String httpHost = "http://211.149.198.8:9805/index.php/home/api/verify";
		try {
			HttpPost httpPost = HttpPost.parseUrl(httpHost);
			httpPost.putString("tel", tel);
			httpPost.send();
			Log.e("xxxxxxxxxxxxxxxxxx", tel);
			httpPost.setOnSendListener(new OnSendListener() {
				@Override
				public void start() {

				}

				@Override
				public void end(String result) {
					try {
						Log.e("xxxxxxxxxxxxxxxxxx", result);
						JSONObject jo = new JSONObject(result);
						Toast.makeText(getApplicationContext(),
								jo.getString("verify"), Toast.LENGTH_LONG)
								.show();
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
	 * 注册
	 * 
	 * @param tel
	 * @param verify
	 * @param password
	 */
	private void register(String tel, String verify, String password) {
	//	String httpHost_add = "http://211.149.198.8:9805/index.php/home/api/register";
		String httpHost_add = "http://211.149.198.8:9805/index.php/home/api/register";
		try {
			HttpPost httpPost = HttpPost.parseUrl(httpHost_add);
			Map<String, String> map = new HashMap<String, String>();
			map.put("tel", tel);
			map.put("password", password);
			map.put("verify", verify);
			httpPost.putMap(map);
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				@Override
				public void start() {

				}

				@Override
				public void end(String result) {
					try {
						JSONObject jo = new JSONObject(result);
						int status = jo.getInt("status");
						if (status == 1) {
							Toast.makeText(getApplicationContext(),
									jo.getString("message"), Toast.LENGTH_SHORT)
									.show();
							Intent intent = new Intent(RegisterActivity.this,
									LoginActivity.class);
							startActivity(intent);
						}
						Toast.makeText(getApplicationContext(),
								jo.getString("message"), Toast.LENGTH_SHORT)
								.show();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}

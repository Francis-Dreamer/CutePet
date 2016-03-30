/*package com.dream.cutpet.server;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.dream.cutepet.MyApplication;
import com.dream.cutepet.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class ChatActivity extends Activity {
	LoginSampleHelper loginSampleHelper;
	String userId;
	String password;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		loginSampleHelper.getInstance();
		loginAndStarActivity();
	}

	private void loginAndStarActivity() {
		// 此对象获取到后，保存为全局对象，供APP使用
		// 此对象跟用户相关，如果切换了用户，需要重新获取
		MyApplication application = (MyApplication) getApplication();
		IntentFilter intentFilter = new IntentFilter(
				application.ACTION_INTENT_TEST);
		MyBroadcast broadcast = new MyBroadcast();
		registerReceiver(broadcast, intentFilter);

		loginSampleHelper.login_Sample(userId, password, new IWxCallback() {

			@SuppressWarnings("deprecation")
			@Override
			public void onSuccess(Object... arg0) {
				final String target = "testpro1"; // 消息接收者ID
				final String appkey = "23331616"; // 消息接收者appKey
				YWIMKit mIMKit = YWAPI.getIMKitInstance();
				Intent intent = mIMKit
						.getChattingActivityIntent(target, appkey);
				startActivity(intent);
			}

			@Override
			public void onProgress(int arg0) {

			}

			@Override
			public void onError(int arg0, String arg1) {

			}
		});
	}

	class MyBroadcast extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			userId = intent.getStringExtra("userId");
			password = intent.getStringExtra("passWord");
		}

	}
}
*/
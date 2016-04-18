package com.dream.cutepet;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.login.YWLoginState;
import com.dream.cutepet.server.LoginSampleHelper;
import com.dream.cutepet.util.AccessTokenKeeper;
import com.dream.cutepet.util.Constants;
import com.dream.cutepet.util.GesturesUtil;
import com.dream.cutepet.util.SharedPreferencesUtil;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * 设置
 * 
 * @author Administrator
 * 
 */
public class SetActivity extends Activity {

	ImageView back;
	LinearLayout linearlayout_share;
	TextView title;

	RadioButton button_weixin;
	RadioButton button_qq;
	RadioButton button_weibo;
	RadioButton button_qqkongjian;
	RadioButton button_pnegyouquan;
	Button button_cancel;

	PopupWindow popupWindow;

	Builder builder;
	AlertDialog alertDialog;
	LinearLayout linear_out;
	ToggleButton toggleButton;
	/** 微博微博分享接口实例 */
	private IWeiboShareAPI mWeiboShareAPI = null;
	WindowManager.LayoutParams lp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		// 创建微博分享接口实例
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.APP_KEY);

		// 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
		// 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
		// NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
		mWeiboShareAPI.registerApp();
		back = (ImageView) findViewById(R.id.back);
		linearlayout_share = (LinearLayout) findViewById(R.id.linearlayout_share);
		linear_out = (LinearLayout) findViewById(R.id.linear_out);
		title = (TextView) findViewById(R.id.title);
		title.setText("设置");
		back.setOnClickListener(ocl);
		linearlayout_share.setOnClickListener(ocl);
		linear_out.setOnClickListener(ocl);
		lp = getWindow().getAttributes();
		toggleButton = (ToggleButton) findViewById(R.id.ToggleButton);
		toggleButton.setOnClickListener(ocl);
		if (!GesturesUtil.hasPassword(getApplicationContext())) {
			toggleButton.setChecked(false);
		}
		toggleButton.setChecked(GesturesUtil
				.IsGestures(getApplicationContext()));

		builder = new AlertDialog.Builder(this);
		alertDialog = builder.create();
	}

	OnClickListener ocl = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				finish();
				break;
			case R.id.linearlayout_share:
				// 设置背景颜色变暗
				lp.alpha = 0.3f;
				getWindow().setAttributes(lp);
				popWindow();
				break;
			case R.id.button_cancel:
				// 销毁弹出框
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
				popupWindow.dismiss();
				break;
			case R.id.linear_out:
				creatAlertDialog();
				break;
			case R.id.ToggleButton:
				setGestures();
				break;
			case R.id.button_weibo:
				// 微博分享
				shateSinWeiBo();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
				popupWindow.dismiss();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 设置手势密码
	 */
	private void setGestures() {
		if (GesturesUtil.hasPassword(getApplicationContext())) {
			// 如果设置了密码，才可以开启锁屏
			GesturesUtil.setGesturesUser(toggleButton.isChecked(),
					getApplicationContext());
			if (toggleButton.isChecked()) {
				Toast.makeText(getApplicationContext(), "开启锁屏成功！",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "关闭锁屏成功！",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "未设置锁屏密码！",
					Toast.LENGTH_SHORT).show();
			toggleButton.setChecked(false);
		}
	}

	/**
	 * 微博分享
	 */
	protected void shateSinWeiBo() {
		// 1. 初始化微博的分享消息
		WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
		weiboMessage.textObject = getTextObj();
		// 2. 初始化从第三方到微博的消息请求
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		// 用transaction唯一标识一个请求
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = weiboMessage;
		AuthInfo authInfo = new AuthInfo(this, Constants.APP_KEY,
				Constants.REDIRECT_URL, Constants.SCOPE);
		Oauth2AccessToken accessToken = AccessTokenKeeper
				.readAccessToken(getApplicationContext());
		String token = "";
		if (accessToken != null) {
			token = accessToken.getToken();
		}
		mWeiboShareAPI.sendRequest(this, request, authInfo, token,
				new WeiboAuthListener() {

					@Override
					public void onWeiboException(WeiboException arg0) {
					}

					@Override
					public void onComplete(Bundle bundle) {
						Oauth2AccessToken newToken = Oauth2AccessToken
								.parseAccessToken(bundle);
						AccessTokenKeeper.writeAccessToken(
								getApplicationContext(), newToken);
					}

					@Override
					public void onCancel() {
					}
				});
	}

	/**
	 * 创建文本消息对象。
	 * 
	 * @return 文本消息对象。
	 */
	private TextObject getTextObj() {
		TextObject textObject = new TextObject();
		textObject.text = getSharedText();
		return textObject;
	}

	/**
	 * 获取分享的文本模板。
	 * 
	 * @return 分享的文本模板
	 */
	private String getSharedText() {
		int formatId = R.string.share_app;
		String format = getString(formatId);
		String text = format;
		return text;
	}

	/**
	 * 底部弹出框popupWindow
	 */
	@SuppressLint("InflateParams")
	public void popWindow() {
		View view = getLayoutInflater().inflate(R.layout.activity_popupwindow,
				null);

		// 获取屏幕宽度
		WindowManager wm = this.getWindowManager();
		@SuppressWarnings("deprecation")
		int width = wm.getDefaultDisplay().getWidth();
		// int height = wm.getDefaultDisplay().getHeight();

		popupWindow = new PopupWindow(view, width, 400, true);

		button_weixin = (RadioButton) view.findViewById(R.id.button_weixin);
		button_qq = (RadioButton) view.findViewById(R.id.button_qq);
		button_weibo = (RadioButton) view.findViewById(R.id.button_weibo);
		button_qqkongjian = (RadioButton) view
				.findViewById(R.id.button_qqkongjian);
		button_pnegyouquan = (RadioButton) view
				.findViewById(R.id.button_pnegyouquan);
		button_cancel = (Button) view.findViewById(R.id.button_cancel);

		button_weixin.setOnClickListener(ocl);
		button_qq.setOnClickListener(ocl);
		button_weibo.setOnClickListener(ocl);
		button_qqkongjian.setOnClickListener(ocl);
		button_pnegyouquan.setOnClickListener(ocl);
		button_cancel.setOnClickListener(ocl);
		// 显示位置
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
		view.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < 400) {
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
						popupWindow.dismiss();
					}
				}
				return true;
			}
		});
	}

	@SuppressWarnings("deprecation")
	public void creatAlertDialog() {
		alertDialog.setTitle("退出登录");
		alertDialog.setMessage("你确定要退出登录吗？");
		alertDialog.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		alertDialog.setButton("Confirm",
				new android.content.DialogInterface.OnClickListener() {

					@SuppressWarnings("static-access")
					@Override
					public void onClick(final DialogInterface dialog,
							final int which) {
						Toast.makeText(SetActivity.this, "I'am sure",
								Toast.LENGTH_SHORT).show();
						SharedPreferencesUtil exit = new SharedPreferencesUtil();
						exit.deleteData(getApplicationContext());
						logout();
						finish();
					}
				});
		alertDialog.setButton2("Cancel",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(final DialogInterface dialog,
							final int which) {
						Toast.makeText(SetActivity.this, "it is a joke",
								Toast.LENGTH_SHORT).show();

					}
				});

		alertDialog.show();
	}

	// 退出阿里云聊天
	public void logout() {
		// openIM SDK提供的登录服务
		YWIMKit mIMKit = LoginSampleHelper.getInstance().getIMKit();
		if (mIMKit == null) {
			return;
		}
		IYWLoginService mLoginService = mIMKit.getLoginService();
		mLoginService.logout(new IWxCallback() {
			// 此时logout已关闭所有基于IMBaseActivity的OpenIM相关Actiivity，s
			@Override
			public void onSuccess(Object... arg0) {
				LoginSampleHelper.getInstance().setAutoLoginState(
						YWLoginState.idle);
			}

			@Override
			public void onProgress(int arg0) {

			}

			@Override
			public void onError(int arg0, String arg1) {
			}
		});
	}
}

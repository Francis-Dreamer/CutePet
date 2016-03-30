package com.dream.cutepet;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.login.YWLoginState;
import com.dream.cutepet.util.SharedPreferencesUtil;
import com.dream.cutpet.server.LoginSampleHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		back = (ImageView) findViewById(R.id.back);
		linearlayout_share = (LinearLayout) findViewById(R.id.linearlayout_share);
		linear_out = (LinearLayout) findViewById(R.id.linear_out);
		title=(TextView) findViewById(R.id.title);
		title.setText("设置");
		back.setOnClickListener(ocl);
		linearlayout_share.setOnClickListener(ocl);
		linear_out.setOnClickListener(ocl);

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
				popWindow();
				break;
			case R.id.button_cancel:
				if (popupWindow != null) {
					popupWindow.dismiss();
					// 设置背景颜色变暗
					WindowManager.LayoutParams lp = getWindow().getAttributes();
					lp.alpha = 1f;
					getWindow().setAttributes(lp);
				}
				break;
			case R.id.linear_out:
				creatAlertDialog();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 底部弹出框popupWindow
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	public void popWindow() {
		// 设置背景颜色变暗
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.5f;
		getWindow().setAttributes(lp);
		View view = getLayoutInflater().inflate(R.layout.activity_popupwindow, null);

		// 获取屏幕宽度
		WindowManager wm = this.getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		// int height = wm.getDefaultDisplay().getHeight();

		popupWindow = new PopupWindow(view, width, 400, true);

		button_weixin = (RadioButton) view.findViewById(R.id.button_weixin);
		button_qq = (RadioButton) view.findViewById(R.id.button_qq);
		button_weibo = (RadioButton) view.findViewById(R.id.button_weibo);
		button_qqkongjian = (RadioButton) view.findViewById(R.id.button_qqkongjian);
		button_pnegyouquan = (RadioButton) view.findViewById(R.id.button_pnegyouquan);
		button_cancel = (Button) view.findViewById(R.id.button_cancel);

		button_weixin.setOnClickListener(ocl);
		button_qq.setOnClickListener(ocl);
		button_weibo.setOnClickListener(ocl);
		button_qqkongjian.setOnClickListener(ocl);
		button_pnegyouquan.setOnClickListener(ocl);
		button_cancel.setOnClickListener(ocl);

		// 显示位置
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
	}

	@SuppressWarnings("deprecation")
	public void creatAlertDialog() {
		alertDialog.setTitle("退出登录");
		alertDialog.setMessage("你确定要退出登录吗？");
		alertDialog.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		alertDialog.setButton("Confirm", new android.content.DialogInterface.OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				Toast.makeText(SetActivity.this, "I'am sure", Toast.LENGTH_SHORT).show();
				SharedPreferencesUtil exit = new SharedPreferencesUtil();
				exit.deleteData(getApplicationContext());
				logout();
				finish();
			}
		});
		alertDialog.setButton2("Cancel", new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				Toast.makeText(SetActivity.this, "it is a joke", Toast.LENGTH_SHORT).show();

			}
		});

		alertDialog.show();
	}
	
	//退出阿里云聊天
	public void logout() {
        // openIM SDK提供的登录服务
		YWIMKit mIMKit = LoginSampleHelper.getInstance().getIMKit();
		if(mIMKit==null){
		return;
		}
        IYWLoginService mLoginService = mIMKit.getLoginService();
        mLoginService.logout(new IWxCallback() {
            //此时logout已关闭所有基于IMBaseActivity的OpenIM相关Actiivity，s
            @Override
            public void onSuccess(Object... arg0) {
                LoginSampleHelper.getInstance().setAutoLoginState(YWLoginState.idle);
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

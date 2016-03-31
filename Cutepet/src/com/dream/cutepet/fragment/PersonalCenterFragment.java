package com.dream.cutepet.fragment;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dream.cutepet.AboutActivity;
import com.dream.cutepet.AccountActivity;
import com.dream.cutepet.LoginActivity;
import com.dream.cutepet.PersonalInformationActivity;
import com.dream.cutepet.R;
import com.dream.cutepet.RegisterActivity;
import com.dream.cutepet.SetActivity;
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;
import com.dream.cutepet.util.HttpTools;
import com.dream.cutepet.util.HttpTools.OndemandListener;
import com.dream.cutepet.util.SharedPreferencesUtil;

/**
 * 个人中心的fragment
 * 
 * @author Administrator
 * 
 */
public class PersonalCenterFragment extends Fragment {

	LinearLayout linear_personalInformation;// 资料信息控件
	LinearLayout linear_account;// 账户管理控件
	LinearLayout linear_about;// 关于萌宠控件
	LinearLayout linear_set;// 设置控件
	RadioGroup radioGroup;

	TextView text_login;
	TextView text_register;
	ImageView back, menu;
	TextView title;

	RelativeLayout relative_weidenglu;
	RelativeLayout relative_yidenglu;

	HttpTools http = new HttpTools();
	String tel;
	String token;

	RadioButton personal_center;

	ImageView image_toxiang;

	//UserModel data;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_personal_center, null);

		initView(view);

		initData();

		Log.i("onCreateView", "onCreateView");
		CheckIsLogin();
		return view;
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		String url = "";
		try {
			HttpPost httpPost = HttpPost.parseUrl(url);
			Map<String, String> map = new HashMap<String, String>();
			map.put("tel", tel);
			map.put("token", token);
			httpPost.putMap(map);
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				@Override
				public void start() {
				}

				@Override
				public void end(String result) {
					// 获取后台传递过来的json值
				//	data = UserModel.changeJson(result);
					updateView();
				}
			});

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	// UI控件的更新
	private void updateView() {
		/*
		 * for(int i = 0 ; i < data.size() ; i++){
		 * 
		 * } //image_toxiang.setImageBitmap(null);
		 */
	}

	/**
	 * 初始化控件
	 * 
	 * @param view
	 */
	private void initView(View view) {
		// // 找到每个控件
		// back = (ImageView) view.findViewById(R.id.back);
		// back.setVisibility(View.GONE);
		title = (TextView) view.findViewById(R.id.title);
		title.setText("个人中心");
		linear_personalInformation = (LinearLayout) view.findViewById(R.id.linear_personalInformation);

		linear_personalInformation = (LinearLayout) view.findViewById(R.id.linear_personalInformation);
		linear_account = (LinearLayout) view.findViewById(R.id.linear_account);
		linear_about = (LinearLayout) view.findViewById(R.id.linear_about);
		linear_set = (LinearLayout) view.findViewById(R.id.linear_set);
		text_login = (TextView) view.findViewById(R.id.text_login);
		text_register = (TextView) view.findViewById(R.id.text_register);

		relative_weidenglu = (RelativeLayout) view.findViewById(R.id.relative_weidenglu);
		relative_yidenglu = (RelativeLayout) view.findViewById(R.id.relative_yidenglu);
		personal_center = (RadioButton) view.findViewById(R.id.personal_center);

		image_toxiang = (ImageView) view.findViewById(R.id.image_toxiang);

		// 设置每个控件的点击事件
		linear_personalInformation.setOnClickListener(ocl);
		linear_account.setOnClickListener(ocl);
		linear_about.setOnClickListener(ocl);
		linear_set.setOnClickListener(ocl);

		// 设置每个控件的点击事件
		linear_personalInformation.setOnClickListener(ocl);
		linear_account.setOnClickListener(ocl);
		linear_about.setOnClickListener(ocl);
		linear_set.setOnClickListener(ocl);

		// 设置每个控件的点击事件
		linear_personalInformation.setOnClickListener(ocl);
		linear_account.setOnClickListener(ocl);
		linear_about.setOnClickListener(ocl);
		linear_set.setOnClickListener(ocl);

		text_login.setOnClickListener(ocl);
		text_register.setOnClickListener(ocl);

		image_toxiang.setOnClickListener(ocl);

	}

	OnClickListener ocl = new OnClickListener() {
		private OndemandListener demandListener = new OndemandListener() {

			@Override
			public void start() {

			}

			@Override
			public void end(String result) {
				try {
					JSONObject jo = new JSONObject(result);
					Log.i("demand=====>", jo.getString("message"));
					Intent intent = new Intent();
					intent.setClass(getActivity(), PersonalInformationActivity.class);
					startActivityForResult(intent, 0);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.linear_personalInformation:
				// 点击后当前个人中心页面跳转到信息资料页面

				intent.setClass(getActivity(), PersonalInformationActivity.class);
				startActivityForResult(intent, 0);

				/*
				 * http.setOndemandListener(); http.DemandAccount(tel, token);
				 */
				// 点击后当前个人中心页面跳转到个人信息页面
				intent.setClass(getActivity(), PersonalInformationActivity.class);
				startActivityForResult(intent, 0);
				break;
			case R.id.linear_account:
				// 点击后当前页面个人中心页面跳转到账户管理页面
				intent.setClass(getActivity(), AccountActivity.class);
				startActivity(intent);
				break;
			case R.id.linear_about:
				// 点击后当前页面个人中心页面跳转到关于萌宠页面
				intent.setClass(getActivity(), AboutActivity.class);
				startActivity(intent);
				break;
			case R.id.linear_set:
				// 点击后当前页面个人中心页面跳转到设置页面
				intent.setClass(getActivity(), SetActivity.class);
				startActivity(intent);
				break;
			case R.id.text_login:
				intent.setClass(getActivity(), LoginActivity.class);
				// 点击登录后跳转到登录页面
				startActivity(intent);
				break;
			case R.id.text_register:
				intent.setClass(getActivity(), RegisterActivity.class);
				// 点击注册后跳转到注册页面
				startActivity(intent);
				break;
			case R.id.personal_center:
				// http.setOnHttpListener(mListener);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onStart() {
		super.onStart();
		CheckIsLogin();
	}

	/**
	 * 判断是否处于登录状态
	 */
	private void CheckIsLogin() {
		Log.i("CheckIsLogin", "CheckIsLogin");
		// 获取本地的sharepreference存储的token值
		String result = SharedPreferencesUtil.getData(getActivity().getApplicationContext());
		Log.i("CheckIsLogin", "token=" + token);
		if (result == null || result.equals("")) {// 判断获取的token值是否为空
			Log.i("CheckIsLogin", "当前没有处于登录状态");
			judgeUnLongin();
		} else {
			// 不为空，则显示个人信息
			String[] temp = result.split(",");
			 tel = temp[0];
			 token = temp[1];
			judgeLongin();
		}
	}

	private void unloadImage() {
		String url = "";
		try {
			HttpPost httpPost = HttpPost.parseUrl(url);
			// httpPost.putMap(map);//上传表单，即多条 数据，key代表后台获取的key，value代表传递的值
			File file = new File("图片在手机的路径地址");
			String newName = file.getName();// 上传图片的图片的名字
			httpPost.putFile("image", file, newName, null);
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				@Override
				public void start() {

				}

				@Override
				public void end(String result) {
					// 上传完毕后 触发的事件，可以直接在这里进行UI的 更新，不需要新开线程
					Log.i("result", "result = " + result);

				}
			});

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	/*
	 * private OnHttpListener mListener = new OnHttpListener() {
	 * 
	 * @Override public void start() { Log.i("OnHttpListener","start"); }
	 * 
	 * @Override public void end(String result) { Log.i("OnHttpListener","end");
	 * try { JSONObject jo = new JSONObject(result); if (jo.getString("token")
	 * != null) { judgeLongin();
	 * 
	 * } } catch (JSONException e) { e.printStackTrace(); } }
	 * 
	 * };
	 */

	/**
	 * 处于登录状态的 个人信息界面
	 */
	public void judgeLongin() {
		relative_weidenglu.setVisibility(View.GONE);
		relative_yidenglu.setVisibility(View.VISIBLE);
	}

	/**
	 * 处于未登录状态的个人信息界面
	 * 
	 */
	public void judgeUnLongin() {
		relative_weidenglu.setVisibility(View.VISIBLE);
		relative_yidenglu.setVisibility(View.GONE);
	}

}

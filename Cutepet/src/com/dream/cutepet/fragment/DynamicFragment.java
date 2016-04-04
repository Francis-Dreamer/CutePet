package com.dream.cutepet.fragment;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.cutepet.LoginActivity;
import com.dream.cutepet.PerfectInformationActivity;
import com.dream.cutepet.R;
import com.dream.cutepet.adapter.DynamicAlbumAdapter;
import com.dream.cutepet.model.DynamicAlbumModel;
import com.dream.cutepet.model.PetMessageModel;
import com.dream.cutepet.ui.FusionActivity;
import com.dream.cutepet.ui.MyPhotoAlbumActivity;
import com.dream.cutepet.ui.NewPhotoAlbumActivity;
import com.dream.cutepet.ui.SetPetIconActivity;
import com.dream.cutepet.util.AsyncImageLoader;
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;
import com.dream.cutepet.util.SharedPreferencesUtil;
import com.dream.cutepet.view.MyGridView;

/**
 * 动态
 * 
 * @author Administrator
 * 
 */
public class DynamicFragment extends Fragment {
	ImageView iv_goto, iv_head;
	MyGridView gridView;
	List<DynamicAlbumModel.AlbumData> data_album;
	PetMessageModel data_petMessage;
	DynamicAlbumAdapter adapter;
	RadioGroup group, group_top;
	RadioButton radioButton;
	RelativeLayout rlayout_set, rlayout_login, rlayout_unlogin;
	TextView tv_name, tv_number, tv_type, tv_content, tv_age;
	ImageView iv_icon, iv_sex;

	TextView tv_unlogin, tv_setMsg;

	private View view;
	private String username;
//	private String url_Top = "http://192.168.1.106";
	private String url_Top = "http://192.168.1.107";
	private AsyncImageLoader imageLoader;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_personal_dynamic, null);
		imageLoader = new AsyncImageLoader(getActivity());

		initView();

		return view;
	}

	/**
	 * 初始化接口
	 */
	private void initView() {
		iv_goto = (ImageView) view
				.findViewById(R.id.iv_personal_dynamic_gotoFusion);
		iv_goto.setOnClickListener(clickListener);

		rlayout_set = (RelativeLayout) view
				.findViewById(R.id.rlayout_dynamic_petmessage_setMessage);
		rlayout_login = (RelativeLayout) view
				.findViewById(R.id.rlayout_dynamic_petmessage_login);
		rlayout_unlogin = (RelativeLayout) view
				.findViewById(R.id.rlayout_dynamic_petmessage_unlogin);

		tv_unlogin = (TextView) view
				.findViewById(R.id.tv_personal_dynamic_name);
		tv_setMsg = (TextView) view
				.findViewById(R.id.tv_personal_dynamic_name_before_perfect_information);
		tv_unlogin.setOnClickListener(clickListener);
		tv_setMsg.setOnClickListener(clickListener);

		gridView = (MyGridView) view
				.findViewById(R.id.gv_personal_dynamic_album);
		adapter = new DynamicAlbumAdapter(getActivity(), data_album);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(itemClickListener);

		group = (RadioGroup) view.findViewById(R.id.bottom_radiogroup);
		group_top = (RadioGroup) view.findViewById(R.id.rg_petRing_radiogroup);

		tv_name = (TextView) view
				.findViewById(R.id.tv_personal_dynamic_name_after_landing);
		tv_number = (TextView) view
				.findViewById(R.id.tv_personal_dynamic_petNumber_after_landing);
		tv_type = (TextView) view.findViewById(R.id.tv_personal_dynamic_type);
		tv_age = (TextView) view.findViewById(R.id.tv_personal_dynamic_age);
		tv_content = (TextView) view
				.findViewById(R.id.tv_personal_dynamic_autograph);
		iv_icon = (ImageView) view
				.findViewById(R.id.iv_personal_dynamic_icon_after_landing);
		iv_icon.setOnClickListener(clickListener);
		iv_sex = (ImageView) view.findViewById(R.id.iv_personal_dynamic_sex);
	}

	/**
	 * 初始化个人信息界面
	 */
	private void initPetDynamic() {
		rlayout_set.setVisibility(View.GONE);
		rlayout_login.setVisibility(View.VISIBLE);

		tv_name.setText(data_petMessage.getNickname());
		tv_number.setText(data_petMessage.getPetnumber() + "");
		tv_type.setText(data_petMessage.getType());
		tv_age.setText("今年" + data_petMessage.getAge() + "岁了");
		tv_content.setText(data_petMessage.getContent());
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent();
			if (position >= data_album.size()) {
				// 跳转到新建相册
				intent.setClass(getActivity(), NewPhotoAlbumActivity.class);
				intent.putExtra("tel", username);
				startActivityForResult(intent, 0);
			} else {
				// 跳转到我的相册
				intent.setClass(getActivity(), MyPhotoAlbumActivity.class);
				intent.putExtra("tel", username);
				intent.putExtra("title", data_album.get(position)
						.getAlbumname());
				startActivityForResult(intent, 1);
			}
		}
	};

	@Override
	public void onStart() {
		super.onStart();
		if (checkIsLogin()) {
			rlayout_unlogin.setVisibility(View.GONE);
			initPetMessageData();
			initAlbumData();
		} else {
			// 未登录状态
			rlayout_unlogin.setVisibility(View.VISIBLE);
			rlayout_login.setVisibility(View.GONE);
			rlayout_set.setVisibility(View.GONE);
		}
	}

	/**
	 * 初始化宠物信息数据
	 */
	private void initPetMessageData() {
		// 获取宠物信息的数据
//		String url_petMessage = "http://192.168.1.106/index.php/home/api/getPetMessage";
		String url_petMessage = "http://192.168.1.107/index.php/home/api/getPetMessage";
		try {
			HttpPost httpPost = HttpPost.parseUrl(url_petMessage);
			httpPost.putString("tel", username);
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				public void start() {
				}

				public void end(String result) {
					Log.i("initPetMessageData", "result = " + result);
					try {
						JSONObject jsonObject = new JSONObject(result);
						int status = jsonObject.getInt("status");
						if (status == 1) {
							data_petMessage = PetMessageModel.setJson(result);
							initPetDynamic();
						} else {
							noMessage();
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
	 * 获取相册数据
	 */
	private void initAlbumData() {
	//	String url_album = "http://192.168.1.106/index.php/home/api/getAlbum";
		String url_album = "http://192.168.1.107/index.php/home/api/getAlbum";
		try {
			HttpPost httpPost = HttpPost.parseUrl(url_album);
			httpPost.putString("tel", username);
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				public void start() {
				}

				public void end(String result) {
					Log.i("initAlbumData", "result = " + result);
					DynamicAlbumModel model = DynamicAlbumModel.setJson(result);
					data_album = new ArrayList<DynamicAlbumModel.AlbumData>();
					if (model.getStatus() == 1) {
						data_album = model.getMessage();
					}
					adapter.setData(data_album);
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 没有宠物信息，则设置
	 */
	private void noMessage() {
		rlayout_set.setVisibility(View.VISIBLE);
		rlayout_login.setVisibility(View.GONE);
		rlayout_set.setOnClickListener(clickListener);
	}

	/**
	 * 跳转到时光轴
	 */
	private void gotoFusion() {
		Intent intent = new Intent(getActivity(), FusionActivity.class);
		intent.putExtra("tel", username);
		startActivityForResult(intent, 0);
	}

	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.iv_personal_dynamic_gotoFusion:
				if (checkIsLogin()) {
					gotoFusion();
				}
				break;
			case R.id.tv_personal_dynamic_name:
				// 登录
				intent.setClass(getActivity(), LoginActivity.class);
				startActivity(intent);
				break;
			case R.id.tv_personal_dynamic_name_before_perfect_information:
				// 设置宠物信息
				intent.setClass(getActivity(), PerfectInformationActivity.class);
				intent.putExtra("tel", username);
				startActivity(intent);
				break;
			case R.id.iv_personal_dynamic_icon_after_landing:
				// 设置宠物头像
				intent.setClass(getActivity(), SetPetIconActivity.class);
				intent.putExtra("tel", username);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 判断是否处于登录状态
	 */
	private boolean checkIsLogin() {
		// 获取本地的sharepreference存储的token值
		String result = SharedPreferencesUtil.getData(getActivity()
				.getApplicationContext());
		if (result == null || result.equals("")) {// 判断获取的token值是否为空
			Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
			return false;
		}
		username = result.split(",")[1];
		return true;
	}
}

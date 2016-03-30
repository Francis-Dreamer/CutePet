package com.dream.cutepet.fragment;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.cutepet.PetDetailsActivity;
import com.dream.cutepet.R;
import com.dream.cutepet.adapter.HomePageAdapter;
import com.dream.cutepet.adapter.HomePageAdapter.CallParise;
import com.dream.cutepet.model.PersonageModel;
import com.dream.cutepet.model.PetStoreModel;
import com.dream.cutepet.ui.PetStoreActivity;
import com.dream.cutepet.util.AsyncImageLoader;
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;
import com.dream.cutepet.util.SharedPreferencesUtil;

/**
 * 主页frament
 * 
 * @author Administrator
 * 
 */
public class HomePageFragment extends Fragment implements CallParise {

	LinearLayout llayout_petStore;
	LayoutInflater inflater;
	List<PetStoreModel> data_petStore;
	List<PersonageModel> data_personage = new ArrayList<PersonageModel>();
	ListView listView;
	HomePageAdapter adapter;
	private AsyncImageLoader imageLoader;
	private String username;
	private View view;
	private String url_top = "http://192.168.11.238";

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_homepage, null);
		imageLoader = new AsyncImageLoader(getActivity());

		initView();

		initStoreData();

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		initPersonalData();
	}

	/**
	 * 初始化宠物店数据
	 */
	private void initStoreData() {
		String URL_store = "http://192.168.11.238/index.php/home/api/getPetStore";
		try {
			HttpPost post_store = HttpPost.parseUrl(URL_store);
			post_store.send();
			post_store.setOnSendListener(new OnSendListener() {
				public void start() {
				}

				public void end(String result) {
					Log.i("data_petStore", result);
					data_petStore = PetStoreModel.setJson(result);
					initPetStoreView();
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化主人寄语数据
	 */
	private void initPersonalData() {
		String URL_store = "http://192.168.11.238/index.php/home/api/getPersonal";
		try {
			HttpPost post_store = HttpPost.parseUrl(URL_store);
			post_store.send();
			post_store.setOnSendListener(new OnSendListener() {
				public void start() {
				}

				public void end(String result) {
					Log.i("initPersonalData", result);
					data_personage = PersonageModel.setJson(result);
					adapter.setData(data_personage);
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		listView = (ListView) view.findViewById(R.id.lv_homepage_listview);
		listView.setOnItemClickListener(itemClickListener);
	}

	/**
	 * listview的item点击事件
	 */
	OnItemClickListener itemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(getActivity(), PetDetailsActivity.class);
			startActivityForResult(intent, 1357);
		}
	};

	/*
	 * 跳转到宠物商店
	 */
	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (checkLogin()) {
				int index = (Integer) v.getTag();
				Intent intent = new Intent(getActivity(),
						PetStoreActivity.class);
				intent.putExtra("index", index);
				startActivityForResult(intent, 0);
			}
		}
	};

	/**
	 * 初始化 宠物店的画廊
	 */
	@SuppressLint("InflateParams")
	private void initPetStoreView() {
		inflater = LayoutInflater.from(getActivity());
		View header = inflater.inflate(R.layout.activity_homepage_header, null);
		llayout_petStore = (LinearLayout) header
				.findViewById(R.id.llayout_homepage_petStore);
		for (int i = 0; i < data_petStore.size(); i++) {
			View view = inflater.inflate(R.layout.activity_homepage_item, null);
			ImageView img = (ImageView) view
					.findViewById(R.id.iv_homepage_item_picture);

			final String imgUrl = url_top + data_petStore.get(i).getLogo();
			// 给 ImageView 设置一个 tag
			img.setTag(imgUrl);
			if (!TextUtils.isEmpty(imgUrl)) {
				// 异步加载图片
				Bitmap bitmap = imageLoader.loadImage(img, imgUrl);
				if (bitmap != null) {
					img.setImageBitmap(bitmap);
				}
			}
			TextView txt = (TextView) view
					.findViewById(R.id.tv_homepage_item_word);
			txt.setText(data_petStore.get(i).getName());
			view.setTag(i);
			view.setOnClickListener(listener);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 0, 10, 0);
			view.setLayoutParams(params);

			llayout_petStore.addView(view);
		}
		listView.addHeaderView(header, null, false);
		adapter = new HomePageAdapter(getActivity(),data_personage,this);
		listView.setAdapter(adapter);
	}

	@Override
	public void click(View v) {
		if (checkLogin()) {
			int position = (Integer) v.getTag();
			setParise(position);
		}
	}

	/**
	 * 点赞功能
	 * 
	 * @param position
	 */
	private void setParise(int position) {
		String url = "http://192.168.11.238/index.php/home/api/uploadPraise_personal";
		try {
			HttpPost httpPost = HttpPost.parseUrl(url);
			Map<String, String> map = new HashMap<String, String>();
			map.put("mine_name", username);
			map.put("issue_id", data_personage.get(position).getId() + "");
			map.put("issue_name", data_personage.get(position).getUsername());
			Log.i("mine_name", username);
			Log.i("issue_id",data_personage.get(position).getId() + "");
			Log.i("issue_name", data_personage.get(position).getUsername());

			httpPost.putMap(map);
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				@Override
				public void start() {
				}
				@Override
				public void end(String result) {
					Log.i("data_petStore", result);
					try {
						JSONObject jsonObject = new JSONObject(result);
						Toast.makeText(getActivity(),
								jsonObject.getString("message"),
								Toast.LENGTH_SHORT).show();
						initPersonalData();
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
	 * 判断账号是否登录
	 * 
	 * @return
	 */
	private boolean checkLogin() {
		String tok = SharedPreferencesUtil.getData(getActivity());
		if (tok != null && !tok.equals("")) {
			username = tok.split(",")[1];
			return true;
		}
		Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
		return false;
	}

}

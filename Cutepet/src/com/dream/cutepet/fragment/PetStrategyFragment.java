package com.dream.cutepet.fragment;

import java.net.MalformedURLException;
import java.util.List;
import com.dream.cutepet.PetStrategyDetailsActivity;
import com.dream.cutepet.R;
import com.dream.cutepet.adapter.PetStrategyBaseAdapter;
import com.dream.cutepet.model.PetStrategyModel;
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;
import com.dream.cutepet.util.SharedPreferencesUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 宠物攻略fragment
 * 
 * @author Administrator
 * 
 */
public class PetStrategyFragment extends Fragment {
	ListView listView;
	List<PetStrategyModel> data;
	PetStrategyBaseAdapter adapter = new PetStrategyBaseAdapter();
	String getData;
	TextView tv_resources, tv_strategy;
	View view;
	private String tel;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_pet_strategy, null);

		initView();

		return view;
	}
	
	/**
	 * 加载listview的数据
	 */
	private void initView() {
		listView = (ListView) view.findViewById(R.id.pet_strategy_listview);
		listView.setOnItemClickListener(itemClickListener);
	}

	/**
	 * 点击跳转
	 */
	OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (checkLogin()) {
				PetStrategyModel model = new PetStrategyModel();
				model = data.get(position);
				String petName = model.getPet_strategy_comment_chinese_name();
				String petGrade = model.getGrade();
				String petMoney = model.getMoney();
				String petTrait = model.getTrait();
				String petContent_data = model.getPet_strategy_content_data();
				String petImage = model.getPet_strategy_image();
				String userId = model.getId();
				String userName = model.getUsername();

				Bundle bundle = new Bundle();
				bundle.putString("petName", petName);
				bundle.putString("petGrade", petGrade);
				bundle.putString("petMoney", petMoney);
				bundle.putString("petTrait", petTrait);
				bundle.putString("petContent_data", petContent_data);
				bundle.putString("petImage", petImage);
				bundle.putString("userId", userId);
				bundle.putString("userName", userName);
				bundle.putString("tel", tel);

				Intent intent = new Intent();
				intent.setClass(getActivity(), PetStrategyDetailsActivity.class);
				intent.putExtras(bundle);
				startActivityForResult(intent, 0);
			}
		}
	};

	public void onStart() {
		super.onStart();
		getData();
	}

	/**
	 * 初始化数据
	 */
	private void getData() {
		String url = "http://211.149.198.8:9805/index.php/home/api/getStrategy";
		try {
			HttpPost httpPost = HttpPost.parseUrl(url);
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				public void start() {
				}

				public void end(String result) {
					Log.e("result", result);
					data = PetStrategyModel.setJson(result);
					adapter = new PetStrategyBaseAdapter(data, getActivity());
					listView.setAdapter(adapter);
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否登录
	 * 
	 * @return
	 */
	private boolean checkLogin() {
		String tok = SharedPreferencesUtil.getData(getActivity());
		if (tok != null) {
			tel = tok.split(",")[1];
			return true;
		}
		Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
		return false;
	}
}

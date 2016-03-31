package com.dream.cutepet.fragment;

import java.net.MalformedURLException;
import java.util.List;
import com.dream.cutepet.PetStrategyDetailsActivity;
import com.dream.cutepet.R;
import com.dream.cutepet.adapter.PetStrategyBaseAdapter;
import com.dream.cutepet.model.PetStrategyModel;
import com.dream.cutepet.util.AsyncImageLoader;
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 宠物攻略fragment
 * 
 * @author Administrator
 * 
 */
public class PetStrategyFragment extends Fragment {

	ListView listView;
	List<PetStrategyModel> data;
	PetStrategyBaseAdapter adapter=new PetStrategyBaseAdapter();;
	String getData;
	TextView tv_resources, tv_strategy;
	View view;
	AsyncImageLoader imageLoader;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_pet_strategy, null);
		
		initview();
		initListView();
	
		return view;
	}

	/**
	 * 初始化以及加载控件
	 */
	@SuppressLint("InflateParams")
	private void initview() {
		// 加上headerview
		listView = (ListView) view.findViewById(R.id.pet_strategy_listview);
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View headerview = inflater.inflate(R.layout.strategy_head_view, null);
		ImageView pet_strategy_head_image = (ImageView) headerview
				.findViewById(R.id.pet_strategy_head_image);
		pet_strategy_head_image.setImageResource(R.drawable.banner);
		listView.addHeaderView(headerview, null, false);
	}

	/**
	 * 加载listview的数据
	 */
	private void initListView(){
		adapter = new PetStrategyBaseAdapter(data, getActivity());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(itemClickListener);
	}
	
	/**
	 * 点击跳转时间
	 */
	OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent();
			int sendPosition=position-1;
			intent.setClass(getActivity(), PetStrategyDetailsActivity.class);
			intent.putExtra("petStrategyPosition", sendPosition);
			startActivityForResult(intent, 0);  
		}
	};
	
	public void onStart() {
		super.onStart();
		getData();
	}

	/**
	 * 初始化数据
	 */
	private void getData(){
		String url="http://192.168.11.238/index.php/home/api/getStrategy";
		try {
			HttpPost httpPost=HttpPost.parseUrl(url);
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				public void start() {
				}
				public void end(String result) {
					Log.e("PetStrategyFragment", result);
					data=PetStrategyModel.setJson(result);
					adapter.setData(data);
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}

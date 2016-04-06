package com.dream.cutepet.fragment;

import com.dream.cutepet.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;


/**
 * 首页
 * 
 * @author Administrator
 *
 */
public class HomePageAndPetStrategyFragment extends Fragment {

	HomePageFragment homePageFragmentChen;
	PetStrategyFragment petStrategyFragment;

	RadioButton resources, strategy;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_homepage_pet_strategy, null);

		resources = (RadioButton) view.findViewById(R.id.resources);
		strategy = (RadioButton) view.findViewById(R.id.strategy);
		
		FragmentManager fm=getFragmentManager();
		FragmentTransaction transaction=fm.beginTransaction();
		
		homePageFragmentChen=new HomePageFragment();
		petStrategyFragment=new PetStrategyFragment();
		transaction.replace(R.id.mid_homepage_petstrategy, homePageFragmentChen);
		transaction.commit();
		resources.setChecked(true);
		resources.setOnClickListener(clickListener);
		strategy.setOnClickListener(clickListener);

		return view;
	}
	
	/**
	 * 点击顶部切换页面
	 */
	OnClickListener clickListener = new OnClickListener() {

		@SuppressLint({ "InflateParams", "Recycle" })
		public void onClick(View v) {
			FragmentManager fm=getFragmentManager();
			FragmentTransaction transaction=fm.beginTransaction();
			switch (v.getId()) {
			case R.id.resources:
				resources.setChecked(true);
				strategy.setChecked(false);
				if(homePageFragmentChen==null){
					homePageFragmentChen=new HomePageFragment();
				}
				transaction.replace(R.id.mid_homepage_petstrategy, homePageFragmentChen);
				break;
			case R.id.strategy:
				strategy.setChecked(true);
				resources.setChecked(false);
				if(petStrategyFragment==null){
					petStrategyFragment=new PetStrategyFragment();
				}
				transaction.replace(R.id.mid_homepage_petstrategy, petStrategyFragment);
				break;
			default:
				break;
			}
			transaction.commit();
		}
	};

}

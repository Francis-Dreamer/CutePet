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
import android.widget.TextView;

/**
 * 宠物圈
 * @author Administrator
 *
 */
public class SquareAndDynamicFragment extends Fragment{
	
	TextView square,dynamic;
	DynamicFragment dynamicFragment;
	SquareFragment squareFragment;
	
	@SuppressLint("InflateParams") @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view =inflater.inflate(R.layout.fragment_square_dynamic, null);
		
		
		square=(TextView) view.findViewById(R.id.square);
		dynamic=(TextView) view.findViewById(R.id.dynamic);
		
		FragmentManager fm=getFragmentManager();
		FragmentTransaction transaction=fm.beginTransaction();
		
		dynamicFragment=new DynamicFragment();
		squareFragment=new SquareFragment();
		
		transaction.replace(R.id.mid_square_dynamic, squareFragment);
		transaction.commit();
		
		square.setOnClickListener(clickListener);
		dynamic.setOnClickListener(clickListener);
		
		return view;
	}
	
	/**
	 * 点击顶部切换页面
	 */
	OnClickListener clickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			FragmentManager fm=getFragmentManager();
			FragmentTransaction transaction=fm.beginTransaction();
			
			switch (v.getId()) {
			case R.id.square:
				if (squareFragment==null) {
					squareFragment=new SquareFragment();
				}
				transaction.replace(R.id.mid_square_dynamic, squareFragment);
				break;
			case R.id.dynamic:
				if (dynamicFragment==null) {
					dynamicFragment=new DynamicFragment();
				}
				transaction.replace(R.id.mid_square_dynamic,dynamicFragment);
				break;
			default:
				break;
			}
			transaction.commit();
		}
	};
	
}

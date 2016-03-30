package com.dream.cutepet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 攻略点评
 * @author Administrator
 *
 */
public class PetStrategyCommentActivity extends Activity{

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet_strategy_comment);
		
		init();
	}
	
	private void init(){
		TextView title=(TextView) findViewById(R.id.title);
		title.setText("点评");
		TextView menu_hide=(TextView) findViewById(R.id.menu_hide);
		menu_hide.setVisibility(View.VISIBLE);
		menu_hide.setText("提交");
		ImageView back=(ImageView) findViewById(R.id.back);
		back.setOnClickListener(clickListener);
		
	}
	
	OnClickListener clickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				back();
				break;

			default:
				break;
			}
		}
	};
	
	private void back(){
		finish();
	}
	
}

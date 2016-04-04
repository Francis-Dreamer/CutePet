package com.dream.cutepet;

import com.dream.cutepet.model.PetStrategyModel;
import com.dream.cutepet.util.AsyncImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 攻略详情
 * 
 * @author Administrator
 * 
 */
public class PetStrategyDetailsActivity extends Activity {

	PetStrategyModel model;
	int petStrategyPosition;
	AsyncImageLoader imageLoader;
	String petName;
	String petGrade;
	String petMoney;
	String petTrait;
	String petContent_data;
	String petImage;
	String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet_strategy_details);
		imageLoader = new AsyncImageLoader(this);
		
	}

	protected void onStart() {
		super.onStart();
		initData();
	}
	
	
	/**
	 * 初始化控件
	 */

	private void initView() {
		ImageView back = (ImageView) findViewById(R.id.back);
		TextView title = (TextView) findViewById(R.id.title);
		ImageView pet_strategy_details_image = (ImageView) findViewById(R.id.pet_strategy_details_image);
		TextView pet_strategy_details_comment = (TextView) findViewById(R.id.pet_strategy_details_comment);
		TextView pet_strategy_details_collection = (TextView) findViewById(R.id.pet_strategy_details_collection);
		TextView pet_strategy_details_breed = (TextView) findViewById(R.id.pet_strategy_details_breed);
		TextView pet_strategy_details_ratingbar_num = (TextView) findViewById(R.id.pet_strategy_details_ratingbar_num);
		TextView pet_strategy_details_num = (TextView) findViewById(R.id.pet_strategy_details_num);
		TextView pet_strategy_details_characteristic = (TextView) findViewById(R.id.pet_strategy_details_characteristic);
		TextView pet_strategy_details_content = (TextView) findViewById(R.id.pet_strategy_details_content);
		RatingBar pet_strategy_details_ratingbar = (RatingBar) findViewById(R.id.pet_strategy_details_ratingbar);

		pet_strategy_details_breed.setText(petName);
		pet_strategy_details_ratingbar.setRating((float) (Integer
				.parseInt(petGrade)));
		pet_strategy_details_ratingbar_num.setText(petGrade + "分");
		pet_strategy_details_num.setText(petMoney);
		pet_strategy_details_characteristic.setText(petTrait);
		pet_strategy_details_content.setText(petContent_data);

	//	String imageUrl = "http://192.168.1.106"+ petImage;
		String imageUrl = "http://192.168.1.106"+ petImage;
		// 给图片一个tag
		pet_strategy_details_image.setTag(imageUrl);
		// 给个预设图片
		pet_strategy_details_image.setImageResource(R.drawable.ic_launcher);

		// 异步加载图片
		if (!TextUtils.isEmpty(imageUrl)) {
			Bitmap bitmap = imageLoader.loadImage(pet_strategy_details_image,
					imageUrl);
			if (bitmap != null) {
				pet_strategy_details_image.setImageBitmap(bitmap);
			}
		}

		title.setText("详情");

		pet_strategy_details_comment.setOnClickListener(clickListener);
		pet_strategy_details_collection.setOnClickListener(clickListener);
		back.setOnClickListener(clickListener);
	}

	/**
	 * 加载数据
	 */
	private void initData() {

		Bundle bundle=getIntent().getExtras();
		petName=bundle.getString("petName");
		petGrade=bundle.getString("petGrade");
		petMoney=bundle.getString("petMoney");
		petTrait=bundle.getString("petTrait");
		petContent_data=bundle.getString("petContent_data");
		petImage=bundle.getString("petImage");
		initView();

	}

	/**
	 * 点击跳转事件
	 */
	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent;
			switch (v.getId()) {
			case R.id.pet_strategy_details_comment:
				intent = new Intent();
				intent.setClass(PetStrategyDetailsActivity.this,
						PetStrategyCommentActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("petName", petName);
				bundle.putString("petGrade", petGrade);
				bundle.putString("petContent_data", petContent_data);
				bundle.putString("petTrait", petTrait);
				bundle.putString("petImage", petImage);
				intent.putExtras(bundle);
				startActivityForResult(intent, 0);
				break;
			case R.id.pet_strategy_details_collection:

				break;
			case R.id.back:
				back();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 返回
	 */
	private void back() {
		finish();
	}

}

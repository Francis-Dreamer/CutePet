package com.dream.cutepet;

import java.io.File;

import com.dream.cutepet.util.AsyncImageLoader;
import com.dream.cutepet.util.SDCardAllPhotoUtil;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 攻略点评
 * @author Administrator
 *
 */
public class PetStrategyCommentActivity extends Activity{

	String petName;
	String petGrade;
	String petTrait;
	String petContent_data;
	String petImage;
	String username;
	AsyncImageLoader imageLoader;
	String view_address;
	File file;
	ImageView pet_strategy_comment_view;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet_strategy_comment);
		imageLoader=new AsyncImageLoader(this);
	}
	
	protected void onStart() {
		super.onStart();
		getData();
	}
	
	/**
	 * 加载页面
	 */
	private void init(){
		TextView title=(TextView) findViewById(R.id.title);
		title.setText("点评");
		TextView menu_hide=(TextView) findViewById(R.id.menu_hide);
		menu_hide.setVisibility(View.VISIBLE);
		menu_hide.setText("提交");
		ImageView back=(ImageView) findViewById(R.id.back);
		ImageView pet_strategy_comment_addview=(ImageView) findViewById(R.id.pet_strategy_comment_addview);
		pet_strategy_comment_view=(ImageView) findViewById(R.id.pet_strategy_comment_view);
		
		back.setOnClickListener(clickListener);
		pet_strategy_comment_addview.setOnClickListener(clickListener);
		menu_hide.setOnClickListener(clickListener);
		
		
		TextView pet_strategy_comment_chinese_name=(TextView) findViewById(R.id.pet_strategy_comment_chinese_name);
	//	TextView pet_strategy_comment_english_name=(TextView) findViewById(R.id.pet_strategy_comment_english_name);
		TextView pet_strategy_content_data=(TextView) findViewById(R.id.pet_strategy_content_data);
		RatingBar pet_strategy_comment_ratingbar=(RatingBar) findViewById(R.id.pet_strategy_comment_ratingbar);
		TextView pet_strategy_comment_ratingbar_num=(TextView) findViewById(R.id.pet_strategy_comment_ratingbar_num);
		TextView pet_strategy_comment_characteristic=(TextView) findViewById(R.id.pet_strategy_comment_characteristic);
		ImageView pet_strategy_image=(ImageView) findViewById(R.id.pet_strategy_image);
		
		pet_strategy_comment_chinese_name.setText(petName);
		pet_strategy_content_data.setText(petContent_data);
		pet_strategy_comment_ratingbar.setRating((Float.parseFloat(petGrade)));
		pet_strategy_comment_ratingbar_num.setText(petGrade+".0分");
		pet_strategy_comment_characteristic.setText(petTrait);
		String imageUrl="http://192.168.1.106"+petImage;
		
		pet_strategy_image.setTag(imageUrl);
		pet_strategy_image.setImageResource(R.drawable.icon_tx);
		if(!TextUtils.isEmpty(imageUrl)){
			Bitmap bitmap=imageLoader.loadImage(pet_strategy_image, imageUrl);
			if(bitmap!=null){
				pet_strategy_image.setImageBitmap(bitmap);
			}
		}
		
	}
	
	/**
	 * 获得数据
	 */
	private void getData(){
		Bundle bundle=getIntent().getExtras();
		petName=bundle.getString("petName");
		petGrade=bundle.getString("petGrade");
		petTrait=bundle.getString("petTrait");
		petContent_data=bundle.getString("petContent_data");
		petImage=bundle.getString("petImage");
		init();
	}
	
	OnClickListener clickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				back();
				break;
			case R.id.menu_hide:
				if(file!=null){
					uploadStrategy(file);
				}else{
					Toast.makeText(getApplication(), "请选择图片", Toast.LENGTH_SHORT).show();
				}
	
				break;
			case R.id.pet_strategy_comment_addview:
				selectImage();
				break;
			default:
				break;
			}
		}
	};
	
	private void back(){
		finish();
	}
	
	
	/**
	 * 上传攻略
	 * @param file
	 */
	private void uploadStrategy(File file){
		
	/*	String tel=
		String petname=
		String grade=
		String content=*/
		
	}
	
	/**
	 * 跳转到选择图片页面
	 */
	private void selectImage(){
		Intent intent=new Intent();
		intent.setClass(PetStrategyCommentActivity.this, SelectPhotoActivity.class);
		startActivityForResult(intent, 111333);
	}
	
	/**
	 * onActivityResult
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		
		switch (requestCode) {
		case 111333:
			if(data!=null){
				Bundle bundle=data.getExtras();
				view_address=bundle.getString("view_address");
				Log.e("XXXXXXXXXXXXXXX", view_address);
				file=new File(view_address);
				pet_strategy_comment_view.setImageBitmap(SDCardAllPhotoUtil.getDiskBitmap(view_address));
			}
			
			break;

		default:
			break;
		}
	
	}
	

	
	
}

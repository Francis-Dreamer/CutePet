package com.dream.cutepet;

import java.net.MalformedURLException;
import java.util.List;

import com.dream.cutepet.adapter.DynamicDetailsBaseAdapter;
import com.dream.cutepet.model.DynamicDetailsData;
import com.dream.cutepet.model.SquareModel;
import com.dream.cutepet.util.AsyncImageLoader;
import com.dream.cutepet.util.BitmapUtil;
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class DynamicDetailsActivity extends Activity {
	List<SquareModel> squareData;
	List<DynamicDetailsData> dynamicDetailsData;
	DynamicDetailsBaseAdapter adapter;
	ListView listView;
	RadioGroup radioGroup_bottom;
	String str_edit;
	EditText dynamic_details_edit;
	String urlTop="http://192.168.11.238";
	AsyncImageLoader imageLoader;
	TextView dynamic_details_nickname;
	TextView dynamic_details_time;
	TextView dynamic_details_address;
	TextView dynamic_details_content;
	ImageView dynamic_details_image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_details);
		imageLoader=new AsyncImageLoader(this);
		initView();
	}

	protected void onStart() {
		super.onStart();
		
		getData();
		
	}
	
	@SuppressLint("InflateParams") private void initView() {
		ImageView back=(ImageView) findViewById(R.id.back);
		ImageView send=(ImageView) findViewById(R.id.send);
		dynamic_details_edit=(EditText) findViewById(R.id.dynamic_details_edit);
		listView = (ListView) findViewById(R.id.dynamic_details_listview);
		TextView title = (TextView) findViewById(R.id.title);
		title.setTextColor(Color.rgb(51, 51, 51));
		title.setText("详情");
		// 加上headerview
		LayoutInflater inflater = LayoutInflater.from(this);
		View dynamic_details_headerview = inflater.inflate(R.layout.dynamic_details_head_view, null);

		dynamic_details_nickname=(TextView) dynamic_details_headerview.findViewById(R.id.dynamic_details_nickname);
		dynamic_details_time=(TextView) dynamic_details_headerview.findViewById(R.id.dynamic_details_time);
		dynamic_details_address=(TextView) dynamic_details_headerview.findViewById(R.id.dynamic_details_address);
		dynamic_details_content=(TextView) dynamic_details_headerview.findViewById(R.id.dynamic_details_content);
		dynamic_details_image=(ImageView) dynamic_details_headerview.findViewById(R.id.dynamic_details_image);
		
		// 头像
		ImageView dynamic_details_portrait = (ImageView) dynamic_details_headerview.findViewById(R.id.dynamic_details_portrait);
	
		//把其他形状转化成圆形头像
		Bundle bundle=getIntent().getExtras();
		int position=bundle.getInt("thePosition");
		String id=bundle.getString("theId");
		String username=bundle.getString("theUsername");
		String portraitUrl=urlTop+squareData.get(position).getSquare_portrait();
		String imageUrl=urlTop+squareData.get(position).getSquare_image();
		
		dynamic_details_nickname.setText(squareData.get(position).getSquare_neckname());
		dynamic_details_time.setText(squareData.get(position).getSquare_comment_time());
		dynamic_details_address.setText(squareData.get(position).getSquare_address());
		dynamic_details_content.setText(squareData.get(position).getSquare_comment_content());
		
		if(!TextUtils.isEmpty(imageUrl)){
			dynamic_details_image.setTag(imageUrl);
			dynamic_details_image.setImageResource(R.drawable.icon_tx);
			Bitmap bt2=imageLoader.loadImage(dynamic_details_image, imageUrl);
			if(bt2!=null){
				dynamic_details_image.setImageBitmap(BitmapUtil.toRoundBitmap(bt2));
			}
		}
		
		if(!TextUtils.isEmpty(portraitUrl)){
			dynamic_details_portrait.setTag(portraitUrl);
			dynamic_details_portrait.setImageResource(R.drawable.icon_tx);
			Bitmap bt1=imageLoader.loadImage(dynamic_details_portrait, portraitUrl);
			if(bt1!=null){
				dynamic_details_portrait.setImageBitmap(BitmapUtil.toRoundBitmap(bt1));
			}
		}
		
		listView.addHeaderView(dynamic_details_headerview);
		adapter = new DynamicDetailsBaseAdapter(dynamicDetailsData, this);
		listView.setAdapter(adapter);
		back.setOnClickListener(clickListener);
		send.setOnClickListener(clickListener);
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		String url = "http://192.168.1.107/index.php/home/api/getTalk";
		try {
			HttpPost httpPost=HttpPost.parseUrl(url);
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				public void start() {
					
				}
				public void end(String result) {
					Log.e("111111111111111", result);
					squareData=SquareModel.setJson(result);
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	OnClickListener clickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				back();
				break;
			case R.id.send:
				
				break;
			default:
				break;
			}
		}
	};
	
	/**
	 * 返回
	 */
	private void back(){
		finish();
	}
	
	/**
	 * 输入内容
	 */

}

package com.dream.cutepet;

import java.io.File;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import com.dream.cutepet.util.HttpPost;
import com.dream.cutepet.util.HttpPost.OnSendListener;
import com.dream.cutepet.util.SDCardAllPhotoUtil;
import com.dream.cutepet.util.SharedPreferencesUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WriteTalkActivity extends Activity{
	
	List<String> getPath;
	String username;
	EditText edit_content;
	EditText edit_address;
	GridLayout gridLayout;
	ImageView getImage;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_writetalk);
		
		initView();
	}

	/**
	 * 加载页面
	 */
	private void initView(){
		
		ImageView back=(ImageView) findViewById(R.id.back);
		TextView title=(TextView) findViewById(R.id.title);
		title.setText("写说说");
		TextView menu_hide=(TextView) findViewById(R.id.menu_hide);
		menu_hide.setText("发表");
		menu_hide.setVisibility(View.VISIBLE);
		ImageView add_image=(ImageView) findViewById(R.id.add_image);
		edit_content=(EditText) findViewById(R.id.ed_write);
		edit_address=(EditText) findViewById(R.id.address);
		gridLayout=(GridLayout) findViewById(R.id.writetalk_gridview);
	//	initGridLayout();
		
		back.setOnClickListener(clickListener);
		menu_hide.setOnClickListener(clickListener);
		add_image.setOnClickListener(clickListener);
		
	}
	
	/**
	 * 加载网格布局
	 */
	private void initGridLayout(){
		
		
		for (int i = 0; i < getPath.size(); i++) {
			String file_path=getPath.get(i);
			Bitmap bitmap=SDCardAllPhotoUtil.getDiskBitmap(file_path);
			getImage.setImageBitmap(bitmap);
		}
		
		
		
	}
	
	OnClickListener clickListener=new OnClickListener() {
		
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				finish();
				break;
			case R.id.menu_hide:
				send();
				break;
			case R.id.add_image:
				Intent intent=new Intent();
				intent.setClass(WriteTalkActivity.this, WriteTalkUpLoadPhotoActivity.class);
				startActivityForResult(intent, 5551);
				break;
			default:
				break;
			}
		}
	};
	
	/**
	 * 发表
	 */
	@SuppressLint("SimpleDateFormat") private void send(){
		String tok = SharedPreferencesUtil.getData(this);
		username = tok.split(",")[1];
		
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日");
		String create_time=format.format(date);
		String content=edit_content.getText().toString().trim();
		String address=edit_address.getText().toString().trim();
		
		
		String url="http://192.168.11.238/index.php/home/api/uploadTalk";
		try {
			HttpPost httpPost=HttpPost.parseUrl(url);
			Map<String, String> map=new HashMap<String, String>();
			map.put("tel", username);
			map.put("create_time", create_time);
			map.put("content", content);
			map.put("address", address);
			httpPost.putMap(map);
			for (int i = 0; i < getPath.size(); i++) {
				String file_path=getPath.get(i);
				File file=new File(file_path);
				httpPost.putFile("file", file, file.getName(), null);	
			}
			httpPost.send();
			httpPost.setOnSendListener(new OnSendListener() {
				
				public void start() {
					
				}
				
				public void end(String result) {
					Log.e("result", "result = " + result);
					try {
						JSONObject object=new JSONObject(result);
						String message=object.getString("message");
						Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 5551:
			if(data!=null){
				Bundle bundle=data.getExtras();
				getPath=new ArrayList<String>();
				getPath=bundle.getStringArrayList("checked_path");
			}
			break;

		default:
			break;
		}
	};
	
	
	
}

package com.dream.cutepet;

import com.dream.cutepet.cache.AsyncImageLoader;
import com.dream.cutepet.cache.ImageCacheManager;
import com.dream.cutepet.util.SharedPreferencesUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PetDetailsActivity extends Activity {
	private TextView title, menu_hide;
	private ImageView back;
	private String username;
	private String logo;
	private String name;
	private String petName;
	private String content;
	private AsyncImageLoader imageLoader;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_petdetails);

		ImageCacheManager cacheManager = new ImageCacheManager(
				getApplicationContext());
		imageLoader = new AsyncImageLoader(getApplicationContext(),
				cacheManager.getMemoryCache(),
				cacheManager.getPlacardFileCache());

		Bundle bundle = getIntent().getExtras();
		username = bundle.getString("tel");
		logo = bundle.getString("petlogo");
		name = bundle.getString("name");
		petName = bundle.getString("petname");
		content = bundle.getString("content");

		initview();
	}

	/**
	 * 获取本地账号
	 * 
	 * @return
	 */
	private boolean getUser() {
		String tok = SharedPreferencesUtil.getData(getApplicationContext());
		if (tok != null && !tok.equals("")) {
			username = tok.split(",")[1];
			return true;
		} else {
			Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
	}

	/**
	 * 加载页面
	 */
	private void initview() {
		title = (TextView) findViewById(R.id.title);
		menu_hide = (TextView) findViewById(R.id.menu_hide);
		back = (ImageView) findViewById(R.id.back);
		menu_hide.setVisibility(View.VISIBLE);
		title.setText("宠物详情");
		menu_hide.setText("发布");

		back.setOnClickListener(clickListener);
		menu_hide.setOnClickListener(clickListener);

		ImageView iv_logo = (ImageView) findViewById(R.id.iv_personal_logo);
		TextView tv_name = (TextView) findViewById(R.id.tv_personal_name);
		TextView tv_petname = (TextView) findViewById(R.id.tv_personal_type);
		TextView tv_content = (TextView) findViewById(R.id.tv_personal_content);
		tv_name.setText(name + "");
		tv_petname.setText(petName + "");
		tv_content.setText(content + "");

		String url_img = "http://192.168.11.238" + logo;
		iv_logo.setTag(url_img);
		Bitmap bitmap = imageLoader.loadBitmap(iv_logo, url_img, true);
		if (bitmap != null) {
			iv_logo.setImageBitmap(bitmap);
		} else {
			iv_logo.setImageResource(R.drawable.friends_sends_pictures_no);
		}
	}

	/**
	 * 点击事件
	 */
	OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				finish();
				break;
			case R.id.menu_hide:
				if (getUser()) {
					Intent intent = new Intent(PetDetailsActivity.this,
							PetReleaseActivity.class);
					intent.putExtra("tel", username);
					startActivity(intent);
				}
				break;
			default:
				break;
			}
		}
	};

}

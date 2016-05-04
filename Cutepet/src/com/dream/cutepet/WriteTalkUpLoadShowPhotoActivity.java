package com.dream.cutepet;

import java.util.ArrayList;
import java.util.List;

import com.dream.cutepet.adapter.ShowPhotoAdapter;
import com.dream.cutepet.adapter.ShowPhotoAdapter.OnItemCheckChangeListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class WriteTalkUpLoadShowPhotoActivity extends Activity implements
		OnClickListener, OnItemCheckChangeListener, OnItemClickListener {
	private GridView mGridView;
	private List<String> list;
	private ShowPhotoAdapter adapter;
	private TextView tv_cancel, tv_sure;
	AlertDialog dialog;
	Builder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showphoto_item);

		list = getIntent().getStringArrayListExtra("data");
		
		builder = new AlertDialog.Builder(this);
		
		initView();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		mGridView = (GridView) findViewById(R.id.gv_showphoto_item_gridview);
		adapter = new ShowPhotoAdapter(getApplicationContext(), list,
				mGridView, this);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(this);

		tv_cancel = (TextView) findViewById(R.id.tv_showphoto_cancel);
		tv_sure = (TextView) findViewById(R.id.tv_showphoto_sure);
		tv_cancel.setOnClickListener(this);
		tv_sure.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_showphoto_cancel:
			finish();
			break;
		case R.id.tv_showphoto_sure:
			isSure();
			break;
		default:
			break;
		}
	}

	/**
	 * 确定选中图片
	 */
	private void isSure() {
		// 获取选中的Item的position
		List<Integer> num = adapter.getSelectItems();
		// 创建一个存储file对象的list
		ArrayList<String> checked_path = new ArrayList<String>();
		for (int i = 0; i < num.size(); i++) {
			// 获取图片选中的路径
			String path = list.get(num.get(i));
			checked_path.add(path);
		}
		MyApplication application = (MyApplication) getApplication();
		application.addMoreImage(checked_path);
		finish();
	}

	@Override
	public void onItemCheckChangeListener(View v) {
		int len = adapter.getSelectItems().size();
		tv_sure.setText("确定(" + len + ")");
	}
	
	/**
	 * 放大照片(全屏显示)
	 * 
	 * @param position
	 */
	private void EnlargeImage(int position) {
		// 创建AlertDialog
		dialog = builder.create();
		ImageView imageView = new ImageView(this);
		// 通过点击的position获取该图片的地址，并转换为bitmap型
		Bitmap bitmap = BitmapFactory.decodeFile(list.get(position));
		imageView.setImageBitmap(bitmap);
		imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		imageView.setScaleType(ScaleType.FIT_CENTER);
		// 设置不留白
		imageView.setAdjustViewBounds(true);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dialog != null) {
					// 关闭AlertDialog，并置为空
					dialog.dismiss();
					dialog = null;
				}
			}
		});
		// 设置dialog的上下文为该图片
		dialog.setView(imageView);
		dialog.show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 放大照片
		EnlargeImage(position);
	}
}
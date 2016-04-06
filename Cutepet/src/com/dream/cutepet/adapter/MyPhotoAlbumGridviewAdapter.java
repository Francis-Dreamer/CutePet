package com.dream.cutepet.adapter;

import java.util.List;

import com.dream.cutepet.R;
import com.dream.cutepet.util.ImageDownLoader;
import com.dream.cutepet.util.ImageDownLoader.onImageLoaderListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MyPhotoAlbumGridviewAdapter extends BaseAdapter implements
		OnScrollListener {
	List<String> data;
	Context context;
	private String url_Top = "http://192.168.11.238";
	private ImageDownLoader mImageDownLoader;

	/**
	 * 记录是否刚打开程序，用于解决进入程序不滚动屏幕，不会下载图片的问题。
	 * 参考http://blog.csdn.net/guolin_blog/article/details/9526203#comments
	 */
	private boolean isFirstEnter = true;

	/**
	 * 一屏中第一个item的位置
	 */
	private int mFirstVisibleItem;

	/**
	 * 一屏中所有item的个数
	 */
	private int mVisibleItemCount;

	private GridView mGridView;

	public MyPhotoAlbumGridviewAdapter() {

	}

	public MyPhotoAlbumGridviewAdapter(Context context, List<String> data,
			GridView mGridView) {
		this.data = data;
		this.context = context;
		this.mGridView = mGridView;
		mImageDownLoader = new ImageDownLoader(context);
		mGridView.setOnScrollListener(this);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		// 获取图片url地址
		String img = data.get(position);
		if (convertView == null) {
			imageView = new ImageView(context);
		} else {
			imageView = (ImageView) convertView;
		}
		imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
		imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		showImage(0, data.size());

		String mImageUrl = url_Top + img;
		imageView.setTag(mImageUrl);
		Log.e("getView", "i=" + position + "......" + "mImageUrl = "
				+ mImageUrl);
		/******************************* 去掉下面这几行试试是什么效果 ****************************/
		Bitmap bitmap = mImageDownLoader.showCacheBitmap(mImageUrl.replaceAll(
				"[^\\w]", ""));
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			imageView.setImageResource(R.drawable.friends_sends_pictures_no);
		}
		/**********************************************************************************/
		return imageView;
	}

	/**
	 * 显示当前屏幕的图片，先会去查找LruCache，LruCache没有就去sd卡或者手机目录查找，在没有就开启线程去下载
	 * 
	 * @param firstVisibleItem
	 * @param visibleItemCount
	 */
	private void showImage(int firstVisibleItem, int visibleItemCount) {
		Bitmap bitmap = null;
		for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
			String mImageUrl = url_Top + data.get(i);
			Log.e("showImage", "i=" + i + "......" + "mImageUrl = " + mImageUrl);
			final ImageView mImageView = (ImageView) mGridView
					.findViewWithTag(mImageUrl);
			bitmap = mImageDownLoader.downloadImage(mImageUrl,
					new onImageLoaderListener() {
						@Override
						public void onImageLoader(Bitmap bitmap, String url) {
							if (mImageView != null && bitmap != null) {
								mImageView.setImageBitmap(bitmap);
							}

						}
					});
			Log.e("showImage", "mImageView = " + mImageView);
			if (bitmap != null) {
				mImageView.setImageBitmap(bitmap);
			} else {
				mImageView.setImageDrawable(context.getResources().getDrawable(
						R.drawable.friends_sends_pictures_no));
			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 仅当GridView静止时才去下载图片，GridView滑动时取消所有正在下载的任务
		if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
			showImage(mFirstVisibleItem, mVisibleItemCount);
		} else {
			cancelTask();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		mFirstVisibleItem = firstVisibleItem;
		mVisibleItemCount = visibleItemCount;
		// 因此在这里为首次进入程序开启下载任务。
		if (isFirstEnter && visibleItemCount > 0) {
			showImage(mFirstVisibleItem, mVisibleItemCount);
			isFirstEnter = false;
		}
	}

	/**
	 * 取消下载任务
	 */
	public void cancelTask() {
		mImageDownLoader.cancelTask();
	}
}

package com.dream.cutepet.adapter;

import java.util.List;

import com.dream.cutepet.R;
import com.dream.cutepet.util.AsyncImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class WriteTalkGridAdapter extends BaseAdapter{

	List<String> data;
	Context context;
	LayoutInflater inflater;
	AsyncImageLoader imageLoader;
	
	public WriteTalkGridAdapter(){
		
	}
	
	public WriteTalkGridAdapter(List<String> data,Context context){
		this.data=data;
		this.context=context;
		inflater=LayoutInflater.from(context);
		imageLoader=new AsyncImageLoader(context);
	}
	
	public void setData(List<String> data){
		this.data=data;
		this.notifyDataSetChanged();
	}
	
	
	public int getCount() {
		if(data!=null){
			return data.size();
		}else{
			return 0;
		}
	
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.every_writetalk_image, null);
			holder.image=(ImageView) convertView.findViewById(R.id.oneimage);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		String path=(String) getItem(position);
		if(!TextUtils.isEmpty(path)){
			holder.image.setTag(path);
			holder.image.setImageResource(R.drawable.icon_tx);
			Bitmap bitmap=imageLoader.loadImage(holder.image, path);
			if(bitmap!=null){
				holder.image.setImageBitmap(bitmap);
			}
		}
		return convertView;
	}

	class ViewHolder{
		ImageView image;
	}
}

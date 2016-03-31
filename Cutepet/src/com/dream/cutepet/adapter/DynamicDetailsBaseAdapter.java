package com.dream.cutepet.adapter;

import java.util.List;

import com.dream.cutepet.R;
import com.dream.cutepet.model.DynamicDetailsData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DynamicDetailsBaseAdapter extends BaseAdapter{

	List<DynamicDetailsData> data;
	Context context;
	LayoutInflater inflater;
	
	public DynamicDetailsBaseAdapter(){
		
	}
	
	public DynamicDetailsBaseAdapter(List<DynamicDetailsData> data,Context context){
		this.data=data;
		this.context=context;
		inflater=LayoutInflater.from(context);
	}
	
	public void setData(List<DynamicDetailsData> data){
		this.data=data;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if(data!=null){
			return data.size();
		}else{
			return 0;
		}
		
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.every_dynamic_details, null);
			holder=new ViewHolder();
			holder.dynamic_details_portrait=(ImageView) convertView.findViewById(R.id.dynamic_details_comment_portrait);
			holder.dynamic_details_neckname=(TextView) convertView.findViewById(R.id.dynamic_details_comment_neckname);
			holder.dynamic_details_comment_content=(TextView) convertView.findViewById(R.id.dynamic_details_comment_content);
			holder.dynamic_details_comment_time=(TextView) convertView.findViewById(R.id.dynamic_details_comment_time);
			convertView.setTag(holder);
		
		}
		holder=(ViewHolder) convertView.getTag();
		
		holder.dynamic_details_portrait.setImageResource(data.get(position).getDynamic_details_portrait());
		holder.dynamic_details_neckname.setText(data.get(position).getDynamic_details_neckname());
		holder.dynamic_details_comment_content.setText(data.get(position).getDynamic_details_comment_content());
		holder.dynamic_details_comment_time.setText(data.get(position).getDynamic_details_comment_time());
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView dynamic_details_portrait;
		TextView dynamic_details_neckname;
		TextView dynamic_details_comment_content;
		TextView dynamic_details_comment_time;
	}

}

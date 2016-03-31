package com.dream.cutpet.server;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMConversationListUI;
import com.dream.cutepet.R;
/**
 * 最近联系人自定义
 * @author Administrator
 *
 */
public class ConversationListUICustomSample extends IMConversationListUI{

	public ConversationListUICustomSample(Pointcut pointcut) {
		super(pointcut);
		// TODO Auto-generated constructor stub
	}
	@SuppressLint("ResourceAsColor") @Override
	public View getCustomConversationListTitle(Fragment fragment,
			Context context, LayoutInflater inflater) {
		TextView textView=new TextView(context);
		textView.setText("最近联系人");
		textView.setGravity(Gravity.CENTER);
		textView.setTextColor(Color.BLACK);
		textView.setBackgroundColor(Color.parseColor("#FFDA44"));
		textView.setTextSize(18);
		return textView;
	}
}

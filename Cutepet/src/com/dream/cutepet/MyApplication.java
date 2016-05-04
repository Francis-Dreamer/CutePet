package com.dream.cutepet;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.aop.AdviceBinder;
import com.alibaba.mobileim.aop.PointCutEnum;
import com.alibaba.wxlib.util.SysUtil;
import com.dream.cutepet.server.ChattingCustomAdviceSample;
import com.dream.cutepet.server.ContactsUICustomSample;
import com.dream.cutepet.server.ConversationListUICustomSample;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
	/**
	 * 单张相片的路径
	 */
	private String image = "";

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = "";
		this.image = image;
	}

	private List<String> moreImage = new ArrayList<String>();
	
	public void addMoreImage(List<String> moreImage) {
		this.moreImage.clear();
		this.moreImage = moreImage;
	}
	
	public List<String> getMoreImage() {
		return moreImage;
	}
	
	public void clearMoreImage(){
		this.moreImage.clear();
	}

	public void setMoreImage(List<String> moreImage) {
		this.moreImage = moreImage;
	}

	public static Context getsContext() {
		return sContext;
	}

	public static void setsContext(Context sContext) {
		MyApplication.sContext = sContext;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_UI_POINTCUT,
				ChattingCustomAdviceSample.class);
		AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_UI_POINTCUT,
				ConversationListUICustomSample.class);
		AdviceBinder.bindAdvice(PointCutEnum.CONTACTS_UI_POINTCUT,
				ContactsUICustomSample.class);
		// Application.onCreate中，首先执行这部分代码，以下代码固定在此处，不要改动，这里return是为了退出Application.onCreate！！！
		if (mustRunFirstInsideApplicationOnCreate()) {
			// todo 如果在":TCMSSevice"进程中，无需进行openIM和app业务的初始化，以节省内存
			return;
		}
		// SDK初始化
		final String APP_KEY = "23331616";

		// 必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
		SysUtil.setApplication(this);
		if (SysUtil.isTCMSServiceProcess(this)) {
			return;
		}

		// 第一个参数是Application Context
		// 这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
		if (SysUtil.isMainProcess(this)) {
			YWAPI.init(this, APP_KEY);
		}

	}

	// 云旺OpenIM的DEMO用到的Application上下文实例
	private static Context sContext;

	public static Context getContext() {
		return sContext;
	}

	private boolean mustRunFirstInsideApplicationOnCreate() {
		// 必须的初始化
		SysUtil.setApplication(this);
		sContext = getApplicationContext();
		return SysUtil.isTCMSServiceProcess(sContext);
	}
}

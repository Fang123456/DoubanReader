package com.k.douban;

import android.app.Application;
import android.content.Intent;
import com.k.douban.domain.Note;

public class MyApp extends Application {

	public Note note;

	public void onCreate() {
		super.onCreate();
		//把自定义的异常处理类设置 给主线程
		MyCrashHandler myCrashHandler =	MyCrashHandler.getInstance();
		myCrashHandler.init(getApplicationContext());
		Thread.currentThread().setUncaughtExceptionHandler(myCrashHandler);
	}
	@Override
	public void onLowMemory() {
		super.onLowMemory();

		// 发送一些广播 关闭掉一些activity service
		Intent intent = new Intent();
		intent.setAction("kill_activity_action");
		sendBroadcast(intent);


	}
}


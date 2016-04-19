package com.k.douban;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

//import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.PlainTextConstruct;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;

public class MyCrashHandler implements UncaughtExceptionHandler {
	// 保证MyCrashHandler只有一个实例
	// 2.提供一个静态的程序变量
	private static MyCrashHandler myCrashHandler;
	private Context context;

	// 1.私有化构造方法
	private MyCrashHandler() {

	}

	// 3.暴露出来一个静态的方法 获取myCrashHandler

	public static synchronized MyCrashHandler getInstance() {
		if (myCrashHandler == null) {
			myCrashHandler = new MyCrashHandler();
		}
		return myCrashHandler;
	}

	public void init(Context context) {
		this.context = context;
	}

	// 程序发生异常的时候调用的方法
	// try catch

	public void uncaughtException(Thread thread, Throwable ex) {
		System.out.println("出现错误啦 哈哈");
		StringBuilder sb = new StringBuilder();
		// 1.获取当前应用程序的版本号.
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(context.getPackageName(),
					0);
			sb.append("程序的版本号为" + packinfo.versionName);
			sb.append("\n");

			// 2.获取手机的硬件信息.
			Field[] fields = Build.class.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				// 暴力反射,获取私有的字段信息
				fields[i].setAccessible(true);
				String name = fields[i].getName();
				sb.append(name + " = ");
				String value = fields[i].get(null).toString();
				sb.append(value);
				sb.append("\n");
			}
			// 3.获取程序错误的堆栈信息 .
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			ex.printStackTrace(printWriter);
			
		    String result =	writer.toString();
		    sb.append(result);
		    
		    
		    System.out.println(sb.toString());

			// 4.把错误信息 提交到服务器
//		    String apiKey = "0c51c1ba21ad8cfd24f5452e6508a6f7";
//			String secret = "359e16e5e5c62b6e";
//			DoubanService myService = new DoubanService("黑马小瓣瓣", apiKey,
//					secret);
//			SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
//			String accesstoken = sp.getString("accesstoken", "");
//			String tokensecret = sp.getString("tokensecret", "");
//			myService.setAccessToken(accesstoken, tokensecret);
//
//
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//			String title = format.format(new Date(System.currentTimeMillis()));
//			myService.createNote(new PlainTextConstruct(title), new PlainTextConstruct(sb.toString()), "private", "no");
//
			Log.e("error",sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 完成自杀的操作
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}

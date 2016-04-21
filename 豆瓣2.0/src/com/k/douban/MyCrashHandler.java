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
	// ��֤MyCrashHandlerֻ��һ��ʵ��
	// 2.�ṩһ����̬�ĳ������
	private static MyCrashHandler myCrashHandler;
	private Context context;

	// 1.˽�л����췽��
	private MyCrashHandler() {

	}

	// 3.��¶����һ����̬�ķ��� ��ȡmyCrashHandler

	public static synchronized MyCrashHandler getInstance() {
		if (myCrashHandler == null) {
			myCrashHandler = new MyCrashHandler();
		}
		return myCrashHandler;
	}

	public void init(Context context) {
		this.context = context;
	}

	// �������쳣��ʱ����õķ���
	// try catch

	public void uncaughtException(Thread thread, Throwable ex) {
		System.out.println("���ִ����� ����");
		StringBuilder sb = new StringBuilder();
		// 1.��ȡ��ǰӦ�ó���İ汾��.
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(context.getPackageName(),
					0);
			sb.append("����İ汾��Ϊ" + packinfo.versionName);
			sb.append("\n");

			// 2.��ȡ�ֻ���Ӳ����Ϣ.
			Field[] fields = Build.class.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				// ��������,��ȡ˽�е��ֶ���Ϣ
				fields[i].setAccessible(true);
				String name = fields[i].getName();
				sb.append(name + " = ");
				String value = fields[i].get(null).toString();
				sb.append(value);
				sb.append("\n");
			}
			// 3.��ȡ�������Ķ�ջ��Ϣ .
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			ex.printStackTrace(printWriter);
			
		    String result =	writer.toString();
		    sb.append(result);
		    
		    
		    System.out.println(sb.toString());

			// 4.�Ѵ�����Ϣ �ύ��������
//		    String apiKey = "0c51c1ba21ad8cfd24f5452e6508a6f7";
//			String secret = "359e16e5e5c62b6e";
//			DoubanService myService = new DoubanService("����С���", apiKey,
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

		// �����ɱ�Ĳ���
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}

package com.k.douban;

import java.io.IOException;


import android.app.Activity;
import android.content.SharedPreferences;
import com.google.gdata.data.Link;
import com.google.gdata.data.TextContent;
//import com.google.gdata.data.douban.UserEntry;
import com.google.gdata.util.ServiceException;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.k.douban.util.LoadImageAsynTask;
import douban.model.user.DoubanUserObj;
import douban.playground.service.DoubanUserServiceTest;

public class MyInfoActivity extends BaseActivity implements OnClickListener {
	private TextView tv_name;
	private TextView tv_location;
	private TextView tv_info;
	private ImageView iv_icon;
	private ImageButton  back_button;
	private SharedPreferences sharedPreferences;
	String name ;
	String location ;
	String content ;
	String iconurl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//保证首先设置contentview 然后再去调用父类的方法
		setContentView(R.layout.my_info);
		super.onCreate(savedInstanceState);
//		System.out.println("开始：");
		back_button= (ImageButton) findViewById(R.id.back_button);
		back_button.setOnClickListener(mGoBack);
		setupView();
		setListener();
		fillData();
	}
	public View.OnClickListener mGoBack = new View.OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};
	@Override
	public void setupView() {
		System.out.println("setupView()");
		tv_name= (TextView) this.findViewById(R.id.txtUserName);
		tv_location= (TextView) this.findViewById(R.id.txtUserAddress);
		tv_info= (TextView) this.findViewById(R.id.txtUserDescription);
		iv_icon = (ImageView)this.findViewById(R.id.imgUser);
		mRelativeLoading = (RelativeLayout) this.findViewById(R.id.loading);
		mTextViewTitle = (TextView) this.findViewById(R.id.myTitle);
		mImageBack = (ImageButton)this.findViewById(R.id.back_button);
	}

	@Override
	public void setListener() {
		mImageBack.setOnClickListener(this);
	}

	@Override
	public void fillData() {
		System.out.println("调用fillData()");
		//            参数， 进度，结果
		new AsyncTask<Void, Void, Void>() {
			//onPreExecute 在异步任务执行之前调用的方法,运行在主线程里面的,初始化ui的操作
			@Override
			protected void onPreExecute() {
				System.out.println("onPreExecute()  showLoading()");
				showLoading();
				super.onPreExecute();
			}

			// onPostExecute 在异步任务(后台任务)执行之后调用的方法,运行在ui线程中 ,
			@Override
			protected void onPostExecute(Void result) {
				hideLoading();
				super.onPostExecute(result);
				System.out.println("将内容添加到界面");
				tv_info.setText(content);
				tv_location.setText(location);
				tv_name.setText(name);
				//设置用户的头像 
				LoadImageAsynTask task = new LoadImageAsynTask(new LoadImageAsynTask.LoadImageAsynTaskCallback() {
					public void beforeLoadImage() {
						System.out.println("beforeLoadImage");
						iv_icon.setImageResource(R.drawable.ic_launcher);  //设置默认图标
						
					}
					public void afterLoadImage(Bitmap bitmap) {
						System.out.println("afterLoadImage");
						if (bitmap!=null) {
							iv_icon.setImageBitmap(bitmap);
						}else{
							iv_icon.setImageResource(R.drawable.ic_launcher);//设置默认图标
						}
						
					}
				});
				task.execute(iconurl);
			}
			// doInBackground 后台执行的任务 
			// 方法运行在一个子线程当中 
			@Override
			protected Void doInBackground(Void... params) {
				System.out.println("doInBackground()");
				// 执行耗时的操作 
//				try {

					//获取用户信息并打印
//					UserEntry ue = myService.getAuthorizedUser();
//					 name = ue.getTitle().getPlainText();
//					 location = ue.getLocation();
//					 content = ((TextContent) ue.getContent()).getContent().getPlainText();
//						for (Link link : ue.getLinks()) {
//							if("icon".equals(link.getRel())){
//								iconurl = link.getHref();
//							}
//						}
					  //NAME 用户id CONTENT ICONURL

				DoubanUserServiceTest doubanUserServiceTest=new DoubanUserServiceTest();
				System.out.println("呵呵呵呵呵呵呵呵");
				DoubanUserObj result= null;
				sharedPreferences= getSharedPreferences("config", Activity.MODE_PRIVATE);
				String userid=sharedPreferences.getString("userid", "");
				try {
					result = doubanUserServiceTest.testGetUserProfileByUid(userid);
				} catch (Exception e) {
					e.printStackTrace();
				}
				name=result.getTitle();
				location="常居：天津";
				iconurl=result.getLinks().get(2).getHref();
				content= result.getContent();
				System.out.println("哈哈哈哈哈哈哈");
				System.out.println("getUserProfileByUid1"+result.getTitle());   //用户名
				System.out.println("getUserProfileByUid2"+result.getLocation());//null
				System.out.println("getUserProfileByUid3"+result.getContent()); //自我介绍
				System.out.println("getUserProfileByUid4"+result.getUid());      //用户id
				System.out.println("getUserProfileByUid5"+result.getLinks().get(2).getHref());
				System.out.println("getUserProfileByUid6"+result.getSignature()); //签名
				System.out.println("getUserProfileByUid7" + result.getUri());  //entry的地址
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
				return null;
			}
		}.execute();
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_button:
			finish();
			break;
		}
		
	}

}

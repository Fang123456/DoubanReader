package com.k.douban;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public abstract class  BaseActivity extends Activity {
	public TextView mTextViewTitle;//最上面的title
	public RelativeLayout mRelativeLoading; //加载条
//	public DoubanService myService;
	public ImageButton mImageBack;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String apiKey = "0c51c1ba21ad8cfd24f5452e6508a6f7";
		String secret = "359e16e5e5c62b6e";

//		myService = new DoubanService("黑马小瓣瓣", apiKey,
//				secret);
//		//密钥设置给myService
//		SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
//		String accesstoken = sp.getString("accesstoken", "");
//		String tokensecret = sp.getString("tokensecret", "");
//		myService.setAccessToken(accesstoken, tokensecret);


		setupView();
		setListener() ;
		fillData();
	}



	public abstract void setupView();
	public abstract void setListener();
	public abstract void fillData();
	public  void showLoading(){
		mRelativeLoading.setVisibility(View.VISIBLE);
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(1000);
		ScaleAnimation sa = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f);
		sa.setDuration(1000);
		AnimationSet set = new AnimationSet(false);
		set.addAnimation(sa);
		set.addAnimation(aa);
		mRelativeLoading.setAnimation(set);
		mRelativeLoading.startAnimation(set);
	}
	public  void hideLoading(){
		AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);
		aa.setDuration(1000);
		ScaleAnimation sa = new ScaleAnimation(1.0f, 0.0f, 1.0f,0.0f);
		sa.setDuration(1000);
		AnimationSet set = new AnimationSet(false);
		set.addAnimation(sa);
		set.addAnimation(aa);
		mRelativeLoading.setAnimation(set);
		mRelativeLoading.startAnimation(set);
		mRelativeLoading.setVisibility(View.INVISIBLE);
	}
	
	public void showToast(String text){
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	
}

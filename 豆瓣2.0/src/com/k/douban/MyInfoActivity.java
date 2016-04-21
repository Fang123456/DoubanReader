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
		//��֤��������contentview Ȼ����ȥ���ø���ķ���
		setContentView(R.layout.my_info);
		super.onCreate(savedInstanceState);
//		System.out.println("��ʼ��");
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
		System.out.println("����fillData()");
		//            ������ ���ȣ����
		new AsyncTask<Void, Void, Void>() {
			//onPreExecute ���첽����ִ��֮ǰ���õķ���,���������߳������,��ʼ��ui�Ĳ���
			@Override
			protected void onPreExecute() {
				System.out.println("onPreExecute()  showLoading()");
				showLoading();
				super.onPreExecute();
			}

			// onPostExecute ���첽����(��̨����)ִ��֮����õķ���,������ui�߳��� ,
			@Override
			protected void onPostExecute(Void result) {
				hideLoading();
				super.onPostExecute(result);
				System.out.println("��������ӵ�����");
				tv_info.setText(content);
				tv_location.setText(location);
				tv_name.setText(name);
				//�����û���ͷ�� 
				LoadImageAsynTask task = new LoadImageAsynTask(new LoadImageAsynTask.LoadImageAsynTaskCallback() {
					public void beforeLoadImage() {
						System.out.println("beforeLoadImage");
						iv_icon.setImageResource(R.drawable.ic_launcher);  //����Ĭ��ͼ��
						
					}
					public void afterLoadImage(Bitmap bitmap) {
						System.out.println("afterLoadImage");
						if (bitmap!=null) {
							iv_icon.setImageBitmap(bitmap);
						}else{
							iv_icon.setImageResource(R.drawable.ic_launcher);//����Ĭ��ͼ��
						}
						
					}
				});
				task.execute(iconurl);
			}
			// doInBackground ��ִ̨�е����� 
			// ����������һ�����̵߳��� 
			@Override
			protected Void doInBackground(Void... params) {
				System.out.println("doInBackground()");
				// ִ�к�ʱ�Ĳ��� 
//				try {

					//��ȡ�û���Ϣ����ӡ
//					UserEntry ue = myService.getAuthorizedUser();
//					 name = ue.getTitle().getPlainText();
//					 location = ue.getLocation();
//					 content = ((TextContent) ue.getContent()).getContent().getPlainText();
//						for (Link link : ue.getLinks()) {
//							if("icon".equals(link.getRel())){
//								iconurl = link.getHref();
//							}
//						}
					  //NAME �û�id CONTENT ICONURL

				DoubanUserServiceTest doubanUserServiceTest=new DoubanUserServiceTest();
				System.out.println("�ǺǺǺǺǺǺǺ�");
				DoubanUserObj result= null;
				sharedPreferences= getSharedPreferences("config", Activity.MODE_PRIVATE);
				String userid=sharedPreferences.getString("userid", "");
				try {
					result = doubanUserServiceTest.testGetUserProfileByUid(userid);
				} catch (Exception e) {
					e.printStackTrace();
				}
				name=result.getTitle();
				location="���ӣ����";
				iconurl=result.getLinks().get(2).getHref();
				content= result.getContent();
				System.out.println("��������������");
				System.out.println("getUserProfileByUid1"+result.getTitle());   //�û���
				System.out.println("getUserProfileByUid2"+result.getLocation());//null
				System.out.println("getUserProfileByUid3"+result.getContent()); //���ҽ���
				System.out.println("getUserProfileByUid4"+result.getUid());      //�û�id
				System.out.println("getUserProfileByUid5"+result.getLinks().get(2).getHref());
				System.out.println("getUserProfileByUid6"+result.getSignature()); //ǩ��
				System.out.println("getUserProfileByUid7" + result.getUri());  //entry�ĵ�ַ
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

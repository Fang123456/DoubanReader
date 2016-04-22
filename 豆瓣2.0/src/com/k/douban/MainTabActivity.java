package com.k.douban;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Created by AAAAA on 2015/12/7.
 */
public class MainTabActivity extends TabActivity {
    private TabHost mtabHost;
    private LayoutInflater inflater;
    private ImageView iv1, iv2, iv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_tab);

        inflater = inflater.from(this);//��ʼ�������
        mtabHost = (TabHost) findViewById(android.R.id.tabhost);
        mtabHost.setup();   //��ʼ��tabHost�������tabs


//        mtabHost.addTab(tab3Spec());//����


        mtabHost.addTab(tab2Spec());//����
        mtabHost.addTab(tab4Spec());//����
        mtabHost.addTab(tab1Spec());//�ҵĶ���
        mtabHost.addTab(tab5Spec());//����---�����������
        mtabHost.setCurrentTabByTag("two"); //����Ĭ��Ŀ¼
    }


    /*
    * ����tab1�ı�ǩ������*/
    private TabHost.TabSpec tab1Spec() {
        TabHost.TabSpec tabSpec = mtabHost.newTabSpec("one");
        Intent intent = new Intent(MainTabActivity.this, MeActivity.class);
        tabSpec.setContent(intent);
        //�Զ������ñ�ǩ��View������
        tabSpec.setIndicator(getTab1View("�ҵ�", R.drawable.tab_main_nav_me_selector));//tab1(�ҵĶ���)
        return tabSpec;
    }

    /*
    * ��ȡtab1������ǩ��View*/
    private View getTab1View(String tabTitle, int iconId) {
        View view = inflater.inflate(R.layout.tab_main_nav, null);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.ivIcon);
        TextView  tv_title= (TextView) view.findViewById(R.id.tvTitle);
        iv_icon.setImageResource(iconId);//tabNav��ͼƬ
        tv_title.setText(tabTitle);
        return view;
    }


    /*
     * ����tab2�ı�ǩ������*/
    private TabHost.TabSpec tab2Spec() {
        TabHost.TabSpec tabSpec = mtabHost.newTabSpec("two");
        Intent intent = new Intent(MainTabActivity.this, NewBookActivity.class);
        tabSpec.setContent(intent);
        //�Զ������ñ�ǩ��View������
        tabSpec.setIndicator(getTab2View("����", R.drawable.tab_main_nav_home_selector));//tab2(����)
        return tabSpec;
    }

    /*
    * ��ȡtab2������ǩ��View*/
    private View getTab2View(String tabTitle, int iconId) {
        View view = inflater.inflate(R.layout.tab_main_nav, null);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.ivIcon);
        iv_icon.setBackground(getResources().getDrawable(iconId));
//        iv_icon.setImageResource(iconId);//tabNav��ͼƬ
        TextView  tv_title= (TextView) view.findViewById(R.id.tvTitle);
        iv_icon.setImageResource(iconId);//tabNav��ͼƬ
        tv_title.setText(tabTitle);
        return view;
    }


    /*
     * ����tab3�ı�ǩ������*/
    private TabHost.TabSpec tab3Spec() {
        TabHost.TabSpec tabSpec = mtabHost.newTabSpec("three");
        Intent intent = new Intent(MainTabActivity.this, TestActivity1.class);
        tabSpec.setContent(intent);
        //�Զ������ñ�ǩ��View������
        tabSpec.setIndicator(getTab3View("����", R.drawable.detail_comment_selected));//tab3(����)
        return tabSpec;
    }

    /*
    * ��ȡtab3������ǩ��View*/
    private View getTab3View(String tabTitle, int iconId) {
        View view = inflater.inflate(R.layout.tab_main_nav, null);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.ivIcon);
        TextView  tv_title= (TextView) view.findViewById(R.id.tvTitle);
        iv_icon.setImageResource(iconId);//tabNav��ͼƬ
        tv_title.setText(tabTitle);
        return view;
    }


    /*
 * ����tab4�ı�ǩ������*/
    private TabHost.TabSpec tab4Spec() {
        TabHost.TabSpec tabSpec = mtabHost.newTabSpec("four");

        Intent intent = new Intent(MainTabActivity.this, SearchActivity.class);
//        Intent intent = new Intent(MainTabActivity.this, TestActivity1.class);
        tabSpec.setContent(intent);
        //�Զ������ñ�ǩ��View������
        tabSpec.setIndicator(getTab4View("����", R.drawable.tab_main_nav_search_selector));//tab4(����)
        return tabSpec;
    }

    /*
    * ��ȡtab4������ǩ��View*/
    private View getTab4View(String tabTitle, int iconId) {
        View view = inflater.inflate(R.layout.tab_main_nav, null);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.ivIcon);
        TextView  tv_title= (TextView) view.findViewById(R.id.tvTitle);
        iv_icon.setImageResource(iconId);//tabNav��ͼƬ
        tv_title.setText(tabTitle);
        return view;
    }


    /*
    * ����tab5�ı�ǩ������*/
    private TabHost.TabSpec tab5Spec() {
        TabHost.TabSpec tabSpec = mtabHost.newTabSpec("five");
        Intent intent = new Intent(MainTabActivity.this, AboutActivity.class);
//        Intent intent = new Intent(MainTabActivity.this, BookshelfActivity.class);
        tabSpec.setContent(intent);
        //�Զ������ñ�ǩ��View������
        tabSpec.setIndicator(getTab5View("����", R.drawable.tab_main_nav_fav_selector));//tab5(����)
        return tabSpec;
    }

    /*
    * ��ȡtab5������ǩ��View*/
    private View getTab5View(String tabTitle, int iconId) {
        View view = inflater.inflate(R.layout.tab_main_nav, null);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.ivIcon);
        TextView  tv_title= (TextView) view.findViewById(R.id.tvTitle);
        iv_icon.setImageResource(iconId);//tabNav��ͼƬ
        tv_title.setText(tabTitle);
        return view;
    }

    /**����˵�*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main_tab_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**�������˵������SharedPreferences*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_tab_menu_clear_user:
                System.out.println("clear");
                SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
//                editor.putString("accesstoken", "");
//                editor.putString("refreshtoken", "");
//                editor.putString("userid", "");


                //���ڲ���ֱ���õ�code��������һ�±�
                editor.putString("username","");
                editor.putString("userpwd","");
                editor.commit();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * ʵ�ֻ���Ч��*/


}


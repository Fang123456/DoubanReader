package com.k.douban;

import android.app.Activity;
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

        inflater = inflater.from(this);//初始化填充器
        mtabHost = (TabHost) findViewById(android.R.id.tabhost);
        mtabHost.setup();   //初始化tabHost，并填充tabs
        mtabHost.addTab(tab1Spec());//我的豆瓣
        mtabHost.addTab(tab2Spec());//新书
        mtabHost.addTab(tab3Spec());//书评
        mtabHost.addTab(tab4Spec());//搜索
        mtabHost.addTab(tab5Spec());//关于
        mtabHost.setCurrentTabByTag("three"); //设置默认目录
    }


    /*
    * 设置tab1的标签和内容*/
    private TabHost.TabSpec tab1Spec() {
        TabHost.TabSpec tabSpec = mtabHost.newTabSpec("one");
        Intent intent = new Intent(MainTabActivity.this, MeActivity.class);
        tabSpec.setContent(intent);
        //自定义设置标签（View）内容
        tabSpec.setIndicator(getTab1View("我的", R.drawable.tab_main_nav_me_selector));//tab1(我的豆瓣)
        return tabSpec;
    }

    /*
    * 获取tab1导航标签的View*/
    private View getTab1View(String tabTitle, int iconId) {
        View view = inflater.inflate(R.layout.tab_main_nav, null);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.ivIcon);
        TextView  tv_title= (TextView) view.findViewById(R.id.tvTitle);
        iv_icon.setImageResource(iconId);//tabNav的图片
        tv_title.setText(tabTitle);
        return view;
    }


    /*
     * 设置tab2的标签和内容*/
    private TabHost.TabSpec tab2Spec() {
        TabHost.TabSpec tabSpec = mtabHost.newTabSpec("two");
        Intent intent = new Intent(MainTabActivity.this, NewBookActivity.class);
        tabSpec.setContent(intent);
        //自定义设置标签（View）内容
        tabSpec.setIndicator(getTab2View("新书", R.drawable.tab_main_nav_home_selector));//tab2(新书)
        return tabSpec;
    }

    /*
    * 获取tab2导航标签的View*/
    private View getTab2View(String tabTitle, int iconId) {
        View view = inflater.inflate(R.layout.tab_main_nav, null);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.ivIcon);
        iv_icon.setBackground(getResources().getDrawable(iconId));
//        iv_icon.setImageResource(iconId);//tabNav的图片
        TextView  tv_title= (TextView) view.findViewById(R.id.tvTitle);
        iv_icon.setImageResource(iconId);//tabNav的图片
        tv_title.setText(tabTitle);
        return view;
    }


    /*
     * 设置tab3的标签和内容*/
    private TabHost.TabSpec tab3Spec() {
        TabHost.TabSpec tabSpec = mtabHost.newTabSpec("three");
        Intent intent = new Intent(MainTabActivity.this, TestActivity1.class);
        tabSpec.setContent(intent);
        //自定义设置标签（View）内容
        tabSpec.setIndicator(getTab3View("书评", R.drawable.detail_comment_selected));//tab3(书评)
        return tabSpec;
    }

    /*
    * 获取tab3导航标签的View*/
    private View getTab3View(String tabTitle, int iconId) {
        View view = inflater.inflate(R.layout.tab_main_nav, null);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.ivIcon);
        TextView  tv_title= (TextView) view.findViewById(R.id.tvTitle);
        iv_icon.setImageResource(iconId);//tabNav的图片
        tv_title.setText(tabTitle);
        return view;
    }


    /*
 * 设置tab4的标签和内容*/
    private TabHost.TabSpec tab4Spec() {
        TabHost.TabSpec tabSpec = mtabHost.newTabSpec("four");

        Intent intent = new Intent(MainTabActivity.this, SearchActivity.class);
//        Intent intent = new Intent(MainTabActivity.this, TestActivity1.class);
        tabSpec.setContent(intent);
        //自定义设置标签（View）内容
        tabSpec.setIndicator(getTab4View("搜索", R.drawable.tab_main_nav_search_selector));//tab4(搜索)
        return tabSpec;
    }

    /*
    * 获取tab4导航标签的View*/
    private View getTab4View(String tabTitle, int iconId) {
        View view = inflater.inflate(R.layout.tab_main_nav, null);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.ivIcon);
        TextView  tv_title= (TextView) view.findViewById(R.id.tvTitle);
        iv_icon.setImageResource(iconId);//tabNav的图片
        tv_title.setText(tabTitle);
        return view;
    }


    /*
    * 设置tab5的标签和内容*/
    private TabHost.TabSpec tab5Spec() {
        TabHost.TabSpec tabSpec = mtabHost.newTabSpec("five");
        Intent intent = new Intent(MainTabActivity.this, AboutActivity.class);
        tabSpec.setContent(intent);
        //自定义设置标签（View）内容
        tabSpec.setIndicator(getTab5View("关于", R.drawable.tab_main_nav_fav_selector));//tab5(关于)
        return tabSpec;
    }

    /*
    * 获取tab5导航标签的View*/
    private View getTab5View(String tabTitle, int iconId) {
        View view = inflater.inflate(R.layout.tab_main_nav, null);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.ivIcon);
        TextView  tv_title= (TextView) view.findViewById(R.id.tvTitle);
        iv_icon.setImageResource(iconId);//tabNav的图片
        tv_title.setText(tabTitle);
        return view;
    }

    /**清除菜单*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main_tab_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**点击清除菜单，清除SharedPreferences*/
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


                //由于不能直接拿到code，所以作一下弊
                editor.putString("username","");
                editor.putString("userpwd","");
                editor.commit();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 实现滑动效果*/


}


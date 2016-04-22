package com.k.douban;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * Created by AAAAA on 2015/12/7.
 */
public class MeActivity extends Activity implements AdapterView.OnItemClickListener{
    private ListView mListView;
    private SharedPreferences sp;
    private ImageButton back_button;
//    private static final String[] arr = {"我读","我看","我听","我评","我的日记","我的资料","小组"};
    private static final String[] arr = {"我的收藏","我的日记","我的资料"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.meactivity);
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        mListView = (ListView) this.findViewById(R.id.melistview);

        mListView.setAdapter(new ArrayAdapter<String>(this, R.layout.me_item, R.id.fav_title, arr));
        mListView.setOnItemClickListener(this);
        back_button= (ImageButton) findViewById(R.id.back_button);
        back_button.setOnClickListener(mGoBack);

    }

    public View.OnClickListener mGoBack = new View.OnClickListener() {
        public void onClick(View v) {
            finish();
        }
    };
    /**
     * 监听点击事件*/
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(isUserAuthoroized()){
           System.out.println("yanzheng"+isUserAuthoroized());
            //进入到对应的界面
            switch (position) {
                case 0://我读
                    Intent  myReadIntent = new Intent(MeActivity.this,MyReadActivity.class);
                    startActivity(myReadIntent);
                    break;
//                case 1://我听
////                    Intent  myReadIntent = new Intent(MeActivity.this,MyReadActivity.class);
////                    startActivity(myReadIntent);
//                    break;
//                case 1://我评
//                    Intent  myReadIntent = new Intent(MeActivity.this,MyReadActivity.class);
//                    startActivity(myReadIntent);
//                    break;
                case 1://我的日记
                    Intent  myNoteIntent = new Intent(MeActivity.this,MyNoteActivity.class);
                    startActivity(myNoteIntent);
                    break;
                case 2://我的资料
                    Intent myInfoIntent = new Intent(MeActivity.this, MyInfoActivity.class);
                    startActivity(myInfoIntent);
                    break;
//
//                case 5://小组
//                    Intent myInfoIntent = new Intent(MeActivity.this, MyInfoActivity.class);
//                    startActivity(myInfoIntent);
//                    break;
            }
        }else{
            //定向到登陆界面
            Intent intent = new Intent(this,LoginInActivity.class);
            startActivity(intent);
        }

    }

    /**
     * 判断用户是否获取到了授权
     */
    private boolean isUserAuthoroized(){
        sp= getSharedPreferences("config", Activity.MODE_PRIVATE);
        String accesstoken = sp.getString("accesstoken", null);
        String refreshtoken = sp.getString("refreshtoken", null);
        String uid=sp.getString("userid",null);

        //由于无法手动拿到code，所以作弊一下(其实不该保存用户名和密码)
        String username=sp.getString("username",null);
        String userpwd=sp.getString("userpwd",null);
        System.out.println("username:"+username);
        System.out.println("userpwd:"+userpwd);

        if(accesstoken==null||refreshtoken==null||accesstoken.equals("")||refreshtoken.equals("")){
            return false;
        }else{
            //由于无法手动拿到code，所以作弊一下(其实不该保存用户名和密码)
            if(username==null||userpwd==null||username.equals("")||userpwd.equals(""))
                return false;
            return true;
        }

    }
}




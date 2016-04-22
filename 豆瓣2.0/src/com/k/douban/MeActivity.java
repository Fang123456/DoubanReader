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
//    private static final String[] arr = {"�Ҷ�","�ҿ�","����","����","�ҵ��ռ�","�ҵ�����","С��"};
    private static final String[] arr = {"�ҵ��ղ�","�ҵ��ռ�","�ҵ�����"};

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
     * ��������¼�*/
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(isUserAuthoroized()){
           System.out.println("yanzheng"+isUserAuthoroized());
            //���뵽��Ӧ�Ľ���
            switch (position) {
                case 0://�Ҷ�
                    Intent  myReadIntent = new Intent(MeActivity.this,MyReadActivity.class);
                    startActivity(myReadIntent);
                    break;
//                case 1://����
////                    Intent  myReadIntent = new Intent(MeActivity.this,MyReadActivity.class);
////                    startActivity(myReadIntent);
//                    break;
//                case 1://����
//                    Intent  myReadIntent = new Intent(MeActivity.this,MyReadActivity.class);
//                    startActivity(myReadIntent);
//                    break;
                case 1://�ҵ��ռ�
                    Intent  myNoteIntent = new Intent(MeActivity.this,MyNoteActivity.class);
                    startActivity(myNoteIntent);
                    break;
                case 2://�ҵ�����
                    Intent myInfoIntent = new Intent(MeActivity.this, MyInfoActivity.class);
                    startActivity(myInfoIntent);
                    break;
//
//                case 5://С��
//                    Intent myInfoIntent = new Intent(MeActivity.this, MyInfoActivity.class);
//                    startActivity(myInfoIntent);
//                    break;
            }
        }else{
            //���򵽵�½����
            Intent intent = new Intent(this,LoginInActivity.class);
            startActivity(intent);
        }

    }

    /**
     * �ж��û��Ƿ��ȡ������Ȩ
     */
    private boolean isUserAuthoroized(){
        sp= getSharedPreferences("config", Activity.MODE_PRIVATE);
        String accesstoken = sp.getString("accesstoken", null);
        String refreshtoken = sp.getString("refreshtoken", null);
        String uid=sp.getString("userid",null);

        //�����޷��ֶ��õ�code����������һ��(��ʵ���ñ����û���������)
        String username=sp.getString("username",null);
        String userpwd=sp.getString("userpwd",null);
        System.out.println("username:"+username);
        System.out.println("userpwd:"+userpwd);

        if(accesstoken==null||refreshtoken==null||accesstoken.equals("")||refreshtoken.equals("")){
            return false;
        }else{
            //�����޷��ֶ��õ�code����������һ��(��ʵ���ñ����û���������)
            if(username==null||userpwd==null||username.equals("")||userpwd.equals(""))
                return false;
            return true;
        }

    }
}




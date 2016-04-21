package com.k.douban;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private TextView versionNumber;
    private LinearLayout mLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash);
        mLinearLayout = (LinearLayout) this.findViewById(R.id.LinearLayout01);
        versionNumber = (TextView) this.findViewById(R.id.versionNumber);
        versionNumber.setText(getVersion());

        // �жϵ�ǰ����״̬�Ƿ����
        if(isNetWorkConnected()){
        //if(true){
            //splash ��һ������,����������
            AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
            aa.setDuration(2000);
            mLinearLayout.setAnimation(aa);
            mLinearLayout.startAnimation(aa);
            //ͨ��handler ��ʱ2�� ִ��r����
            new Handler().postDelayed(new LoadMainTabTask(),2000);
        }else{
            showSetNetworkDialog();
        }
    }

    /**
     * ��ת��������*/
    private class LoadMainTabTask implements Runnable{

        public void run() {
            Intent intent = new Intent(SplashActivity.this,MainTabActivity.class);
            startActivity(intent);
            finish();

        }

    }

    /**
     * ���������硱������*/
    private void showSetNetworkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("��������");
        builder.setMessage("���������������״̬");
        builder.setPositiveButton("��������", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //��ת��ϵͳ����
                //���ڰ汾��ͬ������д����ͬ��
                Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
                finish();

//                Intent intent = new Intent();
//                //����һ��Ҫ��������
//                intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
//                startActivity(intent);
//                finish();
            }
        });
        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();

    }

    /**
     * �õ��汾��Ϣ*/
    private String getVersion(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            return "Version " +info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "Version";
        }
    }

    /**
     * �ж�����״̬
     */
    private boolean isNetWorkConnected(){

//		WifiManager  wifimanager =  (WifiManager) getSystemService(WIFI_SERVICE);
//		wifimanager.isWifiEnabled();
//		wifimanager.getWifiState();

        //�õ�ϵͳ����������Ϣ
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}


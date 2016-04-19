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

        // 判断当前网络状态是否可用
        if(isNetWorkConnected()){
        //if(true){
            //splash 做一个动画,进入主界面
            AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
            aa.setDuration(2000);
            mLinearLayout.setAnimation(aa);
            mLinearLayout.startAnimation(aa);
            //通过handler 延时2秒 执行r任务
            new Handler().postDelayed(new LoadMainTabTask(),2000);
        }else{
            showSetNetworkDialog();
        }
    }

    /**
     * 跳转到主界面*/
    private class LoadMainTabTask implements Runnable{

        public void run() {
            Intent intent = new Intent(SplashActivity.this,MainTabActivity.class);
            startActivity(intent);
            finish();

        }

    }

    /**
     * “设置网络”弹出框*/
    private void showSetNetworkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置网络");
        builder.setMessage("网络错误请检查网络状态");
        builder.setPositiveButton("设置网络", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //跳转到系统设置
                //由于版本不同，所以写法不同。
                Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
                finish();

//                Intent intent = new Intent();
//                //类名一定要包含包名
//                intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
//                startActivity(intent);
//                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();

    }

    /**
     * 拿到版本信息*/
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
     * 判断网络状态
     */
    private boolean isNetWorkConnected(){

//		WifiManager  wifimanager =  (WifiManager) getSystemService(WIFI_SERVICE);
//		wifimanager.isWifiEnabled();
//		wifimanager.getWifiState();

        //拿到系统网络连接信息
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}


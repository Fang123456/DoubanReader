package com.k.douban;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by AAAAA on 2015/12/16.
 */
public class AboutActivity extends Activity{
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
        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(2000);
        mLinearLayout.setAnimation(aa);
        mLinearLayout.startAnimation(aa);
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

}

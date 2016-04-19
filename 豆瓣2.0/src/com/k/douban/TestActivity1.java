package com.k.douban;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Created by AAAAA on 2015/12/10.
 */
public class TestActivity1 extends Activity implements View.OnClickListener{
    private EditText search_text;
    private ImageButton search_button;
    private ImageButton back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_content);
        search_text = (EditText) findViewById(R.id.search_text);
        search_button = (ImageButton) findViewById(R.id.search_button);
        back_button = (ImageButton) findViewById(R.id.back_button);
        search_button.setOnClickListener(search);
        back_button.setOnClickListener(mGoBack);
    }

    public View.OnClickListener search = new View.OnClickListener() {
        public void onClick(View v) {
            String key = search_text.getText().toString();
            //跳轉到搜索頁面
        }
    };

////    @Override
////    public void onClick(View view) {
////
////    }
//
//    public View.OnClickListener mGoBack = new View.OnClickListener() {
//        public void onClick(View v) {
//            finish();
//        }
//    };

    @Override
    public void onClick(View view) {

    }
}

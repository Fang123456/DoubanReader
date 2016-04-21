package com.k.douban;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AAAAA on 2015/12/10.
 * 做一个线程，每次搜索栏改变就调用一次线程
 */
public class TestActivity1 extends Activity{
    private EditText search_text;
    private ImageButton search_button;
    private ImageButton back_button;
//
//    Handler myhandler = new Handler();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.search_content);
//
//        set_esearch_textChanged();
//
//        set_search_button_OnClick();
//
//        set_mListView_adapter();
//    }
//    private void set_mListView_adapter()
//    {
//        mListView = (ListView) findViewById(R.id.mListView);
//
//        getmData(mData);
//
//        adapter = new SimpleAdapter(this,mData,android.R.layout.simple_list_item_2,
//                new String[]{"title","text"},new int[]{android.R.id.text1,android.R.id.text2});
//
//        mListView.setAdapter(adapter);
//    }
//
//
//    private void set_esearch_textChanged()
//    {
//        search_text = (EditText) findViewById(R.id.search_text);
//
//        search_text.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                // TODO Auto-generated method stub
//                //改变时
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//                                          int arg3) {
//                // TODO Auto-generated method stub
//                //改变前
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//                /**改变后
//                 */
//                if(s.length() == 0){
//                    search_button.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
//                }
//                else {
//                    search_button.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉
//                }
//
//                myhandler.post(eChanged);
//            }
//        });
//
//    }
//
//
//
//    Runnable eChanged = new Runnable() {
//
//        @Override
//        public void run() {
//            // TODO Auto-generated method stub
//            String data = search_text.getText().toString();
//
//            mData.clear();//???????????????
//
//            getmDataSub(mData, data);//???????????
//
//            adapter.notifyDataSetChanged();//????
//
//        }
//    };
//
//    /**
//     * ?????????????????data????????????????????????????mDataSubs??
//     * @param mDataSubs
//     * @param data
//     */
//
//    private void getmDataSub(ArrayList<Map<String, Object>> mDataSubs, String data)
//    {
//        int length = mListTitle.size();
//        for(int i = 0; i < length; ++i){
//            if(mListTitle.get(i).contains(data) || mListText.get(i).contains(data)){
//                Map<String,Object> item = new HashMap<String,Object>();
//                item.put("title", mListTitle.get(i));
//                item.put("text",  mListText.get(i));
//                mDataSubs.add(item);
//            }
//        }
//    }
//
//    /**
//     * ???貌??????????????????
//     */
//
//    private void set_search_button_OnClick()
//    {
//        search_button = (ImageButton) findViewById(R.id.search_button);
//        search_button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                //
//                search_text.setText("");
//            }
//        });
//    }
//
//    /**
//     * ???????? ???????mDatas
//     * @param mDatas
//     */
//
//    private void getmData(ArrayList<Map<String, Object>> mDatas)
//    {
//        Map<String, Object> item = new HashMap<String, Object>();
//        mListTitle.add("???????????!");
//        mListText.add("???????.\n2014.09.18.19.50");
//
//        item.put("title", mListTitle.get(0));
//        item.put("text", mListText.get(0));
//        mDatas.add(item);
//
//
//        mListTitle.add("?????????????!");
//        mListText.add("????????????.\n2014.09.18.19.51");
//
//        item = new HashMap<String, Object>();
//        item.put("title", mListTitle.get(1));
//        item.put("text", mListText.get(1));
//        mDatas.add(item);
//    }
}


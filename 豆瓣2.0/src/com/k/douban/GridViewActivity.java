package com.k.douban;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.k.douban.domain.NewBook;
import com.k.douban.util.NetUtil;
//import cn.itcast.douban.domain.NewBook;
//import cn.itcast.douban.util.LoadImageAsynTask;
//import cn.itcast.douban.util.NetUtil;
//import cn.itcast.douban.util.LoadImageAsynTask.LoadImageAsynTaskCallback;

public class GridViewActivity extends BaseActivity {

	private GridView subjectGrid;
	List<NewBook> newbooks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.subjectgrid);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void setupView() {
		mRelativeLoading = (RelativeLayout) this.findViewById(R.id.loading);
		subjectGrid = (GridView) this.findViewById(R.id.subjectgrid);

	}

	@Override
	public void setListener() {

	}

	@Override
	public void fillData() {

		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {

				try {
					newbooks = NetUtil.getNewBooks(getApplicationContext());
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}

			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoading();
			}

			@Override
			protected void onPostExecute(Boolean result) {
				hideLoading();
				super.onPostExecute(result);
				if (result) {
					// 设置数据适配器
					MyGridViewAdapter adapter = new MyGridViewAdapter();
					subjectGrid.setAdapter(adapter);
				} else {
					showToast("数据获取 失败,请检查网络");
				}
			}

		}.execute();

	}

	private class MyGridViewAdapter extends BaseAdapter {

		public int getCount() {
			int size = newbooks.size();
			if (size % 3 == 0) {
				return size / 3;
			} else {
				return (size / 3) + 1;
			}
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout ll = new LinearLayout(GridViewActivity.this);
			ll.setOrientation(LinearLayout.HORIZONTAL);
			if(position%3==0){
				ll.setBackgroundColor(Color.RED);
			}else if(position%3==1){
				ll.setBackgroundColor(Color.YELLOW);
			}else if(position%3==2){
				ll.setBackgroundColor(Color.GRAY);
			}
			
			int baseindex = position * 3;
			TextView tv_book1 = new TextView(GridViewActivity.this);

			tv_book1.setText(newbooks.get(baseindex).getName());
			ll.addView(tv_book1);
			
			TextView tv_book2 = new TextView(GridViewActivity.this);

			if(baseindex + 1 < newbooks.size()){
			tv_book2.setText(newbooks.get(baseindex + 1).getName());

			ll.addView(tv_book2);
			}
			TextView tv_book3 = new TextView(GridViewActivity.this);


			if(baseindex + 2 < newbooks.size()){
			tv_book3.setText(newbooks.get(baseindex + 2).getName());

			ll.addView(tv_book3);
			
			}




			return ll;
		}

	}

}

package com.k.douban;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;

//import cn.itcast.douban.domain.NewBook;
//import cn.itcast.douban.util.LoadImageAsynTask;
//import cn.itcast.douban.util.LoadImageAsynTask.LoadImageAsynTaskCallback;
//import cn.itcast.douban.util.NetUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import com.k.douban.domain.Book;
import com.k.douban.domain.NewBook;
import com.k.douban.util.LoadImageAsynTask;
import com.k.douban.util.NetUtil;

public class NewBookActivity extends BaseActivity implements AdapterView.OnItemClickListener {

	private ListView subjectlist;
	Map<String, SoftReference<Bitmap>> iconCache;
	List<NewBook> newbooks;
	NewBookAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.subject);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void setupView() {
		mRelativeLoading = (RelativeLayout) this.findViewById(R.id.loading);
		subjectlist = (ListView) this.findViewById(R.id.subjectlist);

	}

	@Override
	public void setListener() {
		subjectlist.setOnItemClickListener(this);
	}

	@Override
	public void fillData() {

		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {

				try {
					System.out.println("调用book的方法");
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
				if(result){
					//设置数据适配器 
					if(adapter==null){
						adapter = new NewBookAdapter();
						subjectlist.setAdapter(adapter);
					}else{
						adapter.notifyDataSetChanged();
					}
				}else{
					showToast("数据获取 失败,请检查网络");
				}
			}

		}.execute();

	}

	private class NewBookAdapter extends BaseAdapter {

		public int getCount() {
			
			return newbooks.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return newbooks.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			NewBook newbook = newbooks.get(position);
			View view;
			if(convertView==null){
				view = View.inflate(NewBookActivity.this, R.layout.new_book_item, null);
			}else{
				view = convertView;
			}
			final ImageView iv = new ImageView(NewBookActivity.this);
			//RatingBar rb = new RatingBar(NewBookActivity.this);
			  RatingBar rb = new RatingBar(NewBookActivity.this, null, android.R.attr.ratingBarStyleSmall);
			rb.setMax(5);
			rb.setProgress(4);
		
			LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_book_image);
			//清空ll的里面的view对象
			ll.removeAllViews();
			
			ll.addView(iv, new LayoutParams(60, 60));
			ll.addView(rb,new LayoutParams(60, LayoutParams.WRAP_CONTENT));
			TextView tv_title = (TextView) view.findViewById(R.id.book_title);
			TextView tv_description = (TextView) view
					.findViewById(R.id.book_description);
			
			rb.setRating(4.0f);
			tv_title.setText(newbook.getName());
			tv_description.setText(newbook.getDescription());
			SharedPreferences  sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			boolean canloadicon = sp.getBoolean("canloadicon", false);
			if(canloadicon){
			LoadImageAsynTask task = new LoadImageAsynTask(new LoadImageAsynTask.LoadImageAsynTaskCallback() {
				
				public void beforeLoadImage() {
					System.out.println("beforeLoadImage");
					iv.setImageResource(R.drawable.book);
				}
				
				public void afterLoadImage(Bitmap bitmap) {
					System.out.println("afterLoadImage");
					if(bitmap!=null){
						iv.setImageBitmap(bitmap);
					}else{
						iv.setImageResource(R.drawable.book);
					}
					
				}
			});
			task.execute(newbook.getIconpath());
			}else{
				iv.setImageResource(R.drawable.book);
			}
			return view;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "设置界面");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this,SettingActivity.class);
		startActivity(intent);
		return super.onOptionsItemSelected(item);
	}


	//点击每个条目所对应的点击事件
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		System.out.println("点击条目");
		NewBook newbook =(NewBook) subjectlist.getItemAtPosition(position);
		System.out.println("就发觉看法哈的vnzcv"+newbook.getId());
		Intent intent = new Intent(this,BookDetailActivity.class);
		intent.putExtra("SubjectId", newbook.getId());
		startActivity(intent);

	}



}

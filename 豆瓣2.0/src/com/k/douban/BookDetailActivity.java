package com.k.douban;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import net.htmlparser.jericho.Source;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BookDetailActivity extends Activity {
	TextView tv_title;
	TextView tv_summary;
	LinearLayout ll_loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_detail);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_summary = (TextView) this.findViewById(R.id.tv_summary);
		ll_loading = (LinearLayout) this.findViewById(R.id.ll_book_detail);
		String SubjectId = getIntent().getStringExtra("SubjectId");
		fillData(SubjectId);
		
	}

	private void fillData(String isbn) {
		new AsyncTask<String, Void, Boolean>() {
			String title;
			String summary;
			String price;
			@Override
			protected Boolean doInBackground(String... params) {
				// http://api.douban.com/book/subject/isbn/7543632608?alt=json
				String path = "http://api.douban.com/book/subject/" + params[0] + "?alt=json";
			
				try {
					URL url = new URL(path);
					Source source = new Source(url.openConnection());
					String jsonstr = source.toString();
					JSONObject jsonobj = new JSONObject(jsonstr);
					String titlestr = jsonobj.get("title").toString();
					JSONObject jsontitle = new JSONObject(titlestr);
					title = jsontitle.get("$t").toString();
					System.out.println(title);

					String summarystr = jsonobj.get("summary").toString();
					if (summarystr != null) {
						JSONObject jsonsummary = new JSONObject(summarystr);
						summary = jsonsummary.get("$t").toString();
						System.out.println(summary);
					}
					
					String attributestr = jsonobj.get("db:attribute").toString();
					JSONArray attributearray = new JSONArray(attributestr);
					for(int i=0;i<attributearray.length();i++){
						JSONObject jsonatt = new JSONObject( attributearray.get(i).toString());
						if("price".equals( (jsonatt.get("@name").toString()))){
							price = jsonatt.get("$t").toString();
							System.out.println(price);
						}
					}
					return true;

				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}

			}

			@Override
			protected void onPreExecute() {
				ll_loading.setVisibility(View.VISIBLE);
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(Boolean result) {
				ll_loading.setVisibility(View.INVISIBLE);
				if(result){
					tv_title.setText(title+"/"+price);
					tv_summary.setText(summary);
				}else{
					Toast.makeText(getApplicationContext(), "查看详情失败", Toast.LENGTH_SHORT).show();
				}
				super.onPostExecute(result);
			}

		}.execute(isbn);
	}
}

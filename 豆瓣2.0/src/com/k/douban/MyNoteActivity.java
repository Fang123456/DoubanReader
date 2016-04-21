package com.k.douban;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.*;
import com.google.gdata.data.TextContent;
//import com.google.gdata.data.douban.Attribute;
//import com.google.gdata.data.douban.NoteEntry;
//import com.google.gdata.data.douban.NoteFeed;
//import com.google.gdata.data.douban.UserEntry;
import com.google.gdata.util.ServiceException;

//import cn.itcast.douban.domain.Note;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import com.k.douban.domain.Note;
import douban.model.collection.DoubanCollectionObj;
import douban.model.note.DoubanNoteEntryObj;
import douban.model.note.DoubanNoteFeedObj;
import douban.playground.service.DoubanNoteServiceTest;

public class MyNoteActivity extends BaseActivity implements OnClickListener {
	public static final int NEW_NOTE=0;
	public static final int EDIT_NOTE=1;
	private ImageButton back_button;

	private ListView subjectlist;
	private Button bt_next, bt_pre;
	private int startindex = 1;
	private int count = 10;
	private boolean isloading=false;
	private SharedPreferences sharedPreferences;
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.my_note);
		super.onCreate(savedInstanceState);
		back_button= (ImageButton) findViewById(R.id.back_button);
		back_button.setOnClickListener(mGoBack);
	}

	public View.OnClickListener mGoBack = new View.OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};
	@Override
	public void setupView() {
		mRelativeLoading = (RelativeLayout) this.findViewById(R.id.loading);
		subjectlist = (ListView) this.findViewById(R.id.subjectlist);
//	    subjectlist.setEmptyView();//当数据为空的时候所显示的内容
		bt_next = (Button) this.findViewById(R.id.bt_next);
		bt_pre = (Button) this.findViewById(R.id.bt_pre);

	}

	@Override
	public void setListener() {
		bt_next.setOnClickListener(this);
		bt_pre.setOnClickListener(this);
//		subjectlist.setOnItemClickListener(new OnItemClickListener() {
//
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//
//
//			}
//		});
		registerForContextMenu(subjectlist);
		

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	  super.onCreateContextMenu(menu, v, menuInfo);
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {

	  AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	  int position =  (int) info.id;
		Note note = (Note) subjectlist.getItemAtPosition(position);
		DoubanNoteEntryObj entry = note.getNoteEntry();
	  switch (item.getItemId()) {
	  case R.id.menu_add_note:
		    Intent intent = new Intent(this,NewNoteActivity.class);
		    startActivityForResult(intent, NEW_NOTE);
	        return true;
	  case R.id.menu_delete_note:
		  	deleteNote(entry);
		    return true;
	  case R.id.menu_edit_note:
		  	Intent editintent = new Intent(this,NewNoteActivity.class);
		    // 需要通知 NewNoteActivity 操作是一个编辑日记的操作
		    editintent.putExtra("iseditnote", true);
		    MyApp myapp = (MyApp) getApplication();
		    myapp.note = note;
		    startActivityForResult(editintent, EDIT_NOTE);
		    return true;
	  }
	  return super.onContextItemSelected(item);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==200){
			startindex = 1;
			fillData();
			if(requestCode==NEW_NOTE){
				showToast("发表新日记成功,正在刷新页面");
			}else if(requestCode==EDIT_NOTE){
				showToast("更新日记成功,正在刷新页面");
			}
		}
	}

	/**
	 * 删除日记
	 * @param entry
	 */
	private void deleteNote(DoubanNoteEntryObj entry) {
		new AsyncTask<DoubanNoteEntryObj, Void, Boolean>(){

			@Override
			protected Boolean doInBackground(DoubanNoteEntryObj... params) {
			 	try {
					sharedPreferences= getSharedPreferences("config", Activity.MODE_PRIVATE);
					String accesstoken=sharedPreferences.getString("accesstoken", "");
					Note note=new Note();
					long noteid= Long.parseLong(note.getNoteid());
					DoubanNoteServiceTest doubanNoteServiceTest=new DoubanNoteServiceTest();
					boolean result=doubanNoteServiceTest.testDeleteNote(noteid,accesstoken);
//					myService.deleteNote( params[0]);
					return result;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			
			}

			@Override
			protected void onPreExecute() {
				pd = new ProgressDialog(MyNoteActivity.this);
				pd.setMessage("正在删除日记");
				pd.show();
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(Boolean result) {
				pd.dismiss();
				if(result){
					fillData();
				}else{
					showToast("删除日记失败");
				}
				super.onPostExecute(result);
			}


		}.execute(entry);
		
	}

	@Override
	public void fillData() {
		if(isloading){
//			showToast("正在下载数据中");
			showToast("正在下载数据中");
			return;
		}
		
		new AsyncTask<Void, Void, List<Note>>() {

			@Override
			protected void onPreExecute() {
				showLoading();
				isloading = true;
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(List<Note> result) {
				hideLoading();
				super.onPostExecute(result);
				if (result != null) {
					// 设置到数据适配器里面
					MyNoteAdapter adapter = new MyNoteAdapter(result);
					subjectlist.setAdapter(adapter);
				} else {
					showToast("下载数据发生异常");
				}
				isloading =false;
			}

			@Override
			protected List<Note> doInBackground(Void... params) {
				//获取日记信息
				DoubanNoteServiceTest doubanNoteServiceTest=new DoubanNoteServiceTest();
				try {
					sharedPreferences= getSharedPreferences("config", Activity.MODE_PRIVATE);
					String userid=sharedPreferences.getString("userid", "");

					DoubanNoteFeedObj result=doubanNoteServiceTest.testGetAllNotesFromUser_3args(userid,startindex,count);
					List<Note> notes = new ArrayList<Note>();
					for (DoubanNoteEntryObj ne : result.getEntries()) {
						Note note = new Note();
						note.setNoteEntry(ne);
						if (ne.getContent() != null)
							note.setContent(ne.getContent());
						note.setNoteid(ne.getId().substring(ne.getId().lastIndexOf("/")+1,ne.getId().length()));
						note.setTitle(ne.getTitle());
//						for (Attribute attr : ne.getAttributes()) {
//
//							if ("can_reply".equals(attr.getName())) {
//								note.setCan_reply(attr.getContent());
//							} else if ("privacy".equals(attr.getName())) {
//								note.setCan_reply(attr.getContent());
//							}
//						}
						note.setPubdate(ne.getPublished().toString());
						notes.add(note);

						System.out.println("ne title : " + ne.getTitle());
						System.out.println("ne title : " + ne.getPublished());
					}
					return notes;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
//				try {
//					UserEntry ue = myService.getAuthorizedUser();
//					String uid = ue.getUid();
//					// 首先获取用户的 所有收集的信息
//					NoteFeed noteFeed = myService.getUserNotes(uid, startindex,
//							count);
//					List<Note> notes = new ArrayList<Note>();
//					for (NoteEntry ne : noteFeed.getEntries()) {
//						Note note = new Note();
//						note.setNoteEntry(ne);
//						if (ne.getContent() != null)
//
//							note.setContent(((TextContent) ne.getContent())
//									.getContent().getPlainText());
//
//						note.setTitle(ne.getTitle().getPlainText());
//
//						for (Attribute attr : ne.getAttributes()) {
//
//							if ("can_reply".equals(attr.getName())) {
//								note.setCan_reply(attr.getContent());
//							} else if ("privacy".equals(attr.getName())) {
//								note.setCan_reply(attr.getContent());
//							}
//						}
//						note.setPubdate(ne.getPublished().toString());
//						notes.add(note);
//					}
//					return notes;
//				} catch (Exception e) {
//					e.printStackTrace();
//					return null;
//				}
			}
		}.execute();

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_next:
			startindex+=count;
			if(startindex>100){
				showToast("最多获取100条");
				return ;
			}
			fillData();
			break;

		case R.id.bt_pre:
			if(startindex>11){
				startindex=startindex-count;
				fillData();
			}else{
				showToast("已经翻到了第一页");
			}
			break;
		}

	}

	private class MyNoteAdapter extends BaseAdapter {
		private List<Note> userNotes;

		public MyNoteAdapter(List<Note> userNotes) {
			this.userNotes = userNotes;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return userNotes.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return userNotes.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if (convertView == null) {

				view = View.inflate(getApplicationContext(), R.layout.note_item, null);
			}else{
				view = convertView;
			}
			TextView tv = (TextView) view.findViewById(R.id.fav_title);
			tv.setText(userNotes.get(position).getTitle());
			return view;

		}

	}
}

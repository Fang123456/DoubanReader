package com.k.douban;

import java.io.IOException;

import android.app.Activity;
import android.content.SharedPreferences;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.util.ServiceException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import com.k.douban.domain.Note;
import douban.playground.service.DoubanNoteServiceTest;

public class NewNoteActivity extends BaseActivity implements OnClickListener {
    EditText 	EditTextTitle;
    EditText  EditTextContent;
    Button btnSave,btnCancle;
    RadioButton rb1,rb2,rb3;
    CheckBox cb;
    ProgressDialog pd;
	private SharedPreferences sharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.note_edit);
		pd = new ProgressDialog(this);
		
		super.onCreate(savedInstanceState);
		boolean flag = getIntent().getBooleanExtra("iseditnote", false);
		if (flag) {
			// 编辑日记
			MyApp myapp = (MyApp) getApplication();
			Note note = myapp.note;
			EditTextTitle.setText(note.getTitle());
			EditTextContent.setText(note.getContent());
			String privacy = note.getPrivacy();
			if ("private".equals(privacy)) {
				rb1.setChecked(true);
			} else if ("friend".equals(privacy)) {
				rb2.setChecked(true);
			} else {
				rb3.setChecked(true);
			}
			String can_reply = note.getCan_reply();
			if ("yes".equals(can_reply)) {
				cb.setChecked(true);
			} else {
				cb.setChecked(false);
			}

		} else {
			// 新日记
		}
	}

	@Override
	public void setupView() {
		EditTextTitle = (EditText) this.findViewById(R.id.EditTextTitle);
		EditTextContent = (EditText) this.findViewById(R.id.EditTextContent);
		btnSave = (Button) this.findViewById(R.id.btnSave);
		btnCancle = (Button) this.findViewById(R.id.btnCancel);
		rb1 = (RadioButton) this.findViewById(R.id.rb_private);
		rb2 = (RadioButton) this.findViewById(R.id.rb_friend);
		rb3 = (RadioButton) this.findViewById(R.id.rb_public);
		cb = (CheckBox) this.findViewById(R.id.cb_can_reply);

	}

	@Override
	public void setListener() {
		btnSave.setOnClickListener(this);
		btnCancle.setOnClickListener(this);

	}

	@Override
	public void fillData() {
		
		
		

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCancel:
			finish();
			break;

		case R.id.btnSave:
			final String title = EditTextTitle.getText().toString();
			final String content = EditTextContent.getText().toString();
			String auth = null;
			if( rb1.isChecked()){
				auth = "private";
			}else if ( rb2.isChecked()){
				auth = "friend";
			}else {
				auth = "public";
			}
			String can_reply =null;
			if(cb.isChecked()){
				can_reply="yes";
			}else{
				can_reply ="no";
			}
			new AsyncTask<String, Void, Boolean>(){

				@Override
				protected Boolean doInBackground(String... params) {
					try {
						boolean isPrivate=true,canReply=true;
						if (params[0].equals("public")){
							isPrivate=false;
						}
						if (params[1].equals("no")){
							canReply=false;
						}
						// 判断 是发表新日记 还是更新日记
						boolean flag = getIntent().getBooleanExtra("iseditnote", false);
						sharedPreferences= getSharedPreferences("config", Activity.MODE_PRIVATE);
						String accesstoken=sharedPreferences.getString("accesstoken", "");

						if (flag) {

							MyApp myapp = (MyApp) getApplication();
							Note note = myapp.note;
							long upnoteid= Long.parseLong(note.getNoteid());
							String uptitle=note.getTitle();
							String upcontent=note.getContent();
							DoubanNoteServiceTest doubanNoteServiceTest = new DoubanNoteServiceTest();
							doubanNoteServiceTest.testUpdateNote(upnoteid,uptitle,upcontent,isPrivate,canReply,accesstoken);


//							myService.updateNote(note.getNoteEntry(),
//									new PlainTextConstruct(title),
//									new PlainTextConstruct(content), params[0], params[1]);
						} else {

							DoubanNoteServiceTest doubanNoteServiceTest = new DoubanNoteServiceTest();
							doubanNoteServiceTest.testCreateNewNote(title, content, isPrivate, canReply,accesstoken);
							//myService.createNote(new PlainTextConstruct(title), new PlainTextConstruct(content), params[0], params[1]);
						}
						return true;
					} catch (Exception e) {
						return false;
					}
				}

				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					pd.show();
				}

				@Override
				protected void onPostExecute(Boolean result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					pd.dismiss();
					if(result){
						setResult(200);
						finish();

					}else{
						showToast("发表日记失败");
					}
				}

			}.execute(auth,can_reply);
			break;

		}
		
	}

}

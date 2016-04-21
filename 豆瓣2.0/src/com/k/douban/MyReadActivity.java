package com.k.douban;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.*;
//import com.google.gdata.data.douban.Attribute;
//import com.google.gdata.data.douban.CollectionEntry;
//import com.google.gdata.data.douban.CollectionFeed;
//import com.google.gdata.data.douban.Subject;
//import com.google.gdata.data.douban.SubjectEntry;
//import com.google.gdata.data.douban.UserEntry;

//import cn.itcast.douban.domain.Book;
//import cn.itcast.douban.util.LoadImageAsynTask;
//import cn.itcast.douban.util.LoadImageAsynTask.LoadImageAsynTaskCallback;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import com.k.douban.domain.Book;
import com.k.douban.util.LoadImageAsynTask;
import douban.model.collection.DoubanCollectionFeedObj;
import douban.model.collection.DoubanCollectionObj;
import douban.model.subject.DoubanSubjectObj;
import douban.playground.service.DoubanBookMovieMusicServiceTest;
import douban.playground.service.DoubanCollectionServiceTest;

public class MyReadActivity extends BaseActivity implements OnItemClickListener {
	private ListView subjectlist;
	private SharedPreferences sharedPreferences;
	private ImageButton  back_button;
	MyReadAdapter adapter;
	// Map<String,Bitmap> iconCache;
	Map<String, SoftReference<Bitmap>> iconCache;
	int startindex; // ��ʼ��ȡ���ݵ�id
	int count;
	int max = 100;//�������ļ�����Ŀ
	boolean isloading = false;
	IntentFilter filter;
	KillReceiver receiver ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.subject);
		super.onCreate(savedInstanceState);
		startindex = 1;
		count = 5;
		// ��ʼ���ڴ滺��
		iconCache = new HashMap<String, SoftReference<Bitmap>>();

		back_button= (ImageButton) findViewById(R.id.back_button);
		back_button.setOnClickListener(mGoBack);
		
	}

	@Override
	public void setupView() {
		mRelativeLoading = (RelativeLayout) this.findViewById(R.id.loading);
		subjectlist = (ListView) this.findViewById(R.id.subjectlist);
		filter = new IntentFilter();
		filter.addAction("kill_activity_action");
		receiver = new KillReceiver();
		this.registerReceiver(receiver, filter);
	}

	@Override
	public void setListener() {
		subjectlist.setOnItemClickListener(this);
		subjectlist.setOnScrollListener(new OnScrollListener() {

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
					case OnScrollListener.SCROLL_STATE_IDLE:
						// �����ǰ����״̬Ϊ��ֹ״̬
						// ����listview�������һ���û��ɼ�����Ŀ ���� ����listview������������������һ����Ŀ
						// ��ȡlistview�����һ���û��ɼ���Ŀ��λ��
						int positon = view.getLastVisiblePosition();
						System.out.println("���һ���ɼ���Ŀ��λ�� " + positon);
						int totalcount = adapter.getCount();
						System.out.println("listview ��Ŀ����Ŀ " + totalcount);
						if (positon == (totalcount - 1)) {// ����ǰ�����϶��������·�
							// ��ȡ���������
							startindex = startindex + count;
							if (startindex > max) {
								showToast("�����Ѿ����ص������Ŀ");
								return;
							}
							if (isloading) {
								return;
							}
							fillData();
						}

						break;

				}

			}

			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {

			}
		});
	}

	@Override
	public void fillData() {
		// ͨ���첽���� ��ȡ���� Ȼ����ʾ��������
		new AsyncTask<Void, Void, List<Book>>() {

			@Override
			protected void onPreExecute() {
				showLoading();
				isloading = true;
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(List<Book> result) {
				hideLoading();
				super.onPostExecute(result);
				if (result != null) {
					if (adapter == null) {
						adapter = new MyReadAdapter(result);
						subjectlist.setAdapter(adapter);
					} else {
						// ���»�ȡ�������� �ӵ�listview����������������
						// ֪ͨ�����������
						adapter.addMoreBook(result);
						// ֪ͨ������������������
						adapter.notifyDataSetChanged();
					}
				} else {
					showToast("��ȡ����ʧ��");
				}
				isloading = false;
			}

			@Override
			protected List<Book> doInBackground(Void... params) {
				//�õ�book��Ϣ
				DoubanCollectionServiceTest doubanCollectionServiceTest=new DoubanCollectionServiceTest();
				try {
					sharedPreferences=getSharedPreferences("config", Context.MODE_PRIVATE);
					String userid=sharedPreferences.getString("userid", "");
					List<Book> Bookes = new ArrayList<Book>();
					DoubanCollectionFeedObj result1=doubanCollectionServiceTest.testGetUsersCollection_8args(startindex, count, userid);
					for (DoubanCollectionObj col : result1.getCollections()) {
						Book Book = new Book();
						String title=col.getTitle();
						String updated=col.getUpdateTime();

						String subjectUrl=col.getLinks().get(1).getHref();
//						System.out.println("subjectUrl:"+subjectUrl);
						int start = subjectUrl.lastIndexOf("/")+1;
						int end=subjectUrl.length();
						String subjectId = subjectUrl.substring(start, end);
						String summary=col.getSummary();

						try {
							Long l_subjectId=Long.valueOf(subjectId);
							System.out.println("l_subjectId"+l_subjectId);
							DoubanBookMovieMusicServiceTest doubanBookMovieMusicServiceTest=new DoubanBookMovieMusicServiceTest();
							DoubanSubjectObj result= doubanBookMovieMusicServiceTest.testGetBookInfoById(l_subjectId);

							String title1=result.getTitle();
							String name=result.getAuthors().get(0).getName();
							String imageUrl=result.getLinks().get(2).getHref();
							String summary1=result.getSummary();
							float rating=result.getRating().getAverage();

							Book.setTitle1(title1);
							Book.setName(name);
							Book.setImageUrl(imageUrl);
							Book.setSummary1(summary1);
							Book.setRating(rating);

							System.out.println("1:"+title1);
							System.out.println("2:"+name);
							System.out.println("3:"+imageUrl);
							System.out.println("4:"+summary1);
							System.out.println("5:"+rating);


						Book.setTitle(title);
						Book.setUpdated(updated);
						Book.setSubjectId(subjectId);
						Book.setSummary(summary);

						System.out.println("A : " + title);
						System.out.println("B : " + updated);
						System.out.println("C : " + subjectId);
						System.out.println("D : " + summary);
						Bookes.add(Book);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					return Bookes;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}

//					List<Book> books = new ArrayList<Book>();
//					for (DoubanCollectionObj col : result.getCollections()) {
//						Book book = new Book();
//						String title = col.getTitle();
//						String id=col.getId();
////						String hhh= String.valueOf(col.getTags().get(0));
////						System.out.println("dngkjs"+hhh);
//						book.setId(id);
//						book.setName(title);
//						book.setDescription(col.getUpdateTime());
//
//						book.setRating((float) 7.5); //�ֶ����õġ�������
//						book.setBookurl("https://img3.doubanio.com/lpic/s28349261.jpg");//�ֶ���������
//						books.add(book);
//
////						System.out.println("col title : " + col.getTitle());
////						System.out.println("col title : " + col.getUpdateTime());
//					}
//					return books;
//				} catch (Exception e) {
//					e.printStackTrace();
//					return null;
//				}


//				try {
//					UserEntry ue = myService.getAuthorizedUser();
//					String uid = ue.getUid();
//					// ���Ȼ�ȡ�û��� �����ռ�����Ϣ
//					CollectionFeed feeds = myService.getUserCollections(uid,
//							"book", null, null, startindex, count);
//					List<Book> books = new ArrayList<Book>();
//					for (CollectionEntry ce : feeds.getEntries()) {
//						Subject  se = ce.getSubjectEntry();
//
//						if (se != null) {
//							Book book = new Book();
//							String title = se.getTitle().getPlainText();
//							book.setName(title);
//							StringBuilder sb = new StringBuilder();
//							for (Attribute attr : se.getAttributes()) {
//								if ("author".equals(attr.getName())) {
//									sb.append(attr.getContent());
//									sb.append("/");
//								} else if ("publisher".equals(attr.getName())) {
//									sb.append(attr.getContent());
//									sb.append("/");
//								} else if ("pubdate".equals(attr.getName())) {
//									sb.append(attr.getContent());
//									sb.append("/");
//								} else if ("isbn10".equals(attr.getName())) {
//									sb.append(attr.getContent());
//									sb.append("/");
//								}
//							}
//							book.setDescription(sb.toString());
//
//							Rating rating = se.getRating();
//							if (rating != null) {
//								book.setRating(rating.getAverage());
//
//							}
//							for (Link link : se.getLinks()) {
//								if ("image".equals(link.getRel())) {
//									book.setBookurl(link.getHref());
//								}
//							}
//							books.add(book);
//						}
//
//					}
//					return books;
//				} catch (Exception e) {
//					e.printStackTrace();
//					return null;
//				}
			}
		}.execute();
	}

	//���ÿ����Ŀ����Ӧ�ĵ���¼�
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		System.out.println("�����Ŀ");
		Book book =(Book) subjectlist.getItemAtPosition(position);
		Intent intent = new Intent(this,BookDetailActivity.class);
		intent.putExtra("SubjectId", book.getSubjectId());
		startActivity(intent);

	}

	private class MyReadAdapter extends BaseAdapter {

		private List<Book> books;

		public MyReadAdapter(List<Book> books) {
			this.books = books;
		}

		public void addMoreBook(List<Book> books) {
			for (Book book : books) {
				this.books.add(book);
			}
		}

		public int getCount() {

			return books.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return books.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(MyReadActivity.this, R.layout.book_item, null);
			final ImageView iv_book = (ImageView) view.findViewById(R.id.book_img);
			RatingBar rb = (RatingBar) view.findViewById(R.id.ratingbar);
			TextView tv_title = (TextView) view.findViewById(R.id.book_title);
			TextView tv_description = (TextView) view.findViewById(R.id.book_description);
			Book book = books.get(position);
			if (book.getRating() != 0) {
				rb.setRating(book.getRating());
			} else {
				rb.setVisibility(View.INVISIBLE);
			}
			tv_description.setText(book.getUpdated()+"\n"+book.getSummary());
			tv_title.setText(book.getTitle());
			// �ж� ͼƬ�Ƿ���sd���ϴ���
			String iconpath = book.getImageUrl();
//			System.out.println("iconpath"+iconpath);
//			System.out.println("book"+book);
			final String iconname = iconpath.substring(
					iconpath.lastIndexOf("/") + 1, iconpath.length());
			/*
			 * File file = new File("/sdcard/" + iconname);
			 * if (file.exists()) {
			 * iv_book.setImageURI(Uri.fromFile(file));
			 * System.out.println("ʹ��sd������"); } else {
			 */

			if (iconCache.containsKey(iconname)) {
				SoftReference<Bitmap> softref = iconCache.get(iconname);
				if (softref != null) {
					Bitmap bitmap = softref.get();
					if (bitmap != null) {
						System.out.println("ʹ���ڴ滺�� ");
						iv_book.setImageBitmap(bitmap);
					} else {
						loadimage(iv_book, book, iconname);
					}

				}

			} else {

				loadimage(iv_book, book, iconname);
			}
			return view;
		}

		private void loadimage(final ImageView iv_book, Book book,
				final String iconname) {
			LoadImageAsynTask task = new LoadImageAsynTask(
					new LoadImageAsynTask.LoadImageAsynTaskCallback() {
						public void beforeLoadImage() {

							iv_book.setImageResource(R.drawable.book);
						}

						public void afterLoadImage(Bitmap bitmap) {
							if (bitmap != null) {
								System.out.println("���ط�����ͼƬ");
								iv_book.setImageBitmap(bitmap);
								/*
								 * // ��bitmap��ŵ�sd���� try { File file = new
								 * File("/sdcard/" + iconname); FileOutputStream
								 * stream = new FileOutputStream( file);
								 * bitmap.compress(CompressFormat.JPEG, 100,
								 * stream); } catch (Exception e) {
								 * e.printStackTrace(); }
								 */
								// ��ͼƬ��ŵ��ڴ滺������
								iconCache.put(iconname,
										new SoftReference<Bitmap>(bitmap));

							} else {
								iv_book.setImageResource(R.drawable.book);
							}

						}
					});
			task.execute(book.getImageUrl());
		}

	}

	private class KillReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			
			iconCache = null;
			showToast("�ڴ治��activity�˳�");
			finish();
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}


	public View.OnClickListener mGoBack = new View.OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};
}

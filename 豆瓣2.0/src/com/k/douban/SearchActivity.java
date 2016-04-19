

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
import douban.model.subject.DoubanSubjectFeedObj;
import douban.model.subject.DoubanSubjectObj;
import douban.playground.service.DoubanBookMovieMusicServiceTest;
import douban.playground.service.DoubanCollectionServiceTest;

public class SearchActivity extends BaseActivity implements OnItemClickListener {
    private ListView subjectlist;
    private SharedPreferences sharedPreferences;
    private ImageButton  back_button;
    MyReadAdapter adapter;
    // Map<String,Bitmap> iconCache;
    Map<String, SoftReference<Bitmap>> iconCache;
    int startindex; // 开始获取内容的id
    int count;
    int max = 100;//设置最大的加载条目
    boolean isloading = false;
    IntentFilter filter;
    KillReceiver receiver ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.subject);
        super.onCreate(savedInstanceState);
        startindex = 1;
        count = 5;
        // 初始化内存缓存
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
                        // 如果当前滚动状态为静止状态
                        // 并且listview里面最后一个用户可见的条目 内容 等于listview数据适配器里面的最后一个条目
                        // 获取listview中最后一个用户可见条目的位置
                        int positon = view.getLastVisiblePosition();
                        System.out.println("最后一个可见条目的位置 " + positon);
                        int totalcount = adapter.getCount();
                        System.out.println("listview 条目的数目 " + totalcount);
                        if (positon == (totalcount - 1)) {// 代表当前界面拖动到了最下方
                            // 获取更多的数据
                            startindex = startindex + count;
                            if (startindex > max) {
                                showToast("数据已经加载到最大条目");
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
        // 通过异步任务 获取数据 然后显示到界面上
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
                        // 把新获取到的数据 加到listview的数据适配器里面
                        // 通知界面更新内容
                        adapter.addMoreBook(result);
                        // 通知数据适配器更新数据
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    showToast("获取数据失败");
                }
                isloading = false;
            }

            @Override
            protected List<Book> doInBackground(Void... params) {
                //拿到book信息
               // DoubanCollectionServiceTest doubanCollectionServiceTest=new DoubanCollectionServiceTest();
                DoubanBookMovieMusicServiceTest doubanBookMovieMusicServiceTest=new DoubanBookMovieMusicServiceTest();
                try {
                    sharedPreferences=getSharedPreferences("config", Context.MODE_PRIVATE);
                    String userid=sharedPreferences.getString("userid", "");
                    List<Book> Bookes = new ArrayList<Book>();
                    //DoubanCollectionFeedObj result1=doubanCollectionServiceTest.testGetUsersCollection_8args(startindex, count, userid);
                    String key="c语言";
                    DoubanSubjectFeedObj result1=doubanBookMovieMusicServiceTest.testSearchBook_String_String(key);
                    for (DoubanSubjectObj col : result1.getSubjects()) {
                        Book Book = new Book();
                        String title=col.getTitle();
                        //String updated=col.getUpdateTime();
                        String updated="2015-12-12";

                        String subjectUrl=col.getLinks().get(1).getHref();
//						System.out.println("subjectUrl:"+subjectUrl);
                        int start = subjectUrl.lastIndexOf("/")+1;
                        int end=subjectUrl.length();
                        String subjectId = subjectUrl.substring(start, end);
                        String summary=col.getSummary();

                        try {
                            Long l_subjectId=Long.valueOf(subjectId);
                            System.out.println("l_subjectId"+l_subjectId);
                            //DoubanBookMovieMusicServiceTest doubanBookMovieMusicServiceTest=new DoubanBookMovieMusicServiceTest();
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
            }
        }.execute();
    }

    //点击每个条目所对应的点击事件
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("点击条目");
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
            View view = View.inflate(SearchActivity.this, R.layout.book_item, null);
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
            // 判断 图片是否在sd卡上存在
            String iconpath = book.getImageUrl();
//			System.out.println("iconpath"+iconpath);
//			System.out.println("book"+book);
            final String iconname = iconpath.substring(
                    iconpath.lastIndexOf("/") + 1, iconpath.length());
			/*
			 * File file = new File("/sdcard/" + iconname);
			 * if (file.exists()) {
			 * iv_book.setImageURI(Uri.fromFile(file));
			 * System.out.println("使用sd卡缓存"); } else {
			 */

            if (iconCache.containsKey(iconname)) {
                SoftReference<Bitmap> softref = iconCache.get(iconname);
                if (softref != null) {
                    Bitmap bitmap = softref.get();
                    if (bitmap != null) {
                        System.out.println("使用内存缓存 ");
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
                                System.out.println("下载服务器图片");
                                iv_book.setImageBitmap(bitmap);
								/*
								 * // 把bitmap存放到sd卡上 try { File file = new
								 * File("/sdcard/" + iconname); FileOutputStream
								 * stream = new FileOutputStream( file);
								 * bitmap.compress(CompressFormat.JPEG, 100,
								 * stream); } catch (Exception e) {
								 * e.printStackTrace(); }
								 */
                                // 把图片存放到内存缓存里面
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
            showToast("内存不足activity退出");
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





//package com.k.douban;
//
//import java.lang.ref.SoftReference;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Bitmap;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.view.*;
//
//import android.widget.*;
//
//import com.k.douban.util.LoadImageAsynTask;
//import com.k.douban.util.NetUtil;
//import com.k.douban.domain.SearchBook
//
//
//public class SearchActivity extends BaseActivity implements AdapterView.OnItemClickListener{
//    private ListView subjectlist;
//    Map<String, SoftReference<Bitmap>> iconCache;
//    List<SearchBook> SearchBooks;
//    NewBookAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        setContentView(R.layout.search_content);
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public void setupView() {
//        mRelativeLoading = (RelativeLayout) this.findViewById(R.id.loading);
//        subjectlist = (ListView) this.findViewById(R.id.list);
//
//    }
//
//    @Override
//    public void setListener() {
//        subjectlist.setOnItemClickListener(this);
//    }
//
//    @Override
//    public void fillData() {
//
//        new AsyncTask<Void, Void, Boolean>() {
//
//            @Override
//            protected Boolean doInBackground(Void... params) {
//
//                try {
//                    SearchBooks = NetUtil.getNewBooks(getApplicationContext());
//                    return true;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return false;
//                }
//
//            }
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                showLoading();
//            }
//
//            @Override
//            protected void onPostExecute(Boolean result) {
//                hideLoading();
//                super.onPostExecute(result);
//                if(result){
//                    //设置数据适配器
//                    if(adapter==null){
//                        adapter = new NewBookAdapter();
//                        subjectlist.setAdapter(adapter);
//                    }else{
//                        adapter.notifyDataSetChanged();
//                    }
//                }else{
//                    showToast("数据获取 失败,请检查网络");
//                }
//            }
//
//        }.execute();
//
//    }
//
//    private class NewBookAdapter extends BaseAdapter {
//
//        public int getCount() {
//
//            return SearchBooks.size();
//        }
//
//        public Object getItem(int position) {
//            // TODO Auto-generated method stub
//            return SearchBooks.get(position);
//        }
//
//        public long getItemId(int position) {
//            // TODO Auto-generated method stub
//            return position;
//        }
//
//        public View getView(int position, View convertView, ViewGroup parent) {
//            SearchBooks searchBook = searchBook.get(position);
//            View view;
//            if(convertView==null){
//                view = View.inflate(SearchActivity.this, R.layout.new_book_item, null);
//            }else{
//                view = convertView;
//            }
//            final ImageView iv = new ImageView(SearchActivity.this);
//            //RatingBar rb = new RatingBar(NewBookActivity.this);
//            RatingBar rb = new RatingBar(SearchActivity.this, null, android.R.attr.ratingBarStyleSmall);
//            rb.setMax(5);
//            rb.setProgress(4);
//
//            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_book_image);
//            //清空ll的里面的view对象
//            ll.removeAllViews();
//
//            ll.addView(iv, new ViewGroup.LayoutParams(60, 60));
//            ll.addView(rb,new ViewGroup.LayoutParams(60, ViewGroup.LayoutParams.WRAP_CONTENT));
//            TextView tv_title = (TextView) view.findViewById(R.id.book_title);
//            TextView tv_description = (TextView) view
//                    .findViewById(R.id.book_description);
//
//            rb.setRating(4.0f);
//            tv_title.setText(searchBook.getName());
//            tv_description.setText(searchBook.getDescription());
//            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            boolean canloadicon = sp.getBoolean("canloadicon", false);
//            if(canloadicon){
//                LoadImageAsynTask task = new LoadImageAsynTask(new LoadImageAsynTask.LoadImageAsynTaskCallback() {
//
//                    public void beforeLoadImage() {
//                        System.out.println("beforeLoadImage");
//                        iv.setImageResource(R.drawable.book);
//                    }
//
//                    public void afterLoadImage(Bitmap bitmap) {
//                        System.out.println("afterLoadImage");
//                        if(bitmap!=null){
//                            iv.setImageBitmap(bitmap);
//                        }else{
//                            iv.setImageResource(R.drawable.book);
//                        }
//
//                    }
//                });
//                task.execute(searchBook.getIconpath());
//            }else{
//                iv.setImageResource(R.drawable.book);
//            }
//            return view;
//        }
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(0, 0, 0, "设置界面");
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent intent = new Intent(this,SettingActivity.class);
//        startActivity(intent);
//        return super.onOptionsItemSelected(item);
//    }
//
//
//    //点击每个条目所对应的点击事件
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        System.out.println("点击条目");
//        SearchBook searchBook =(searchBook) subjectlist.getItemAtPosition(position);
//        System.out.println("就发觉看法哈的vnzcv"+searchBook.getId());
//        Intent intent = new Intent(this,BookDetailActivity.class);
//        intent.putExtra("SubjectId", searchBook.getId());
//        startActivity(intent);
//
//    }
//}
////
//////	private List<Subject> books = new ArrayList<Subject>();
////////	private List<Subject> movies = new ArrayList<Subject>();
////////	private List<Subject> musics = new ArrayList<Subject>();
//////
//////	private ViewFlipper viewFlipper;
//////
//////	private GestureDetector mGestureDetector;
//////	private static final int SWIPE_MAX_OFF_PATH = 100;
//////
//////	private static final int SWIPE_MIN_DISTANCE = 100;
//////
//////	private static final int SWIPE_THRESHOLD_VELOCITY = 100;
//////
//////	private int bookIndex = 1;
//////	private int movieIndex = 1;
//////	private int musicIndex = 1;
//////	private int count = 10; // 每次获取数目
//////	private boolean isFilling = false; // 判断是否正在获取数据
//////	protected SubjectListAdapter bookListAdapter;
//////
//////	private int bookTotal; // 最大条目数
//////	private int movieTotal; // 最大条目数
//////	private int musicTotal; // 最大条目数
//////
//////	@Override
//////	public void onCreate(Bundle savedInstanceState) {
//////		super.onCreate(savedInstanceState);
//////		requestWindowFeature(Window.FEATURE_NO_TITLE);
//////
//////		setContentView(R.layout.search);
//////
//////		initView(R.id.search_book, R.string.book_search_hint, "book");
//////		initView(R.id.search_movie, R.string.movie_search_hint, "movie");
//////		initView(R.id.search_music, R.string.music_search_hint, "music");
//////
//////		viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
//////
//////		mGestureDetector = new GestureDetector(
//////				new GestureDetector.SimpleOnGestureListener() {
//////					public boolean onFling(MotionEvent e1, MotionEvent e2,
//////							float velocityX, float velocityY) {
//////						if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
//////							return false;
//////
//////						if ((e1.getX() - e2.getX()) > SWIPE_MIN_DISTANCE
//////								&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//////							viewFlipper.setInAnimation(AnimationUtils
//////									.loadAnimation(SearchActivity.this,
//////											R.anim.push_right_in));
//////							viewFlipper.setOutAnimation(AnimationUtils
//////									.loadAnimation(SearchActivity.this,
//////											R.anim.push_left_out));
//////							viewFlipper.showNext();
//////						} else if ((e2.getX() - e1.getX()) > SWIPE_MIN_DISTANCE
//////								&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//////							viewFlipper.setInAnimation(AnimationUtils
//////									.loadAnimation(SearchActivity.this,
//////											R.anim.push_left_in));
//////							viewFlipper.setOutAnimation(AnimationUtils
//////									.loadAnimation(SearchActivity.this,
//////											R.anim.push_right_out));
//////							viewFlipper.showPrevious();
//////						}
//////						return true;
//////					}
//////				});
//////
//////	}
//////
//////	private void initView(int layoutId, int hintId, final String cat) {
//////		final View searchView = findViewById(layoutId);
//////		EditText searchText = (EditText) searchView
//////				.findViewById(R.id.search_text);
//////		searchText.setHint(hintId);
//////		ImageButton searchButton = (ImageButton) searchView
//////				.findViewById(R.id.search_button);
//////		searchView.setTag(cat);
//////
//////		searchButton.setOnClickListener(new OnClickListener() {
//////			public void onClick(View view) {
//////				doSearch(searchView, cat);
//////			}
//////		});
//////
//////		TextView titleView = (TextView) searchView.findViewById(R.id.myTitle);
//////		if (Subject.BOOK.equals(cat)) {
//////			titleView.setText("图书搜索");
//////		}
//////
//////
//////		ListView listView = (ListView) searchView
//////				.findViewById(android.R.id.list);
//////		if (Subject.BOOK.equals(cat)) {
//////			listView.setOnItemClickListener(new OnItemClickListener() {
//////				public void onItemClick(AdapterView<?> parent, View view,
//////						int position, long id) {
//////					Intent i = new Intent(SearchActivity.this,
//////							SubjectViewActivity.class);
//////					Subject subject = books.get(position);
//////					i.putExtra("subject", subject);
//////					startActivity(i);
//////
//////				}
//////			});
//////		}
//////
//////		listView.setOnScrollListener(new OnScrollListener() {
//////
//////			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
//////
//////			}
//////
//////			public void onScrollStateChanged(AbsListView view, int scrollState) {
//////				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
//////					// 判断滚动到底部
//////					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
//////						loadRemnantListItem(searchView);
//////					}
//////				}
//////			}
//////		});
//////
//////	}
//////
//////	// 获取更多条目
//////	private void loadRemnantListItem(View searchView) {
//////		if (isFilling) {
//////			return;
//////		}
//////		String cat = (String) searchView.getTag();
//////		if (Subject.BOOK.equals(cat)) {
//////			bookIndex = bookIndex + count;
//////			if (bookIndex > bookTotal) {
//////				return;
//////			}
//////		} else if (Subject.MOVIE.equals(cat)) {
//////			movieIndex = movieIndex + count;
//////			if (movieIndex > movieTotal) {
//////				return;
//////			}
//////		} else if (Subject.MUSIC.equals(cat)) {
//////			musicIndex = musicIndex + count;
//////			if (musicIndex > musicTotal) {
//////				return;
//////			}
//////		}
//////		RelativeLayout loading = (RelativeLayout) searchView
//////				.findViewById(R.id.loading);
//////		LayoutParams lp = (LayoutParams) loading.getLayoutParams();
//////		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//////		loading.setLayoutParams(lp);
//////
//////		fillData(searchView, cat);
//////	}
//////
//////
//////	private void doSearch(final View searchView, final String cat) {
//////		EditText searchText = (EditText) searchView
//////				.findViewById(R.id.search_text);
//////
//////		String searchTitle = searchText.getText().toString();
//////		if ("".equals(searchTitle.trim())) {
//////			return;
//////		}
//////		if (Subject.BOOK.equals(cat)) {
//////			bookIndex = 1;
//////			bookListAdapter = null;
//////			books.clear();
//////		} else if (Subject.MOVIE.equals(cat)) {
//////			movieIndex = 1;
//////			movieListAdapter = null;
//////			movies.clear();
//////		} else if (Subject.MUSIC.equals(cat)) {
//////			musicIndex = 1;
//////			musicListAdapter = null;
//////			musics.clear();
//////		}
//////		fillData(searchView, cat);
//////	}
//////
//////	private void fillData(final View searchView, final String cat) {
//////		new AsyncTask<View, Void, SubjectFeed>() {
//////
//////			@Override
//////			protected SubjectFeed doInBackground(View... args) {
//////				View searchView = args[0];
//////				String cat = (String) searchView.getTag();
//////				EditText searchText = (EditText) searchView
//////						.findViewById(R.id.search_text);
//////
//////				String title = searchText.getText().toString();
//////				SubjectFeed feed = null;
//////				try {
//////					if (Subject.BOOK.equals(cat)) {
//////						feed = NetUtil.getDoubanService().findBook(title, "",
//////								bookIndex, count);
//////						bookTotal = feed.getTotalResults();
//////					} else if (Subject.MOVIE.equals(cat)) {
//////						feed = NetUtil.getDoubanService().findMovie(title, "",
//////								movieIndex, count);
//////						movieTotal = feed.getTotalResults();
//////					} else if (Subject.MUSIC.equals(cat)) {
//////						feed = NetUtil.getDoubanService().findMusic(title, "",
//////								musicIndex, count);
//////						musicTotal = feed.getTotalResults();
//////					}
//////				} catch (Exception e) {
//////					e.printStackTrace();
//////				}
//////				return feed;
//////			}
//////
//////			@Override
//////			protected void onPostExecute(SubjectFeed result) {
//////				super.onPostExecute(result);
//////				closeProgressBar(searchView);
//////				if (result != null) {
//////					ListView listView = (ListView) searchView
//////							.findViewById(android.R.id.list);
//////					if (Subject.BOOK.equals(cat)) {
//////						books.addAll(ConvertUtil.ConvertSubjects(result, cat));
//////						if (bookListAdapter == null) {
//////							bookListAdapter = new SubjectListAdapter(
//////									SearchActivity.this, listView, books);
//////							listView.setAdapter(bookListAdapter);
//////						} else {
//////							bookListAdapter.notifyDataSetChanged();
//////						}
//////					} else if (Subject.MOVIE.equals(cat)) {
//////						movies.addAll(ConvertUtil.ConvertSubjects(result, cat));
//////						if (movieListAdapter == null) {
//////							movieListAdapter = new SubjectListAdapter(
//////									SearchActivity.this, listView, movies);
//////							listView.setAdapter(movieListAdapter);
//////						} else {
//////							movieListAdapter.notifyDataSetChanged();
//////						}
//////					} else if (Subject.MUSIC.equals(cat)) {
//////						musics.addAll(ConvertUtil.ConvertSubjects(result, cat));
//////						if (musicListAdapter == null) {
//////							musicListAdapter = new SubjectListAdapter(
//////									SearchActivity.this, listView, musics);
//////							listView.setAdapter(musicListAdapter);
//////						} else {
//////							musicListAdapter.notifyDataSetChanged();
//////						}
//////					}
//////
//////				}
//////				isFilling = false;
//////			}
//////
//////			@Override
//////			protected void onPreExecute() {
//////				super.onPreExecute();
//////				isFilling = true;
//////				showProgressBar(searchView);
//////
//////			}
//////
//////		}.execute(searchView);
//////	}
//////
//////}

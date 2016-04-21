

package com.k.douban;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
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
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
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


/**
 * 点击button时候 调用线程 传入key*/
public class SearchActivity extends Activity implements OnItemClickListener {
    public TextView mTextViewTitle;//最上面的title
    public RelativeLayout mRelativeLoading; //加载条
    //	public DoubanService myService;
    public ImageButton mImageBack;
    private String key = null;
    private ListView subjectlist;
    private SharedPreferences sharedPreferences;
    private EditText search_text;
    private ImageButton back_button, search_button;
    MyReadAdapter adapter;
    // Map<String,Bitmap> iconCache;
    Map<String, SoftReference<Bitmap>> iconCache;
    int startindex; // 开始获取内容的id
    int count;
    int max = 100;//设置最大的加载条目
    boolean isloading = false;
    IntentFilter filter;
    KillReceiver receiver ;
    Handler myhandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.subject);
        setContentView(R.layout.search_content);
        super.onCreate(savedInstanceState);
        startindex = 1;
        count = 5;
        // 初始化内存缓存
        iconCache = new HashMap<String, SoftReference<Bitmap>>();
        back_button = (ImageButton) findViewById(R.id.back_button);
        search_button = (ImageButton) findViewById(R.id.search_button);
        search_text = (EditText) findViewById(R.id.search_text);
        back_button.setOnClickListener(mGoBack);
        search_button.setOnClickListener(mSearch);
    }


    public void setupView() {
        System.out.println("setupView()");
        mRelativeLoading = (RelativeLayout) this.findViewById(R.id.loading);
//        subjectlist = (ListView) this.findViewById(R.id.subjectlist);
        subjectlist = (ListView) this.findViewById(R.id.list);
        filter = new IntentFilter();
        filter.addAction("kill_activity_action");
        receiver = new KillReceiver();
        this.registerReceiver(receiver, filter);
    }


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


    public void fillData() {
        System.out.println("fillData()");
        // 通过异步任务 获取数据 然后显示到界面上
        new AsyncTask<Void, Void, List<Book>>() {
            @Override
            protected void onPreExecute() {
                System.out.println("onPreExecute()");
                showLoading();
                isloading = true;
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(List<Book> result) {
                System.out.println("onPostExecute(List<Book> result)");
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
                System.out.println("doInBackground(Void... params)");
                //拿到book信息
               // DoubanCollectionServiceTest doubanCollectionServiceTest=new DoubanCollectionServiceTest();
                DoubanBookMovieMusicServiceTest doubanBookMovieMusicServiceTest=new DoubanBookMovieMusicServiceTest();
                try {
                    sharedPreferences=getSharedPreferences("config", Context.MODE_PRIVATE);
                    String userid=sharedPreferences.getString("userid", "");
                    List<Book> Bookes = new ArrayList<Book>();
//                    Bookes.clear();
                    //DoubanCollectionFeedObj result1=doubanCollectionServiceTest.testGetUsersCollection_8args(startindex, count, userid);
//                    String key="c语言";
                    System.out.println("开始搜索");
                    DoubanSubjectFeedObj result1=doubanBookMovieMusicServiceTest.testSearchBook_String_String(key);
                    for (DoubanSubjectObj col : result1.getSubjects()) {
                        Book Book = new Book();
                        String title=col.getTitle();
                        System.out.println("title----------------------" + title);
                        //String updated=col.getUpdateTime();
                        String updated="2015-12-12";

                        String subjectUrl=col.getLinks().get(1).getHref();
						System.out.println("subjectUrl:"+subjectUrl);
                        String[] url = subjectUrl.split("/");
                        String subjectId = url[4];
//                        int start = subjectUrl.lastIndexOf("/")+1;
//                        int end=subjectUrl.length();
//                        String subjectId = subjectUrl.substring(start, end);
                        System.out.println("subjectId======================" + subjectId);
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

        public MyReadAdapter(List<Book> books) {this.books = books;}

        public void addMoreBook(List<Book> books) {
            for (Book book : books) {
                this.books.add(book);
            }
        }

        public int getCount() {return books.size();}

        public Object getItem(int position) {return books.get(position);}

        public long getItemId(int position) {return position;}

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

    public View.OnClickListener mSearch = new View.OnClickListener() {
        public void onClick(View v) {

            key = search_text.getText().toString();
            System.out.println("key=========" + key);
//            myhandler.post(eChanged);
//            clearList(Bookes);
            setupView();
            setListener() ;
            fillData();
//            search_text.setText(" ");
            //调用线程填充数据
//            adapter.notifyDataSetChanged();
        }
    };
    public void clearList(List<Book> f) {
        int size = f.size();
        if (size > 0) {
            f.removeAll(f);
            adapter.notifyDataSetChanged();
        }
    }

    Runnable eChanged = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub

        }
    };


    public  void showLoading(){
        mRelativeLoading.setVisibility(View.VISIBLE);
        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(1000);
        ScaleAnimation sa = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f);
        sa.setDuration(1000);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(sa);
        set.addAnimation(aa);
        mRelativeLoading.setAnimation(set);
        mRelativeLoading.startAnimation(set);
    }
    public  void hideLoading(){
        AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);
        aa.setDuration(1000);
        ScaleAnimation sa = new ScaleAnimation(1.0f, 0.0f, 1.0f,0.0f);
        sa.setDuration(1000);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(sa);
        set.addAnimation(aa);
        mRelativeLoading.setAnimation(set);
        mRelativeLoading.startAnimation(set);
        mRelativeLoading.setVisibility(View.INVISIBLE);
    }

    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}





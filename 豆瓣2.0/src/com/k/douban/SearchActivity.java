

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
 * ���buttonʱ�� �����߳� ����key*/
public class SearchActivity extends Activity implements OnItemClickListener {
    public TextView mTextViewTitle;//�������title
    public RelativeLayout mRelativeLoading; //������
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
    int startindex; // ��ʼ��ȡ���ݵ�id
    int count;
    int max = 100;//�������ļ�����Ŀ
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
        // ��ʼ���ڴ滺��
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


    public void fillData() {
        System.out.println("fillData()");
        // ͨ���첽���� ��ȡ���� Ȼ����ʾ��������
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
                System.out.println("doInBackground(Void... params)");
                //�õ�book��Ϣ
               // DoubanCollectionServiceTest doubanCollectionServiceTest=new DoubanCollectionServiceTest();
                DoubanBookMovieMusicServiceTest doubanBookMovieMusicServiceTest=new DoubanBookMovieMusicServiceTest();
                try {
                    sharedPreferences=getSharedPreferences("config", Context.MODE_PRIVATE);
                    String userid=sharedPreferences.getString("userid", "");
                    List<Book> Bookes = new ArrayList<Book>();
//                    Bookes.clear();
                    //DoubanCollectionFeedObj result1=doubanCollectionServiceTest.testGetUsersCollection_8args(startindex, count, userid);
//                    String key="c����";
                    System.out.println("��ʼ����");
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
//                        String summary=col.getSummary();
                        String summary=" ";

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
            //�����߳��������
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





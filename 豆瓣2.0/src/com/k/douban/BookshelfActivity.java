package com.k.douban;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Created by AAAAA on 2016/4/21.
 */

public class BookshelfActivity extends Activity {

    private ShelfAdapter mAdapter;
    private ListView shelf_list;

    @ Override
    public void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.bookshelf);
        init ();
        mAdapter = new ShelfAdapter ();//new adapter对象才能用
        shelf_list.setAdapter ( mAdapter );
    }

    private void init () {
        shelf_list = ( ListView ) findViewById ( R.id.shelf_list );
    }

    public class ShelfAdapter extends BaseAdapter {

        int[ ] size = new int[ 5 ];

        public ShelfAdapter () {

        }

        @ Override
        public int getCount () {

            if ( size.length > 3 ) {
                return size.length;
            } else {
                return 3;
            }

        }

        @ Override
        public Object getItem ( int position ) {

            return size[ position ];
        }

        @ Override
        public long getItemId ( int position ) {

            return position;
        }

        @ Override
        public View getView ( int position , View convertView , ViewGroup parent ) {
            LayoutInflater layout_inflater = ( LayoutInflater ) BookshelfActivity.this.getSystemService ( Context.LAYOUT_INFLATER_SERVICE );
            View layout = layout_inflater.inflate ( R.layout.bookshelf_list_item , null );

            return layout;
        }
    };

}
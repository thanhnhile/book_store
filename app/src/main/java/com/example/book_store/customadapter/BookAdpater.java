package com.example.book_store.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.book_store.R;
import com.example.book_store.model.Book;

import java.util.ArrayList;

public class BookAdpater extends BaseAdapter {
    private Context context;
    LayoutInflater inflater = null;
//    ArrayList<Book> list;
//    {
//        list = new ArrayList<>();
//    }

    public BookAdpater(Context context,ArrayList<Book> list) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        this.list = list;
    }

    @Override
    public int getCount() {
        //return list.size();
        return 1;
    }

    @Override
    public Object getItem(int i) {
        //return list.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.book_layout, viewGroup, false);
        }
//        Book book = list.get(i);
//        ImageView img = view.findViewById(R.id.book_img);
//        TextView title = view.findViewById(R.id.book_title);
//        TextView price = view.findViewById(R.id.book_price);
//        img.setImageResource(book.getImg());
//        title.setText(book.getTitle());
//        String bookPrice = Integer.toString(book.getPrice());
//        price.setText(bookPrice);
        return view;
    }
}

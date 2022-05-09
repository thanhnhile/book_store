package com.example.book_store.customadapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.book_store.R;
import com.example.book_store.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdpater extends  RecyclerView.Adapter<BookAdpater.BookViewHolder> {
    Context context;
    private List<Book> mList;

    public BookAdpater(Context context) {
        this.context = context;
    }

    public  void setData(List<Book> list){
        this.mList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_layout,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = mList.get(position);
        if(book == null)
            return;
        holder.bookTitle.setText(book.getTitle());
        String price = Integer.toString(book.getPrice());
        holder.bookPrice.setText(price);
        Glide.with(context).load(book.getImgURL()).into(holder.bookImg);
    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        return 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        private ImageView bookImg;
        private TextView bookTitle;
        private TextView bookPrice;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImg = itemView.findViewById(R.id.book_img);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookPrice = itemView.findViewById(R.id.book_price);
        }
    }
}

package com.example.book_store.customadapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.book_store.R;
import com.example.book_store.ShowDetailFragment;
import com.example.book_store.model.Book;
import com.example.book_store.ui.FormatCurrency;

import java.util.List;

public class BookAdpater extends  RecyclerView.Adapter<BookAdpater.BookViewHolder> {
    Context context;
    private List<Book> mList;
    FragmentManager fragmentManager;
    public BookAdpater(Context context,FragmentManager fragmentManager) {

        this.context = context;
        this.fragmentManager = fragmentManager;
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
        ViewGroup.LayoutParams params = holder.rootView.getLayoutParams();
        ViewGroup.MarginLayoutParams s =(ViewGroup.MarginLayoutParams)params;
        if(position % 2 == 0){
            s.setMargins(0,0,30,30);
        }
        else {
            s.setMargins(0,0,0,30);
        }
        holder.rootView.setLayoutParams(params);
        Book book = mList.get(position);
        if(book == null)
            return;
        holder.bookTitle.setText(book.getTitle());
        holder.bookPrice.setText(FormatCurrency.formatVND(book.getPrice()));
        Glide.with(context).load(book.getImgURL()).into(holder.bookImg);
        //onclick
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetailBook(book);
            }
        });
    }
    public void showDetailBook(Book book){
        ShowDetailFragment fragment = new ShowDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("book-target",book);
        fragment.setArguments(bundle);
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        ft.replace(R.id.container,fragment).addToBackStack(null).commit();
    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        return 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        private View rootView;
        private ImageView bookImg;
        private TextView bookTitle;
        private TextView bookPrice;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            bookImg = itemView.findViewById(R.id.book_img);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookPrice = itemView.findViewById(R.id.book_price);
        }
    }
}

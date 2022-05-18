package com.example.book_store.customadapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.book_store.R;
import com.example.book_store.admin.UpdateFragment;
import com.example.book_store.model.Book;
import com.example.book_store.ui.FormatCurrency;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BookListViewAdapter extends BaseAdapter {
    private Context context;
    LayoutInflater inflater = null;
    ArrayList<Book> list;
    {
        list = new ArrayList<>();
    }
    FragmentManager fragmentManager;
    public BookListViewAdapter(Context context, ArrayList<Book> list, FragmentManager fragmentManager) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Book book = list.get(i);
        ViewHolder viewHolder;
        if(view == null){
            view = inflater.inflate(R.layout.book_listview_layout, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.lv_txt_title);
            viewHolder.author = (TextView) view.findViewById(R.id.lv_txt_author);
            viewHolder.active = (TextView) view.findViewById(R.id.lv_txt_active);
            viewHolder.price = (TextView) view.findViewById(R.id.lv_txt_price);
            viewHolder.img = (ImageView) view.findViewById(R.id.lv_img);
            viewHolder.btnEdit = (Button) view.findViewById(R.id.lv_btn_edit);
            viewHolder.btnActive = (Button) view.findViewById(R.id.lv_btn_active);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context).load(book.getImgURL()).into(viewHolder.img);
        viewHolder.title.setText(book.getTitle());
        viewHolder.author.setText(book.getAuthor());
        if(book.getIsActive() == 1){
            viewHolder.active.setText("Hoạt động");
            viewHolder.active.setTextColor(Color.parseColor("#35a813"));
        }
        else {
            viewHolder.active.setText("Đã ẩn");
            viewHolder.active.setTextColor(Color.parseColor("#d71a00"));
        }
        viewHolder.price.setText(FormatCurrency.formatVND(book.getPrice()));

        //handle event
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Books");
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(book.getId());
            }
        });

        viewHolder.btnActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int isActive = book.getIsActive() == 1 ? 0 : 1;
                book.setIsActive(isActive);
                myRef.child(book.getId()).child("isActive").setValue(book.getIsActive()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isComplete()){
                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }
    private class ViewHolder{
        TextView title;
        TextView author;
        TextView active;
        TextView price;
        ImageView img;
        Button btnEdit;
        Button btnActive;
    }
    private void update(String id){
        UpdateFragment updateFragment = new UpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("BOOK_ID",id);
        updateFragment.setArguments(bundle);
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        ft.replace(R.id.admin_list_book_container,updateFragment).addToBackStack(null).commit();
    }

}

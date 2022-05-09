package com.example.book_store.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_store.R;
import com.example.book_store.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    List<Category> mList;

    public CategoryAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Category> list){
        this.mList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_listbook,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mList.get(position);
        if(category == null)
            return;
        holder.txtName.setText(category.getCategory());
        //Xu ly khi click vao xem toan bo sach thuoc category nay
        holder.txtViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2,LinearLayoutManager.VERTICAL,false);
        holder.rvBook.setLayoutManager(linearLayoutManager);
        BookAdpater bookAdpater = new BookAdpater(context);
        bookAdpater.setData(category.getList());
        holder.rvBook.setAdapter(bookAdpater);
    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName;
        private TextView txtViewAll;
        private RecyclerView rvBook;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.category_txt_name);
            txtViewAll = itemView.findViewById(R.id.category_txt_viewAll);
            rvBook = itemView.findViewById(R.id.category_recy_book);
        }
    }
}

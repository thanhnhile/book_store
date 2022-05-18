package com.example.book_store.customadapter;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_store.CategoryShowAllBooksFragment;
import com.example.book_store.R;
import com.example.book_store.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    List<Category> mList;
    FragmentManager fragmentManager;
    Boolean withViewAll;
    public CategoryAdapter(Context context,FragmentManager fragmentManager,Boolean withViewAll) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.withViewAll = withViewAll;
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
        if(category.getCategory() != null){
            holder.txtName.setText(category.getCategory());
        }
        else{
            holder.txtName.setText("Kết quả tìm kiếm");
        }
        //Xu ly khi click vao xem toan bo sach thuoc category nay
        if(withViewAll){
            holder.txtViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewAllBookByCategory(category.getCategory());
                }
            });
        }else{
            holder.txtViewAll.setVisibility(View.INVISIBLE);
        }
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2,LinearLayoutManager.VERTICAL,false);
        //int spacingInPixels = 25;
        //holder.rvBook.addItemDecoration(new com.example.book_store.ui.GridSpacingItemDecoration(2, spacingInPixels, false, 0));
        holder.rvBook.setLayoutManager(gridLayoutManager);
        BookAdpater bookAdpater = new BookAdpater(context,this.fragmentManager);
        bookAdpater.setData(category.getList());
        holder.rvBook.setAdapter(bookAdpater);
    }
    private void viewAllBookByCategory(String category){
        CategoryShowAllBooksFragment fragment = new CategoryShowAllBooksFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category_name",category);
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

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName;
        private TextView txtViewAll;
        private RecyclerView rvBook;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.category_txt_name);
            txtViewAll = itemView.findViewById(R.id.category_txt_viewAll);
            txtViewAll.setVisibility(View.VISIBLE);
            rvBook = itemView.findViewById(R.id.category_recy_book);
        }
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}

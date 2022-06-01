package com.example.book_store.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_store.R;
import com.example.book_store.model.OrderedItem;

import java.util.ArrayList;

public class OrderedItemAdapter extends RecyclerView.Adapter<OrderedItemAdapter.HolderOrderedItem> {

    private Context context;
    private ArrayList<OrderedItem> orderedItemList;

    public OrderedItemAdapter(Context context, ArrayList<OrderedItem> orderedItemList) {
        this.context = context;
        this.orderedItemList = orderedItemList;
    }
    @NonNull
    @Override
    public HolderOrderedItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_ordereditem,parent,false);
        return new HolderOrderedItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrderedItem holder, int position) {
        OrderedItem orderedItem = orderedItemList.get(position);
        String Id = orderedItem.getId();
        String BookId = orderedItem.getBookId();
        String Quantity = orderedItem.getQuantity();

        holder.txtTitle.setText(BookId);
        holder.txtQuantiy.setText(Quantity);
    }

    @Override
    public int getItemCount() {
        return orderedItemList.size();
    }

    class HolderOrderedItem extends RecyclerView.ViewHolder{
        private TextView txtTitle,txtPrice, txtPriceEach,txtQuantiy;

        public HolderOrderedItem(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtPriceEach = itemView.findViewById(R.id.txtPriceEach);
            txtQuantiy = itemView.findViewById(R.id.txtQuantity);
        }
    }
}

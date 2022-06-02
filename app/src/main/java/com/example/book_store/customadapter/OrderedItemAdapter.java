package com.example.book_store.customadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.book_store.CartFragment;
import com.example.book_store.R;
import com.example.book_store.database.CartDao;
import com.example.book_store.model.Book;
import com.example.book_store.model.CartItem;
import com.example.book_store.model.OrderedItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderedItemAdapter extends RecyclerView.Adapter<OrderedItemAdapter.HolderOrderedItem> {

    private Context context;
    private ArrayList<OrderedItem> orderedItemList;
    FragmentManager fragmentManager;
    CartDao cartDao;

    public OrderedItemAdapter(Context context, ArrayList<OrderedItem> orderedItemList) {
        this.context = context;
        this.orderedItemList = orderedItemList;
        cartDao = new CartDao(context);
    }
    public void setData(ArrayList<OrderedItem> orderedItemList){
        this.orderedItemList = orderedItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HolderOrderedItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_ordereditem,parent,false);
        return new HolderOrderedItem(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull HolderOrderedItem holder, @SuppressLint("RecyclerView") int position) {
//        OrderedItem orderedItem = orderedItemList.get(position);
//        String Id = orderedItem.getId();
//        String BookId = orderedItem.getBookId();
//        String Quantity = orderedItem.getQuantity();
//
//        holder.txtTitle.setText(BookId);
//        holder.txtQuantiy.setText(Quantity);
        OrderedItem orderedItem = orderedItemList.get(position);
        if(orderedItem == null){
            return;
        }
        final int[] price = new int[1];
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Books");
        myRef.child(orderedItem.getBookId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Book book = snapshot.getValue(Book.class);
                    holder.txtTitle.setText(book.getTitle());
                    price[0] = book.getPrice();
                    updateOrderedItemViewValue(holder,book.getPrice(),orderedItem.getQuantity(),position);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("get ordered item error",error.getMessage());
            }
        });
        String num = orderedItem.getQuantity();
        holder.txtQuantiy.setText(num);
    }
    public void updateOrderedItemViewValue(OrderedItemAdapter.HolderOrderedItem holder, int bookPrice, String num, int pos){
        int quantity = Integer.parseInt(num);
        int price = bookPrice * quantity;
        holder.txtPrice.setText(price);
        CartFragment.updateCartValue();
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

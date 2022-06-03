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
import com.example.book_store.ui.FormatCurrency;
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

    public OrderedItemAdapter(Context context, ArrayList<OrderedItem> orderedItemList) {
        this.context = context;
        this.orderedItemList = orderedItemList;
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
        OrderedItem orderedItem = orderedItemList.get(position);
        if(orderedItem == null){
            return;
        }
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Books");
        myRef.child(orderedItem.getBookId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Book book = snapshot.getValue(Book.class);
                    holder.txtTitle.setText(book.getTitle());
                    holder.txtAuthor.setText(book.getAuthor());
                    int price = book.getPrice();
                    String priceTxt = Integer.toString(price);
                    holder.txtPrice.setText(priceTxt);
                    String num = orderedItem.getQuantity();
                    holder.txtQuantiy.setText(num);
                    int number = Integer.parseInt(num);
                    int eachPrice = number * price;
                    holder.txtPriceEach.setText(FormatCurrency.formatVND(eachPrice));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("get ordered item error",error.getMessage());
            }
        });

    }
    @Override
    public int getItemCount() {
        return orderedItemList.size();
    }

    class HolderOrderedItem extends RecyclerView.ViewHolder{
        private TextView txtTitle,txtPrice, txtPriceEach,txtQuantiy,txtAuthor;

        public HolderOrderedItem(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtPriceEach = itemView.findViewById(R.id.txtPriceEach);
            txtQuantiy = itemView.findViewById(R.id.txtQuantity);
        }
    }
}

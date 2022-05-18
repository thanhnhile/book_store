package com.example.book_store.customadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.book_store.CartFragment;
import com.example.book_store.R;
import com.example.book_store.database.BookDao;
import com.example.book_store.database.CartDao;
import com.example.book_store.model.Book;
import com.example.book_store.model.CartItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {
    Context context;
    FragmentManager fragmentManager;
    List<CartItem> list;
    CartDao cartDao;

    public CartItemAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        cartDao = new CartDao(context);
    }


    public void setData(List<CartItem> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CartItem cartItem = list.get(position);
        if(cartItem == null){
            return;
        }
        final int[] price = new int[1];
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Books");
        myRef.child(cartItem.getBookId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Book book = snapshot.getValue(Book.class);
                    Glide.with(context).load(book.getImgURL()).into(holder.img);
                    holder.txtTitle.setText(book.getTitle());
                    holder.txtAuthor.setText(book.getAuthor());
                    price[0] = book.getPrice();
                    updateCartItemViewValue(holder,book.getPrice(),cartItem.getNum(),position);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("get book error",error.getMessage());
            }
        });
        String num = Integer.toString(cartItem.getNum());
        holder.txtNum.setText(num);
        holder.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = cartItem.getNum();
                num += 1;
                cartItem.setNum(num);
                cartDao.updateCartItem(cartItem);
                holder.txtNum.setText(Integer.toString(num));
                updateCartItemViewValue(holder,price[0],cartItem.getNum(),position);
            }
        });
        holder.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = cartItem.getNum();
                if(num > 1){
                    num -= 1;
                    cartItem.setNum(num);
                    cartDao.updateCartItem(cartItem);
                    holder.txtNum.setText(Integer.toString(num));
                    updateCartItemViewValue(holder,price[0],cartItem.getNum(),position);
                }
            }
        });
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartDao.deleteCartItem(cartItem.getId());
                list.remove(cartItem);
                notifyDataSetChanged();
                CartFragment.updateCartValue();
            }
        });
    }
    public void updateCartItemViewValue(CartItemViewHolder holder,int bookPrice,int num,int pos){
        int price = bookPrice * num;
        holder.txtPrice.setText(Integer.toString(price));
        CartFragment.updateCartValue();
    }

    @Override
    public int getItemCount() {
        if(list != null)
            return  list.size();
        return 0;
    }
    public class CartItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView txtTitle,txtAuthor,txtPrice,txtNum;
        Button btnTang,btnGiam;
        ImageView btnRemove;
        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.lv_img);
            txtTitle = itemView.findViewById(R.id.lv_txt_title);
            txtAuthor = itemView.findViewById(R.id.lv_txt_author);
            txtPrice = itemView.findViewById(R.id.lv_txt_price);
            txtNum = itemView.findViewById(R.id.txtsoluong);
            btnTang = itemView.findViewById(R.id.btntangsoloung);
            btnGiam = itemView.findViewById(R.id.btngiamsoluong);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}

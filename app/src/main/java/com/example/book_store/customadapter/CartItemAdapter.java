package com.example.book_store.customadapter;

import android.content.Context;
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
import com.example.book_store.R;
import com.example.book_store.model.CartItem;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {
    Context context;
    FragmentManager fragmentManager;
    List<CartItem> list;

    public CartItemAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
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
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem cartItem = list.get(position);
        if(cartItem == null){
            return;
        }
        Glide.with(context).load(cartItem.getBook().getImgURL()).into(holder.img);
        holder.txtTitle.setText(cartItem.getBook().getTitle());
        holder.txtAuthor.setText(cartItem.getBook().getAuthor());
        String price = Integer.toString(cartItem.getBook().getPrice());
        holder.txtPrice.setText(price);
        String num = Integer.toString(cartItem.getNum());
        holder.txtNum.setText(num);
        holder.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = cartItem.getNum();
                num += 1;
                cartItem.setNum(num);
                holder.txtNum.setText(Integer.toString(num));
            }
        });
        holder.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = cartItem.getNum();
                if(num > 1){
                    num -= 1;
                    cartItem.setNum(num);
                    holder.txtNum.setText(Integer.toString(num));
                }
            }
        });
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Xoa cai nay", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list != null)
            return  list.size();
        return 0;
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtTitle,txtAuthor,txtPrice,txtNum;
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

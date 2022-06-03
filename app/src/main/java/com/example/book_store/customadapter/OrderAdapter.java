package com.example.book_store.customadapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_store.OrderDetailFragment;
import com.example.book_store.R;
import com.example.book_store.model.Order;
import com.example.book_store.sharedpreferences.Constants;
import com.example.book_store.sharedpreferences.PreferenceManager;

import java.util.ArrayList;
import java.util.Calendar;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.HolderOrder> {

    private Context context;
    private ArrayList<Order> orderList;
    private FragmentManager fragmentManager;

    public OrderAdapter(Context context, ArrayList<Order> orderList,FragmentManager fm) {
        this.context = context;
        this.orderList = orderList;
        this.fragmentManager = fm;
    }

    @NonNull
    @Override
    public HolderOrder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_order_user, parent, false);
        return new HolderOrder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrder holder, int position) {
        Order order = orderList.get(position);
        String orderId = order.getOrderId();
        String orderTime = order.getOrderTime();
        String orderBy = order.getOrderBy();
        String orderCost = order.getOrderCost();
        String orderStatus = order.getOrderStatus();

        holder.txtOrder.setText(orderId);
        holder.txtTotal.setText(orderCost);
        holder.txtStatus.setText(orderStatus);

        if (orderStatus.equals("In progress")){
            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.primary_color));
        } else if (orderStatus.equals("Completed")){
            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.second_color));
        } else if (orderStatus.equals("Cancelled")){
            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.text_second));
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(orderTime));
        String formatedDate = DateFormat.format("dd/MM/yyyy", calendar).toString();
        holder.txtDate.setText(formatedDate);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open order details
                handleOrderItemClick(orderId,orderBy);
            }
        });
    }
    void handleOrderItemClick(String orderId,String orderBy){
        PreferenceManager preferenceManager = new PreferenceManager(context, Constants.LOGIN_KEY_PREFERENCE_NAME);
        int isAdmin = preferenceManager.getInt(Constants.LOGIN_IS_ADMIN);
        OrderDetailFragment orderDetailFragment = new OrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("orderId",orderId);
        bundle.putString("orderBy",orderBy);
        orderDetailFragment.setArguments(bundle);
        int containerID;
        if(isAdmin == 1){
            containerID = R.id.admin_menu_container;
        }
        else containerID = R.id.container;
        this.fragmentManager.beginTransaction()
                .replace(containerID,orderDetailFragment).addToBackStack(null).commit();
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class HolderOrder extends RecyclerView.ViewHolder {
        private TextView txtOrder, txtDate, txtTotal, txtStatus;

        public HolderOrder(@NonNull View itemView) {
            super(itemView);

            txtOrder = itemView.findViewById(R.id.txtOrder);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtStatus = itemView.findViewById(R.id.txtStatus);
        }
    }
}

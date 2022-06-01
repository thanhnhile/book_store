package com.example.book_store.customadapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_store.OrderDetailFragment;
import com.example.book_store.R;
import com.example.book_store.model.Order;

import java.util.ArrayList;
import java.util.Calendar;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.HolderOrder> {

    private Context context;
    private ArrayList<Order> orderList;

    public OrderAdapter(Context context, ArrayList<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
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
                Intent intent = new Intent(context, OrderDetailFragment.class);
                intent.putExtra("orderId",orderId);
                intent.putExtra("orderBy",orderBy);
                context.startActivity(intent);
            }
        });
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

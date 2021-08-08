package com.ecs.hffcadminapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    Context context;
    List<ModelOrders> list;
    public static final String ORDER_ID = "com.ecs.hffcadminapp.orderId";

    public OrdersAdapter(Context context, List<ModelOrders> olist) {
        this.context = context;
        this.list = olist;
    }

    @NonNull
    @Override
    public OrdersAdapter.OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_items_layout,parent,false);
        OrdersViewHolder holder = new OrdersViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.OrdersViewHolder holder, int position) {
        final ModelOrders orders = list.get(position);
        holder.tvOrderId.setText(": "+orders.getId());
        holder.tvCustomerPhone.setText(": "+orders.getPhoneNumber());
        holder.tvCustomerName.setText(": "+orders.getCustomerName());
        holder.btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(),OrderDetailsActivity.class);
                intent.putExtra(ORDER_ID,orders.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId,tvCustomerName,tvCustomerPhone;
        Button btnViewOrder;
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.textView_orderId);
            tvCustomerName = itemView.findViewById(R.id.textView_orderName);
            tvCustomerPhone = itemView.findViewById(R.id.textView_orderPhone);
            btnViewOrder = itemView.findViewById(R.id.button_viewOrder);
        }
    }
}

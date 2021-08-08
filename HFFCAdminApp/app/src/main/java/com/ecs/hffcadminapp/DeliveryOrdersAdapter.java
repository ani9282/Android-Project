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

public class DeliveryOrdersAdapter extends RecyclerView.Adapter<DeliveryOrdersAdapter.DeliveredOrderViewHolder> {
    private Context context;
    private List<ModelOrders> list;
    public static final String DELIVERED_ORDER_ID = "com.ecs.hffcadminapp.deliveredOrderId";

    public DeliveryOrdersAdapter(Context context, List<ModelOrders> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DeliveredOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_items_layout,parent,false);
        DeliveryOrdersAdapter.DeliveredOrderViewHolder holder = new DeliveryOrdersAdapter.DeliveredOrderViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredOrderViewHolder holder, int position) {
        final ModelOrders orders = list.get(position);
        holder.tvOrderId.setText(": "+orders.getId());
        holder.tvCustomerPhone.setText(": "+orders.getPhoneNumber());
        holder.tvCustomerName.setText(": "+orders.getCustomerName());
        holder.btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(),DeliveredOrderDetailsActivity.class);
                intent.putExtra(DELIVERED_ORDER_ID,orders.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DeliveredOrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId,tvCustomerName,tvCustomerPhone;
        Button btnViewOrder;

        public DeliveredOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.textView_orderId);
            tvCustomerName = itemView.findViewById(R.id.textView_orderName);
            tvCustomerPhone = itemView.findViewById(R.id.textView_orderPhone);
            btnViewOrder = itemView.findViewById(R.id.button_viewOrder);
        }
    }
}

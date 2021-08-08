package com.ecs.hindfarmfreshchicken;

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
    private Context context;
    private List<ModelOrders> list;
    public static final String ORDER_ID = "OrdersId";

    public OrdersAdapter(Context context, List<ModelOrders> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public OrdersAdapter.OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_items_layout,parent,false);
        OrdersViewHolder viewHolder = new OrdersViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.OrdersViewHolder holder, int position) {
        final ModelOrders orders = list.get(position);
        holder.tvOrderId.setText(": "+orders.getId());
        holder.tvQuantity.setText(": "+orders.getQuantity());
        holder.tvPrice.setText(": "+orders.getCost());
        holder.btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,OrderDetailsActivity.class);
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
        TextView tvOrderId,tvQuantity,tvPrice;
        Button btnViewOrder;
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.textView_orderId);
            tvQuantity = itemView.findViewById(R.id.textView_orderQuantity);
            tvPrice = itemView.findViewById(R.id.textView_orderPrice);
            btnViewOrder = itemView.findViewById(R.id.button_viewOrder);
        }
    }
}

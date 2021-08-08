package com.ecs.a3giftshopy;

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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>
{
    private Context context;
    private List<ModelClass> list;
    private int sid;
    public static final String DELIVERED_ORDER_ID = "com.ecs.royalrex.deliveredOrderId";
    public static final String sender_mobile = "com.ecs.royalrex.sender_mobile";

    public ProductAdapter(Context context, List<ModelClass> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_product,parent,false);
        ProductAdapter.ProductViewHolder holder = new ProductAdapter.ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {

        final ModelClass orders = list.get(position);
        holder.id.setText(": "+orders.getId());
        holder.name.setText(": "+orders.getName());
        holder.price.setText(": "+orders.getPrice());



        holder.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(),Show_all_product_detail.class);
                intent.putExtra(DELIVERED_ORDER_ID,orders.getId());
                intent.putExtra("driver_id",sid);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView id,name,price;
        Button register;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            register=itemView.findViewById(R.id.register);
        }
    }
}

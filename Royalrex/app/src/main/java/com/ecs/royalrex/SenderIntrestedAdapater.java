package com.ecs.royalrex;

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


public class SenderIntrestedAdapater extends  RecyclerView.Adapter<SenderIntrestedAdapater.ProductViewHolder>
{

    private Context context;
    private List<ModelSender_intrested> list;
    public static final String DELIVERED_ORDER_ID = "com.ecs.royalrex.deliveredOrderId";
    public static final String driver_mobile = "com.ecs.royalrex.driver_mobile";

    public SenderIntrestedAdapater(Context context, List<ModelSender_intrested> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public SenderIntrestedAdapater.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.layout_sender_intrested,parent,false);
       SenderIntrestedAdapater.ProductViewHolder holder = new SenderIntrestedAdapater.ProductViewHolder(view);
       return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull SenderIntrestedAdapater.ProductViewHolder holder, int position) {
        final ModelSender_intrested orders = list.get(position);
        holder.id.setText(": "+orders.getId());
        holder.driver_name.setText(": "+orders.getDriver_name());
        holder.driver_mobile.setText(": "+orders.getDriver_mobile());
        holder.vehical_name.setText(": "+orders.getVehical_name());
        holder.vehical_number.setText(": "+orders.getVehical_number());
        holder.price.setText(": "+orders.getPrice());


        holder.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(),Sender_detail_intrested.class);
                intent.putExtra(DELIVERED_ORDER_ID,orders.getId());
                intent.putExtra(driver_mobile,orders.getDriver_mobile());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView id,driver_name,driver_mobile,vehical_name,vehical_number,price;
        Button register;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            driver_name=itemView.findViewById(R.id.dname);
            driver_mobile=itemView.findViewById(R.id.dmobile);
            vehical_name=itemView.findViewById(R.id.vname);
            vehical_number=itemView.findViewById(R.id.vnumber);
            price=itemView.findViewById(R.id.price);
            register=itemView.findViewById(R.id.register);

        }
    }



}





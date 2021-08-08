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

public class DriverSearchVehicalAdapter extends RecyclerView.Adapter<DriverSearchVehicalAdapter.ProductViewHolder>
{

    private Context context;
    private List<ModelDriver> list;
    public static final String DELIVERED_ORDER_ID = "com.ecs.royalrex.deliveredOrderId";


    public DriverSearchVehicalAdapter(Context context, List<ModelDriver> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public DriverSearchVehicalAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_driver_search_vehical,parent,false);
        DriverSearchVehicalAdapter.ProductViewHolder holder = new DriverSearchVehicalAdapter.ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DriverSearchVehicalAdapter.ProductViewHolder holder, int position) {
        final ModelDriver orders = list.get(position);
        holder.id.setText(": "+orders.getId());
        holder.vehical_name.setText(": "+orders.getVehical_name());
        holder.vehical_number.setText(": "+orders.getVehical_number());
        holder.driver_name.setText(": "+orders.getDriver_name());
        holder.driver_mobile.setText(": "+orders.getDriver_mobile());

        holder.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(),Driver_profile.class);
                intent.putExtra(DELIVERED_ORDER_ID,orders.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView id,vehical_name,vehical_number,driver_name,driver_mobile;
        Button register;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.id);
            vehical_name=itemView.findViewById(R.id.vehical_name);
            vehical_number=itemView.findViewById(R.id.vehical_number);
            driver_name=itemView.findViewById(R.id.driver_name);
            driver_mobile=itemView.findViewById(R.id.driver_mobile);

            register=itemView.findViewById(R.id.register);

        }
    }

}

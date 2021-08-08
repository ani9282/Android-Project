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

public class SearchVehicalAdapter extends RecyclerView.Adapter<SearchVehicalAdapter.ProductViewHolder>
{

    private Context context;
    private List<ModelDriver> list;

    public SearchVehicalAdapter(Context context, List<ModelDriver> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchVehicalAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_search_driver,parent,false);
        SearchVehicalAdapter.ProductViewHolder holder = new SearchVehicalAdapter.ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchVehicalAdapter.ProductViewHolder holder, int position) {

        final ModelDriver orders = list.get(position);
        holder.vehical_name.setText(": "+orders.getVehical_name());
        holder.vehical_number.setText(": "+orders.getVehical_number());
        holder.driver_name.setText(": "+orders.getDriver_name());
        holder.driver_mobile.setText(": "+orders.getDriver_mobile());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView vehical_name,vehical_number,driver_name,driver_mobile;
        //Button register;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

           vehical_name=itemView.findViewById(R.id.vehical_name);
            vehical_number=itemView.findViewById(R.id.vehical_number);
            driver_name=itemView.findViewById(R.id.driver_name);
            driver_mobile=itemView.findViewById(R.id.driver_mobile);

            //register=itemView.findViewById(R.id.register);

        }
    }
}

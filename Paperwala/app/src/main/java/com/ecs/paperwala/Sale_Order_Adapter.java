package com.ecs.paperwala;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Sale_Order_Adapter extends RecyclerView.Adapter<Sale_Order_Adapter.ProductViewHolder>
{
    private Context context;
    private List<ModelSaleOrder> list;
    public static final String DELIVERED_ORDER_ID = "com.ecs.royalrex.deliveredOrderId";
    public Sale_Order_Adapter(Context context, List<ModelSaleOrder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Sale_Order_Adapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_retailer_balance,parent,false);
        Sale_Order_Adapter.ProductViewHolder holder = new Sale_Order_Adapter.ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Sale_Order_Adapter.ProductViewHolder holder, int position) {
        final ModelSaleOrder orders = list.get(position);
        holder.sale_id.setText(orders.getSaleOrder());
        holder.name.setText(orders.getRetailerName());
        double paid_amt=orders.getPaidAmount();
        String paid=String.valueOf(paid_amt);
        holder.paid_amount.setText(paid);
        double final_amount=orders.getFinalBalaceAmount();
        String s=String.valueOf(final_amount);
        holder.final_balance_amount.setText(s);


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"You Click Add ",Toast.LENGTH_LONG).show();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"You Click Delete",Toast.LENGTH_LONG).show();
            }
        });


    }



    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name,sale_id,paid_amount,final_balance_amount,view,delete;
        //Button register;
        //public TextView buttonViewOption;
        ImageView icon;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            sale_id = itemView.findViewById(R.id.sale_id);
            name=itemView.findViewById(R.id.name);
            paid_amount=itemView.findViewById(R.id.paid_amount);
            final_balance_amount=itemView.findViewById(R.id.final_amount);
            view=itemView.findViewById(R.id.view_data);
            delete=itemView.findViewById(R.id.delete);
        }
    }





}

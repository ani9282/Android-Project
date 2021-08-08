package com.ecs.paperwala;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Product_adapter extends RecyclerView.Adapter<Product_adapter.ProductViewHolder>
{

    private String id;
    private Context context;
    private List<ModelClass> list;
    public static final String DELIVERED_ORDER_ID = "com.ecs.royalrex.deliveredOrderId";
    public Product_adapter(Context context, List<ModelClass> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public Product_adapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_product,parent,false);
       Product_adapter.ProductViewHolder holder = new Product_adapter.ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Product_adapter.ProductViewHolder holder, int position) {
        final ModelClass orders = list.get(position);
        //holder.id.setText(orders.getRetailerId());
        holder.name.setText(orders.getRetailerName());
        holder.address.setText(orders.getRetailerAddress());
        holder.mobile.setText(" | "+orders.getMobileNo());
        holder.amount.setText("Remaining Amt: "+orders.getRemainingAMT()+"/-");
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context,view);
                //inflating menu from xml resource
                popup.inflate(R.menu.retailer_cardview_overflow);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                               //Intent intent=new Intent(Product_adapter.this,Product_adapter.class);
                               // startactivity(intent);
                                //intent.putExtra(DELIVERED_ORDER_ID,orders.getId());
                                //context.startActivity(new Intent(context,Edit_retailer.class));
                                Intent intent=new Intent(context,Edit_retailer.class);
                                intent.putExtra("id",orders.getRetailerId());
                                context.startActivity(intent);


                                break;
                            case R.id.menu2:
                               //Toast.makeText(Product_adapter.this,"You Click Delete",Toast.LENGTH_LONG).show();
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });

        holder.add_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Add_transaction.class);
                intent.putExtra("name",orders.getRetailerName());
                intent.putExtra("city_id",orders.getCity_id());
                intent.putExtra("distributor_id",orders.getDistribute_id());
                intent.putExtra("doubleValue_e5",orders.getRemainingAMT());
                intent.putExtra("rid",orders.getRetailerId());
                context.startActivity(intent);
            }
        });

        holder.return_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Return_transaction.class);
                intent.putExtra("name",orders.getRetailerName());
                intent.putExtra("city_id",orders.getCity_id());
                intent.putExtra("distributor_id",orders.getDistribute_id());
                intent.putExtra("doubleValue_e5",orders.getRemainingAMT());
                intent.putExtra("rid",orders.getRetailerId());
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
       return list.size();

    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView id,name,address,mobile,amount,add_transaction,return_transaction;
        //Button register;
        //public TextView buttonViewOption;
        ImageView icon;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            //id = itemView.findViewById(R.id.);
            name=itemView.findViewById(R.id.image1);
            address=itemView.findViewById(R.id.address);
            mobile=itemView.findViewById(R.id.mobile);
            amount=itemView.findViewById(R.id.amount);
            //buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
            icon=itemView.findViewById(R.id.textViewOptions);
            add_transaction=itemView.findViewById(R.id.add_transaction);
            return_transaction=itemView.findViewById(R.id.return_transaction);
        }
    }
}

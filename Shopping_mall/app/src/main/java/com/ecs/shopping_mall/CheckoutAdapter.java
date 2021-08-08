package com.ecs.shopping_mall;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CheckoutAdapter  extends RecyclerView.Adapter<CheckoutAdapter.ProductViewHolder>
{

    private Context context;
    private List<ModelClass> list;

    public CheckoutAdapter(Context context, List<ModelClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CheckoutAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_product,parent,false);
       CheckoutAdapter.ProductViewHolder holder = new CheckoutAdapter.ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CheckoutAdapter.ProductViewHolder holder, int position) {

        final ModelClass orders = list.get(position);
        final List<ItemsImagesList> itemsImagesLists=orders.getItemsImagesList();
        final ItemsImagesList itemsImagesList=itemsImagesLists.get(0);
        holder.first_name.setText(orders.getItemsName());
        List<ItemsPriceList> itemsPriceLists=orders.getItemsPriceList();
        final ItemsPriceList itemsPriceList=itemsPriceLists.get(0);


        final String imageUri="http://testmandaiapp-env.eba-nrb4na2q.us-east-2.elasticbeanstalk.com/mandayiapplication/api/downloadfile/"+itemsImagesList.getImage();
        Picasso.with(context).load(imageUri).into(holder.imageView);
        //holder.imageView.setimage;

        holder.category_name.setText(orders.getCategoryName());
        double discount_amount=itemsPriceList.getDiscountAmount();
        String dis_amt="Rs. "+String.valueOf(discount_amount);
        double price_double=itemsPriceList.getItemsPrice();
        String price="MRP "+String.valueOf(price_double);
        holder.price.setText(price);
        holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.discount_price.setText(dis_amt);
        double price_double1=itemsPriceList.getItemsQuantity();
        String qty=String.valueOf(price_double1);
        holder.qty.setText("/ "+qty+" "+itemsPriceList.getItemsUnit());




    }


    @Override
    public int getItemCount() {
        return list.size();
    }






    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView first_name,category_name,price,qty,discount_price;
        ImageView imageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            first_name=itemView.findViewById(R.id.item_name);
            imageView=itemView.findViewById(R.id.image5);
            category_name=itemView.findViewById(R.id.category_name);
            qty=itemView.findViewById(R.id.qty);
            discount_price=itemView.findViewById(R.id.discounted_price);

        }
    }


}

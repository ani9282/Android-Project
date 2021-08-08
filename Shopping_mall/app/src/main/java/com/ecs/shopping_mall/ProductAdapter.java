package com.ecs.shopping_mall;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecs.shopping_mall.ItemsImagesList;
import com.ecs.shopping_mall.ModelClass;
import com.ecs.shopping_mall.R;
import com.google.gson.Gson;
import com.readystatesoftware.viewbadger.BadgeView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.http.Field;

import static android.content.Context.MODE_PRIVATE;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    private Context context;
    private List<ModelClass> list;
    private List<ModelClass> cart_list;
    private CheckoutAdapter adapter;

    public static final String DELIVERED_ORDER_ID = "com.ecs.royalrex.deliveredOrderId";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    int value = 1,icon_count=0;
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.shopping_mall.file";
    private SharedPreferences.Editor editor;


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
    public void onBindViewHolder(@NonNull final ProductAdapter.ProductViewHolder holder, int position) {

        final List<ModelClass> cart_list= new ArrayList<>();
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



        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = context.getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
                editor = sharedPreferences.edit();
                value=0;

                holder.increment.setVisibility(View.VISIBLE);
                holder.decrement.setVisibility(View.VISIBLE);
                holder.value.setVisibility(View.VISIBLE);
                holder.add.setVisibility(View.GONE);

                holder.increment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ModelClass u=new ModelClass();
                        String name=orders.getItemsName();
                        u.setItemsName(name);
                        u.setCategoryName(orders.getCategoryName());
                        String image=itemsImagesList.getImage();
                        //double price=itemsPriceList.getDiscountAmount();
                        u.setItemsPriceList(orders.getItemsPriceList());
                        String v1=String.valueOf(value++);
                        holder.value.setText(v1);
                        cart_list.add(u);
                        Gson gson = new Gson();
                        String json = gson.toJson(cart_list);
                        editor.putString("key", json);
                        //editor.putInt("qty",value);
                        editor.apply();     // This line is IMPORTANT !!!

                    }
                });

                holder.decrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String v1=String.valueOf(value--);
                        holder.value.setText(v1);
                       // editor.putInt("qty",value);
                    }
                });



                /*
                BadgeView badge = new BadgeView(context,);
                String count=String.valueOf(icon_count++);
                badge.setText(count);
                badge.show();




                 */



              // double amt=price*value;








            }
        });

        /*
        Gson gson = new Gson();
        cart_list.add(u);
        String json = gson.toJson(cart_list);
        editor.putString("jsondata",json.toString());
        editor.apply();     // This line is IMPORTANT !!!

         */




    }


    @Override
    public int getItemCount() {
        return list.size();
    }






    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView first_name,category_name,price,qty,discount_price;
        ImageView imageView;
        Button add,increment,decrement;
        EditText value;
        View target;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            first_name=itemView.findViewById(R.id.item_name);
            imageView=itemView.findViewById(R.id.image5);
            category_name=itemView.findViewById(R.id.category_name);
            price=itemView.findViewById(R.id.price);
            qty=itemView.findViewById(R.id.qty);
            discount_price=itemView.findViewById(R.id.discounted_price);
            add=itemView.findViewById(R.id.add);
            increment=itemView.findViewById(R.id.incrementButton);
            decrement=itemView.findViewById(R.id.decrementButton);
            value=itemView.findViewById(R.id.quantity_textview);
            increment.setVisibility(View.GONE);
            decrement.setVisibility(View.GONE);
            value.setVisibility(View.GONE);
            target = itemView.findViewById(R.id.action_user);


        }
    }




}

package com.ecs.shopping_mall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.PrivateKey;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Show_All_Product extends AppCompatActivity {


    private List<ModelClass> list;
    private RecyclerView recyclerView;
    Bitmap bmp;
    private ProductAdapter adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__all__product);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Show_All_Product.this));
        getData();
    }

    public void getData()
    {
        Call<List<ModelClass>> modelClass=BloggerAPI.getServices().getModelList(1,8,"","","","","",411006);
        modelClass.enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call, Response<List<ModelClass>> response) {
                Log.d("This activity ==== ","response.raw().request().url();"+response.raw().request().url());
                Log.d("This activity ==== ","Success=========> "+response.body());
                //Toast.makeText(Show_All_Product.this, "Success"+response.body(), Toast.LENGTH_SHORT).show();
               // ModelClass u=new ModelClass();
                //list.add(u);
                List<ModelClass> list= response.body();
                adapter = new ProductAdapter(Show_All_Product.this,list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ModelClass>> call, Throwable t) {
                Log.d("This activity ==== ","response.raw().request().url();"+call.request().url());
                Toast.makeText(Show_All_Product.this, "Failed"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*
    public static  getImage(final String image) {
        Call<ItemsImagesList> modelClass1=BloggerImageAPI.getServices().getImageList(image);
        modelClass1.enqueue(new Callback<ItemsImagesList>() {
            @Override
            public void onResponse(Call<ItemsImagesList> call, Response<ItemsImagesList> response) {
                //bmp = BitmapFactory.decodeStream(image.getBytes().);
               // ByteArrayInputStream imageStream = new ByteArrayInputStream(response.body());
               // bmp = BitmapFactory.decodeStream(imageStream);
               // Toast.makeText(Show_All_Product.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                return null;
            }

            @Override
            public void onFailure(Call<ItemsImagesList> call, Throwable t) {

            }
        });

        }
     */

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.card_view,menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_user: //Your task
               //Toast.makeText(this, "You Click On Basket", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Show_All_Product.this,Show_cart.class);
                startActivity(intent);
                return true;

            default:return super.onOptionsItemSelected(item);
        }
    }



    }








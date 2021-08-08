package com.rajendra.foodapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rajendra.foodapp.adapter.AsiaFoodAdapter;
import com.rajendra.foodapp.adapter.PopularFoodAdapter;
import com.rajendra.foodapp.model.AsiaFood;
import com.rajendra.foodapp.model.PopularFood;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView popularRecycler, asiaRecycler;
    PopularFoodAdapter popularFoodAdapter;
    AsiaFoodAdapter asiaFoodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView6 =findViewById(R.id.imageView6);
        imageView6.setOnClickListener(new View.OnClickListener()
                                      {
                                          @Override
                                          public void onClick(View V){
                                              Intent intent= new Intent(Intent.ACTION_SEND);
                                              intent.setType("text/Plain");

                                              String Sub="http://play.google.com";

                                              intent.putExtra(Intent.EXTRA_TEXT,Sub);
                                              startActivity(Intent.createChooser(intent,"Share Using"));

                                          }
                                      });





        List<PopularFood> popularFoodList = new ArrayList<>();

        popularFoodList.add(new PopularFood("Aloo\nGobi", "Rs.300", R.drawable.popularfood1));
        popularFoodList.add(new PopularFood("Butter Chicken", "Rs.250", R.drawable.popularfood2));
        popularFoodList.add(new PopularFood("Palak Panner", "Rs.150", R.drawable.popularfood3));
        popularFoodList.add(new PopularFood("Fish\nCurry", "Rs.160", R.drawable.popularfood4));
        popularFoodList.add(new PopularFood("Spicy\nKofta", "Rs.320", R.drawable.popularfood5));
        popularFoodList.add(new PopularFood("Delicious\nBiryani", "Rs.250", R.drawable.popularfood6));

        setPopularRecycler(popularFoodList);


        List<AsiaFood> asiaFoodList = new ArrayList<>();
        asiaFoodList.add(new AsiaFood("Chicago Pizza", "$20", R.drawable.asiafood1, "4.5", "Briand Restaurant"));
        asiaFoodList.add(new AsiaFood("Straberry Cake", "$25", R.drawable.asiafood2, "4.2", "Friends Restaurant"));
        asiaFoodList.add(new AsiaFood("Chicago Pizza", "$20", R.drawable.asiafood1, "4.5", "Briand Restaurant"));
        asiaFoodList.add(new AsiaFood("Straberry Cake", "$25", R.drawable.asiafood2, "4.2", "Friends Restaurant"));
        asiaFoodList.add(new AsiaFood("Chicago Pizza", "$20", R.drawable.asiafood1, "4.5", "Briand Restaurant"));
        asiaFoodList.add(new AsiaFood("Straberry Cake", "$25", R.drawable.asiafood2, "4.2", "Friends Restaurant"));

        setAsiaRecycler(asiaFoodList);

    }



    private void setPopularRecycler(List<PopularFood> popularFoodList) {

        popularRecycler = findViewById(R.id.popular_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        popularRecycler.setLayoutManager(layoutManager);
        popularFoodAdapter = new PopularFoodAdapter(this, popularFoodList);
        popularRecycler.setAdapter(popularFoodAdapter);

    }

    private void setAsiaRecycler(List<AsiaFood> asiaFoodList) {

        asiaRecycler = findViewById(R.id.asia_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        asiaRecycler.setLayoutManager(layoutManager);
        asiaFoodAdapter = new AsiaFoodAdapter(this, asiaFoodList);
        asiaRecycler.setAdapter(asiaFoodAdapter);

    }
    FloatingActionButton btn4=(FloatingActionButton) findViewById(R.id.floatingActionButton2);






}

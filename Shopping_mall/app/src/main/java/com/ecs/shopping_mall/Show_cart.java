package com.ecs.shopping_mall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Show_cart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckoutAdapter adapter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String sharedPrefFile = "com.ecs.shopping_mall.file";
    List<ModelClass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cart);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Show_cart.this));

       // sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
       // editor = sharedPreferences.edit();

        list=new ArrayList<>();
        list=getDataFromSharedPreferences();
        adapter = new CheckoutAdapter(Show_cart.this,list);
        recyclerView.setAdapter(adapter);

    }

    private List<ModelClass> getDataFromSharedPreferences(){
        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("key", null);
        //String json1= sharedPreferences.getString("qty", null);
//        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        //return gson.fromJson(json, type);
        ObjectMapper mapper = new ObjectMapper();
        StudentList studentList = mapper.readValue(jsonString, StudentList.class);
        List<ModelClass> participantJsonList = mapper.readValue(json, new TypeReference<List<ModelClass>>(){});
    }



}
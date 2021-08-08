package com.ecs.paperwala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order_product extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    public static final String sharedPrefFile = "com.ecs.paperwala.file";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String sid;
    Double amount;
    int id;
    private Product_adapter adapter;
    List<ModelClass> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product);
        progressDialog = new ProgressDialog(Order_product.this);
        progressDialog.setMessage("Loading Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPreferences = getSharedPreferences(Login.sharedPrefFile, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sid = sharedPreferences.getString("id", "id");
        id = Integer.parseInt(sid);


        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Order_product.this));


        //SaveData saveData = new SaveData();
        //saveData.execute();

        String URL = "http://paperwala.live/api/webdistributor/RetailerList?disId=" + id + "";
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>()
        {
            ArrayList<ModelClass> list = new ArrayList<>();
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray contacts = new JSONArray(s);

                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        ModelClass u=new ModelClass();
                        u.setRetailerId(c.getString("RetailerId"));
                        u.setRetailerName(c.getString("RetailerName"));
                        u.setRetailerAddress(c.getString("RetailerAddress"));
                        u.setMobileNo(c.getString("MobileNo"));
                        u.setRemainingAMT(c.getDouble("RemainingAMT"));
                        u.setCity_id(c.getString("CityId"));
                        u.setDistribute_id(c.getString("DistributorId"));
                        list.add(u);
                    }

                   // Collections.copy(modelList,list);
                    adapter=new Product_adapter(Order_product.this,list);
                    recyclerView.setAdapter(adapter);

                } catch (Exception e) {
                    Toast.makeText(Order_product.this, "" + e, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Order_product.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
            }
        }) {

        };


        RequestQueue rQueue = Volley.newRequestQueue(Order_product.this);
        rQueue.add(request);



    }


    /*
    class SaveData extends AsyncTask<String, String, String> {
        boolean isSuccess = false;
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }


        @Override
        protected String doInBackground(String... strings) {

            String URL = "http://paperwala.live/api/webdistributor/RetailerList?disId=" + id + "";
            StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String s) {
                    try {
                        JSONArray contacts = new JSONArray(s);
                          modelList = new ArrayList<>();
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            ModelClass u=new ModelClass();
                            u.setRetailerId(c.getString("RetailerId"));
                            u.setRetailerName(c.getString("RetailerName"));
                            u.setRetailerAddress(c.getString("RetailerAddress"));
                            u.setMobileNo(c.getString("MobileNo"));
                            u.setRemainingAMT(c.getDouble("RemainingAMT"));
                            modelList.add(u);
                        }


                    } catch (Exception e) {
                        Toast.makeText(Order_product.this, "" + e, Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(Order_product.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                }
            }) {

            };


            RequestQueue rQueue = Volley.newRequestQueue(Order_product.this);
            rQueue.add(request);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            adapter=new Product_adapter(Order_product.this,modelList);
            recyclerView.setAdapter(adapter);
        }
    }


     */



}
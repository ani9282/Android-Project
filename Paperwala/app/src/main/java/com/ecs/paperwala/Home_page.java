package com.ecs.paperwala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home_page extends AppCompatActivity {

    CardView sale,return_paper,balance,retailer;
    int count=0;
    int id,retailer1;
    double amount[];
    double amt;
    TextView retailer_value,amount_value;
    ProgressDialog progressDialog;
    public static final String sharedPrefFile = "com.ecs.paperwala.file";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String sid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        progressDialog = new ProgressDialog(Home_page.this);
        progressDialog.setMessage("Loading Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        sale=(CardView)findViewById(R.id.sale);
        return_paper=(CardView)findViewById(R.id.return_paper);
        retailer=(CardView)findViewById(R.id.retailer);
        balance=(CardView)findViewById(R.id.balance);
        //id=getIntent().getIntExtra("id",id);
        retailer_value=(TextView) findViewById(R.id.retailer_value);
        amount_value=(TextView)findViewById(R.id.amount_value);
        sharedPreferences = getSharedPreferences(Login.sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
       // sid = getIntent().getStringExtra("id");
        sid=sharedPreferences.getString("id","id");
        id=Integer.parseInt(sid);
        SaveData saveData=new SaveData();
        saveData.execute();

        /*
        String URL ="http://paperwala.live/api/webdistributor/RetailerList?disId="+id+"";
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {

                   // JSONObject jsonObj = new JSONObject(s);


                    // Getting JSON Array node
                    //JSONArray contacts = jsonObj.getJSONArray(s);

                    JSONArray contacts = new JSONArray(s);
                    retailer1=contacts.length();
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("RetailerId");
                       //c.getDouble("RemainingAMT");
                       amt+=c.getDouble("RemainingAMT");

                    }

                    amount_value.setText(String.valueOf(amt));
                    retailer_value.setText(String.valueOf(retailer1));

                }
                catch (Exception e) {
                   Toast.makeText(Home_page.this,""+e,Toast.LENGTH_SHORT).show();
                }

            }
            },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Home_page.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();
            }
        }) {

        };


        RequestQueue rQueue = Volley.newRequestQueue(Home_page.this);
        rQueue.add(request);



         */

        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Home_page.this,"You Click Sale Paper",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Home_page.this,Sale_order.class);
                startActivity(i);
            }
        });

        return_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home_page.this,"You Click Return Paper",Toast.LENGTH_SHORT).show();
            }
        });

        retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(Home_page.this,"You Click Retailer",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Home_page.this,Order_product.class);
                startActivity(i);
                
            }
        });

        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Home_page.this,"You Click Balance",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Home_page.this,Retailer_balance.class);
                startActivity(i);
            }
        });

    }


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
            String URL ="http://paperwala.live/api/webdistributor/RetailerList?disId="+id+"";
            StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
                @Override
                public void onResponse(String s) {
                    try {

                        // JSONObject jsonObj = new JSONObject(s);


                        // Getting JSON Array node
                        //JSONArray contacts = jsonObj.getJSONArray(s);

                        JSONArray contacts = new JSONArray(s);
                        retailer1=contacts.length();
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            String id = c.getString("RetailerId");
                            //c.getDouble("RemainingAMT");
                            amt+=c.getDouble("RemainingAMT");

                        }

                        amount_value.setText(String.valueOf(amt));
                        retailer_value.setText(String.valueOf(retailer1));

                    }
                    catch (Exception e) {
                        Toast.makeText(Home_page.this,""+e,Toast.LENGTH_SHORT).show();
                    }

                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(Home_page.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();
                }
            }) {

            };


            RequestQueue rQueue = Volley.newRequestQueue(Home_page.this);
            rQueue.add(request);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    progressDialog.dismiss();
                }
            }, 3000); // 3000 milliseconds delay
        }
    }





    }
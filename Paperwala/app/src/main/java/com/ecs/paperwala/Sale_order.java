package com.ecs.paperwala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import java.util.List;

public class Sale_order extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    public static final String sharedPrefFile = "com.ecs.paperwala.file";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String sid;
    Double amount;
    int id;
    private Sale_Order_Adapter adapter;
    List<ModelSaleOrder> modelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_order);
        progressDialog = new ProgressDialog(Sale_order.this);
        progressDialog.setMessage("Loading Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPreferences = getSharedPreferences(Login.sharedPrefFile, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sid = sharedPreferences.getString("id", "id");
        id = Integer.parseInt(sid);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Sale_order.this));

        String URL = "http://paperwala.live/api/webdistributor/GetDistributorSaleDetails?DistributorId=" + id + "";
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>()
        {
            ArrayList<ModelSaleOrder> list = new ArrayList<>();
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray contacts = new JSONArray(s);

                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        ModelSaleOrder u=new ModelSaleOrder();
                        u.setRetailerName(c.getString("RetailerName"));
                        u.setSaleOrder(c.getString("SaleOrder"));
                        u.setPaidAmount(c.getDouble("PaidAmount"));
                        u.setFinalBalaceAmount(c.getDouble("FinalBalaceAmount"));
                        list.add(u);
                    }

                    // Collections.copy(modelList,list);
                    adapter=new Sale_Order_Adapter(Sale_order.this,list);
                    recyclerView.setAdapter(adapter);

                } catch (Exception e) {
                    Toast.makeText(Sale_order.this, "" + e, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Sale_order.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                // Log.d("msg",volleyError.toString());
            }
        }) {

        };


        RequestQueue rQueue = Volley.newRequestQueue(Sale_order.this);
        rQueue.add(request);

    }

}


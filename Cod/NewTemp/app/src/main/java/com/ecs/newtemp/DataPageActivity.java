package com.ecs.newtemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataPageActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private TableRow tableRow;
    private TextView tvCount;
    private TextView tvTemp;
    private TextView tvHumidity;
    private static final String DATA_URL = "https://www.ecssofttech.com/datatest/getdata.php";
    private List<ModelData> list;
    private ProgressDialog progressDialog;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_page);
        mMainNav=(BottomNavigationView)findViewById(R.id.bootom_navigation_bar);
        mMainNav.setSelectedItemId(R.id.share);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home:
                        Intent i1=new Intent(getApplicationContext(), MainActivity2.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(i1);
                        return true;

                    case R.id.search:
                        // Toast.makeText(Homepage.this,"You Click Search",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(), Order.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(i);
                        return true;

                    case R.id.profile:
                        //Toast.makeText(MainActivity2.this,"You Click Profile",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(),Ultrasonic.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(intent);
                        return true;

                    case R.id.share:
                      // Intent in=new Intent(getApplicationContext(),DataPageActivity.class);
                       // overridePendingTransition(0,0);
                      ///  finish();
                        //startActivity(in);
                        //Toast.makeText(Homepage.this, "You Click Share", Toast.LENGTH_SHORT).show();
                        return true;


                }
                return false;
            }
        });
        tableLayout = findViewById(R.id.dpTableLayout_dataTable);
        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        getAllData();
    }

    private void addHeaders() {
        tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TextView tvCountHead = new TextView(this);
        tvCountHead.setText("No.");
        tvCountHead.setTextColor(Color.BLACK);
        tvCountHead.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        tvCountHead.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tvCountHead.setPadding(5,5,5,0);
        tableRow.addView(tvCountHead);
        TextView tvTempHead = new TextView(this);
        tvTempHead.setText("Temp");
        tvTempHead.setTextColor(Color.BLACK);
        tvTempHead.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        tvTempHead.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tvTempHead.setPadding(5,5,5,0);
        tableRow.addView(tvTempHead);
        TextView tvHumidityHead = new TextView(this);
        tvHumidityHead.setText("Humidity");
        tvHumidityHead.setTextColor(Color.BLACK);
        tvHumidityHead.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        tvHumidityHead.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tvHumidityHead.setPadding(5,5,5,0);
        tableRow.addView(tvHumidityHead);
        tableLayout.addView(tableRow);
    }

    private void getAllData() {
        addHeaders();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                ModelData data = new ModelData(object.getInt("id"),
                                        object.getString("temp"),
                                        object.getString("humidity"));
                                list.add(data);
                            }
                            setData();
                        }
                        catch (JSONException e) {
                            progressDialog.dismiss();
                            Toast.makeText(DataPageActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(DataPageActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingletonRequest.getInstance(getApplicationContext()).addToRequestQue(stringRequest);
    }

    private void setData() {
        for (int i=0;i<list.size();i++) {
            ModelData data = list.get(i);
            tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView tvCountHead = new TextView(this);
            tvCountHead.setText(""+(i+1));
            tvCountHead.setTextColor(Color.BLACK);
            tvCountHead.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tvCountHead.setPadding(5,5,5,0);
            tableRow.addView(tvCountHead);

            TextView tvTempHead = new TextView(this);
            tvTempHead.setText(data.getTemp());
            tvTempHead.setTextColor(Color.BLACK);
            tvTempHead.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tvTempHead.setPadding(5,5,5,0);
            tableRow.addView(tvTempHead);

            TextView tvHumidityHead = new TextView(this);
            tvHumidityHead.setText(data.getHumidity());
            tvHumidityHead.setTextColor(Color.BLACK);
            tvHumidityHead.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tvHumidityHead.setPadding(5,5,5,0);
            tableRow.addView(tvHumidityHead);

            tableLayout.addView(tableRow);
        }
        progressDialog.dismiss();


    }

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(DataPageActivity.this, MainActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}

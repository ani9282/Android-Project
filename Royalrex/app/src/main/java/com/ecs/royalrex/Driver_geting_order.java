package com.ecs.royalrex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Driver_geting_order extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DriverOrderAdapter adapter;
    ProgressDialog progressDialog;
    List<ModelClass> modelList;
    private String mobile;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_geting_order);
        progressDialog=new ProgressDialog(Driver_geting_order.this);
        progressDialog.setMessage("Loading Please wait....");
        progressDialog.show();

        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mobile=sharedPreferences.getString("mobile","mobile");

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Driver_geting_order.this));

        SaveData saveData=new SaveData();
        saveData.execute();

        mMainNav=(BottomNavigationView)findViewById(R.id.bootom_navigation_bar);
        mMainNav.setSelectedItemId(R.id.news);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home:
                        Intent intent=new Intent(getApplicationContext(),Driver_home_page.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(intent);
                        return true;
                    case R.id.search:
                        //Toast.makeText(Driver_home_page.this,"You Click Search",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),Driver_profile.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(i);
                        return true;

                    case R.id.profile:
                        //Toast.makeText(Driver_home_page.this,"You Click Profile",Toast.LENGTH_LONG).show();
                        Intent intent1=new Intent(getApplicationContext(),Driver_show_profile.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(intent1);
                        return true;

                    case R.id.share:
                        Intent in=new Intent(getApplicationContext(),Driver_add_vehical.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(in);

                        //Toast.makeText(Driver_home_page.this,"You Click Share",Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.news:
                        // Toast.makeText(Driver_home_page.this,"You Click News",Toast.LENGTH_LONG).show();
                       // Intent intent2=new Intent(getApplicationContext(),Driver_geting_order.class);
                       // overridePendingTransition(0,0);
                       // finish();
                        //startActivity(intent2);
                        return true;

                }
                return false;
            }
        });


    }

    class SaveData extends AsyncTask<String, String, String> {
        boolean isSuccess = false;
        String response;
        //String status="Confirm Order";
        String status;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select * from Royalrex_SenderProfile where owner_mobile='"+mobile+"'";
                    Statement statement = connection.createStatement();
                    ResultSet rs1 = statement.executeQuery(query);
                    modelList = new ArrayList<>();
                    while (rs1.next()) {
                        isSuccess = true;
                        status=rs1.getString("status");
                        if((rs1.getString("role").equals("Sender")) && (status.equals("Confirm Order") || status.equals("Picked Order"))) {
                            ModelClass u = new ModelClass();
                            u.setId(rs1.getInt("id"));
                            // u.setName(rs1.getString("name"));
                            u.setFirst_name(rs1.getString("first_name"));
                            u.setLast_name(rs1.getString("last_name"));
                            u.setMobile(rs1.getString("mobile"));
                            //u.setWeight(rs1.getString("weight"));
                            //u.setDate(rs1.getString("date"));
                            // u.setTime(rs1.getString("time"));
                            // u.setVehical(rs1.getString("vehical"));
                            u.setSource(rs1.getString("source"));
                            u.setDestination(rs1.getString("destination"));
                            u.setStatus(rs1.getString("status"));
                            modelList.add(u);
                        }



                        response = "Data Added Successfully...";
                    }

                } else {

                    response = "Please Connect to internet";
                }

            } catch (SQLException e) {

                Log.d("exceptio", e.toString());
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            if(modelList.size()>0)
            {
                adapter = new DriverOrderAdapter(Driver_geting_order.this,modelList);
                recyclerView.setAdapter(adapter);
            }

            else {
                Intent intent=new Intent(Driver_geting_order.this,Driver_no_result_found.class);
                startActivity(intent);
                finish();
            }

        }
    }

    private Connection connectionMethod(String userName, String password, String databaseName, String ipaddress) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connUrl = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connUrl = "jdbc:jtds:sqlserver://" + ipaddress + ":1433/" + databaseName + ";user=" + userName + ";password=" + password + ";";
            connection = DriverManager.getConnection(connUrl);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;

    } //connection method

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(Driver_geting_order.this, Driver_home_page.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

}
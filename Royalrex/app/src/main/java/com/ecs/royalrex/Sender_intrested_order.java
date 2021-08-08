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

public class Sender_intrested_order extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SenderIntrestedAdapater adapter;
    private ProgressDialog progressDialog;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    List<ModelSender_intrested> modelList;
    private String mobile,date="Interested";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_intrested_order);
        progressDialog=new ProgressDialog(Sender_intrested_order.this);
        progressDialog.setMessage("Loading Please wait.....");
        progressDialog.setCanceledOnTouchOutside(false);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Sender_intrested_order.this));

        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mobile=sharedPreferences.getString("mobile","mobile");
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
                        Intent intent=new Intent(getApplicationContext(),Sender_home_page.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(intent);
                        return true;

                    case R.id.search:
                        //Toast.makeText(Sender_home_page.this,"You Click Search",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),Search_vehical_by_sender.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(i);
                        return true;

                    case R.id.profile:
                        //Toast.makeText(Sender_home_page.this,"You Click Profile",Toast.LENGTH_LONG).show();
                        Intent inten=new Intent(getApplicationContext(),Sender_show_profile.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(inten);
                        return true;

                    case R.id.share:
                        Intent in=new Intent(getApplicationContext(),Sender_profile.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(in);
                        return true;

                    case R.id.news:
                        //Toast.makeText(Sender_intrested_order.this,"You Click News",Toast.LENGTH_LONG).show();
                        //Intent intent1=new Intent(getApplicationContext(),Sender_profile.class);
                        //overridePendingTransition(0,0);
                        //finish();
                        //startActivity(intent1);
                        return true;


                }
                return false;
            }
        });

    }

    class SaveData extends AsyncTask<String, String, String> {
        boolean isSuccess = false;
        String response,status;

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
                    String query = "select * from Royalrex_driver_intrested";
                    Statement statement = connection.createStatement();
                    ResultSet rs1 = statement.executeQuery(query);
                    modelList = new ArrayList<>();
                    while (rs1.next()) {
                        isSuccess = true;




                        status=rs1.getString("status");

                        if(!status.equals("Confirm Order"))
                        {
                            ModelSender_intrested u=new ModelSender_intrested();
                            u.setId(rs1.getInt("id"));
                            u.setDriver_name(rs1.getString("driver_name"));
                            u.setDriver_mobile(rs1.getString("driver_mobile"));
                            u.setVehical_name(rs1.getString("vehical_name"));
                            u.setVehical_number(rs1.getString("vehical_number"));
                            u.setPrice(rs1.getString("price"));
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
                adapter = new SenderIntrestedAdapater(Sender_intrested_order.this,modelList);
                recyclerView.setAdapter(adapter);
            }

            else {
                Intent intent=new Intent(Sender_intrested_order.this,No_result_found.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(Sender_intrested_order.this, Sender_home_page.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

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
}
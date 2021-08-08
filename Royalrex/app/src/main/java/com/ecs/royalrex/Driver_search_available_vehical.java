package com.ecs.royalrex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Driver_search_available_vehical extends AppCompatActivity {

    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    List<ModelDriver> list;
    DriverSearchVehicalAdapter adapter;
    private String owner_mobile;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_search_available_vehical);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading Please Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        owner_mobile=sharedPreferences.getString("mobile","mobile");

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Driver_search_available_vehical.this));

        SaveData saveData=new SaveData();
        saveData.execute();

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
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select * from Royalrex_add_Vehical where owner_mobile='"+owner_mobile+"'";
                    Statement statement = connection.createStatement();
                    ResultSet rs1 = statement.executeQuery(query);
                    list = new ArrayList<>();
                    while (rs1.next())
                    {

                        isSuccess = true;

                        String status=rs1.getString("status");

                        if(status.equals("Empty"))
                        {
                            ModelDriver u=new ModelDriver();
                            u.setId(rs1.getInt("id"));
                            // u.setName(rs1.getString("name"));
                            u.setVehical_name(rs1.getString("vehical_name"));
                            u.setVehical_number(rs1.getString("vehical_number"));
                            u.setDriver_name(rs1.getString("driver_name"));
                            u.setDriver_mobile(rs1.getString("driver_mobile"));
                            list.add(u);
                        }


                        //response = "Data Added Successfully...";
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
            if(list.size()>0)
            {
                adapter=new DriverSearchVehicalAdapter(Driver_search_available_vehical.this,list);
                recyclerView.setAdapter(adapter);
            }

            else
            {
                Intent intent=new Intent(Driver_search_available_vehical.this,Driver_no_result_found.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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


}
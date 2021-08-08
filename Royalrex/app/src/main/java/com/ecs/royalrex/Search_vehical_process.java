package com.ecs.royalrex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Search_vehical_process extends AppCompatActivity  implements LocationListener {

    String Sender_location,date;
    private RecyclerView recyclerView;
    private SearchVehicalAdapter adapter;
    String vehical_name,vehical_number,driver_name,driver_mobile,driver_location,area_zone;
    List<ModelDriver> list;
    ProgressDialog progressDialog;
    double latitude,longitude;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vehical_process);
        progressDialog=new ProgressDialog(Search_vehical_process.this);
        progressDialog.setMessage("Loading Please Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);
        Sender_location= getIntent().getStringExtra("sender_location");
        date= getIntent().getStringExtra("date");
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Search_vehical_process.this));

        mMainNav=(BottomNavigationView)findViewById(R.id.bootom_navigation_bar);
        mMainNav.setSelectedItemId(R.id.search);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home:
                        Intent i=new Intent(getApplicationContext(),Sender_home_page.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(i);
                        return true;

                    case R.id.search:
                        //Toast.makeText(Sender_home_page.this,"You Click Search",Toast.LENGTH_LONG).show();
                       // Intent i=new Intent(getApplicationContext(),Search_vehical_by_sender.class);
                       // overridePendingTransition(0,0);
                       // finish();
                      //  startActivity(i);
                        return true;

                    case R.id.profile:
                        //Toast.makeText(Sender_home_page.this,"You Click Profile",Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.share:
                        Intent in=new Intent(getApplicationContext(),Sender_profile.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(in);
                        return true;

                    case R.id.news:
                        Toast.makeText(Search_vehical_process.this,"You Click News",Toast.LENGTH_LONG).show();
                        return true;


                }
                return false;
            }
        });

        SaveData saveData=new SaveData();
        saveData.execute();


    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
        SaveData saveData=new SaveData();
        saveData.execute();
    }


     */
    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
           Sender_location=addresses.get(0).getAddressLine(0);
            latitude=location.getLatitude();
            longitude=location.getLongitude();
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(Search_vehical_process.this, Sender_home_page.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    class SaveData extends AsyncTask<String, String, String> {
        boolean isSuccess = false;
        String response;
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

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
                    String query = "select * from Royalrex_add_Vehical where  date='"+date+"'";
                    Statement statement = connection.createStatement();
                    ResultSet rs1 = statement.executeQuery(query);
                    list = new ArrayList<>();
                    while (rs1.next())
                    {

                        isSuccess = true;

                        driver_location=rs1.getString("pickup_location");
                        area_zone=rs1.getString("area_zone");

                        double lat,lan;
                        Geocoder geocoder = new Geocoder(Search_vehical_process.this, Locale.getDefault());
                        List<Address> addresses  = geocoder.getFromLocationName(driver_location, 1);
                        Location locationA = new Location(addresses.toString());
                        Address location = addresses.get(0);
                        lat=location.getLatitude();
                        lan=location.getLongitude();

                        locationA.setLatitude(lat);
                        locationA.setLongitude(lan);



                        Geocoder gcd = new Geocoder(Search_vehical_process.this, Locale.getDefault());
                        List<Address> address;
                        address = gcd.getFromLocationName(Sender_location, 1);
                        Location locationB = new Location(address.toString());
                        Address location1 = address.get(0);
                        latitude=location1.getLatitude();
                        longitude=location1.getLongitude();

                        locationB.setLatitude(lat);
                        locationB.setLongitude(lan);






                        double theta = longitude-lan;
                        double dist = Math.sin(Math.toRadians(latitude)) * Math.sin(Math.toRadians(lat)) + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(theta));
                        dist = Math.acos(dist);
                        dist = Math.toDegrees(dist);
                        dist = dist * 60 * 1.1515;
                        dist = dist * 1.609344;




                        double area = Double.parseDouble(area_zone);

                        if(dist<area)
                        {
                            ModelDriver u=new ModelDriver();
                            //u.setId(rs1.getInt("id"));
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
            } catch (SQLException | IOException e) {

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
                adapter = new SearchVehicalAdapter(Search_vehical_process.this,list);
                recyclerView.setAdapter(adapter);
            }

            else
                {
                Intent intent=new Intent(Search_vehical_process.this,No_result_found.class);
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
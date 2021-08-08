package com.ecs.royalrex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*
public class Order_fragment extends AppCompatActivity {

    private List<ModelClass> ordersList;
    private ProgressDialog progressDialog;
    //the recyclerview
    RecyclerView recyclerView;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private ProductAdapter adapter;
    Double latitude, longitude;
   // TextView id,first_name,last_name,mobile;
    String customer_source, current_location, latitude1, longitude1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_fragment);
        progressDialog = new ProgressDialog(Order_fragment.this);
        progressDialog.setMessage("Loading Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);


        /*
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Order_fragment.this));

         */

/*
        current_location = getIntent().getStringExtra("c_location");
        latitude1=getIntent().getStringExtra("latitude");
        longitude1=getIntent().getStringExtra("longitude");
        latitude=Double.parseDouble(latitude1);
        longitude=Double.parseDouble(longitude1);

        SaveData saveData=new SaveData();
        saveData.execute();


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
                    String query = "select * from Royalrex_SenderProfile";
                    Statement statement = connection.createStatement();
                    ResultSet rs1 = statement.executeQuery(query);
                    ordersList = new ArrayList<>();
                    while (rs1.next()) {
                        isSuccess = true;
                        customer_source = rs1.getString("source");
                        //customer_destination=rs1.getString("destination");


                        Location locationA = new Location(current_location);
                        locationA.setLatitude(latitude);
                        locationA.setAltitude(longitude);


                        //Geocoder coder = new Geocoder(Driver_profile.this);
                        double lat, lan;
                        Geocoder gcd = new Geocoder(Order_fragment.this, Locale.getDefault());
                        List<Address> address;
                        address = gcd.getFromLocationName(customer_source, 1);
                        Location locationB = new Location(address.toString());
                        Address location = address.get(0);
                        lat = location.getLatitude();
                        lan = location.getLongitude();

                        locationB.setLatitude(lat);
                        locationB.setLongitude(lan);


                        double theta = longitude - lan;
                        double dist = Math.sin(Math.toRadians(latitude)) * Math.sin(Math.toRadians(lat)) + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(theta));
                        dist = Math.acos(dist);
                        dist = Math.toDegrees(dist);
                        dist = dist * 60 * 1.1515;
                        dist = dist * 1.609344;


                        // double distance1 = locationA.distanceTo(locationB);   // in meters
                        //distance = locationA.distanceTo(locationB)/1000;   // in km
                        // double distance2 = locationA.distanceTo(locationB)/1609.344;   // in miles

                        if (dist < 10) {


                            if (rs1.getString("role").equals("Sender")) {

                                ModelClass u = new ModelClass();
                                u.setId(rs1.getInt("id"));
                               // u.setName(rs1.getString("name"));
                                u.setFirst_name(rs1.getString("first_name"));
                                u.setLast_name(rs1.getString("last_name"));
                                u.setMobile(rs1.getString("mobile"));
                               // u.setWeight(rs1.getString("weight"));
                               // u.setDate(rs1.getString("date"));
                               // u.setTime(rs1.getString("time"));
                               // u.setVehical(rs1.getString("vehical"));
                               // u.setSource(rs1.getString("source"));
                               // u.setDestination(rs1.getString("destination"));
                                ordersList.add(u);



                            }

                            adapter = new ProductAdapter(Order_fragment.this, ordersList);


                        }

                        response = "Data Added Successfully...";
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
            recyclerView.setAdapter(adapter);

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

    }
}

*/
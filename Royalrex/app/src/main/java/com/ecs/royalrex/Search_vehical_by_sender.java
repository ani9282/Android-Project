package com.ecs.royalrex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Search_vehical_by_sender extends AppCompatActivity implements LocationListener {

    EditText s_location,pincode;
    double latitude,longitude;
    Button register,btnDate;
    String date,vehical_name,vehical_number,driver_name,driver_mobile,driver_location,area_zone,Sender_location;
    ProgressDialog progressDialog;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    List<ModelDriver> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vehical_by_sender);
        progressDialog=new ProgressDialog(Search_vehical_by_sender.this);
        progressDialog.setMessage("Loading Pleaase Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);
        s_location=(EditText)findViewById(R.id.location);
        btnDate = findViewById(R.id.date);
        pincode=(EditText)findViewById(R.id.pincode);
        register=(Button)findViewById(R.id.register);


        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Sender_location=s_location.getText().toString()+ " "+pincode.getText().toString();

                if(s_location.getText().toString().isEmpty() || TextUtils.isEmpty(date) || pincode.getText().toString().isEmpty() || pincode.getText().toString().length()<6 || pincode.getText().toString().length()>=7)
                {
                    if(pincode.getText().toString().length()<6 || pincode.getText().toString().length()>=7)
                    {
                        Toast.makeText(Search_vehical_by_sender.this, "Invalid Pincode", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Toast.makeText(Search_vehical_by_sender.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
                    }

                }

                else {
                    Intent intent=new Intent(Search_vehical_by_sender.this,Search_vehical_process.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("sender_location",Sender_location);
                    intent.putExtra("date",date);
                    startActivity(intent);
                    //SaveData saveData=new SaveData();
                   // saveData.execute();
                }
            }
        });

    }

    private void getDate() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DAY = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = dayOfMonth+"-"+(month+1)+"-"+year;
                btnDate.setText(date);
            }
        },YEAR,MONTH,DAY);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        /*
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Sender_location=addresses.get(0).getAddressLine(0);
            latitude=location.getLatitude();
            longitude=location.getLongitude();
        } catch (Exception e) {
        }

         */
    }

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(Search_vehical_by_sender.this, Sender_home_page.class);
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
                        vehical_name=rs1.getString("vehical_name");
                        vehical_number=rs1.getString("vehical_number");
                        driver_name=rs1.getString("driver_name");
                        driver_mobile=rs1.getString("driver_mobile");
                        driver_location=rs1.getString("pickup_location");
                        area_zone=rs1.getString("area_zone");

                        double lat,lan;
                        Geocoder gcd = new Geocoder(Search_vehical_by_sender.this, Locale.getDefault());
                        List<Address> address;
                        address = gcd.getFromLocationName(driver_location, 1);
                        Location locationB = new Location(address.toString());
                        Address location = address.get(0);
                        lat=location.getLatitude();
                        lan=location.getLongitude();

                        locationB.setLatitude(lat);
                        locationB.setLongitude(lan);



                        Geocoder gcd1= new Geocoder(Search_vehical_by_sender.this, Locale.getDefault());
                        List<Address> address1;
                        address1 = gcd1.getFromLocationName(Sender_location, 1);
                        Location locationA = new Location(address1.toString());
                        Address location1 = address1.get(0);
                        latitude=location1.getLatitude();
                        longitude=location1.getLongitude();






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
                            u.setVehical_name(rs1.getString("first_name"));
                            u.setVehical_number(rs1.getString("last_name"));
                            u.setDriver_name(rs1.getString("mobile"));
                            u.setDriver_mobile(rs1.getString("source"));
                            list.add(u);
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
            /*
            adapter = new ProductAdapter(Search_vehical_by_sender.this,modelList);
           // recyclerView.setAdapter(adapter);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    progressDialog.dismiss();
                }
            }, 2000); // 2000 milliseconds delay

             */

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



}//end Program
package com.ecs.royalrex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.widget.LinearLayout.LayoutParams;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Driver_profile extends AppCompatActivity  implements LocationListener {

    String current_location,customer_source,customer_destination,mobile;
    double latitude, longitude;
    //float distance;
    String date;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    LocationManager locationManager;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    List<ModelClass> modelList;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    private int sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        progressDialog = new ProgressDialog(Driver_profile.this);
        progressDialog.setMessage("Loading Your Current Location Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);


        Intent intent = getIntent();
        sid = intent.getIntExtra(ProductAdapter.DELIVERED_ORDER_ID,0);
        //date=java.text.DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1); //for yesterday date
        Date date1 = cal.getTime();
        date = dateFormat.format(date1);






        SaveData saveData=new SaveData();
        saveData.execute();

        /*
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }


        locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationEnabled();
        getLocation();


         */




        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Driver_profile.this));




        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mobile=sharedPreferences.getString("mobile","mobile");


        mMainNav=(BottomNavigationView)findViewById(R.id.bootom_navigation_bar);
        mMainNav.setSelectedItemId(R.id.search);
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
                        //Toast.makeText(Driver_show_profile.this,"You Click Profile",Toast.LENGTH_LONG).show();
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
                        Intent intent2=new Intent(getApplicationContext(),Driver_geting_order.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(intent2);
                        return true;

                }
                return false;
            }
        });



        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SaveData saveData=new SaveData();
                saveData.execute();
                pullToRefresh.setRefreshing(false);
            }
        });

    }




    private void locationEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(Driver_profile.this)
                    .setTitle("Enable GPS Service")
                    .setMessage("We need your GPS location to show Near Places around you.")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    void getLocation() {

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
             current_location=addresses.get(0).getAddressLine(0);
             latitude=location.getLatitude();
             longitude=location.getLongitude();
        } catch (Exception e) {
        }
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }









    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(Driver_profile.this, Driver_home_page.class);
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
                    String query = "select * from Royalrex_SenderProfile where date>='"+date+"'";
                    Statement statement = connection.createStatement();
                    ResultSet rs1 = statement.executeQuery(query);
                    modelList = new ArrayList<>();
                    while (rs1.next()) {
                        isSuccess = true;
                        //customer_source=rs1.getString("source");
                        //customer_destination=rs1.getString("destination");

                        /*
                        Location locationA = new Location(current_location);
                        locationA.setLatitude(latitude);
                        locationA.setAltitude(longitude);



                        //Geocoder coder = new Geocoder(Driver_profile.this);
                        double lat,lan;
                        Geocoder gcd = new Geocoder(Driver_profile.this, Locale.getDefault());
                        List<Address> address;
                        address = gcd.getFromLocationName(customer_source, 1);
                        Location locationB = new Location(address.toString());
                        Address location = address.get(0);
                        lat=location.getLatitude();
                        lan=location.getLongitude();

                        locationB.setLatitude(lat);
                        locationB.setLongitude(lan);


                        double theta = longitude-lan;
                        double dist = Math.sin(Math.toRadians(latitude)) * Math.sin(Math.toRadians(lat)) + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(theta));
                        dist = Math.acos(dist);
                        dist = Math.toDegrees(dist);
                        dist = dist * 60 * 1.1515;
                        dist = dist * 1.609344;



                       // double distance1 = locationA.distanceTo(locationB);   // in meters
                       //distance = locationA.distanceTo(locationB)/1000;   // in km
                       // double distance2 = locationA.distanceTo(locationB)/1609.344;   // in miles


                         */

                           String status=rs1.getString("status");
                           int id=rs1.getInt("id");
                            if((rs1.getString("role").equals("Sender") && status.equals("Pending Order")) )
                            {

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
            /*
            try {
                connection = connectionMethod(USER_NAME,PASSWORD,DATABASE_NAME,IPADDRESS);
                if (connection != null) {
                    String query = "update  Royalrex set location='"+current_location+"' where mobile='"+mobile+"'";
                    Statement statement = connection.createStatement();
                    int resultSet = statement.executeUpdate(query);
                    if (resultSet == 0) {
                        response = "Some Error Occured";
                    }
                    else {
                        isSuccess = true;
                    }

                }
                else {
                    response = "Check Your Internet Connection";
                }
            }
            catch (SQLException e) {
                Log.d("exceptio",e.toString());
            }


             */



               //setlocation();
                if(modelList.size()>0)
                {
                    adapter = new ProductAdapter(Driver_profile.this,modelList,sid);
                    recyclerView.setAdapter(adapter);
                }

                else {
                    Intent intent=new Intent(Driver_profile.this,Driver_no_result_found.class);
                    startActivity(intent);
                }

                /*
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000); // 2000 milliseconds delay

                 */



        }
    }  //end save data method



    private void setlocation()
    {
        try {
            connection = connectionMethod(USER_NAME,PASSWORD,DATABASE_NAME,IPADDRESS);
            if (connection != null) {
                String query = "update  Royalrex set location='"+current_location+"' where mobile='"+mobile+"'";
                Statement statement = connection.createStatement();
                int resultSet = statement.executeUpdate(query);
                if (resultSet == 0) {
                   // response = "Some Error Occured";
                }
                else {
                   // isSuccess = true;
                }

            }
            else {
                //response = "Check Your Internet Connection";
            }
        }
        catch (SQLException e) {
            Log.d("exceptio",e.toString());
        }

    }


    /*
    @Override
    protected void onResume() {
        super.onResume();


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }



        //locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
       // locationEnabled();
       //getLocation();
      SaveData saveData=new SaveData();
      saveData.execute();
    }


     */



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

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator=getMenuInflater();
        inflator.inflate(R.menu.driver_profile_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.corder:
                Toast.makeText(Driver_profile.this,"You Click Track MyOrder",Toast.LENGTH_LONG).show();
                return true;
            case R.id.dlogout:
                editor.clear();
                editor.apply();
                Intent intent = new Intent(Driver_profile.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


     */



}
package com.ecs.royalrex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Sender_home_page extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
private BottomNavigationView mMainNav;
private FrameLayout mMainFrame;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    private SharedPreferences.Editor editor;
    private String mobile,name="",source="",destination="",pan_card="",adhar_card="",driving_licence="";
    int order_id;
    private Connection connection;

    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private TextView link_adhar,link_pancard,link_licence;

    TextView tname,tsource,tdestination,tpancard,tadharcard,tdrivinglicence;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_home_page);
        progressDialog=new ProgressDialog(Sender_home_page.this);
        progressDialog.setMessage("Loading Please Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);

        //remeber me
        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mobile=sharedPreferences.getString("mobile","mobile");



        tname=(TextView)findViewById(R.id.name);
        tsource=(TextView)findViewById(R.id.source);
        tdestination=(TextView)findViewById(R.id.destination);
        tpancard=(TextView)findViewById(R.id.pancard);
        tadharcard=(TextView)findViewById(R.id.adharcard);
        tdrivinglicence=(TextView)findViewById(R.id.drivinglicence);
        link_adhar=(TextView)findViewById(R.id.linkadhar);
        link_pancard=(TextView)findViewById(R.id.linkpan);
        link_licence=(TextView)findViewById(R.id.link_licence);
        mMainNav=(BottomNavigationView)findViewById(R.id.bootom_navigation_bar);
        mMainNav.setSelectedItemId(R.id.home);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home:
                        //Toast.makeText(Sender_home_page.this,"You Click Home",Toast.LENGTH_LONG).show();
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
                        Intent intent=new Intent(getApplicationContext(),Sender_show_profile.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(intent);
                        return true;

                    case R.id.share:
                        Intent in=new Intent(getApplicationContext(),Sender_profile.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(in);
                        return true;

                    case R.id.news:
                        //Toast.makeText(Sender_home_page.this,"You Click News",Toast.LENGTH_LONG).show();
                        Intent intent2=new Intent(getApplicationContext(),Sender_intrested_order.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(intent2);
                        return true;


                }
                return false;
            }
        });

        link_adhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Sender_home_page.this,Link_Adhar_card.class);
                startActivity(intent);
            }
        });

        link_pancard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Sender_home_page.this,Link_pan_card.class);
                startActivity(intent);
            }
        });

        link_licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Sender_home_page.this,Link_driving_Licence.class);
                startActivity(intent);
            }
        });

        SaveData saveData=new SaveData();
        saveData.execute();

        //SaveData1 saveData1=new SaveData1();
        //saveData1.execute();

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    private class SaveData extends AsyncTask<Void,Void,String> {
        private String response;
        private boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select top 1 * from Royalrex_SenderProfile  where mobile='"+mobile+"' order by id desc;";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {
                        order_id = resultSet.getInt("id");
                    }
                } else {
                    response = "Please Connect to internet";
                }
            } catch (SQLException e) {

                Log.d("exceptio", e.toString());
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select  * from Royalrex_SenderProfile  where id='"+order_id+"';";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    if(resultSet.next()) {
                        order_id = resultSet.getInt("id");
                        name=resultSet.getString("name");
                        source=resultSet.getString("source");
                        destination=resultSet.getString("destination");

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
            tname.setText(": "+name);
            tsource.setText(": "+source);
            tdestination.setText(": "+destination);
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select  * from royalrex where mobile='"+mobile+"';";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    if(resultSet.next()) {
                        pan_card=resultSet.getString("pancard");
                        adhar_card=resultSet.getString("adharcard");
                       driving_licence=resultSet.getString("driving_licence");

                    }
                } else {
                    response = "Please Connect to internet";
                }
            } catch (SQLException e) {

                Log.d("exceptio", e.toString());
            }


            if(pan_card!=null)
            {
                tpancard.setText(":Updated");
            }

            else {
                tpancard.setText(":Not Update");
            }

            if(adhar_card!=null)
            {
                tadharcard.setText(":Updated");
            }

            else {
                tadharcard.setText(":Not Updated");
            }

            if(driving_licence!=null)
            {
                tdrivinglicence.setText("Updated");
            }

            else {
                tdrivinglicence.setText(":Not Updated");
            }
            progressDialog.dismiss();
        }
    }


    private class SaveData1 extends AsyncTask<Void,Void,String> {
        private String response;
        private boolean isSuccess = false;

        @Override
        protected String doInBackground(Void... voids) {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select  * from Royalrex where id='"+order_id+"';";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    if(resultSet.next()) {
                        pan_card=resultSet.getString("pancard");
                        adhar_card=resultSet.getString("adharcard");
                        driving_licence=resultSet.getString("driving_licence");
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
            if(pan_card.equals("") || adhar_card.equals("") || driving_licence.equals(""))
            {
                tpancard.setText("Not Update");
                tadharcard.setText("Not Update");
                tdrivinglicence.setText("Not Update");
            }

            else {
                tpancard.setText("Updated");
                tadharcard.setText("Updated");
                tdrivinglicence.setText("Updated");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator=getMenuInflater();
        inflator.inflate(R.menu.sender_home_logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.driver:
                Intent intent1 = new Intent(Sender_home_page.this,Driver_home_page.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                finish();
                return true;

            case R.id.logout:
                editor.clear();
                editor.apply();
                Intent intent = new Intent(Sender_home_page.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
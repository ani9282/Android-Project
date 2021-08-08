package com.ecs.royalrex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Sender_show_profile extends AppCompatActivity {

    private String mobile;
    TextView efname,elname,emobile,eemail,erole;
    Button edit_profile;
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    private SharedPreferences.Editor editor;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    ProgressDialog progressDialog;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_show_profile);
        progressDialog=new ProgressDialog(Sender_show_profile.this);
        progressDialog.setMessage("Loading Please Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);
        efname=(TextView)findViewById(R.id.fname);
        elname=(TextView) findViewById(R.id.lname);
        emobile=(TextView) findViewById(R.id.mobile);
        eemail=(TextView) findViewById(R.id.email);
        erole=(TextView)findViewById(R.id.drole);
        edit_profile=(Button)findViewById(R.id.edit_profile);
        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        imageView=(ImageView)findViewById(R.id.profile_photo);
        mobile=sharedPreferences.getString("mobile","mobile");


        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Sender_show_profile.this,Sender_edit_profile.class);
                startActivity(intent);
            }
        });



        SaveData saveData=new SaveData();
        saveData.execute();
        mMainNav=(BottomNavigationView)findViewById(R.id.bootom_navigation_bar);
        mMainNav.setSelectedItemId(R.id.profile);
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
                       // Intent intent=new Intent(getApplicationContext(),Sender_show_profile.class);
                       // overridePendingTransition(0,0);
                       // finish();
                      //  startActivity(intent);
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



    }

    private class SaveData extends AsyncTask<Void,Void,String> {
        private String response;
        private boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids)
        {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select * from Royalrex where mobile='"+mobile+"'";
                    Statement statement = connection.createStatement();
                    ResultSet rs1 = statement.executeQuery(query);
                    if (rs1.next())
                    {
                            isSuccess = true;
                        setText(efname,rs1.getString("first_name"));
                        setText(elname,rs1.getString("last_name"));
                        setText(emobile,rs1.getString("mobile"));
                        setText(eemail,rs1.getString("email"));
                        setText(erole,rs1.getString("role"));
                        //imageView.setImageDrawable();
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
        }
    }

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(Sender_show_profile.this, Sender_home_page.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    private void setText(final TextView text,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(":"+value);
            }
        });
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
        inflator.inflate(R.menu.sender_profile_change_password,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.password:
                Intent intent = new Intent(Sender_show_profile.this,Sender_change_password.class);
                startActivity(intent);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
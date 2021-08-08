package com.ecs.newtemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Order extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private TextView name,qty,type;
    private Button register;
    private ProgressDialog progressDialog;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading Please Wait.......");
        progressDialog.setCanceledOnTouchOutside(false);
        name=(TextView)findViewById(R.id.name);
        qty=(TextView)findViewById(R.id.qty);
        type=(TextView)findViewById(R.id.type);
        register=(Button)findViewById(R.id.register);
        mMainNav=(BottomNavigationView)findViewById(R.id.bootom_navigation_bar);
        mMainNav.setSelectedItemId(R.id.search);
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
                       // Intent i=new Intent(getApplicationContext(), Order.class);
                       // overridePendingTransition(0,0);
                       // finish();
                       // startActivity(i);
                        return true;

                    case R.id.profile:
                        //Toast.makeText(MainActivity2.this,"You Click Profile",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(),Ultrasonic.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(intent);
                        return true;

                    case R.id.share:
                        Intent in=new Intent(getApplicationContext(),DataPageActivity.class);
                        overridePendingTransition(0,0);
                          finish();
                        startActivity(in);
                        //Toast.makeText(Homepage.this, "You Click Share", Toast.LENGTH_SHORT).show();
                        return true;


                }
                return false;
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty() || qty.getText().toString().isEmpty() || type.getText().toString().isEmpty())
                {
                    Toast.makeText(Order.this, "All Field Required", Toast.LENGTH_SHORT).show();
                }
                else {
                        SaveData saveData=new SaveData();
                        saveData.execute();
                }
            }
        });
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
                    String query = "insert into smart_refrigerator_registration(name,qty,type) values('"+name.getText().toString()+"','"+qty.getText().toString()+"','"+type.getText().toString()+"')";
                    Statement statement = connection.createStatement();
                    int resultSet = statement.executeUpdate(query);
                    if (resultSet > 0) {
                        isSuccess = true;
                        response = "Order Placed Successfully";
                    } else {
                        response = "Some Error..Order not placed";
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
            AlertDialog.Builder dialog=new AlertDialog.Builder(Order.this);
            dialog.setMessage("Registration Successfully Thank You......");
            dialog.setTitle("Registration Alert");
            dialog.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            Intent i=new Intent(Order.this,MainActivity2.class);
                            startActivity(i);
                        }
                    });

            AlertDialog alertDialog=dialog.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
    }

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(Order.this, MainActivity2.class);
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
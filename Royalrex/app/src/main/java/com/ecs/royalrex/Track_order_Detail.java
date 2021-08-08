package com.ecs.royalrex;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Track_order_Detail extends AppCompatActivity {

    int sid;
    TextView tid,material_name,source,destination,weight,date,time,vehical,dname,dmobile,dlocation,status;
    Button register;
    String driver_mobile,location;
    private ProgressDialog progressDialog;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private ModelClass detailOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order__detail);
        Intent intent = getIntent();
        sid = intent.getIntExtra(TrackAdapter.DELIVERED_ORDER_ID,0);
        progressDialog = new ProgressDialog(Track_order_Detail.this);
        progressDialog.setMessage("Loading Please Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);


         tid=(TextView)findViewById(R.id.id);
        material_name=(TextView) findViewById(R.id.mname);
        weight=(TextView)findViewById(R.id.weight);
        date=(TextView)findViewById(R.id.date);
        time=(TextView)findViewById(R.id.time);
        vehical=(TextView)findViewById(R.id.vehical);
        dname=(TextView)findViewById(R.id.dname);
        dmobile=(TextView)findViewById(R.id.dmobile);
        dlocation=(TextView)findViewById(R.id.location);
        status=(TextView)findViewById(R.id.status);
        source=(TextView) findViewById(R.id.source);
        destination=(TextView) findViewById(R.id.destination);
       register=findViewById(R.id.complete_order);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Track_order_Detail.this,"You Click Track Order",Toast.LENGTH_LONG).show();
                try {
                    Uri uri=Uri.parse("http://maps.google.co.in/maps?q="+location);
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    intent.setPackage("com.google.android.apps.maps");
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }

                catch (ActivityNotFoundException e){
                    Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });



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
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select * from Royalrex_SenderProfile where id='"+sid+"'";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    if (resultSet.next()) {
                        isSuccess = true;
                        driver_mobile=resultSet.getString("driver_mobile");

                    }

                } else {

                    response = "Please Connect to internet";
                }


            }  catch (SQLException e) {
                Log.d("exceptio",e.toString());
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select * from Royalrex where mobile='"+driver_mobile+"'";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    if (resultSet.next()) {
                        isSuccess = true;
                        // tid.setText(Integer.toString(resultSet.getInt("id")));
                        location=resultSet.getString("location");

                    }

                } else {

                    response = "Please Connect to internet";
                }


            }  catch (SQLException e) {
                Log.d("exceptio",e.toString());
            }
            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select * from Royalrex_SenderProfile where id='"+sid+"'";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    if (resultSet.next()) {
                        isSuccess = true;
                        // tid.setText(Integer.toString(resultSet.getInt("id")));

                        String id=Integer.toString(sid);
                        setText(tid,id);
                        setText(material_name,resultSet.getString("name"));
                        setText(weight,resultSet.getString("weight"));
                        setText(date,resultSet.getString("date"));
                        setText(time,resultSet.getString("time"));
                        setText(vehical,resultSet.getString("vehical"));
                        setText(source,resultSet.getString("source"));
                        setText(destination,resultSet.getString("destination"));
                        setText(dname,resultSet.getString("driver_name"));
                        setText(dmobile,resultSet.getString("driver_mobile"));
                        setText(dlocation,location);
                        setText(status,resultSet.getString("status"));


                    }

                } else {

                    response = "Please Connect to internet";
                }


            }  catch (SQLException e) {
                Log.d("exceptio",e.toString());
            }
            //return response;
            progressDialog.dismiss();
        }
    }



    private void setText(final TextView text,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
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
    }


}
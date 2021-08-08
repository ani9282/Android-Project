package com.ecs.royalrex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sender_detail_intrested extends AppCompatActivity {

    int sid;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    ProgressDialog progressDialog;
    String driver_mobile,owner_mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_detail_intrested);
        progressDialog=new ProgressDialog(Sender_detail_intrested.this);
        progressDialog.setMessage("Loading Please Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);
        Intent intent = getIntent();
        sid = intent.getIntExtra(SenderIntrestedAdapater.DELIVERED_ORDER_ID,0);
        driver_mobile = intent.getStringExtra(SenderIntrestedAdapater.driver_mobile);

        SaveData saveData=new SaveData();
        saveData.execute();
    }

    class SaveData extends AsyncTask<String, String, String> {
        boolean isSuccess = false;
        String response,status="Confirm Order";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select  * from Royalrex_add_Vehical  where driver_mobile='"+driver_mobile+"' ";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    if (resultSet.next()) {
                        isSuccess = true;
                        // tid.setText(Integer.toString(resultSet.getInt("id")));
                        owner_mobile=resultSet.getString("owner_mobile");

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
                connection = connectionMethod(USER_NAME,PASSWORD,DATABASE_NAME,IPADDRESS);
                if (connection != null) {
                    String query = "update  Royalrex_SenderProfile set status='"+status+"',owner_mobile='"+owner_mobile+"' where id='"+sid+"'";
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
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            String status1="Confirm Order";

            try {
                connection = connectionMethod(USER_NAME,PASSWORD,DATABASE_NAME,IPADDRESS);
                if (connection != null) {
                    String query = "update Royalrex_driver_intrested set status='"+status1+"' where id='"+sid+"'";
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



            Intent intent = new Intent(Sender_detail_intrested.this, Sender_intrested_order.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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
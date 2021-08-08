package com.ecs.royalrex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Driver_details_activity extends AppCompatActivity {

    TextView tid,name,first_name,last_name,emobile,weight,date,time,vehical,source,destination,estatus,pickup_type;
    Button complete_order,pick_order;
    ProgressDialog progressDialog;
    List<ModelClass> modelList;
    private String mobile,driver_name,driver_mobile,vehical_name,vehical_number,price,sender_mobile,material_name;
    int sid;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details_activity);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading Please wait....");
        progressDialog.show();
        Intent intent = getIntent();
        sid = intent.getIntExtra(ProductAdapter.DELIVERED_ORDER_ID,0);
        tid=(TextView)findViewById(R.id.id);
        name=(TextView)findViewById(R.id.mname);
        first_name=(TextView)findViewById(R.id.fname);
        last_name=(TextView)findViewById(R.id.lname);
        emobile=(TextView)findViewById(R.id.mobile);
        weight=(TextView)findViewById(R.id.mweight);
        date=(TextView)findViewById(R.id.date);
        time=(TextView)findViewById(R.id.time);
        vehical=(TextView)findViewById(R.id.vehical);
        source=(TextView)findViewById(R.id.source);
        destination=(TextView)findViewById(R.id.destination);
        estatus=(TextView)findViewById(R.id.status);
        pickup_type=(TextView)findViewById(R.id.type);
        complete_order=(Button)findViewById(R.id.corder);
        pick_order=(Button)findViewById(R.id.register);

        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mobile=sharedPreferences.getString("mobile","mobile");


        SaveData saveData=new SaveData();
        saveData.execute();

        pick_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                PickedOrder pickedOrder=new PickedOrder();
                pickedOrder.execute();
            }
        });

        complete_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    CompleteOrder completeOrder=new CompleteOrder();
                    completeOrder.execute();
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
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select * from Royalrex_driver_intrested where id='" + sid + "'";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    if (resultSet.next()) {
                        isSuccess = true;


                       driver_name= resultSet.getString("driver_name");
                        driver_mobile= resultSet.getString("driver_mobile");
                        vehical_name=resultSet.getString("vehical_name");
                       vehical_number= resultSet.getString("vehical_number");
                         price=resultSet.getString("price");
                        sender_mobile= resultSet.getString("sender_mobile");

                    }

                } else {

                    response = "Please Connect to internet";
                }


            } catch (SQLException e) {
                Log.d("exceptio", e.toString());
            }



        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select * from Royalrex_SenderProfile where id='" + sid + "'";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    if (resultSet.next()) {
                        isSuccess = true;

                        String id = Integer.toString(sid);
                        setText(tid, id);
                        setText(name, resultSet.getString("name"));
                        material_name=resultSet.getString("name");
                        setText(first_name, resultSet.getString("first_name"));
                        setText(last_name, resultSet.getString("last_name"));
                        setText(emobile, resultSet.getString("mobile"));
                        setText(weight, resultSet.getString("weight"));
                        setText(date, resultSet.getString("date"));
                        setText(time, resultSet.getString("time"));
                        setText(vehical, resultSet.getString("vehical"));
                        setText(source, resultSet.getString("source"));
                        setText(destination, resultSet.getString("destination"));
                        setText(estatus, resultSet.getString("status"));
                        setText(pickup_type, resultSet.getString("pickup_category"));
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

        private void setText(final TextView text, final String value) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText(value);
                }
            });
    }



        class PickedOrder extends AsyncTask<String, String, String> {
            boolean isSuccess = false;
            String response, status="Picked Order",status1="Reserved";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
                try {
                    connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                    if (connection != null) {
                        String query = "update Royalrex_add_Vehical set status='"+status1+"' where id='"+sid+"'";
                        Statement statement = connection.createStatement();
                        int resultSet = statement.executeUpdate(query);
                        if (resultSet > 0) {
                            isSuccess = true;
                            response = "Data Added Successfully...";
                        } else {
                            response = "Some Error..Order not placed";
                        }
                    } else {


                        response = "Please Connect to internet";
                    }
                } catch (SQLException e) {

                    Log.d("exceptio", e.toString());
                }


                
            }

            @Override
            protected String doInBackground(String... strings)
            {
                try {
                    connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                    if (connection != null) {
                        String query = "update Royalrex_SenderProfile set status='"+status+"' where id='"+sid+"'";
                        Statement statement = connection.createStatement();
                        int resultSet = statement.executeUpdate(query);
                        if (resultSet > 0) {
                            isSuccess = true;
                            response = "Data Added Successfully...";
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
                String user = "rahulecs";
                String password = "rahulecs";
                String number =sender_mobile;
                String message="Welcome Royalrex Courier Your Order id ="+sid+" Material Name "+material_name+"Order Picked name ="+driver_name+" Mobile No "+driver_mobile+" Vehical Name "+vehical_name+" Vehical Number "+vehical_number+" Courier Price is="+price;
                String SID = "HINFRM";
                String smssigma = "http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user+"&password="+password+"&msisdn="+number+"&sid="+SID+"&msg="+message+"&fl=0"+"&dc=8"+"&gwid=2";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, smssigma, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Driver_details_activity.this, "Your OTP send your Mobile Number Successfully....", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Driver_details_activity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


                RequestQueue requestQueue = Volley.newRequestQueue(Driver_details_activity.this);
                requestQueue.add(stringRequest);
                Intent intent = new Intent(Driver_details_activity.this,Driver_geting_order.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }

    class CompleteOrder extends AsyncTask<String, String, String> {
        boolean isSuccess = false;
        String response, status = "Complete Order",status1 = "Empty";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "update Royalrex_add_Vehical set status='"+status1+"' where vehical_number='"+vehical_number+"'";
                    Statement statement = connection.createStatement();
                    int resultSet = statement.executeUpdate(query);
                    if (resultSet > 0) {
                        isSuccess = true;
                        response = "Data Added Successfully...";
                    } else {
                        response = "Some Error..Order not placed";
                    }
                } else {


                    response = "Please Connect to internet";
                }
            } catch (SQLException e) {

                Log.d("exceptio", e.toString());
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "update Royalrex_SenderProfile set status='"+status+"' where id='"+sid+"'";
                    Statement statement = connection.createStatement();
                    int resultSet = statement.executeUpdate(query);
                    if (resultSet > 0) {
                        isSuccess = true;
                        response = "Data Added Successfully...";
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
            String user = "rahulecs";
            String password = "rahulecs";
            String number =sender_mobile;
            String message="Welcome Royalrex Courier Your Order id ="+sid+" Material Name is "+material_name+" Succesfully Completed Thank You Using Royalrex Services Visit Again";
            String SID = "HINFRM";
            String smssigma2 = "http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user+"&password="+password+"&msisdn="+number+"&sid="+SID+"&msg="+message+"&fl=0"+"&dc=8"+"&gwid=2";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, smssigma2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(Driver_details_activity.this, "Details Send To Sender", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Driver_details_activity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(Driver_details_activity.this);
            requestQueue.add(stringRequest);
            Intent intent = new Intent(Driver_details_activity.this,Driver_geting_order.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

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
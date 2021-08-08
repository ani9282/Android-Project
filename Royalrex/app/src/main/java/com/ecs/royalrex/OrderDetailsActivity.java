package com.ecs.royalrex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderDetailsActivity extends AppCompatActivity {

    int sid,driver_id;
    String pending_order_status,owner_mobile,dmobile,dname,dlastname,dlocation,amount,vehical_name,vehical_number,driver_mobile,sender_mobile;
    Button complete_order,interested;
    TextView tid,name,first_name,last_name,mobile,weight,date,time,vehical,source,destination,status,pickup_type;
    private ProgressDialog progressDialog;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";

    //Shared prefferences
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Intent intent = getIntent();
        sid = intent.getIntExtra(ProductAdapter.DELIVERED_ORDER_ID,0);
        driver_id=intent.getIntExtra("driver_id",sid);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Please Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);

        //remember me
        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        owner_mobile=sharedPreferences.getString("mobile","mobile");
        tid=(TextView)findViewById(R.id.id);
        name=(TextView)findViewById(R.id.mname);
        first_name=(TextView)findViewById(R.id.fname);
        last_name=(TextView)findViewById(R.id.lname);
        mobile=(TextView)findViewById(R.id.mobile);
        weight=(TextView)findViewById(R.id.mweight);
        date=(TextView)findViewById(R.id.date);
        time=(TextView)findViewById(R.id.time);
        vehical=(TextView)findViewById(R.id.vehical);
        source=(TextView)findViewById(R.id.source);
        destination=(TextView)findViewById(R.id.destination);
        status=(TextView)findViewById(R.id.status);
        pickup_type=(TextView)findViewById(R.id.type);
        //complete_order=(Button)findViewById(R.id.complete_order);
        interested=(Button)findViewById(R.id.interested);
        SaveData saveData=new SaveData();
        saveData.execute();

        /*
        complete_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailsActivity.this);
                builder.setTitle("Confirm Complete Order");
                builder.setMessage("are you sure");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       CompleteOrder completeOrder = new CompleteOrder();
                        completeOrder.execute();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
            }
        });

         */

        interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailsActivity.this);

                builder.setTitle("Enter amount for This Order");

// Set up the input
                final EditText input = new EditText(OrderDetailsActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);



// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        amount = input.getText().toString();
                        PendingOrder pendingOrder=new PendingOrder();
                        pendingOrder.execute();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setCancelable(false);
                builder.show();
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
                    String query = "select * from Royalrex_SenderProfile where id='"+sid+"'";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    if (resultSet.next()) {
                        isSuccess = true;
                       // tid.setText(Integer.toString(resultSet.getInt("id")));

                        /*
                        tid.setText(Integer.toString(sid));
                        name.setText(resultSet.getString("name"));
                        first_name.setText(resultSet.getString("first_name"));
                        last_name.setText(resultSet.getString("last_name"));
                        mobile.setText(resultSet.getString("mobile"));
                        weight.setText(resultSet.getString("weight")+"Kg");
                        date.setText(resultSet.getString("date"));
                        time.setText(resultSet.getString("time"));
                        vehical.setText(resultSet.getString("vehical"));
                        source.setText(resultSet.getString("source"));
                        destination.setText(resultSet.getString("destination"));

                         */
                        String id=Integer.toString(sid);
                        setText(tid,id);
                        setText(name,resultSet.getString("name"));
                        setText(first_name,resultSet.getString("first_name"));
                        setText(last_name,resultSet.getString("last_name"));
                        setText(mobile,resultSet.getString("mobile"));
                        sender_mobile=resultSet.getString("mobile");
                        setText(weight,resultSet.getString("weight"));
                        setText(date,resultSet.getString("date"));
                        setText(time,resultSet.getString("time"));
                        setText(vehical,resultSet.getString("vehical"));
                        setText(source,resultSet.getString("source"));
                        setText(destination,resultSet.getString("destination"));
                        setText(status,resultSet.getString("status"));
                        setText(pickup_type,resultSet.getString("pickup_category"));
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
                    String query = "select  * from Royalrex_add_Vehical  where id='"+driver_id+"' ";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    if (resultSet.next()) {
                        isSuccess = true;
                        // tid.setText(Integer.toString(resultSet.getInt("id")));
                         dname=resultSet.getString("driver_name");
                         dmobile=resultSet.getString("driver_mobile");
                         vehical_name=resultSet.getString("vehical_name");
                         vehical_number=resultSet.getString("vehical_number");

                    }

                } else {

                    response = "Please Connect to internet";
                }


            }  catch (SQLException e) {
                Log.d("exceptio",e.toString());
            }

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





    private class PendingOrder extends AsyncTask<Void,Void,String> {
        private String response;
        private boolean isSuccess = false;
        String status="Interested";
        String status1="Booked";
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "update Royalrex_add_Vehical  set status='"+status1+"' where id='"+driver_id+"'";
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
        protected String doInBackground(Void... voids) {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "insert into Royalrex_driver_intrested (id,sender_mobile,driver_name,driver_mobile,vehical_name,vehical_number,price,status) values('"+sid+"','"+sender_mobile+"','"+dname+"','"+dmobile+"','"+vehical_name+"','"+vehical_number+"','"+amount+"','"+status1+"')";
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


            }  catch (SQLException e) {
                Log.d("exceptio",e.toString());
            }

            return response;
        }

//String query = "insert into Royalrex_driver_intrested (id,driver_mobile) values('"+sid+"','"+dmobile+"')";

        @Override
        protected void onPostExecute(String s) {
            if (isSuccess) {


                progressDialog.dismiss();
                Toast.makeText(OrderDetailsActivity.this,"Your Order Complete Successfully.....", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderDetailsActivity.this,Driver_search_available_vehical.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
            else {
                progressDialog.dismiss();
                //Toast.makeText(DeliveredOrderDetailsActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }
    }


}
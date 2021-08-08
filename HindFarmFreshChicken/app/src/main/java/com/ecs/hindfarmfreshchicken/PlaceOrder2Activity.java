package com.ecs.hindfarmfreshchicken;

import android.Manifest;
import android.app.Activity;

import instamojo.library.InstapayListener;
import instamojo.library.InstamojoPay;

import org.json.JSONObject;
import org.json.JSONException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class PlaceOrder2Activity extends AppCompatActivity {
    private String paymentMethod;
    String phoneNumber;
    String customerName;
    String address;
    String deliveryBoy;
    int orderId;
    String orderName;
    String orderQuantity;
    String quantity;
    String orderAddress;
    private String date;
    private String time;
    float price;
    private TextView tvPhone, tvQuantity, tvCost;
    private Button btnProceed;
    private String otp;
    private EditText edOtp;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    String paymentId;
    private RadioGroup radioGroup;
    private SharedPreferences preferences;
    private String sharedPrefFile = "com.ecs.hindfarmfreshchicken.shareFile";
    SharedPreferences.Editor editor;
    public static final String CUSTOMER_PHONE = "com.ecs.hindfarmfreshchicken.customerPhone";
    public static final String CUSTOMER_NAME = "com.ecs.hindfarmfreshchicken.customerName";
    public static final String CUSTOMER_ADDRESS = "com.ecs.hindfarmfreshchicken.customerAddress";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order2);
        preferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = preferences.edit();
        phoneNumber = getIntent().getStringExtra("phone");
        customerName = getIntent().getStringExtra("name");
        edOtp = findViewById(R.id.editText_otp);
        address = getIntent().getStringExtra("address");
        deliveryBoy = getIntent().getStringExtra("deliveryBoy");
        quantity = getIntent().getStringExtra("quantity");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        price = getIntent().getFloatExtra("price", 200);
        tvPhone = findViewById(R.id.textView_phoneNumber);
        tvQuantity = findViewById(R.id.textView_quantity);
        tvCost = findViewById(R.id.textView_cost);
        tvPhone.setText("" + phoneNumber);
        tvCost.setText("" + price);
        tvQuantity.setText("" + quantity + " KG");
        btnProceed = findViewById(R.id.button_proceed);
        radioGroup = findViewById(R.id.radioGroup);
        progressDialog = new ProgressDialog(PlaceOrder2Activity.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
        otp = getIntent().getStringExtra("otp");


        //otp auto read code
        requestSMSPermission();
        OTP_Receiver call=new  OTP_Receiver();
        call.setEditText(edOtp);
        /*
        Random random = new Random();
        otp = String.format("%04d", random.nextInt(10000));
        String user = "rahulecs";
        String password = "rahulecs";
        String number = "91" +phoneNumber;
        String Message="Welcome To Hind Farm Chiken Your One Time Password OTP is ="+otp;
        String SID = "HINFRM";
        String smssigma ="http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user+"&password="+password+"&msisdn="+number+"&sid="+SID+"&msg="+Message+"&fl=0&gwid=2";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, smssigma, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(PlaceOrder2Activity.this, "OTP sent to your mobile", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PlaceOrder2Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        */
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.equals(edOtp.getText().toString())) {
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(PlaceOrder2Activity.this, "Select One payment method", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (paymentMethod.equals("cod")) {
                            SaveCODOrder saveCODOrder = new SaveCODOrder();
                            saveCODOrder.execute();
                        } else if (paymentMethod.equals("online")) {
                            callInstamojoPay("p_imran490@yahoo.com","9156526089",Float.toString(price),"Chicken App Payment",customerName);
                        }
                    }

                } else {
                    Toast.makeText(PlaceOrder2Activity.this, "OTP not matched", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onRadiobuttonClicked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton_cod:
                if (isChecked) {
                    paymentMethod = "cod";
                }
                break;
            case R.id.radioButton_online:
                if (isChecked) {
                    paymentMethod = "online";
                }
                break;
            default:
                break;
        }
    }

    class SaveCODOrder extends AsyncTask<String, String, String> {
        boolean isSuccess = false;
        String response;

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "insert into hffc_orders values('"+phoneNumber+"','"+customerName+"','"+address+"','pending','"+quantity+"',"+price+",'"+paymentMethod+"','not paid','"+date+"','"+time+"');";
                    Statement statement = connection.createStatement();
                    int resultSet = statement.executeUpdate(query);
                    if (resultSet>0) {
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
        protected void onPostExecute(String aFloat) {
            if (isSuccess) {
                editor.putString("1","saved");
                editor.putString(CUSTOMER_NAME,customerName);
                editor.putString(CUSTOMER_PHONE,phoneNumber);
                editor.putString(CUSTOMER_ADDRESS,address);
                editor.apply();

                try {
                    connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                    if (connection != null) {
                        String query = "select top 1 * from hffc_orders order by id desc;";
                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(query);
                        while (resultSet.next()) {
                            orderId = resultSet.getInt("id");
                            orderName = resultSet.getString("customer_name");
                            orderQuantity = resultSet.getString("quantity");
                            orderAddress = resultSet.getString("customer_address");
                        }
                    } else {
                        response = "Please Connect to internet";
                    }
                } catch (SQLException e) {

                    Log.d("exceptio", e.toString());
                }
                String user = "rahulecs";
                String password = "rahulecs";
                String ph = "919096219149";
                String SID = "HINFRM";
                String Message="has placed new order "+" Customer Number "+phoneNumber;
                String smssigma ="http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user+"&password="+password+"&msisdn="+ph+"&sid="+SID+"&msg="+Message+"&fl=0&gwid=2";
                String newsmsm = smssigma.replaceAll(" ","%20");
                StringRequest stringRequest = new StringRequest(Request.Method.GET, newsmsm, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(PlaceOrder2Activity.this, "OTP sent to your mobile", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PlaceOrder2Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(PlaceOrder2Activity.this);
                requestQueue.add(stringRequest);

                String user2 = "rahulecs";
                String password2 = "rahulecs";
                String SID2 = "HINFRM";
                String Message2="You have placed new order on hind farm fresh chicken. Your order ID is "+ orderId +" name is "+orderName+" address is "+orderAddress+" quantity is "+quantity+"Kg";
                String smssigma2 = "http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user2+"&password="+password2+"&msisdn="+phoneNumber+"&sid="+SID2+"&msg="+Message2+"&fl=0&gwid=2";
                String newsmsm2 = smssigma2.replaceAll(" ","%20");
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, newsmsm2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(PlaceOrder2Activity.this, "OTP sent to your mobile", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PlaceOrder2Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue requestQueue2 = Volley.newRequestQueue(PlaceOrder2Activity.this);
                requestQueue2.add(stringRequest2);

                progressDialog.dismiss();
                Intent intent = new Intent(PlaceOrder2Activity.this,MainPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


                Toast.makeText(PlaceOrder2Activity.this, aFloat, Toast.LENGTH_SHORT).show();
            }
            else {
                progressDialog.dismiss();
                Toast.makeText(PlaceOrder2Activity.this, aFloat, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Connection connectionMethod(String username, String password, String databaseName, String ipAddress) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connUrl = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connUrl = "jdbc:jtds:sqlserver://" + ipAddress + ":1433/" + databaseName + ";user=" + username + ";password=" + password + ";";
            connection = DriverManager.getConnection(connUrl);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
            pay.put("send_sms", true);
            pay.put("send_email", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }

    InstapayListener listener;
    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                String resArray[] = response.split(":");
                paymentId = resArray[3].substring(resArray[3].indexOf("=")+1);
                String orderId = resArray[1].substring(resArray[1].indexOf("=")+1);
                String txnId = resArray[2].substring(resArray[2].indexOf("=")+1);
                SaveOnlineOrder saveOnlineOrder = new SaveOnlineOrder();
                saveOnlineOrder.execute();
            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }

    class SaveOnlineOrder extends AsyncTask<String, String, String> {
        boolean isSuccess = false;
        String response;

        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "insert into hffc_orders values('"+phoneNumber+"','"+customerName+"','"+address+"','pending','"+quantity+"',"+price+",'"+paymentMethod+"','paid with "+paymentId+"','"+date+"','"+time+"');";
                    Statement statement = connection.createStatement();
                    int resultSet = statement.executeUpdate(query);
                    if (resultSet>0) {
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
        protected void onPostExecute(String aFloat) {
            if (isSuccess) {
                editor.putString("1","saved");
                editor.putString(CUSTOMER_NAME,customerName);
                editor.putString(CUSTOMER_PHONE,phoneNumber);
                editor.putString(CUSTOMER_ADDRESS,address);
                editor.apply();

                try {
                    connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                    if (connection != null) {
                        String query = "select top 1 * from hffc_orders order by id desc;";
                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(query);
                        while (resultSet.next()) {
                            orderId = resultSet.getInt("id");
                        }
                    } else {
                        response = "Please Connect to internet";
                    }
                } catch (SQLException e) {

                    Log.d("exceptio", e.toString());
                }


                String user = "rahulecs";
                String password = "rahulecs";
                String msisdn = "919096219149";
                String SID = "HINFRM";
                String Message3="has placed new order"+" Customer NO is "+phoneNumber;
                String smssigma ="http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user+"&password="+password+"&msisdn="+msisdn+"&sid="+SID+"&msg="+Message3+"&fl=0&gwid=2";
                String newsmsm = smssigma.replaceAll(" ","%20");
                StringRequest stringRequest = new StringRequest(Request.Method.GET, newsmsm, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(PlaceOrder2Activity.this, "OTP sent to your mobile", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PlaceOrder2Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(PlaceOrder2Activity.this);
                requestQueue.add(stringRequest);

                String Message4="You have placed new order on hind farm fresh chicken. Your order ID is "+orderId+" name is "+customerName +" address is "+ address +" quantity is "+quantity+"kg "+" Price is="+price;
                String smssigma2 = "http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user+"&password="+password+"&msisdn="+phoneNumber+"&sid="+SID+"&msg="+Message4+"&fl=0&gwid=2";
                String newsmsm2 = smssigma2.replaceAll(" ","%20");
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, newsmsm2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(PlaceOrder2Activity.this, "OTP sent to your mobile", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PlaceOrder2Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue requestQueue2 = Volley.newRequestQueue(PlaceOrder2Activity.this);
                requestQueue2.add(stringRequest2);

                Intent intent = new Intent(PlaceOrder2Activity.this,MainPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                Toast.makeText(PlaceOrder2Activity.this, aFloat, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(PlaceOrder2Activity.this, aFloat, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestSMSPermission()
    {
        String permission = Manifest.permission.RECEIVE_SMS;

        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED)
        {
            String[] permission_list = new String[1];
            permission_list[0] = permission;

            ActivityCompat.requestPermissions(this, permission_list,1);
        }
    }
}

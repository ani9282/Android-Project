package com.ecs.mahaeseva;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;


public class User_Registration extends AppCompatActivity {

    private Button otp, register;
    private EditText phoneNumber, dial;
    private String ph, generate_otp = "", dial_otp;
    int otpno, counter = 0, attempt = 4, order_id;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    private TextView radio;
    private ProgressDialog progressDialog;

    //remember me
    CheckBox rememberMe;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    //payment String paymentId;
    float amount;
    //database properties
    private int id;
    private String name;
    private String certificate_type;
    private String address;
    private String mobileno;
    private String payment_status;
    String response, price;
    String str;
    private EditText cname, ctype, cadd, mobile, cpayment;

    private Connection connection;

    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";


    public boolean checkForMobile() {
        Context c;
        EditText mEtMobile = (EditText) findViewById(R.id.mobile);
        String mStrMobile = mEtMobile.getText().toString();
        if (android.util.Patterns.PHONE.matcher(mStrMobile).matches() || android.util.Patterns.PHONE.matches("[a-zA-Z]+", mStrMobile)) {
            if (mStrMobile.length() <= 9 || mStrMobile.length() >= 11) {

                Toast.makeText(this, "Phone No is not valid", Toast.LENGTH_LONG).show();
            } else {
                return true;
            }

        }


        Toast.makeText(this, "Phone No is not valid", Toast.LENGTH_LONG).show();
        return false;
    }

    public int randomotpgenerate() {

        return ((int) (Math.random() * 9000) + 1000);
    }

    public void onlinecashpayment() {
        String message = "Welcome To Mahaseva Kundal" + " Your id is " + order_id + " Your Order Place for " + certificate_type + " Certificate price " + price + " Thank you Using MahaESeva Kundal.  For More Information Contact Rohit Patil Mobile No -9503438113";
        String user = "rahulecs";
        String password = "rahulecs";
        String number = "91"+ph;
        String SID = "HINFRM";
        String smssigma =  "http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user+"&password="+password+"&msisdn="+number+"&sid="+SID+"&msg="+message+"&fl=0"+"&dc=8"+"&gwid=2";



        StringRequest stringRequest = new StringRequest(Request.Method.GET, smssigma, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(User_Registration.this, "Order Details send To Your Mobile no Shortly Thank You!!!...", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(User_Registration.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(User_Registration.this);
        requestQueue.add(stringRequest);
        Toast.makeText(this, "Online payment Successfully Thank You....", Toast.LENGTH_LONG).show();
        Intent i = new Intent(User_Registration.this, Activity_Home.class);
        startActivity(i);
    }


    public void cashondelivery() {

        String message = "Welcome To Mahaseva Kundal" + " Your id is " + order_id + " Your Order Place for " + certificate_type + " Certificate price " + price + " Thank you Using MahaESeva Kundal.  For More Information Contact Rohit Patil Mobile No -9503438113";
        String user = "rahulecs";
        String password = "rahulecs";
        String number = "91" +ph;
        String SID = "HINFRM";
        String smssigma  =  "http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user+"&password="+password+"&msisdn="+number+"&sid="+SID+"&msg="+message+"&fl=0"+"&dc=8"+"&gwid=2";



        StringRequest stringRequest = new StringRequest(Request.Method.GET, smssigma, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(User_Registration.this, "Order Details send To Your Mobile no Shortly Thank You!!!...", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(User_Registration.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(User_Registration.this);
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__registration);
        progressDialog = new ProgressDialog(User_Registration.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("msg");

        if (message.equals("उत्पन्नाचा दाखला")) {
            price = "11 Rs";
        } else if (message.equals("आधार कार्ड")) {
            price = "11 Rs";
        } else if (message.equals("जातीचा दाखला")) {
            price = "11 Rs";
        } else if (message.equals("जात पडताळणी दाखला")) {
            price = "11 Rs";
        } else if (message.equals("डोमिसाईल दाखला")) {
            price = "11 Rs";
        } else if (message.equals("EWS दाखला")) {
            price = "11 Rs";
        } else if (message.equals("शेतकरी दाखला")) {
            price = "11 Rs";
        } else if (message.equals("३३% महिला आरक्षण दाखला")) {
            price = "11 Rs";
        } else if (message.equals("गॅझेट/राजपत्र")) {
            price = "11 Rs";
        } else if (message.equals("अल्पभूधारक दाखला")) {
            price = "11 Rs";
        } else if (message.equals("नॉन क्रिमिनल दाखला")) {
            price = "11 Rs";
        } else if (message.equals("पॅन कार्ड")) {
            price = "11 Rs";
        } else if (message.equals("पोलीस व्हेरीफिकेशन")) {
            price = "11 Rs";
        } else if (message.equals("शॉप कायदा")) {
            price = "11 Rs";
        } else if (message.equals("उद्योग आधार")) {
            price = "11 Rs";
        }
        str = price;
        str = str.replaceAll("\\s.*", "");
        amount = Float.parseFloat(str);
        cname = (EditText) findViewById(R.id.name);
        ctype = (EditText) findViewById(R.id.type);
        cadd = (EditText) findViewById(R.id.address);
        mobile = (EditText) findViewById(R.id.mobile);

        //remember me
        rememberMe = findViewById(R.id.remember);
        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        name = sharedPreferences.getString("name", "");
        address = sharedPreferences.getString("address", "");
        mobileno = sharedPreferences.getString("mobile", "");
        cname.setText(name);
        cadd.setText(address);
        mobile.setText(mobileno);


        EditText txtView = (EditText) findViewById(R.id.type);
        txtView.setText(message);

        EditText txt = (EditText) findViewById(R.id.price);
        txt.setText(price);


        otp = (Button) findViewById(R.id.btnotp);
        register = (Button) findViewById(R.id.btnLogin);

        //radio Button Key
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        //time
        Date currentdate = new Date();
        SimpleDateFormat time = new SimpleDateFormat("hh:mm");


        phoneNumber = findViewById(R.id.mobile);
        dial = (EditText) findViewById(R.id.otp);
        otp.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                counter = 0;
                attempt = 4;
                boolean ans = checkForMobile();

                if (ans == true) {
                    ph = phoneNumber.getText().toString();
                    otpno = randomotpgenerate();
                    //otpno = 1212;
                    generate_otp = String.valueOf(otpno);
                    String message = "Your MahaESeva Kundal One Time Password(OTP) is: " + otpno;
                    String user = "rahulecs";
                    String password = "rahulecs";
                    String number =ph;
                    String SID = "HINFRM";
                    String smssigma =  "http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user+"&password="+password+"&msisdn="+number+"&sid="+SID+"&msg="+message+"&fl=0&gwid=2";
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, smssigma, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(User_Registration.this, "OTP sent to your mobile", Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(User_Registration.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });


                    RequestQueue requestQueue = Volley.newRequestQueue(User_Registration.this);
                    requestQueue.add(stringRequest);


                }


            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dial_otp = dial.getText().toString();
                counter++;

                phoneNumber = findViewById(R.id.mobile);
                ph = phoneNumber.getText().toString();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(cname.getWindowToken(), 0);

                name = cname.getText().toString();
                address = cadd.getText().toString();


                if (ph.equals("") || android.util.Patterns.PHONE.matches("[a-zA-Z]+", ph) || ph.length() <= 9 || ph.length() >= 11) {

                    Toast.makeText(User_Registration.this, "Please Enter Valid Phone.... ", Toast.LENGTH_LONG).show();


                } else if (dial_otp.equals("")) {
                    Toast.makeText(User_Registration.this, "Please First Generate OTP...", Toast.LENGTH_LONG).show();
                } else if (generate_otp.equals("")) {
                    Toast.makeText(User_Registration.this, "Please First Generate OTP...", Toast.LENGTH_LONG).show();
                } else {
                    if (counter < 4) {
                        if (generate_otp.equals(dial_otp)) {

                            selectedRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                            String t = selectedRadioButton.getText().toString();
                            payment_status = t;


                            if (t.equals("Online Payment")) {

                                name = cname.getText().toString();
                                certificate_type = ctype.getText().toString();
                                mobileno = phoneNumber.getText().toString();
                                address = cadd.getText().toString();
                                payment_status = "Online Payment";
                                callInstamojoPay("rahulpawar9766@gmail.com", ph, Float.toString(amount), "MahaEseva Kundal App Payment", name);
                            } else if (t.equals("Cash On Delivery")) {

                                if (rememberMe.isChecked()) {
                                    editor.putString("name", cname.getText().toString());
                                    editor.putString("address", cadd.getText().toString());
                                    editor.putString("mobile", mobile.getText().toString());
                                    editor.commit();
                                } else {
                                    editor.putString("name", "");
                                    editor.putString("address", "");
                                    editor.putString("mobile", "");
                                    editor.commit();
                                }
                                name = cname.getText().toString();
                                certificate_type = ctype.getText().toString();
                                mobileno = phoneNumber.getText().toString();
                                address = cadd.getText().toString();
                                payment_status = t;

                                SaveCODOrder saveCODOrder = new SaveCODOrder();
                                saveCODOrder.execute();
                            }


                        } else {
                            attempt--;
                            Toast.makeText(User_Registration.this, "Invalid OTP.......  OTP attempt Left " + attempt, Toast.LENGTH_LONG).show();
                        }


                    } else {

                        int otp;
                        otp = randomotpgenerate();
                        generate_otp = String.valueOf(otpno);
                        Toast.makeText(User_Registration.this, "OTP attempt Failed ", Toast.LENGTH_LONG).show();
                    }

                }


            }
        });

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
                String paymentId = resArray[3].substring(resArray[3].indexOf("=") + 1);
                String orderId = resArray[1].substring(resArray[1].indexOf("=") + 1);
                String txnId = resArray[2].substring(resArray[2].indexOf("=") + 1);

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
                    String query = "insert into Mahaseva values(N'" + name + "',N'" + certificate_type + "',N'" + address + "','" + mobileno + "','" + price + "','" + payment_status + "');";
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
        protected void onPostExecute(String aFloat) {
            if (isSuccess) {
                editor.putString("name", cname.getText().toString());
                editor.putString("address", cadd.getText().toString());
                editor.putString("mobile", mobile.getText().toString());
                editor.commit();

                try {
                    connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                    if (connection != null) {
                        String query = "select top 1 * from Mahaseva order by id desc;";
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

                String message = "Welcome To MahaEseva Kundal \n" + "Customer id " + order_id + " Order Placed by " + name + " Customer address is " + address + " Mobile No " + mobileno + " Certificate For " + certificate_type + " Certificate Price " + price;
                // String message="Your OTP is 1211";
                String user = "rahulecs";
                String password = "rahulecs";
                String number ="919850202777";
                //  String Message="Welcome To Mahaeseva Your One Time Password OTP is ="+otp;
                String SID = "HINFRM";
                String smssigma =  "http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user+"&password="+password+"&msisdn="+number+"&sid="+SID+"&msg="+message+"&fl=0"+"&dc=8"+"&gwid=2";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, smssigma, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(User_Registration.this, "Your Order Placed Successfully....", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(User_Registration.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


                RequestQueue requestQueue = Volley.newRequestQueue(User_Registration.this);
                requestQueue.add(stringRequest);

                onlinecashpayment();
            }
        }
    }

    class SaveCODOrder extends AsyncTask<String, String, String> {
        boolean isSuccess = false;
        String response;

        @Override
        protected void onPreExecute() {

            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select top 1 * from Mahaseva order by id desc;";
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



            Intent i = new Intent(User_Registration.this, Activity_Home.class);
            startActivity(i);
            String message = "Welcome To MahaEseva Kundal \n" + "Customer id " + order_id + " Order Placed by " + name + " Customer address is " + address + " Mobile No " + mobileno + " Certificate For " + certificate_type + " Certificate Price " + price;
            // String message="Your OTP is 1211";
            String user = "rahulecs";
            String password = "rahulecs";
            String number ="919850202777";
          //  String Message="Welcome To Mahaeseva Your One Time Password OTP is ="+otp;
            String SID = "HINFRM";
            String smssigma = "http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user+"&password="+password+"&msisdn="+number+"&sid="+SID+"&msg="+message+"&fl=0"+"&dc=8"+"&gwid=2";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, smssigma, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(User_Registration.this, "Your Order Placed Successfully....", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(User_Registration.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });


            RequestQueue requestQueue = Volley.newRequestQueue(User_Registration.this);
            requestQueue.add(stringRequest);

            cashondelivery();
        }  //add database files and function -----------------------------------------


        // database method


        protected String doInBackground(String... strings) {

            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "insert into Mahaseva values(N'" + name + "',N'" + certificate_type + "',N'" + address + "','" + mobileno + "','" + price + "','" + payment_status + "');";
                    Statement statement = connection.createStatement();
                    int resultSet = statement.executeUpdate(query);
                    if (resultSet > 0) {
                        boolean isSuccess = true;
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
        }//end database method


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




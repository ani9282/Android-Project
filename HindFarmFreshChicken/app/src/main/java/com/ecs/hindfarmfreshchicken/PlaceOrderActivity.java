package com.ecs.hindfarmfreshchicken;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PlaceOrderActivity extends AppCompatActivity {
    private EditText edPhoneNumber;
    private EditText edCustomerName;
    private EditText edCustomerAddress;
    private EditText edQuantity;
    private Button btnDate;
    private Button btnTime;
    String phoneNumber;
    String customerName;
    String address;
    String quantity;
    private String date;
    private String time;
    float todaysRate;
    private SharedPreferences preferences;
    private String sharedPrefFile = "com.ecs.hindfarmfreshchicken.shareFile";
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private TextView tvPromotion;
    private String promotionText;
    private ProgressDialog progressDialog;
    private String otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        tvPromotion = findViewById(R.id.textView_promtion);
        edPhoneNumber = findViewById(R.id.poeditText_phoneNumebr);
        edCustomerName = findViewById(R.id.poeditText_customerName);
        edCustomerAddress = findViewById(R.id.poeditText_customerAddress);
        edQuantity = findViewById(R.id.poeditText_orderStatus);
        btnDate = findViewById(R.id.pobutton_date);
        btnTime = findViewById(R.id.pobutton_time);
        preferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        progressDialog = new ProgressDialog(PlaceOrderActivity.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
        todaysRate = getIntent().getFloatExtra("rate",180);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime();
            }
        });
        GetPromotionData getPromotionData = new GetPromotionData();
        getPromotionData.execute();
        if (preferences.getString("1","2").equals("saved")) {
            edPhoneNumber.setText(preferences.getString(PlaceOrder2Activity.CUSTOMER_PHONE,""));
            edCustomerName.setText(preferences.getString(PlaceOrder2Activity.CUSTOMER_NAME,""));
            edCustomerAddress.setText(preferences.getString(PlaceOrder2Activity.CUSTOMER_ADDRESS,""));
        }
    }

    public class GetPromotionData extends AsyncTask<String, String, Float> {

        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Float doInBackground(String... strings) {
            try {
                connection = connectionMethod(USER_NAME,PASSWORD,DATABASE_NAME,IPADDRESS);
                if (connection != null) {
                    Statement statement = connection.createStatement();
                    String query = "select * from hffc_promotion;";
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        isSuccess = true;
                        promotionText = resultSet.getString(2);
                    }
                    else {
                    }
                }
                else {
                }
            }
            catch (SQLException e) {
                Log.d("exceptio",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Float aFloat) {
            tvPromotion.setText(promotionText);
            progressDialog.dismiss();
        }
    }

    private void getTime() {
        Calendar calendar = Calendar.getInstance();
        final int HOUR = calendar.get(Calendar.HOUR);
        final int MINUTES = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                try
                {
                    time = hourOfDay+":"+minute;
                    DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                    Date date = new SimpleDateFormat("HH:mm").parse(time);
                    time = dateFormat.format(date);
                    btnTime.setText(time);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        },HOUR,MINUTES,false);
        timePickerDialog.show();
    }

    private void getDate() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DAY = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = dayOfMonth+"-"+(month+1)+"-"+year;
                btnDate.setText(date);
            }
        },YEAR,MONTH,DAY);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    public void placeOrder(View view) {
        Random random = new Random();
        otp = String.format("%04d", random.nextInt(10000));
        phoneNumber = edPhoneNumber.getText().toString();
        customerName = edCustomerName.getText().toString();
        address = edCustomerAddress.getText().toString();
        quantity = edQuantity.getText().toString();
        String regx = "^[+]?[0-9]{10}$";
        if (!Patterns.PHONE.matcher(phoneNumber).matches() || phoneNumber.matches(regx) == false) {
            edPhoneNumber.setError(getString(R.string.phone_number_error_msg));
            return;
        }
        if (TextUtils.isEmpty(quantity) ||TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(customerName) || TextUtils.isEmpty(address) || TextUtils.isEmpty(date) || TextUtils.isEmpty(time)) {
            Toast.makeText(this, "Fill All Fields", Toast.LENGTH_LONG).show();
        }
        else {
            String user = "rahulecs";
            String password = "rahulecs";
            String number = "91" +phoneNumber;
            String Message="Welcome To Hind Farm Chiken Your One Time Password OTP is="+otp;
            String SID = "HINFRM";
            String smssigma ="http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user+"&password="+password+"&msisdn="+number+"&sid="+SID+"&msg="+Message+"&fl=0&gwid=2";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, smssigma, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(PlaceOrderActivity.this, "OTP sent to your mobile", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(PlaceOrderActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
            Intent intent = new Intent(PlaceOrderActivity.this,PlaceOrder2Activity.class);
            intent.putExtra("phone",phoneNumber);
            intent.putExtra("name",customerName);
            intent.putExtra("address",address);
            intent.putExtra("quantity",quantity);
            float qa = Float.valueOf(quantity);
            intent.putExtra("price",todaysRate*qa);
            intent.putExtra("date",date);
            intent.putExtra("time",time);
            intent.putExtra("otp",otp);
            startActivity(intent);
            finish();
        }
    }
    public Connection connectionMethod(String username,String password,String databaseName,String ipAddress) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connUrl = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connUrl = "jdbc:jtds:sqlserver://" + ipAddress + ":1433/"+ databaseName + ";user=" + username+ ";password=" + password + ";";
            connection = DriverManager.getConnection(connUrl);
        }
        catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

}

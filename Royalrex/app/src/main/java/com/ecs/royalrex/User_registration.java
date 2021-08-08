package com.ecs.royalrex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Random;

public class User_registration extends AppCompatActivity {

    EditText efname,elname,emobile,epass,ecpass,eemail;
    String role,otp,verifynumber;
    private Connection connection;
    Button submit;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    ArrayList<ModelClass> modelList;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        efname =(EditText)findViewById(R.id.fname);
        elname=(EditText)findViewById(R.id.lname);
        emobile=(EditText)findViewById(R.id.mobile);
        epass=(EditText)findViewById(R.id.pass);
        ecpass=(EditText)findViewById(R.id.cpass);
        eemail=(EditText)findViewById(R.id.email);
        submit=(Button)findViewById(R.id.register);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                String t = selectedRadioButton.getText().toString();

                if (t.equals("Sender")) {
                    role=t;
                } else if (t.equals("Driver")) {
                    role=t;
                }
                else if(t.equals("Both")){
                   role=t;
                }



                if(efname.getText().toString().isEmpty() || elname.getText().toString().isEmpty() || emobile.getText().toString().isEmpty() || emobile.getText().toString().length()<=9 || emobile.getText().toString().length()>=11 || epass.getText().toString().length()<3  || epass.getText().toString().isEmpty() || !epass.getText().toString().equals(ecpass.getText().toString())){
                    if(efname.getText().toString().isEmpty())
                    {
                        efname.setError("First Name Can not Empty");
                    }

                    if(elname.getText().toString().isEmpty())
                    {
                        elname.setError("Last Name Can not Empty");
                    }

                    if(emobile.getText().toString().isEmpty())
                    {
                        emobile.setError("Mobile Can not Empty");
                    }

                    if(emobile.getText().toString().length()<=9 || emobile.getText().toString().length()>=11)
                    {
                        emobile.setError("Invalid Mobile");
                    }

                    if(epass.getText().toString().isEmpty())
                    {
                        epass.setError("Password Can not Empty");
                    }

                    if(epass.getText().toString().length()<3)
                    {
                        epass.setError("Password Weak");
                    }

                    if(!epass.getText().toString().equals(ecpass.getText().toString()))
                    {
                        epass.setError("Password And Confirm Password Not Matched");
                        ecpass.setError("Password And Confirm Password Not Matched");
                    }



                }


                else
                {

                    if(eemail.getText().toString().isEmpty() || eemail.getText().toString().matches(emailPattern))
                    {
                        SaveData saveData=new SaveData();
                        saveData.execute();
                    }

                    else if(!eemail.getText().toString().matches(emailPattern))
                    {
                        eemail.setError("Invalid Email");
                    }




                }

            }

        });

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
              Intent intent=new Intent(this,Login.class);
              startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class SaveData extends AsyncTask<String, String, String> {
        boolean isSuccess = false;
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select mobile from royalrex";
                    Statement statement = connection.createStatement();
                    ResultSet rs1 = statement.executeQuery(query);
                    while (rs1.next())
                    {
                        if(emobile.getText().toString().equals(rs1.getString("mobile")))
                        {
                            verifynumber=emobile.getText().toString();
                        }
                    }
                } else {

                    response = "Please Connect to internet";
                }
            } catch (SQLException e) {

                Log.d("exceptio", e.toString());
            }
            return response;
        }//end database method

        @Override
        protected void onPostExecute(String s) {


                if(emobile.getText().toString().equals(verifynumber))
                {
                    Toast.makeText(User_registration.this,"Mobile Number Already Register....",Toast.LENGTH_LONG).show();
                }

                else
                {
                    Random random = new Random();
                    otp = String.format("%04d", random.nextInt(10000));
                    String message = "Welcome To Royalrex Your OTP is"+otp;
                    // String message="Your OTP is 1211";
                    String user = "rahulecs";
                    String password = "rahulecs";
                    String number =emobile.getText().toString();
                    //  String Message="Welcome To Mahaeseva Your One Time Password OTP is ="+otp;
                    String SID = "HINFRM";
                    String smssigma = "http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user+"&password="+password+"&msisdn="+number+"&sid="+SID+"&msg="+message+"&fl=0"+"&dc=8"+"&gwid=2";

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, smssigma, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(User_registration.this, "Your OTP send your Mobile Number Successfully....", Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(User_registration.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });


                    RequestQueue requestQueue = Volley.newRequestQueue(User_registration.this);
                    requestQueue.add(stringRequest);



                    Intent i=new Intent(User_registration.this,OTP_verification.class);
                    i.putExtra("fname",efname.getText().toString());
                    i.putExtra("lname",elname.getText().toString());
                    i.putExtra("mobile",emobile.getText().toString());
                    i.putExtra("pass",epass.getText().toString());
                    i.putExtra("role",role);
                    i.putExtra("email",eemail.getText().toString());
                    i.putExtra("otp",otp);
                    startActivity(i);
                    finish();
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
}

package com.ecs.a3_gift_shoppy_user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends AppCompatActivity {

    EditText eusername,epassword;
    String username,password,email,mobile,pass,role,flag="false",first_name,last_name;
    Button register;
    private String status="Unblock";
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private ProgressDialog progressDialog;
    //remember me
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.a3giftshopy.file";
    public static final String LOGIN_KEY = "com.ecs.a3giftshopy.loginkey";
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eusername=(EditText)findViewById(R.id.email);
        role = getIntent().getStringExtra("role");
        register=(Button) findViewById(R.id.login);
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Loading Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);

        boolean ans=isNetworkConnected();

        if(ans==true)
        {
            //remember me
            sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
            editor = sharedPreferences.edit();
            if (sharedPreferences.getString(LOGIN_KEY,"no").equals("yes")) {
               Intent intent = new Intent(Login.this,Homepage.class);
                startActivity(intent);
                finish();
            }
        }

        else {
            Toast.makeText(Login.this,"Please Connect To internet",Toast.LENGTH_LONG).show();
        }










        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ans=isNetworkConnected();
                if(ans==true)
                {
                    SaveData save=new SaveData();
                    save.execute();
                }

                else {
                    Toast.makeText(Login.this,"Please Connect To internet",Toast.LENGTH_LONG).show();
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
                    String query = "select * from A3_gift_shoppy_registration where mobile='"+eusername.getText().toString()+"' and  status='"+status+"'";
                    Statement statement = connection.createStatement();
                    ResultSet rs1 = statement.executeQuery(query);
                    if (rs1.next()) {
                        isSuccess = true;

                    } else
                    {
                        response = "Some Error..Order not placed";
                    }

                } else {

                    response = "Please Connect to internet";
                }
            }
            catch (SQLException e ) {

                Log.d("exceptio", e.toString());
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(isSuccess==true)
            {
                Intent intent=new Intent(Login.this,Homepage.class);
                startActivity(intent);
                editor.putString(LOGIN_KEY,"yes");
                editor.putString("mobile",eusername.getText().toString());
                editor.apply();
                finish();
            }

            else {
                Toast.makeText(Login.this, "You Block Please Contact Administrator", Toast.LENGTH_LONG).show();
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


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
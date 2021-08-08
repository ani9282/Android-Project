package com.ecs.royalrex;

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

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Login extends AppCompatActivity {


    TextView login;
    EditText eusername,epassword;
    String username,password,email,mobile,pass,role,flag="false",first_name,last_name;
    Button register;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    ArrayList<ModelClass> modelList;
    private ProgressDialog progressDialog;

    //remember me
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    public static final String LOGIN_KEY = "com.ecs.royalrex.loginkey";
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eusername=(EditText)findViewById(R.id.email);
        epassword=(EditText)findViewById(R.id.password);
        role = getIntent().getStringExtra("role");
        register=(Button) findViewById(R.id.login);
        login = (TextView)findViewById(R.id.register);
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
                Intent intent = new Intent(Login.this,Sender_home_page.class);
                startActivity(intent);
                finish();
            }

            if (sharedPreferences.getString(LOGIN_KEY,"no").equals("yes1")) {
                Intent intent = new Intent(Login.this,Driver_home_page.class);
                startActivity(intent);
                finish();
            }
        }

        else {
            Toast.makeText(Login.this,"Please Connect To internet",Toast.LENGTH_LONG).show();
        }






        login.setMovementMethod(LinkMovementMethod.getInstance());
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean ans=isNetworkConnected();
                    if(ans==true)
                    {
                        Intent intent = new Intent(Login.this, User_registration.class);
                        startActivity(intent);
                    }

                    else {
                        Toast.makeText(Login.this,"Please Connect To internet",Toast.LENGTH_LONG).show();
                    }

                }
            });



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
            /*
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select * from royalrex where mobile='"+eusername.getText().toString()+"' and  pass='"+epassword.getText().toString()+"'";
                    Statement statement = connection.createStatement();
                    ResultSet rs1 = statement.executeQuery(query);
                    if (rs1.next()) {
                        //isSuccess = true;
                        role=rs1.getString("role");
                        flag=rs1.getString("flag");
                        first_name=rs1.getString("first_name");
                        last_name=rs1.getString("last_name");
                        mobile=rs1.getString("mobile");
                       // response = "Data Added Successfully...";
                    } else {
                        response = "Some Error..Order not placed";
                    }

                } else {

                    response = "Please Connect to internet";
                }
            } catch (SQLException e) {

                Log.d("exceptio", e.toString());
            }


             */

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select * from royalrex where mobile='"+eusername.getText().toString()+"' and  pass='"+epassword.getText().toString()+"'";
                    Statement statement = connection.createStatement();
                    ResultSet rs1 = statement.executeQuery(query);
                    if (rs1.next()) {
                        isSuccess = true;
                        role=rs1.getString("role");
                        flag=rs1.getString("flag");
                        first_name=rs1.getString("first_name");
                        last_name=rs1.getString("last_name");
                        mobile=rs1.getString("mobile");
                       // response = "Data Added Successfully...";
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
                /*
                if(flag.equals("false"))
                {

                    Intent intent=new Intent(Login.this,KYC_Verification.class);
                    intent.putExtra("mobile",eusername.getText().toString());
                    intent.putExtra("role",role);
                    intent.putExtra("first_name",first_name);
                    intent.putExtra("last_name",last_name);
                    startActivity(intent);
                    finish();
                }

                 */


                   if(role.equals("Sender") || role.equals("Both"))
                   {
                       Intent intent=new Intent(Login.this,Sender_home_page.class);
                       editor.putString(LOGIN_KEY,"yes");
                       editor.putString("mobile",eusername.getText().toString());
                       editor.putString("first_name",first_name);
                       editor.putString("last_name",last_name);
                       editor.putString("role",role);
                       editor.putString("sender_password",epassword.getText().toString());
                       editor.apply();
                       startActivity(intent);
                       finish();
                   }

                   else {
                       Intent intent=new Intent(Login.this,Driver_home_page.class);
                       intent.putExtra("mobile",eusername.getText().toString());
                       intent.putExtra("role",role);
                       editor.putString(LOGIN_KEY,"yes1");
                       editor.putString("mobile",eusername.getText().toString());
                       editor.putString("first_name",first_name);
                       editor.putString("last_name",last_name);
                       editor.putString("driver_password",epassword.getText().toString());
                       editor.apply();
                       startActivity(intent);
                       finish();
                   }




            }

            else {
                eusername.setError("Invalid Username");
                epassword.setError("Invalid Password");
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



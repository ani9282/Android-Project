package com.ecs.royalrex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OTP_verification extends AppCompatActivity {

    Button register;
    EditText eotp;
    String fname,lname,mobile,pass,email,role,otp,flag="false";
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verification);
        eotp=(EditText)findViewById(R.id.cotp);
        register=(Button)findViewById(R.id.register);
        fname=getIntent().getStringExtra("fname");
        lname = getIntent().getStringExtra("lname");
        mobile=getIntent().getStringExtra("mobile");
        pass= getIntent().getStringExtra("pass");
        role= getIntent().getStringExtra("role");
        email= getIntent().getStringExtra("email");
        otp = getIntent().getStringExtra("otp");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(eotp.getText().toString().equals(otp) || eotp.getText().toString().isEmpty())
                    {
                        if(eotp.getText().toString().isEmpty())
                        {
                            Toast.makeText(OTP_verification.this,"First Enter OTP.....",Toast.LENGTH_LONG).show();
                        }
                        else {
                            SaveData saveData=new SaveData();
                            saveData.execute();
                        }

                    }

                    else {
                        Toast.makeText(OTP_verification.this,"Invalid OTP.....",Toast.LENGTH_LONG).show();
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

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "insert into Royalrex (first_name,last_name,mobile,email,pass,role,flag) values(N'"+ fname + "',N'" +lname + "',N'" + mobile + "','"+email+"','"+pass+"','" + role + "','"+flag+"');";
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
        }//end database method

        @Override
        protected void onPostExecute(String s) {

            if(isSuccess==true)
            {
                AlertDialog.Builder dialog=new AlertDialog.Builder(OTP_verification.this);
                dialog.setMessage("Registration Successfully Thank You......");
                dialog.setTitle("Registration Alert");
                dialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent i=new Intent(OTP_verification.this,Login.class);
                                startActivity(i);
                            }
                        });

                AlertDialog alertDialog=dialog.create();
                alertDialog.setCancelable(false);
                alertDialog.show();

            }
            else {
                Toast.makeText(OTP_verification.this, "Sorry Error in Database....", Toast.LENGTH_LONG).show();
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
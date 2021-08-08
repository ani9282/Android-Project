package com.ecs.royalrex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Driver_change_password extends AppCompatActivity {

    EditText old_password,new_password,confirm_password;
    String oldpass;
    Button register;
    ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    private SharedPreferences.Editor editor;
    private String mobile;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_change_password);
        progressDialog=new ProgressDialog(Driver_change_password.this);
        progressDialog.setMessage("Loading Please Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mobile=sharedPreferences.getString("mobile","mobile");
        oldpass=sharedPreferences.getString("driver_password","driver_password");
        old_password=(EditText)findViewById(R.id.old_password);
        new_password=(EditText)findViewById(R.id.new_password);
        confirm_password=(EditText)findViewById(R.id.confirm_password);
        register=(Button)findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(old_password.getText().toString().isEmpty() || new_password.getText().toString().isEmpty() || confirm_password.getText().toString().isEmpty() || !new_password.getText().toString().equals(confirm_password.getText().toString()) || !old_password.getText().toString().equals(oldpass))
                {
                    if(!confirm_password.getText().toString().equals(new_password.getText().toString()))
                    {
                        Toast.makeText(Driver_change_password.this,"New Password and Confirm Password Not Matched",Toast.LENGTH_SHORT).show();
                    }


                    if(!old_password.getText().toString().equals(oldpass))
                    {
                        old_password.setError("Old Password Wrong Entered");
                    }
                    else {
                        Toast.makeText(Driver_change_password.this,"All Fill Field",Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    SaveData saveData=new SaveData();
                    saveData.execute();
                }
            }
        });

    }

    private class SaveData extends AsyncTask<Void,Void,String> {
        private String response;
        private boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                connection = connectionMethod(USER_NAME,PASSWORD,DATABASE_NAME,IPADDRESS);
                if (connection != null) {
                    String query = "update  Royalrex set pass='"+new_password.getText().toString()+"' where mobile='"+mobile+"'";
                    Statement statement = connection.createStatement();
                    int resultSet = statement.executeUpdate(query);
                    if (resultSet == 0) {
                        //response = "Some Error Occured";
                    }
                    else {
                        isSuccess = true;
                    }

                }
                else {
                    //response = "Check Your Internet Connection";
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

            if(isSuccess==true)
            {
                AlertDialog.Builder dialog=new AlertDialog.Builder(Driver_change_password.this);
                dialog.setMessage("Change Password Successfully Thank You......");
                dialog.setTitle("Change Password Alert");
                dialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                editor.clear();
                                editor.apply();
                                Intent intent = new Intent(Driver_change_password.this,Login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });

                AlertDialog alertDialog=dialog.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
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
    }
}
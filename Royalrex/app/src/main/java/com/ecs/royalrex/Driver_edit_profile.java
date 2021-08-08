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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Driver_edit_profile extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    private SharedPreferences.Editor editor;
    private String mobile;
    EditText efirst_name,elast_name,emobile,eemail,erole;
    Button update;
    private ProgressDialog progressDialog;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_edit_profile);
        progressDialog=new ProgressDialog(Driver_edit_profile.this);
        progressDialog.setMessage("Loading Profile Please Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);
        efirst_name=(EditText)findViewById(R.id.fname);
        elast_name=(EditText)findViewById(R.id.lname);
        emobile=(EditText)findViewById(R.id.mobile);
        eemail=(EditText)findViewById(R.id.email);
        erole=(EditText)findViewById(R.id.role);
        update=(Button)findViewById(R.id.update);
        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mobile=sharedPreferences.getString("mobile","mobile");

        SaveData saveData=new SaveData();
        saveData.execute();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateData updateData= new UpdateData();
                updateData.execute();
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
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "select * from Royalrex where mobile='"+mobile+"'";
                    Statement statement = connection.createStatement();
                    ResultSet rs1 = statement.executeQuery(query);
                    if (rs1.next())
                    {
                        isSuccess = true;
                        setText(efirst_name,rs1.getString("first_name"));
                        setText(elast_name,rs1.getString("last_name"));
                        setText(emobile,rs1.getString("mobile"));
                        setText(eemail,rs1.getString("email"));
                        setText(erole,rs1.getString("role"));
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

    private void setText(final EditText text, final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    private class UpdateData extends AsyncTask<Void,Void,String> {
        private String response;
        private boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids)
        {
            try {
                connection = connectionMethod(USER_NAME,PASSWORD,DATABASE_NAME,IPADDRESS);
                if (connection != null) {
                    String query = "update  Royalrex set first_name='"+efirst_name.getText().toString()+"',last_name='"+elast_name.getText().toString()+"',email='"+eemail.getText().toString()+"' where mobile='"+mobile+"'";
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
                AlertDialog.Builder dialog=new AlertDialog.Builder(Driver_edit_profile.this);
                dialog.setMessage("Edit Profile Successfully Thank You......");
                dialog.setTitle("Edit Profile Alert");
                dialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent intent = new Intent(Driver_edit_profile.this,Sender_show_profile.class);
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

    } //connection method

}
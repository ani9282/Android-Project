package com.ecs.royalrex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Owner_driver_info extends AppCompatActivity {

    ProgressDialog progressDialog;
    private String date;
    private String time,owner_mobile;
    private Button btnDate,btnTime,register;
    EditText dname,dmobile,location,number,area,pincode;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_driver_info);
        progressDialog=new ProgressDialog(Owner_driver_info.this);
        progressDialog.setMessage("Loading Please wait.....");
        progressDialog.setCanceledOnTouchOutside(false);
        dname=(EditText)findViewById(R.id.dname);
        pincode=(EditText)findViewById(R.id.pincode);
        dmobile=(EditText)findViewById(R.id.dmobile);
        location=(EditText)findViewById(R.id.pickup_location);
        btnDate = findViewById(R.id.date);
        btnTime = findViewById(R.id.time);
        area=(EditText)findViewById(R.id.zone);
        number=(EditText)findViewById(R.id.number);
        register=(Button)findViewById(R.id.register);

        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        owner_mobile=sharedPreferences.getString("mobile","mobile");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dname.getText().toString().isEmpty() ||  TextUtils.isEmpty(date) || TextUtils.isEmpty(time) ||dmobile.getText().toString().length()<=9 || dmobile.getText().toString().length()>=11 || dmobile.getText().toString().isEmpty() || location.getText().toString().isEmpty())
                {
                    if(dmobile.getText().toString().length()<=9 || dmobile.getText().toString().length()>=11)
                    {
                        dmobile.setError("Invalid Mobile Number");
                    }

                    else {
                        Toast.makeText(Owner_driver_info.this, "Fill All Fields", Toast.LENGTH_LONG).show();
                    }
                }

                else {
                        SaveData saveData=new SaveData();
                        saveData.execute();
                }
            }
        });

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

    }


    class SaveData extends AsyncTask<String, String, String> {
        boolean isSuccess = false;
        String response;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                connection = connectionMethod(USER_NAME,PASSWORD,DATABASE_NAME,IPADDRESS);
                if (connection != null) {
                    String query = "update Royalrex_add_Vehical set driver_name='"+dname.getText().toString()+"',driver_mobile='"+dmobile.getText().toString()+"',pickup_location='"+location.getText().toString()+" "+pincode.getText().toString()+"',area_zone='"+area.getText().toString()+"',date='"+date+"',time='"+time+"' where vehical_number='"+number.getText().toString()+"'";
                    Statement statement = connection.createStatement();
                    int resultSet = statement.executeUpdate(query);
                    if (resultSet> 0) {
                        isSuccess = true;
                    }

                }
                else {
                    response = "Check Your Internet Connection";
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
                AlertDialog.Builder dialog=new AlertDialog.Builder(Owner_driver_info.this);
                dialog.setMessage("Driver Registration Successfully Thank You......");
                dialog.setTitle("Driver Registration Alert");
                dialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent intent=new Intent(Owner_driver_info.this,Driver_home_page.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });

                AlertDialog alertDialog=dialog.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
            else {
                Toast.makeText(Owner_driver_info.this, "Please First Add Vehical Details and then add Driver Info....", Toast.LENGTH_LONG).show();
            }
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



                    /*
                    Calendar datetime = Calendar.getInstance();
                    Calendar c = Calendar.getInstance();
                    datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    datetime.set(Calendar.MINUTE, minute);
                    if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {
                        //it's after current
                        int hour = hourOfDay % 12;
                        btnTime.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                                minute, hourOfDay < 12 ? "am" : "pm"));
                    } else {
                        //it's before current'

                        btnTime.setText("");
                        //Toast.makeText(getApplicationContext(), "Invalid Time", Toast.LENGTH_LONG).show();
                       // Snackbar snackbar = Snackbar.make(this, "Please Enter Correct UserName and Password", Snackbar.LENGTH_LONG);
                        //snackbar.show();

                    }

                     */


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
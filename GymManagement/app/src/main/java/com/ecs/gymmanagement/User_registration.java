package com.ecs.gymmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class User_registration extends AppCompatActivity {

    private EditText name,address,mobile;
    private Button register;
    private ProgressDialog progressDialog;
    private static final String HI= "https://www.ecssofttech.com/api/Gym/inter_data.php";
    private Button btnDate;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        btnDate = findViewById(R.id.pobutton_date);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading please Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);
        name=(EditText)findViewById(R.id.name);
        address=(EditText)findViewById(R.id.address);
        mobile=(EditText)findViewById(R.id.mobile);
        //dob=(EditText)findViewById(R.id.dob);
        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty() || address.getText().toString().isEmpty() || mobile.getText().toString().isEmpty() || TextUtils.isEmpty(date) || mobile.getText().toString().length()>=11 || mobile.getText().toString().length()<=9)
                {
                    Toast.makeText(User_registration.this, "All Field Required", Toast.LENGTH_SHORT).show();
                    if(mobile.getText().toString().length()>=11 || mobile.getText().toString().length()<=9)
                    {
                        Toast.makeText(User_registration.this, "Mobile Number Not Valid", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Toast.makeText(User_registration.this, "All Field Required", Toast.LENGTH_SHORT).show();
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
            OkHttpClient client = new OkHttpClient();
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("https://www.ecssofttech.com/api/Gym/inter_data.php?customer_name="+name.getText().toString()+
                            "&customer_address="+address.getText()+"&customer_mobile="+mobile.getText().toString()+"&date_of_birth="+date)  //send parameteres to php script
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                System.out.println(responseString);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            AlertDialog.Builder dialog=new AlertDialog.Builder(User_registration.this);
            dialog.setMessage("Registration Successfully Thank You......");
            dialog.setTitle("Registration Alert");
            dialog.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            Intent i=new Intent(User_registration.this,Homepage.class);
                            startActivity(i);
                        }
                    });

            AlertDialog alertDialog=dialog.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
    }


}
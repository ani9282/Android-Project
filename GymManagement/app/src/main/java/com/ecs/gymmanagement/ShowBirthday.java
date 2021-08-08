package com.ecs.gymmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class ShowBirthday extends AppCompatActivity {
    private List<PostList> list;
    private String date;
    private EditText message;
    private Button btnDate,register;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_birthday);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading please Wait.......");
        progressDialog.setCanceledOnTouchOutside(false);
        btnDate=findViewById(R.id.date);
        message=(EditText)findViewById(R.id.message);
        register=findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(TextUtils.isEmpty(date))
                    {
                        Toast.makeText(ShowBirthday.this, "Select Date First", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        //SaveData saveData=new SaveData();
                        //saveData.execute();
                        Intent intent=new Intent(ShowBirthday.this,Show_birthday_details.class);
                        intent.putExtra("date",date);
                        intent.putExtra("message",message.getText().toString());
                        startActivity(intent);
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
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
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
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }


}
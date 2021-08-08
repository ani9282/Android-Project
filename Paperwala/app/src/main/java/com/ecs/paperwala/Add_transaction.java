package com.ecs.paperwala;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Add_transaction extends AppCompatActivity {

    private String date,sale_order_id,name;
    private EditText btnDate,order_id;
    private TextView retailer_name;
    private Button add;
    private String city_id,distributor_id,retailer_id,amt;
    private double retailer_balance;
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    public static final String LOGIN_KEY = "com.ecs.royalrex.loginkey";
    private SharedPreferences.Editor editor;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        date=getIntent().getStringExtra("date");
        city_id=getIntent().getStringExtra("city_id");
        distributor_id=getIntent().getStringExtra("distributor_id");
        retailer_id=getIntent().getStringExtra("rid");
        retailer_balance=getIntent().getDoubleExtra("retailer_balance", 0.00);
        btnDate = (EditText) findViewById(R.id.date);
        order_id=(EditText)findViewById(R.id.order_id);
        add=(Button)findViewById(R.id.add_transaction);
        name= getIntent().getStringExtra("name");
        retailer_name=(TextView)findViewById(R.id.name);
        retailer_name.setText(name);
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Calendar cal = Calendar.getInstance();
        Date date1 = cal.getTime();
        date = dateFormat.format(date1);


        DateFormat dateFormat5 = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal5 = Calendar.getInstance();
        Date date4 = cal.getTime();
        String date5 = dateFormat5.format(date4);
        btnDate.setText(date5);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Add_transaction.this,"You Click Add Sale Order",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Add_transaction.this,Add_transaction_detail.class);
                intent.putExtra("date",date);
                intent.putExtra("id",sale_order_id);
                intent.putExtra("city_id",city_id);
                intent.putExtra("distributor_id",distributor_id);
                intent.putExtra("retailer_balance",retailer_balance);
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("id", retailer_id);
                editor.apply();
                startActivity(intent);

            }
        });

        // this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS") ;
        DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
        Calendar cal1 = Calendar.getInstance();
        Date date2 = cal1.getTime();
        sale_order_id = dateFormat1.format(date2);
        order_id.setText(sale_order_id);

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





}
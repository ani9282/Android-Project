package com.ecs.paperwala;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Edit_retailer extends AppCompatActivity {

    EditText name,address,area,state,city,pincode,mobile,alternate_mobile,email,dob,balance;
    private String sid,sname,date;
    private int id;
    private EditText btnDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_retailer);
        name=(EditText)findViewById(R.id.name);
        address=(EditText)findViewById(R.id.address);
        area=(EditText)findViewById(R.id.area);
        state=(EditText)findViewById(R.id.state);
        city=(EditText)findViewById(R.id.city);
        pincode=(EditText)findViewById(R.id.pincode);
        mobile=(EditText)findViewById(R.id.mobile);
        alternate_mobile=(EditText)findViewById(R.id.alternate_mobile);
        email=(EditText)findViewById(R.id.email);
        btnDate = (EditText) findViewById(R.id.dob);
        balance=(EditText)findViewById(R.id.amount);
        //Intent intent = getIntent();
        sid= getIntent().getStringExtra("id");
        id=Integer.parseInt(sid);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });



        String URL = "http://paperwala.live/api/webdistributor/RetailerById?id="+id+"";
        StringRequest request = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {

                    JSONArray contacts = new JSONArray(s);
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("RetailerId");
                        String retailer_name=c.getString("RetailerName");
                        name.setText(retailer_name);
                        String retailer_address=c.getString("RetailerAddress");
                        address.setText(retailer_address);
                        String retailer_pincode=c.getString("Pincode");
                        pincode.setText(retailer_pincode);
                        String retailer_area=c.getString("Area");
                        area.setText(retailer_area);
                        String retailer_mobile=c.getString("MobileNo");
                        mobile.setText(retailer_mobile);
                        String retailer_alternate_mobile=c.getString("AltMobileNo");
                        alternate_mobile.setText(retailer_alternate_mobile);
                        String retailer_city=c.getString("CityName");
                        city.setText(retailer_city);
                        String retailer_state=c.getString("StateName");
                        state.setText(retailer_state);
                        String retailer_email=c.getString("Email");
                        email.setText(retailer_email);
                        String retailer_dob=c.getString("DateofBirth");
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try
                        {
                            Date date1 = simpleDateFormat.parse(retailer_dob);

                            //System.out.println("date : "+simpleDateFormat.format(date1));
                            SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
                            String strDate = sm.format(date1);
                            btnDate.setText(strDate);
                        }
                        catch (ParseException ex)
                        {
                            System.out.println("Exception "+ex);
                        }
                        //btnDate.setText(retailer_dob);
                        String retailer_amount=c.getString("RemainingAMT");
                        balance.setText(retailer_amount);


                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Edit_retailer.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
            }
        }) {

        };

        RequestQueue rQueue = Volley.newRequestQueue(Edit_retailer.this);
        rQueue.add(request);


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

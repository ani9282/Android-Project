package com.ecs.mahaeseva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class My_order extends AppCompatActivity {

    private Button otp, register;
    private EditText phoneNumber, dial;
    private String ph, generate_otp = "", dial_otp;
    int otpno, counter = 0, attempt = 4, order_id;
    private String msg;

    public boolean checkForMobile() {
        Context c;
        EditText mEtMobile = (EditText) findViewById(R.id.mobile);
        String mStrMobile = mEtMobile.getText().toString();
        if (android.util.Patterns.PHONE.matcher(mStrMobile).matches() || android.util.Patterns.PHONE.matches("[a-zA-Z]+", mStrMobile)) {
            if (mStrMobile.length() <= 9 || mStrMobile.length() >= 11) {

                Toast.makeText(this, "Phone No is not valid", Toast.LENGTH_LONG).show();
            } else {
                return true;
            }

        }


        Toast.makeText(this, "Phone No is not valid", Toast.LENGTH_LONG).show();
        return false;
    }

    public int randomotpgenerate() {

        return ((int) (Math.random() * 9000) + 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        otp = (Button) findViewById(R.id.btnotp);
        register = (Button) findViewById(R.id.register);

        phoneNumber = findViewById(R.id.mobile);
        dial = (EditText) findViewById(R.id.otp);


        otp.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                counter = 0;
                attempt = 4;
                boolean ans = checkForMobile();

                if (ans == true) {
                    ph = phoneNumber.getText().toString();
                    otpno = randomotpgenerate();
                    //otpno=1212;
                    generate_otp = String.valueOf(otpno);
                    String message = "Your MahaESeva Kundal One Time Password(OTP) is: " + otpno;
                    String user = "rahulecs";
                    String password = "rahulecs";
                    String number =ph;
                    //  String Message="Welcome To Mahaeseva Your One Time Password OTP is ="+otp;
                    String SID = "HINFRM";
                    String smssigma =  "http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user+"&password="+password+"&msisdn="+number+"&sid="+SID+"&msg="+message+"&unicode=1"+"&fl=0&gwid=2";
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, smssigma, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(My_order.this, "OTP sent to your mobile", Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(My_order.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });


                    RequestQueue requestQueue = Volley.newRequestQueue(My_order.this);
                    requestQueue.add(stringRequest);


                }


            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dial_otp = dial.getText().toString();
                counter++;

                phoneNumber = findViewById(R.id.mobile);
                ph = phoneNumber.getText().toString();



                if (ph.equals("") || android.util.Patterns.PHONE.matches("[a-zA-Z]+", ph) || ph.length() <= 9 || ph.length() >= 11) {

                    Toast.makeText(My_order.this, "Please Enter Valid Phone.... ", Toast.LENGTH_LONG).show();


                } else if (dial_otp.equals("")) {
                    Toast.makeText(My_order.this, "Please First Generate OTP...", Toast.LENGTH_LONG).show();
                } else if (generate_otp.equals("")) {
                    Toast.makeText(My_order.this, "Please First Generate OTP...", Toast.LENGTH_LONG).show();
                } else {
                    if (counter < 4) {
                        if (generate_otp.equals(dial_otp)) {

                            String msg = ph;
                            Intent i = new Intent(My_order.this, Show_history.class);
                            i.putExtra("msg", msg);
                            startActivity(i);
                        } else {
                            attempt--;
                            Toast.makeText(My_order.this, "Invalid OTP.......  OTP attempt Left " + attempt, Toast.LENGTH_LONG).show();
                        }


                    } else {

                        int otp;
                        otp = randomotpgenerate();
                        generate_otp = String.valueOf(otpno);
                        Toast.makeText(My_order.this, "OTP attempt Failed ", Toast.LENGTH_LONG).show();
                    }

                }

            }


        });
    }
}
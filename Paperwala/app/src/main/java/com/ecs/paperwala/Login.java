package com.ecs.paperwala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MicrophoneInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    int id;
    EditText emailBox,passwordBox;
    public static final String PASS = null;
    public static final String MOB_NO = null;
    Button register;
    String username,password;
    private ConstraintLayout constraintLayout;
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.paperwala.file";
    public static final String LOGIN_KEY = "com.ecs.paperwala.loginkey";
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailBox = (EditText)findViewById(R.id.userName);
        passwordBox = (EditText)findViewById(R.id.password);
        constraintLayout=(ConstraintLayout)findViewById(R.id.snackbar);

        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences.getString(LOGIN_KEY,"no").equals("yes")) {
            Intent intent = new Intent(Login.this,Home_page.class);
            startActivity(intent);
            finish();
        }


        register=(Button)findViewById(R.id.btn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=emailBox.getText().toString();
                password=passwordBox.getText().toString();



                String URL ="http://paperwala.live/api/WebLoginDistributor/GetLogin?UserName="+username+"&Password="+password+"";
                StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        try {

                            JSONObject jsonObj = new JSONObject(s);
                            String DisID=jsonObj.getString("DistributorId");
                             id=Integer.parseInt(DisID);
                            if(id!=0) {
                                String CityID = jsonObj.getString("CityId");
                                Intent i = new Intent(Login.this, Home_page.class);
                               // i.putExtra("id",id);
                                editor.putString(LOGIN_KEY,"yes");
                                editor.putString("id",DisID);
                                editor.apply();
                                startActivity(i);
                            }
                            else
                            {

                                if(username.isEmpty() || password.isEmpty())
                                {
                                    Snackbar snackbar = Snackbar
                                            .make(constraintLayout, "Can not Empty Username and Password", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }

                                else
                                {
                                    //Toast.makeText(Login.this, "Please Enter Correct UserName and Password", Toast.LENGTH_LONG).show();;
                                    Snackbar snackbar = Snackbar.make(constraintLayout, "Please Enter Correct UserName and Password", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Login.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
                    }
                }) {

                };

                RequestQueue rQueue = Volley.newRequestQueue(Login.this);
                rQueue.add(request);
            }
        });//end of register button click

    }


}
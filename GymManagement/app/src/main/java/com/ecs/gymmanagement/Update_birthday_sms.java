package com.ecs.gymmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class Update_birthday_sms extends AppCompatActivity {

    private EditText sms,osms;
    private String oldsms;
    private Button register;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_birthday_sms);
        osms=findViewById(R.id.oldsms);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("https://ecssofttech.com/api/Gym/oldsms.php")  //send parameteres to php script
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            System.out.println(responseString);
            try {
                JSONArray contacts = new JSONArray(responseString);

                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    oldsms=c.getString("sms").toString();
                    osms.setText(oldsms);
                }



            } catch (Exception e) {
                Toast.makeText(Update_birthday_sms.this, "" + e, Toast.LENGTH_SHORT).show();
            }
            Log.d("This activity ==== ","Old sms Response"+responseString);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading Please Wait.......");
        progressDialog.setCancelable(false);
        sms=(EditText)findViewById(R.id.message);
        register=findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(sms.getText().toString().isEmpty())
                    {
                        Toast.makeText(Update_birthday_sms.this, "Fill the Field", Toast.LENGTH_SHORT).show();
                    }

                    else {
                            SaveData saveData=new SaveData();
                            saveData.execute();
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
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("https://ecssofttech.com/api/Gym/updatebirthdaysms.php?sms="+"%27"+sms.getText().toString()+"%27")  //send parameteres to php script
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
            AlertDialog.Builder dialog=new AlertDialog.Builder(Update_birthday_sms.this);
            dialog.setMessage("Registration Successfully Thank You......");
            dialog.setTitle("Registration Alert");
            dialog.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            Intent i=new Intent(Update_birthday_sms.this,Homepage.class);
                            startActivity(i);
                        }
                    });

            AlertDialog alertDialog=dialog.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
    }
}
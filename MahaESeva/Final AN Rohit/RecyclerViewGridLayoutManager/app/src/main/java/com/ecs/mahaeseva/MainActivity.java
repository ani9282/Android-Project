package com.ecs.mahaeseva;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import instamojo.library.InstapayListener;
import instamojo.library.InstamojoPay;

import org.json.JSONObject;
import org.json.JSONException;
import android.content.IntentFilter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener {


    RecyclerView recyclerView;
    ArrayList<DataModel> arrayList;

    
    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
       pay.put("send_sms", true);
      pay.put("send_email", true);
 } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }
    
    InstapayListener listener;

    
    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Call the function callInstamojo to start payment here


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        arrayList.add(new DataModel("उत्पन्नाचा दाखला", R.drawable.img, "#09A9FF"));
        arrayList.add(new DataModel("डोमेसाइल दाखला ", R.drawable.img, "#3E51B1"));
        arrayList.add(new DataModel("जातीचा दाखला ", R.drawable.img, "#673BB7"));
        arrayList.add(new DataModel("नॉन क्रिमिनल दाखला",  R.drawable.img, "#4BAA50"));
        arrayList.add(new DataModel("EWS दाखला ",  R.drawable.img, "#F94336"));
        arrayList.add(new DataModel("शेतकरी दाखला ", R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("अल्पभूधारक  दाखला ",  R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("३३% महिला आरक्षण दाखला ", R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("आधार कार्ड ", R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel(" पॅन कार्ड ", R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("जात पडताळणी ", R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("गॅझेट/राजपत्र ",  R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("शॉप कायदा ",  R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("उद्योग आधार ",  R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("पोलीस व्हेरीफिकेशन ",  R.drawable.img, "#0A9B88"));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);


        /**
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 230);
        recyclerView.setLayoutManager(layoutManager);


        /**
         Simple GridLayoutManager that spans two columns
         **/
        /*GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);*/
    }

    @Override
    public void onItemClick(DataModel item) {

        Toast.makeText(getApplicationContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();
       // Intent i=new Intent(MainActivity.this,Main2Activity.class);
       // startActivity(i);


            if (item.text.equals("उत्पन्नाचा दाखला")) {
                Intent i=new Intent(MainActivity.this,income_certificate.class);
                startActivity(i);
            } else if (item.text.equals("डोमेसाइल दाखला ")) {
                Intent i=new Intent(MainActivity.this,domicile_certificate.class);
                startActivity(i);
            } else if (item.text.equals("जातीचा दाखला ")) {
                Intent i=new Intent(MainActivity.this,Caste_Certificate.class);
                startActivity(i);
            }
            else if (item.text.equals("नॉन क्रिमिनल दाखला")) {
                Intent i=new Intent(MainActivity.this,Non_Criminal_Certificate.class);
                startActivity(i);
            }
            else if (item.text.equals("EWS दाखला ")) {
                Intent i=new Intent(MainActivity.this,Ews_Certificate.class);
                startActivity(i);
            }
            else if (item.text.equals("शेतकरी दाखला ")) {
                Intent i=new Intent(MainActivity.this,Farmer_Certificate.class);
                startActivity(i);
            }
            else if (item.text.equals("अल्पभूधारक  दाखला ")) {
                Intent i=new Intent(MainActivity.this,Minority_Certificate.class);
                startActivity(i);
            }
            else if (item.text.equals("३३% महिला आरक्षण दाखला ")) {
                Intent i=new Intent(MainActivity.this,Female_Reservation_Certificate.class);
                startActivity(i);
            }
            else if (item.text.equals("आधार कार्ड ")) {
                Intent i=new Intent(MainActivity.this,Adhar_Card.class);
                startActivity(i);
            }
            else if (item.text.equals(" पॅन कार्ड ")) {
                Intent i=new Intent(MainActivity.this,Pan_Card.class);
                startActivity(i);
            }
            else if (item.text.equals("जात पडताळणी ")) {
                Intent i=new Intent(MainActivity.this,Caste_Verification.class);
                startActivity(i);
            }
            else if (item.text.equals("गॅझेट/राजपत्र ")) {
                Intent i=new Intent(MainActivity.this,Gadget.class);
                startActivity(i);
            }

            else if (item.text.equals("शॉप कायदा ")) {
                Intent i=new Intent(MainActivity.this,Shop_Act.class);
                startActivity(i);
            }

            else if (item.text.equals("उद्योग आधार ")) {
                Intent i=new Intent(MainActivity.this,Udyog_adhar.class);
                startActivity(i);
            }

            else if (item.text.equals("पोलीस व्हेरीफिकेशन ")) {
                Intent i=new Intent(MainActivity.this,Police_Verification.class);
                startActivity(i);
            }




    }
}

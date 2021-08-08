package com.rajendra.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {
 TextView textView7;
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textView7 = (TextView) findViewById(R.id.textView7);
        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopUpWindow();
            }
        });



        ImageView btn1=(ImageView) findViewById(R.id.imageView12);

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Your order is placed! You Will Get Soon.. ", Toast.LENGTH_SHORT).show();

            }


        });



    }

    private void openPopUpWindow() {
    Intent popup = new Intent(DetailsActivity.this, PopupWindow.class);
    startActivity(popup);
    }

}

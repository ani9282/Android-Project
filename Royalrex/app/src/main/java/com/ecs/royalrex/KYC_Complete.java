package com.ecs.royalrex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class KYC_Complete extends AppCompatActivity {

    Button register;
    String role,mobile,first_name,last_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_y_c__complete);
        role = getIntent().getStringExtra("role");
        first_name = getIntent().getStringExtra("first_name");
        last_name = getIntent().getStringExtra("last_name");
        mobile=getIntent().getStringExtra("mobile");
        register=(Button)findViewById(R.id.ride);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(role.equals("Sender"))
                {
                    Intent i=new Intent(KYC_Complete.this,Sender_profile.class);
                    i.putExtra("mobile",mobile);
                    i.putExtra("role",role);
                    i.putExtra("first_name",first_name);
                    i.putExtra("last_name",last_name);
                    startActivity(i);
                    finish();
                }

                else if(role.equals("Driver")) {
                   Intent i=new Intent(KYC_Complete.this,Driver_profile.class);
                   startActivity(i);
                   finish();
                }
            }
        });


    }


}
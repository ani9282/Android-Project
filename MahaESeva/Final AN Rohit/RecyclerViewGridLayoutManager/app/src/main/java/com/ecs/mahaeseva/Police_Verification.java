package com.ecs.mahaeseva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Police_Verification extends AppCompatActivity {


    private Button mButton;
    private String msg="पोलीस व्हेरीफिकेशन";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police__verification);
        mButton = findViewById(R.id.button);



        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Police_Verification.this,User_Registration.class);
                i.putExtra("msg", msg);
                startActivity(i);
            }
        });
    }
}
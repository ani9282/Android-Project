package com.ecs.mahaeseva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Minority_Certificate extends AppCompatActivity {


    private Button mButton;
    private String msg="अल्पभूधारक दाखला";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minority__certificate);


        mButton = findViewById(R.id.button);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Minority_Certificate.this,User_Registration.class);
                i.putExtra("msg", msg);
                startActivity(i);
            }
        });
    }
}
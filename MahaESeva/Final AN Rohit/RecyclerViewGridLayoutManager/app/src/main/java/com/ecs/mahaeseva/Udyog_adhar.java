package com.ecs.mahaeseva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Udyog_adhar extends AppCompatActivity {

    private Button mButton;
    private String msg="उद्योग आधार";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udyog_adhar);

        mButton = findViewById(R.id.button);




        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Udyog_adhar.this,User_Registration.class);
                i.putExtra("msg", msg);
                startActivity(i);
            }
        });
    }
}
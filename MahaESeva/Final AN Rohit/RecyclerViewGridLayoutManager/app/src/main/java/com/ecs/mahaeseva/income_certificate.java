package com.ecs.mahaeseva;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class income_certificate extends AppCompatActivity {

    private Button mButton;
    private String msg="उत्पन्नाचा दाखला";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_certificate);
        mButton = findViewById(R.id.button);




        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(income_certificate.this,User_Registration.class);
                i.putExtra("msg", msg);
                startActivity(i);
            }
        });
    }


}
package com.ecs.gymmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText username,password;
    Button register;
    //remember me
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.indoornavigation.file";
    public static final String LOGIN_KEY = "com.ecs.indoornavigation.loginkey";
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences.getString(LOGIN_KEY,"no").equals("yes")) {
            Intent intent = new Intent(Login.this,Homepage.class);
            startActivity(intent);
            finish();
        }

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        register=(Button)findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                {
                    Toast.makeText(Login.this, "All Field Required", Toast.LENGTH_SHORT).show();
                }

                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin"))
                {
                    Intent intent=new Intent(Login.this,Homepage.class);
                    editor.putString(LOGIN_KEY,"yes");
                    editor.apply();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                else {
                    Toast.makeText(Login.this, "Please Re-enter Credential", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
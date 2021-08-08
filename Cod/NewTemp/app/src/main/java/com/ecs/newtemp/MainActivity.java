package com.ecs.newtemp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout tilUsername;
    private TextInputEditText tieUsername;
    private TextInputLayout tilPassword;
    private TextInputEditText tiePassword;
    private MaterialButton btnSignIn;
    //remember me
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.smart.file";
    public static final String LOGIN_KEY = "com.ecs.key";
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tilUsername = findViewById(R.id.maTextInputLayout_username);
        tieUsername = findViewById(R.id.maTextInputEditText_username);
        tilPassword = findViewById(R.id.maTextInputLayout_password);
        tiePassword = findViewById(R.id.maTextInputEditText_password);

        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences.getString(LOGIN_KEY,"no").equals("yes")) {
            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
            startActivity(intent);
            finish();
        }


        btnSignIn = findViewById(R.id.maButton_signIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        tieUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilUsername.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tiePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    private void signIn() {
        String username = tieUsername.getText().toString();
        if (username.isEmpty()) {
            tilUsername.setError("Fill This");
            tieUsername.requestFocus();
            return;
        }
        String password = tiePassword.getText().toString();
        if (password.isEmpty()) {
            tilPassword.setError("Fill This");
            tiePassword.requestFocus();
            return;
        }
        if (username.equals("admin")) {
            if (password.equals("admin")) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                editor.putString(LOGIN_KEY,"yes");
                editor.apply();
                startActivity(intent);
                finish();
            }
            else {
                tilPassword.setError("Wrong Password");
                tiePassword.requestFocus();
            }
        }
        else {
            tilUsername.setError("Wrong username");
            tieUsername.requestFocus();
        }
    }
}

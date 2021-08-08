package com.ecs.hffcadminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    private EditText edUsername;
    private String username;
    private EditText edPassword;
    private String password;
    private Button btnLogin;
    private Connection connection;
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.hffcadminapp.file";
    public static final String LOGIN_KEY = "com.ecs.hffcadminapp.loginkey";
    private SharedPreferences.Editor editor;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
        edUsername = findViewById(R.id.maEditText_userName);
        edPassword = findViewById(R.id.maEditText_password);
        btnLogin = findViewById(R.id.maButton_login);
        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences.getString(LOGIN_KEY,"no").equals("yes")) {
            Intent intent = new Intent(MainActivity.this,AdminHomeActivity.class);
            startActivity(intent);
            finish();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminLogin();
            }
        });
    }

    private void adminLogin() {
        username = edUsername.getText().toString();
        password = edPassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            edUsername.setError(getString(R.string.fill_this));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            edPassword.setError(getString(R.string.fill_this));
            return;
        }
        AdminLogin adminLogin = new AdminLogin();
        adminLogin.execute();
    }

    public class AdminLogin extends AsyncTask<Void,Void,String> {
        private String response;
        private boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                connection = connectionMethod(USER_NAME,PASSWORD,DATABASE_NAME,IPADDRESS);
                if (connection != null) {
                    String query = "select * from hffc_admin_credentials where username = '"+username+"';";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        String passQuery = resultSet.getString("password");
                        if (passQuery.equals(password)) {
                            isSuccess = true;
                        }
                        else {
                            response = getString(R.string.wrong_password);
                        }
                    }
                    else {
                        response = getString(R.string.wrong_username);
                    }
                }
                else {
                    response = getString(R.string.check_internet_message);
                }
            }
            catch (SQLException e) {
                Log.d("exceptio",e.toString());
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (isSuccess) {
                editor.putString(LOGIN_KEY,"yes");
                editor.apply();
                Intent intent = new Intent(MainActivity.this,AdminHomeActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }

        }
    }

    public Connection connectionMethod(String username, String password, String databaseName, String ipAddress) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connUrl = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connUrl = "jdbc:jtds:sqlserver://" + ipAddress + ":1433/"+ databaseName + ";user=" + username+ ";password=" + password + ";";
            connection = DriverManager.getConnection(connUrl);
        }
        catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return connection;
    }
}

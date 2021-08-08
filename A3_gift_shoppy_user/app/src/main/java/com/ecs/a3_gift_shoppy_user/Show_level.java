package com.ecs.a3_gift_shoppy_user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Show_level extends AppCompatActivity {

    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private ProgressDialog progressDialog;
    private TextView level1,level2,level3,level4,level5,level6,level7,level8,level9,level10;
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.a3giftshopy.file";
    private SharedPreferences.Editor editor;
    private String mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_level);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading please wait......");
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mobile=sharedPreferences.getString("mobile","");
        level1=(TextView)findViewById(R.id.level1);
        level2=(TextView)findViewById(R.id.level2);
        level3=(TextView)findViewById(R.id.level3);
        level4=(TextView)findViewById(R.id.level4);
        level5=(TextView)findViewById(R.id.level5);
        level6=(TextView)findViewById(R.id.level6);
        level7=(TextView)findViewById(R.id.level7);
        level8=(TextView)findViewById(R.id.level8);
        level9=(TextView)findViewById(R.id.level9);
        level10=(TextView)findViewById(R.id.level10);

        SaveData saveData=new SaveData();
        saveData.execute();


    }

    class SaveData extends AsyncTask<String, String, String> {
        boolean isSuccess = false;
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+mobile+"'";
                    Statement statement = connection.createStatement();
                    ResultSet rs1 = statement.executeQuery(query);
                    while (rs1.next())
                    {
                        setText(level1,rs1.getString("level1"));
                        setText(level2,rs1.getString("level2"));
                        setText(level3,rs1.getString("level3"));
                        setText(level4,rs1.getString("level4"));
                        setText(level5,rs1.getString("level5"));
                        setText(level6,rs1.getString("level6"));
                        setText(level7,rs1.getString("level7"));
                        setText(level8,rs1.getString("level8"));
                        setText(level9,rs1.getString("level9"));
                        setText(level10,rs1.getString("level10"));

                    }
                } else {

                    response = "Please Connect to internet";
                }
            } catch (SQLException e) {

                Log.d("exceptio", e.toString());
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }


    private void setText(final TextView text,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }



    private Connection connectionMethod(String userName, String password, String databaseName, String ipaddress) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connUrl = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connUrl = "jdbc:jtds:sqlserver://" + ipaddress + ":1433/" + databaseName + ";user=" + userName + ";password=" + password + ";";
            connection = DriverManager.getConnection(connUrl);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;

    } //connection method


}
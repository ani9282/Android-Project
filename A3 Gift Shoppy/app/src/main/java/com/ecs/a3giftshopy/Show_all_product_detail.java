package com.ecs.a3giftshopy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

import id.zelory.compressor.Compressor;

public class Show_all_product_detail extends AppCompatActivity {

    TextView id,name,price,description;
    private int orderId;
    ImageView photo;
    private ProgressDialog progressDialog;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_product_detail);
        Intent intent = getIntent();
        orderId = intent.getIntExtra(ProductAdapter.DELIVERED_ORDER_ID,0);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading Pleas Wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        id=(TextView)findViewById(R.id.id);
        name=(TextView)findViewById(R.id.name);
        price=(TextView) findViewById(R.id.price);
        description=(TextView)findViewById(R.id.description);
        photo=(ImageView)findViewById(R.id.photo);
        GetOrdersDetail getOrdersDetail=new GetOrdersDetail();
        getOrdersDetail.execute();

    }

    private class GetOrdersDetail extends AsyncTask<Void,Void,String> {
        private String response;
        private boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                connection = connectionMethod(USER_NAME,PASSWORD,DATABASE_NAME,IPADDRESS);
                if (connection != null) {
                    String query = "select * from A3_gift_shoppy where id = "+orderId+";";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        String id1=String.valueOf(orderId);
                        isSuccess = true;
                        setText(id,id1);
                        setText(name,resultSet.getString("name"));
                        setText(price,resultSet.getString("price"));
                        setText(description,resultSet.getString("description"));
                        setText1(resultSet.getString("photo"));

                    }

                }
                else {
                    response ="check_internet_message";
                }
            }
            catch (SQLException e) {
                Log.d("exceptio",e.toString());
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }

    private void setText(final TextView text, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    private void setText1(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //text.setText(value);
                //ImageIcon imageIcon = new ImageIcon(value);
                try {
                    //byte [] encodeByte= Base64.decode(value,Base64.DEFAULT);
                    //Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                    //photo.setImageBitmap(bitmap);
                } catch(Exception e) {
                    e.getMessage();
                }
            }
        });
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
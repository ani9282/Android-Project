package com.ecs.a3giftshopy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Show_all_product extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    List<ModelClass> modelList;
    private ProductAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_product);
        progressDialog = new ProgressDialog(Show_all_product.this);
        progressDialog.setMessage("Loading Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Show_all_product.this));
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
                    String query = "select * from A3_gift_shoppy";
                    Statement statement = connection.createStatement();
                    ResultSet rs1 = statement.executeQuery(query);
                    modelList = new ArrayList<>();
                    while (rs1.next()) {
                        isSuccess = true;
                        ModelClass u=new ModelClass();
                        u.setId(rs1.getInt("id"));
                        u.setName(rs1.getString("name"));
                        u.setPhoto(rs1.getString("photo"));
                        u.setPrice(rs1.getString("price"));
                        u.setDescription(rs1.getString("description"));
                        modelList.add(u);
                        response = "Data Added Successfully...";
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
            //setting adapter to recyclerview
            adapter = new ProductAdapter(Show_all_product.this,modelList);
            recyclerView.setAdapter(adapter);
        }
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
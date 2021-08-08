package com.ecs.hindfarmfreshchicken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends AppCompatActivity {
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private RecyclerView recyclerView;
    private OrdersAdapter adapter;
    private ProgressDialog progressDialog;
    private List<ModelOrders> list;
    private SharedPreferences preferences;
    private String sharedPrefFile = "com.ecs.hindfarmfreshchicken.shareFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        preferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        recyclerView = findViewById(R.id.moRecyclerView_orders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyOrdersActivity.this));
        progressDialog = new ProgressDialog(MyOrdersActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        new GetOrdersData().execute();
    }

    private class GetOrdersData extends AsyncTask<Void,Void,String> {
        private String response;
        private boolean isSuccess = false;
        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            list = new ArrayList<>();
            try {
                connection = connectionMethod(USER_NAME,PASSWORD,DATABASE_NAME,IPADDRESS);
                if (connection != null) {
                    isSuccess = true;
                    String query = "select * from hffc_orders where phone_number = '"+preferences.getString(PlaceOrder2Activity.CUSTOMER_PHONE,"0")+"' and order_status = 'pending';";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {
                        ModelOrders orders = new ModelOrders(resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getFloat(7),
                                resultSet.getString(8),
                                resultSet.getString(9),
                                resultSet.getString(10),
                                resultSet.getString(11));
                        list.add(orders);
                    }
                }
                else {
                    response = "Please check internet connection";
                }
                adapter = new OrdersAdapter(MyOrdersActivity.this,list);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            if (isSuccess) {
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }
            else {
                progressDialog.dismiss();
                Toast.makeText(MyOrdersActivity.this, s, Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetOrdersData getOrdersData = new GetOrdersData();
        getOrdersData.execute();
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

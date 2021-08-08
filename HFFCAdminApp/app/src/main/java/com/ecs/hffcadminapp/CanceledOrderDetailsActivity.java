package com.ecs.hffcadminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CanceledOrderDetailsActivity extends AppCompatActivity {
    private int orderId;
    private TextView tvOrderId;
    private TextView tvPhone;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvOrderStatus;
    private TextView tvOrderQuantity;
    private TextView tvOrderCost;
    private TextView tvMop;
    private TextView tvPaymentStatus;
    private TextView tvOrderDate;
    private TextView tvOrderTime;
    private Button btnDeleteOrder;
    private Button btnRetainOrder;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private ProgressDialog progressDialog;
    private ModelOrders detailOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canceled_order_details);
        Intent intent = getIntent();
        orderId = intent.getIntExtra(CanceledOrdersAdapter.CANCELED_ORDER_ID,0);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
        tvOrderId = findViewById(R.id.codTextview_orderId);
        tvPhone = findViewById(R.id.codTextView_number);
        tvName = findViewById(R.id.codTextView_name);
        tvAddress = findViewById(R.id.codTextView_address);
        tvOrderStatus = findViewById(R.id.codTextView_orderStatus);
        tvOrderQuantity = findViewById(R.id.codTextView_quantity);
        tvOrderCost = findViewById(R.id.codTextView_cost);
        tvMop = findViewById(R.id.codTextView_mop);
        tvPaymentStatus = findViewById(R.id.codTextView_ps);
        tvOrderDate = findViewById(R.id.codTextView_date);
        tvOrderTime = findViewById(R.id.codTextView_time);
        btnDeleteOrder = findViewById(R.id.codButton_deleteOrder);
        btnRetainOrder = findViewById(R.id.codButton_retainOrder);

        btnDeleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CanceledOrderDetailsActivity.this);
                builder.setTitle(getString(R.string.delete_order));
                builder.setMessage(getString(R.string.are_you_sure));
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteOrder deleteOrder = new DeleteOrder();
                        deleteOrder.execute();
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        btnRetainOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CanceledOrderDetailsActivity.this);
                builder.setTitle(getString(R.string.retain_order));
                builder.setMessage(getString(R.string.are_you_sure));
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RetainOrder retainOrder = new RetainOrder();
                        retainOrder.execute();
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        GetOrdersDetail getOrdersDetail = new GetOrdersDetail();
        getOrdersDetail.execute();
    }

    private class RetainOrder extends AsyncTask<Void,Void,String> {
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
                    String query = "update hffc_orders set order_status = 'pending' where id = "+orderId+";";
                    Statement statement = connection.createStatement();
                    int resultSet = statement.executeUpdate(query);
                    if (resultSet == 0) {
                        response = "Some Error Occured";
                    }
                    else {
                        isSuccess = true;
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
            if (isSuccess) {
                progressDialog.dismiss();
                Toast.makeText(CanceledOrderDetailsActivity.this, getString(R.string.success), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CanceledOrderDetailsActivity.this,AdminHomeActivity.class);
                startActivity(intent);
            }
            else {
                progressDialog.dismiss();
                Toast.makeText(CanceledOrderDetailsActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DeleteOrder extends AsyncTask<Void,Void,String> {
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
                    String query = "delete from hffc_orders where id = "+orderId+";";
                    Statement statement = connection.createStatement();
                    int resultSet = statement.executeUpdate(query);
                    if (resultSet == 0) {
                        response = "Some Error Occured";
                    }
                    else {
                        isSuccess = true;
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
            if (isSuccess) {
                progressDialog.dismiss();
                Toast.makeText(CanceledOrderDetailsActivity.this, getString(R.string.success), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CanceledOrderDetailsActivity.this,AdminHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
            else {
                progressDialog.dismiss();
                Toast.makeText(CanceledOrderDetailsActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class GetOrdersDetail extends AsyncTask<Void,Void,String> {
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
                    String query = "select * from hffc_orders where id = "+orderId+";";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        isSuccess = true;
                        detailOrder = new ModelOrders(resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getFloat(7),
                                resultSet.getString(8),
                                resultSet.getString(9),
                                resultSet.getString(10),
                                resultSet.getString(11)
                        );
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
                tvOrderId.setText(": "+detailOrder.getId());
                tvPhone.setText(": "+detailOrder.getPhoneNumber());
                tvName.setText(": "+detailOrder.getCustomerName());
                tvAddress.setText(": "+detailOrder.getCustomerAddress());
                tvMop.setText(": "+detailOrder.getModeOfPayment());
                tvOrderCost.setText(": "+detailOrder.getCost());
                tvOrderDate.setText(": "+detailOrder.getDate());
                tvOrderQuantity.setText(": "+detailOrder.getQuantity());
                tvOrderTime.setText(": "+detailOrder.getTime());
                tvPaymentStatus.setText(": "+detailOrder.getPaymentStatus());
                tvOrderStatus.setText(": "+detailOrder.getOrderStatus());
            }
            else {
                Toast.makeText(CanceledOrderDetailsActivity.this, s, Toast.LENGTH_SHORT).show();
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

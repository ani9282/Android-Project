package com.ecs.mahaeseva;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Show_history extends AppCompatActivity implements View.OnClickListener {
    String response;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";

    //database value declaration
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int count;

    TableLayout tableLayout;
    ArrayList<ModelClass> modelList;
    private ProgressDialog progressDialog;
    String gmobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_history);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("msg");
        gmobile = message;

        initViews();

        tableLayout = (TableLayout)findViewById(R.id.tbl_layout);
        progressDialog = new ProgressDialog(Show_history.this);

        progressDialog.setMessage("Loading Data Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        fetchdata();


    }



    public void initViews(){
        tableLayout = (TableLayout)findViewById(R.id.tbl_layout);
        addHeaders();

        modelList = new ArrayList<>();
        ModelClass modelClass = new ModelClass();
        modelClass.setId(modelClass.getId());
        modelClass.setCname(modelClass.getCname());
        modelClass.setCertificate_type(modelClass.getAddress());
        modelClass.setAddress(modelClass.getAddress());
        modelList.add(modelClass);




    }



    public void addHeaders() {

        TableLayout tl = findViewById(R.id.tbl_layout);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());

        //tr.addView(getTextView(0, "Auditor id", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "ID", Color.WHITE, Typeface.BOLD, R.color.colorAccent ));
        tr.addView(getTextView(0, "Name", Color.WHITE, Typeface.BOLD,R.color.colorAccent ));
        tr.addView(getTextView(0, "Type", Color.WHITE, Typeface.BOLD,R.color.colorAccent ));
       tr.addView(getTextView(0, "address",Color.WHITE, Typeface.BOLD, R.color.colorAccent ));
        tr.addView(getTextView(0, "Mobile", Color.WHITE, Typeface.BOLD,R.color.colorAccent ));
       tr.addView(getTextView(0, "Price", Color.WHITE, Typeface.BOLD,R.color.colorAccent ));
        tr.addView(getTextView(0, "Payment", Color.WHITE, Typeface.BOLD, R.color.colorAccent ));
        tl.addView(tr, getTblLayoutParams());
    }

    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(10, 10, 10, 10);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setBackgroundResource(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }


    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(1, 1, 1, 1);
        params.weight = 1;
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {

    }

    public void addRows(){
        // Collections.reverse(trainScheduleList);
        for (int i = 0; i < modelList.size(); i++) {
            TableRow tr = new TableRow(Show_history.this);
            tr.setLayoutParams(getLayoutParams());
            int id=modelList.get(i).getId();
            String s=String.valueOf(id);
          tr.addView(getRowsTextView(0, s, Color.BLACK, Typeface.BOLD, R.drawable.cell_shape ));
           tr.addView(getRowsTextView(0, modelList.get(i).getCname(), Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
           tr.addView(getRowsTextView(0, modelList.get(i).getCertificate_type(), Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
            tr.addView(getRowsTextView(0, modelList.get(i).getAddress(), Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
            tr.addView(getRowsTextView(0, modelList.get(i).getMobile(), Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
           tr.addView(getRowsTextView(0, modelList.get(i).getPrice(), Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
            tr.addView(getRowsTextView(0, modelList.get(i).getPayment_status(), Color.BLACK, Typeface.BOLD ,R.drawable.cell_shape ));
            tableLayout.addView(tr, getTblLayoutParams());

        }

    }

    private TextView getRowsTextView(int id, String title, int color, int typeface,int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title);
        tv.setTextColor(color);
        tv.setPadding(10, 10, 10, 10);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundResource(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }




    private void fetchdata()
    {
        Show_history.SaveCODOrder saveCODOrder = new Show_history.SaveCODOrder();
        saveCODOrder.execute();
    }



    class SaveCODOrder extends AsyncTask<String, String, String> {
            boolean isSuccess = false;
            String response;

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            Runnable progressRunnable = new Runnable() {

                @Override
                public void run() {
                    progressDialog.cancel();
                }
            };

            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 3000);
        }



            protected String doInBackground(String... strings) {


                try {
                    connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                    if (connection != null) {
                        String query = "select  * from Mahaseva where mobile='"+gmobile+"'";
                        Statement statement = connection.createStatement();
                        ResultSet rs1 = statement.executeQuery(query);

                        modelList = new ArrayList<>();
                        while (rs1.next())
                        {

                            ModelClass u=new ModelClass();
                            u.setId(rs1.getInt(1));
                            u.setCname(rs1.getString(2));
                            u.setCertificate_type(rs1.getString(3));
                            u.setAddress(rs1.getString(4));
                            u.setMobile(rs1.getString(5));
                            u.setPrice(rs1.getString(6));
                            u.setPayment_status(rs1.getString(7));
                            modelList.add(u);
                        }





                    } else {


                        response = "Please Connect to internet";
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                return response;

            }//end database method

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            addRows();
        }
    }


    //connection method
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




    }//end of program
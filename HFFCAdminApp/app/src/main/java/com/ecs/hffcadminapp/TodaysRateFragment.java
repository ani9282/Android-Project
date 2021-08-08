package com.ecs.hffcadminapp;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodaysRateFragment extends Fragment {
    private EditText edRate;
    private Button btnUpdateRate;
    private String todaysRate;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private ProgressDialog progressDialog;


    public TodaysRateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_todays_rate, container, false);
        edRate = root.findViewById(R.id.treditText_rate);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
        btnUpdateRate = root.findViewById(R.id.trbutton_rate);
        btnUpdateRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatetRate();
            }
        });
        return root;
    }
    private void updatetRate() {
        todaysRate = edRate.getText().toString();
        if (TextUtils.isEmpty(todaysRate)) {
            edRate.setError("Fill This");
            return;
        }
        else {
            UpdateRate updateRate = new UpdateRate();
            updateRate.execute();
        }

    }
    public class UpdateRate extends AsyncTask<String,String,String> {
        String response;
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = connectionMethod(USER_NAME,PASSWORD,DATABASE_NAME,IPADDRESS);
                if (connection != null) {
                    String query = "update hffc_todays_rate set rate = "+todaysRate+" where id = 1;";
                    Statement statement = connection.createStatement();
                    int resultSet = statement.executeUpdate(query);
                    if (resultSet>0) {
                        isSuccess = true;
                        response = "Updated Successfully";
                    }
                    else {
                        response = "Error not updated";
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
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Connection connectionMethod(String username,String password,String databaseName,String ipAddress) {
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

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
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * A simple {@link Fragment} subclass.
 */
public class PromotionMessageFragment extends Fragment {
    private EditText edMessage;
    private String message;
    private TextView tvMessage;
    private Button btnUpdate;
    private ProgressDialog progressDialog;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";


    public PromotionMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotion_message, container, false);
        edMessage = view.findViewById(R.id.pmEditText_message);
        tvMessage = view.findViewById(R.id.pmTextView_message);
        btnUpdate = view.findViewById(R.id.pmButton_update);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
        GetOldData getOldData = new GetOldData();
        getOldData.execute();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = edMessage.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    edMessage.setError(getString(R.string.fill_this));
                    return;
                }
                SaveNewPromo saveNewPromo = new SaveNewPromo();
                saveNewPromo.execute(message);

            }
        });
        return view;
    }

    private class SaveNewPromo extends AsyncTask<String,Void,String> {
        private String response;
        private boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = connectionMethod(USER_NAME,PASSWORD,DATABASE_NAME,IPADDRESS);
                if (connection != null) {
                    String query = "update hffc_promotion set offer = '"+strings[0]+"' where id = 1;";
                    Statement statement = connection.createStatement();
                    int result1 = statement.executeUpdate(query);
                    if (result1>0 ) {
                        isSuccess = true;
                        response = "Updated Successfully";
                    }
                    else {
                        response = "Some error occurred";
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
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                GetOldData getOldData = new GetOldData();
                getOldData.execute();
            }
            else {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetOldData extends AsyncTask<Void,Void,String> {
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
                    String query = "select * from hffc_promotion where id = 1;";
                    Statement statement = connection.createStatement();
                    ResultSet result1 = statement.executeQuery(query);
                    if (result1.next() ) {
                        isSuccess = true;
                        message = result1.getString(2);
                    }
                    else {
                        response = "Some error occured";
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
                tvMessage.setText(message);
            }
            else {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
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

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateContactsFragment extends Fragment {
    private EditText edName1;
    private String name1;
    private EditText edNumber1;
    private String number1;
    private EditText edName2;
    private String name2;
    private EditText edNumber2;
    private String number2;
    private Button btnUpdate;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private ProgressDialog progressDialog;


    public UpdateContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_contacts, container, false);
        edName1 = view.findViewById(R.id.ucEditText_name1);
        edNumber1 = view.findViewById(R.id.ucEditText_number1);
        edName2 = view.findViewById(R.id.ucEditText_name2);
        edNumber2 = view.findViewById(R.id.ucEditText_number2);
        btnUpdate = view.findViewById(R.id.ucButton_update);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
        GetContactsData getContactsData = new GetContactsData();
        getContactsData.execute();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1 = edName1.getText().toString();
                if (TextUtils.isEmpty(name1)) {
                    edName1.setError(getString(R.string.fill_this));
                    return;
                }
                number1 = edNumber1.getText().toString();
                if (TextUtils.isEmpty(number1)) {
                    edNumber1.setError(getString(R.string.fill_this));
                    return;
                }
                name2 = edName2.getText().toString();
                if (TextUtils.isEmpty(name2)) {
                    edName2.setError(getString(R.string.fill_this));
                    return;
                }
                number2 = edNumber2.getText().toString();
                if (TextUtils.isEmpty(number2)) {
                    edNumber2.setError(getString(R.string.fill_this));
                    return;
                }
                UpdateData updateData = new UpdateData();
                updateData.execute();
            }
        });
        return view;
    }

    private class GetContactsData extends AsyncTask<Void,Void,String> {
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
                    String contact1query = "select * from hffc_contact_1 where id = 1;";
                    String contact2query = "select * from hffc_contact_2 where id = 2;";
                    Statement statementContact1 = connection.createStatement();
                    Statement statementContact2 = connection.createStatement();
                    ResultSet result1 = statementContact1.executeQuery(contact1query);
                    ResultSet result2 = statementContact2.executeQuery(contact2query);
                    if (result1.next() && result2.next() ) {
                        isSuccess = true;
                        name1 = result1.getString(2);
                        number1 = result1.getString(3);
                        name2 = result2.getString(2);
                        number2 = result2.getString(3);
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
                edName1.setText(name1);
                edNumber1.setText(number1);
                edName2.setText(name2);
                edNumber2.setText(number2);
            }
            else {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class UpdateData extends AsyncTask<Void,Void,String> {
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
                    String contact1query = "update hffc_contact_1 set name = '"+name1+"',phone = '"+number1+"' where id = 1;";
                    String contact2query = "update hffc_contact_2 set name = '"+name2+"',phone = '"+number2+"' where id = 2;";
                    Statement statementContact1 = connection.createStatement();
                    Statement statementContact2 = connection.createStatement();
                    int result1 = statementContact1.executeUpdate(contact1query);
                    int result2 = statementContact2.executeUpdate(contact2query);
                    if (result1>0 && result2>0 ) {
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

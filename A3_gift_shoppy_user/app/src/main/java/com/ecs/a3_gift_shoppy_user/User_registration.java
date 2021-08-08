package com.ecs.a3_gift_shoppy_user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class User_registration extends AppCompatActivity {

    EditText efname,elname,emobile,eaddress,erefrenceby;
    String role,otp,verifynumber;
    private Connection connection;
    Button submit;
    private int count=0,level=0,value=0,level1=0,level2=0,level3=0,level4=0,level5=0,level6=0,level7=0,level8=0,level9=0,level10=0;
    private int ref_level2=0,ref_level3=0,ref_level4=0,ref_level5=0,ref_level6=0,ref_level7=0,ref_level8=0,ref_level9=0,ref_level10=0;
    String strDate,status="Block",mobile,reference_by,reference_by3,reference_by4,reference_by5,reference_by6,reference_by7,reference_by8,reference_by9,reference_by10;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    //ArrayList<ModelClass> modelList;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.a3giftshopy.file";
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mobile=sharedPreferences.getString("mobile","");
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading Please Wait........");
        progressDialog.setCanceledOnTouchOutside(false);
        efname=(EditText)findViewById(R.id.fname);
        elname=(EditText)findViewById(R.id.lname);
        emobile=(EditText)findViewById(R.id.mobile);
        eaddress=(EditText)findViewById(R.id.address);
        erefrenceby=(EditText)findViewById(R.id.refrence);
        erefrenceby.setText(mobile);
        submit=(Button)findViewById(R.id.register);
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        strDate = dateFormat.format(date);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(efname.getText().toString().isEmpty() || elname.getText().toString().isEmpty() || emobile.getText().toString().isEmpty() || eaddress.getText().toString().isEmpty() || erefrenceby.getText().toString().isEmpty() || emobile.getText().toString().length()<=9 || emobile.getText().toString().length()>=11)
                {
                    if(efname.getText().toString().isEmpty() || elname.getText().toString().isEmpty() || emobile.getText().toString().isEmpty() || eaddress.getText().toString().isEmpty() || erefrenceby.getText().toString().isEmpty())
                    {
                        Toast.makeText(User_registration.this, "All Field Required", Toast.LENGTH_SHORT).show();
                    }

                    if(emobile.getText().toString().length()<=9 || emobile.getText().toString().length()>=11)
                    {
                        Toast.makeText(User_registration.this, "Mobile Number Invalid", Toast.LENGTH_SHORT).show();
                    }

                }

                else {
                    SaveData saveData=new SaveData();
                    saveData.execute();
                }
            }
        });



    }

    class SaveData extends AsyncTask<String, String, String> {
        boolean isSuccess = false;
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+mobile+"'";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                       // count = resultSet.getInt(1);
                        String slevel1=resultSet.getString("level1");
                        level1=Integer.parseInt(slevel1);
                        String slevel2=resultSet.getString("level2");
                        level2=Integer.parseInt(slevel2);
                       // String slevel3=resultSet.getString("level3");
                       // level3=Integer.parseInt(slevel3);

                        /*
                        String slevel5=resultSet.getString("level5");
                        level5=Integer.parseInt(slevel5);
                        String slevel6=resultSet.getString("level6");
                        level6=Integer.parseInt(slevel6);
                        String slevel7=resultSet.getString("level7");
                        level7=Integer.parseInt(slevel7);
                        String slevel8=resultSet.getString("level8");
                        level8=Integer.parseInt(slevel8);
                        String slevel9=resultSet.getString("level9");
                        level9=Integer.parseInt(slevel9);
                        String slevel10=resultSet.getString("level10");
                        level10=Integer.parseInt(slevel10);

                         */
                        reference_by=resultSet.getString("reference_by");
                        response = "Data Added Successfully...";


                    } else {
                        response = "Some Error..Order not placed";
                    }
                } else {

                    response = "Please Connect to internet";
                }
            } catch (SQLException e) {

                Log.d("exceptio", e.toString());
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            if(level1<2)
            {
                try {
                    connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                    if (connection != null) {
                        String query = "insert into A3_gift_shoppy_registration (first_name,last_name,mobile,address,reference_by_mobile_no,date,status) values(N'"+ efname.getText().toString() + "',N'" +elname.getText().toString() + "',N'" + emobile.getText().toString() + "','"+eaddress.getText().toString()+"','"+erefrenceby.getText().toString()+"','"+strDate+"','"+status+"');";
                        Statement statement = connection.createStatement();
                        int resultSet = statement.executeUpdate(query);
                        if (resultSet > 0) {
                            isSuccess = true;
                            level1++;

                                try {
                                    connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                                    if (connection != null) {
                                        //String query1 = "update A3_gift_shoppy_registration  set level1='"+level1+"',reference_by='"+mobile+"' where mobile='"+emobile.getText().toString()+"'";
                                        String query1 = "insert into A3_gift_shoppy_level (mobile,level1,level2,level3,level4,level5,level6,level7,level8,level9,level10,reference_by) values('"+emobile.getText().toString()+"','"+level+"','"+level+"','"+level+"','"+level+"','"+level+"','"+level+"','"+level+"','"+level+"','"+level+"','"+level+"','"+mobile+"');";
                                        Statement statement1 = connection.createStatement();
                                        int rs1 = statement.executeUpdate(query1);
                                        if (rs1>0)
                                        {
                                            connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                                            String query3 = "update A3_gift_shoppy_level  set level1='"+level1+"' where mobile='"+mobile+"'";
                                            Statement statement3 = connection.createStatement();
                                            int resultSet3 =statement3.executeUpdate(query3);
                                            if (resultSet3 > 0) {
                                                response = "Data Added Successfully...";
                                            } else {
                                                response = "Some Error..Order not placed";
                                            }
                                        }

                                        String query5 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by+"'";
                                        Statement statement5 = connection.createStatement();
                                        ResultSet resultSet5 = statement.executeQuery(query5);
                                        if (resultSet5.next()) {
                                            String slevel2=resultSet5.getString("level2");
                                            ref_level2=Integer.parseInt(slevel2);
                                            String slevel3=resultSet5.getString("level3");
                                            ref_level3=Integer.parseInt(slevel3);

                                        }

                                        ref_level2++;
                                        connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                                        String query4 = "update A3_gift_shoppy_level  set level2='"+ref_level2+"' where mobile='"+reference_by+"'";
                                        Statement statement4 = connection.createStatement();
                                        int resultSet4 =statement4.executeUpdate(query4);
                                        if (resultSet4 > 0) {
                                            response = "Data Added Successfully...";
                                        } else {
                                            response = "Some Error..Order not placed";
                                        }

                                        level2++;
                                        connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                                        String query6 = "update A3_gift_shoppy_level  set level2='"+level2+"' where mobile='"+mobile+"'";
                                        Statement statement6 = connection.createStatement();
                                        int resultSet6 =statement4.executeUpdate(query4);
                                        if (resultSet6 > 0) {
                                            response = "Data Added Successfully...";
                                        } else {
                                            response = "Some Error..Order not placed";
                                        }

    //---------------------------------------- Level 3 ----------------------------------------------------------
                                        String query7 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by+"'";
                                        Statement statement7 = connection.createStatement();
                                        ResultSet resultSet7 = statement7.executeQuery(query7);
                                        if (resultSet7.next()) {
                                            reference_by3=resultSet7.getString("reference_by");
                                        }


                                        String query11 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by3+"'";
                                        Statement statement11 = connection.createStatement();
                                        ResultSet resultSet11 = statement11.executeQuery(query11);
                                        if (resultSet11.next()) {
                                            String slevel3=resultSet11.getString("level3");
                                            ref_level3=Integer.parseInt(slevel3);
                                        }



                                        ref_level3++;
                                        connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                                        String query8 = "update A3_gift_shoppy_level  set level3='"+ref_level3+"' where mobile='"+reference_by3+"'";
                                        Statement statement8 = connection.createStatement();
                                        int resultSet8 =statement8.executeUpdate(query8);
                                        if (resultSet8 > 0) {
                                            response = "Data Added Successfully...";
                                        } else {
                                            response = "Some Error..Order not placed";
                                        }
//---------------------------------------------------Level 4 -------------------------------------------------------------------

                                        String query9 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by3+"'";
                                        Statement statement9 = connection.createStatement();
                                        ResultSet resultSet9 = statement9.executeQuery(query9);
                                        if (resultSet9.next()) {
                                           // String slevel4=resultSet9.getString("level4");
                                            reference_by4=resultSet9.getString("reference_by");
                                            //ref_level4=Integer.parseInt(slevel4);
                                        }

                                        String query12 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by4+"'";
                                        Statement statement12 = connection.createStatement();
                                        ResultSet resultSet12 = statement11.executeQuery(query12);
                                        if (resultSet12.next()) {
                                            String slevel4=resultSet12.getString("level4");
                                            ref_level4=Integer.parseInt(slevel4);
                                        }



                                       ref_level4++;
                                        connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                                        String query10 = "update A3_gift_shoppy_level  set level4='"+ref_level4+"' where mobile='"+reference_by4+"'";
                                        Statement statement10 = connection.createStatement();
                                        int resultSet10 =statement10.executeUpdate(query10);
                                        if (resultSet10 > 0) {
                                            response = "Data Added Successfully...";
                                        } else {
                                            response = "Some Error..Order not placed";
                                        }


 //-------------------------------------------------------- Level 5 -------------------------------------------------------------------

                                        String query13 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by4+"'";
                                        Statement statement13 = connection.createStatement();
                                        ResultSet resultSet13 = statement13.executeQuery(query13);
                                        if (resultSet13.next()) {
                                            // String slevel4=resultSet9.getString("level4");
                                            reference_by5=resultSet13.getString("reference_by");
                                            //ref_level4=Integer.parseInt(slevel4);
                                        }

                                        String query14 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by5+"'";
                                        Statement statement14 = connection.createStatement();
                                        ResultSet resultSet14 = statement14.executeQuery(query14);
                                        if (resultSet14.next()) {
                                            String slevel5=resultSet14.getString("level5");
                                            ref_level5=Integer.parseInt(slevel5);
                                        }



                                        ref_level5++;
                                        connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                                        String query15 = "update A3_gift_shoppy_level  set level5='"+ref_level5+"' where mobile='"+reference_by5+"'";
                                        Statement statement15 = connection.createStatement();
                                        int resultSet15 =statement15.executeUpdate(query15);
                                        if (resultSet15 > 0) {
                                            response = "Data Added Successfully...";
                                        } else {
                                            response = "Some Error..Order not placed";
                                        }
//------------------------------------------------ Level 6 --------------------------------------------------------------

                                        String query16 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by5+"'";
                                        Statement statement16 = connection.createStatement();
                                        ResultSet resultSet16 = statement13.executeQuery(query16);
                                        if (resultSet16.next()) {
                                            // String slevel4=resultSet9.getString("level4");
                                            reference_by6=resultSet16.getString("reference_by");
                                            //ref_level4=Integer.parseInt(slevel4);
                                        }

                                        String query17 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by6+"'";
                                        Statement statement17 = connection.createStatement();
                                        ResultSet resultSet17 = statement17.executeQuery(query17);
                                        if (resultSet17.next()) {
                                            String slevel6=resultSet17.getString("level6");
                                            ref_level6=Integer.parseInt(slevel6);
                                        }



                                        ref_level6++;
                                        connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                                        String query18 = "update A3_gift_shoppy_level  set level6='"+ref_level6+"' where mobile='"+reference_by6+"'";
                                        Statement statement18 = connection.createStatement();
                                        int resultSet18 =statement18.executeUpdate(query18);
                                        if (resultSet18 > 0) {
                                            response = "Data Added Successfully...";
                                        } else {
                                            response = "Some Error..Order not placed";
                                        }


   //----------------------------------------------- Level 7 ------------------------------------------------------------
                                        String query19 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by6+"'";
                                        Statement statement19 = connection.createStatement();
                                        ResultSet resultSet19 = statement13.executeQuery(query19);
                                        if (resultSet19.next()) {
                                            // String slevel4=resultSet9.getString("level4");
                                            reference_by7=resultSet19.getString("reference_by");
                                            //ref_level4=Integer.parseInt(slevel4);
                                        }

                                        String query20 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by7+"'";
                                        Statement statement20 = connection.createStatement();
                                        ResultSet resultSet20 = statement20.executeQuery(query20);
                                        if (resultSet20.next()) {
                                            String slevel7=resultSet20.getString("level7");
                                            ref_level7=Integer.parseInt(slevel7);
                                        }



                                        ref_level7++;
                                        connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                                        String query21 = "update A3_gift_shoppy_level  set level7='"+ref_level7+"' where mobile='"+reference_by7+"'";
                                        Statement statement21 = connection.createStatement();
                                        int resultSet21 =statement21.executeUpdate(query21);
                                        if (resultSet21 > 0) {
                                            response = "Data Added Successfully...";
                                        } else {
                                            response = "Some Error..Order not placed";
                                        }

   //------------------------------------------------------ Level 8 ---------------------------------------------------
                                        String query22 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by7+"'";
                                        Statement statement22 = connection.createStatement();
                                        ResultSet resultSet22 = statement22.executeQuery(query22);
                                        if (resultSet22.next()) {
                                            // String slevel4=resultSet9.getString("level4");
                                            reference_by8=resultSet22.getString("reference_by");
                                            //ref_level4=Integer.parseInt(slevel4);
                                        }

                                        String query23 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by8+"'";
                                        Statement statement23 = connection.createStatement();
                                        ResultSet resultSet23 = statement23.executeQuery(query23);
                                        if (resultSet23.next()) {
                                            String slevel8=resultSet23.getString("level8");
                                            ref_level8=Integer.parseInt(slevel8);
                                        }



                                        ref_level8++;
                                        connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                                        String query24 = "update A3_gift_shoppy_level  set level8='"+ref_level8+"' where mobile='"+reference_by8+"'";
                                        Statement statement24 = connection.createStatement();
                                        int resultSet24 =statement24.executeUpdate(query24);
                                        if (resultSet24 > 0) {
                                            response = "Data Added Successfully...";
                                        } else {
                                            response = "Some Error..Order not placed";
                                        }


      //----------------------------------------------------- Level 9 ----------------------------------------------------------
                                        String query25 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by8+"'";
                                        Statement statement25 = connection.createStatement();
                                        ResultSet resultSet25 = statement25.executeQuery(query25);
                                        if (resultSet25.next()) {
                                            // String slevel4=resultSet9.getString("level4");
                                            reference_by9=resultSet25.getString("reference_by");
                                            //ref_level4=Integer.parseInt(slevel4);
                                        }

                                        String query26 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by9+"'";
                                        Statement statement26 = connection.createStatement();
                                        ResultSet resultSet26 = statement26.executeQuery(query26);
                                        if (resultSet26.next()) {
                                            String slevel9=resultSet26.getString("level9");
                                            ref_level9=Integer.parseInt(slevel9);
                                        }



                                        ref_level9++;
                                        connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                                        String query27 = "update A3_gift_shoppy_level  set level9='"+ref_level9+"' where mobile='"+reference_by9+"'";
                                        Statement statement27 = connection.createStatement();
                                        int resultSet27 =statement27.executeUpdate(query27);
                                        if (resultSet27 > 0) {
                                            response = "Data Added Successfully...";
                                        } else {
                                            response = "Some Error..Order not placed";
                                        }

    //------------------------------------------------- Level 10 -----------------------------------------------------------------

                                        String query28 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by9+"'";
                                        Statement statement28 = connection.createStatement();
                                        ResultSet resultSet28 = statement28.executeQuery(query28);
                                        if (resultSet28.next()) {
                                            // String slevel4=resultSet9.getString("level4");
                                            reference_by10=resultSet28.getString("reference_by");
                                            //ref_level4=Integer.parseInt(slevel4);
                                        }

                                        String query29 = "SELECT * FROM A3_gift_shoppy_level  where mobile='"+reference_by10+"'";
                                        Statement statement29 = connection.createStatement();
                                        ResultSet resultSet29 = statement29.executeQuery(query29);
                                        if (resultSet29.next()) {
                                            String slevel10=resultSet29.getString("level10");
                                            ref_level10=Integer.parseInt(slevel10);
                                        }



                                        ref_level10++;
                                        connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                                        String query30 = "update A3_gift_shoppy_level  set level10='"+ref_level10+"' where mobile='"+reference_by10+"'";
                                        Statement statement30 = connection.createStatement();
                                        int resultSet30 =statement30.executeUpdate(query30);
                                        if (resultSet30 > 0) {
                                            response = "Data Added Successfully...";
                                        } else {
                                            response = "Some Error..Order not placed";
                                        }

     //---------------------------------------------- Level End -------------------------------------------------------------

                                    } else {

                                        response = "Please Connect to internet";
                                    }
                                } catch (SQLException e) {

                                    Log.d("exceptio", e.toString());
                                }

                            response = "Data Added Successfully...";
                        } else {
                            response = "Some Error..Order not placed";
                        }
                    } else {

                        response = "Please Connect to internet";
                    }
                } catch (SQLException e) {

                    Log.d("exceptio", e.toString());
                }
            }



            else {
                isSuccess=false;
            }

            return response;
        }//end database method

        @Override
        protected void onPostExecute(String s) {
            if(isSuccess==true)
            {
                String message = "Thanks For Registration on A3 Gift Shoppy";
                String user = "rahulecs";
                String password = "rahulecs";
                String number =mobile;
                //  String Message="Welcome To Mahaeseva Your One Time Password OTP is ="+otp;
                String SID = "ATGIFT";
                String smssigma = "http://5.189.153.48:8080/vendorsms/pushsms.aspx?user="+user+"&password="+password+"&msisdn="+number+"&sid="+SID+"&msg="+message+"&fl=0"+"&dc=8"+"&gwid=2";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, smssigma, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(User_registration.this, "Sms send to your Mobile Number Successfully....", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(User_registration.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


                RequestQueue requestQueue = Volley.newRequestQueue(User_registration.this);
                requestQueue.add(stringRequest);

                AlertDialog.Builder dialog=new AlertDialog.Builder(User_registration.this);
                dialog.setMessage("Registration Successfully Thank You......");
                dialog.setTitle("Registration Alert");
                dialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent i=new Intent(User_registration.this,Homepage.class);
                                startActivity(i);
                            }
                        });

                AlertDialog alertDialog=dialog.create();
                alertDialog.setCancelable(false);
                alertDialog.show();

            }
            else {
                progressDialog.dismiss();
                Toast.makeText(User_registration.this, "You can Add Only 2 Customer", Toast.LENGTH_LONG).show();
            }
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


    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(User_registration.this, Homepage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}
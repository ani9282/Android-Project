package com.ecs.hindfarmfreshchicken;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;




public class MainPageActivity extends AppCompatActivity  {
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private TextView tvRate;
    float response;
    private String contactName1, contactName2;
    private TextView tvContactName1, tvContactName2, tvContactNumber1, tvContactNumber2;
    private String contactNumber1, contactNumber2;
    ResultSet resultSetContacts2;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        tvRate = findViewById(R.id.textView11);
        tvContactName1 = findViewById(R.id.textView6);
        tvContactName2 = findViewById(R.id.textView10);
        tvContactNumber1 = findViewById(R.id.textView7);
        tvContactNumber2 = findViewById(R.id.textView9);
        progressDialog = new ProgressDialog(MainPageActivity.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
        GetData getData = new GetData();
        getData.execute();

    //auto update Code
        AppUpdateChecker appUpdateChecker=new AppUpdateChecker(this);  //pass the activity in constructure
        appUpdateChecker.checkForUpdate(false); //mannual check false here

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_myOrder) {
            Intent intent = new Intent(MainPageActivity.this, MyOrdersActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setEnglish(View view) {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        MainPageActivity.this.getResources().updateConfiguration(configuration, MainPageActivity.this.getResources().getDisplayMetrics());
        Toast.makeText(this, "Language set to english", Toast.LENGTH_SHORT).show();
        MainPageActivity.this.recreate();
    }

    public void setHindi(View view) {
        Locale locale = new Locale("hi");
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        MainPageActivity.this.getResources().updateConfiguration(configuration, MainPageActivity.this.getResources().getDisplayMetrics());
        Toast.makeText(this, "Language set to hindi", Toast.LENGTH_SHORT).show();
        MainPageActivity.this.recreate();
    }





    public class GetData extends AsyncTask<String, String, Float> {

        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Float doInBackground(String... strings) {
            try {
                connection = connectionMethod(USER_NAME,PASSWORD,DATABASE_NAME,IPADDRESS);
                if (connection != null) {
                    Statement statement = connection.createStatement();
                    Statement statementContact1 = connection.createStatement();
                    Statement statementContact2 = connection.createStatement();

                    String contactsQuery = "select * from hffc_contact_1;";
                    ResultSet resultSetContacts = statementContact1.executeQuery(contactsQuery);
                    while (resultSetContacts.next()) {
                        contactName1 = resultSetContacts.getString(2);
                        contactNumber1 = resultSetContacts.getString(3);
                    }
                    String contactsQuery2 = "select * from hffc_contact_2;";
                    resultSetContacts2 = statementContact2.executeQuery(contactsQuery2);
                    if (resultSetContacts2.next()) {
                        contactName2 = resultSetContacts2.getString(2);
                        contactNumber2 = resultSetContacts2.getString(3);
                    }
                    String query = "select * from hffc_todays_rate where id = 1;";

                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        isSuccess = true;
                        response = resultSet.getFloat("rate");
                    }
                    else {
                        response = 180;
                    }
                }
                else {
                    response = 180;
                }
            }
            catch (SQLException e) {
                Log.d("exceptio",e.toString());
            }
            return response;
        }

        @Override
        protected void onPostExecute(Float aFloat) {
            tvRate.setText(aFloat+"/KG");
            tvContactName1.setText("1) "+contactName1);
            tvContactName2.setText("2) "+contactName2);
            tvContactNumber1.setText("    "+contactNumber1);
            tvContactNumber2.setText("    "+contactNumber2);
            progressDialog.dismiss();
        }
    }

    public void goToOrder(View view) {
        Intent intent = new Intent(MainPageActivity.this,PlaceOrderActivity.class);
        intent.putExtra("rate",response);
        startActivity(intent);
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

    @Override
    protected void onResume() {
        super.onResume();
        GetData getData = new GetData();
        getData.execute();
    }

    public class AppUpdateChecker {
        private Activity activity;
        public AppUpdateChecker(Activity activity) {
            this.activity = activity;
        }
        //current version of app installed in the device
        private String getCurrentVersion(){
            PackageManager pm = activity.getPackageManager();
            PackageInfo pInfo = null;
            try {
                pInfo = pm.getPackageInfo(activity.getPackageName(),0);
            } catch (PackageManager.NameNotFoundException e1) {
                e1.printStackTrace();
            }
            return pInfo.versionName;
        }
        private class GetLatestVersion extends AsyncTask<String, String, String> {
            private String latestVersion;
            private ProgressDialog progressDialog;
            private boolean manualCheck;
            GetLatestVersion(boolean manualCheck) {
                this.manualCheck = manualCheck;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (manualCheck)
                {
                    if (progressDialog!=null)
                    {
                        if (progressDialog.isShowing())
                        {
                            progressDialog.dismiss();
                        }
                    }
                }
                String currentVersion = getCurrentVersion();
                //If the versions are not the same
                if(!currentVersion.equals(latestVersion)&&latestVersion!=null){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("An Update is Available");
                    builder.setMessage("Its better to update now");
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Click button action
                            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+activity.getPackageName())));
                            dialog.dismiss();
                        }
                    });
                    /*
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Cancel button action
                        }
                    });
                    builder.setCancelable(false);
                     */
                    builder.show();
                }else {
                    if (manualCheck) {
                        Toast.makeText(activity, "No Update Available", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (manualCheck) {
                    progressDialog=new ProgressDialog(activity);
                    progressDialog.setMessage("Checking For Update.....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                try {
                    //It retrieves the latest version by scraping the content of current version from play store at runtime
                    latestVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + activity.getPackageName() + "&hl=it")
                            .timeout(30000)
                            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                            .referrer("http://www.google.com")
                            .get()
                            .select(".hAyfc .htlgb")
                            .get(7)
                            .ownText();
                    return latestVersion;
                } catch (Exception e) {
                    return latestVersion;
                }
            }
        }
        public void checkForUpdate(boolean manualCheck)
        {
            new GetLatestVersion(manualCheck).execute();
        }
    }

}

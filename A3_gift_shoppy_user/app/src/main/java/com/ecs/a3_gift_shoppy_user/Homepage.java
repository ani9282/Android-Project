package com.ecs.a3_gift_shoppy_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Jsoup;

public class Homepage extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.a3giftshopy.file";
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        AppUpdateChecker appUpdateChecker=new AppUpdateChecker(this);  //pass the activity in constructure
        appUpdateChecker.checkForUpdate(false); //mannual check false here
        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mMainNav=(BottomNavigationView)findViewById(R.id.bootom_navigation_bar);
        mMainNav.setSelectedItemId(R.id.home);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home:
                        Toast.makeText(Homepage.this,"You Click Home",Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.search:
                        Toast.makeText(Homepage.this,"You Click Search",Toast.LENGTH_LONG).show();
                       // Intent i=new Intent(getApplicationContext(),Show_all_product.class);
                        //overridePendingTransition(0,0);
                       // finish();
                       // startActivity(i);
                        return true;

                    case R.id.profile:
                        //Toast.makeText(Sender_home_page.this,"You Click Profile",Toast.LENGTH_LONG).show();
                        //Intent intent=new Intent(getApplicationContext(),Sender_show_profile.class);
                        //overridePendingTransition(0,0);
                        // finish();
                        // startActivity(intent);
                        return true;

                    case R.id.share:
                       // Intent in=new Intent(getApplicationContext(),Add_Product.class);
                        //overridePendingTransition(0,0);
                       // finish();
                       // startActivity(in);
                        Toast.makeText(Homepage.this, "You Click Share", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.news:
                        //Toast.makeText(Homepage.this,"You Click News",Toast.LENGTH_LONG).show();
                       Intent intent2=new Intent(getApplicationContext(),User_registration.class);
                       overridePendingTransition(0,0);
                       finish();
                      startActivity(intent2);
                        return true;


                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator=getMenuInflater();
        inflator.inflate(R.menu.home_logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.level:
                Intent intent1 = new Intent(Homepage.this,Show_level.class);
                startActivity(intent1);
                finish();
                return true;


            case R.id.logout:
                editor.clear();
                editor.apply();
                Intent intent = new Intent(Homepage.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                    builder.setCancelable(false);
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

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        startActivity(getIntent());
    }


}
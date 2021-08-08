package com.ecs.mahaeseva;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;

import java.util.ArrayList;

public class Activity_Home extends AppCompatActivity implements RecyclerViewAdapter.ItemListener {

    private AppBarConfiguration mAppBarConfiguration;
    RecyclerView recyclerView;
    ArrayList<DataModel> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        arrayList.add(new DataModel("उत्पन्नाचा दाखला", R.drawable.img, "#09A9FF"));
        arrayList.add(new DataModel("डोमेसाइल दाखला ", R.drawable.img, "#3E51B1"));
        arrayList.add(new DataModel("जातीचा दाखला ", R.drawable.img, "#673BB7"));
        arrayList.add(new DataModel("नॉन क्रिमिनल दाखला",  R.drawable.img, "#4BAA50"));
        arrayList.add(new DataModel("EWS दाखला ",  R.drawable.img, "#F94336"));
        arrayList.add(new DataModel("शेतकरी दाखला ", R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("अल्पभूधारक  दाखला ",  R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("३३% महिला आरक्षण दाखला ", R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("आधार कार्ड ", R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel(" पॅन कार्ड ", R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("जात पडताळणी ", R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("गॅझेट/राजपत्र ",  R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("शॉप कायदा ",  R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("उद्योग आधार ",  R.drawable.img, "#0A9B88"));
        arrayList.add(new DataModel("पोलीस व्हेरीफिकेशन ",  R.drawable.img, "#0A9B88"));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 230);
        recyclerView.setLayoutManager(layoutManager);
        AppUpdateChecker appUpdateChecker=new AppUpdateChecker(this);  //pass the activity in constructure
        appUpdateChecker.checkForUpdate(false); //mannual check false here
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity__home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id == R.id.order)
        {
            Intent i=new Intent(Activity_Home.this,My_order.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(DataModel item) {

        Toast.makeText(getApplicationContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();
        // Intent i=new Intent(MainActivity.this,Main2Activity.class);
        // startActivity(i);


        if (item.text.equals("उत्पन्नाचा दाखला")) {
            Intent i=new Intent(Activity_Home.this,income_certificate.class);
            startActivity(i);
        } else if (item.text.equals("डोमेसाइल दाखला ")) {
            Intent i=new Intent(Activity_Home.this,domicile_certificate.class);
            startActivity(i);
        } else if (item.text.equals("जातीचा दाखला ")) {
            Intent i=new Intent(Activity_Home.this,Caste_Certificate.class);
            startActivity(i);
        }
        else if (item.text.equals("नॉन क्रिमिनल दाखला")) {
            Intent i=new Intent(Activity_Home.this,Non_Criminal_Certificate.class);
            startActivity(i);
        }
        else if (item.text.equals("EWS दाखला ")) {
            Intent i=new Intent(Activity_Home.this,Ews_Certificate.class);
            startActivity(i);
        }
        else if (item.text.equals("शेतकरी दाखला ")) {
            Intent i=new Intent(Activity_Home.this,Farmer_Certificate.class);
            startActivity(i);
        }
        else if (item.text.equals("अल्पभूधारक  दाखला ")) {
            Intent i=new Intent(Activity_Home.this,Minority_Certificate.class);
            startActivity(i);
        }
        else if (item.text.equals("३३% महिला आरक्षण दाखला ")) {
            Intent i=new Intent(Activity_Home.this,Female_Reservation_Certificate.class);
            startActivity(i);
        }
        else if (item.text.equals("आधार कार्ड ")) {
            Intent i=new Intent(Activity_Home.this,Adhar_Card.class);
            startActivity(i);
        }
        else if (item.text.equals(" पॅन कार्ड ")) {
            Intent i=new Intent(Activity_Home.this,Pan_Card.class);
            startActivity(i);
        }
        else if (item.text.equals("जात पडताळणी ")) {
            Intent i=new Intent(Activity_Home.this,Caste_Verification.class);
            startActivity(i);
        }
        else if (item.text.equals("गॅझेट/राजपत्र ")) {
            Intent i=new Intent(Activity_Home.this,Gadget.class);
            startActivity(i);
        }

        else if (item.text.equals("शॉप कायदा ")) {
            Intent i=new Intent(Activity_Home.this,Shop_Act.class);
            startActivity(i);
        }

        else if (item.text.equals("उद्योग आधार ")) {
            Intent i=new Intent(Activity_Home.this,Udyog_adhar.class);
            startActivity(i);
        }

        else if (item.text.equals("पोलीस व्हेरीफिकेशन ")) {
            Intent i=new Intent(Activity_Home.this,Police_Verification.class);
            startActivity(i);
        }




    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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

}
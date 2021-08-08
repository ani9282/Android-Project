package com.ecs.gymmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Homepage extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.indoornavigation.file";
    public static final String LOGIN_KEY = "com.ecs.indoornavigation.loginkey";
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
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
                        //Toast.makeText(Sender_home_page.this,"You Click Home",Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.search:
                        Toast.makeText(Homepage.this,"You Click Homepage",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),ShowBirthday.class);
                         overridePendingTransition(0,0);
                        finish();
                         startActivity(i);
                        return true;

                    case R.id.profile:
                        //Toast.makeText(Sender_home_page.this,"You Click Profile",Toast.LENGTH_LONG).show();
                        //Intent intent=new Intent(getApplicationContext(),Sender_show_profile.class);
                        //overridePendingTransition(0,0);
                        // finish();
                        // startActivity(intent);
                        return true;

                    case R.id.share:
                        Intent in=new Intent(getApplicationContext(),User_registration.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(in);
                        //Toast.makeText(Homepage.this, "You Click Share", Toast.LENGTH_SHORT).show();
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



            case R.id.updatesms:
                editor.clear();
                editor.apply();
                Intent intent1 = new Intent(Homepage.this,Update_birthday_sms.class);
                startActivity(intent1);
                return true;


            case R.id.updateimage:
                editor.clear();
                editor.apply();
                Intent i = new Intent(Homepage.this,Upload_image.class);
                startActivity(i);
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
}
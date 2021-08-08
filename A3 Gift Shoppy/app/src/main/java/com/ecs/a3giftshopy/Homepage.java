package com.ecs.a3giftshopy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Homepage extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
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
                        //Toast.makeText(Homepage.this,"You Click Homepage",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),Show_all_product.class);
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
                        Intent in=new Intent(getApplicationContext(),Add_Product.class);
                       overridePendingTransition(0,0);
                        finish();
                        startActivity(in);
                        //Toast.makeText(Homepage.this, "You Click Share", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.news:
                        Toast.makeText(Homepage.this,"You Click News",Toast.LENGTH_LONG).show();
//                        Intent intent2=new Intent(getApplicationContext(),Sender_intrested_order.class);
//                        overridePendingTransition(0,0);
//                        finish();
//                        startActivity(intent2);
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


}
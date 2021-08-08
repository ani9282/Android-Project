package com.ecs.royalrex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import id.zelory.compressor.Compressor;
import me.drakeet.materialdialog.MaterialDialog;

public class Driver_add_vehical extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    EditText name,number,rc_no,min_capacity,max_capacity,length,height,width;
    byte[] vehical;
    Button register;
    String mobile;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";

    //image
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "SelectImageActivity";
    private final int SELECT_PHOTO = 101;
    private final int CAPTURE_PHOTO = 102;
    final private int REQUEST_CODE_WRITE_STORAGE = 1;
    Uri uri;
    ImageView v_image;
    ProgressDialog progressDialog;
    private Connection connection;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    String vehical_status="Empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_add_vehical);

        progressDialog=new ProgressDialog(Driver_add_vehical.this);
        progressDialog.setMessage("Loading Please Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);
        name=(EditText)findViewById(R.id.name);
        number=(EditText)findViewById(R.id.number);
        rc_no=(EditText)findViewById(R.id.rc_number);
        min_capacity=(EditText)findViewById(R.id.min_capacity);
        max_capacity=(EditText)findViewById(R.id.max_capacity);
        length=(EditText)findViewById(R.id.length);
        height=(EditText)findViewById(R.id.height);
        width=(EditText)findViewById(R.id.width);
        register=(Button)findViewById(R.id.register);

        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mobile=sharedPreferences.getString("mobile","mobile");

        initViews();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty() || number.getText().toString().isEmpty() || min_capacity.getText().toString().isEmpty() || max_capacity.getText().toString().isEmpty() || length.getText().toString().isEmpty() || height.getText().toString().isEmpty() || width.getText().toString().isEmpty() )
                {
                    Toast.makeText(Driver_add_vehical.this,"All Field required",Toast.LENGTH_LONG).show();
                }

                else {
                        SaveData saveData=new SaveData();
                        saveData.execute();
                }
            }
        });

        mMainNav=(BottomNavigationView)findViewById(R.id.bootom_navigation_bar);
        mMainNav.setSelectedItemId(R.id.share);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home:
                        Intent intent=new Intent(getApplicationContext(),Driver_home_page.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(intent);
                        return true;

                    case R.id.search:
                        //Toast.makeText(Driver_home_page.this,"You Click Search",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),Driver_profile.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(i);
                        return true;

                    case R.id.profile:
                        //Toast.makeText(Driver_show_profile.this,"You Click Profile",Toast.LENGTH_LONG).show();
                        Intent intent1=new Intent(getApplicationContext(),Driver_show_profile.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(intent1);
                        return true;

                    case R.id.share:
                        Intent in=new Intent(getApplicationContext(),Driver_add_vehical.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(in);

                        //Toast.makeText(Driver_home_page.this,"You Click Share",Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.news:
                        // Toast.makeText(Driver_home_page.this,"You Click News",Toast.LENGTH_LONG).show();
                        Intent intent2=new Intent(getApplicationContext(),Driver_geting_order.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(intent2);
                        return true;

                }

                return false;
            }
        });

    }

    public void initViews(){
        v_image = (ImageView) findViewById(R.id.vehical_Image);
        //register=(Button)findViewById(R.id.btn);
       v_image = (ImageView)findViewById(R.id.vehical_Image);
       v_image.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                int hasWriteStoragePermission = 0;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hasWriteStoragePermission = checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

                if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE_WRITE_STORAGE);
                    }
                    //return;
                }

                listDialogue();
            }
        });

    }




    public void listDialogue(){
        final ArrayAdapter<String> arrayAdapter
                = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        arrayAdapter.add("Take Photo");
        arrayAdapter.add("Select Gallery");

        ListView listView = new ListView(this);
        listView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
        listView.setDividerHeight(0);
        listView.setAdapter(arrayAdapter);

        final MaterialDialog alert = new MaterialDialog(this).setContentView(listView);

        alert.setPositiveButton("Cancel", new View.OnClickListener() {
            @Override public void onClick(View v) {
                alert.dismiss();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){

                    alert.dismiss();
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //Uri uri  = Uri.parse("file:///sdcard/photo.jpg");
                    String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "propic.jpg";
                    uri = Uri.parse(root);
                    //i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(i, CAPTURE_PHOTO);

                }else {

                    alert.dismiss();
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PHOTO);

                }
            }
        });

        alert.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {

                    Uri imageUri = imageReturnedIntent.getData();
                    String selectedImagePath = getPath(imageUri);
                    File f = new File(selectedImagePath);
                    Bitmap bmp = Compressor.getDefault(this).compressToBitmap(f);


                    v_image.setImageBitmap(bmp);
                    Bitmap bitmapObtained =bmp;
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmapObtained.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    vehical = stream.toByteArray();

                }

                break;

            case CAPTURE_PHOTO:
                if (resultCode == RESULT_OK) {

                    Bitmap bmp = imageReturnedIntent.getExtras().getParcelable("data");

                    v_image.setImageBitmap(bmp);
                    Bitmap bitmapObtained =bmp;
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmapObtained.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    vehical = stream.toByteArray();



                }

                break;
        }
    }


    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null,
                null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

    class SaveData extends AsyncTask<String, String, String> {
        boolean isSuccess = false;
        String response,status="Empty";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "insert into Royalrex_add_Vehical (vehical_name,vehical_number,vehical_image,owner_mobile,min_capacity,max_capacity,length,width,height,status) values(N'"+ name.getText().toString() + "',N'" +number.getText().toString() + "','"+vehical+"',N'" + mobile + "','"+min_capacity.getText().toString()+"','" + max_capacity.getText().toString() + "','"+length.getText().toString()+"','"+width.getText().toString()+"','"+height.getText().toString()+"','"+status+"');";
                    Statement statement = connection.createStatement();
                    int resultSet = statement.executeUpdate(query);
                    if (resultSet > 0) {
                        isSuccess = true;
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
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(isSuccess==true)
            {
                AlertDialog.Builder dialog=new AlertDialog.Builder(Driver_add_vehical.this);
                dialog.setMessage("Vehical Information added Successfully Thank You......");
                dialog.setTitle("Vehical Registration Alert");
                dialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent i=new Intent(Driver_add_vehical.this,Driver_home_page.class);
                                startActivity(i);
                                finish();
                            }
                        });

                AlertDialog alertDialog=dialog.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
            progressDialog.dismiss();
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
        Intent intent = new Intent(Driver_add_vehical.this, Driver_home_page.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


    }
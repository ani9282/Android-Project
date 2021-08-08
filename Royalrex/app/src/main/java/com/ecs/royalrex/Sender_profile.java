package com.ecs.royalrex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.zelory.compressor.Compressor;
import me.drakeet.materialdialog.MaterialDialog;

public class Sender_profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnMapReadyCallback {

    private Connection connection;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    private static final String USER_NAME = "karad";
    private static final String PASSWORD = "Rahul@123";
    private static final String DATABASE_NAME = "Dbyesmamu";
    private static final String IPADDRESS = "182.50.133.109";
    private Button register,btnDate,btnTime,btnsource,btndestination;
    EditText ename,eweight,esource,edestination,esourcepin,edestinationpin;
    private String date;
    private String time;
    String vehical,mobile,first_name,last_name,role,status="Pending Order",driver_status="Not Intrested";
    private ProgressDialog progressDialog;
    String source,destination;
    String regex = "/^\\d{6}$/";
    byte[] photo;
    String pickup_type;
    ImageView imagereceive;
    //remember me Login
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "SelectImageActivity";
    private final int SELECT_PHOTO = 101;
    private final int CAPTURE_PHOTO = 102;
    final private int REQUEST_CODE_WRITE_STORAGE = 1;
    Uri uri;
    ImageView viewImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_profile);
        ename = (EditText) findViewById(R.id.name);
        eweight = (EditText) findViewById(R.id.weight);
        esource=(EditText)findViewById(R.id.source);
        edestination = (EditText) findViewById(R.id.destination);
        btnDate = findViewById(R.id.date);
        btnTime = findViewById(R.id.time);
        esourcepin=(EditText)findViewById(R.id.sourcepincode);
        edestinationpin=(EditText)findViewById(R.id.destinationpin);
        register = (Button) findViewById(R.id.register);
        progressDialog = new ProgressDialog(Sender_profile.this);
        progressDialog.setMessage("Loading Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        sharedPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mobile=sharedPreferences.getString("mobile","mobile");
        first_name=sharedPreferences.getString("first_name","first_name");
        last_name=sharedPreferences.getString("last_name","last_name");
        role=sharedPreferences.getString("role","role");
        // mobile=getIntent().getStringExtra("mobile");
        //for Vehical Dropdown Box
        Spinner spinner = findViewById(R.id.vehical);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //rememmber me
        sharedPreferences = getSharedPreferences(Login.sharedPrefFile,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime();
            }
        });


         initViews();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                String t = selectedRadioButton.getText().toString();
                if (t.equals("Individual")) {
                    pickup_type=t;
                } else if (t.equals("Sharing")) {
                    pickup_type=t;
                }


                if (ename.getText().toString().isEmpty() || eweight.getText().toString().isEmpty() || TextUtils.isEmpty(date) || TextUtils.isEmpty(time) || esource.getText().toString().isEmpty() || edestination.getText().toString().isEmpty() || vehical.isEmpty() || esourcepin.getText().toString().isEmpty() || edestinationpin.getText().toString().isEmpty() || esourcepin.getText().toString().length()<6 || edestinationpin.getText().toString().length()<6 || esourcepin.getText().toString().length()>=7 || edestinationpin.getText().toString().length()>=7) {



                    if(esourcepin.getText().toString().length()<6 || esourcepin.getText().toString().length()>=7)
                    {
                        esourcepin.setError("Invalid Pincode");
                    }

                    if(edestinationpin.getText().toString().length()<6 || edestinationpin.getText().toString().length()>=7)
                    {
                        edestinationpin.setError("Invalid Pincode");
                    }

                    else {
                        Toast.makeText(Sender_profile.this, "Fill All Fields", Toast.LENGTH_LONG).show();
                    }

                } else {

                    SaveData saveData = new SaveData();
                    saveData.execute();
                }
            }
        });

        btnsource=(Button)findViewById(R.id.btnsource);
        btndestination=(Button)findViewById(R.id.btndestination);

        btnsource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                source=esource.getText().toString();
                try {
                    Uri uri=Uri.parse("https://www.google.co.in/maps/place/"+source);
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    intent.setPackage("com.google.android.apps.maps");
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);

                }

                catch (ActivityNotFoundException e){
                    Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        btndestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                destination=edestination.getText().toString();
                try {
                    Uri uri=Uri.parse("https://www.google.co.in/maps/place/"+destination);
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    intent.setPackage("com.google.android.apps.maps");
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }

                catch (ActivityNotFoundException e){
                    Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
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
                        Intent in=new Intent(getBaseContext(),Sender_home_page.class);
                        startActivity(in);
                        finish();
                        return true;

                    case R.id.search:
                        //Toast.makeText(Sender_profile.this,"You Click Search",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),Search_vehical_by_sender.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(i);
                        return true;

                    case R.id.profile:
                        Toast.makeText(Sender_profile.this,"You Click Profile",Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.share:
                        //Intent in=new Intent(getBaseContext(),Sender_profile.class);
                        //startActivity(in);
                        return true;

                    case R.id.news:
                        //Toast.makeText(Sender_home_page.this,"You Click News",Toast.LENGTH_LONG).show();
                        Intent intent2=new Intent(getApplicationContext(),Sender_intrested_order.class);
                        overridePendingTransition(0,0);
                        finish();
                        startActivity(intent2);
                        return true;


                }
                return false;
            }
        });


    }



    private void getTime() {
        Calendar calendar = Calendar.getInstance();
        final int HOUR = calendar.get(Calendar.HOUR);
        final int MINUTES = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                try
                {
                    time = hourOfDay+":"+minute;
                    DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                    Date date = new SimpleDateFormat("HH:mm").parse(time);
                    time = dateFormat.format(date);
                    btnTime.setText(time);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        },HOUR,MINUTES,false);
        timePickerDialog.show();
    }


    private void getDate() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DAY = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = dayOfMonth+"-"+(month+1)+"-"+year;
                btnDate.setText(date);
            }
        },YEAR,MONTH,DAY);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        vehical = parent.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        vehical="";
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }


    class SaveData extends AsyncTask<String, String, String> {
        boolean isSuccess = false;
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = connectionMethod(USER_NAME, PASSWORD, DATABASE_NAME, IPADDRESS);
                if (connection != null) {
                    String query = "insert into Royalrex_SenderProfile (name,weight,photo,date,time,vehical,pickup_category,source,destination,first_name,last_name,mobile,role,status) values(N'"+ ename.getText().toString() + "',N'" +eweight.getText().toString() + "','"+photo+"',N'" + date + "','"+time+"','" + vehical + "','"+pickup_type+"','"+esource.getText().toString()+" "+esourcepin.getText().toString()+"','"+edestination.getText().toString()+" "+edestinationpin.getText().toString()+"','"+first_name+"','"+last_name+"','"+mobile+"','"+role+"','"+status+"');";
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
        }//end database method

        @Override
        protected void onPostExecute(String s) {

            if(isSuccess==true)
            {
                AlertDialog.Builder dialog=new AlertDialog.Builder(Sender_profile.this);
                dialog.setMessage("Sender Registration Successfully Thank You......");
                dialog.setTitle("Sender Registration Alert");
                dialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent i=new Intent(Sender_profile.this,Sender_home_page.class);
                                startActivity(i);
                                finish();
                            }
                        });

                AlertDialog alertDialog=dialog.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
            else {
                Toast.makeText(Sender_profile.this, "Sorry Error in Database....", Toast.LENGTH_LONG).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator=getMenuInflater();
        inflator.inflate(R.menu.sender_profile_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.order:

                Intent i = new Intent(Sender_profile.this,Track_sender_profile.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;
            case R.id.logout:
                editor.clear();
                editor.apply();
                Intent intent = new Intent(Sender_profile.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(Sender_profile.this, Sender_home_page.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


    public void initViews(){
        imagereceive = (ImageView) findViewById(R.id.Profile_Image);
        //register=(Button)findViewById(R.id.btn);
        viewImage = (ImageView)findViewById(R.id.Profile_Image);
        imagereceive.setOnClickListener(new View.OnClickListener() {
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

                    
                    viewImage.setImageBitmap(bmp);
                    Bitmap bitmapObtained =bmp;
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmapObtained.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    photo = stream.toByteArray();

                }
                break;

            case CAPTURE_PHOTO:
                if (resultCode == RESULT_OK) {

                    Bitmap bmp = imageReturnedIntent.getExtras().getParcelable("data");

                    viewImage.setImageBitmap(bmp);
                    Bitmap bitmapObtained =bmp;
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmapObtained.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    photo = stream.toByteArray();

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

}
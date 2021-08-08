package com.ecs.paperwala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Generate_invoice extends AppCompatActivity {

    Bitmap bmp, scaledbmp;
    int pageWidth = 1200,i;
    File mypath;
    String filepath;
    public static final String sharedPrefFile = "com.ecs.paperwala.file";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String sid,orderId,paper_name,saleId,name,address,mobile,string_pmp_qty,string_pmp_total,string_total;
    double paper_rate,paper_qty,paper_price,pmp_rate,pmp_qty,pmp_total,total;
    Canvas canvas;
    Paint myPaint;
    ArrayList<ModelRetailerBalance> friends=null;
    int horizontal=840,i1=0,stopy=860,toy=540;
    public static final int REQUEST_PERM_READ_STORAGE=103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_invoice);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 400, 200, false);
        sharedPreferences = getSharedPreferences(Login.sharedPrefFile, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sid = sharedPreferences.getString("id", "id");
        Intent intent = getIntent();
        orderId=intent.getStringExtra(Retailer_Balance_Adapter.ORDER_ID);
        saleId=intent.getStringExtra(Retailer_Balance_Adapter.SALE_ID);
        name=intent.getStringExtra("name");
       // SaveData saveData=new SaveData();
        //saveData.execute();
        fetchdata();


    }

    private void fetchdata()
    {
        String URL = "http://paperwala.live/api/webdistributor/BindSalePaperList?SaleOrder=" + saleId + "";
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>()
        {

            @Override
            public void onResponse(String s) {
                try {
                    friends = new ArrayList<>();
                    JSONArray contacts = new JSONArray(s);
                    int i1=0,horizonal=840;

                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        i1++;
                        horizonal+=50;


                        ModelRetailerBalance u=new ModelRetailerBalance();
                        String si=String.valueOf(i1);
                        paper_name=c.getString("PaperName");
                        u.setPaperName(paper_name);
                        //friends.add(paper_name);
                        //canvas.drawText(c.getString("PaperName"), 140, 890, myPaint);
                        // paper_name=c.getString("PaperName");



                          paper_rate=c.getDouble("PaperRate");
                          u.setPaperRate(paper_rate);
                         paper_qty=c.getDouble("PaperQuantity");
                            u.setPaperQuantity(paper_qty);
                        paper_price=c.getDouble("TotalPrice");
                        u.setTotalPrice(paper_price);
                        pmp_rate=c.getDouble("PamphletRate");
                        u.setPamphletRate(pmp_rate);
                        pmp_qty=c.getDouble("PamphletQuantity");
                        u.setPamphletQuantity(pmp_qty);
                        pmp_total=c.getDouble("TotalPamphletAmount");
                        u.setTotalPamphletAmount(pmp_total);
                         total=c.getDouble("TotalFinalAmount");
                        u.setTotalFinalAmount(total);
                        friends.add(u);

                       /*
                        String string_paper_rate=String.valueOf(paper_rate);

                        friends.add(string_paper_rate);
                        String string_paper_qty=String.valueOf(paper_qty);
                        friends.add(string_paper_qty);
                        String string_paper_price=String.valueOf(paper_price);
                        friends.add(string_paper_price);
                        String string_pmp_rate=String.valueOf(pmp_rate);
                        friends.add(string_pmp_rate);
                        String string_pmp_qty=String.valueOf(pmp_qty);
                        friends.add(string_pmp_qty);
                        String string_pmp_total=String.valueOf(pmp_total);
                        friends.add(string_pmp_total);
                        String string_total=String.valueOf(total);
                        friends.add(string_total);

                        */

                        /*
                        canvas.drawText(si, 40, horizonal, myPaint);

                        canvas.drawText(paper_name, 140, horizonal, myPaint);

                        canvas.drawText(string_paper_rate, 350, horizonal, myPaint);

                        canvas.drawText(string_paper_qty, 540, horizonal, myPaint);

                        canvas.drawText(string_paper_price, 730, horizonal, myPaint);

                        canvas.drawText(string_pmp_rate, 940, horizonal, myPaint);

                        canvas.drawText(string_pmp_qty, 1100, horizonal, myPaint);

                         */


                    }




                } catch (Exception e) {
                    Toast.makeText(Generate_invoice.this, "" + e, Toast.LENGTH_SHORT).show();
                }

                createPDf();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Generate_invoice.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                // Log.d("msg",volleyError.toString());
            }
        }) {

        };



        RequestQueue rQueue = Volley.newRequestQueue(Generate_invoice.this);
        rQueue.add(request);

    }

    private void createPDf() {
        PdfDocument mypdfDocument = new PdfDocument();
        myPaint = new Paint();
        Paint titlePaint = new Paint();
        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
        PdfDocument.Page myPage1 = mypdfDocument.startPage(myPageInfo1);
        canvas = myPage1.getCanvas();
        canvas.drawBitmap(scaledbmp, pageWidth-450, 590, myPaint);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTextSize(80f);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Invoice", pageWidth / 2, 270, titlePaint);
        //myPaint.setColor(Color.rgb(0,113,188));
        //myPaint.setTextSize(30f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(35f);
        myPaint.setColor(Color.BLACK);
        canvas.drawText("From", 20, 590, myPaint);
        canvas.drawText("Jay Paper Services", 20, 640, myPaint);
        canvas.drawText("Kothrud Pune", 20, 690, myPaint);
        canvas.drawText("9075015526", 20, 750, myPaint);

        //---------------------------------------------------------------------------
        myPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("To", pageWidth - 700, 590, myPaint);
        canvas.drawText(name, pageWidth - 700, 590+50, myPaint);
        //-----------------------------------------------------------------------------

        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(5);
        canvas.drawRect(20, 800, pageWidth - 20, 860, myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("Sr.No", 40, 840, myPaint);

        canvas.drawText("Paper Name", 140, 840, myPaint);

        canvas.drawText("Paper Rate", 350, 840, myPaint);

        canvas.drawText("Paper QTY", 540, 840, myPaint);

        canvas.drawText("Paper Price", 730, 840, myPaint);

        canvas.drawText("PMP Rate", 940, 840, myPaint);

        //canvas.drawText("QTY", 1100, 840, myPaint);
        /*
        canvas.drawText("PMP TOTAL", 1050, 830, myPaint);
        canvas.drawText("TOTAL", 1200, 830, myPaint);

         */




        canvas.drawLine(130, 800, 130, 860, myPaint);
        canvas.drawLine(340, 800, 340, 860, myPaint);

        canvas.drawLine(530, 800, 530, 860, myPaint);

        canvas.drawLine(720, 800, 720, 860, myPaint);

        canvas.drawLine(930, 800, 930, 860, myPaint);

        canvas.drawLine(1220, 800, 1220, 860, myPaint);

        //canvas.drawLine(1350, 800, 1350, 860, myPaint);

       // canvas.drawLine(1500, 800, 1500, 860, myPaint);





        for(int i=0;i<friends.size();i++)
        {
            i1++;
            horizontal+=60;
            stopy+=60;
            String stringsr=String.valueOf(i1);
            final ModelRetailerBalance orders = friends.get(i);
            String value=orders.getPaperName();
            paper_rate=orders.getPaperRate();
            String stringpaperrate=String.valueOf(paper_rate);
            paper_qty=orders.getPaperQuantity();
            String string_paper_qty=String.valueOf(paper_qty);
            paper_price=orders.getTotalPrice();
            String string_paper_price=String.valueOf(paper_price);
            pmp_rate=orders.getPamphletRate();
            String string_pmp_rate=String.valueOf(pmp_rate);
            pmp_qty=orders.getPamphletQuantity();
            String string_pmp_qty=String.valueOf(pmp_qty);
            pmp_qty+=orders.getPamphletQuantity();
            pmp_total+=orders.getTotalPamphletAmount();
            total+=orders.getTotalFinalAmount();
            //Toast.makeText(Generate_invoice.this,""+value,Toast.LENGTH_LONG).show();
            canvas.drawText(stringsr, 40, horizontal, myPaint);
            canvas.drawText(value,140,horizontal,myPaint);
            canvas.drawText(stringpaperrate, 350, horizontal, myPaint);
            canvas.drawText(string_paper_qty, 540,horizontal, myPaint);
            canvas.drawText(string_paper_price, 730,horizontal, myPaint);
            canvas.drawText(string_pmp_rate, 940, horizontal, myPaint);
            //canvas.drawText(string_pmp_qty, 1100, horizontal, myPaint);



        }
          //  final ModelRetailerBalance orders = friends.;







        myPaint.setTextSize(50f);
        canvas.drawText("PMP QTY", pageWidth-450, horizontal+550, myPaint);
        canvas.drawText(":"+String.valueOf(pmp_qty), pageWidth-200, horizontal+550, myPaint);
        canvas.drawText("PMP PRICE", pageWidth-500, horizontal+600, myPaint);
        canvas.drawText(":"+String.valueOf(pmp_total)+"/-", pageWidth-200, horizontal+600, myPaint);
        //myPaint.setColor(Color.rgb(247,147,30));
        //canvas.drawRect(680,1650,pageWidth-20,1450,myPaint);
        canvas.drawText("Total", pageWidth-380, horizontal+650, myPaint);
        canvas.drawText(":"+String.valueOf(total)+"/-", pageWidth-200, horizontal+650, myPaint);
       // canvas.drawBitmap(scaledbmp, pageWidth-450, 590, myPaint);




    //canvas.drawText(paper_name,140,920,myPaint);
        mypdfDocument.finishPage(myPage1);
       // File file = new File(Environment.getExternalStorageDirectory(), "/Invoice.pdf");
      //  mypdfDocument.close();

        try {
            mypath = new File(this.getExternalFilesDir(null).toString() + "/" + "invoice.pdf");
            filepath = mypath.toString();
            mypdfDocument.writeTo(new FileOutputStream(mypath));
            Log.d("ez_Receipt", "The external pdf file should be stored at: " + filepath);
        } catch (FileNotFoundException e) {
            Log.d("ez_Receipt", "File not found exception. Make sure WRITE_EXTERNAL_STORAGE permissions exist in your AndroidManifest.xml");
        } catch (IOException e) {
            Log.d("ez_Receipt", "IOException");
        }
        // close the document
        mypdfDocument.close();
        displayFromAsset();
        // return filepath;

    }



    private void setText(final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String str=value;


                    canvas.drawText(str, 140,890, myPaint);
            }
        });
    }




    private void displayFromAsset() {
        /*
        Uri path = Uri.fromFile(mypath);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {
            this.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this,
                    "No Application available to viewPDF"+e, Toast.LENGTH_SHORT).show();
        }

         */


            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            File f = new File(filepath);

            Uri uri = null;

            // Above Compile SDKversion: 25 -- Uri.fromFile Not working
            // So you have to use Provider
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = Uri.parse("content://com.ecs.paperwala.PdfContentProvider/"+"invoice.pdf");

                // Add in case of if We get Uri from fileProvider.
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }else{
                uri = Uri.fromFile(mypath);
            }

            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);


    }










}
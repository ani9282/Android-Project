package com.ecs.paperwala;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Add_transaction_detail extends AppCompatActivity  implements  AdapterView.OnItemSelectedListener{

    private EditText paper_qty, paper_price, paper_total, phamphlete_qty, phamphlete_price, phamphlete_total, final_total_price, edittext_set_final_amount, paid_amount, balance_amount, prev_bal_amount, final_balance_amount;
    private TextView transaction_date, sale_order;
    private Button add_paper, submit;
    private String date, id, city_id, distributor_id,strDate,finaltotalprice;
    int size, check = 0;
    String[] paper_name = new String[10];
    String[] items = new String[]{"Chai Latte", "Green Tea", "Black Tea"};
    String name, paper, vehical,value="0.00";
    private Spinner spinner;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    ArrayList<String> paperid = new ArrayList<String>();
    String[] array;
    private RecyclerView recyclerView;
    TextView card_paper_rate,card_pamphlete_rate,card_paper_qty,card_pamphelete_qty,card_paper_total,card_pamphelete_total,card_total_final_amount;
    List<ModelselectedPaper> modelList;
    private Selected_Paper_Adapter adapter;
    double amount,retailer_balance;
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    private SharedPreferences.Editor editor;
    private static EditText editText;
    private static EditText editText1;
    private static EditText editText2;
    private static EditText editText3;
    String retailer_balnce_in_string,retailer_id;
    String sid,permanant_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction_detail);
        city_id = getIntent().getStringExtra("city_id");
        distributor_id = getIntent().getStringExtra("distributor_id");
        date = getIntent().getStringExtra("date");
        retailer_balance=getIntent().getDoubleExtra("retailer_balance", 0.00);
        id = getIntent().getStringExtra("id");
        paper_qty = (EditText) findViewById(R.id.paper_qty);
        paper_price = (EditText) findViewById(R.id.paper_price);
        paper_total = (EditText) findViewById(R.id.paper_total);
        phamphlete_qty = (EditText) findViewById(R.id.pamphlete_qty);
        phamphlete_price = (EditText) findViewById(R.id.pamphlete_price);
        phamphlete_total = (EditText) findViewById(R.id.pamphlete_total);
        final_total_price = (EditText) findViewById(R.id.final_total_price);
        edittext_set_final_amount=(EditText) findViewById(R.id.edittext_final_amount);
        paid_amount = (EditText) findViewById(R.id.paid_amount);
        balance_amount = (EditText) findViewById(R.id.balance_amount);
        prev_bal_amount = (EditText) findViewById(R.id.pre_bal_amount);
        final_balance_amount = (EditText) findViewById(R.id.final_balance_amount);
        transaction_date = (TextView) findViewById(R.id.transaction_date);
        sale_order = (TextView) findViewById(R.id.sale_id);
        transaction_date.setText(date);
        sale_order.setText(id);
        add_paper = (Button) findViewById(R.id.add_transaction);
        submit = (Button) findViewById(R.id.submit);

        //sharedPreferences = getSharedPreferences(Login.sharedPrefFile,MODE_PRIVATE);
        //editor = sharedPreferences.edit();
        //sid=sharedPreferences.getString("r_id","r_id");







        paid_amount.setText("0.00");
        retailer_balnce_in_string=String.valueOf(retailer_balance);
        prev_bal_amount.setText(retailer_balnce_in_string);
        new Add_transaction_detail().setEditText(edittext_set_final_amount);
        new Add_transaction_detail().setEditText1(balance_amount);
        new Add_transaction_detail().setEditText2(prev_bal_amount);
        new Add_transaction_detail().setEditText3(final_balance_amount);





        /*
        card_paper_total=(TextView)findViewById(R.id.card_paper_rate_rs);
        card_pamphlete_rate=(TextView)findViewById(R.id.card_pamphlete_rate_rs);
        card_paper_qty=(TextView)findViewById(R.id.card_paper_qty_select);
        card_pamphelete_qty=(TextView)findViewById(R.id.card_pamphlete_qty_select);
        card_paper_total=(TextView)findViewById(R.id.card_paper_total_select);
        card_pamphelete_total=(TextView)findViewById(R.id.card_pamphlete_total_select);
        card_total_final_amount=(TextView)findViewById(R.id.card_pamphlete_total_select);

         */



        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(Add_transaction_detail.this));
        modelList=new ArrayList<>();

        add_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //  String v=final_total_price.getText().toString();
               // amount+=Double.parseDouble(v);
                //value=String.valueOf(amount);
                //final_amount.setText(value);
                ModelselectedPaper u=new ModelselectedPaper();
                u.setPaper_name(spinner.getSelectedItem().toString());
                u.setPaper_rate(paper_price.getText().toString());
                u.setPamphlete_rate(phamphlete_price.getText().toString());
                u.setPaper_qty(paper_qty.getText().toString());
                u.setPamphlete_qty(phamphlete_qty.getText().toString());
                u.setPaper_total(paper_total.getText().toString());
                u.setPamphlete_total(phamphlete_total.getText().toString());
                u.setTotal_final_price(final_total_price.getText().toString());

                modelList.add(u);

                adapter=new Selected_Paper_Adapter(Add_transaction_detail.this,modelList);
                recyclerView.setAdapter(adapter);

               // paper_price.setText("");
               // phamphlete_price.setText("");
                paper_qty.setText("");
                phamphlete_qty.setText("");
                paper_total.setText("");
                phamphlete_total.setText("");
                final_total_price.setText("");


            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String URL = "http://paperwala.live/api/webdistributor/CRMainOrder";
                StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                    }


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Add_transaction_detail.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                        ;
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("@DistributorSaleProductId", distributor_id);
                        params.put("@SaleOrder", id);
                        params.put("@PaperId", retailer_id);
                        params.put("@PaperRate", transaction_date.getText().toString());
                        params.put("@PaperQuantity", distributor_id);
                        params.put("@CreatedDate", date);
                        params.put("@CreatedBy", "Alif");
                        params.put("@ModifiedDate", "http://itsalif.info");
                        params.put("@ModifiedBy", "Alif");
                        params.put("@DeleteStatus", "http://itsalif.info");
                        return params;
                    }
                };

                RequestQueue rQueue = Volley.newRequestQueue(Add_transaction_detail.this);
                rQueue.add(request);
            }
        });



        String URL = "http://paperwala.live/api/webdistributor/BindPaperListByCityIdnDistributorId?CityId=" + city_id + "&DistributorId=" + distributor_id + "";
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {


                    JSONArray contacts = new JSONArray(s);
                    stringArrayList.add("-----Select Paper-----");
                    paperid.add("--------------------");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String data = c.getString("PaperName");
                        paperid.add(c.getString("PaperId"));
                        stringArrayList.add(data);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                spinner = findViewById(R.id.vehical);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Add_transaction_detail.this, android.R.layout.simple_spinner_item, stringArrayList);
                //  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setSelection(0, false);
                spinner.setOnItemSelectedListener(Add_transaction_detail.this);


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Add_transaction_detail.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                ;
            }
        }) {

        };

        RequestQueue rQueue = Volley.newRequestQueue(Add_transaction_detail.this);
        rQueue.add(request);



        /*
        spinner = findViewById(R.id.vehical);
       ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,stringArrayList);
       //  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

         */





    } //end of on create method

    private void mymethod(String string)
    {
        String val=string;
    }


    public void setEditText(final EditText editText)
    {
        Add_transaction_detail.editText = editText;
    }

    public void setEditText1(EditText editText)
    {
        Add_transaction_detail.editText1 =editText;

    }

    public void setEditText2(EditText editText)
    {
        Add_transaction_detail.editText2 =editText;
    }

    public void setEditText3(EditText editText)
    {
        Add_transaction_detail.editText3 =editText;
    }




    void set_prev_bal(String s)
    {
        editText2.setText(s);
    }

    void set_final_balance_amount(String s)
    {
        editText3.setText(s);
    }


    void set_final_amount(double a,double bal)
    {

        value=String.valueOf(a);
      //  final_amount=(EditText)findViewById(R.id.edittext_final_amount);
        editText.setText(value);
        editText1.setText(value);
        //--------------------------

        double d1=Double.parseDouble(value);
      // String java = bal;
      //  retailer_balance=Double.parseDouble(java);
        double d2= bal;
      double d3=d2+d1;
      String finals=String.format("%.2f",d3);
       editText2.setText(finals);
    }


















    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item

        String item = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        int id1 = (int) parent.getItemIdAtPosition(position);


        int me = Integer.parseInt(paperid.get(id1));
        //Toast.makeText(parent.getContext(), "Selected: " + me, Toast.LENGTH_LONG).show();


        //http://paperwala.live/api/webdistributor/GetPaperRate?PaperId=1&Tdate=

        String URL = "http://paperwala.live/api/webdistributor/GetPaperRate?PaperId="+me+"&Tdate="+date+"";
        StringRequest request = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                String []splitterString=s.split("\"");
                for (String str : splitterString) {
                    paper_price.setText(str);
                    phamphlete_price.setText("0.25");
                }

                /*
                String paperqty=paper_qty.getText().toString();
                double d=Double.parseDouble(paperqty);
                String paperprice=paper_price.getText().toString();
                double d1=Double.parseDouble(paperprice);
                Double d2=d*d1;
                String s=String.valueOf(d2);
                paper_total.setText(s);

                 */


                paper_qty.addTextChangedListener(new TextWatcher() {

                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                        if(s.equals("") ) {
                            paper_qty.setText("");

                        }

                        else {
                            String paperqty=paper_qty.getText().toString();
                            if(paperqty.equals("") || paper_price.getText().toString().equals(""))
                            {
                                paper_total.setText("");
                            }
                            else {
                                double d=Double.parseDouble(paperqty);
                                String paperprice=paper_price.getText().toString();
                                double d1=Double.parseDouble(paperprice);
                                Double d2=d*d1;
                               // String s1=String.valueOf(d2);
                                String s1=String.format("%.2f",d2);
                                paper_total.setText(s1);
                                finaltotalprice=s1;
                                final_total_price.setText(finaltotalprice);
                            }


                        }
                    }



                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {

                    }

                    public void afterTextChanged(Editable s) {

                    }
                });




                phamphlete_qty.addTextChangedListener(new TextWatcher() {

                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                        if(s.equals("") ) {
                            phamphlete_qty.setText("");
                        }

                        else {
                            String pampleteqty=phamphlete_qty.getText().toString();
                            if(pampleteqty.equals(""))
                            {
                                phamphlete_total.setText("");
                                final_total_price.setText(finaltotalprice);
                            }
                            else {
                                double d=Double.parseDouble(pampleteqty);
                                String pampleteprice=phamphlete_price.getText().toString();
                                double d1=Double.parseDouble(pampleteprice);
                                Double d2=d*d1;
                                String s2=String.format("%.2f",d2);
                                phamphlete_total.setText(s2);

                                    double d3=Double.parseDouble(finaltotalprice);
                                    double d4=d3+d2;
                                    String s3=String.format("%.2f",d4);
                                    final_total_price.setText(s3);

                            }

                        }
                    }



                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {

                    }

                    public void afterTextChanged(Editable s) {

                    }
                });



                /*
                String ptotal=phamphlete_total.getText().toString();
                double d1=Double.parseDouble(ptotal);
                String paptotal=paper_total.getText().toString();
                double d2=Double.parseDouble(paptotal);
                double d3=d1+d2;
                String s2=String.valueOf(d3);
                final_total_price.setText(s2);

                 */




            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Add_transaction_detail.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
            }
        }) {

        };

        RequestQueue rQueue = Volley.newRequestQueue(Add_transaction_detail.this);
        rQueue.add(request);



    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }





}


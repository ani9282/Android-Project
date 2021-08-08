package com.ecs.paperwala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Return_transaction_detail extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    private EditText paper_qty, paper_price, paper_total, phamphlete_qty, phamphlete_price, phamphlete_total, final_total_price, edittext_set_final_amount, paid_amount, balance_amount, prev_bal_amount, final_balance_amount;
    private String city_id,distributor_id,date,id,finaltotalprice;
    private double retailer_balance;
    private Button add_paper, submit;
    private TextView transaction_date, sale_order;
    private Spinner spinner;
    private Return_Paper_Adapter adapter;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    ArrayList<String> paperid = new ArrayList<String>();
    private RecyclerView recyclerView;
    List<ModelReturnPapper> modelList;
    private Date d1=null;
    private static EditText editText5;
    private static EditText editText6;
    private static EditText editText7;
    private static EditText editText8;
    private String retailer_balnce_in_string,value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_transaction_detail);
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
        edittext_set_final_amount=(EditText) findViewById(R.id.final_amount);
        paid_amount = (EditText) findViewById(R.id.paid_amount);
        balance_amount = (EditText) findViewById(R.id.balance_amount);
        prev_bal_amount = (EditText) findViewById(R.id.pre_bal_amount);
        final_balance_amount = (EditText) findViewById(R.id.final_balance_amount);
        transaction_date = (TextView) findViewById(R.id.transaction_date);
        sale_order = (TextView) findViewById(R.id.sale_id);
        add_paper = (Button) findViewById(R.id.add_transaction);
        submit = (Button) findViewById(R.id.submit);

        transaction_date.setText(date);
        sale_order.setText(id);
        paid_amount.setText("0.00");
        retailer_balnce_in_string=String.valueOf(retailer_balance);
        prev_bal_amount.setText(retailer_balnce_in_string);
        new Return_transaction_detail().setEditText5(edittext_set_final_amount);
        new Return_transaction_detail().setEditText6(balance_amount);
        new Return_transaction_detail().setEditText7(prev_bal_amount);
        new Return_transaction_detail().setEditText8(final_balance_amount);




        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(Return_transaction_detail.this));
        modelList=new ArrayList<>();

        add_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  String v=final_total_price.getText().toString();
                // amount+=Double.parseDouble(v);
                //value=String.valueOf(amount);
                //final_amount.setText(value);
                ModelReturnPapper u=new ModelReturnPapper();
                u.setPaper_name(spinner.getSelectedItem().toString());
                u.setPaper_rate(paper_price.getText().toString());
                u.setPamphlete_rate(phamphlete_price.getText().toString());
                u.setPaper_qty(paper_qty.getText().toString());
                u.setPamphlete_qty(phamphlete_qty.getText().toString());
                u.setPaper_total(paper_total.getText().toString());
                u.setPamphlete_total(phamphlete_total.getText().toString());
                u.setTotal_final_price(final_total_price.getText().toString());

                modelList.add(u);

                adapter=new Return_Paper_Adapter(Return_transaction_detail.this,modelList);
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Return_transaction_detail.this, android.R.layout.simple_spinner_item, stringArrayList);
                //  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setSelection(0, false);
                spinner.setOnItemSelectedListener(Return_transaction_detail.this);


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Return_transaction_detail.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                ;
            }
        }) {

        };

        RequestQueue rQueue = Volley.newRequestQueue(Return_transaction_detail.this);
        rQueue.add(request);


    } //end of on create method

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
                Toast.makeText(Return_transaction_detail.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
            }
        }) {

        };

        RequestQueue rQueue = Volley.newRequestQueue(Return_transaction_detail.this);
        rQueue.add(request);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }


    private void mymethod(String string)
    {
        String val=string;
    }


    public void setEditText5(final EditText editText)
    {
        Return_transaction_detail.editText5 = editText;
    }

    public void setEditText6(EditText editText)
    {
        Return_transaction_detail.editText6 =editText;

    }

    public void setEditText7(EditText editText)
    {
        Return_transaction_detail.editText7 =editText;
    }

    public void setEditText8(EditText editText)
    {
        Return_transaction_detail.editText8 =editText;
    }




    void set_prev_bal(String s)
    {
        editText7.setText(s);
    }

    void set_final_balance_amount(String s)
    {
        editText8.setText(s);
    }

    void set_final_amount(double a,double bal)
    {

        value=String.valueOf(a);
        //  final_amount=(EditText)findViewById(R.id.edittext_final_amount);
        editText5.setText(value);
        editText6.setText(value);
        //--------------------------

        double d1=Double.parseDouble(value);
        // String java = bal;
        //  retailer_balance=Double.parseDouble(java);
        double d2= bal;
        double d3=d2+d1;
        String finals=String.format("%.2f",d3);
        editText7.setText(finals);
    }




}
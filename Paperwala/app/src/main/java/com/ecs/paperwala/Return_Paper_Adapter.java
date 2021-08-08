package com.ecs.paperwala;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Return_Paper_Adapter extends RecyclerView.Adapter<Return_Paper_Adapter.ProductViewHolder>
{
    private Context context;
    private List<ModelReturnPapper> list;
    private double amount;
    private double amt;
    private EditText final_amount;
    private SharedPreferences sharedPreferences;
    public static final String sharedPrefFile = "com.ecs.royalrex.file";
    private SharedPreferences.Editor editor;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static final String spName = "My";
    private String sid;

    public Return_Paper_Adapter(Context context, List<ModelReturnPapper> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public Return_Paper_Adapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_selected_paper,parent,false);
       Return_Paper_Adapter.ProductViewHolder holder = new Return_Paper_Adapter.ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Return_Paper_Adapter.ProductViewHolder holder, int position) {

        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        sid = prefs.getString("id", "No name defined");//"No name defined" is the default value.


        final ModelReturnPapper orders = list.get(position);
        //holder.id.setText(orders.getRetailerId());
        holder.card_paper_name.setText(orders.getPaper_name());
        holder.card_paper_rate.setText(orders.getPaper_rate());
        holder.card_pamphlete_rate.setText(orders.getPamphlete_rate());
        holder.card_paper_qty.setText(orders.getPaper_qty());
        holder.card_pamphelete_qty.setText(orders.getPamphlete_qty());
        holder.card_paper_total.setText(orders.getPaper_total());
        holder.card_pamphelete_total.setText(orders.getPamphlete_total());
        holder.card_total_final_amount.setText("TOTAL FINAL AMOUNT: "+orders.getTotal_final_price());
        amount+=Double.parseDouble(orders.getTotal_final_price());
        // String value=String.valueOf(amount);

        String URL ="http://paperwala.live/api/webdistributor/GetRetailerPrevBal?RetailerId="+sid+"";
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {

                s=s.replaceAll("^\"|\"$", "");
                new Return_transaction_detail().set_prev_bal(s);
                double d1=Double.parseDouble(s);
                double d3=d1+amount;
                String v=String.format("%.2f",d3);
               new Return_transaction_detail().set_final_balance_amount(v);
                //amt=Double.parseDouble(s);

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();
            }
        }) {

        };

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);

        new Return_transaction_detail().set_final_amount(amount,amt);



        holder.deleteExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                amount-=Double.parseDouble(orders.getTotal_final_price());
                notifyItemRangeChanged(holder.getAdapterPosition(), list.size());
                String URL ="http://paperwala.live/api/webdistributor/GetRetailerPrevBal?RetailerId="+sid+"";
                StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {

                        s=s.replaceAll("^\"|\"$", "");
                        new Add_transaction_detail().set_prev_bal(s);
                        double d1=Double.parseDouble(s);
                        double d3=d1-amount;
                        String v=String.format("%.2f",d3);
                        new Return_transaction_detail().set_final_balance_amount(v);
                        //amt=Double.parseDouble(s);

                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();
                    }
                }) {

                };

                RequestQueue rQueue = Volley.newRequestQueue(context);
                rQueue.add(request);
                new Return_transaction_detail().set_final_amount(amount,amt);
                String value=String.format("%.2f",amount);
                //editText.setText(value);

            }
        });

    }




    @Override
    public int getItemCount() {
        return list.size();

    }




    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView card_paper_name,card_paper_rate,card_pamphlete_rate,card_paper_qty,card_pamphelete_qty,card_paper_total,card_pamphelete_total,card_total_final_amount;
        ImageView icon;
        private ImageView deleteExercise;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            card_paper_name=itemView.findViewById(R.id.selected_paper_name);
            card_paper_rate=itemView.findViewById(R.id.card_paper_rate_rs);
            card_pamphlete_rate=itemView.findViewById(R.id.card_pamphlete_rate_rs);
            card_paper_qty=itemView.findViewById(R.id.card_paper_qty_select);
            card_pamphelete_qty=itemView.findViewById(R.id.card_pamphlete_qty_select);
            card_paper_total=itemView.findViewById(R.id.card_paper_total_select);
            card_pamphelete_total=itemView.findViewById(R.id.card_pamphlete_total_select);
            card_total_final_amount=itemView.findViewById(R.id.total_final_amount);
            deleteExercise = itemView.findViewById(R.id.deleteBtn);

        }

    }


}

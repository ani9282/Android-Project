package com.ecs.paperwala;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Retailer_Balance_Adapter extends RecyclerView.Adapter<Retailer_Balance_Adapter.ProductViewHolder>
{
    private Context context;
    private List<ModelRetailerBalance> list;
    public static final String ORDER_ID = "com.ecs.paperwala.orderId";
    public static final String SALE_ID = "com.ecs.paperwala.saleId";
    public Retailer_Balance_Adapter(Context context, List<ModelRetailerBalance> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Retailer_Balance_Adapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_retailer_balance,parent,false);
        Retailer_Balance_Adapter.ProductViewHolder holder = new Retailer_Balance_Adapter.ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Retailer_Balance_Adapter.ProductViewHolder holder, int position) {
        final ModelRetailerBalance orders = list.get(position);
        holder.sale_id.setText(orders.getSaleOrder());
        holder.name.setText(orders.getRetailerName());
        double paid_amt=orders.getPaidAmount();
        String paid=String.valueOf(paid_amt);
        holder.paid_amount.setText(paid);
        double final_amount=orders.getFinalBalaceAmount();
        String s=String.valueOf(final_amount);
        holder.final_balance_amount.setText(s);


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent=new Intent(context,Generate_pdf_invoice.class);
                //context.startActivity(intent);
               // Generate_pdf receipt = new Generate_pdf(context);
               // String pdfFilePath=receipt.buildPdf();
                //receipt.buildPdf();
                //Intent intent = new Intent();
                //intent.setAction(Intent.ACTION_VIEW);
               // intent.setDataAndType(Uri.parse(pdfFilePath), "pdf/*");
                //context.startActivity(intent);

                //----------------------
                Intent intent=new Intent(context,Generate_invoice.class);
                intent.putExtra(ORDER_ID,orders.getDistributorSaleId());
                intent.putExtra(SALE_ID,orders.getSaleOrder());
                intent.putExtra("name",orders.getRetailerName());
                //intent.putExtra("address",orders.get);
                context.startActivity(intent);


            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"You Click Delete",Toast.LENGTH_LONG).show();
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name,sale_id,paid_amount,final_balance_amount,view,delete;
        //Button register;
        //public TextView buttonViewOption;
        ImageView icon;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            sale_id = itemView.findViewById(R.id.sale_id);
            name=itemView.findViewById(R.id.name);
            paid_amount=itemView.findViewById(R.id.paid_amount);
            final_balance_amount=itemView.findViewById(R.id.final_amount);
            view=itemView.findViewById(R.id.view_data);
            delete=itemView.findViewById(R.id.delete);
        }
    }


}

package com.ecs.royalrex;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ProductViewHolder>
{

    private Context context;
    private List<ModelClass> list;
    public static final String DELIVERED_ORDER_ID = "com.ecs.royalrex.deliveredOrderId";

    public TrackAdapter(Context context, List<ModelClass> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public TrackAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_sender_track_order,parent,false);
        TrackAdapter.ProductViewHolder holder = new TrackAdapter.ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        final ModelClass orders = list.get(position);
        holder.id.setText(": "+orders.getId());
        holder.mname.setText(": "+orders.getName());
        holder.source.setText(": "+orders.getSource());
        holder.destination.setText(": "+orders.getDestination());
        holder.status.setText(": "+orders.getStatus());

        holder.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(),Track_order_Detail.class);
                intent.putExtra(DELIVERED_ORDER_ID,orders.getId());
                context.startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView id,mname,source,destination,status;
        Button register;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            mname=itemView.findViewById(R.id.mname);
            source=itemView.findViewById(R.id.source);
            destination=itemView.findViewById(R.id.destination);
            status=itemView.findViewById(R.id.status);
            register=itemView.findViewById(R.id.register);

        }
    }

}

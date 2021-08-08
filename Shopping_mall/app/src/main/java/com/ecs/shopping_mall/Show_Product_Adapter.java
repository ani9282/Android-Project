//package com.ecs.shopping_mall;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
////public class Show_Product_Adapter extends RecyclerView.Adapter<Show_Product_Adapter.ProductViewHolder>
//{
//    private Context context;
//    private List<ModelClass> list;
//    public static final String DELIVERED_ORDER_ID = "com.ecs.royalrex.deliveredOrderId";
//    public Show_Product_Adapter(Context context, List<ModelClass> list) {
//        this.context = context;
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public Show_Product_Adapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.layout_product,parent,false);
//        Show_Product_Adapter.ProductViewHolder holder = new Show_Product_Adapter.ProductViewHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final Show_Product_Adapter.ProductViewHolder holder, int position) {
//        final ModelClass orders = list.get(position);
//        //holder.id.setText(orders.getRetailerId());
//       // holder.name.setText(orders.getRetailerName());
//
//    }
//
//
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//
//    }
//
//    public class ProductViewHolder extends RecyclerView.ViewHolder {
//        TextView id,name,address,mobile,amount,add_transaction,return_transaction;
//        //Button register;
//        //public TextView buttonViewOption;
//        ImageView icon;
//        public ProductViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            //id = itemView.findViewById(R.id.);
//            //name=itemView.findViewById(R.id.image1);
//            //address=itemView.findViewById(R.id.address);
//
//        }
//    }
//
//
//}
//
// */

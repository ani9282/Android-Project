package com.ecs.gymmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;





import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Show_birthday_details extends AppCompatActivity {

    private String date,message,mobile;
    private List<PostList> list;
    private RecyclerView recyclerView;
    Bitmap bmp;
   // private ProductAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_birthday_details);
        date=getIntent().getStringExtra("date");
        message=getIntent().getStringExtra("message");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Show_birthday_details.this));
        getData(date);
    }

    public void getData(String date)
    {
        Call<List<PostList>> modelClass=BloggerAPI.getServices().getModelList(date);
        modelClass.enqueue(new Callback<List<PostList>>() {
            @Override
            public void onResponse(Call<List<PostList>> call, Response<List<PostList>> response) {
                Log.d("This activity ==== ","response.raw().request().url();"+response.raw().request().url());
                Log.d("This activity ==== ","Success=========> "+response.body());
                //Toast.makeText(Show_All_Product.this, "Success"+response.body(), Toast.LENGTH_SHORT).show();
                // ModelClass u=new ModelClass();
                //list.add(u);
                List<PostList> list= response.body();
                for(int i=0;i<list.size();i++)
                {
                    mobile=list.get(i).getCustomerMobile();
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    OkHttpClient client = new OkHttpClient();
                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url("http://www.smssigma.com/API/WebSMS/Http/v1.0a/index.php?username=rahulpawar&password=Rahul@123&sender=ROYALS&to="+mobile+"&message="+message+"&reqid=1&format={json|text}&route_id=7&msgtype=unicode%22")  //send parameteres to php script
                            .build();
                    try {
                        okhttp3.Response response1 = client.newCall(request).execute();
                        String responseString = response1.body().string();
                        System.out.println(responseString);


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                //adapter = new ProductAdapter(Show_birthday_details.this,list);
                //recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<PostList>> call, Throwable t) {
                Log.d("This activity ==== ","response.raw().request().url();"+call.request().url());
                Toast.makeText(Show_birthday_details.this, "Failed"+t, Toast.LENGTH_LONG).show();
                Log.d("tag",t.toString());
            }
        });
    }

}
package com.ecs.shopping_mall;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class BloggerImageAPI
{
    private static final String url="http://testmandaiapp-env.eba-nrb4na2q.us-east-2.elasticbeanstalk.com/mandayiapplication/api/";
    private static final String key="searchText=&sortType=&reFine=&userId=&categoryId=&pincode=411006";

    public static BloggerAPI.ImageList postService=null;

    public static BloggerAPI.ImageList getServices()

    {

        if (postService == null) {

            Retrofit retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
            postService=retrofit.create(BloggerAPI.ImageList.class);
        }

        return postService;

    }


    public interface ImageList
    {
        @GET("downloadfile/{image}")
        Call<ItemsImagesList> getImageList(@Path("image") String image);
    }
}

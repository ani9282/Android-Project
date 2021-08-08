package com.ecs.shopping_mall;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class BloggerAPI
{
    private static final String url="http://testmandaiapp-env.eba-nrb4na2q.us-east-2.elasticbeanstalk.com/mandayiapplication/api/";
    private static final String key="searchText=&sortType=&reFine=&userId=&categoryId=&pincode=411006";

    public static PostService postService=null;

    public static PostService getServices()

    {

        if (postService == null) {

            Retrofit retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
            postService=retrofit.create(PostService.class);
        }

        return postService;

    }
    public interface PostService
    {
        @GET("items/search-sort-refine/list/pagination/{pageno}/{pagesize}")
        Call<List<ModelClass>> getModelList( @Path("pageno") int pageno,
                                             @Path("pagesize") int pagesize,
                                            @Query("searchText") String searchText,
                                            @Query("sortType") String sortType,
                                            @Query("reFine") String reFine,
                                            @Query("userId") String userId,
                                            @Query("categoryId") String categoryId,
                                            @Query("pincode") int pincode
                                      );
    }

    public interface ImageList
    {
        @GET("downloadfile/{image}")
        Call<ItemsImagesList> getImageList(@Path("image") String image);
    }
}

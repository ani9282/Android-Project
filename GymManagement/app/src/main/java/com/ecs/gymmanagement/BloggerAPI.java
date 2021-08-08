package com.ecs.gymmanagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class BloggerAPI
{
    private static final String url="https://www.ecssofttech.com/api/";
    private static final String key="gym";

    public static PostService postService=null;

    public static PostService getServices()

    {

        if (postService == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
            postService=retrofit.create(PostService.class);
        }

        return postService;

    }
    public interface PostService
    {
        @GET("Gym/showbirthdayapi.php")
        Call<List<PostList>> getModelList(@Query("flag") String date_of_birth
        );
    }


    public interface uploadimage
    {
        @Multipart
        @POST("Gym/saveimage.php")
        Call<ResponseBody> uploadImage(@Part MultipartBody.Part file,
                                       @Part("user_image") RequestBody name);
    }


}

package com.herokuapp.swype;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 *@author Abdel-Rahim Abdalla
 *@version 1.0
 *@since 1.0
 */

public class Swype {

    /**
     * Converts inputted data into HTTP requests and sends to server
     */
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://swype.herokuapp.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public SwypeService service = retrofit.create(SwypeService.class);


    public Swype() {
    }

    /**
     * Links HTTP requests with Java methods
     *
     *@author Abdel-Rahim Abdalla
     *@version 1.0
     *@since 1.0
     */
    public interface SwypeService {
        //sets HTTP headers being used
        @Headers({
                "Accept: application/json",
                "Content-Type: application/json"
        })

        //HTTP requests and equivalent Java methods

        @POST("register")
        Call<User> registerUser(@Body User user);

        @POST("login")
        Call<User> loginUser(@Body User id);

        @GET("script/hot")
        Call<Script[]> getHotScripts();

        @POST("script/save")
        Call<Script> saveScript(@Body Script script);

        @GET("script/search/{search}")
        Call<Script[]> searchScripts(@Path("search") String search);

        @GET("script/user/{user}")
        Call<Script[]> getUserScripts(@Path("user") String user);

        @PUT("print/add/{id}/{requester}")
        Call<String> printScript(@Path("id") int id, @Path("requester") String requester);

        @DELETE("print/remove/{id}")
        Call<Void> removeTask(@Path("id") int id);

        @GET("print")
        Call<Script[]> getQueue();
    }


}

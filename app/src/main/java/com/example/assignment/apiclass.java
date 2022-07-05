package com.example.assignment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface apiclass {

    @GET("users")
    Call<List<model>> getPosts();

}

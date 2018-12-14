package com.dimana.bobo.bobodimanaapp.Rest;

import com.dimana.bobo.bobodimanaapp.Model.KostSchema;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query;


public interface ApiInteface {

    @GET("c_kost")
    Call<KostSchema> getKost();
    @GET("c_kost")
    Call<KostSchema> getDetailKost(@Query("id") String id );
}

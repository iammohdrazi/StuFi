package com.zaph.loginsignupdesign.api;

import com.zaph.loginsignupdesign.models.Event;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("getEventsByUser")
    Call<List<Event>> getEventByUser(@FieldMap HashMap<String,String> map);

}

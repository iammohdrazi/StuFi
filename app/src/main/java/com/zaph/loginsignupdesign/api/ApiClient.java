package com.zaph.loginsignupdesign.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class
ApiClient {

    private final static String BASE_URL = "https://us-central1-jibble-ffd5f.cloudfunctions.net/";
    private static OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
    private static Retrofit retrofit = null;

    /**
     * Used to access the Retrofit Object
     *
     * @return a Retrofit Object
     */
    public static ApiInterface getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(loggingInterceptor);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }


}

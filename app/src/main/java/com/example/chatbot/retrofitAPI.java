package com.example.chatbot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface retrofitAPI {
    @GET
    Call<msgmodel> getmessage(@Url String url);
}

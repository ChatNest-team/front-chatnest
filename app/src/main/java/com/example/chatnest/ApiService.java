package com.example.chatnest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;
import java.util.Map;


public interface ApiService {
    @POST("login/client")
    Call<AuthResponse> loginClient(@Body Map<String, String> body);

    @GET("announcements")
    Call<List<Announcement>> getAnnouncements();
}


package com.example.chatnest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;


public interface ApiService {
    @POST("login/client")
    Call<AuthResponse> loginClient(@Body Map<String, String> body);

    @GET("announcements")
    Call<List<Announcement>> getAnnouncements();

    @POST("send-message/{id_client}/{id_agent}")
    Call<AuthResponse> messageSend(
            @Path("id_client") int idClient,
            @Path("id_agent") int idAgent,
            @Body MessageRequest messageRequest);



}


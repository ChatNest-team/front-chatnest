package com.example.chatnest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;


public interface ApiService {
    @POST("login/client")
    Call<AuthResponse> loginClient(@Body Map<String, String> body);


    @POST("agent-send")
    Call<AuthAgent> loginAgent(@Body Map<String, String> body);

    @GET("announcements")
    Call<List<Announcement>> getAnnouncements();

    @POST("send-message/{id_client}/{id_agent}")
    Call<Void> messageSend(
            @Path("id_client") int idClient,
            @Path("id_agent") int idAgent,
            @Body MessageRequest messageRequest);

    @GET("messages/{id_client}/{id_agent}")
    Call<List<Message>> getMessages(
            @Path("id_client") int idClient,
            @Path("id_agent") int idAgent
    );

    @GET("clients/{id_agent}")
    Call<List<Client>> getClients(
            @Path("id_agent") int idAgent
    );

    @POST("visites/create/{id_agent}/{id_client}/{id_propriete}")
    Call<Void> visitesSend(
            @Path("id_agent") int idAgent,
            @Path("id_client") int idClient,
            @Path("id_propriete") int idPropriete,
            @Body Map<String, String> body
    );




}


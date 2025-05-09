package com.example.chatnest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
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


    @POST("send-message/{envoyeur}/{recepteur}")
    Call<Void> messageSend(
            @Path("envoyeur") int idClient,
            @Path("recepteur") int idAgent,
            @Body MessageRequest messageRequest
    );


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

    @GET("visites/{id_agent}")
    Call<List<Visite>> getVisites(
            @Path("id_agent") int idAgent
    );

    @GET("user/{id}")
    Call<Personne> getPersonne(@Path("id") int id);

    @GET("message/{id}")
    Call<List<Conversation>> getConversation(@Path("id") int id);

    @DELETE("visite/destroy/{id_visite}")
    Call<Void> deleteVisiteById(@Path("id_visite") int idVisite);

    @GET("visites/{id}")
    Call<Visite> getVisiteById(@Path("id") int id);

    @Headers("Accept: application/json")
    @PATCH("visites/update/{id}")
    Call<Void> updateVisite(@Path("id") int id, @Body Map<String, Object> data);







}


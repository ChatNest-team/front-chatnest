package com.example.chatnest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import java.util.Map;


public interface ApiService {
    @POST("login/client")
    Call<AuthResponse> loginClient(@Body Map<String, String> body);
}


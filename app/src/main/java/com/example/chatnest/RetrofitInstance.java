package com.example.chatnest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://ton-api-url.com"; // Remplace par l'URL de base de ton API

    // Méthode pour obtenir une instance Retrofit
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // URL de base de ton API
                    .addConverterFactory(GsonConverterFactory.create()) // Utilisation de Gson pour la conversion
                    .build();
        }
        return retrofit;
    }

    // Méthode pour obtenir ton API (interface)
    public static ApiService getApi() {
        return getRetrofitInstance().create(ApiService.class); // Remplace ApiService par le nom de ton interface
    }
}


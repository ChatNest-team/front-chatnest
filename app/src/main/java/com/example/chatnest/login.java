package com.example.chatnest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {

    private EditText accessCodeInput;
    private TextView messageText;
    private Button validateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accessCodeInput = findViewById(R.id.accessCodeInput);
        messageText = findViewById(R.id.messageText);
        validateButton = findViewById(R.id.validateButton);

        validateButton.setOnClickListener(v -> {
            String code = accessCodeInput.getText().toString().trim();
            Log.d("Login", "Code saisi: " + code);  // Log le code saisi

            if (!code.isEmpty()) {
                messageText.setText("Validation en cours...");
                messageText.setTextColor(Color.BLUE);
                messageText.setVisibility(View.VISIBLE);

                // Configuration de Retrofit
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2/api/") // Ajout du port et s'assurer que l'URL se termine par un slash
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService apiService = retrofit.create(ApiService.class);

                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("code_client", code);
                Log.d("Login", "Requête envoyée: " + requestBody);  // Log des données envoyées

                // Appel de l'API
                Call<AuthResponse> call = apiService.loginClient(requestBody);
                call.enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        Log.d("Login", "Code réponse: " + response.code());  // Log du code de réponse HTTP
                        if (response.isSuccessful() && response.body() != null) {
                            AuthResponse auth = response.body();
                            Log.d("Login", "Réponse du serveur: " + auth.message);  // Log de la réponse du serveur

                            if (auth.client != null) {
                                messageText.setText(auth.message + "\nBienvenue ");
                                messageText.setTextColor(Color.GREEN);

                                // Créer un Intent pour aller vers l'activité Home
                                Intent intent = new Intent(login.this, Home.class);
                                startActivity(intent);
                                finish();
                            } else {
                                messageText.setText(auth.message);
                                messageText.setTextColor(Color.RED);
                            }

                        } else {
                            Log.e("Login", "Échec de l'authentification, réponse échouée");  // Log pour une réponse échouée
                            messageText.setText("Échec de l'authentification: " + response.code());
                            messageText.setTextColor(Color.RED);
                        }
                        messageText.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        Log.e("Login", "Erreur dans l'appel API: " + t.getMessage(), t);  // Log en cas d'erreur dans l'appel API
                        messageText.setText("Erreur lors de l'appel à l'API: " + t.getMessage());
                        messageText.setTextColor(Color.RED);
                        messageText.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                messageText.setText("Le code d'accès ne peut pas être vide");
                messageText.setTextColor(Color.RED);
                messageText.setVisibility(View.VISIBLE);
            }
        });
    }
}
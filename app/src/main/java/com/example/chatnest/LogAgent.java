package com.example.chatnest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogAgent extends AppCompatActivity {

    private TextView loginClient;
    private EditText accessLicenceInput;
    private TextView messageText;
    private Button validateButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_agent);

        // Gestion de la mise en page des fenêtres
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialisation des vues
        loginClient = findViewById(R.id.tologinclient);
        validateButton = findViewById(R.id.validateButton);
        accessLicenceInput = findViewById(R.id.accessLicenceInput);
        messageText = findViewById(R.id.messageText);
        progressBar = findViewById(R.id.progressBar); // ProgressBar ajouté pour le chargement

        // Écouteur de clic pour redirection vers la page de connexion client
        loginClient.setOnClickListener(v -> {
            Intent intent = new Intent(LogAgent.this, login.class);
            startActivity(intent);
        });

        // Écouteur de clic pour validation de la licence
        validateButton.setOnClickListener(v -> {
            String licence = accessLicenceInput.getText().toString().trim();

            if (!licence.isEmpty()) {
                messageText.setText("Validation en cours...");
                messageText.setTextColor(Color.BLUE);
                messageText.setVisibility(View.VISIBLE);

                // Afficher la ProgressBar pour signaler le chargement
                progressBar.setVisibility(View.VISIBLE);

                // Configuration de Retrofit
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2/api/") // Vérifie que cette adresse est correcte
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService apiService = retrofit.create(ApiService.class);

                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("licence", licence);
                //Log.d("Login", "Requête envoyée: " + requestBody);

                // Appel de l'API
                Call<AuthAgent> call = apiService.loginAgent(requestBody);
                call.enqueue(new Callback<AuthAgent>() {
                    @Override
                    public void onResponse(Call<AuthAgent> call, Response<AuthAgent> response) {
                        //Log.d("Login", "Code réponse: " + response.code());

                        // Masquer la ProgressBar après la réponse
                        progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful() && response.body() != null) {
                            AuthAgent auth = response.body();

                            if (auth.agent != null) {
                                messageText.setText(auth.message + "\nBienvenue ");
                                messageText.setTextColor(Color.GREEN);
                                SharedPreferences sharedPreferences = getSharedPreferences("MySession", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("id", String.valueOf(auth.agent.id));
                                editor.putString("role", String.valueOf(auth.agent.role));
                                editor.apply();
                                //Log.d("Login", "role : " + auth.agent.role);

                                Intent intent = new Intent(LogAgent.this, Home.class);
                                startActivity(intent);
                                finish();
                            } else {
                                messageText.setText(auth.message);
                                messageText.setTextColor(Color.RED);
                            }
                        } else {
                            //Log.e("Login", "Échec de l'authentification, réponse échouée");
                            messageText.setText("Échec de l'authentification: " + response.code());
                            messageText.setTextColor(Color.RED);
                        }
                        messageText.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<AuthAgent> call, Throwable t) {
                        //Log.e("Login", "Erreur dans l'appel API: " + t.getMessage(), t);

                        // Masquer la ProgressBar en cas d'échec
                        progressBar.setVisibility(View.GONE);

                        messageText.setText("Erreur lors de l'appel à l'API: " + t.getMessage());
                        messageText.setTextColor(Color.RED);
                        messageText.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                messageText.setText("Veuillez entrer une licence.");
                messageText.setTextColor(Color.RED);
                messageText.setVisibility(View.VISIBLE);
            }
        });
    }
}

package com.example.chatnest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profil extends AppCompatActivity {

    private int idPersonne;
    private TextView nom;
    private TextView date_inscription;
    private TextView email;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profil);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialiser les vues
        nom = findViewById(R.id.tvNom);
        date_inscription = findViewById(R.id.tvDateInscription);
        email = findViewById(R.id.tvEmail);
        logout = findViewById(R.id.btnDeconnecter);

        // Récupérer l'ID
        Intent intent = getIntent();
        idPersonne = intent.getIntExtra("idPersonne", -1);
        Log.d("Profil", "ID reçu dans Profil : " + idPersonne);
        if (idPersonne != -1) {
            fetchProfil(idPersonne);
        }

        logout.setOnClickListener(v -> {
            Intent logoutIntent = new Intent(Profil.this, login.class);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(logoutIntent);
        });
    }

    private void fetchProfil(int idPersonne) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<Personne> call = apiService.getPersonne(idPersonne);

        call.enqueue(new Callback<Personne>() {
            @Override
            public void onResponse(Call<Personne> call, Response<Personne> response) {
                if (!response.isSuccessful()) {
                    Log.e("API", "Code HTTP: " + response.code());
                    return;
                }

                Personne user = response.body();
                if (user != null) {
                    String nomComplet = user.getNom() + " " + user.getPrenom();
                    nom.setText(nomComplet);
                    email.setText(user.getEmail());
                    date_inscription.setText(user.getDate_inscription());
                }

                Log.d("API", "Réponse brute : " + new Gson().toJson(response.body()));

            }

            @Override
            public void onFailure(Call<Personne> call, Throwable t) {
                Log.e("API", "Erreur réseau : " + t.getMessage());
            }
        });
    }
}

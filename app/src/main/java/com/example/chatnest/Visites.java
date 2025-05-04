package com.example.chatnest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Visites extends BaseActivity {

    private LinearLayout linearLayoutVisites;
    private int idAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visites);

        setupBottomNavigation(R.id.nav_visite);

        getSupportActionBar().hide();

        linearLayoutVisites = findViewById(R.id.visiteLayout);

        // Récupérer l'ID agent passé par l'Intent
        Intent intent = getIntent();
        idAgent = intent.getIntExtra("idAgent", -1);

        if (idAgent != -1) {
            Log.e("Visites", "ID agent reçu : " + idAgent);
            fetchVisites(idAgent);
        } else {
            Log.e("Visites", "ID agent invalide");
        }
    }

    private void fetchVisites(int idAgent) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Visite>> call = apiService.getVisites(idAgent);

        call.enqueue(new Callback<List<Visite>>() {
            @Override
            public void onResponse(Call<List<Visite>> call, Response<List<Visite>> response) {
                if (!response.isSuccessful()) {
                    Log.e("API", "Code HTTP: " + response.code());
                    return;
                }
                LayoutInflater inflater = LayoutInflater.from(Visites.this);

                List<Visite> visites = response.body();
                if (visites != null && !visites.isEmpty()) {
                    linearLayoutVisites.removeAllViews();

                    for (Visite visite : visites) {
                        View visiteView = inflater.inflate(R.layout.list_item_visite, linearLayoutVisites, false);

                        TextView nomClient = visiteView.findViewById(R.id.nom_client);
                        TextView prenomClient = visiteView.findViewById(R.id.prenom_client);
                        TextView dateVisite = visiteView.findViewById(R.id.date_visite);
                        TextView heureVisite = visiteView.findViewById(R.id.heure_visite);
                        TextView adresseVisite = visiteView.findViewById(R.id.adresse_visite);

                        nomClient.setText(visite.getNom_client());
                        prenomClient.setText(visite.getPrenom_client());
                        dateVisite.setText("Date : " + visite.getDate_visite());
                        heureVisite.setText("Heure : " + visite.getHeure());
                        adresseVisite.setText("Adresse : " + visite.getAdresse());

                        int visiteid = visite.getID_Visite();

                        LinearLayout buttonsLayout = visiteView.findViewById(R.id.buttonsLayout);

                        if (visiteid > 0) {
                            Button btnSupprimer = new Button(Visites.this);
                            btnSupprimer.setText("Supprimer");
                            // Store the ID as an object, not as a resource ID
                            btnSupprimer.setTag(Integer.valueOf(visiteid));
                            btnSupprimer.setOnClickListener(v -> {
                                // Safely cast the tag to Integer
                                Integer visiteIdObj = (Integer) v.getTag();
                                if (visiteIdObj != null) {
                                    SupprimerVisite(visiteIdObj.intValue());
                                }
                            });

                            buttonsLayout.addView(btnSupprimer);
                        } else {
                            Log.e("Visites", "ID de visite invalide : " + visiteid);
                        }

                        linearLayoutVisites.addView(visiteView);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Visite>> call, Throwable t) {
                Log.e("API", "Erreur réseau/API : " + t.getMessage(), t);
            }
        });
    }

    private void SupprimerVisite(int idVisite) {
        Toast.makeText(this, "Suppression de la visite ID : " + String.valueOf(idVisite), Toast.LENGTH_SHORT).show();

        // Créer Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")  // Assure-toi que l'URL est correcte
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Requête DELETE pour supprimer la visite
        Call<Void> call = apiService.deleteVisiteById(idVisite);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Log.e("API", "Code HTTP: " + response.code());
                    return;
                }

                // Mise à jour de l'interface utilisateur : on pourrait supprimer la visite de la liste affichée
                Toast.makeText(Visites.this, "Visite supprimée avec succès", Toast.LENGTH_SHORT).show();

                fetchVisites(idAgent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API", "Erreur réseau/API : " + t.getMessage(), t);
            }
        });
    }
}
package com.example.chatnest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
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

        linearLayoutVisites = findViewById(R.id.linearLayoutVisites);

        // Récupérer l'ID agent passé par l'Intent
        Intent intent = getIntent();
        int idAgent = intent.getIntExtra("idAgent", -1);

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

                List<Visite> visites = response.body();
                if (visites != null && !visites.isEmpty()) {
                    Log.d("API", "Nombre de visites récupérées: " + visites.size());

                    // Vider le LinearLayout avant d'ajouter de nouvelles vues
                    linearLayoutVisites.removeAllViews();

                    // Ajouter chaque visite comme un LinearLayout dynamique
                    for (Visite visite : visites) {
                        LinearLayout visiteLayout = createVisiteLayout(visite);
                        linearLayoutVisites.addView(visiteLayout);
                    }
                } else {
                    Log.e("API", "La réponse ne contient pas de données.");
                }
            }

            @Override
            public void onFailure(Call<List<Visite>> call, Throwable t) {
                Log.e("API", "Erreur réseau/API : " + t.getMessage(), t);
            }
        });
    }

    // Méthode pour créer un LinearLayout dynamique avec les informations de la visite
    private LinearLayout createVisiteLayout(Visite visite) {
        // Créer le LinearLayout principal pour chaque visite
        LinearLayout visiteLayout = new LinearLayout(this);
        visiteLayout.setOrientation(LinearLayout.HORIZONTAL);
        visiteLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        visiteLayout.setPadding(16, 16, 16, 16);

        // Créer l'image de profil (ImageView)
        ImageView imageProfil = new ImageView(this);
        imageProfil.setLayoutParams(new LinearLayout.LayoutParams(64, 64));
        imageProfil.setImageResource(R.drawable.user); // Par défaut, une image de profil (à personnaliser)
        imageProfil.setBackgroundResource(R.drawable.circle_background);
        imageProfil.setClipToOutline(true);
        imageProfil.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // Créer le LinearLayout pour les infos de la visite
        LinearLayout infosVisite = new LinearLayout(this);
        infosVisite.setOrientation(LinearLayout.VERTICAL);
        infosVisite.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1)); // Prend tout l'espace restant

        // Nom du client
        TextView nomClient = new TextView(this);
        nomClient.setText(visite.getNom_client());
        nomClient.setTextSize(16);
        nomClient.setTextColor(getResources().getColor(android.R.color.black));
        nomClient.setTypeface(null, Typeface.BOLD);

        // Prénom du client
        TextView prenomClient = new TextView(this);
        prenomClient.setText(visite.getPrenom_client());
        prenomClient.setTextSize(14);
        prenomClient.setTextColor(getResources().getColor(android.R.color.darker_gray));

        // Date de la visite
        TextView dateVisite = new TextView(this);
        dateVisite.setText("Date: " + visite.getDate_visite());
        dateVisite.setTextSize(14);
        dateVisite.setTextColor(getResources().getColor(android.R.color.darker_gray));

        // Heure de la visite
        TextView heureVisite = new TextView(this);
        heureVisite.setText("Heure: " + visite.getHeure());
        heureVisite.setTextSize(14);
        heureVisite.setTextColor(getResources().getColor(android.R.color.darker_gray));

        // Adresse de la visite (si disponible)
        TextView adresseVisite = new TextView(this);
        adresseVisite.setText("Adresse: " + visite.getAdresse());
        adresseVisite.setTextSize(14);
        adresseVisite.setTextColor(getResources().getColor(android.R.color.darker_gray));
        adresseVisite.setVisibility(View.VISIBLE); // Cachée par défaut

        // Ajouter les TextViews dans le LinearLayout infosVisite
        infosVisite.addView(nomClient);
        infosVisite.addView(prenomClient);
        infosVisite.addView(dateVisite);
        infosVisite.addView(heureVisite);
        infosVisite.addView(adresseVisite);

        // Créer un LinearLayout pour les boutons
        LinearLayout buttonsLayout = new LinearLayout(this);
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        buttonsLayout.setPadding(0, 8, 0, 0);

        // Bouton Modifier
        Button btnModifier = new Button(this);
        btnModifier.setText("Modifier");
        btnModifier.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        buttonsLayout.addView(btnModifier);

        // Bouton Supprimer
        Button btnSupprimer = new Button(this);
        btnSupprimer.setText("Supprimer");
        btnSupprimer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        buttonsLayout.addView(btnSupprimer);

        // Ajouter le LinearLayout des boutons dans le layout principal
        infosVisite.addView(buttonsLayout);

        // Ajouter l'ImageView et le LinearLayout des infos dans le layout principal
        visiteLayout.addView(imageProfil);
        visiteLayout.addView(infosVisite);

        return visiteLayout;
    }


}

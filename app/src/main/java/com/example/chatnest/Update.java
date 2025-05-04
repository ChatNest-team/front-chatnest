package com.example.chatnest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class Update extends AppCompatActivity {

    private EditText editDate, editHeure, editAdresse;
    private Button btnUpdate;
    private static final String BASE_URL = "http://10.0.2.2:8000/api/";
    private ApiService apiService;
    private int idVisite;

    private TextView retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().hide();
        editDate = findViewById(R.id.UpdateDateVisite);
        editHeure = findViewById(R.id.UpdateHeureVisite);
        btnUpdate = findViewById(R.id.btnSubmitVisiteUpdate);


        retour = findViewById(R.id.idretourupdate);

        retour.setOnClickListener(v -> {
            Intent intent = new Intent(Update.this, Visites.class);
            startActivity(intent);
            finish();
        });

        idVisite = getIntent().getIntExtra("idVisite", -1);
        if (idVisite == -1) return;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        // 1. Récupère la visite
        apiService.getVisiteById(idVisite).enqueue(new Callback<Visite>() {
            @Override
            public void onResponse(Call<Visite> call, Response<Visite> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Visite visite = response.body();
                    editDate.setText(visite.getDate_visite());
                    editHeure.setText(visite.getHeure());

                }
            }

            @Override
            public void onFailure(Call<Visite> call, Throwable t) {
                // erreur réseau
            }
        });

        // 2. Envoie la mise à jour
        btnUpdate.setOnClickListener(view -> {
            Map<String, Object> data = new HashMap<>();
            data.put("date_Visite", editDate.getText().toString());
            data.put("heure_Visite", editHeure.getText().toString());


            apiService.updateVisite(idVisite, data).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(Update.this, "Mise à jour effectuée avec succès !", Toast.LENGTH_SHORT).show();
                        // Mettre à jour les champs avec les nouvelles données

                        finish();
                    } else {
                        Toast.makeText(Update.this, "Mise à jour impossible.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(Update.this, "Échec de connexion : veuillez vérifier votre réseau.", Toast.LENGTH_LONG).show();
                }

            });
        });
    }
}

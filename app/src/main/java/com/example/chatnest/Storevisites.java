package com.example.chatnest;

import static com.example.chatnest.Messagerie.EXTRA_ID_AGENT;

import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Storevisites extends AppCompatActivity {

    private static final String BASE_URL = "http://10.0.2.2:8000/api/";
    private int idAgent;
    private Button btnSubmitVisite;

    private int clientvisiteid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_storevisites);

        // Appel à la fonction pour récupérer l'ID de l'agent et effectuer l'appel API
        LinearLayout lClients = findViewById(R.id.lClients);
        ClientUtils.fetchClients(this, idAgent, lClients);
        Button btnSubmitVisite = findViewById(R.id.btnSubmitVisite);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        btnSubmitVisite.setOnClickListener(v -> {
            String idAgentStr = getIntent().getStringExtra(EXTRA_ID_AGENT);
            String idAnnouncementStr = getIntent().getStringExtra("idPropriete");
            String adresse = getIntent().getStringExtra("AnnounceAdress");

            // Récupérer les valeurs des champs du formulaire
            EditText dateVisiteField = findViewById(R.id.DateVisite);
            EditText heureVisiteField = findViewById(R.id.HeureVisite);

            String dateVisite = dateVisiteField.getText().toString().trim();
            String heureVisite = heureVisiteField.getText().toString().trim();


            if (idAgentStr != null) {
                try {
                    int idAgent = Integer.parseInt(idAgentStr);
                    int idClient = clientvisiteid;
                    int idPropriete = Integer.parseInt(idAnnouncementStr);


                    // Créer le Map avec les données
                    Map<String, String> body = new HashMap<>();
                    body.put("adresse", adresse != null ? adresse : "");
                    body.put("date_visite", dateVisite);
                    body.put("heure_visite", heureVisite);

                    // Appel API avec @Body
                    Call<Void> call = apiService.visitesSend(idAgent, idClient, idPropriete, body);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.d("Visite", "Visite créée avec succès !");
                                Toast.makeText(getApplicationContext(), "Visite enregistrée", Toast.LENGTH_SHORT).show();
                                finish(); // Revenir à l'écran précédent
                            } else {
                                Log.e("Visite", "Erreur : " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("Visite", "Échec appel API : " + t.getMessage());
                        }
                    });

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

    }






}

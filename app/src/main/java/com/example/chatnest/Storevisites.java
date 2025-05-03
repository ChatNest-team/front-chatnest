package com.example.chatnest;

import static com.example.chatnest.Messagerie.EXTRA_ID_AGENT;

import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class Storevisites extends BaseActivity {

    private static final String BASE_URL = "http://10.0.2.2:8000/api/";
    private int idAgent;
    private int clientvisiteid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_storevisites);

        // Initialisation du bouton
        Button btnSubmitVisite = findViewById(R.id.btnSubmitVisite);

        // Récupération des clients dès le chargement
        fetchClients();

        // Rétrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        btnSubmitVisite.setOnClickListener(v -> {
            String idAgentStr = getIntent().getStringExtra(EXTRA_ID_AGENT);
            String idAnnouncementStr = getIntent().getStringExtra("idPropriete");
            String adresse = getIntent().getStringExtra("AnnounceAdress");

            EditText dateVisiteField = findViewById(R.id.DateVisite);
            EditText heureVisiteField = findViewById(R.id.HeureVisite);

            String dateVisite = dateVisiteField.getText().toString().trim();
            String heureVisite = heureVisiteField.getText().toString().trim();

            if (idAgentStr != null && idAnnouncementStr != null) {
                try {
                    int idAgent = Integer.parseInt(idAgentStr);
                    int idPropriete = Integer.parseInt(idAnnouncementStr);
                    int idClient = clientvisiteid;

                    Map<String, String> body = new HashMap<>();
                    body.put("adresse", adresse != null ? adresse : "");
                    body.put("date_visite", dateVisite);
                    body.put("heure_visite", heureVisite);

                    Call<Void> call = apiService.visitesSend(idAgent, idClient, idPropriete, body);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.d("Visite", "Visite créée avec succès !");
                                Toast.makeText(getApplicationContext(), "Visite enregistrée", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e("Visite", "Erreur : " + response.code());
                                Toast.makeText(getApplicationContext(), "Erreur lors de l’enregistrement", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("Visite", "Échec appel API : " + t.getMessage());
                            Toast.makeText(getApplicationContext(), "Échec réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (NumberFormatException e) {
                    Log.e("Visite", "Erreur de format : " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "ID invalide", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Données manquantes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchClients() {
        String idAgentStr = getIntent().getStringExtra(EXTRA_ID_AGENT);
        Log.d("Storevisites", "ID Agent récupéré : " + idAgentStr);

        if (idAgentStr != null) {
            try {
                idAgent = Integer.parseInt(idAgentStr);
            } catch (NumberFormatException e) {
                Log.e("Storevisites", "Erreur conversion ID agent : " + e.getMessage());
                idAgent = -1;
            }
        }

        if (idAgent != -1) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);
            Call<List<Client>> call = apiService.getClients(idAgent);

            call.enqueue(new Callback<List<Client>>() {
                @Override
                public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Client> clients = response.body();
                        LinearLayout lClients = findViewById(R.id.lClients);
                        lClients.removeAllViews();

                        for (Client client : clients) {
                            LinearLayout clientLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.clientitem, null);

                            TextView clientName = clientLayout.findViewById(R.id.clientName);
                            clientName.setText(client.getNom() + " " + client.getPrenom());

                            ImageView clientImage = clientLayout.findViewById(R.id.clientImage);
                            String imageUrl = client.getImage();

                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                Glide.with(Storevisites.this)
                                        .load(imageUrl)
                                        .circleCrop()
                                        .into(clientImage);
                            } else {
                                clientImage.setImageResource(R.drawable.user);
                            }

                            clientImage.setOnClickListener(v -> {
                                clientvisiteid = client.getId();
                                Log.d("Storevisites", "Client sélectionné ID : " + clientvisiteid);
                            });

                            lClients.addView(clientLayout);
                        }
                    } else {
                        Log.e("Storevisites", "Erreur dans la réponse des clients");
                    }
                }

                @Override
                public void onFailure(Call<List<Client>> call, Throwable t) {
                    Log.e("Storevisites", "Erreur réseau clients : " + t.getMessage());
                }
            });
        } else {
            Log.e("Storevisites", "ID agent invalide, clients non récupérés.");
        }
    }
}

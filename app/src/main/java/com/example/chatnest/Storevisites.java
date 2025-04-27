package com.example.chatnest;

import static com.example.chatnest.Messagerie.EXTRA_ID_AGENT;

import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Storevisites extends AppCompatActivity {

    private static final String BASE_URL = "http://10.0.2.2/api/";
    private int idAgent;
    private Button btnSubmitVisite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_storevisites);

        // Appel à la fonction pour récupérer l'ID de l'agent et effectuer l'appel API
        fetchClients();
        Button btnSubmitVisite = findViewById(R.id.btnSubmitVisite);

        btnSubmitVisite.setOnClickListener(v->{

        });
    }

    private void fetchClients() {
        String idAgentStr = getIntent().getStringExtra(EXTRA_ID_AGENT);
        Log.d("Storevisites", "ID Agent récupéré : " + idAgentStr);

        if (idAgentStr != null) {
            try {
                idAgent = Integer.parseInt(idAgentStr);
            } catch (NumberFormatException e) {
                Log.e("Erreur de conversion de l'ID agent", e.getMessage());
                idAgent = -1;
            }
        } else {
            Log.e("ID agent non fourni", "L'ID de l'agent est manquant.");
            idAgent = -1;
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

                        // Référence au LinearLayout où les clients seront ajoutés
                        LinearLayout lClients = findViewById(R.id.lClients);
                        lClients.removeAllViews();  // Vider le LinearLayout avant d'ajouter les nouveaux clients

                        // Créer un layout pour chaque client
                        for (Client client : clients) {
                            // Créer un RelativeLayout pour chaque client
                            LinearLayout clientLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.clientitem, null);


                            // Référence au TextView pour le nom/prénom du client
                            TextView clientName = clientLayout.findViewById(R.id.clientName);
                            clientName.setText(client.getNom() + " " + client.getPrenom());  // Nom et prénom dynamiques

                            // Référence à l'ImageView pour l'image du client
                            ImageView clientImage = clientLayout.findViewById(R.id.clientImage);
                            String imageUrl = client.getImage(); // Exemple : URL de la photo
                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                // Utilisez Glide ou Picasso pour charger l'image depuis une URL
                                Glide.with(Storevisites.this)
                                        .load(imageUrl)
                                        .circleCrop()  // Pour rendre l'image ronde
                                        .into(clientImage);
                            } else {
                                // Image par défaut si aucune photo n'est fournie
                                clientImage.setImageResource(R.drawable.user); // Image par défaut
                            }

                            // Ajouter un OnClickListener pour l'image du client
                            clientImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Récupérer l'ID du client
                                    int clientId = client.getId();  // Assurez-vous que votre modèle Client a cette méthode

                                    // Afficher l'ID du client dans les logs
                                    Log.d("Storevisites", "ID du client cliqué : " + clientId);
                                }
                            });

                            // Ajouter le layout du client au LinearLayout principal
                            lClients.addView(clientLayout);
                        }
                    } else {
                        Log.e("Storevisites", "Erreur dans la réponse");
                    }
                }

                @Override
                public void onFailure(Call<List<Client>> call, Throwable t) {
                    Log.e("Storevisites", "Échec : " + t.getMessage());
                }
            });
        } else {
            Log.e("Storevisites", "ID de l'agent invalide, impossible de récupérer les clients.");
        }
    }




}

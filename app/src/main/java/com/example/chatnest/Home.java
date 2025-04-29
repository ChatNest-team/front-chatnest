package com.example.chatnest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnnonceAdapter annonceAdapter;
    private List<Announcement> annonces = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerAnnonces);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialiser la navigation
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                SharedPreferences sharedPreferences = getSharedPreferences("MySession", MODE_PRIVATE);
                if (item.getItemId() == R.id.nav_home) {
                    Toast.makeText(Home.this, "Accueil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Home.this, Home.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.nav_profil) {
                    // Récupération de l'ID de l'agent
                    String idPersonne = sharedPreferences.getString("id", null);
                    if (idPersonne != null) {
                        try {
                            int idPers = Integer.parseInt(idPersonne);
                            //Log.e("Visites", "ID personne : " + idPers);

                            // Passer l'ID agent à l'activité Visites
                            Intent intent = new Intent(Home.this, Profil.class);
                            intent.putExtra("idPersonne", idPers);
                            startActivity(intent);
                        } catch (NumberFormatException e) {
                            Log.e("Visites", "ID agent invalide");
                            Toast.makeText(Home.this, "Erreur de format d'ID agent", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Home.this, "Aucun ID d'agent trouvé", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                } else if (item.getItemId() == R.id.nav_messages) {
                    Toast.makeText(Home.this, "Messages", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Home.this, Messagelist.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.nav_visite) {
                    // Récupération de l'ID de l'agent
                    String idAgentStr = sharedPreferences.getString("id", null);
                    if (idAgentStr != null) {
                        try {
                            int idAgent = Integer.parseInt(idAgentStr);
                            Log.e("Visites", "ID agent : " + idAgent);

                            // Passer l'ID agent à l'activité Visites
                            Intent intent = new Intent(Home.this, Visites.class);
                            intent.putExtra("idAgent", idAgent);
                            startActivity(intent);
                        } catch (NumberFormatException e) {
                            Log.e("Visites", "ID agent invalide");
                            Toast.makeText(Home.this, "Erreur de format d'ID agent", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Home.this, "Aucun ID d'agent trouvé", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        // Appel de la requête API pour récupérer les annonces
        makeApiRequest();
    }

    private void makeApiRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Announcement>> call = apiService.getAnnouncements();

        call.enqueue(new Callback<List<Announcement>>() {
            @Override
            public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    annonces = response.body();
                    annonceAdapter = new AnnonceAdapter(annonces);
                    recyclerView.setAdapter(annonceAdapter);
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    Log.d("API_ANNONCES", gson.toJson(annonces));
                    Toast.makeText(Home.this, "Nombre d'annonces : " + annonces.size(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Home.this, "Erreur lors de la récupération des annonces", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Announcement>> call, Throwable t) {
                Toast.makeText(Home.this, "Échec de la requête : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

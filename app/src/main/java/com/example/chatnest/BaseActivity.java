package com.example.chatnest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity {

    protected void setupBottomNavigation(int selectedItemId) {
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);

        navigationView.setSelectedItemId(selectedItemId);

        navigationView.setOnNavigationItemSelectedListener(item -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MySession", MODE_PRIVATE);

            if (item.getItemId() == R.id.nav_home && selectedItemId != R.id.nav_home) {
                startActivity(new Intent(this, Home.class));
                return true;
            } else if (item.getItemId() == R.id.nav_profil && selectedItemId != R.id.nav_profil) {
                String idPersonne = sharedPreferences.getString("id", null);
                if (idPersonne != null) {
                    try {
                        int idPers = Integer.parseInt(idPersonne);
                        Intent intent = new Intent(this, Profil.class);
                        intent.putExtra("idPersonne", idPers);
                        startActivity(intent);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "ID invalide", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            } else if (item.getItemId() == R.id.nav_messages && selectedItemId != R.id.nav_messages) {
                String id = sharedPreferences.getString("id", null);
                String role = sharedPreferences.getString("role", null);
                if (id != null) {
                    try {
                        int idPers = Integer.parseInt(id);
                        Intent intent = new Intent(this, Messagelist.class);
                        intent.putExtra("idPersonne", idPers);
                        intent.putExtra("role", role);
                        startActivity(intent);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "ID invalide", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            } else if (item.getItemId() == R.id.nav_visite && selectedItemId != R.id.nav_visite) {
                String id = sharedPreferences.getString("id", null);
                if (id != null) {
                    try {
                        int idAgent = Integer.parseInt(id);
                        Intent intent = new Intent(this, Visites.class);
                        intent.putExtra("idAgent", idAgent);
                        startActivity(intent);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "ID invalide", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
            return false;
        });
    }
}


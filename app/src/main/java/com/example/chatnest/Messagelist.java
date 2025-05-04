package com.example.chatnest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Messagelist extends BaseActivity {

    private LinearLayout linearLayoutConversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("MySession", MODE_PRIVATE);
        linearLayoutConversation = findViewById(R.id.linearLayoutConversation);
        getSupportActionBar().hide();

        setupBottomNavigation(R.id.nav_messages);

        Intent intent = getIntent();
        int idPersonne = intent.getIntExtra("idPersonne", -1);
        String roleUser = intent.getStringExtra("role");

        if (idPersonne != -1) {
            Log.e("List message", "role reçu : " + roleUser);
            fetchConversation(idPersonne);
        } else {
            Log.e("List message", "ID invalide");
        }
    }

    private void fetchConversation(int idPersonne) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Conversation>> call = apiService.getConversation(idPersonne);

        call.enqueue(new Callback<List<Conversation>>() {
            @Override
            public void onResponse(Call<List<Conversation>> call, Response<List<Conversation>> response) {
                if (!response.isSuccessful()) {
                    Log.e("API", "Code HTTP: " + response.code());
                    return;
                }

                List<Conversation> conversations = response.body();
                if (conversations != null && !conversations.isEmpty()) {
                    Log.d("API", "Nombre de conversations récupérées: " + conversations.size());

                    linearLayoutConversation.removeAllViews();

                    for (Conversation c : conversations) {
                        final Conversation conversation = c;

                        LinearLayout conversationLayout = createConversationItem(
                                Messagelist.this,
                                conversation.getInterlocuteur_nom(),
                                conversation.getMessage_texte(),
                                conversation.getEnvoye_le()
                        );

                        conversationLayout.setOnClickListener(v -> {
                            SharedPreferences sharedPreferences = getSharedPreferences("MySession", MODE_PRIVATE);
                            String role = sharedPreferences.getString("role", null);
                            String userId = sharedPreferences.getString("id", null);
                            //Log.d("Session", "Rôle : " + role);
                            //Log.d("Session", "ID Utilisateur : " + userId);
                            if (role == null || userId == null) {
                                Log.e("CLICK", "Données de session manquantes");
                                return;
                            }

                            int idAgent, idClient;

                            if (role.equals("agent")) {
                                idAgent = Integer.parseInt(userId); // la personne connectée
                                idClient = conversation.getInterlocuteur_id(); // l'autre
                            } else {
                                idClient = Integer.parseInt(userId);
                                idAgent = conversation.getInterlocuteur_id();
                            }

                            Log.d("CLICK", "Agent ID : " + idAgent);
                            Log.d("CLICK", "Client ID : " + idClient);

                            Intent intent = new Intent(Messagelist.this, Messagerie.class);
                            intent.putExtra("idAgent", String.valueOf(idAgent));
                            intent.putExtra("idClient", String.valueOf(idClient));
                            intent.putExtra("role", role);

                            startActivity(intent);
                        });


                        linearLayoutConversation.addView(conversationLayout);
                    }
                } else {
                    Log.e("API", "La réponse ne contient pas de données.");
                }
            }

            @Override
            public void onFailure(Call<List<Conversation>> call, Throwable t) {
                Log.e("API", "Erreur réseau/API : " + t.getMessage(), t);
            }
        });
    }

    public LinearLayout createConversationItem(Context context, String userName, String lastMessage, String lastTime) {
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setPadding(12, 12, 12, 12);
        container.setGravity(Gravity.CENTER_VERTICAL);
        container.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        ImageView avatar = new ImageView(context);
        avatar.setImageResource(R.drawable.user);
        LinearLayout.LayoutParams avatarParams = new LinearLayout.LayoutParams(120, 120);
        avatarParams.setMarginEnd(12);
        avatar.setLayoutParams(avatarParams);
        avatar.setScaleType(ImageView.ScaleType.CENTER_CROP);

        LinearLayout textContainer = new LinearLayout(context);
        textContainer.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        textContainer.setLayoutParams(textParams);

        TextView nameText = new TextView(context);
        nameText.setText(userName);
        nameText.setTextSize(16);
        nameText.setTypeface(null, Typeface.BOLD);
        nameText.setTextColor(Color.parseColor("#000000"));

        TextView messageText = new TextView(context);
        messageText.setText(lastMessage);
        messageText.setTextSize(14);
        messageText.setTextColor(Color.DKGRAY);
        messageText.setEllipsize(TextUtils.TruncateAt.END);
        messageText.setMaxLines(1);

        TextView timeText = new TextView(context);
        timeText.setText(lastTime);
        timeText.setTextSize(12);
        timeText.setTextColor(Color.DKGRAY);
        timeText.setGravity(Gravity.CENTER_VERTICAL);

        textContainer.addView(nameText);
        textContainer.addView(messageText);
        textContainer.addView(timeText);

        container.addView(avatar);
        container.addView(textContainer);

        return container;
    }
}

package com.example.chatnest;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class Messagerie extends AppCompatActivity {

    private static final String TAG = "MessagerieActivity";

    public static final String EXTRA_ID_AGENT = "idAgent";

    private EditText messageInput;
    private Button send;
    private int idAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagerie);

        messageInput = findViewById(R.id.etMessage);

        String idAgentStr = getIntent().getStringExtra(EXTRA_ID_AGENT);
        String role = getIntent().getStringExtra("role");

        String idClientStr = getIntent().getStringExtra("idClient");
        int idClient = -1;

        if (idClientStr != null) {
            try {
                idClient = Integer.parseInt(idClientStr);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Erreur de conversion de l'ID client", e);
            }
        } else {
            Log.w(TAG, "ID client non fourni");
        }

        // Convertir l'ID agent
        if (idAgentStr != null) {
            try {
                idAgent = Integer.parseInt(idAgentStr);

            } catch (NumberFormatException e) {
               // Log.e(TAG, "Erreur de conversion de l'ID agent", e);
                idAgent = -1;
            }
        } else {
            //Log.w(TAG, "ID agent non fourni");
            idAgent = -1;
        }

        if (idClient != -1 ) {
            fetchMessages(idClient, idAgent,role);
        }

        if (idClient != -1 ) {
            fetchMessages(idClient, idAgent,role);
        }

        // Bouton d'envoi du message

        final ImageView send = findViewById(R.id.ivEnvoyer);

        int finalIdClient = idClient;
        send.setOnClickListener(v -> {
            String messagetoSend = messageInput.getText().toString();

            if (messagetoSend.isEmpty()) {
                Log.e(TAG, "Message vide");
                Toast.makeText(Messagerie.this, "Le message ne peut pas être vide", Toast.LENGTH_SHORT).show();
                return;
            }

            // Afficher le message directement
            LinearLayout messageList = findViewById(R.id.messageList);
            LayoutInflater inflater = LayoutInflater.from(Messagerie.this);
            ScrollView scrollView = findViewById(R.id.scrollMessages);

            View messageEnvoyeView = inflater.inflate(R.layout.item_message_send, messageList, false);
            TextView tvMessage = messageEnvoyeView.findViewById(R.id.messagetextsend);
            tvMessage.setText(messagetoSend);

            messageList.addView(messageEnvoyeView);
            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));

            // Effacer le champ input
            messageInput.setText("");


            // Envoi du message au serveur
            MessageRequest messageRequest = new MessageRequest();
            messageRequest.setTexte_Message(messagetoSend);
            messageRequest.setEnvoyeur("client");
            messageRequest.setId_plateforme(1);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);

            apiService.messageSend(finalIdClient, idAgent, messageRequest).enqueue(new retrofit2.Callback<Void>() {
                @Override
                public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                    // Cacher l'indicateur de progression
                   // progressBar.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        Log.d(TAG, "Message envoyé avec succès !");
                    } else {
                        // Afficher une erreur avec un bouton "Réessayer"
                        showRetryMessage("Erreur lors de l'envoi : " + response.code());
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                    // Cacher l'indicateur de progression
                    //progressBar.setVisibility(View.GONE);

                    // Afficher une erreur avec un bouton "Réessayer"
                    showRetryMessage("Échec de l'envoi : " + t.getMessage());
                }
            });
        });


        // Récupérer les messages existants
        if (idClient != -1 && idAgent != -1) {
            fetchMessages(idClient, idAgent,role);
        }
    }

    // Récupérer les messages de l'API

    //mise a jours

    private final Handler handler = new Handler();
    private final int REFRESH_INTERVAL = 3000; //
    private void fetchMessages(int idClient, int idAgent, String role) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getMessages(idClient, idAgent).enqueue(new retrofit2.Callback<List<Message>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Message>> call, retrofit2.Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    List<Message> messages = response.body();
                    if (messages != null) {
                        LinearLayout messageList = findViewById(R.id.messageList);
                        LayoutInflater inflater = LayoutInflater.from(Messagerie.this);

                        for (Message message : messages) {
                            View messageView;
                            TextView tvMessage;

                            if (role != null && role.equalsIgnoreCase(message.getEnvoyeur())) {
                                messageView = inflater.inflate(R.layout.item_message_send, messageList, false);
                                tvMessage = messageView.findViewById(R.id.messagetextsend);
                            } else {
                                // Sinon → message à gauche
                                messageView = inflater.inflate(R.layout.item_message_recived, messageList, false);
                                tvMessage = messageView.findViewById(R.id.messagetextreceived);
                            }
                            tvMessage.setText(message.getTexte());
                            messageList.addView(messageView);
                        }
                    }
                } else {
                    Log.e("ERROR", "Erreur lors de la récupération des messages : " + response.code());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Message>> call, Throwable t) {
                Log.e("ERROR", "Échec de la récupération des messages : " + t.getMessage());
            }
        });
    }




    // Méthode pour afficher un message d'erreur avec un bouton "Réessayer"
    private void showRetryMessage(String errorMessage) {
        LinearLayout messageList = findViewById(R.id.messageList);
        LayoutInflater inflater = LayoutInflater.from(Messagerie.this);

        // Créer un layout d'erreur
        View errorView = inflater.inflate(R.layout.item_message_error, messageList, false);
        TextView errorText = errorView.findViewById(R.id.errorMessage);
        Button retryButton = errorView.findViewById(R.id.retryButton);

        errorText.setText(errorMessage);

        retryButton.setOnClickListener(v -> {
            // Réessayer l'envoi du message en cas d'erreur
            send.performClick();
        });

        messageList.addView(errorView);
    }



}

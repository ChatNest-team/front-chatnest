package com.example.chatnest;

import static java.time.MonthDay.now;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Messagerie extends AppCompatActivity {


    private static final String TAG = "MessagerieActivity";

    public static final String EXTRA_ID_AGENT = "idAgent";

    private EditText messageInput;
    private Button send;
    private int idAgent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_messagerie);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        messageInput=findViewById(R.id.etMessage);

        String idAgentStr = getIntent().getStringExtra(EXTRA_ID_AGENT);
        String idClient = getIntent().getStringExtra("idClient");


        if (idAgentStr != null) {
            try {
                idAgent = Integer.parseInt(idAgentStr);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Erreur de conversion de l'ID agent", e);
                idAgent = -1;

            }
        } else {
            Log.w(TAG, "ID agent non fourni");
            idAgent = -1;



        }


        send = findViewById(R.id.btnEnvoyer);

        send.setOnClickListener(v -> {
            String messagetoSend = messageInput.getText().toString();
            //Log.d(TAG, "route :" + idClient + "/" + idAgent + "/" + messagetoSend);

            if (idClient == null || idClient.isEmpty()) {
                Log.e(TAG, "ID client non fourni");
                return;
            }

            int idClientInt = Integer.parseInt(idClient);

            MessageRequest messageRequest = new MessageRequest();
            messageRequest.setTexte_Message(messagetoSend);
            messageRequest.setEnvoyeur("client");
            messageRequest.setId_plateforme(1);


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);

            apiService.messageSend(idClientInt, idAgent, messageRequest).enqueue(new retrofit2.Callback<Void>() {
                @Override
                public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Message envoyé avec succès !");
                        messageInput.setText("");
                        // Optionnel : Afficher une Toast ou autre notification visuelle à l'utilisateur
                    } else {
                        Log.e(TAG, "Erreur lors de l'envoi : " + response.code());
                        // Optionnel : Afficher un message d'erreur à l'utilisateur
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                    Log.e(TAG, "Échec de l'envoi : " + t.getMessage());
                    // Optionnel : Afficher une erreur à l'utilisateur si la requête échoue
                }
            });
        });


    }}

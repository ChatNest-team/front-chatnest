package com.example.chatnest;

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

public class Messagerie extends AppCompatActivity {


    private static final String TAG = "MessagerieActivity";

    public static final String EXTRA_ID_AGENT = "idAgent";


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

        EditText messageText=findViewById(R.id.etMessage);
        String textMessage = messageText.getText().toString();

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
            Log.d(TAG, "route :" + idClient +"/"+idAgent+"/"+textMessage);

            //MessageRequest messageRequest = new MessageRequest(idClient,idAgent,)

        });
    }
}

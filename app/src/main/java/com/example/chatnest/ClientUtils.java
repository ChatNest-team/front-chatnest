package com.example.chatnest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUtils {

    public static void fetchClients(Context context, int idAgent, LinearLayout lClients) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://your-base-url.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Client>> call = apiService.getClients(idAgent);

        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Client> clients = response.body();
                    lClients.removeAllViews();

                    for (Client client : clients) {
                        LinearLayout clientLayout = (LinearLayout) LayoutInflater.from(context)
                                .inflate(R.layout.clientitem, null);

                        TextView clientName = clientLayout.findViewById(R.id.clientName);
                        clientName.setText(client.getNom() + " " + client.getPrenom());

                        ImageView clientImage = clientLayout.findViewById(R.id.clientImage);
                        String imageUrl = client.getImage();
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Glide.with(context)
                                    .load(imageUrl)
                                    .circleCrop()
                                    .into(clientImage);
                        } else {
                            clientImage.setImageResource(R.drawable.user);
                        }

                        lClients.addView(clientLayout);
                    }
                } else {
                    Log.e("ClientUtils", "Erreur dans la réponse");
                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                Log.e("ClientUtils", "Échec : " + t.getMessage());
            }
        });
    }
}


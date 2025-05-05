package com.example.chatnest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnnonceAdapter extends RecyclerView.Adapter<AnnonceAdapter.AnnonceViewHolder> {

    private List<Announcement> announcementList;

    public AnnonceAdapter(List<Announcement> announcementList) {
        this.announcementList = announcementList;
    }

    @NonNull
    @Override
    public AnnonceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_announcement, parent, false);
        return new AnnonceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnonceViewHolder holder, int position) {
        Announcement announcement = announcementList.get(position);

        Context context = holder.itemView.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySession", Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString("role", "client");
        //Log.d("AnnonceAdapter", "Rôle de l'utilisateur : " + userRole);
        holder.tvTitreAnnonce.setText("Titre : " + announcement.getNom());
        holder.tvPrix.setText(String.format("Prix : %.2f €", announcement.getPrixPropriete()));
        holder.tvAdresse.setText("Adresse : " + announcement.getAdresse());
        holder.tvNomAgent.setText("Ajouté par : " + announcement.getNomAgent());

        // Montrer ou cacher le bouton "Contacter"
        if ("agent".equals(userRole)) {
            holder.btnContacter.setVisibility(View.GONE);
        } else {
            holder.btnContacter.setVisibility(View.VISIBLE);
            holder.idstorevisite.setVisibility(View.GONE);
        }

        holder.idstorevisite.setOnClickListener(v->{
            String agentId = sharedPreferences.getString("id", null);

            String announcementId = String.valueOf(announcement.getIdPropriete());
            String announcementAdress = String.valueOf(announcement.getAdresse());
            if (agentId != null) {
                Intent intent = new Intent(context, Storevisites.class);
                intent.putExtra("idAgent", agentId);
                intent.putExtra("AnnounceAdress", announcementAdress);
                intent.putExtra("idPropriete", announcementId);
                intent.putExtra("role", "agent");
                context.startActivity(intent); // Lancer l'activité
            } else {
                Log.e("AnnonceAdapter", "Agent ID est null !");
            }
        });

        holder.btnContacter.setOnClickListener(v -> {
            String userId = sharedPreferences.getString("id", null);
            Intent intent = new Intent(context, Messagerie.class);
            intent.putExtra("idAgent", String.valueOf(announcement.getIdAgent()));
            intent.putExtra("idClient", userId);
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }

    public static class AnnonceViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitreAnnonce;
        TextView tvPrix;
        ImageView ivPoster;
        TextView tvAdresse;
        TextView tvNomAgent;
        Button btnContacter;
        ImageView idstorevisite;

        public AnnonceViewHolder(View itemView) {
            super(itemView);
            //ivPoster = itemView.findViewById(R.id.idivPoster);
            tvTitreAnnonce = itemView.findViewById(R.id.idtvTitreAnnonce);
            tvPrix = itemView.findViewById(R.id.idtvPrix);
            tvAdresse = itemView.findViewById(R.id.idtvAdresse);
            tvNomAgent = itemView.findViewById(R.id.tvNomAgent);
            btnContacter = itemView.findViewById(R.id.idbtnContacter);
            idstorevisite = itemView.findViewById(R.id.idstorevisite);
        }
    }
}

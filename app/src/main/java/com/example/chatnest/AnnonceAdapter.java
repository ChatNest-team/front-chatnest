package com.example.chatnest;

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
        //holder.ivPoster.setImageAlpha(announcement.getPoster());
        holder.tvTitreAnnonce.setText("Titre : " + announcement.getNom());
        holder.tvPrix.setText(String.format("Prix : %.2f €", announcement.getPrixPropriete()));
        holder.tvAdresse.setText("Adresse : " + announcement.getVille());
        //holder.tvNomAgent.setText("Ajouté par : " + announcement.getNomAgent());
        holder.btnContacter.setOnClickListener(v -> {
            // Action à déclencher
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

        public AnnonceViewHolder(View itemView) {
            super(itemView);
            //ivPoster = itemView.findViewById(R.id.idivPoster);
            tvTitreAnnonce = itemView.findViewById(R.id.idtvTitreAnnonce);
            tvPrix = itemView.findViewById(R.id.idtvPrix); // Assure-toi que cet ID existe bien dans le XML
            tvAdresse = itemView.findViewById(R.id.idtvAdresse);
            tvNomAgent = itemView.findViewById(R.id.tvNomAgent);
            btnContacter = itemView.findViewById(R.id.idbtnContacter);
        }
    }
}

package com.example.chatnest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class VisiteAdapter extends BaseAdapter {

    private Context context;
    private List<Visite> visites;

    public VisiteAdapter(Context context, List<Visite> visites) {
        this.context = context;
        this.visites = visites;
    }

    @Override
    public int getCount() {
        return visites.size();
    }

    @Override
    public Object getItem(int position) {
        return visites.get(position);
    }

    @Override
    public long getItemId(int position) {
        return visites.get(position).getID_Visite();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_visite, parent, false);
        }

        // Récupérer l'objet Visite à la position donnée
        Visite visite = visites.get(position);

        // Références aux TextViews dans list_item_visite.xml
        TextView nomClient = convertView.findViewById(R.id.nom_client);
        TextView prenomClient = convertView.findViewById(R.id.prenom_client);
        TextView dateVisite = convertView.findViewById(R.id.date_visite);
        TextView heureVisite = convertView.findViewById(R.id.heure_visite);
        TextView adresseVisite = convertView.findViewById(R.id.adresse_visite);
        Button btnModifier = convertView.findViewById(R.id.btn_modifier);
        Button btnSupprimer = convertView.findViewById(R.id.btn_supprimer);

        // Afficher les données dans les TextViews
        nomClient.setText(visite.getNom_client());
        prenomClient.setText(visite.getPrenom_client());
        dateVisite.setText(visite.getDate_visite());
        heureVisite.setText(visite.getHeure());

        // Afficher l'adresse uniquement si elle existe
        if (visite.getAdresse() != null && !visite.getAdresse().isEmpty()) {
            adresseVisite.setVisibility(View.VISIBLE);
            adresseVisite.setText(visite.getAdresse());
        } else {
            adresseVisite.setVisibility(View.GONE);
        }

        // Ajouter des actions aux boutons si nécessaire
        btnModifier.setOnClickListener(v -> {
            // Action de modification
        });

        btnSupprimer.setOnClickListener(v -> {
            // Action de suppression
        });

        return convertView;
    }
}

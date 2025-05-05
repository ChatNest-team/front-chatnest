package com.example.chatnest;

public class Visite {
    private int ID_Visite;
    private String nom_client;
    private String prenom_client;
    private String date_visite;
    private String heure;
    private String adresse;

    public Visite(String adresse, String heure, String date_visite, String prenom_client, String nom_client, int ID_Visite) {
        this.adresse = adresse;
        this.heure = heure;
        this.date_visite = date_visite;
        this.prenom_client = prenom_client;
        this.nom_client = nom_client;
        this.ID_Visite = ID_Visite;
    }

    public int getID_Visite() {
        return ID_Visite;
    }

    public void setID_Visite(int ID_Visite) {
        this.ID_Visite = ID_Visite;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getPrenom_client() {
        return prenom_client;
    }

    public void setPrenom_client(String prenom_client) {
        this.prenom_client = prenom_client;
    }

    public String getDate_visite() {
        return date_visite;
    }

    public void setDate_visite(String date_visite) {
        this.date_visite = date_visite;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}

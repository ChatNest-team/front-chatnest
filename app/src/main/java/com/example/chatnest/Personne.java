package com.example.chatnest;

public class Personne {
    private int ID_Personne;
    private String Nom;

    private  String Prenom;
    private String Email;
    private String Adresse;
    private String Date_inscription;

    public Personne(int ID_Personne, String nom, String prenom, String email, String adresse, String date_inscription) {
        this.ID_Personne = ID_Personne;
        Nom = nom;
        Prenom = prenom;
        Email = email;
        Adresse = adresse;
        Date_inscription = date_inscription;
    }

    public int getID_Personne() {
        return ID_Personne;
    }

    public void setID_Personne(int ID_Personne) {
        this.ID_Personne = ID_Personne;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getDate_inscription() {
        return Date_inscription;
    }

    public void setDate_inscription(String date_inscription) {
        Date_inscription = date_inscription;
    }
}

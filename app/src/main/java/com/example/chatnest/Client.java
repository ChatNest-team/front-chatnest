package com.example.chatnest;

// com.example.chatnest.Client.java
public class Client {
    public int id;
    public String Nom;
    public String Prenom;
    public String Email;
    public String Parrain_Nom;
    public String Parrain_Prenom;
    public String Tél;
    public String Image;

    public String role;

   public int id_parrain;

    public Client(int id_parrain, String role, String image, String tél, String parrain_Prenom, String parrain_Nom, String email, String prenom, String nom, int id) {
        this.id_parrain = id_parrain;
        this.role = role;
        Image = image;
        Tél = tél;
        Parrain_Prenom = parrain_Prenom;
        Parrain_Nom = parrain_Nom;
        Email = email;
        Prenom = prenom;
        Nom = nom;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getParrain_Nom() {
        return Parrain_Nom;
    }

    public void setParrain_Nom(String parrain_Nom) {
        Parrain_Nom = parrain_Nom;
    }

    public String getParrain_Prenom() {
        return Parrain_Prenom;
    }

    public void setParrain_Prenom(String parrain_Prenom) {
        Parrain_Prenom = parrain_Prenom;
    }

    public String getTél() {
        return Tél;
    }

    public void setTél(String tél) {
        Tél = tél;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId_parrain() {
        return id_parrain;
    }

    public void setId_parrain(int id_parrain) {
        this.id_parrain = id_parrain;
    }
}

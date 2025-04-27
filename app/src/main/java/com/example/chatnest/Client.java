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

    public String role;

    public Client(int id, String nom, String prenom, String email, String parrain_Nom, String parrain_Prenom, String tél, String role) {
        this.id = id;
        Nom = nom;
        Prenom = prenom;
        Email = email;
        Parrain_Nom = parrain_Nom;
        Parrain_Prenom = parrain_Prenom;
        Tél = tél;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

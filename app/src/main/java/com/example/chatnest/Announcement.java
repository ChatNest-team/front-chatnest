package com.example.chatnest;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.SerializedName;

public class Announcement {

    @SerializedName("ID_Propriete")
    private int idPropriete;

    @SerializedName("Nom")
    private String nom;

    @SerializedName("Prix_Propriete")
    private double prixPropriete; // ou double si tu préfères, selon l'utilisation

    @SerializedName("Dimension")
    private String dimension;

    @SerializedName("Description_Propriete")
    private String description;

    @SerializedName("Piscine")
    private boolean piscine;

    @SerializedName("Meuble")
    private boolean meuble;

    @SerializedName("Jardin")
    private boolean jardin;

    @SerializedName("poster")
    private String poster;

    @SerializedName("Ville")
    private String ville;

    @SerializedName("Adresse")
    private String adresse;

    @SerializedName("CodePostal")
    private int codePostal;

    @SerializedName("Region")
    private String region;

    @SerializedName("statut")
    private String statut;

    @SerializedName("Nombre_Piece")
    private int nombrePiece;

    public int getIdPropriete() {
        return idPropriete;
    }

    public void setIdPropriete(int idPropriete) {
        this.idPropriete = idPropriete;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrixPropriete() {
        return prixPropriete;
    }

    public void setPrixPropriete(double prixPropriete) {
        this.prixPropriete = prixPropriete;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPiscine() {
        return piscine;
    }

    public void setPiscine(boolean piscine) {
        this.piscine = piscine;
    }

    public boolean isMeuble() {
        return meuble;
    }

    public void setMeuble(boolean meuble) {
        this.meuble = meuble;
    }

    public boolean isJardin() {
        return jardin;
    }

    public void setJardin(boolean jardin) {
        this.jardin = jardin;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getNombrePiece() {
        return nombrePiece;
    }

    public void setNombrePiece(int nombrePiece) {
        this.nombrePiece = nombrePiece;
    }
}



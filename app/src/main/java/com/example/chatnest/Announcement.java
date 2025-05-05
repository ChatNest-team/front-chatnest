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

    @SerializedName("adresse")
    private String adresse;

    @SerializedName("CodePostal")
    private int codePostal;

    @SerializedName("Region")
    private String region;

    @SerializedName("statut")
    private String statut;

    @SerializedName("Nombre_Piece")
    private int nombrePiece;

    @SerializedName("Nom_Agent")
    private String nomAgent;

    @SerializedName("Prenom_Agent")
    private String prenomAgent;

    @SerializedName("id_agent")
    private int idAgent;

    @SerializedName("id_user")
    private int idUser;

    public Announcement(int idPropriete, String nom, double prixPropriete, String dimension, String description, boolean piscine, boolean meuble, boolean jardin, String poster, String ville, String adresse, int codePostal, String region, String statut, int nombrePiece, String nomAgent, String prenomAgent, int idAgent, int idUser) {
        this.idPropriete = idPropriete;
        this.nom = nom;
        this.prixPropriete = prixPropriete;
        this.dimension = dimension;
        this.description = description;
        this.piscine = piscine;
        this.meuble = meuble;
        this.jardin = jardin;
        this.poster = poster;
        this.ville = ville;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.region = region;
        this.statut = statut;
        this.nombrePiece = nombrePiece;
        this.nomAgent = nomAgent;
        this.prenomAgent = prenomAgent;
        this.idAgent = idAgent;
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(int idAgent) {
        this.idAgent = idAgent;
    }

    public String getPrenomAgent() {
        return prenomAgent;
    }

    public void setPrenomAgent(String prenomAgent) {
        this.prenomAgent = prenomAgent;
    }

    public String getNomAgent() {
        return nomAgent;
    }

    public void setNomAgent(String nomAgent) {
        this.nomAgent = nomAgent;
    }

    public int getNombrePiece() {
        return nombrePiece;
    }

    public void setNombrePiece(int nombrePiece) {
        this.nombrePiece = nombrePiece;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public boolean isJardin() {
        return jardin;
    }

    public void setJardin(boolean jardin) {
        this.jardin = jardin;
    }

    public boolean isMeuble() {
        return meuble;
    }

    public void setMeuble(boolean meuble) {
        this.meuble = meuble;
    }

    public boolean isPiscine() {
        return piscine;
    }

    public void setPiscine(boolean piscine) {
        this.piscine = piscine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public double getPrixPropriete() {
        return prixPropriete;
    }

    public void setPrixPropriete(double prixPropriete) {
        this.prixPropriete = prixPropriete;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdPropriete() {
        return idPropriete;
    }

    public void setIdPropriete(int idPropriete) {
        this.idPropriete = idPropriete;
    }
}



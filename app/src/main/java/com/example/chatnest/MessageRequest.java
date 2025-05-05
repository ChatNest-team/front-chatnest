package com.example.chatnest;

public class MessageRequest {
    private Integer id_client;
    private Integer id_agent;
    private String Texte_Message;
    private String Statut;
    private String envoyeur;

    private int envoyeurId;
    private int id_plateforme;
    //private int id_Messagerie;


    public Integer getId_client() {
        return id_client;
    }

    public void setId_client(Integer id_client) {
        this.id_client = id_client;
    }

    public Integer getId_agent() {
        return id_agent;
    }

    public void setId_agent(Integer id_agent) {
        this.id_agent = id_agent;
    }

    public String getTexte_Message() {
        return Texte_Message;
    }

    public void setTexte_Message(String texte_Message) {
        Texte_Message = texte_Message;
    }

    public String getStatut() {
        return Statut;
    }

    public void setStatut(String statut) {
        Statut = statut;
    }

    public String getEnvoyeur() {
        return envoyeur;
    }

    public void setEnvoyeur(String envoyeur) {
        this.envoyeur = envoyeur;
    }

    public int getEnvoyeurId() {
        return envoyeurId;
    }

    public void setEnvoyeurId(int envoyeurId) {
        this.envoyeurId = envoyeurId;
    }

    public int getId_plateforme() {
        return id_plateforme;
    }

    public void setId_plateforme(int id_plateforme) {
        this.id_plateforme = id_plateforme;
    }
}

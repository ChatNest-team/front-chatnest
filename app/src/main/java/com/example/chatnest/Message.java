package com.example.chatnest;

public class Message {
    private String texte;
    private boolean isFromMe;

    private String envoyeur;

    public Message(String texte, boolean isFromMe, String envoyeur) {
        this.texte = texte;
        this.isFromMe = isFromMe;
        this.envoyeur = envoyeur;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public boolean isFromMe() {
        return isFromMe;
    }

    public void setFromMe(boolean fromMe) {
        isFromMe = fromMe;
    }

    public String getEnvoyeur() {
        return envoyeur;
    }

    public void setEnvoyeur(String envoyeur) {
        this.envoyeur = envoyeur;
    }
}


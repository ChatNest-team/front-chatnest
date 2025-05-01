package com.example.chatnest;

public class Conversation {
    private int interlocuteur_id;
    private String interlocuteur_nom;


   private String message_texte;
   private String envoye_par;

   private String envoye_le;

    public Conversation(int interlocuteur_id, String interlocuteur_nom, String message_texte, String envoye_par, String envoye_le) {
        this.interlocuteur_id = interlocuteur_id;
        this.interlocuteur_nom = interlocuteur_nom;
        this.message_texte = message_texte;
        this.envoye_par = envoye_par;
        this.envoye_le = envoye_le;
    }

    public int getInterlocuteur_id() {
        return interlocuteur_id;
    }

    public void setInterlocuteur_id(int interlocuteur_id) {
        this.interlocuteur_id = interlocuteur_id;
    }

    public String getInterlocuteur_nom() {
        return interlocuteur_nom;
    }

    public void setInterlocuteur_nom(String interlocuteur_nom) {
        this.interlocuteur_nom = interlocuteur_nom;
    }

    public String getMessage_texte() {
        return message_texte;
    }

    public void setMessage_texte(String message_texte) {
        this.message_texte = message_texte;
    }

    public String getEnvoye_par() {
        return envoye_par;
    }

    public void setEnvoye_par(String envoye_par) {
        this.envoye_par = envoye_par;
    }

    public String getEnvoye_le() {
        return envoye_le;
    }

    public void setEnvoye_le(String envoye_le) {
        this.envoye_le = envoye_le;
    }
}

package com.example.chatnest;

public class Message {
    private String texte;
    private boolean isFromMe;

    public Message(String texte, boolean isFromMe) {
        this.texte = texte;
        this.isFromMe = isFromMe;
    }

    public String getTexte() {
        return texte;
    }

    public boolean isFromMe() {
        return isFromMe;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public void setFromMe(boolean fromMe) {
        isFromMe = fromMe;
    }
}


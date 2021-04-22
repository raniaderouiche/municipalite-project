package org.fsb.municipalite.entities;

import javax.persistence.Entity;

@Entity
public class Complaint extends BaseEntity{
    private String nomCitoyen;
    private String sujet;
    private long cin;
    private String core;
    
    public enum Etat {
        processed, unprocessed
    }
    private Etat etat;

    public Complaint() {
    }

    public String getNomCitoyen() {
        return nomCitoyen;
    }

    public void setNomCitoyen(String nomCitoyen) {
        this.nomCitoyen = nomCitoyen;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public long getCin() {
        return cin;
    }

    public void setCin(long cin) {
        this.cin= cin;
    }
    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }
    
    @Override
    public String toString() {
        return "Complaint{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", nomCitoyen='" + nomCitoyen + '\'' +
                ", sujet='" + sujet + '\'' +
                ", etat=" + etat +
                ", cin ="+cin+
                '}';
    }
}







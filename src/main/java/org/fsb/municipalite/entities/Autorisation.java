package org.fsb.municipalite.entities;

import javax.persistence.Entity;

@Entity
public class Autorisation extends BaseEntity{
    private String nomCitoyen;
    private String sujet;
    public enum Etat {
        processed, unprocessed
    }
    private Etat etat;

    public Autorisation() {
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

    @Override
    public String toString() {
        return "Autorisation{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", nomCitoyen='" + nomCitoyen + '\'' +
                ", sujet='" + sujet + '\'' +
                ", etat=" + etat +
                '}';
    }
}

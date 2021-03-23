package org.fsb.municipalite.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Materiel extends BaseEntity{
    private int reference;
    private String nom;

    enum Etat {
        disponible, occupe,enPanne;
    }
    private Etat etat;

    @ManyToOne
    @JoinColumn
    private Projet projet;

    public Materiel() {
    }

    public Materiel(int reference, String nom, Etat etat) {
        this.reference = reference;
        this.nom = nom;
        this.etat = etat;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    @Override
    public String toString() {
        return "Materiel{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", reference=" + reference +
                ", nom='" + nom + '\'' +
                ", etat=" + etat +
                ", projet=" + projet +
                '}';
    }
}

package org.fsb.municipalite.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Materiel extends BaseEntity{
	
	public enum Etat {
	    disponible, occupe,enPanne;
	}
	
	
    private String reference;
    private String nom;
    private Etat etat;

    @ManyToOne
    @JoinColumn
    private Projet projet_id;

    public Materiel() {
    }

    public Materiel(String reference, String nom, Etat etat) {
        this.reference = reference;
        this.nom = nom;
        this.etat = etat;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
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
        return projet_id;
    }

    public void setProjet(Projet projet) {
        this.projet_id = projet;
    }

    @Override
    public String toString() {
        return "Materiel{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", reference=" + reference +
                ", nom='" + nom + '\'' +
                ", etat=" + etat +
                ", projet=" + projet_id +
                '}';
    }
}

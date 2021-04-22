package org.fsb.municipalite.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
    private Projet projet;


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
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Long getProjetValue() {
    	if(this.projet != null )
    		return projet.getId();
    	return -1l;
    }

    public String getCreatedAtValue(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        return createdAt.format(format);
    }

    @Override
    public String toString() {
        return "Materiel{" +
                "id=" + id +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", reference='" + reference + '\'' +
                ", nom='" + nom + '\'' +
                ", etat=" + etat +
                ", projet=" + projet +
                '}';
    }
}

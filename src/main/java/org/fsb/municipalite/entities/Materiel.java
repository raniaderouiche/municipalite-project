package org.fsb.municipalite.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Entity
public class Materiel extends BaseEntity{
	
	public enum Etat {
	    disponible, occupe,enPanne;
	}
	
	
    private String reference;
    private String nom;
    private Etat etat;

    private LocalDate startDate;
    private LocalDate endDate;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getProjetValue() {
    	if(this.projet != null )
    		return projet.getId() + " - " + projet.getName();
    	return " - ";
    }

    public String getCreatedAtValue(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        return createdAt.format(format);
    }

    public String getStartDateValue(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return startDate.format(format);
    }

    public String getEndDateAtValue(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return endDate.format(format);
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

package org.fsb.municipalite.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Projet extends BaseEntity{
    private String name;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String Lieu;
    private int budget;

    public enum Etat {
        Finished, Unfinished;
    }

    private Etat etat;

    @ManyToOne
    @JoinColumn
    private Equipe equipe;

    @OneToMany(mappedBy = "projet")
    private List<Materiel> materielList;

    public Projet() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getLieu() {
        return Lieu;
    }

    public void setLieu(String lieu) {
        Lieu = lieu;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public String getEquipeValue() {
    	if(this.equipe != null){ return equipe.getId() + " - " + equipe.getNom();}
    	return "-";
    }
    
    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public List<Materiel> getMaterielList() {
        return materielList;
    }

    public void setMaterielList(List<Materiel> materielList) {
        this.materielList = materielList;
    }

    @Override
    public String toString() {
        return "Projet{" +
                "id=" + id +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", Lieu='" + Lieu + '\'' +
                ", budget=" + budget +
                ", equipe=" + equipe +
                ", materielList=" + materielList +
                '}';
    }
}

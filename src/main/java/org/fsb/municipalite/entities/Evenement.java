package org.fsb.municipalite.entities;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Evenement extends BaseEntity{

    private String nom;
    private LocalDate dateEvenement;
    private String lieu;
    private Long budget;

    public Evenement() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateEvenement() {
        return dateEvenement;
    }

    public void setDateEvenement(LocalDate dateEvenement) {
        this.dateEvenement = dateEvenement;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", dateEvenement=" + dateEvenement +
                ", lieu='" + lieu + '\'' +
                ", budget=" + budget +
                '}';
    }
}

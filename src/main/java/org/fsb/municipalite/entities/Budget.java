package org.fsb.municipalite.entities;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Budget extends BaseEntity{
    private String secteur;
    private LocalDate year;
    private double budget;


    public Budget() {
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", secteur='" + secteur + '\'' +
                ", year=" + year +
                ", budget=" + budget +
                '}';
    }
}

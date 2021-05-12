package org.fsb.municipalite.entities;

import javax.persistence.Entity;

@Entity
public class Budget extends BaseEntity{
    private String secteur;
    private String year;
    private String source;
    private long budget;


    public Budget() {
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
                ", nom='" + secteur + '\'' +
                ", year=" + year +
                ", lieu='" + source + '\'' +
                ", budget=" + budget +
                '}';
    }
}

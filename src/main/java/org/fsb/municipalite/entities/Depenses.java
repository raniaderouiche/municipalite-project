package org.fsb.municipalite.entities;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Depenses extends BaseEntity{
    private String secteur_dep;
    private LocalDate date_dep;
    private double somme_dep;


    public Depenses() {
    }

    public void setSecteur_dep(String secteur_dep) {
        this.secteur_dep = secteur_dep;
    }

    public void setDate_dep(LocalDate date_dep) {
        this.date_dep = date_dep;
    }

    public void setSomme_dep(double somme_dep) {
        this.somme_dep = somme_dep;
    }

    public String getSecteur_dep() {
        return secteur_dep;
    }

    public LocalDate getDate_dep() {
        return date_dep;
    }

    public double getSomme_dep() {
        return somme_dep;
    }

    @Override
    public String toString() {
        return "Depenses{" +
                "id=" + id +
                ", secteur='" + secteur_dep + '\'' +
                ", date=" + date_dep +
                ", somme ='" + somme_dep + '\'' +
                '}';
    }
}

package org.fsb.municipalite.entities;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Depenses extends BaseEntity{
    private String secteur_dep;
    private LocalDate date_dep;
    private long somme_dep;


    public Depenses() {
    }

    public String getSecteurDep() {
        return secteur_dep;
    }

    public void setSecteurDep(String secteur_dep) {
        this.secteur_dep = secteur_dep;
    }

    public LocalDate getDateDep() { return date_dep; }

    public void setDateDep(LocalDate date_dep) { this.date_dep = date_dep; }

    public long getSommeDep() { return somme_dep; }

    public void setSommeDep(long somme_dep) { this.somme_dep = somme_dep; }

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

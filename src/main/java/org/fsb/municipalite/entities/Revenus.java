package org.fsb.municipalite.entities;

import javax.persistence.Entity;

@Entity
public class Revenus extends BaseEntity {
    private String source_rev;
    private String date_rev;
    private Long somme_rev;



    public Revenus() {
    }

    public String getDate() {
        return date_rev;
    }

    public void setDate(String date) {
        this.date_rev = date_rev;
    }

    public String getSourceRev() {
        return source_rev;
    }

    public void setSourceRev(String source) {
        this.source_rev = source_rev;
    }

    public Long getSommeRev() {
        return somme_rev;
    }

    public void setSommeRev(Long somme_rev) {
        this.somme_rev = somme_rev;
    }

    @Override
    public String toString() {
        return "Revenus{" +
                "id=" + id +
                ", date=" + date_rev +
                ", source='" + source_rev + '\'' +
                ", somme=" + somme_rev +
                '}';
    }
}

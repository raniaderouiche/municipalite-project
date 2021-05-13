package org.fsb.municipalite.entities;

import javax.persistence.Entity;

@Entity
public class Revenus extends BaseEntity {
    private String source_rev;
    private String date_rev;
    private Long somme_rev;

    public Revenus() {
    }

    public String getSource_rev() {
        return source_rev;
    }

    public String getDate_rev() {
        return date_rev;
    }

    public Long getSomme_rev() {
        return somme_rev;
    }

    public void setSource_rev(String source_rev) {
        this.source_rev = source_rev;
    }

    public void setDate_rev(String date_rev) {
        this.date_rev = date_rev;
    }

    public void setSomme_rev(Long somme_rev) {
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

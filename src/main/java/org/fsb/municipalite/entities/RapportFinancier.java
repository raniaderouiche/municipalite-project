package org.fsb.municipalite.entities;

import javax.persistence.Entity;

@Entity
public class RapportFinancier extends BaseEntity{
    private String titre;
    private String facture;

    public RapportFinancier() {
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getFacture() {
        return facture;
    }

    public void setFacture(String facture) {
        this.facture = facture;
    }

    @Override
    public String toString() {
        return "RapportFinancier{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", titre='" + titre + '\'' +
                ", facture='" + facture + '\'' +
                '}';
    }
}

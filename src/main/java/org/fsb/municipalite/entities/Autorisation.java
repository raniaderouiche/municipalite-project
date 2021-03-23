package org.fsb.municipalite.entities;

import javax.persistence.Entity;

@Entity
public class Autorisation extends BaseEntity{
    private String titre;
    private String description;

    public Autorisation() {
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Autorisation{" +
                "titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", version=" + version +
                ", createdAt=" + createdAt +
                '}';
    }
}

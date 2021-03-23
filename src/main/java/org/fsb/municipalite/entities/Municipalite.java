package org.fsb.municipalite.entities;

import javax.persistence.Entity;

@Entity
public class Municipalite extends BaseEntity{
    private String nom;
    private String adresse;
    private String email;
    private String tel;
    private String website;
    private String workHours;

    public Municipalite() {
    }

    public Municipalite(String nom, String adresse, String email, String tel, String website, String workHours) {
        this.nom = nom;
        this.adresse = adresse;
        this.email = email;
        this.tel = tel;
        this.website = website;
        this.workHours = workHours;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    @Override
    public String toString() {
        return "Municipalite{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", website='" + website + '\'' +
                ", workHours='" + workHours + '\'' +
                '}';
    }
}

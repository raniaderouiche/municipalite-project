package org.fsb.municipalite.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.format.DateTimeFormatter;

@Entity
public class Complaint extends BaseEntity{
    private String nomCitoyen;
    private String sujet;
    private long cin;
    @Column(columnDefinition = "CLOB")
    private String msg;
    
    public enum Etat {
        processed, unprocessed
    }
    private Etat etat;
    public Complaint() {
    }

    public String getDateComplaint(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.createdAt.format(format);
    }

    public String getNomCitoyen() {
        return nomCitoyen;
    }

    public void setNomCitoyen(String nomCitoyen) {
        this.nomCitoyen = nomCitoyen;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public long getCin() {
        return cin;
    }

    public void setCin(long cin) {
        this.cin= cin;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    @Override
    public String toString() {
        return "Complaint{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", nomCitoyen='" + nomCitoyen + '\'' +
                ", sujet='" + sujet + '\'' +
                ", etat=" + etat +
                ", cin ="+cin+
                '}';
    }
}







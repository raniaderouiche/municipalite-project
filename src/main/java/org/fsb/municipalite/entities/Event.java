package org.fsb.municipalite.entities;

import javax.persistence.Entity;
import java.time.LocalDateTime;


public class Event extends BaseEntity{
    private LocalDateTime date;
    private String nom;
    private String location;
    private Float budget;

    public Event() {
    }

    public Event(LocalDateTime date, String nom, String location, Float budget) {
        this.date = date;
        this.nom = nom;
        this.location = location;
        this.budget = budget;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", date=" + date +
                ", nom='" + nom + '\'' +
                ", location='" + location + '\'' +
                ", budget=" + budget +
                '}';
    }
}
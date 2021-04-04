package org.fsb.municipalite.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Projet extends BaseEntity{
    private String name;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String Lieu;
    private int budget;

    @ManyToOne
    @JoinColumn
    private Employee employee;

    @ManyToOne
    @JoinColumn
    private Equipe equipe;

    @OneToMany(mappedBy = "projet_id")
    private List<Materiel> materielList;

    public Projet() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getLieu() {
        return Lieu;
    }

    public void setLieu(String lieu) {
        Lieu = lieu;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public List<Materiel> getMaterielList() {
        return materielList;
    }

    public void setMaterielList(List<Materiel> materielList) {
        this.materielList = materielList;
    }

    @Override
    public String toString() {
        return "Projet{" +
                "id=" + id +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", Lieu='" + Lieu + '\'' +
                ", budget=" + budget +
                ", employee=" + employee +
                ", equipe=" + equipe +
                ", materielList=" + materielList +
                '}';
    }
}

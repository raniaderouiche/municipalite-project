package org.fsb.municipalite.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Employee extends BaseEntity {

    private String nom;
    private String prenom;
    private String cin;
    private String etatCivil;
    private String sexe;
    private LocalDateTime dateNaissance;

    @OneToMany(mappedBy = "employee")
    private List<Projet> projectList;

    @ManyToOne
    @JoinColumn
    private Equipe equipe;

    @OneToMany(mappedBy = "employee")
    private List<Tache> tacheList;

    @OneToOne(mappedBy = "employee")
    private Compte compte;

    @ManyToOne
    @JoinColumn
    private Role role;

    public Employee() {
    }

    public Employee(String nom, String prenom, String cin, String etatCivil, String sexe, LocalDateTime dateNaissance, String fonction, List<Projet> projectList) {
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.etatCivil = etatCivil;
        this.sexe = sexe;
        this.dateNaissance = dateNaissance;
        this.projectList = projectList;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEtatCivil() {
        return etatCivil;
    }

    public void setEtatCivil(String etatCivil) {
        this.etatCivil = etatCivil;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public LocalDateTime getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDateTime dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    

    public List<Projet> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Projet> projectList) {
        this.projectList = projectList;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public List<Tache> getTacheList() {
        return tacheList;
    }

    public void setTacheList(List<Tache> tacheList) {
        this.tacheList = tacheList;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", cin='" + cin + '\'' +
                ", etatCivil='" + etatCivil + '\'' +
                ", sexe='" + sexe + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", equipe=" + equipe +
                ", role=" + role +
                '}';
    }
}

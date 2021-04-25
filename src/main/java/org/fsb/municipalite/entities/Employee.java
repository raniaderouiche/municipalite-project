package org.fsb.municipalite.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Employee extends BaseEntity {

    private String nom;
    private String prenom;
    private String cin;
    private String etatCivil;
    private String sexe;
    private LocalDate dateNaissance;
    private String role;


    @ManyToOne
    @JoinColumn
    private Equipe equipe;

    @OneToMany(mappedBy = "employee")
    private List<Tache> tacheList;

    @OneToOne(mappedBy = "employee")
    private Compte compte;

    
    @OneToMany(mappedBy = "Responsable")
    private List<Equipe> equipeList;

    public Employee() {
    }

    public Employee(String nom, String prenom, String cin, String etatCivil, String sexe, LocalDate dateNaissance, String fonction, List<Projet> projectList) {
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.etatCivil = etatCivil;
        this.sexe = sexe;
        this.dateNaissance = dateNaissance;
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

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }


    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public List<Equipe> getEquipeList() {
		return equipeList;
	}

	public void setEquipeList(List<Equipe> equipeList) {
		this.equipeList = equipeList;
	}

	public List<Tache> getTacheList() {
        return tacheList;
    }

    public void setTacheList(List<Tache> tacheList) {
        this.tacheList = tacheList;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", cin='" + cin + '\'' +
                ", etatCivil='" + etatCivil + '\'' +
                ", sexe='" + sexe + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", role='" + role + '\'' +
                '}';
    }
}

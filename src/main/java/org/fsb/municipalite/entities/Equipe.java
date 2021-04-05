package org.fsb.municipalite.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Collection;
import java.util.List;

@Entity
public class Equipe extends BaseEntity{

    private Long idResponsable;
    private String nom;
    @OneToMany(mappedBy = "equipe")
    private List<Employee> employeeList;

    @OneToMany(mappedBy = "equipe")
    private List<Projet> projetList;

    @OneToMany(mappedBy = "equipe")
    private List<Tache> tacheList;

    public Equipe() {
    }

    public Long getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Long idResponsable) {
        this.idResponsable = idResponsable;
    }

   /* public List<Employee> getEmployeeList() {
        return employeeList;
    }*/

  /*  public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }*/

    public List<Tache> getTacheList() {
        return tacheList;
    }

    public void setTacheList(List<Tache> tacheList) {
        this.tacheList = tacheList;
    }


    public List<Projet> getProjetList() {
        return projetList;
    }

    public void setProjetList(List<Projet> projetList) {
        this.projetList = projetList;
    }

    public void setNom(String nom) {
    	this.nom = nom;
    }

    public String getNom() {
    	return this.nom;
    }
}

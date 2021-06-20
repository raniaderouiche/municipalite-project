package org.fsb.municipalite.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Equipe extends BaseEntity{
	
	@ManyToOne
	@JoinColumn
    private Employee Responsable;
	
    private String nom;
    @OneToMany(mappedBy = "equipe")
    private List<Employee> employeeList;

    @OneToMany(mappedBy = "equipe")
    private List<Projet> projetList;
    
    public Equipe() {
    }

    public Employee getResponsable() {
        return Responsable;
    }

    public void setResponsable(Employee idResponsable) {
        this.Responsable = idResponsable;
    }
    
    
    public String getResponsableValue() {
    	return Responsable.getId() + " - " + Responsable.getNom();
    }


    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
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

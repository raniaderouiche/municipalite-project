package org.fsb.municipalite.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class Tache extends BaseEntity{
    private LocalDate dueDate;
    public enum Etat {
        done,inProgress;
    }
    private Etat etat;
    private String name;
    @ManyToOne
    @JoinColumn
    private Employee employee;

    public Tache() {
    }

    public Tache(String name, LocalDate dueDate, Employee employee) {
    	this.name = name;
        this.dueDate = dueDate;
        this.employee = employee;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Employee getEmployee() {
        return employee;
    }
    
    public String getEmployeeId() {
    	if(this.employee != null)
    		return employee.getId() + " - " + employee.getNom();
    	return "-";
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getName() { 
    	return name;
    }

    public void setName(String name) {
    	this.name = name; 
	}
    @Override
    public String toString() {
        return "Tache{" +
                "id=" + id +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", dueDate=" + dueDate +
                ", etat=" + etat +
                ", employee=" + employee +
                '}';
    }
}

package org.fsb.municipalite.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Role extends BaseEntity{
    private String nom;

    @OneToMany (mappedBy = "role")
    private List<Employee> employeeList;

    public Role() {
    }

    public Role(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}

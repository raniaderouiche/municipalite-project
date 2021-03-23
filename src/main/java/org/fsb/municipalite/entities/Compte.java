package org.fsb.municipalite.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Compte extends BaseEntity{
    private String username;
    private String password;

    @OneToOne
    @JoinColumn
    private Employee employee;

    public Compte() {
    }

    public Compte(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "createdAt=" + createdAt +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", employee=" + employee +
                '}';
    }
}

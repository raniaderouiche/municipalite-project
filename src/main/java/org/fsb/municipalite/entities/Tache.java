package org.fsb.municipalite.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Tache extends BaseEntity{
    private LocalDateTime dueDate;
    enum Etat {
        done,inProgress;
    }
    private Etat etat;

    @ManyToOne
    @JoinColumn
    private Employee employee;

    @ManyToOne
    @JoinColumn
    private Equipe equipe;

    public Tache() {
    }

    public Tache(LocalDateTime dueDate, Employee employee, Equipe equipe) {
        this.dueDate = dueDate;
        this.employee = employee;
        this.equipe = equipe;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
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

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
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
                ", equipe=" + equipe +
                '}';
    }
}

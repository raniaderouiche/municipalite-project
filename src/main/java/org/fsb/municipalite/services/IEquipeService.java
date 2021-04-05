package org.fsb.municipalite.services;

import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.entities.Tache;

import java.util.List;

public interface IEquipeService {

    public Equipe create(Equipe equipe);

    public Equipe update(Equipe equipe);

    public List<Equipe> selectAll();

    public List<Equipe> selectBy(String param, String value);

    public List<Equipe> selectAll(String sortField, String sort);

    public Equipe getById(Long id);

    public void remove(Long id);

    public Equipe findOne(String paramName, Object paramValue);

    public Equipe findOne(String[] paramNames, Object[] paramValues);

    public int findCountBy(String paramName, String paramValue);

    public void addMember(Employee employee, List<Employee> employeeList);

}

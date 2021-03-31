package org.fsb.municipalite.services;
import java.util.List;

import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Employee;

public interface IEmployeeService {

    public Employee create(Employee employee);
    
    public Employee update(Employee materiel);

    public List<Employee> selectAll();

    public List<Employee> selectBy(String param, String value);

    public List<Employee> selectAll(String sortField, String sort);

    public Employee getById(Long id);

    public void remove(Long id);

    public Employee findOne(String paramName, Object paramValue);

    public Employee findOne(String[] paramNames, Object[] paramValues);

    public int findCountBy(String paramName, String paramValue);
}

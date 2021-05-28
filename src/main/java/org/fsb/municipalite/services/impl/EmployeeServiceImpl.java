package org.fsb.municipalite.services.impl;

import java.util.List;

import org.fsb.municipalite.dao.IEmployeeDao;
import org.fsb.municipalite.dao.impl.EmployeeDaoImpl;
import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.services.IEmployeeService;

public class EmployeeServiceImpl implements IEmployeeService {

     IEmployeeDao employeeDao;

    public EmployeeServiceImpl() {
        employeeDao = new EmployeeDaoImpl();
    }

    @Override
    public Employee create(Employee employee){
        return employeeDao.save(employee);
    }
    
    @Override
    public Employee update(Employee Employee) {
        return employeeDao.update(Employee);
    }

    @Override
    public List<Employee> selectAll() {
        return employeeDao.selectAll();
    }

    @Override
    public List<Employee> selectBy(String param, String value) {
        return employeeDao.selectBy(param,value);
    }

    @Override
    public List<Employee> selectAll(String sortField, String sort) {
        return employeeDao.selectAll(sortField,sort);
    }

    @Override
    public Employee getById(Long id) {
        return employeeDao.getById(id);
    }

    @Override
    public void remove(Long id) {
        employeeDao.remove(id);
    }

    @Override
    public Employee findOne(String paramName, Object paramValue) {
        return employeeDao.findOne(paramName,paramValue);
    }

    @Override
    public Employee findOne(String[] paramNames, Object[] paramValues) {
        return employeeDao.findOne(paramNames,paramValues);
    }

    @Override
    public int findCountBy(String paramName, String paramValue) {
        return employeeDao.findCountBy(paramName,paramValue);
    }
    @Override
    public List<Employee> selectLike(String param, String value) {
        return employeeDao.selectLike(param,value);
    }

}

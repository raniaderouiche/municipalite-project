package org.fsb.municipalite.services.impl;

import org.fsb.municipalite.dao.IEmployeeDao;
import org.fsb.municipalite.dao.impl.EmployeeDaoImpl;
import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.services.IEmployeeService;

public class EmployeeServiceImpl implements IEmployeeService {

    private IEmployeeDao employeeDao;

    public EmployeeServiceImpl() {
        employeeDao = new EmployeeDaoImpl();
    }

    @Override
    public Employee create(Employee employee){
        return employeeDao.save(employee);
    }
}

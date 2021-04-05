package org.fsb.municipalite.services.impl;

import org.fsb.municipalite.dao.impl.EquipeDaoImpl;
import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.entities.Tache;
import org.fsb.municipalite.services.IEquipeService;

import java.util.List;

public class EquipeServiceImpl implements IEquipeService {

    EquipeDaoImpl equipeDao;

    public EquipeServiceImpl() {
        this.equipeDao = new EquipeDaoImpl();
    }

    @Override
    public Equipe create(Equipe equipe) {
        return equipeDao.save(equipe);
    }

    @Override
    public Equipe update(Equipe equipe) {
        return equipeDao.update(equipe);
    }

    @Override
    public List<Equipe> selectAll() {
        return equipeDao.selectAll();
    }

    @Override
    public List<Equipe> selectBy(String param, String value) {
        return equipeDao.selectBy(param,value);
    }

    @Override
    public List<Equipe> selectAll(String sortField, String sort) {
        return equipeDao.selectAll(sortField, sort);
    }

    @Override
    public Equipe getById(Long id) {
        return equipeDao.getById(id);
    }

    @Override
    public void remove(Long id) {
        equipeDao.remove(id);
    }

    @Override
    public Equipe findOne(String paramName, Object paramValue) {
        return equipeDao.findOne(paramName,paramValue);
    }

    @Override
    public Equipe findOne(String[] paramNames, Object[] paramValues) {
        return equipeDao.findOne(paramNames,paramValues);
    }

    @Override
    public int findCountBy(String paramName, String paramValue) {
        return equipeDao.findCountBy(paramName,paramValue);
    }

    @Override
    public void addMember(Employee employee,List<Employee> employeeList) {
        employeeList.add(employee);
    }

}

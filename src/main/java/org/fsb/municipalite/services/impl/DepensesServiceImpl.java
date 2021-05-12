package org.fsb.municipalite.services.impl;

import org.fsb.municipalite.dao.impl.DepensesDaoImpl;
import org.fsb.municipalite.entities.Depenses;
import org.fsb.municipalite.services.IDepensesServices;

import java.util.List;

public class DepensesServiceImpl implements IDepensesServices{

    private DepensesDaoImpl depensesDao;

    public DepensesServiceImpl() { this.depensesDao = new DepensesDaoImpl(); }

    @Override
    public Depenses create(Depenses depenses) {
        return depensesDao.save(depenses);
    }

    @Override
    public Depenses update(Depenses depenses) {
        return depensesDao.update(depenses);
    }

    @Override
    public void remove(Long id) {
        depensesDao.remove(id);
    }

    @Override
    public List<Depenses> selectBy(String param, String value) {
        return depensesDao.selectBy(param,value);
    }

    @Override
    public List<Depenses> selectAll() {
        return depensesDao.selectAll();
    }
    @Override
    public Depenses getById(Long id) {
        return depensesDao.getById(id);
    }

    @Override
    public List<Depenses> selectLike(String param, String value) {
        return depensesDao.selectLike(param,value);
    }


}

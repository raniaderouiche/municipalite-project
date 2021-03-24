package org.fsb.municipalite.services.impl;

import org.fsb.municipalite.dao.IMaterielDao;
import org.fsb.municipalite.dao.impl.MaterielDaoImpl;
import org.fsb.municipalite.entities.Materiel;
import org.fsb.municipalite.services.IMaterielService;

import java.util.List;

public class MaterielServiceImpl implements IMaterielService {

    private IMaterielDao materielDao;

    public MaterielServiceImpl() {
        this.materielDao = new MaterielDaoImpl();
    }


    @Override
    public Materiel create(Materiel materiel) {
        return materielDao.save(materiel);
    }

    @Override
    public Materiel update(Materiel materiel) {
        return materielDao.update(materiel);
    }

    @Override
    public List<Materiel> selectAll() {
        return materielDao.selectAll();
    }

    @Override
    public List<Materiel> selectBy(String param, String value) {
        return materielDao.selectBy(param,value);
    }

    @Override
    public List<Materiel> selectAll(String sortField, String sort) {
        return materielDao.selectAll(sortField,sort);
    }

    @Override
    public Materiel getById(Long id) {
        return materielDao.getById(id);
    }

    @Override
    public void remove(Long id) {
        materielDao.remove(id);
    }

    @Override
    public Materiel findOne(String paramName, Object paramValue) {
        return materielDao.findOne(paramName,paramValue);
    }

    @Override
    public Materiel findOne(String[] paramNames, Object[] paramValues) {
        return materielDao.findOne(paramNames,paramValues);
    }

    @Override
    public int findCountBy(String paramName, String paramValue) {
        return materielDao.findCountBy(paramName,paramValue);
    }


}

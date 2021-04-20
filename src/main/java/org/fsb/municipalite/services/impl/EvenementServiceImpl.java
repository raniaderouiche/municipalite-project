package org.fsb.municipalite.services.impl;

import org.fsb.municipalite.dao.impl.EmployeeDaoImpl;
import org.fsb.municipalite.dao.impl.EvenementDaoImpl;
import org.fsb.municipalite.entities.Evenement;
import org.fsb.municipalite.services.IEvenementService;

import java.util.List;

public class EvenementServiceImpl implements IEvenementService {

    EvenementDaoImpl evenementDao;

    public EvenementServiceImpl() {
        this.evenementDao = new EvenementDaoImpl();
    }

    @Override
    public Evenement create(Evenement evenement) {
        return evenementDao.save(evenement);
    }

    @Override
    public Evenement update(Evenement evenement) {
        return evenementDao.update(evenement);
    }

    @Override
    public List<Evenement> selectAll() {
        return evenementDao.selectAll();
    }

    @Override
    public List<Evenement> selectBy(String param, String value) {
        return evenementDao.selectBy(param, value);
    }

    @Override
    public List<Evenement> selectAll(String sortField, String sort) {
        return evenementDao.selectAll();
    }

    @Override
    public Evenement getById(Long id) {
        return evenementDao.getById(id);
    }

    @Override
    public void remove(Long id) {
        evenementDao.remove(id);
    }

    @Override
    public Evenement findOne(String paramName, Object paramValue) {
        return evenementDao.findOne(paramName, paramValue);
    }

    @Override
    public Evenement findOne(String[] paramNames, Object[] paramValues) {
        return evenementDao.findOne(paramNames, paramValues);
    }

    @Override
    public int findCountBy(String paramName, String paramValue) {
        return evenementDao.findCountBy(paramName, paramValue);
    }

    @Override
    public List<Evenement> selectLike(String param, String value) {
        return evenementDao.selectLike(param, value);
    }
}

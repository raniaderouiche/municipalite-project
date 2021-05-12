package org.fsb.municipalite.services.impl;

import org.fsb.municipalite.dao.impl.BudgetDaoImpl;
import org.fsb.municipalite.dao.impl.RevenusDaoImpl;
import org.fsb.municipalite.entities.Budget;
import org.fsb.municipalite.entities.Revenus;
import org.fsb.municipalite.services.IBudgetService;
import org.fsb.municipalite.services.IRevenusService;

import java.util.List;

public class RevenusServiceImpl implements IRevenusService {
    RevenusDaoImpl revenusDao;

    public RevenusServiceImpl() {
        this.revenusDao = new RevenusDaoImpl();
    }

    @Override
    public Revenus create(Revenus revenus) {
        return revenusDao.save(revenus);
    }

    @Override
    public Revenus update(Revenus revenus) {
        return revenusDao.update(revenus);
    }

    @Override
    public List<Revenus> selectAll() {
        return revenusDao.selectAll();
    }

    @Override
    public List<Revenus> selectBy(String param, String value) {
        return revenusDao.selectBy(param, value);
    }

    @Override
    public List<Revenus> selectAll(String sortField, String sort) {
        return revenusDao.selectAll();
    }

    @Override
    public Revenus getById(Long id) {
        return revenusDao.getById(id);
    }

    @Override
    public void remove(Long id) {
        revenusDao.remove(id);
    }

    @Override
    public Revenus findOne(String paramName, Object paramValue) {
        return revenusDao.findOne(paramName, paramValue);
    }

    @Override
    public Revenus findOne(String[] paramNames, Object[] paramValues) {
        return revenusDao.findOne(paramNames, paramValues);
    }

    @Override
    public int findCountBy(String paramName, String paramValue) {
        return revenusDao.findCountBy(paramName, paramValue);
    }

    @Override
    public List<Revenus> selectLike(String param, String value) {
        return revenusDao.selectLike(param, value);
    }
}

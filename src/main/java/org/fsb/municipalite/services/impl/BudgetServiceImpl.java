package org.fsb.municipalite.services.impl;

import org.fsb.municipalite.dao.impl.BudgetDaoImpl;
import org.fsb.municipalite.entities.Budget;
import org.fsb.municipalite.services.IBudgetService;

import java.util.List;

public class BudgetServiceImpl implements IBudgetService {


   BudgetDaoImpl budgetDao;

    public BudgetServiceImpl() {
        this.budgetDao = new BudgetDaoImpl();
    }

    @Override
    public Budget create(Budget budget) {
        return budgetDao.save(budget);
    }

    @Override
    public Budget update(Budget budget) {
        return budgetDao.update(budget);
    }

    @Override
    public List<Budget> selectAll() {
        return budgetDao.selectAll();
    }

    @Override
    public List<Budget> selectBy(String param, String value) {
        return budgetDao.selectBy(param, value);
    }

    @Override
    public List<Budget> selectAll(String sortField, String sort) {
        return budgetDao.selectAll();
    }

    @Override
    public Budget getById(Long id) {
        return budgetDao.getById(id);
    }

    @Override
    public void remove(Long id) {
        budgetDao.remove(id);
    }

    @Override
    public Budget findOne(String paramName, Object paramValue) {
        return budgetDao.findOne(paramName, paramValue);
    }

    @Override
    public Budget findOne(String[] paramNames, Object[] paramValues) {
        return budgetDao.findOne(paramNames, paramValues);
    }

    @Override
    public int findCountBy(String paramName, String paramValue) {
        return budgetDao.findCountBy(paramName, paramValue);
    }

    @Override
    public List<Budget> selectLike(String param, String value) {
        return budgetDao.selectLike(param, value);
    }
}


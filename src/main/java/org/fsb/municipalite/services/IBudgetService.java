package org.fsb.municipalite.services;

import org.fsb.municipalite.entities.Budget;

import java.util.List;

public interface IBudgetService {
    public Budget create(Budget budget);

    public Budget update(Budget budget);

    public List<Budget> selectAll();

    public List<Budget> selectBy(String param, String value);

    public List<Budget> selectAll(String sortField, String sort);

    public Budget getById(Long id);

    public void remove(Long id);

    public Budget findOne(String paramName, Object paramValue);

    public Budget findOne(String[] paramNames, Object[] paramValues);

    public int findCountBy(String paramName, String paramValue);

    public List<Budget> selectLike(String param, String value);
}

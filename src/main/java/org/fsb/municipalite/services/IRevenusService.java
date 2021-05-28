package org.fsb.municipalite.services;


import org.fsb.municipalite.entities.Revenus;

import java.util.List;

public interface IRevenusService {
    public Revenus create(Revenus revenus);

    public Revenus update(Revenus revenus);

    public List<Revenus> selectAll();

    public List<Revenus> selectBy(String param, String value);

    public List<Revenus> selectAll(String sortField, String sort);

    public Revenus getById(Long id);

    public void remove(Long id);

    public Revenus findOne(String paramName, Object paramValue);

    public Revenus findOne(String[] paramNames, Object[] paramValues);

    public int findCountBy(String paramName, String paramValue);

    public List<Revenus> selectLike(String param, String value);
}

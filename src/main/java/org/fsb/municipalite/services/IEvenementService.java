package org.fsb.municipalite.services;

import org.fsb.municipalite.entities.Evenement;

import java.util.List;

public interface IEvenementService {

    public Evenement create(Evenement evenement);

    public Evenement update(Evenement evenement);

    public List<Evenement> selectAll();

    public List<Evenement> selectBy(String param, String value);

    public List<Evenement> selectAll(String sortField, String sort);

    public Evenement getById(Long id);

    public void remove(Long id);

    public Evenement findOne(String paramName, Object paramValue);

    public Evenement findOne(String[] paramNames, Object[] paramValues);

    public int findCountBy(String paramName, String paramValue);

    public List<Evenement> selectLike(String param, String value);
}

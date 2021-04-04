package org.fsb.municipalite.services;

import org.fsb.municipalite.entities.Projet;

import java.util.List;

public interface IProjetService {

    public Projet create(Projet projet);

    public Projet getById(Long id);

    public Projet update(Projet projet);

    public List<Projet> selectAll();

    public List<Projet> selectBy(String param, String value);

    public List<Projet> selectAll(String sortField, String sort);

    public void remove(Long id);

    public Projet findOne(String paramName, Object paramValue);

    public Projet findOne(String[] paramNames, Object[] paramValues);

    public int findCountBy(String paramName, String paramValue);
}

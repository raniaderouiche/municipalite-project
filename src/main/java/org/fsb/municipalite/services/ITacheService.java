package org.fsb.municipalite.services;

import org.fsb.municipalite.entities.Tache;

import java.util.List;

public interface ITacheService {
    public Tache create(Tache tache);

    public Tache getById(Long id);

    public Tache update(Tache tache);

    public List<Tache> selectAll();

    public List<Tache> selectBy(String param, String value);

    public List<Tache> selectAll(String sortField, String sort);

    public void remove(Long id);

    public Tache findOne(String paramName, Object paramValue);

    public Tache findOne(String[] paramNames, Object[] paramValues);

    public int findCountBy(String paramName, String paramValue);

    List<Tache> selectLike(String param, String value);
}

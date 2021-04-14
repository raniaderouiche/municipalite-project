package org.fsb.municipalite.services;

import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.entities.Materiel;
import java.util.List;

public interface IMaterielService {

    public Materiel create(Materiel materiel);

    public Materiel update(Materiel materiel);

    public List<Materiel> selectAll();

    public List<Materiel> selectBy(String param, String value);

    public List<Materiel> selectAll(String sortField, String sort);

    public Materiel getById(Long id);

    public void remove(Long id);

    public Materiel findOne(String paramName, Object paramValue);

    public Materiel findOne(String[] paramNames, Object[] paramValues);

    public int findCountBy(String paramName, String paramValue);

    public List<Materiel> selectLike(String param, String value);
}

package org.fsb.municipalite.services;
import org.fsb.municipalite.entities.Municipalite;

import java.util.List;

public interface IMunicipaliteService {

    public Municipalite create(Municipalite municipalite);

    public Municipalite update(Municipalite municipalite);

    public List<Municipalite> selectAll();

    public Municipalite getById(Long id);

    public void remove(Long id);

    public Municipalite findOne(String paramName, Object paramValue);

    public Municipalite findOne(String[] paramNames, Object[] paramValues);


}

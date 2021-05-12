package org.fsb.municipalite.services;

import org.fsb.municipalite.entities.Depenses;

import java.util.List;


public interface IDepensesServices {

    public Depenses create(Depenses depenses);

    public Depenses update(Depenses depenses);

    public List<Depenses> selectAll();

    public void remove(Long id);

    public List<Depenses> selectBy(String param, String value);

    public Depenses getById(Long id);

    public List<Depenses> selectLike(String param, String value);
}
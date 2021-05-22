package org.fsb.municipalite.services.impl;

import org.fsb.municipalite.dao.IProjetDao;
import org.fsb.municipalite.dao.impl.ProjetDaoImpl;
import org.fsb.municipalite.entities.Materiel;
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.services.IProjetService;

import java.util.List;

public class ProjetServiceImpl implements IProjetService {
     IProjetDao projetDao;

    public ProjetServiceImpl() {
        this.projetDao = new ProjetDaoImpl();
    }

    @Override
    public Projet create(Projet projet) {
        return projetDao.save(projet);
    }

    @Override
    public Projet getById(Long id) {
        return projetDao.getById(id);
    }

    @Override
    public Projet update(Projet projet) {
        return projetDao.update(projet);
    }

    @Override
    public List<Projet> selectAll() {
        return projetDao.selectAll();
    }

    @Override
    public List<Projet> selectBy(String param, String value) {
        return projetDao.selectBy(param,value);
    }

    @Override
    public List<Projet> selectAll(String sortField, String sort) {
        return projetDao.selectAll();
    }

    @Override
    public void remove(Long id) {
        this.projetDao.remove(id);
    }

    @Override
    public Projet findOne(String paramName, Object paramValue) {
        return projetDao.findOne(paramName,paramValue);
    }

    @Override
    public Projet findOne(String[] paramNames, Object[] paramValues) {
        return projetDao.findOne(paramNames,paramValues);
    }

    @Override
    public int findCountBy(String paramName, String paramValue) {
        return projetDao.findCountBy(paramName,paramValue);
    }

    public List<Projet> selectLike(String param, String value){
        return projetDao.selectLike(param,value);
    }
}

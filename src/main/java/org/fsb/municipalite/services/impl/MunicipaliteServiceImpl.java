package org.fsb.municipalite.services.impl;

import org.fsb.municipalite.dao.impl.MunicipaliteDaoImpl;
import org.fsb.municipalite.entities.Municipalite;
import org.fsb.municipalite.services.IMunicipaliteService;

import java.util.List;

public class MunicipaliteServiceImpl implements IMunicipaliteService {

    MunicipaliteDaoImpl municipaliteDao;

    public MunicipaliteServiceImpl() {
        this.municipaliteDao = new MunicipaliteDaoImpl();
    }

    @Override
    public Municipalite create(Municipalite municipalite) {
        return municipaliteDao.save(municipalite);
    }

    @Override
    public Municipalite update(Municipalite municipalite) {
        return municipaliteDao.update(municipalite);
    }

    @Override
    public List<Municipalite> selectAll() {
        return municipaliteDao.selectAll();
    }

    @Override
    public Municipalite getById(Long id) {
        return municipaliteDao.getById(id);
    }

    @Override
    public void remove(Long id) {
        municipaliteDao.remove(id);
    }

    @Override
    public Municipalite findOne(String paramName, Object paramValue) {
        return municipaliteDao.findOne(paramName, paramValue);
    }

    @Override
    public Municipalite findOne(String[] paramNames, Object[] paramValues) {
        return municipaliteDao.findOne(paramNames, paramValues);
    }
}

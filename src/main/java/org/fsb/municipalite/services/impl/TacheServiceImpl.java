package org.fsb.municipalite.services.impl;
import org.fsb.municipalite.dao.ITacheDao;
import org.fsb.municipalite.dao.impl.TacheDaoImpl;
import org.fsb.municipalite.entities.Tache;
import org.fsb.municipalite.services.ITacheService;
import java.util.List;

public class TacheServiceImpl implements ITacheService {

    private ITacheDao tacheDao;

    public TacheServiceImpl() {
        this.tacheDao = new TacheDaoImpl();
    }

    @Override
    public Tache create(Tache tache) {
        return tacheDao.save(tache);
    }

    @Override
    public Tache getById(Long id) {
        return tacheDao.getById(id);
    }

    @Override
    public Tache update(Tache tache) {
        return tacheDao.update(tache);
    }

    @Override
    public List<Tache> selectAll() {
        return tacheDao.selectAll();
    }

    @Override
    public List<Tache> selectBy(String param, String value) {
        return tacheDao.selectBy(param,value);
    }

    @Override
    public List<Tache> selectAll(String sortField, String sort) {
        return tacheDao.selectAll();
    }

    @Override
    public void remove(Long id) {
        this.tacheDao.remove(id);
    }

    @Override
    public Tache findOne(String paramName, Object paramValue) {
        return tacheDao.findOne(paramName,paramValue);
    }

    @Override
    public Tache findOne(String[] paramNames, Object[] paramValues) {
        return tacheDao.findOne(paramNames,paramValues);
    }

    @Override
    public int findCountBy(String paramName, String paramValue) {
        return tacheDao.findCountBy(paramName,paramValue);
    }
    @Override
    public List<Tache> selectLike(String param, String value) {
        return tacheDao.selectLike(param,value);
    }
}

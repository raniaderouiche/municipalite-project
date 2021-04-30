package org.fsb.municipalite.services.impl;

import java.util.List;

import org.fsb.municipalite.dao.impl.AutorisationDaoImpl;
import org.fsb.municipalite.entities.Autorisation;
import org.fsb.municipalite.services.IAutorisationServices;

public class AutorisationServiceImpl implements IAutorisationServices{

	private AutorisationDaoImpl autorisationDao;
	
	public AutorisationServiceImpl() {
		this.autorisationDao = new AutorisationDaoImpl();
	}
	
	@Override
	public Autorisation create(Autorisation autorisation) {
		return autorisationDao.save(autorisation);
	}

	@Override
	public Autorisation update(Autorisation autorisation) {
		return autorisationDao.update(autorisation);
	}

	@Override
    public void remove(Long id) {
		autorisationDao.remove(id);
    }
	
	@Override
	public List<Autorisation> selectBy(String param, String value) {
		return autorisationDao.selectBy(param,value);
	}

	@Override
	public List<Autorisation> selectAll() {
		return autorisationDao.selectAll();
	}
	@Override
    public Autorisation getById(Long id) {
        return autorisationDao.getById(id);
    }

	@Override
	public List<Autorisation> selectLike(String param, String value) {
		return autorisationDao.selectLike(param,value);
	}


}

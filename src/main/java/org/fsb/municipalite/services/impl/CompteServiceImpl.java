package org.fsb.municipalite.services.impl;

import java.util.List;

import org.fsb.municipalite.dao.impl.CompteDaoImpl;
import org.fsb.municipalite.entities.Compte;
import org.fsb.municipalite.services.ICompteServices;

public class CompteServiceImpl implements ICompteServices{

	private CompteDaoImpl compteDao;
	
	public CompteServiceImpl() {
		this.compteDao = new CompteDaoImpl();
	}
	@Override
	public Compte create(Compte compte) {
		return compteDao.save(compte);
	}

	@Override
	public Compte update(Compte compte) {
		return compteDao.update(compte);
	}

	@Override
	public List<Compte> selectAll() {
		return compteDao.selectAll();
	}

	@Override
	public void remove(Long id) {
		compteDao.remove(id);		
	}

	@Override
	public List<Compte> selectBy(String param, String value) {
		return compteDao.selectBy(param, value);
	}

	@Override
	public Compte getById(Long id) {
		return compteDao.getById(id);
	}

	@Override
	public List<Compte> selectLike(String param, String value) {
		return compteDao.selectLike(param, value);
	}
	@Override
	public List<Compte> selectAllInColumn(String param) {
		return compteDao.selectAllInColumn(param);
	}

}

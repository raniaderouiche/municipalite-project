package org.fsb.municipalite.services.impl;

import org.fsb.municipalite.dao.IProjetDao;
import org.fsb.municipalite.dao.impl.ProjetDaoImpl;
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.services.IProjetService;

public class ProjetServiceImpl implements IProjetService {
    private IProjetDao projetDao;

    public ProjetServiceImpl() {
        this.projetDao = new ProjetDaoImpl();
    }

    @Override
    public Projet create(Projet projet) {
        return projetDao.save(projet);
    }
}

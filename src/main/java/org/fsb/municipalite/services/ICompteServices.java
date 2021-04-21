package org.fsb.municipalite.services;

import java.util.List;

import org.fsb.municipalite.entities.Compte;

public interface ICompteServices {
	public Compte create(Compte compte);

    public Compte update(Compte compte);
    
    public List<Compte> selectAll();
    
    public void remove(Long id);
    
    public List<Compte> selectBy(String param, String value);
    
    public Compte getById(Long id);

    public List<Compte> selectLike(String param, String value);
    
    public List<Compte> selectAllInColumn(String param);

    
}

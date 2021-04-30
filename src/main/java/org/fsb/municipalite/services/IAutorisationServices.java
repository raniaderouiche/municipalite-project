package org.fsb.municipalite.services;

import java.util.List;
import org.fsb.municipalite.entities.Autorisation;


public interface IAutorisationServices {

	public Autorisation create(Autorisation autorisation);

    public Autorisation update(Autorisation autorisation);
    
    public List<Autorisation> selectAll();
    
    public void remove(Long id);
    
    public List<Autorisation> selectBy(String param, String value);
    
    public Autorisation getById(Long id);

    public List<Autorisation> selectLike(String param, String value);
}

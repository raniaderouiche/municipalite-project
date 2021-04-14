package org.fsb.municipalite.services;

import java.util.List;
import org.fsb.municipalite.entities.Complaint;


public interface IComplaintServices {

	public Complaint create(Complaint complaint);

    public Complaint update(Complaint complaint);
    
    public List<Complaint> selectAll();
    
    public void remove(Long id);
    
    public List<Complaint> selectBy(String param, String value);
    
    public Complaint getById(Long id);

    public List<Complaint> selectLike(String param, String value);
}

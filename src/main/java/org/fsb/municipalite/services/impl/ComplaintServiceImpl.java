package org.fsb.municipalite.services.impl;

import java.util.List;

import org.fsb.municipalite.dao.impl.ComplaintDaoImpl;
import org.fsb.municipalite.entities.Complaint;
import org.fsb.municipalite.services.IComplaintServices;

public class ComplaintServiceImpl implements IComplaintServices{

	 ComplaintDaoImpl complaintDao;
	
	public ComplaintServiceImpl() {
		this.complaintDao = new ComplaintDaoImpl();
	}
	
	@Override
	public Complaint create(Complaint complaint) {
		return complaintDao.save(complaint);
	}

	@Override
	public Complaint update(Complaint complaint) {
		return complaintDao.update(complaint);
	}

	@Override
    public void remove(Long id) {
        complaintDao.remove(id);
    }
	
	@Override
	public List<Complaint> selectBy(String param, String value) {
		return complaintDao.selectBy(param,value);
	}

	@Override
	public List<Complaint> selectAll() {
		return complaintDao.selectAll();
	}
	@Override
    public Complaint getById(Long id) {
        return complaintDao.getById(id);
    }

	@Override
	public List<Complaint> selectLike(String param, String value) {
		return complaintDao.selectLike(param,value);
	}


}

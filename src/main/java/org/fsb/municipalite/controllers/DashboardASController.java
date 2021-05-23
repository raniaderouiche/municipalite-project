package org.fsb.municipalite.controllers;

import java.net.URL; 
import java.util.List;
import java.util.ResourceBundle;

import org.fsb.municipalite.entities.Complaint;
import org.fsb.municipalite.entities.Autorisation;
import org.fsb.municipalite.services.impl.ComplaintServiceImpl;
import org.fsb.municipalite.services.impl.AutorisationServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class DashboardASController implements Initializable{
	
	@FXML
	Label complaintCount;
	@FXML
	Label authorizationCount;
	@FXML
	PieChart authorizationChart;
	@FXML 
	PieChart complaintChart;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ComplaintServiceImpl complaintService = new ComplaintServiceImpl();
		AutorisationServiceImpl autorisationService = new AutorisationServiceImpl();


		//a3melelna methoud bel count fel bd traj3lna int fel emp wel equipe
		List<Complaint> listC = complaintService.selectAll();
		List<Autorisation> listAut = autorisationService.selectAll();
		
		complaintCount.setText(listC.size()+"");
		authorizationCount.setText(listAut.size()+"");
		complaintLoad(listC);
		autorisationLoad(listAut);
	}
	
	public void complaintLoad(List<Complaint> list) {
		
		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
		
    	int processed_count = 0;
    	int unprocessed_count = 0;
    	
    	for(Complaint c : list) {
    		if(c.getEtat().equals(Complaint.Etat.processed)) processed_count++;
    		if(c.getEtat().equals(Complaint.Etat.unprocessed)) unprocessed_count++;
    	}
    	
    	pie.add(new PieChart.Data("Processed", processed_count));
    	pie.add(new PieChart.Data("Unprocessed", unprocessed_count));
    	complaintChart.setData(pie);
	}
	
	public void autorisationLoad(List<Autorisation> list) {
		
		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
		
    	int processed_count = 0;
    	int unprocessed_count = 0;
    	
    	for(Autorisation aut : list) {
    		if(aut.getEtat().equals(Autorisation.Etat.processed)) processed_count++;
    		if(aut.getEtat().equals(Autorisation.Etat.unprocessed)) unprocessed_count++;
    	}
    	
    	pie.add(new PieChart.Data("Processed", processed_count));
    	pie.add(new PieChart.Data("Unprocessed", unprocessed_count));
    	authorizationChart.setData(pie);
	}
}

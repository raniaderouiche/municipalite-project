package org.fsb.municipalite.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.fsb.municipalite.entities.Materiel;
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.services.impl.MaterielServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class DashboardGController implements Initializable{
	@FXML
	private Label toolsCount;
	@FXML
	private Label employeeCount;
	@FXML
	private PieChart toolsChart;
	@FXML 
	private PieChart toolsProjectsChart;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		MaterielServiceImpl toolsService = new MaterielServiceImpl();
		

		//a3melelna methoud bel count fel bd traj3lna int fel emp wel equipe
		List<Materiel> listM = toolsService.selectAll();
		
		toolsCount.setText(listM.size()+"");
		toolsStatusLoad(listM);
		toolsProjectsLoad(listM);
	}
	
	public void toolsStatusLoad(List<Materiel> list) {
		
		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
		
    	int available = 0;
    	int unavailable = 0;
    	int outOfOrder= 0;
    	
    	for(Materiel m : list) {
    		if(m.getEtat().equals(Materiel.Etat.disponible)) available++;
    		if(m.getEtat().equals(Materiel.Etat.occupe)) unavailable++;
    		if(m.getEtat().equals(Materiel.Etat.enPanne)) outOfOrder++;
    	}
    	
    	pie.add(new PieChart.Data("available", available));
    	pie.add(new PieChart.Data("unavailable", unavailable));
    	pie.add(new PieChart.Data("out of order", outOfOrder));
  
    	toolsChart.setData(pie);
	}
	public void toolsProjectsLoad(List<Materiel> list) {
		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
		Map<Projet,Long> mp = new HashMap();
		
		int notUsed = 0;
		
		for(Materiel mt : list) {
			if(mt.getProjet()==null) notUsed++;
			else {
				if(mp.containsKey(mt.getProjet().getId())) {
					mp.replace(mt.getProjet(),mp.get(mt.getProjet())+1);
				}
				if(!mp.containsKey(mt.getProjet().getId())) 
					mp.put(mt.getProjet(),(long)1);
			}
		}
		
		pie.add(new PieChart.Data("notUsed", notUsed));
		for(Projet p : mp.keySet()) {
			pie.add(new PieChart.Data(p.getName(), mp.get(p)));
		}
	
		toolsProjectsChart.setData(pie);
	}	
}

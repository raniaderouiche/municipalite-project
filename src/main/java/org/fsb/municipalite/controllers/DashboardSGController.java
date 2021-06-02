package org.fsb.municipalite.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.fsb.municipalite.entities.Evenement;
import org.fsb.municipalite.services.impl.EvenementServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class DashboardSGController implements Initializable{
	@FXML
	private Label eventCount;
	@FXML
	private Label budgetCount;
	@FXML
	private PieChart eventChart;
	@FXML 
	private PieChart eventRegionChart;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		EvenementServiceImpl eventService = new EvenementServiceImpl();
		

		//a3melelna methoud bel count fel bd traj3lna int fel emp wel equipe
		List<Evenement> listE = eventService.selectAll();
		
		eventCount.setText(listE.size()+"");
		totalBudget(listE);
		eventBudgetLoad(listE);
		eventPlaceLoad(listE);
	}
	
	public void eventBudgetLoad(List<Evenement> list) {
		
		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
		Map<Evenement,Long> mp = new HashMap();
		
		for(Evenement e : list) {
			if(mp.containsKey(e)) {
				mp.replace(e,mp.get(e.getId())+e.getBudget());
			}
			if(!mp.containsKey(e)) 
				mp.put(e,e.getBudget());
		}
		
		for(Evenement e : mp.keySet()) {
			pie.add(new PieChart.Data(e.getNom(), mp.get(e)));
		}
		eventChart.setData(pie);	
	}
	
	public void totalBudget(List<Evenement> list) {
		Long b=(long)0;
		
		for(Evenement e : list) {
			b+=e.getBudget();
		}
		budgetCount.setText(Long.toString(b));
	}
	
	public void eventPlaceLoad(List<Evenement> list) {
		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
		Map<String,Long> mp = new HashMap();
		
		for(Evenement e : list) {
			if(mp.containsKey(e.getLieu())) {
				mp.replace(e.getLieu(),mp.get(e.getLieu())+1);
			}
			if(!mp.containsKey(e.getLieu())) 
				mp.put(e.getLieu(),(long)1);
		}
		
		for(String e : mp.keySet()) {
			pie.add(new PieChart.Data(e, mp.get(e)));
		}
		eventRegionChart.setData(pie);
	}	
}

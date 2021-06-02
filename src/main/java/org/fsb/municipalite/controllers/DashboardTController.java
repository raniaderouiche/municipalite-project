package org.fsb.municipalite.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.entities.Tache;
import org.fsb.municipalite.services.impl.EmployeeServiceImpl;
import org.fsb.municipalite.services.impl.EquipeServiceImpl;
import org.fsb.municipalite.services.impl.ProjetServiceImpl;
import org.fsb.municipalite.services.impl.TacheServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class DashboardTController implements Initializable{
	@FXML
	private Label projectCount;
	@FXML
	private Label teamCount;
	@FXML
	private PieChart projectChart;
	@FXML 
	private PieChart projectTeamChart;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		EquipeServiceImpl equipeService = new EquipeServiceImpl();
		ProjetServiceImpl ProjetService = new ProjetServiceImpl();


		List<Equipe> listE = equipeService.selectAll();
		List<Projet> listP = ProjetService.selectAll();
		
		projectCount.setText(listP.size()+"");
		teamCount.setText(listE.size()+"");
		projectLoad(listP);
		e_t_Load(listP);
	}
	
	public void projectLoad(List<Projet> list) {
		
		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
		
    	int done_count = 0;
    	int in_progress_count = 0;
    	
    	for(Projet p : list) {
    		if(p.getEtat().equals(Projet.Etat.Finished)) done_count++;
    		if(p.getEtat().equals(Projet.Etat.Unfinished)) in_progress_count++;
    	}
    	
    	pie.add(new PieChart.Data("done", done_count));
    	pie.add(new PieChart.Data("inProgress", in_progress_count));
    	projectChart.setData(pie);
	}
	
	public void e_t_Load(List<Projet> list) {
		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
		
		int withTeam = 0;
    	int withoutTeam = 0;
		
    	for(Projet p : list) {
    		if(p.getEquipe()!=null) withTeam++;
    		if(p.getEquipe()==null) withoutTeam++;
    	}
    	
    	pie.add(new PieChart.Data("affeted to a Team", withTeam));
    	pie.add(new PieChart.Data("not affected to a Team", withoutTeam));
    	projectTeamChart.setData(pie);   	
	}
	
}

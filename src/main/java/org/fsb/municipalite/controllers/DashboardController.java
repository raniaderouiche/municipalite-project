package org.fsb.municipalite.controllers;

import java.net.URL; 
import java.util.List;
import java.util.ResourceBundle;

import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.entities.Materiel;
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.entities.Tache;
import org.fsb.municipalite.entities.Materiel.Etat;
import org.fsb.municipalite.services.impl.EmployeeServiceImpl;
import org.fsb.municipalite.services.impl.EquipeServiceImpl;
import org.fsb.municipalite.services.impl.MaterielServiceImpl;
import org.fsb.municipalite.services.impl.ProjetServiceImpl;
import org.fsb.municipalite.services.impl.TacheServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class DashboardController implements Initializable{

    @FXML
    private Label empCount;

    @FXML
    private Label teamCount;
    
    @FXML
    private PieChart projectsChart;
    
    @FXML
    private PieChart tasksChart;

    @FXML
    private PieChart toolsChart;
   
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
		EquipeServiceImpl teamService = new EquipeServiceImpl();
		ProjetServiceImpl projectService = new ProjetServiceImpl();
		MaterielServiceImpl toolService = new MaterielServiceImpl();
		TacheServiceImpl taksService = new TacheServiceImpl();
		
		
		//a3melelna methoud bel count fel bd traj3lna int fel emp wel equipe
		List<Employee> listE = employeeService.selectAll();
		List<Equipe> listT = teamService.selectAll();
		
		List<Projet> listP = projectService.selectAll();
		List<Materiel> listM = toolService.selectAll();
		List<Tache> listTA = taksService.selectAll();

		
		empCount.setText(listE.size()+"");
		teamCount.setText(listT.size()+"");
		projectsLoad(listP);
		toolsLoad(listM);
		tasksLoad(listTA);
	}
	
	public void projectsLoad(List<Projet> list) {
		
		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
		
    	int finished_count = 0;
    	int unfinished_count = 0;
    	
    	for(Projet p : list) {
    		if(p.getEtat().equals(Projet.Etat.Finished)) finished_count++;
    		if(p.getEtat().equals(Projet.Etat.Unfinished)) unfinished_count++;
    	}
    	
    	pie.add(new PieChart.Data("Finshed", finished_count));
    	pie.add(new PieChart.Data("Unfinished", unfinished_count));
    	projectsChart.setData(pie);
	}
	public void toolsLoad(List<Materiel> list) {

		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
		
		int available_count = 0;
		int unavailabe_count = 0;
		int ooo_count = 0;

		for(Materiel m : list) {
			if(m.getEtat().equals(Etat.disponible)) available_count++;
			if(m.getEtat().equals(Etat.occupe)) unavailabe_count++;
			if(m.getEtat().equals(Etat.enPanne)) ooo_count++;
		}

		pie.add(new PieChart.Data("Available", available_count));
		pie.add(new PieChart.Data("Unavailable", unavailabe_count));
		pie.add(new PieChart.Data("Out of order", ooo_count));
		toolsChart.setData(pie);
	}
	public void tasksLoad(List<Tache> list) {

		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
		
		int done_count = 0;
		int inProgress_count = 0;

		for(Tache t : list) {
			if(t.getEtat().equals(Tache.Etat.done)) done_count++;
			if(t.getEtat().equals(Tache.Etat.inProgress)) inProgress_count++;
			
		}

		pie.add(new PieChart.Data("Done", done_count));
		pie.add(new PieChart.Data("In Progress", inProgress_count));
		tasksChart.setData(pie);
	}

}


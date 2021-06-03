package org.fsb.municipalite.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.entities.Tache;
import org.fsb.municipalite.services.impl.EmployeeServiceImpl;
import org.fsb.municipalite.services.impl.EquipeServiceImpl;
import org.fsb.municipalite.services.impl.TacheServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class DashboardCP implements Initializable{
	@FXML
	private Label empCount;
	@FXML 
	private Label teamCount;
	@FXML
	private Label taskCount;
	@FXML 
	private PieChart employeeTeamChart;
	@FXML 
	private PieChart employeeTaskChart;
	
	@FXML
	private PieChart taskChart;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		TacheServiceImpl tacheService = new TacheServiceImpl();
		EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
		EquipeServiceImpl equipeService = new EquipeServiceImpl();
		
		//a3melelna methoud bel count fel bd traj3lna int fel emp wel equipe
		List<Tache> listT = tacheService.selectAll();
		List<Employee> listE = employeeService.selectAll();
		List<Equipe> listEQ = equipeService.selectAll();
		
		taskCount.setText(listT.size()+"");
		empCount.setText(listE.size()+"");
		teamCount.setText(listEQ.size()+"");
		taskLoad(listT);
		e_t_Load(listT);
		equipeEmployeeLoad(listE);
	}
	
	public void taskLoad(List<Tache> list) {
		
		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
		
    	int done_count = 0;
    	int in_progress_count = 0;
    	
    	for(Tache t : list) {
    		if(t.getEtat().equals(Tache.Etat.done)) done_count++;
    		if(t.getEtat().equals(Tache.Etat.inProgress)) in_progress_count++;
    	}
    	
    	pie.add(new PieChart.Data("done", done_count));
    	pie.add(new PieChart.Data("inProgress", in_progress_count));
    	taskChart.setData(pie);
	}
	
	public void e_t_Load(List<Tache> list) {
		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
		
		int withEmployee = 0;
    	int withoutEmployee = 0;
		
    	for(Tache t : list) {
    		if(t.getEmployeeId()!=null) withEmployee++;
    		if(t.getEmployeeId()==null) withoutEmployee++;
    	}
    	
    	pie.add(new PieChart.Data("affeted to an employee", withEmployee));
    	pie.add(new PieChart.Data("not affected to an employee", withoutEmployee));
    	employeeTaskChart.setData(pie);   	
	}
	
	public void equipeEmployeeLoad(List<Employee> list) {
		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
		
		int inTeam = 0;
    	int withoutTeam = 0;
		
    	for(Employee e : list) {
    		if(e.getEquipe()!=null) inTeam++;
    		if(e.getEquipe()==null) withoutTeam++;
    	}
    	
    	pie.add(new PieChart.Data("belong to a team", inTeam));
    	pie.add(new PieChart.Data("doesn't belong to a team", withoutTeam));
    	employeeTeamChart.setData(pie);   	
	}
	

}

package org.fsb.municipalite.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.entities.Tache;
import org.fsb.municipalite.services.impl.EmployeeServiceImpl;
import org.fsb.municipalite.services.impl.EquipeServiceImpl;
import org.fsb.municipalite.services.impl.TacheServiceImpl;

public class TacheDialogController implements Initializable{
	@FXML
    TextField name;

    @FXML
    DatePicker DD;

    @FXML
    RadioButton Done;

    @FXML
    ToggleGroup statusGroup;

    @FXML
    RadioButton InProg;

    @FXML
    ChoiceBox employeeChoice;
    
    @FXML
    Label inv_name;

    @FXML
    Label inv_date;
    
    ObservableList emps =  FXCollections.observableArrayList();
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		setChoiceBox();
		
	}
    
    public void setChoiceBox(){
        EmployeeServiceImpl eService = new EmployeeServiceImpl();
        List<Employee> list = eService.selectAll();
        for(Employee e : list){
            emps.add(e.getId() + ", " + e.getNom());
        }
        employeeChoice.setItems(emps);
    }
    
    
    public void setCurrentTache(Tache t) {
    	t.setName(name.getText());
    	EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        Employee emp = employeeService.getById(Long.parseLong(employeeChoice.getValue().toString().split(",")[0]));
    	t.setEmployee(emp);
    	t.setDueDate(DD.getValue());
    	if(Done.isSelected()) {
    		t.setEtat(Tache.Etat.done);
    	}
    	if(InProg.isSelected()) {
    		t.setEtat(Tache.Etat.inProgress);
    	}
    	
    }
    
    public void setFields(Tache tache){
    	name.setText(tache.getName());
    	EmployeeServiceImpl eService = new EmployeeServiceImpl();
    	Employee e = eService.getById(tache.getEmployeeId());
    	employeeChoice.setValue(e.getId() + ", " + e.getNom());
    	DD.setValue(tache.getDueDate());
    	if(tache.getEtat().equals(Tache.Etat.done))  Done.setSelected(true);
    	else InProg.setSelected(true);
    		
    }

	


}

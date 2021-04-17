package org.fsb.municipalite.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.services.impl.EmployeeServiceImpl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EquipeAddController implements Initializable {
    @FXML
    TextField name;
    @FXML
    ChoiceBox leader;
    @FXML
    Label inv_name;
    ObservableList emps =  FXCollections.observableArrayList();



    public void setChoiceBox(){
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        List<Employee> list = employeeService.selectAll();
        for(Employee e : list){
            emps.add(e.getId() + ", " + e.getNom());
        }
        leader.setItems(emps);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChoiceBox();
        leader.setValue("Choose a leader");
    }
}

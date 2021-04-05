package org.fsb.municipalite.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.services.impl.EmployeeServiceImpl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class EquipeUpdateController implements Initializable {

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    ChoiceBox leader;
    ObservableList emps =  FXCollections.observableArrayList();

    public void setCurrentEquipe(Equipe equipe){
        equipe.setNom(name.getText());
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        Employee emp = employeeService.getById(Long.parseLong(leader.getValue().toString().split(",")[0]));
        equipe.setIdResponsable(emp.getId());
    }

    public void setEquipeDialogPane(Equipe equipe){
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        Employee e = employeeService.getById(equipe.getIdResponsable());
        leader.setValue(e.getId() + ", " + e.getNom());
        id.setText(equipe.getId() + "");
        name.setText(equipe.getNom());
    }


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
    }
}

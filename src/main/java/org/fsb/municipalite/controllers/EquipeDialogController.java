package org.fsb.municipalite.controllers;

import javafx.collections.FXCollections; 
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.services.impl.EmployeeServiceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class EquipeDialogController implements Initializable {

	@FXML
	Label titleLabel;
    @FXML
    TextField id;
    @FXML
    TextField name;
    @FXML
    Label inv_name;
    @FXML
    ChoiceBox leader;
    @FXML
    ChoiceBox membersList;
    @FXML
    ListView members;
    ObservableList emps =  FXCollections.observableArrayList();
    ObservableList<Employee> selected =  FXCollections.observableArrayList();
    List<Employee> deleteList = new ArrayList<Employee>();

    Equipe currentEquipe;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	setChoiceBox();
    }
    
    public void setCurrentEquipe(Equipe equipe){
        currentEquipe = equipe;
        equipe.setNom(name.getText());
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        Employee emp = employeeService.getById(Long.parseLong(leader.getValue().toString().split(",")[0]));
        equipe.setResponsable(emp);
    }

    public void setEquipeDialogPane(Equipe equipe){
        currentEquipe = equipe;
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        Employee e = employeeService.getById(equipe.getResponsable().getId());
        leader.setValue(e.getId() + ", " + e.getNom());
        id.setText(equipe.getId() + "");
        name.setText(equipe.getNom());
        setMembers(equipe);
    }

    public void setMembers(Equipe equipe){
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        List<Employee> list = employeeService.selectBy("equipe",equipe.getId()+"");
        for(Employee emp : list){
            selected.add(emp);
        }
        members.setItems(selected);
    }

    public void setChoiceBox(){
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        List<Employee> list = employeeService.selectAll();
        for(Employee e : list){
            emps.add(e.getId() + ", " + e.getNom());
        }
        leader.setItems(emps);
        membersList.setItems(emps);
    	leader.setValue("Choose a leader");

    }


    public void addMember(ActionEvent actionEvent) {
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        Employee emp = employeeService.getById(Long.parseLong(membersList.getValue().toString().split(",")[0]));
        selected.add(emp);
        membersList.getSelectionModel().clearSelection();
        members.setItems(selected);

    }

    public void deleteMember(ActionEvent actionEvent) {
        if (members.getSelectionModel().getSelectedItem() != null) {
            //add member to deleted list
            EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
            Employee e = (Employee) members.getSelectionModel().getSelectedItem();
            deleteList.add(e);
            //update listview
            ObservableList<Employee> newList =  FXCollections.observableArrayList();
            for (Employee emp : selected){
                if (!(deleteList.contains(emp))){
                    newList.add(emp);
                }
            }

            members.setItems(newList);

        }
    }



}

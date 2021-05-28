package org.fsb.municipalite.controllers;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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
    ListView<Employee> availMembers;
    @FXML
    ListView<Employee> members;
    ObservableList emps =  FXCollections.observableArrayList();
    ObservableList<Employee> selected =  FXCollections.observableArrayList();
    ObservableList<Employee> avail =  FXCollections.observableArrayList();
    List<Employee> deleteList = new ArrayList<Employee>();


    private EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	setChoiceBox();
    	setAvailMembers();
    }
    
    public void setCurrentEquipe(Equipe equipe){
        equipe.setNom(name.getText());
        //EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        Employee emp = employeeService.getById(Long.parseLong(leader.getValue().toString().split(",")[0]));
        equipe.setResponsable(emp);
    }

    public void setEquipeDialogPane(Equipe equipe){
        Employee e = employeeService.getById(equipe.getResponsable().getId());
        leader.setValue(e.getId() + ", " + e.getNom());
        id.setText(equipe.getId() + "");
        name.setText(equipe.getNom());
        setMembers(equipe);
    }

    public void setMembers(Equipe equipe){
        List<Employee> list = employeeService.selectBy("equipe",equipe.getId()+"");
        for(Employee emp : list){
            selected.add(emp);
        }
        members.setItems(selected);
    }

    public void setAvailMembers(){
        List<Employee> employeeList = employeeService.selectAll();
        for (Employee e : employeeList){
            if(e.getEquipe() == null){
                avail.add(e);
            }
        }
        availMembers.setItems(avail);
    }

    public void setChoiceBox(){
        List<Employee> list = employeeService.selectAll();
        for(Employee e : list){
            emps.add(e.getId() + ", " + e.getNom());
        }
        leader.setItems(emps);
    	leader.setValue("Choose a leader");

    }


    public void addMember(ActionEvent actionEvent) {
        if(availMembers.getSelectionModel().getSelectedItem() != null) {
            Employee e = (Employee) availMembers.getSelectionModel().getSelectedItem();
            selected.add(e);
            avail.remove(e);
            members.setItems(selected);
            availMembers.setItems(avail);
        }
    }

    public void deleteMember(ActionEvent actionEvent) {
        if (members.getSelectionModel().getSelectedItem() != null) {
            Employee e = (Employee) members.getSelectionModel().getSelectedItem();
            deleteList.add(e);
            selected.remove(e);
            avail.add(e);
            members.setItems(selected);
            availMembers.setItems(avail);

        }
    }



}

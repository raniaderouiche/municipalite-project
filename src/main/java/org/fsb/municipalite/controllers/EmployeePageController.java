package org.fsb.municipalite.controllers;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.services.impl.EmployeeServiceImpl;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeePageController {

    @FXML
    TableView tableView;
    TableColumn Id = new TableColumn("ID");
    TableColumn Date = new TableColumn("Date ");
    TableColumn Version = new TableColumn("Version");
    TableColumn Status = new TableColumn("Status");
    TableColumn Name = new TableColumn("Name");
    TableColumn Reference = new TableColumn("Reference");
    TableColumn Projet = new TableColumn("Project ID");
    public ObservableList<Employee> data;
    @FXML
    TextField searchBox;

   /* @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().addAll(Id,Date,Version,Status,Name,Reference,Projet);
        Id.setCellValueFactory(new PropertyValueFactory<Materiel,Long>("id"));
        Date.setCellValueFactory(new PropertyValueFactory<Materiel,LocalDateTime>("createdAt"));
        Version.setCellValueFactory(new PropertyValueFactory<Materiel,Long>("version"));
        Status.setCellValueFactory(new PropertyValueFactory<Materiel,Materiel.Etat>("etat"));
        Name.setCellValueFactory(new PropertyValueFactory<Materiel,String>("nom"));
        Reference.setCellValueFactory(new PropertyValueFactory<Materiel,Long>("reference"));
        Projet.setCellValueFactory(new PropertyValueFactory<Materiel,Projet>("projet_id"));

        MaterielServiceImpl materielService =new MaterielServiceImpl();
        List<Materiel> list = materielService.selectAll();
        data  =  FXCollections.observableArrayList();
        for (Materiel m : list) {
            data.addAll(m);
        }
        tableView.setItems(data);

    }*/

    @FXML
    public void onClickEventRemove(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/interfaces/EmployeePageDelete.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    @FXML TextField firstNameField;
    @FXML TextField lastNameField;
    @FXML TextField cinField;
    @FXML RadioButton man;
    @FXML RadioButton woman;
    @FXML RadioButton single;
    @FXML RadioButton married;    
    @FXML DatePicker birthday;
    @FXML
    public void updateEmployee(ActionEvent event){
        Employee employee;
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        employee =employeeService.getById(Long.parseLong(cinField.getText()));
        System.out.println(employee.getId());
        if ((firstNameField.getText().length()) != 0){
            employee.setPrenom(firstNameField.getText());
        }
        if ((lastNameField.getText().length() != 0)) {
            employee.setNom(lastNameField.getText());
        }
        if (man.isSelected()){
            employee.setSexe("man");
        }
        if (woman.isSelected()){
            employee.setSexe("woman");
        }
        
        if (single.isSelected()){
            employee.setEtatCivil("single");
        }
        
        if (married.isSelected()) {
            employee.setEtatCivil("married");
        }
        //if(birthday.)
        
      employeeService.update(employee);
        firstNameField.clear();
        lastNameField.clear();
       
    }


}

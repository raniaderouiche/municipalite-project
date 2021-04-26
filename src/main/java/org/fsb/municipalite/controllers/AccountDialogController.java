package org.fsb.municipalite.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.fsb.municipalite.entities.Compte;
import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.services.impl.CompteServiceImpl;
import org.fsb.municipalite.services.impl.EmployeeServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AccountDialogController implements Initializable{

    @FXML
    private TextField usernameField;
   
	@FXML
    private Label inv_password;

    @FXML
    private Label inv_username;

    @FXML
	private Label inv_email;

    @FXML
	private TextField emailField;

    @FXML
    private ChoiceBox employeeBox;

    @FXML
    private TextField passwordField;

    @FXML
    private Label titleLabel;
    
    @FXML
    private TextField passwordField2;

    

	@FXML
    private Label inv_password2;

    ObservableList emps =  FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	employeeBox.setValue("Choose an employee");
    	setChoiceBox();
    }
    
    public void setCurrentAccount(Compte compte){
        compte.setUsername(usernameField.getText());
        compte.setPassword(passwordField.getText());
        compte.setEmail(emailField.getText());
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        Employee emp = employeeService.getById(Long.parseLong(employeeBox.getValue().toString().split(",")[0]));
        compte.setEmployee(emp);
    }

    public void setAccountDialogPane(Compte compte){
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        Employee e = employeeService.getById(compte.getEmployee().getId());
        employeeBox.setValue(e.getId() + ", " + e.getNom());
        usernameField.setText(compte.getUsername());
        passwordField.setText(compte.getPassword());
        passwordField2.setText(compte.getPassword());
		emailField.setText(compte.getEmail());
    }


    public void setChoiceBox(){
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        CompteServiceImpl compteService = new CompteServiceImpl();

		List<Employee> listemp = employeeService.selectAll();
		List<String> listID = compteService.selectAllInONEColumn("employee");

		for(Employee e : listemp){
			if(!listID.contains(e.toString()))
				emps.add(e.getId() + ", " + e.getNom());
		}
		employeeBox.setItems(emps);
    }

	public Label getInv_email() {
		return inv_email;
	}

	public void setInv_email(Label inv_email) {
		this.inv_email = inv_email;
	}

	public TextField getEmailField() {
		return emailField;
	}

	public void setEmailField(TextField emailField) {
		this.emailField = emailField;
	}

	public TextField getUsernameField() {
		return usernameField;
	}

	public void setUsernameField(TextField usernameField) {
		this.usernameField = usernameField;
	}

	public Label getInv_password() {
		return inv_password;
	}

	public void setInv_password(Label inv_password) {
		this.inv_password = inv_password;
	}

	public Label getInv_username() {
		return inv_username;
	}

	public void setInv_username(Label inv_username) {
		this.inv_username = inv_username;
	}

	public ChoiceBox getEmployeeBox() {
		return employeeBox;
	}

	public void setEmployeeBox(ChoiceBox employeeBox) {
		this.employeeBox = employeeBox;
	}

	public TextField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(TextField passwordField) {
		this.passwordField = passwordField;
	}

	public Label getTitleLabel() {
		return titleLabel;
	}

	public void setTitleLabel(Label titleLabel) {
		this.titleLabel = titleLabel;
	}

	public ObservableList getEmps() {
		return emps;
	}

	public void setEmps(ObservableList emps) {
		this.emps = emps;
	}

	public TextField getPasswordField2() {
		return passwordField2;
	}

	public void setPasswordField2(TextField passwordField2) {
		this.passwordField2 = passwordField2;
	}

	public Label getInv_password2() {
		return inv_password2;
	}

	public void setInv_password2(Label inv_password2) {
		this.inv_password2 = inv_password2;
	}

}

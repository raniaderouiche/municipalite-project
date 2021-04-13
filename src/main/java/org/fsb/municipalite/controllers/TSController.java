package org.fsb.municipalite.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.services.impl.EmployeeServiceImpl;
import org.fsb.municipalite.services.impl.EquipeServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.services.impl.EmployeeServiceImpl;
import org.fsb.municipalite.services.impl.EquipeServiceImpl;


public class TSController implements Initializable{

	//Staff (employee)
	
	@FXML
	TableView<Employee> staffTable;
	@FXML
	TableColumn<Employee, Long> id_emp;
	@FXML
	TableColumn<Employee, String> nom;

	@FXML
	TableColumn<Employee, String> prenom;
	@FXML
	TableColumn<Employee, String> cin;
	@FXML
	TableColumn<Employee, String> etatCivil;
	@FXML
	TableColumn<Employee, String> sexe;
	@FXML
	TableColumn<Employee, LocalDateTime> dateNaissance;
	@FXML
    TextField empSearchField;

	ObservableList<Employee> EmployeesData;
	
	//Team
	@FXML 
	TableView<Equipe> teamTable;
	@FXML 
	TableColumn<Equipe,Long> id_team;
	@FXML 
	TableColumn<Equipe, String> name;
	@FXML 
	TableColumn<Equipe,Long> leader;

	ObservableList<Equipe> teamdata;
	@FXML TextField teamSearchField;


	@Override
	public void initialize (URL location, ResourceBundle resources){
		//staff
		id_emp.setCellValueFactory(new PropertyValueFactory<Employee, Long>("id"));
		nom.setCellValueFactory(new PropertyValueFactory<Employee, String>("nom"));
		prenom.setCellValueFactory(new PropertyValueFactory<Employee, String>("prenom"));
		cin.setCellValueFactory(new PropertyValueFactory<Employee, String>("cin"));
		etatCivil.setCellValueFactory(new PropertyValueFactory<Employee, String>("etatCivil"));
		sexe.setCellValueFactory(new PropertyValueFactory<Employee, String>("sexe"));
		dateNaissance.setCellValueFactory(new PropertyValueFactory<Employee, LocalDateTime>("dateNaissance"));


		EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
		List<Employee> list = employeeService.selectAll();

		EmployeesData = FXCollections.observableArrayList();
		for (Employee p : list) {
			System.out.println(p);
			EmployeesData.add(p);
		}

		staffTable.setItems(EmployeesData);
		//team
		id_team.setCellValueFactory(new PropertyValueFactory<Equipe, Long>("id"));
		name.setCellValueFactory(new PropertyValueFactory<Equipe, String>("nom"));
		leader.setCellValueFactory(new PropertyValueFactory<Equipe, Long>("idResponsable"));

		EquipeServiceImpl equipeService = new EquipeServiceImpl();
		List<Equipe> equipeList = equipeService.selectAll();
		teamdata = FXCollections.observableArrayList();
		for (Equipe e : equipeList) {
			teamdata.addAll(e);
		}
		teamTable.setItems(teamdata);
	}


	/*
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//team
		id_team.setCellValueFactory(new PropertyValueFactory<Equipe,Long>("id"));
		name.setCellValueFactory(new PropertyValueFactory<Equipe, String>("nom"));
		leader.setCellValueFactory(new PropertyValueFactory<Equipe,Long>("idResponsable"));

		EquipeServiceImpl equipeService = new EquipeServiceImpl();
		List<Equipe> equipeList = equipeService.selectAll();
		teamdata  =  FXCollections.observableArrayList();
		for (Equipe e : equipeList) {
			teamdata.addAll(e);
		}
		teamTable.setItems(teamdata);
	}*/

	//aamlou button pls :)
	public void refreshTeam(){
		teamTable.getItems().clear();
		EquipeServiceImpl equipeService = new EquipeServiceImpl();
		List<Equipe> equipeList = equipeService.selectAll();
		teamdata  =  FXCollections.observableArrayList();
		for (Equipe e : equipeList) {
			teamdata.addAll(e);
		}
		teamTable.setItems(teamdata);

	}
/*
	@FXML
	public void addTeam(MouseEvent mouseEvent) {
		TextField teamSearchField;

		ObservableList<Equipe> teamdata;*/


	
	/*//aamlou button pls :)
	public void refreshTeam(){
		teamTable.getItems().clear();
		EquipeServiceImpl equipeService = new EquipeServiceImpl();
		List<Equipe> equipeList = equipeService.selectAll();
		teamdata  =  FXCollections.observableArrayList();
		for (Equipe e : equipeList) {
			teamdata.addAll(e);
		}
		teamTable.setItems(teamdata);

	}*/

		@FXML
		public void reloadEmp (ActionEvent event){
			EmployeeServiceImpl empService = new EmployeeServiceImpl();
			staffTable.getItems().clear();
			List<Employee> list = empService.selectAll();
			for (Employee p : list) {
				EmployeesData.add(p);
			}
			staffTable.setItems(EmployeesData);
		}

		@FXML
		public void addEmp (ActionEvent event){

			try {
				FXMLLoader f = new FXMLLoader();
				f.setLocation(getClass().getResource("/interfaces/StaffAddPage.fxml"));
				Pane empDialogPane = f.load();

				EmployeeDialogController edc = f.getController();

				Dialog<ButtonType> d = new Dialog<>();
				d.setDialogPane((DialogPane) empDialogPane);
				d.setTitle("add Employee");
				Optional<ButtonType> clickedButton = d.showAndWait();

				if (clickedButton.get() == ButtonType.APPLY) {
					Employee emp = new Employee();
					if (!(edc.nom_field.getText().isEmpty())) {
						emp.setNom(edc.nom_field.getText());
					}
					if (!(edc.prenom_field.getText().isEmpty())) {
						emp.setPrenom(edc.prenom_field.getText());
					}
					if (!(edc.cin_field.getText().isEmpty())) {
						emp.setCin(edc.cin_field.getText());
					}
					emp.setEtatCivil(edc.civilStatusBox.getValue());
					RadioButton selectedRadioButton = (RadioButton) edc.genderGroup.getSelectedToggle();
					emp.setSexe(selectedRadioButton.getText());
					emp.setDateNaissance(edc.dnPicker.getValue().atStartOfDay());
					EmployeeServiceImpl empService = new EmployeeServiceImpl();
					empService.create(emp);
					//refreshEmpList(event);

				}
				reloadEmp(event);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@FXML
		public void updateEmp (ActionEvent event){

			try {
				FXMLLoader f = new FXMLLoader();
				f.setLocation(getClass().getResource("/interfaces/StaffAddPage.fxml"));
				Pane empDialogPane = f.load();

				EmployeeDialogController edc = f.getController();
				if (staffTable.getSelectionModel().getSelectedItem() != null) {

					EmployeeServiceImpl empService = new EmployeeServiceImpl();
					Employee selectedEmp = (Employee) staffTable.getSelectionModel().getSelectedItem();
					Employee emp = empService.getById(selectedEmp.getId());

					edc.setEmpDialogPane(emp);

					Dialog<ButtonType> d = new Dialog<>();
					d.setDialogPane((DialogPane) empDialogPane);
					d.setTitle("Update Employee");
					Optional<ButtonType> clickedButton = d.showAndWait();
					if (clickedButton.get() == ButtonType.APPLY) {
						edc.setCurrentEmployee(emp);
						empService.update(emp);
						//refresh
					}
				}
				reloadEmp(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


		@FXML void deleteEmp (ActionEvent event){

			if (staffTable.getSelectionModel().getSelectedItem() != null) {
				Employee emp = (Employee) staffTable.getSelectionModel().getSelectedItem();
				EmployeeServiceImpl empService = new EmployeeServiceImpl();
				empService.remove(emp.getId());
				reloadEmp(event);
			}
		}

		public boolean isNumeric (String str){
			try {
				Long.parseLong(str);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		public void empSearch (ActionEvent event){
			if (!(empSearchField.getText().isEmpty())) {
				staffTable.getItems().clear();
				EmployeeServiceImpl empService = new EmployeeServiceImpl();
				EmployeesData = FXCollections.observableArrayList();
				if (isNumeric(empSearchField.getText())) {
					List<Employee> list = empService.selectBy("id", empSearchField.getText());
					for (Employee e : list) {
						EmployeesData.add(e);
					}
				} else {
					List<Employee> list = empService.selectBy("nom", "'" + empSearchField.getText() + "'");
					for (Employee e : list) {
						EmployeesData.add(e);
					}
				}
				staffTable.setItems(EmployeesData);
				empSearchField.clear();
			}
		}


		@FXML
		public void addTeam (ActionEvent event){
			try {
				FXMLLoader f = new FXMLLoader();
				f.setLocation(getClass().getResource("/interfaces/EquipeAdd.fxml"));
				Pane equipeDialogPane = f.load();
				EquipeAddController eqac = f.getController();

				Dialog<ButtonType> d = new Dialog<>();
				d.setDialogPane((DialogPane) equipeDialogPane);
				d.setTitle("add team");
				Optional<ButtonType> clickedButton = d.showAndWait();

				if (clickedButton.get() == ButtonType.APPLY) {
					Equipe equipe = new Equipe();
					if (!(eqac.name.getText().isEmpty())) {
						equipe.setNom(eqac.name.getText());
					}
					if (!(eqac.leader.getValue().toString().isEmpty())) {
						EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
						Employee emp = employeeService.getById(Long.parseLong(eqac.leader.getValue().toString().split(",")[0]));
						equipe.setIdResponsable(emp.getId());
					}

					EquipeServiceImpl equipeService = new EquipeServiceImpl();
					equipeService.create(equipe);
					refreshTeam();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}


		@FXML
		public void updateTeam (ActionEvent event){
			try {
				FXMLLoader f = new FXMLLoader();
				f.setLocation(getClass().getResource("/interfaces/EquipeUpdate.fxml"));
				Pane equipeDialogPane = f.load();
				EquipeUpdateController equc = f.getController();

				if (teamTable.getSelectionModel().getSelectedItem() != null) {

					EquipeServiceImpl equipeService = new EquipeServiceImpl();
					Equipe e = (Equipe) teamTable.getSelectionModel().getSelectedItem();
					Equipe equipe = equipeService.getById(e.getId());

					equc.setEquipeDialogPane(equipe);
					Dialog<ButtonType> d = new Dialog<>();
					d.setDialogPane((DialogPane) equipeDialogPane);
					d.setTitle("Update equipe");
					Optional<ButtonType> clickedButton = d.showAndWait();
					if (clickedButton.get() == ButtonType.APPLY) {

						equc.setCurrentEquipe(equipe);
						equipeService.update(equipe);
						refreshTeam();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@FXML
		public void deleteTeam (ActionEvent event){
			if (teamTable.getSelectionModel().getSelectedItem() != null) {
				Equipe equipe = (Equipe) teamTable.getSelectionModel().getSelectedItem();
				EquipeServiceImpl equipeService = new EquipeServiceImpl();
				equipeService.remove(equipe.getId());
				refreshTeam();
			}
		}

		@FXML
		public void searchTeam (ActionEvent event){
			if (!(teamSearchField.getText().isEmpty())) {
				teamTable.getItems().clear();
				EquipeServiceImpl equipeService = new EquipeServiceImpl();
				teamdata = FXCollections.observableArrayList();
				if (isNumeric(teamSearchField.getText())) {
					List<Equipe> list = equipeService.selectBy("id", teamSearchField.getText());
					for (Equipe e : list) {
						teamdata.add(e);
					}
				} else {
					List<Equipe> list = equipeService.selectBy("nom", "'" + teamSearchField.getText() + "'");
					for (Equipe e : list) {
						teamdata.add(e);
					}
				}
				teamTable.setItems(teamdata);
				teamSearchField.clear();
			}
		}

	public void ListenerSearch(String n) {
		EmployeeServiceImpl empService = new EmployeeServiceImpl();
		EmployeesData= FXCollections.observableArrayList();
		List<Employee> list;
		if(!(n.equals(""))) {
			if(isNumeric(n)) {
				list = empService.selectBy("id", n);
				for (Employee e : list) {
					EmployeesData.add(e);
				}
			}
			
			staffTable.setItems(EmployeesData);
		}
	}
	
	

}

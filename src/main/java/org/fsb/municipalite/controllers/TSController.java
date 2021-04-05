package org.fsb.municipalite.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.fsb.municipalite.entities.Employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.services.impl.EmployeeServiceImpl;
import org.fsb.municipalite.services.impl.EquipeServiceImpl;
import org.fsb.municipalite.services.impl.ProjetServiceImpl;

public class TSController implements Initializable{

	//staff
	@FXML
	private TableView<Employee> mainTable;
	@FXML
	private TableColumn<Employee, String> nom;
	@FXML
	private TableColumn<Employee, String> prenom;
	@FXML
	private TableColumn<Employee, String> cin;
	@FXML
	private TableColumn<Employee, String> etatCivil;
	@FXML
	private TableColumn<Employee, String> sexe;
	@FXML
	private TableColumn<LocalDateTime, String> dateNaissance;


	//Team

	@FXML TableView teamTable;
	@FXML TableColumn<Equipe,Long> id_team;
	@FXML TableColumn<Equipe, String> name;
	@FXML TableColumn<Equipe,Long> leader;
	@FXML TextField searchBox;
	
	ObservableList<Equipe> teamdata;

	
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
	}
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

	@FXML
	public void addTeam(MouseEvent mouseEvent) {
		try {
			FXMLLoader f = new FXMLLoader();
			f.setLocation(getClass().getResource("/interfaces/EquipeAdd.fxml"));
			Pane equipeDialogPane = f.load();
			EquipeAddController eqac = f.getController();

			Dialog<ButtonType> d = new Dialog<>();
			d.setDialogPane((DialogPane) equipeDialogPane);
			d.setTitle("add team");
			Optional<ButtonType> clickedButton = d.showAndWait();

			if(clickedButton.get() == ButtonType.APPLY) {
				Equipe equipe = new Equipe();
				if(!(eqac.name.getText().isEmpty())){
					equipe.setNom(eqac.name.getText());
				}
				if(!(eqac.leader.getValue().toString().isEmpty())){
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
	public void update(MouseEvent mouseEvent) {
		try {
			FXMLLoader f = new FXMLLoader();
			f.setLocation(getClass().getResource("/interfaces/EquipeUpdate.fxml"));
			Pane equipeDialogPane = f.load();
			EquipeUpdateController equc = f.getController();

			if(teamTable.getSelectionModel().getSelectedItem() != null) {

				EquipeServiceImpl equipeService = new EquipeServiceImpl();
				Equipe e = (Equipe) teamTable.getSelectionModel().getSelectedItem();
				Equipe equipe = equipeService.getById(e.getId());

				equc.setEquipeDialogPane(equipe);
				Dialog<ButtonType> d = new Dialog<>();
				d.setDialogPane((DialogPane) equipeDialogPane);
				d.setTitle("Update equipe");
				Optional<ButtonType> clickedButton = d.showAndWait();
				if(clickedButton.get() == ButtonType.APPLY) {

					equc.setCurrentEquipe(equipe);
					equipeService.update(equipe);
					refreshTeam();
				}
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void delete(MouseEvent mouseEvent) {
		if(teamTable.getSelectionModel().getSelectedItem() != null){
			Equipe equipe = (Equipe) teamTable.getSelectionModel().getSelectedItem();
			EquipeServiceImpl equipeService = new EquipeServiceImpl();
			equipeService.remove(equipe.getId());
			refreshTeam();
		}
	}
	public boolean isNumeric(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}
	@FXML
	public void searchTeam(ActionEvent event) {
		if(!(searchBox.getText().isEmpty())){
			teamTable.getItems().clear();
			EquipeServiceImpl equipeService = new EquipeServiceImpl();
			teamdata  =  FXCollections.observableArrayList();
			if(isNumeric(searchBox.getText())){
				List<Equipe> list = equipeService.selectBy("id",searchBox.getText());
				for (Equipe e : list) {
					teamdata.add(e);
				}
			}else{
				List<Equipe> list = equipeService.selectBy("nom","'" + searchBox.getText() + "'");
				for (Equipe e : list) {
					teamdata.add(e);
				}
			}
			teamTable.setItems(teamdata);
			searchBox.clear();
		}
	}

	public void addPersonnel(ActionEvent event) {
	}

	public void updatePersonnel(ActionEvent event) {
	}

	public void deletePersonnel(ActionEvent event) {
	}

}

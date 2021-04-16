package org.fsb.municipalite.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.services.impl.EmployeeServiceImpl;
import org.fsb.municipalite.services.impl.EquipeServiceImpl;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class TSController implements Initializable {

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
	TableColumn<Equipe, Long> id_team;
	@FXML
	TableColumn<Equipe, String> name;
	@FXML
	TableColumn<Equipe, Long> leader;

	ObservableList<Equipe> teamdata;
	@FXML
	TextField teamSearchField;


	//define your offsets here
    private double xOffset = 0;
    private double yOffset = 0;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
			EmployeesData.add(p);
		}
		staffTable.setItems(EmployeesData);

		//searchListener
		empSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("textfield changed from " + oldValue + " to " + newValue);
			ListenerSearchEmp(newValue);
		});

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

		//searchListener

		teamSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("textfield changed from " + oldValue + " to " + newValue);
			ListenerSearchTeam(newValue);
		});
	}


	
	public void refreshTeam() {
		teamTable.getItems().clear();
		EquipeServiceImpl equipeService = new EquipeServiceImpl();
		List<Equipe> equipeList = equipeService.selectAll();
		teamdata = FXCollections.observableArrayList();
		for (Equipe e : equipeList) {
			teamdata.addAll(e);
		}
		teamTable.setItems(teamdata);
	}


	public void ListenerSearchTeam(String n){
		EquipeServiceImpl equipeService = new EquipeServiceImpl();
		teamdata = FXCollections.observableArrayList();
		List<Equipe> list;
		if (!(n.equals(""))) {
			if (isNumeric(n)) {
				list = equipeService.selectLike("id", n);
				for (Equipe e : list) {
					teamdata.add(e);
				}
			} else {
				list = equipeService.selectLike("nom", "'" + n + "%'");
				for (Equipe e : list) {
					teamdata.add(e);
				}
			}
			teamTable.setItems(teamdata);
		} else {
			list = equipeService.selectAll();
			for (Equipe e : list) {
				teamdata.add(e);
			}
			teamTable.setItems(teamdata);
		}

	}

	public void ListenerSearchEmp(String n) {
		EmployeeServiceImpl empService = new EmployeeServiceImpl();
		EmployeesData = FXCollections.observableArrayList();
		List<Employee> list;
		if (!(n.equals(""))) {
			if (isNumeric(n)) {
				list = empService.selectLike("id", n);
				for (Employee e : list) {
					EmployeesData.add(e);
				}
			} else {
				list = empService.selectLike("nom", "'" + n + "%'");
				for (Employee e : list) {
					EmployeesData.add(e);
				}
			}
			staffTable.setItems(EmployeesData);
		} else {
			list = empService.selectAll();
			for (Employee e : list) {
				EmployeesData.add(e);
			}
			staffTable.setItems(EmployeesData);
		}
	}


	@FXML
	public void reloadEmp(ActionEvent event) {
		EmployeeServiceImpl empService = new EmployeeServiceImpl();
		staffTable.getItems().clear();
		List<Employee> list = empService.selectAll();
		for (Employee p : list) {
			EmployeesData.add(p);
		}
		staffTable.setItems(EmployeesData);
	}

	@FXML
	public void addEmp(ActionEvent event) {

		try {
			FXMLLoader f = new FXMLLoader();
			f.setLocation(getClass().getResource("/interfaces/StaffAddPage.fxml"));
			Pane empDialogPane = f.load();
			//get the current controller and put it in edc
			EmployeeDialogController edc = f.getController();
			
			Dialog<ButtonType> d = new Dialog<>();
			//this is just for adding an icon to the dialog pane
			Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/img/icon.png"));
			
			d.setDialogPane((DialogPane) empDialogPane);
			d.setTitle("Add Employee");
			d.setResizable(false);
			d.initStyle(StageStyle.UNDECORATED);
			
			//these two are for moving the window with the mouse
			empDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
	           @Override
	           public void handle(MouseEvent event) {
	               xOffset = event.getSceneX();
	               yOffset = event.getSceneY();
	           }
			});
       
            empDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
	           @Override
	           public void handle(MouseEvent event) {
	               d.setX(event.getScreenX() - xOffset);
	               d.setY(event.getScreenY() - yOffset);
	           }
            });
			edc.dnPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
				System.out.println("textfield changed from " + oldValue + " to " + newValue);
				if(newValue != null) {
					if(Integer.parseInt(newValue.toString().substring(0,4))>1998) {
						edc.inv_date.setVisible(true);
					}
					else {
						edc.inv_date.setVisible(false);
					}
				}
				
				
			});
			edc.nom_field.textProperty().addListener((observable, oldValue, newValue) -> {
				//System.out.println("textfield changed from " + oldValue + " to " + newValue);
				
				if(!isAlpha(newValue)) {
					edc.inv_name.setVisible(true);
					//System.out.println(edc.dnPicker.getValue().toString().substring(0,4));
					
				}else
					edc.inv_name.setVisible(false);
			});
			
			edc.prenom_field.textProperty().addListener((observable, oldValue, newValue) -> {
				System.out.println("textfield changed from " + oldValue + " to " + newValue);
				if(!isAlpha(newValue)) {
					edc.inv_last.setVisible(true);
					System.out.println(edc.nom_field.getText());
				}else
					edc.inv_last.setVisible(false);
			});
			
			edc.cin_field.textProperty().addListener((observable, oldValue, newValue) -> {
				System.out.println("textfield changed from " + oldValue + " to " + newValue);
				if(isNumeric(newValue) && newValue.length() == 8) {
					edc.inv_cin.setVisible(false);
				}else
					edc.inv_cin.setVisible(true);
			});
			//to apply css on the dialog pane buttons
			d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
			d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");
			
			//make name field first to be selected
			edc.nom_field.requestFocus();
			
			d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() -> 
											edc.nom_field.getText().isEmpty() || edc.prenom_field.getText().isEmpty() || edc.cin_field.getText().isEmpty() || edc.dnPicker.getValue() == null || 
											edc.cin_field.getText().length() != 8 || !isNumeric(edc.cin_field.getText()) || !isAlpha(edc.nom_field.getText()) || !isAlpha(edc.prenom_field.getText()),
											edc.nom_field.textProperty(), edc.prenom_field.textProperty(), edc.cin_field.textProperty(), edc.dnPicker.valueProperty()));
			
			Optional<ButtonType> clickedButton = d.showAndWait();
			
			if (clickedButton.get() == ButtonType.APPLY) {
				Employee emp = new Employee();
				
				emp.setNom(edc.nom_field.getText());
				emp.setPrenom(edc.prenom_field.getText());
				emp.setCin(edc.cin_field.getText());
				emp.setEtatCivil(edc.civilStatusBox.getValue());
				RadioButton selectedRadioButton = (RadioButton) edc.genderGroup.getSelectedToggle();
				emp.setSexe(selectedRadioButton.getText());
				emp.setDateNaissance(edc.dnPicker.getValue().atStartOfDay());
				EmployeeServiceImpl empService = new EmployeeServiceImpl();
				empService.create(emp);
			}
			reloadEmp(event);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void updateEmp(ActionEvent event) {
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
				edc.titleLabel.setText("Update Employee");
				Dialog<ButtonType> d = new Dialog<>();
				//this is just for adding an icon to the dialog pane
				Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image("/assets/img/icon.png"));
				
				d.setDialogPane((DialogPane) empDialogPane);
				d.setTitle("Update Employee");
				d.setResizable(false);
				d.initStyle(StageStyle.UNDECORATED);
				
				//these two are for moving the window with the mouse
				empDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
		           @Override
		           public void handle(MouseEvent event) {
		               xOffset = event.getSceneX();
		               yOffset = event.getSceneY();
		           }
				});
	       
	            empDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
		           @Override
		           public void handle(MouseEvent event) {
		               d.setX(event.getScreenX() - xOffset);
		               d.setY(event.getScreenY() - yOffset);
		           }
	            });
	            //a bunch of listener for testing the fields
				edc.nom_field.textProperty().addListener((observable, oldValue, newValue) -> {
					
					if(!isAlpha(newValue)) {
						edc.inv_name.setVisible(true);
					}else
						edc.inv_name.setVisible(false);
				});
				
				edc.prenom_field.textProperty().addListener((observable, oldValue, newValue) -> {
					if(!isAlpha(newValue)) {
						edc.inv_last.setVisible(true);
					}else
						edc.inv_last.setVisible(false);
				});
				
				edc.cin_field.textProperty().addListener((observable, oldValue, newValue) -> {
					if(isNumeric(newValue) && newValue.length() == 8) {
						edc.inv_cin.setVisible(false);
					}else
						edc.inv_cin.setVisible(true);
				});
				
				//to apply css on the dialog pane buttons
				d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
				d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");
				
				//make name field first to be selected
				edc.nom_field.requestFocus();
				
				d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() -> 
												edc.nom_field.getText().isEmpty() || edc.prenom_field.getText().isEmpty() || edc.cin_field.getText().isEmpty() || edc.dnPicker.getValue() == null || 
												edc.cin_field.getText().length() != 8 || !isNumeric(edc.cin_field.getText()) || !isAlpha(edc.nom_field.getText()) || !isAlpha(edc.prenom_field.getText()),
												edc.nom_field.textProperty(), edc.prenom_field.textProperty(), edc.cin_field.textProperty(), edc.dnPicker.valueProperty()));
				
				
				Optional<ButtonType> clickedButton = d.showAndWait();
				if (clickedButton.get() == ButtonType.APPLY) {
					edc.setCurrentEmployee(emp);
					empService.update(emp);
				}
			}
			reloadEmp(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@FXML
	void deleteEmp(ActionEvent event) {
		if (staffTable.getSelectionModel().getSelectedItem() != null) {
			//this is just for adding an icon to the dialog pane
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/img/icon.png"));
			alert.setTitle("Delete Employee ?");
			alert.setContentText("Are you Sure to Delete " + staffTable.getSelectionModel().getSelectedItem().getNom() + " ?");
			Optional <ButtonType> action = alert.showAndWait();
			if(action.get() == ButtonType.OK) {
				Employee emp = (Employee) staffTable.getSelectionModel().getSelectedItem();
				EmployeeServiceImpl empService = new EmployeeServiceImpl();
				empService.remove(emp.getId());
				reloadEmp(event);
			}
			
		}
	}

	public boolean isNumeric(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean isAlpha(String name) {
	    return name.matches("[a-zA-Z]+");
	}
	
	@FXML
	public void addTeam(ActionEvent event) {
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
	public void updateTeam(ActionEvent event) {
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
	public void deleteTeam(ActionEvent event) {
		if (teamTable.getSelectionModel().getSelectedItem() != null) {
			Equipe equipe = (Equipe) teamTable.getSelectionModel().getSelectedItem();
			EquipeServiceImpl equipeService = new EquipeServiceImpl();
			equipeService.remove(equipe.getId());
			refreshTeam();
		}
	}


}

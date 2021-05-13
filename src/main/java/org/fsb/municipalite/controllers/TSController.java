package org.fsb.municipalite.controllers;

import java.net.URL; 
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
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
	TableColumn<Employee, LocalDate> dateNaissance;
	@FXML TableColumn<Employee,String> role;
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
	TableColumn<Equipe, String> leader;

	ObservableList<Equipe> teamdata;
	
	@FXML
	TextField teamSearchField;
	

    //define dialog window offsets here
    private double xOffset = 0;
    private double yOffset = 0;
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//staff
		staffTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		id_emp.setCellValueFactory(new PropertyValueFactory<Employee, Long>("id"));
		nom.setCellValueFactory(new PropertyValueFactory<Employee, String>("nom"));
		prenom.setCellValueFactory(new PropertyValueFactory<Employee, String>("prenom"));
		cin.setCellValueFactory(new PropertyValueFactory<Employee, String>("cin"));
		etatCivil.setCellValueFactory(new PropertyValueFactory<Employee, String>("etatCivil"));
		sexe.setCellValueFactory(new PropertyValueFactory<Employee, String>("sexe"));
		dateNaissance.setCellValueFactory(new PropertyValueFactory<Employee, LocalDate>("dateNaissance"));
		role.setCellValueFactory(new PropertyValueFactory<Employee, String>("role"));

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
		teamTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		id_team.setCellValueFactory(new PropertyValueFactory<Equipe, Long>("id"));
		name.setCellValueFactory(new PropertyValueFactory<Equipe, String>("nom"));
		leader.setCellValueFactory(new PropertyValueFactory<Equipe, String>("ResponsableValue"));

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
		empSearchField.clear();
		staffTable.getItems().clear();
		EmployeeServiceImpl empService = new EmployeeServiceImpl();
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

            //datePicker listener
			edc.dnPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
				if(newValue != null) {
					if(newValue.isAfter(LocalDate.now().minusYears(18))) {
						edc.inv_date.setVisible(true);
					}
					else {
						edc.inv_date.setVisible(false);
					}
				}
			});

			//first name field listener
			edc.nom_field.textProperty().addListener((observable, oldValue, newValue) -> {
				if(!isAlpha(newValue)) {
					edc.inv_name.setVisible(true);
				}else
					edc.inv_name.setVisible(false);
			});

			//last name listener
			edc.prenom_field.textProperty().addListener((observable, oldValue, newValue) -> {
				if(!isAlpha(newValue)) {
					edc.inv_last.setVisible(true);
				}else
					edc.inv_last.setVisible(false);
			});

			//cin field listener
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
			edc.prenom_field.requestFocus();

			//apply button binder
			d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() -> 
											edc.nom_field.getText().isEmpty()
													|| edc.prenom_field.getText().isEmpty()
													|| edc.cin_field.getText().isEmpty()
													|| edc.dnPicker.getValue() == null ||
											edc.cin_field.getText().length() != 8
													|| !isNumeric(edc.cin_field.getText())
													|| !isAlpha(edc.nom_field.getText())
													|| !isAlpha(edc.prenom_field.getText()) || edc.dnPicker.getValue().isAfter(LocalDate.now().minusYears(18)),
											edc.nom_field.textProperty(), edc.prenom_field.textProperty(), edc.cin_field.textProperty(), edc.dnPicker.valueProperty()));
			
			Optional<ButtonType> clickedButton = d.showAndWait();

			//new employee creation and addition
			if (clickedButton.get() == ButtonType.APPLY) {
				Employee emp = new Employee();
				edc.setCurrentEmployee(emp);
				EmployeeServiceImpl empService = new EmployeeServiceImpl();
				empService.create(emp);
				reloadEmp(event);
			}
			

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
				edc.prenom_field.requestFocus();				
				d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() -> 
												edc.nom_field.getText().isEmpty() || edc.prenom_field.getText().isEmpty() || edc.cin_field.getText().isEmpty() || edc.dnPicker.getValue() == null || 
												edc.cin_field.getText().length() != 8 || !isNumeric(edc.cin_field.getText()) || !isAlpha(edc.nom_field.getText()) || !isAlpha(edc.prenom_field.getText()),
												edc.nom_field.textProperty(), edc.prenom_field.textProperty(), edc.cin_field.textProperty(), edc.dnPicker.valueProperty()));
				
				
				Optional<ButtonType> clickedButton = d.showAndWait();
				if (clickedButton.get() == ButtonType.APPLY) {
					edc.setCurrentEmployee(emp);
					empService.update(emp);
					reloadEmp(event);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void deleteEmp(ActionEvent event) {
		
		if (staffTable.getSelectionModel().getSelectedItem() != null) {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			//this is just for adding an icon to the dialog pane
			stage.getIcons().add(new Image("/assets/img/icon.png"));
			alert.setTitle("Delete Employee ?");
			alert.setHeaderText(null);
			alert.setContentText("Are you Sure You Want to Delete Selected Item(s) ?");
			Optional <ButtonType> action = alert.showAndWait();
			if(action.get() == ButtonType.OK) {
				ObservableList<Employee> selectedItems = staffTable.getSelectionModel().getSelectedItems();
				//todo
				for (Employee e : selectedItems) {
					try {
						EmployeeServiceImpl empService = new EmployeeServiceImpl();
						empService.remove(e.getId());
					}catch(Exception ex) {
						Alert alert_2 = new Alert(AlertType.ERROR);
						alert_2.setTitle("Error!");
						alert_2.setHeaderText(null);
						alert_2.setContentText("Employee is reference in another table.");
						alert_2.show();
					}
					
				}
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

	//Test if String is alphabetical
	public boolean isAlpha(String name) {
	    return name.matches("[a-zA-Z ]+");
	}
	
	@FXML
	public void addTeam(ActionEvent event) {
		try {
			FXMLLoader f = new FXMLLoader();
			f.setLocation(getClass().getResource("/interfaces/EquipeDialog.fxml"));
			Pane equipeDialogPane = f.load();
			EquipeDialogController eqac = f.getController();

			Dialog<ButtonType> d = new Dialog<>();
			
			Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/img/icon.png"));
			eqac.titleLabel.setText("Add Team");
			d.setDialogPane((DialogPane) equipeDialogPane);
			d.setTitle("Add Team");
			d.setResizable(false);
			d.initStyle(StageStyle.UNDECORATED);
			
			
			//these two are for moving the window with the mouse
			equipeDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
	           @Override
	           public void handle(MouseEvent event) {
	               xOffset = event.getSceneX();
	               yOffset = event.getSceneY();
	           }
			});
       
			equipeDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
	           @Override
	           public void handle(MouseEvent event) {
	               d.setX(event.getScreenX() - xOffset);
	               d.setY(event.getScreenY() - yOffset);
	           }
            });
			

			//name field listener
			eqac.name.textProperty().addListener((observable, oldValue, newValue) -> {
				System.out.println("textfield changed from " + oldValue + " to " + newValue);
				if(!isAlpha(newValue)) {
					eqac.inv_name.setVisible(true);

				}else
					eqac.inv_name.setVisible(false);
			});
			//to apply css on the dialog pane buttons
			d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
			d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");
			
			eqac.name.requestFocus();

			//apply button binder
			d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
							eqac.name.getText().isEmpty()
									|| !isAlpha(eqac.name.getText())
									|| eqac.leader.getSelectionModel().isEmpty(),
										eqac.name.textProperty(),
										eqac.leader.valueProperty()));

			Optional<ButtonType> clickedButton = d.showAndWait();

			if (clickedButton.get() == ButtonType.APPLY) {
				Equipe equipe = new Equipe();
				equipe.setNom(eqac.name.getText());
				EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
				Employee emp = employeeService.getById(Long.parseLong(eqac.leader.getValue().toString().split(",")[0]));
				equipe.setResponsable(emp);

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
			f.setLocation(getClass().getResource("/interfaces/EquipeDialog.fxml"));
			Pane equipeDialogPane = f.load();
			EquipeDialogController equc = f.getController();

			if (teamTable.getSelectionModel().getSelectedItem() != null) {

				EquipeServiceImpl equipeService = new EquipeServiceImpl();
				Equipe e = (Equipe) teamTable.getSelectionModel().getSelectedItem();
				Equipe equipe = equipeService.getById(e.getId());

				
				equc.setEquipeDialogPane(equipe);
				Dialog<ButtonType> d = new Dialog<>();
				
				Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image("/assets/img/icon.png"));
				
				d.setDialogPane((DialogPane) equipeDialogPane);
				d.setTitle("Update Employee");
				d.setResizable(false);
				d.initStyle(StageStyle.UNDECORATED);
				
				//these two are for moving the window with the mouse
				equipeDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
		           @Override
		           public void handle(MouseEvent event) {
		               xOffset = event.getSceneX();
		               yOffset = event.getSceneY();
		           }
				});
	       
				equipeDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
		           @Override
		           public void handle(MouseEvent event) {
		               d.setX(event.getScreenX() - xOffset);
		               d.setY(event.getScreenY() - yOffset);
		           }
	            });
				//name field listener
				equc.name.textProperty().addListener((observable, oldValue, newValue) -> {
					if(!isAlpha(newValue)) {
						equc.inv_name.setVisible(true);

					}else
						equc.inv_name.setVisible(false);
				});
				
				//to apply css on the dialog pane buttons
				d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
				d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");
				
				equc.name.requestFocus();

				//apply button binder
				d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
								equc.name.getText().isEmpty()
										|| !isAlpha(equc.name.getText())
										|| equc.leader.getSelectionModel().isEmpty(),
						equc.name.textProperty(),
						equc.leader.valueProperty()));


				Optional<ButtonType> clickedButton = d.showAndWait();

				if (clickedButton.get() == ButtonType.APPLY) {
					equc.setCurrentEquipe(equipe); //set updates
					equipeService.update(equipe); //persist update in the db
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
			Alert alert = new Alert(AlertType.CONFIRMATION);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			//this is just for adding an icon to the dialog pane
			stage.getIcons().add(new Image("/assets/img/icon.png"));
			alert.setTitle("Delete Employee ?");
			alert.setHeaderText(null);
			alert.setContentText("Are you Sure You Want to Delete Selected Item(s) ?");
			Optional <ButtonType> action = alert.showAndWait();
			if(action.get() == ButtonType.OK) {
				ObservableList<Equipe> selectedItems = teamTable.getSelectionModel().getSelectedItems();
				for (Equipe e : selectedItems) {
					try {
						EquipeServiceImpl equipeService = new EquipeServiceImpl();
						equipeService.remove(e.getId());
					}catch(Exception ex) {
						Alert alert_2 = new Alert(AlertType.ERROR);
						alert_2.setTitle("Error!");
						alert_2.setHeaderText(null);
						alert_2.setContentText("Cannot delete team while refreneced in a project!");
						alert_2.show();
					}
					
				}
				refreshTeam();
			}
		}
	}
	
	@FXML
	void selectAllStaff(ActionEvent event) {
		this.staffTable.getSelectionModel().selectAll();
	}
	@FXML
	void selectAllTeam(ActionEvent event) {
		this.teamTable.getSelectionModel().selectAll();
	}
}

package org.fsb.municipalite.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.fsb.municipalite.entities.Compte;

import org.fsb.municipalite.services.impl.CompteServiceImpl;


import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AccountPageController implements Initializable{

    @FXML
    private TextField searchBox;

    @FXML
    private TableView<Compte> tableview;

    @FXML
    private TableColumn<Compte, Long> id;

    @FXML
    private TableColumn<Compte, String> username;

    @FXML
    private TableColumn<Compte, String> password;

	@FXML
	private TableColumn<Compte, String> email;

    @FXML
    private TableColumn<Compte, LocalDateTime> creationdate;

    @FXML
    private TableColumn<Compte, String> question;

    @FXML
    private TableColumn<Compte, String> answer;

    @FXML
    private TableColumn<Compte, String> empID;

    @FXML
    private TableColumn<Compte, Integer> loginSessions;
    
    ObservableList accountsData = FXCollections.observableArrayList();
    
    //define dialog window offsets here
    private double xOffset = 0;
    private double yOffset = 0;
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	tableview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		id.setCellValueFactory(new PropertyValueFactory<Compte, Long>("id"));
		username.setCellValueFactory(new PropertyValueFactory<Compte, String>("username"));
		password.setCellValueFactory(new PropertyValueFactory<Compte, String>("password"));
		email.setCellValueFactory(new PropertyValueFactory<Compte, String>("email"));
		creationdate.setCellValueFactory(new PropertyValueFactory<Compte, LocalDateTime>("CreatedAtValue"));
		question.setCellValueFactory(new PropertyValueFactory<Compte, String>("question"));
		answer.setCellValueFactory(new PropertyValueFactory<Compte, String>("answer"));
		empID.setCellValueFactory(new PropertyValueFactory<Compte, String>("EmpValue"));
		loginSessions.setCellValueFactory(new PropertyValueFactory<Compte, Integer>("loginSessions"));

		CompteServiceImpl compteService = new CompteServiceImpl();

		List<Compte> list = compteService.selectAll();
		for (Compte c : list) {
			accountsData.add(c);
		}
		tableview.setItems(accountsData);

		//searchListener
		searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("textfield changed from " + oldValue + " to " + newValue);
			listenerSearchAccount(newValue);
		});
		
	}

    @FXML
    void refresh(ActionEvent event) {
    	tableview.getItems().clear();
		CompteServiceImpl cService = new CompteServiceImpl();
		List<Compte> list = cService.selectAll();
		for (Compte c : list) {
			accountsData.add(c);
		}
		tableview.setItems(accountsData);
    }
    
    @FXML
    void add(ActionEvent event) {

    	try {
			FXMLLoader f = new FXMLLoader();
			f.setLocation(getClass().getResource("/interfaces/AccountDialog.fxml"));
			Pane accountDialogPane = f.load();
			//get the current controller and put it in edc
			AccountDialogController adc = f.getController();

			CompteServiceImpl cService = new CompteServiceImpl();
			List<String> usernameList = cService.selectAllInONEColumn("username");

			Dialog<ButtonType> d = new Dialog<>();
			//this is just for adding an icon to the dialog pane
			Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/img/icon.png"));
			
			d.setDialogPane((DialogPane) accountDialogPane);
			d.setTitle("Add Account");
			d.setResizable(false);
			d.initStyle(StageStyle.UNDECORATED);
			
			//these two are for moving the window with the mouse
			accountDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
	           @Override
	           public void handle(MouseEvent event) {
	               xOffset = event.getSceneX();
	               yOffset = event.getSceneY();
	           }
			});
       
			accountDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
	           @Override
	           public void handle(MouseEvent event) {
	               d.setX(event.getScreenX() - xOffset);
	               d.setY(event.getScreenY() - yOffset);
	           }
            });

			adc.getUsernameField().textProperty().addListener((observable, oldValue, newValue) -> {
				if(!isAlphaNumericdotdashbel8(newValue)) {
					adc.getInv_username().setText("Invalid Username");
					adc.getInv_username().setVisible(true);

				}else if(usernameList.contains(newValue)) {
					adc.getInv_username().setText("Username already taken");
					adc.getInv_username().setVisible(true);

				}else {
					adc.getInv_username().setVisible(false);
				}

			});

			adc.getPasswordField().textProperty().addListener((observable, oldValue, newValue) -> {
				if(!isAlphaNumericdotdashbel8(newValue)) {
					adc.getInv_password().setVisible(true);
				}else
					adc.getInv_password().setVisible(false);
				if(!newValue.equals(adc.getPasswordField2().getText())) {
					adc.getInv_password2().setVisible(true);
				}else
					adc.getInv_password2().setVisible(false);
			});
			
			adc.getPasswordField2().textProperty().addListener((observable, oldValue, newValue) -> {
				if(!newValue.equals(adc.getPasswordField().getText())) {
					adc.getInv_password2().setVisible(true);
				}else
					adc.getInv_password2().setVisible(false);
			});

			adc.getEmailField().textProperty().addListener((observable, oldValue, newValue) -> {
				if(!newValue.matches("[a-zA-Z0-9.@_]+")) {
					adc.getInv_email().setVisible(true);
				}else
					adc.getInv_email().setVisible(false);

			});


			//to apply css on the dialog pane buttons
			d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
			d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");
			
			//make name field first to be selected
			adc.getInv_username().requestFocus();

			//apply button binder
			d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() -> 
											adc.getUsernameField().getText().isEmpty() 
											|| adc.getPasswordField().getText().isEmpty() 
											|| adc.getPasswordField2().getText().isEmpty()
											|| adc.getEmailField().getText().isEmpty()
											|| adc.getEmployeeBox().getSelectionModel().isEmpty()
											|| !isAlphaNumericdotdashbel8(adc.getUsernameField().getText()) 
											|| !isAlphaNumericdotdashbel8(adc.getPasswordField().getText()) 
											|| !isAlphaNumericdotdashbel8(adc.getPasswordField2().getText())
											|| !adc.getEmailField().getText().matches("[a-zA-Z0-9.@_]+")
											|| !adc.getPasswordField().getText().equals(adc.getPasswordField2().getText())
											|| usernameList.contains(adc.getUsernameField().getText()),
											adc.getUsernameField().textProperty(), 
											adc.getPasswordField().textProperty(), 
											adc.getPasswordField2().textProperty(),
											adc.getEmailField().textProperty(),
											adc.getEmployeeBox().valueProperty()));
			
			Optional<ButtonType> clickedButton = d.showAndWait();

			//new employee creation and addition
			if (clickedButton.get() == ButtonType.APPLY) {
				Compte c = new Compte();
				System.out.println(c);
				adc.setCurrentAccount(c);
				cService.create(c);
				refresh(event);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void update(ActionEvent event) {
    	try {
			FXMLLoader f = new FXMLLoader();
			f.setLocation(getClass().getResource("/interfaces/AccountDialog.fxml"));
			Pane accountDialogPane = f.load();
			//get the current controller and put it in edc
			AccountDialogController adc = f.getController();
			
			if (this.tableview.getSelectionModel().getSelectedItem() != null) {
	
				CompteServiceImpl cService = new CompteServiceImpl();
				Compte c = (Compte) this.tableview.getSelectionModel().getSelectedItem();
				Compte compte = cService.getById(c.getId());
				adc.setAccountDialogPane(compte);
				adc.getTitleLabel().setText("Update Account");
				adc.getEmployeeBox().setDisable(true);

				List<String> usernameList = cService.selectAllInONEColumn("username");
				
				Dialog<ButtonType> d = new Dialog<>();
				//this is just for adding an icon to the dialog pane
				Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image("/assets/img/icon.png"));
				
				d.setDialogPane((DialogPane) accountDialogPane);
				d.setTitle("Update Account");
				d.setResizable(false);
				d.initStyle(StageStyle.UNDECORATED);
				
				//these two are for moving the window with the mouse
				accountDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
		           @Override
		           public void handle(MouseEvent event) {
		               xOffset = event.getSceneX();
		               yOffset = event.getSceneY();
		           }
				});
	       
				accountDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
		           @Override
		           public void handle(MouseEvent event) {
		               d.setX(event.getScreenX() - xOffset);
		               d.setY(event.getScreenY() - yOffset);
		           }
	            });
	
				adc.getUsernameField().textProperty().addListener((observable, oldValue, newValue) -> {
					if(!isAlphaNumericdotdashbel8(newValue)) {
						adc.getInv_username().setText("Invalid Username");
						adc.getInv_username().setVisible(true);

					}else if(usernameList.contains(newValue) && !newValue.equals(c.getUsername())) {
						adc.getInv_username().setText("Username already taken");
						adc.getInv_username().setVisible(true);

					}else {
						adc.getInv_username().setVisible(false);
					}
				});
	
				adc.getPasswordField().textProperty().addListener((observable, oldValue, newValue) -> {
					if(!isAlphaNumericdotdashbel8(newValue)) {
						adc.getInv_password().setVisible(true);
					}else
						adc.getInv_password().setVisible(false);
					if(!newValue.equals(adc.getPasswordField2().getText())) {
						adc.getInv_password2().setVisible(true);
					}else
						adc.getInv_password2().setVisible(false);
				});
				
				adc.getPasswordField2().textProperty().addListener((observable, oldValue, newValue) -> {
					if(!newValue.equals(adc.getPasswordField().getText())) {
						adc.getInv_password2().setVisible(true);
					}else
						adc.getInv_password2().setVisible(false);
				});

				adc.getEmailField().textProperty().addListener((observable, oldValue, newValue) -> {
					if(!newValue.matches("[a-zA-Z0-9.@_]+")) {
						adc.getInv_email().setVisible(true);
					}else
						adc.getInv_email().setVisible(false);

				});


				//to apply css on the dialog pane buttons
				d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
				d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");
				
				//make name field first to be selected
				adc.getInv_username().requestFocus();

				//apply button binder
				d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() -> 
												adc.getUsernameField().getText().isEmpty()
												|| adc.getEmailField().getText().isEmpty()
												|| adc.getPasswordField().getText().isEmpty() 
												|| adc.getPasswordField2().getText().isEmpty()
												|| !isAlphaNumericdotdashbel8(adc.getUsernameField().getText()) 
												|| !isAlphaNumericdotdashbel8(adc.getPasswordField().getText()) 
												|| !isAlphaNumericdotdashbel8(adc.getPasswordField2().getText())
												|| !adc.getEmailField().getText().matches("[a-zA-Z0-9.@_]+")
												|| !adc.getPasswordField().getText().equals(adc.getPasswordField2().getText())
												|| (usernameList.contains(adc.getUsernameField().getText()) && !adc.getUsernameField().getText().equals(c.getUsername())),
												adc.getUsernameField().textProperty(),
						   						adc.getEmailField().textProperty(),
												adc.getPasswordField().textProperty(), 
												adc.getPasswordField2().textProperty()));
				
				Optional<ButtonType> clickedButton = d.showAndWait();
	
				if (clickedButton.get() == ButtonType.APPLY) {
					adc.setCurrentAccount(compte);
					cService.update(compte);
					refresh(event);
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void remove(ActionEvent event) {
    	if (this.tableview.getSelectionModel().getSelectedItem() != null) {

    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    		//this is just for adding an icon to the dialog pane
    		stage.getIcons().add(new Image("/assets/img/icon.png"));
    		alert.setTitle("Delete Account ?");
    		alert.setHeaderText(null);
    		alert.setContentText("Are you Sure You Want to Delete Selected Account(s) ?");
    		Optional <ButtonType> action = alert.showAndWait();
    		if(action.get() == ButtonType.OK) {
    			ObservableList<Compte> selectedItems = this.tableview.getSelectionModel().getSelectedItems();
    			for (Compte c : selectedItems) {
    				CompteServiceImpl cService = new CompteServiceImpl();
    				cService.remove(c.getId());


    			}
    			refresh(event);
    		}

    	}
    }
  	public boolean isAlphaNumericdotdashbel8(String name) {
  	    return name.matches("[a-zA-Z._0-9]+");
  	}

  	public void listenerSearchAccount(String n){
		accountsData.clear();

  		CompteServiceImpl cService = new CompteServiceImpl();
		List<Compte> list;
		if (!(n.equals(""))) {
			if (isNumeric(n)) {
				list = cService.selectLike("EMPLOYEE_ID", n);
				for (Compte e : list) {
					accountsData.add(e);
				}
			} else {
				list = cService.selectLike("USERNAME", "'" + n + "%'");
				for (Compte e : list) {
					accountsData.add(e);
				}
			}
			this.tableview.setItems(accountsData);
		} else {
			list = cService.selectAll();
			for (Compte e : list) {
				accountsData.add(e);
			}
			this.tableview.setItems(accountsData);
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

	public void selectAll(ActionEvent event) {
		this.tableview.getSelectionModel().selectAll();
	}
}

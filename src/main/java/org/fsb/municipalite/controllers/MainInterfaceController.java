package org.fsb.municipalite.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.fsb.municipalite.entities.Compte;
import org.fsb.municipalite.services.impl.CompteServiceImpl;

public class MainInterfaceController implements Initializable{

	
	@FXML
	private BorderPane contentBorderPane;
    @FXML
    private MenuButton profilMenu;
    @FXML
	private Label currentPageTitle;
    @FXML
    private Label dashboard;
    @FXML
    private Label services;

    @FXML
    private Label tools;

    @FXML
    private Label ts;

    @FXML
    private Label finance;

    @FXML
    private Label tasks;

    @FXML
    private Label projects;

    @FXML
    private Label events;

    @FXML
    private Label accounts;

    @FXML
    private Label muniInfoButton;
    
    @FXML
    private VBox mymenu;
    
    private Compte userAccount;
    
    private double xOffset = 0;
    private double yOffset = 0;

    
	public boolean isAlphaNumericdotdashbel8(String name) {
		return name.matches("[a-zA-Z._0-9]+");
	}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mymenu.getChildren().removeAll(services, tools, ts, finance, tasks, projects, events, accounts);
	}
	@FXML
	void settings(ActionEvent event) {
		try {
		FXMLLoader f = new FXMLLoader();
        f.setLocation(getClass().getResource("/interfaces/Settings.fxml"));
        Pane settingsDialogPane = f.load();
		SettingsController sc = f.getController();
        Dialog<ButtonType> d = new Dialog<>();

        //this is just for adding an icon to the dialog pane
		Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/assets/img/icon.png"));
		
        d.setDialogPane((DialogPane) settingsDialogPane);
        d.setTitle("Settings");
        d.setResizable(false);
        d.initStyle(StageStyle.UNDECORATED);
        
        //these two are for moving the window with the mouse
        settingsDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        settingsDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                d.setX(event.getScreenX() - xOffset);
                d.setY(event.getScreenY() - yOffset);
            }
        });

        CompteServiceImpl cService = new CompteServiceImpl();
        //fetch list of usernames
        List<String> usernameList = cService.selectAllInONEColumn("username");


        //set user infos
		sc.setAccountInfos(userAccount);

        //username field listener and tests
			sc.username.textProperty().addListener((observable, oldValue, newValue) -> {
				if(!isAlphaNumericdotdashbel8(newValue)) {
					sc.inv_username.setText("Invalid Username");
					sc.inv_username.setVisible(true);

				}else if(usernameList.contains(newValue) && !newValue.equals(userAccount.getUsername())) {
					sc.inv_username.setText("Username already taken");
					sc.inv_username.setVisible(true);

				}else {
					sc.inv_username.setVisible(false);
				}
			});

		//password listener
			sc.password1.textProperty().addListener((observable, oldValue, newValue) -> {
				if(!isAlphaNumericdotdashbel8(newValue)) {
					sc.inv_password1.setVisible(true);
				}else
					sc.inv_password1.setVisible(false);
				if(!newValue.equals(sc.password2.getText())) {
					sc.inv_password2.setVisible(true);
				}else
					sc.inv_password2.setVisible(false);
			});

			sc.password2.textProperty().addListener((observable, oldValue, newValue) -> {
				if(!newValue.equals(sc.password1.getText())) {
					sc.inv_password2.setVisible(true);
				}else
					sc.inv_password2.setVisible(false);
			});

			//apply button binder
			d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
										sc.username.getText().isEmpty()
									|| sc.password1.getText().isEmpty()
									|| (sc.password2.getText().isEmpty()&&!sc.password2.isDisable())
									|| !isAlphaNumericdotdashbel8(sc.username.getText())
									|| !isAlphaNumericdotdashbel8(sc.password1.getText())
									|| (!sc.password1.getText().matches(sc.password2.getText())&&!sc.password2.isDisable())
									|| (usernameList.contains(sc.username.getText()) && !sc.username.getText().matches(userAccount.getUsername())),
					sc.username.textProperty(),
					sc.password1.textProperty(),
					sc.password2.textProperty()));


			//to apply css on the dialog pane buttons
			d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
			d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");

        Optional<ButtonType> clickedButton = d.showAndWait();
        if (clickedButton.get() == ButtonType.APPLY) {
        	Compte compte = cService.getById(userAccount.getId());
			sc.setAccountUpdate(compte);
			cService.update(compte);
        }
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void logout(ActionEvent event) {	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/loginScene.fxml"));
		try {
			Scene scene = new Scene(loader.load());
			Stage s = (Stage)profilMenu.getScene().getWindow();
			s.setMaximized(false);
			s.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


    //******** Menu Buttons ************
	@FXML
	public void dashboardButton(MouseEvent event) {
		String role = userAccount.getEmployee().getRole();
        String dashboard = "";
        switch(role) {
            case "Administrateur": dashboard = "Dashboard";
                break;
            case "Chef Personnel": dashboard = "DashboardTS";
                break;
            case "Agent De Service": dashboard = "DashboardAS";
                break;
            case "Gestionnaire de Magasin": dashboard = "DashboardG";
                break;
            case "Financier": dashboard = "DashboardF";
                break;
            case "Ingenieur d'Affaires": dashboard = "DashboardT";
                break;
            case "Secretaire General": dashboard = "DashboardSG";
                break;
        }
        Pane view = CustomFxmlLoader.getPage(dashboard);
        contentBorderPane.setCenter(view);
        currentPageTitle.setText("Dashboard");
	}
	@FXML
	private void servicesButton(MouseEvent event) {
		Pane view = CustomFxmlLoader.getPage("ServicesPage");
		contentBorderPane.setCenter(view);
		currentPageTitle.setText("Services");

	}
	@FXML
	private void toolsButton(MouseEvent event) {
		Pane view = CustomFxmlLoader.getPage("MaterielPage");
		contentBorderPane.setCenter(view);
		currentPageTitle.setText("Tools");

	}
	@FXML
	private void TeamsStaffButton(MouseEvent event) {
		Pane view = CustomFxmlLoader.getPage("TeamsStaffPage");
		contentBorderPane.setCenter(view);
		currentPageTitle.setText("Teams and Staff");

	}
	@FXML
	private void FinanceButton(MouseEvent event) {
		System.out.println("Finance Clicked");
		Pane view = CustomFxmlLoader.getPage("FinancePage");
		contentBorderPane.setCenter(view);
		currentPageTitle.setText("Finance");
	}

	@FXML
	public void projectsButton(MouseEvent mouseEvent) {
		Pane view = CustomFxmlLoader.getPage("ProjectPage");
		contentBorderPane.setCenter(view);
		currentPageTitle.setText("Projects");

	}
	@FXML
	public void tasksButton(MouseEvent mouseEvent) {
		Pane view = CustomFxmlLoader.getPage("TachePage");
		contentBorderPane.setCenter(view);
		currentPageTitle.setText("Tasks");

	}
	@FXML
	public void eventsButton(MouseEvent mouseEvent) {
		Pane view = CustomFxmlLoader.getPage("EvenementPage");
		contentBorderPane.setCenter(view);
		currentPageTitle.setText("Events");

	}
	@FXML
	public void accountButton(MouseEvent mouseEvent) {
		Pane view = CustomFxmlLoader.getPage("AccountsPage");
		contentBorderPane.setCenter(view);
		currentPageTitle.setText("Accounts");
	}
	@FXML
	void municipalityInfo(MouseEvent event) throws IOException {
		//send user between pages
		FXMLLoader f = new FXMLLoader();
		f.setLocation(getClass().getResource("/interfaces/MunicipalityInfo.fxml"));
		GridPane g = f.load();
		MunicipalityInfoController muniController = f.getController();
		muniController.setCurrentAccount(userAccount);

		contentBorderPane.setCenter(g);
		currentPageTitle.setText("Municipality Informations");

	}
	/* ********************************** */

	public Compte getUserAccount() {
		return userAccount;
	}

	
	public void setUserAccount(Compte userAccount) {
		this.userAccount = userAccount;
		profilMenu.setText(userAccount.getEmployee().getPrenom());
		setMenu();
	}
	
	public void setMenu() {
		String role = userAccount.getEmployee().getRole();
		switch(role) {
			case "Administrateur": mymenu.getChildren().addAll(services, tools, ts, finance, tasks, projects, events, accounts);
				break;
			case "Chef Personnel": mymenu.getChildren().add(ts);
							       mymenu.getChildren().add(tasks);
				break;
			case "Agent De Service": mymenu.getChildren().add(services);
				break;
			case "Gestionnaire de Magasin": mymenu.getChildren().add(tools);
				break;
			case "Financier": mymenu.getChildren().add(finance);
				break;
			case "Ingenieur d'Affaires": mymenu.getChildren().add(projects);
				break;
			case "Secretaire General": mymenu.getChildren().add(events);
				break;
		}
		dashboardButton(null);
	}
	
}


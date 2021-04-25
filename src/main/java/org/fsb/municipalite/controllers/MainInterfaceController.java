package org.fsb.municipalite.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.fsb.municipalite.entities.Compte;
import org.fsb.municipalite.services.impl.CompteServiceImpl;

public class MainInterfaceController implements Initializable{

	@FXML
	Label username_label;
	
	@FXML
	BorderPane contentBorderPane;
	

    @FXML
    private MenuButton profilMenu;

    private Compte userAccount;

	public boolean isAlphaNumericdotdashbel8(String name) {
		return name.matches("[a-zA-Z._0-9]+");
	}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		servicesButton(null);
		
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
			//bech thablni heudhia
			s.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    @FXML
    void municipalityInfo(MouseEvent event) {
    	
    	Pane view = CustomFxmlLoader.getPage("MunicipalityInfo");
		contentBorderPane.setCenter(view);
    }

	@FXML
	public void dashboardButton(MouseEvent event) {
		System.out.println("Dashboard Clicked");
		
		Pane view = CustomFxmlLoader.getPage("nothing");
		contentBorderPane.setCenter(view);

	}
	@FXML
	private void servicesButton(MouseEvent event) {
		System.out.println("Services Clicked");
		Pane view = CustomFxmlLoader.getPage("ServicesPage");
		contentBorderPane.setCenter(view);
	}
	@FXML
	private void toolsButton(MouseEvent event) {
		System.out.println("Tools Button Clicked");
		Pane view = CustomFxmlLoader.getPage("MaterielPage");
		contentBorderPane.setCenter(view);
	}
	@FXML
	private void TeamsStaffButton(MouseEvent event) {
		
		System.out.println("TS Clicked");
		Pane view = CustomFxmlLoader.getPage("TeamsStaffPage");
		
		contentBorderPane.setCenter(view);
	}
	@FXML
	private void FinanceButton(MouseEvent event) {
		
		System.out.println("Finance Clicked");
		Pane view = CustomFxmlLoader.getPage("nothing");
		contentBorderPane.setCenter(view);
		
	}
	@FXML
	public void projectsButton(MouseEvent mouseEvent) {
		System.out.println("Tools Button Clicked");
		Pane view = CustomFxmlLoader.getPage("ProjectPage");
		contentBorderPane.setCenter(view);
	}
	@FXML
	public void tasksButton(MouseEvent mouseEvent) {
		System.out.println("tasks Button Clicked");
		Pane view = CustomFxmlLoader.getPage("TachePage");
		contentBorderPane.setCenter(view);
	}
	@FXML
	public void eventsButton(MouseEvent mouseEvent) {
		System.out.println("events Button Clicked");
		Pane view = CustomFxmlLoader.getPage("EvenementPage");
		contentBorderPane.setCenter(view);
	}
	@FXML
	public void accountButton(MouseEvent mouseEvent) {
		System.out.println("events Button Clicked");
		Pane view = CustomFxmlLoader.getPage("AccountsPage");
		contentBorderPane.setCenter(view);
	}

	public Compte getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(Compte userAccount) {
		this.userAccount = userAccount;
	}
}


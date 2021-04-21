package org.fsb.municipalite.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainInterfaceController implements Initializable{

	@FXML
	Label username_label;
	
	@FXML
	BorderPane contentBorderPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		servicesButton(null);
		
	}
	
	@FXML
	public void logoutButton(MouseEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/loginScene.fxml"));
		try {
			Scene scene = new Scene(loader.load());
			Stage s = (Stage)((Label)event.getSource()).getScene().getWindow();
			//bech thablni heudhiad
			s.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
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
	
	
	
	
}


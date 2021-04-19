package org.fsb.municipalite.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainInterfaceController implements Initializable{

	
	@FXML
	BorderPane contentBorderPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		servicesButton(null);
		
	}
	
	@FXML
	public void logoutButton(MouseEvent event) {
		Stage s = (Stage)((Label)event.getSource()).getScene().getWindow();
		s.close();
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
	
	
}


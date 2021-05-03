package org.fsb.municipalite.controllers;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.fsb.municipalite.APIs.SendMail;
import org.fsb.municipalite.entities.Compte;
import org.fsb.municipalite.services.impl.CompteServiceImpl;

public class ResetByEmail1Controller {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
    @FXML
    private TextField emailField;

    @FXML
    private Label checkEmail;

    
    @FXML
    void next(ActionEvent event) {
		stage =(Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/ResetByEmail2.fxml"));
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		scene = new Scene(root);

		CompteServiceImpl compteService = new CompteServiceImpl();
		List<String> emails = compteService.selectAllInONEColumn("email");
		System.out.println(emails);

		if(emails.contains(emailField.getText())) {

			String code = generateCode(6);
			System.out.println(code);

			SendMail.sendRecupCode(emailField.getText(), code);

			ResetByEmail2Controller REcontroller = loader.getController();
			REcontroller.setResetCode(code);
			Compte c=compteService.getById(compteService.findOne("email",emailField.getText()).getId());
			REcontroller.setUserAccount(c);

			stage.setScene(scene);

		}else{
			checkEmail.setText("Incorrect Email");
			checkEmail.setStyle("-fx-text-fill: RED");
			checkEmail.setVisible(true);
		}

    }

    
    @FXML
    void back(ActionEvent event) {
    	stage =(Stage)((Node)event.getSource()).getScene().getWindow();		
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/PasswordReset.fxml"));
    	try {
    		root = loader.load();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	scene = new Scene(root);
    	stage.setScene(scene);
    }

	static String generateCode(int n)
	{

		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789"
				+ "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index
					= (int)(AlphaNumericString.length()
					* Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString
					.charAt(index));
		}

		return sb.toString();
	}


}

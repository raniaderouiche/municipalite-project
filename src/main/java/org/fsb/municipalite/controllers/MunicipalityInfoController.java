package org.fsb.municipalite.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MunicipalityInfoController implements Initializable{

    @FXML
    private TextField everydayF;

    @FXML
    private TextField everydayU;

    @FXML
    private TextField fridayF;

    @FXML
    private TextField fridayU;

    @FXML
    private TextField nameField;

    @FXML
    private TextField numberField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField websiteField;
    
    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button modifyButton;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFieldsDisabled(true);
		
		
		

	}

    public void setFieldsDisabled(Boolean b) {
    	this.nameField.setDisable(b);
    	this.numberField.setDisable(b);
    	this.emailField.setDisable(b);
    	this.addressField.setDisable(b);
    	this.websiteField.setDisable(b);
    	this.everydayF.setDisable(b);
    	this.everydayU.setDisable(b);
    	this.fridayF.setDisable(b);
    	this.fridayU.setDisable(b);
    }
    
    @FXML
    void modify(ActionEvent event) {
    	setFieldsDisabled(false);
    	this.saveButton.setVisible(true);
    	this.cancelButton.setVisible(true);
    	this.modifyButton.setDisable(true);
    	
    	nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(isAlpha(newValue)) {
				nameField.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
            }else
				nameField.setStyle("-fx-border-color: red;");
        });
    	
    	numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("[1-9]+") && newValue.length() <=8) {
            	numberField.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
            }else
            	numberField.setStyle("-fx-border-color: red;");
        });
    	
    	emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("^(.+)@(.+)$")) {
            	emailField.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
            }else
            	emailField.setStyle("-fx-border-color: red;");
        });
    	
    	addressField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() < 40) {
            	addressField.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
            }else
            	addressField.setStyle("-fx-border-color: red;");
        });
    	websiteField.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue.matches("w{3}\\.[a-z]+\\.?[a-z]{2,3}(|\\.[a-z]{2,3})")) {
    			websiteField.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
    		}else
    			websiteField.setStyle("-fx-border-color: red;");
    	});
    	everydayF.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue.matches("[1-9]+") && newValue.length()<=2) {
    			everydayF.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
    		}else
    			everydayF.setStyle("-fx-border-color: red;");
    	});
    	everydayU.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue.matches("[1-9]+") && newValue.length()<=2) {
    			everydayU.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
    		}else
    			everydayU.setStyle("-fx-border-color: red;");
    	});
    	fridayF.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue.matches("[1-9]+") && newValue.length()<=2) {
    			fridayF.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
    		}else
    			fridayF.setStyle("-fx-border-color: red;");
    	});
    	fridayU.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue.matches("[1-9]+") && newValue.length()<=2) {
    			fridayU.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
    		}else
    			fridayU.setStyle("-fx-border-color: red;");
    	});


    }
    
    
    @FXML
    void save(ActionEvent event) {
    	setFieldsDisabled(true);
    	this.saveButton.setVisible(false);
    	this.cancelButton.setVisible(false);
    	this.modifyButton.setDisable(false);
    }

    @FXML
    void cancel(ActionEvent event) {
    	setFieldsDisabled(true);
    	this.saveButton.setVisible(false);
    	this.cancelButton.setVisible(false);
    	this.modifyButton.setDisable(false);
    }

    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z ]+");
    }
}

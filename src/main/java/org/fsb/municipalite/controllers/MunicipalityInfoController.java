package org.fsb.municipalite.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.fsb.municipalite.dao.impl.MunicipaliteDaoImpl;
import org.fsb.municipalite.entities.Municipalite;
import org.fsb.municipalite.services.impl.MunicipaliteServiceImpl;

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

    Municipalite municipalite;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFieldsDisabled(true);
		getMunicipalite();
	}

	public void getMunicipalite(){
		MunicipaliteServiceImpl municipaliteService = new MunicipaliteServiceImpl();
		List<Municipalite> list = municipaliteService.selectAll();
		if(list.isEmpty()){
			Municipalite municipalite = new Municipalite();
			municipaliteService.create(municipalite);
		}
		else{
			municipalite = municipaliteService.getById(list.get(0).getId());
			nameField.setText(municipalite.getNom());
			numberField.setText(municipalite.getTel());
			emailField.setText(municipalite.getEmail());
			addressField.setText(municipalite.getAdresse());
			websiteField.setText(municipalite.getWebsite());
			//add work hours
		}

	}

	public void setMunicipalite(Municipalite municipalite){
		municipalite.setNom(nameField.getText());
		municipalite.setTel(numberField.getText());
		municipalite.setEmail(emailField.getText());
		municipalite.setAdresse(addressField.getText());
		municipalite.setWebsite(websiteField.getText());
		//add work hours
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
    		if(newValue.matches("[0-9]+") && newValue.length()<=2) {
    			if(Integer.parseInt(newValue)>=0 && Integer.parseInt(newValue)<24)
    				everydayF.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
    			else
    				everydayF.setStyle("-fx-border-color: red;");
    		}else
    			everydayF.setStyle("-fx-border-color: red;");
    	});
    	everydayU.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue.matches("[1-9]+") && newValue.length()<=2) {
    			if(Integer.parseInt(newValue)>=0 && Integer.parseInt(newValue)<24)
    				everydayU.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
    			else
    				everydayU.setStyle("-fx-border-color: red;");
    		}else
    			everydayU.setStyle("-fx-border-color: red;");
    	});
    	fridayF.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue.matches("[1-9]+") && newValue.length()<=2) {
    			if(Integer.parseInt(newValue)>=0 && Integer.parseInt(newValue)<24)
    				fridayF.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
    			else
    				fridayF.setStyle("-fx-border-color: red;");
    		}else
    			fridayF.setStyle("-fx-border-color: red;");
    	});
    	fridayU.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue.matches("[1-9]+") && newValue.length()<=2) {
    			if(Integer.parseInt(newValue)>=0 && Integer.parseInt(newValue)<24)
    				fridayU.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
    			else
    				fridayU.setStyle("-fx-border-color: red;");
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
		setMunicipalite(municipalite);
		MunicipaliteServiceImpl municipaliteService = new MunicipaliteServiceImpl();
		municipaliteService.update(municipalite);
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

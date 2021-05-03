package org.fsb.municipalite.controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    
    //for testing
    Boolean[] tests = new Boolean[9];
    boolean check = true;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFieldsDisabled(true);
		getMunicipalite();
		listenersGo();
		for(int i = 0; i < tests.length; i++)
			tests[i] = true;
	}

	public void listenersGo() {
		nameField.textProperty().addListener((observablSadme, oldValue, newValue) -> {
            if(isAlpha(newValue)) {
				nameField.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
				tests[0] = true;
            }else {
            	nameField.setStyle("-fx-border-color: red;");
            	tests[0] = false;
            }
        });
    	
    	numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("[0-9]+") && (newValue.length() == 8) ) {
            	numberField.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
            	tests[1] = true;
            }else {
            	numberField.setStyle("-fx-border-color: red;");
            	tests[1] = false;
            }
        });
    	
    	emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("^(.+)@(.+)$")) {
            	emailField.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
            	tests[2] = true;
            }else {
            	emailField.setStyle("-fx-border-color: red;");
            	tests[2] = false;
            }
        });
    	
    	addressField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() < 40) {
            	addressField.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
            	tests[3] = true;
            }else {
            	addressField.setStyle("-fx-border-color: red;");
            	tests[3] = false;
            }
            	
        });
    	websiteField.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue.matches("w{3}\\.[a-z]+\\.?[a-z]{2,3}(|\\.[a-z]{2,3})")) {
    			websiteField.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
            	tests[4] = true;
    		}else {
    			websiteField.setStyle("-fx-border-color: red;");
            	tests[4] = false;
    		}
    	});
    	everydayF.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue.matches("^([0-1]?\\d|2[0-3])(?::([0-5]?\\d))?") ) {
    			everydayF.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
    			tests[5] = true;
    		}else {
    			everydayF.setStyle("-fx-border-color: red;");
    			tests[5] = false;
    		}
    	});
    	everydayU.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue.matches("^([0-1]?\\d|2[0-3])(?::([0-5]?\\d))?")) {
    			everydayU.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
    			tests[6] = true;
    		}else {
    			everydayU.setStyle("-fx-border-color: red;");
    			tests[6] = false;
    		}
    	});
    	fridayF.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue.matches("^([0-1]?\\d|2[0-3])(?::([0-5]?\\d))?")) {
    			fridayF.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
    			tests[7] = true;
    		}else {
    			fridayF.setStyle("-fx-border-color: red;");
    			tests[7] = false;
    		}
    			
    	});
    	fridayU.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue.matches("^([0-1]?\\d|2[0-3])(?::([0-5]?\\d))?")) {
    			fridayU.setStyle("-fx-border-color: rgba(58, 162, 247, 0.842);");
    			tests[8] = true;
    		}else {
    			fridayU.setStyle("-fx-border-color: red;");
    			tests[8] = false;
    		}
    	});
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
			
		    if(municipalite.getWorkHours() != null) {
				List<String> wHours = Arrays.asList(municipalite.getWorkHours().split(",").clone());
				everydayF.setText(wHours.get(0));
				everydayU.setText(wHours.get(1));
			    fridayF.setText(wHours.get(2));
			    fridayU.setText(wHours.get(3));
			}
			
		}

	}

	public void setMunicipalite(Municipalite municipalite){
		municipalite.setNom(nameField.getText());
		municipalite.setTel(numberField.getText());
		municipalite.setEmail(emailField.getText());
		municipalite.setAdresse(addressField.getText());
		municipalite.setWebsite(websiteField.getText());
		municipalite.setWorkHours(everydayF.getText() + "," + everydayU.getText() + "," + 
								  fridayF.getText() + "," + fridayU.getText());
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
    }
    
    
    @FXML
    void save(ActionEvent event) {
    	for(int i = 0; i < tests.length ; i++) {
    		check = check && tests[i];
    		if(!check) break;
    	}
    	if(check) {
    		setFieldsDisabled(true);
        	this.saveButton.setVisible(false);
        	this.cancelButton.setVisible(false);
        	this.modifyButton.setDisable(false);
			MunicipaliteServiceImpl municipaliteService = new MunicipaliteServiceImpl();
			List<Municipalite> list = municipaliteService.selectAll();
			municipalite = municipaliteService.getById(list.get(0).getId());
    		setMunicipalite(municipalite);
    		municipaliteService.update(municipalite);
    		
    	}else {
    		check = true;
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText(null);
    		alert.setContentText("Check Fields");
    		alert.show();
    	}
    	
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

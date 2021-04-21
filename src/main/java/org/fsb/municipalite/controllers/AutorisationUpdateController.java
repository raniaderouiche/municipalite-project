package org.fsb.municipalite.controllers;

	import org.fsb.municipalite.entities.Autorisation;	
	import org.fsb.municipalite.entities.Autorisation.Etat;

	import javafx.fxml.FXML;
	import javafx.scene.control.RadioButton;
	import javafx.scene.control.TextField;
	import javafx.scene.control.ToggleGroup;

public class AutorisationUpdateController {

	    @FXML
	    private TextField citizenNameField;

	    @FXML
	    private TextField subjectField;
	    
	    @FXML
	    private ToggleGroup statusGroup;
	    
	    @FXML
	    private RadioButton processedRB;

	    @FXML
	    private RadioButton unprocessedRB;
	    
	    @FXML
	    private RadioButton selectedRadioButton;
	    
	    public void getCurrentAutorisation(Autorisation a) {
	    	a.setNomCitoyen(citizenNameField.getText());
	    	a.setSujet(subjectField.getText());
	    	if(processedRB.isSelected()) {
	    		a.setEtat(Etat.processed);
	    	}
	    	if(unprocessedRB.isSelected()) {
	    		a.setEtat(Etat.unprocessed);
	    	}
	    }
	    public void setAutorisationDialogPane(Autorisation a) {
	    	selectedRadioButton = (RadioButton) statusGroup.getSelectedToggle();
	    	
	    	citizenNameField.setText(a.getNomCitoyen());
	    	subjectField.setText(a.getSujet());
	    	if(a.getEtat().equals(Etat.processed)) {
	    		
	    		processedRB.setSelected(true);
	    	}else if(a.getEtat().equals(Etat.unprocessed)){
	    		unprocessedRB.setSelected(true);
	    	}
	    }
}

package org.fsb.municipalite.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SecurityQuestionController implements Initializable{

    @FXML
    private ChoiceBox questionChoice;

    public ChoiceBox getQuestionChoice() {
		return questionChoice;
	}

	public void setQuestionChoice(ChoiceBox questionChoice) {
		this.questionChoice = questionChoice;
	}

	public TextField getAnswer() {
		return answer;
	}

	public void setAnswer(TextField answer) {
		this.answer = answer;
	}

	public Label getInv_answer() {
		return inv_answer;
	}

	public void setInv_answer(Label inv_answer) {
		this.inv_answer = inv_answer;
	}

	@FXML
    private TextField answer;

    @FXML
    private Label inv_answer;
    
    @FXML
    private Label titleLabel;

    ObservableList<String> questionsList = FXCollections.observableArrayList("What was your childhood nickname?", "What is the name of your favorite childhood friend?", "In what city or town was your first job?", "In what city or town did your parents meet?");
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		questionChoice.setItems(questionsList);
	}
    

}

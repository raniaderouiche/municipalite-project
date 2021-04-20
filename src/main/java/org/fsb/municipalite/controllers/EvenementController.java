package org.fsb.municipalite.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.fsb.municipalite.entities.Evenement;
import org.fsb.municipalite.services.impl.EvenementServiceImpl;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EvenementController implements Initializable {


    @FXML
    private TextField searchBox;

    @FXML
    private TableView<Evenement> tableview;

    @FXML
    private TableColumn<Evenement,Long> Id;

    @FXML
    private TableColumn<Evenement,String> Name;

    @FXML
    TableColumn<Evenement, LocalDate> Date;
    @FXML
    TableColumn<Evenement,String> Place;
    @FXML
    TableColumn<Evenement,Long> Budget;
    @FXML
    TableColumn<Evenement,Long> Version;

    ObservableList<Evenement> data;

    //define dialog window offsets here
    private double xOffset = 0;
    private double yOffset = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //search listener
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
            ListenerSearch(newValue);
        });

        //load tableview
        tableview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Id.setCellValueFactory(new PropertyValueFactory<Evenement,Long>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<Evenement,String>("nom"));
        Date.setCellValueFactory(new PropertyValueFactory<Evenement, LocalDate>("dateEvenement"));
        Place.setCellValueFactory(new PropertyValueFactory<Evenement,String>("lieu"));
        Budget.setCellValueFactory(new PropertyValueFactory<Evenement,Long>("budget"));
        Version.setCellValueFactory(new PropertyValueFactory<Evenement,Long>("version"));

        EvenementServiceImpl evenementService = new EvenementServiceImpl();
        List<Evenement> list = evenementService.selectAll();
        data = FXCollections.observableArrayList();

        for(Evenement e : list){
            data.add(e);
        }

        tableview.setItems(data);

    }

    //test if string is numerical
    public boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Test if String is alphabetical
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    private void ListenerSearch(String newValue) {
        EvenementServiceImpl evenementService = new EvenementServiceImpl();
        data = FXCollections.observableArrayList();
        List<Evenement> list;
        if(!(newValue.equals(""))){
            if(isAlpha(newValue)){
                //name search
                list = evenementService.selectLike("nom","'"+newValue+"%'");
                //location search
                if (list.isEmpty()){
                    list = evenementService.selectLike("lieu","'"+newValue+"%'");
                    for(Evenement e : list){
                        data.add(e);
                    }
                }
                for(Evenement e : list){
                    data.add(e);
                }
            }
            //id search
            else {
                list = evenementService.selectLike("id", newValue);
                for(Evenement e : list){
                    data.add(e);
                }
            }
            tableview.setItems(data);
        }
    }

    public void refresh(ActionEvent event) {
        searchBox.clear();
        tableview.getItems().clear();
        EvenementServiceImpl evenementService = new EvenementServiceImpl();
        List<Evenement> list = evenementService.selectAll();
        data = FXCollections.observableArrayList();

        for(Evenement e : list){
            data.add(e);
        }

        tableview.setItems(data);

    }

    public void add(ActionEvent event) {
        try{
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("/interfaces/EvenementDialog.fxml"));
            Pane eventDialogPane = f.load();
            EvenementDialogController edc = f.getController();

            Dialog<ButtonType> d = new Dialog<>();
            //this is just for adding an icon to the dialog pane
            Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/assets/img/icon.png"));

            d.setDialogPane((DialogPane) eventDialogPane);
            d.setTitle("Add Event");
            d.setResizable(false);
            d.initStyle(StageStyle.UNDECORATED);

            eventDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            eventDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    d.setX(event.getScreenX() - xOffset);
                    d.setY(event.getScreenY() - yOffset);
                }
            });

            edc.name.requestFocus();

            //to apply css on the dialog pane buttons
            d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
            d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");

            //listeners
            edc.name.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!isAlpha(newValue)) {
                    edc.inv_name.setVisible(true);
                }else
                    edc.inv_name.setVisible(false);
            });

            edc.budget.textProperty().addListener((observable, oldValue, newValue) -> {
                if(isNumeric(newValue)) {
                    edc.inv_budget.setVisible(false);
                }else
                    edc.inv_budget.setVisible(true);
            });

            edc.eventDate.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != null) {
                    if(newValue.isBefore(LocalDate.now())) {
                        edc.inv_eventdate.setVisible(true);
                    }
                    else {
                        edc.inv_eventdate.setVisible(false);
                    }
                }
            });

            //apply button binder
            d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                            edc.name.getText().isEmpty() || edc.budget.getText().isEmpty() || edc.eventDate.getValue() == null ||
                                     !isNumeric(edc.budget.getText()) || !isAlpha(edc.name.getText()) || edc.eventDate.getValue().isBefore(LocalDate.now()),
                    edc.name.textProperty(),  edc.budget.textProperty(), edc.eventDate.valueProperty()));


            Optional<ButtonType> clickedButton = d.showAndWait();

            if (clickedButton.get() == ButtonType.APPLY){
                EvenementServiceImpl evenementService = new EvenementServiceImpl();
                Evenement evenement = new Evenement();
                edc.setCurrentEvent(evenement);

                evenementService.create(evenement);
                refresh(event);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void update(ActionEvent event) {
        try{
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("/interfaces/EvenementDialog.fxml"));
            Pane eventDialogPane = f.load();
            EvenementDialogController edc = f.getController();

            if(tableview.getSelectionModel().getSelectedItem() != null){
                EvenementServiceImpl evenementService = new EvenementServiceImpl();
                Evenement selectedEvent = (Evenement) tableview.getSelectionModel().getSelectedItem();
                Evenement evenement = evenementService.getById(selectedEvent.getId());

                edc.setEventDialogPane(evenement);
                edc.titleLabel.setText("Update Event");

                Dialog<ButtonType> d = new Dialog<>();
                //this is just for adding an icon to the dialog pane
                Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("/assets/img/icon.png"));

                d.setDialogPane((DialogPane) eventDialogPane);
                d.setTitle("Add Event");
                d.setResizable(false);
                d.initStyle(StageStyle.UNDECORATED);

                eventDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = event.getSceneX();
                        yOffset = event.getSceneY();
                    }
                });

                eventDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        d.setX(event.getScreenX() - xOffset);
                        d.setY(event.getScreenY() - yOffset);
                    }
                });

                edc.name.requestFocus();

                //to apply css on the dialog pane buttons
                d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
                d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");

                //listeners
                edc.name.textProperty().addListener((observable, oldValue, newValue) -> {
                    if(!isAlpha(newValue)) {
                        edc.inv_name.setVisible(true);
                    }else
                        edc.inv_name.setVisible(false);
                });

                edc.budget.textProperty().addListener((observable, oldValue, newValue) -> {
                    if(isNumeric(newValue)) {
                        edc.inv_budget.setVisible(false);
                    }else
                        edc.inv_budget.setVisible(true);
                });

                edc.eventDate.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        if(newValue.isBefore(LocalDate.now())) {
                            edc.inv_eventdate.setVisible(true);
                        }
                        else {
                            edc.inv_eventdate.setVisible(false);
                        }
                    }
                });

                //apply button binder
                d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                                edc.name.getText().isEmpty() || edc.budget.getText().isEmpty() || edc.eventDate.getValue() == null ||
                                        !isNumeric(edc.budget.getText()) || !isAlpha(edc.name.getText()) || edc.eventDate.getValue().isBefore(LocalDate.now()),
                        edc.name.textProperty(),  edc.budget.textProperty(), edc.eventDate.valueProperty()));


                Optional<ButtonType> clickedButton = d.showAndWait();

                if (clickedButton.get() == ButtonType.APPLY){
                    edc.setCurrentEvent(evenement);
                    evenementService.update(evenement);
                    refresh(event);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void remove(ActionEvent event) {
        if(tableview.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            //this is just for adding an icon to the dialog pane
            stage.getIcons().add(new Image("/assets/img/icon.png"));
            alert.setTitle("Delete Event?");
            alert.setHeaderText(null);
            alert.setContentText("Are you Sure You Want to Delete Selected Event(s) ?");
            Optional <ButtonType> action = alert.showAndWait();
            if(action.get() == ButtonType.OK) {
                data = tableview.getSelectionModel().getSelectedItems();
                for(Evenement e : data){
                    EvenementServiceImpl evenementService = new EvenementServiceImpl();
                    evenementService.remove(e.getId());
                }
                refresh(event);
            }
        }
    }
}

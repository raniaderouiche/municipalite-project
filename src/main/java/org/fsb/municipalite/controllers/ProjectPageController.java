package org.fsb.municipalite.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.services.impl.ProjetServiceImpl;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectPageController implements Initializable {

    @FXML TableView<Projet> tableview;
    @FXML TableColumn<Projet,Long> id;
    @FXML TableColumn<Projet,String> name;
    @FXML TableColumn<Projet,LocalDate> startDate;
    @FXML TableColumn<Projet,LocalDate> dueDate;
    @FXML TableColumn<Projet,Integer> budget;
    @FXML TableColumn<Projet,Long> version ;
    @FXML TableColumn<Projet,String> place;
    public ObservableList<Projet> data;
    @FXML
    TextField searchBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<Projet,Long>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Projet,String>("name"));
        startDate.setCellValueFactory(new PropertyValueFactory<Projet, LocalDate>("dateDebut"));
        dueDate.setCellValueFactory(new PropertyValueFactory<Projet,LocalDate>("dateFin"));
        budget.setCellValueFactory(new PropertyValueFactory<Projet, Integer>("budget"));
        version.setCellValueFactory(new PropertyValueFactory<Projet,Long>("version"));
        place.setCellValueFactory(new PropertyValueFactory<Projet,String>("lieu"));

        ProjetServiceImpl projetService = new ProjetServiceImpl();
        List<Projet> list = projetService.selectAll();
        data  =  FXCollections.observableArrayList();
        for(Projet p : list) {
            data.add(p);
        }
        tableview.setItems(data);


    }

    @FXML
    public void refresh(ActionEvent event) {
        ProjetServiceImpl projetService = new ProjetServiceImpl();
        tableview.getItems().clear();
        List<Projet> list = projetService.selectAll();
        data  =  FXCollections.observableArrayList();
        for(Projet p : list) {
            data.add(p);
        }
        tableview.setItems(data);
    }

    public boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public void search(ActionEvent event) {
        if(!(searchBox.getText().isEmpty())){
            tableview.getItems().clear();
            ProjetServiceImpl projetService =new ProjetServiceImpl();
            data  =  FXCollections.observableArrayList();
            if(isNumeric(searchBox.getText())){
                List<Projet> list = projetService.selectBy("id",searchBox.getText());
                for (Projet p : list) {
                    data.add(p);
                }
            }else{
                List<Projet> list = projetService.selectBy("name","'" + searchBox.getText() + "'");
                for (Projet p : list) {
                    data.add(p);
                }
            }
            tableview.setItems(data);
            searchBox.clear();
        }
    }

    @FXML
    public void add(ActionEvent event) {
        try {
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("/interfaces/ProjectAdd.fxml"));
            Pane projetDialogPane = f.load();
            ProjectAddController pac = f.getController();

            Dialog<ButtonType> d = new Dialog<>();
            d.setDialogPane((DialogPane) projetDialogPane);
            d.setTitle("add project");
            Optional<ButtonType> clickedButton = d.showAndWait();
            if(clickedButton.get() == ButtonType.APPLY) {
               Projet projet = new Projet();
               if(!(pac.name.getText().isEmpty())){
                   projet.setName(pac.name.getText());
               }
               if(!(pac.budget.getText().isEmpty())){
                   projet.setBudget(Integer.parseInt(pac.budget.getText()));
               }
               if(!(pac.lieu.getText().isEmpty())){
                   projet.setLieu(pac.lieu.getText());
               }
                if (pac.start.getEditor().getText().length() != 0){
                    projet.setDateDebut(pac.start.getValue());
                }
                if (pac.end.getEditor().getText().length() != 0){
                    projet.setDateFin(pac.end.getValue());
                }
                ProjetServiceImpl projetService = new ProjetServiceImpl();
                projetService.create(projet);
                refresh(event);

            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void remove(ActionEvent event) {
        if(tableview.getSelectionModel().getSelectedItem() != null) {
            Projet m = (Projet) tableview.getSelectionModel().getSelectedItem();
            ProjetServiceImpl projetService = new ProjetServiceImpl();
            projetService.remove(m.getId());
            refresh(event);
        }
    }
    @FXML
    public void update(ActionEvent event) {
        try {
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("/interfaces/ProjectUpdate.fxml"));
            Pane materielDialogPane = f.load();
            ProjectUpdateController puc = f.getController();

            if(tableview.getSelectionModel().getSelectedItem() != null) {

                ProjetServiceImpl projetService = new ProjetServiceImpl();
                Projet m = (Projet) tableview.getSelectionModel().getSelectedItem();
                Projet projet = projetService.getById(m.getId());

                puc.setProjectDialogPane(projet);
                Dialog<ButtonType> d = new Dialog<>();
                d.setDialogPane((DialogPane) materielDialogPane);
                d.setTitle("Update projet");
                Optional<ButtonType> clickedButton = d.showAndWait();
                if(clickedButton.get() == ButtonType.APPLY) {

                    puc.setCurrentProject(projet);
                    projetService.update(projet);
                    refresh(event);
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}

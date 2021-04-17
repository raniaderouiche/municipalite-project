package org.fsb.municipalite.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.fsb.municipalite.entities.Equipe;
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.services.impl.EquipeServiceImpl;
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
    @FXML TableColumn<Projet, Long> teamColumn;
    public ObservableList data;
    @FXML
    TextField searchBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        id.setCellValueFactory(new PropertyValueFactory<Projet,Long>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Projet,String>("name"));
        startDate.setCellValueFactory(new PropertyValueFactory<Projet, LocalDate>("dateDebut"));
        dueDate.setCellValueFactory(new PropertyValueFactory<Projet,LocalDate>("dateFin"));
        budget.setCellValueFactory(new PropertyValueFactory<Projet, Integer>("budget"));
        version.setCellValueFactory(new PropertyValueFactory<Projet,Long>("version"));
        place.setCellValueFactory(new PropertyValueFactory<Projet,String>("lieu"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<Projet,Long>("equipe"));
        data  =  FXCollections.observableArrayList();
        ProjetServiceImpl projetService = new ProjetServiceImpl();
        List<Projet> list = projetService.selectAll();

        for(Projet p : list) {
           /* ObservableList row  =  FXCollections.observableArrayList();
            row.add(p.getId());
            row.add(p.getName());
            row.add(p.getDateDebut());
            row.add(p.getDateFin());
            row.add(p.getBudget());
            row.add(p.getVersion());
            row.add(p.getLieu());
            row.add(p.getEquipe().getId());*/
            data.add(p);
        }
        tableview.setItems(data);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
            ListenerSearch(newValue);
        });

    }

    public void ListenerSearch(String n){
        ProjetServiceImpl projetService = new ProjetServiceImpl();
        data = FXCollections.observableArrayList();
        List<Projet> list;
        if (!(n.equals(""))) {
            if (isNumeric(n)) {
                list = projetService.selectLike("id", n ); //rigel il % kbal ma nbi3ouha
                for (Projet p : list) {
                    data.add(p);
                }
            } else {
                list =projetService.selectLike("name", "'" + n + "%'");
                for (Projet p : list) {
                    data.add(p);
                }
            }
            tableview.setItems(data);
        } else {
            list = projetService.selectAll();
            for (Projet p : list) {
                data.add(p);
            }
            tableview.setItems(data);
        }

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

            //name field listener
            pac.name.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!isAlpha(newValue)) {
                    pac.inv_name.setVisible(true);
                }else
                    pac.inv_name.setVisible(false);
            });

            pac.budget.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!isNumeric(newValue)) {
                    pac.inv_budget.setVisible(true);
                }else
                    pac.inv_budget.setVisible(false);
            });

            pac.start.valueProperty().addListener((observable, oldValue, newValue) -> {
                try{
                    if(newValue != null) {
                        if(newValue.isAfter(pac.end.getValue())) {
                            pac.warning.setVisible(true);
                        }
                        else {
                            pac.warning.setVisible(false);
                        }
                    }
                }catch(Exception e){
                    System.out.println("start date picked");
                }
            });

            pac.end.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != null) {
                    if(newValue.isBefore(pac.start.getValue())) {
                        pac.warning.setVisible(true);
                    }
                    else {
                        pac.warning.setVisible(false);
                    }
                }
            });

            d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                            pac.name.getText().isEmpty()
                                    || pac.budget.getText().isEmpty()
                                    ||!isAlpha(pac.name.getText())
                                    ||!isNumeric(pac.budget.getText())
                                    || pac.start.getValue() == null
                                    || pac.end.getValue() == null
                                    ||pac.start.getValue().isAfter(pac.end.getValue()),
                                    pac.name.textProperty(),
                                    pac.budget.textProperty(),
                                    pac.start.valueProperty(),
                                    pac.end.valueProperty()));


            Optional<ButtonType> clickedButton = d.showAndWait();
            if(clickedButton.get() == ButtonType.APPLY) {
               Projet projet = new Projet();
               ProjetServiceImpl projetService = new ProjetServiceImpl();
               projet.setName(pac.name.getText());
               projet.setBudget(Integer.parseInt(pac.budget.getText()));
               projet.setLieu(pac.place.getValue());
               projet.setDateDebut(pac.start.getValue());
               projet.setDateFin(pac.end.getValue());
               EquipeServiceImpl equipeService = new EquipeServiceImpl();
               Equipe e = equipeService.getById(Long.parseLong(pac.team.getValue().toString().split(",")[0]));
               projet.setEquipe(e);

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
            ObservableList<Projet> selectedItems = tableview.getSelectionModel().getSelectedItems();
            for (Projet p : selectedItems){
                ProjetServiceImpl projetService = new ProjetServiceImpl();
                projetService.remove(p.getId());
            }
        }
        refresh(event);
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

                //name field listener
                puc.name.textProperty().addListener((observable, oldValue, newValue) -> {
                    if(!isAlpha(newValue)) {
                        puc.inv_name.setVisible(true);
                    }else
                        puc.inv_name.setVisible(false);
                });

                puc.budget.textProperty().addListener((observable, oldValue, newValue) -> {
                    if(!isNumeric(newValue)) {
                        puc.inv_budget.setVisible(true);
                    }else
                        puc.inv_budget.setVisible(false);
                });

                puc.start.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        if(newValue.isAfter(puc.end.getValue())) {
                            puc.warning.setVisible(true);
                        }
                        else {
                            puc.warning.setVisible(false);
                        }
                    }
                });

                puc.end.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        if(newValue.isBefore(puc.start.getValue())) {
                            puc.warning.setVisible(true);
                        }
                        else {
                            puc.warning.setVisible(false);
                        }
                    }
                });

                d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                                puc.name.getText().isEmpty()
                                        || puc.budget.getText().isEmpty()
                                        ||!isAlpha(puc.name.getText())
                                        ||!isNumeric(puc.budget.getText())
                                        || puc.start.getValue() == null
                                        || puc.end.getValue() == null
                                        ||puc.start.getValue().isAfter(puc.end.getValue()),
                        puc.name.textProperty(),
                        puc.budget.textProperty(),
                        puc.start.valueProperty(),
                        puc.end.valueProperty()));

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


    //Test if String is alphabetical
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

}

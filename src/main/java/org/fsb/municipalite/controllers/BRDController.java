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
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.fsb.municipalite.entities.Budget;
import org.fsb.municipalite.entities.Depenses;
import org.fsb.municipalite.entities.Revenus;
import org.fsb.municipalite.services.impl.BudgetServiceImpl;
import org.fsb.municipalite.services.impl.DepensesServiceImpl;
import org.fsb.municipalite.services.impl.RevenusServiceImpl;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BRDController implements Initializable {
    //Budgets
    @FXML
    TableView<Budget> budgetTable;
    @FXML
    TableColumn<Budget, Long> id_budget;
    @FXML
    TableColumn<Budget, LocalDate> year;

    @FXML
    TableColumn<Budget, Long> budget;
    @FXML
    TableColumn<Budget, String> secteur;
    @FXML
    TextField budSearchField;

    ObservableList<Budget> BudgetsData;

    //revenus
    @FXML
    TableView<Revenus> revenusTable;
    @FXML
    TableColumn<Revenus, Long> id_revenus;
    @FXML
    TableColumn<Revenus, String> source_rev;
    @FXML
    TableColumn<Revenus, Long> somme_rev;
    @FXML
    TableColumn<Revenus, LocalDate> date_rev;
    @FXML
    TextField revSearchField;

    ObservableList<Revenus> RevenusData;

    //depenses
    @FXML
    TextField depSearchField;
    @FXML
    TableView<Depenses> depensesTable;
    @FXML
    TableColumn<Depenses, Long> id_dep;
    @FXML
    TableColumn<Depenses, Long> somme_dep;
    @FXML
    TableColumn<Depenses, LocalDate> date_dep;
    @FXML
    TableColumn<Depenses, String> secteur_dep;

    public ObservableList<Depenses> DepData;

    //define dialog window offsets here
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //load revenus
        revenusTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        id_revenus.setCellValueFactory(new PropertyValueFactory<Revenus, Long>("id"));
        source_rev.setCellValueFactory(new PropertyValueFactory<Revenus, String>("source_rev"));
        date_rev.setCellValueFactory(new PropertyValueFactory<Revenus, LocalDate>("date_rev"));
        somme_rev.setCellValueFactory(new PropertyValueFactory<Revenus, Long>("somme_rev"));

        RevenusServiceImpl revenusService = new RevenusServiceImpl();
        List<Revenus> revenuslist = revenusService.selectAll();
        RevenusData = FXCollections.observableArrayList();

        for (Revenus e : revenuslist) {
            RevenusData.add(e);
        }

        revenusTable.setItems(RevenusData);

        //search listener revenus
        revSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println("textfield changed from " + oldValue + " to " + newValue);
                    ListenerSearchRevenus(newValue);
                });


        //Budgets
        budgetTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        id_budget.setCellValueFactory(new PropertyValueFactory<Budget, Long>("id"));
        secteur.setCellValueFactory(new PropertyValueFactory<Budget, String>("secteur"));
        year.setCellValueFactory(new PropertyValueFactory<Budget, LocalDate>("year"));
        budget.setCellValueFactory(new PropertyValueFactory<Budget, Long>("budget"));


        BudgetServiceImpl budgetService = new BudgetServiceImpl();
        List<Budget> budgetlist = budgetService.selectAll();
        BudgetsData = FXCollections.observableArrayList();

        for (Budget e : budgetlist) {
            BudgetsData.add(e);
        }

        budgetTable.setItems(BudgetsData);

        //search listener budgets
        budSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ListenerSearchBudgets(newValue);
        });


        //load depenses
        depensesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        id_dep.setCellValueFactory(new PropertyValueFactory<Depenses,Long>("id"));
        secteur_dep.setCellValueFactory(new PropertyValueFactory<Depenses,String>("secteur_dep"));
        date_dep.setCellValueFactory(new PropertyValueFactory<Depenses, LocalDate>("date_dep"));
        somme_dep.setCellValueFactory(new PropertyValueFactory<Depenses,Long>("somme_dep"));


        DepensesServiceImpl depensesService = new DepensesServiceImpl();
        List<Depenses> list = depensesService.selectAll();
        DepData= FXCollections.observableArrayList();

        for(Depenses e : list){
            DepData.add(e);
        }

        //search listener depenses
        depSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            DepListenerSearch(newValue);
        });

        depensesTable.setItems(DepData);
    }

    private void ListenerSearchRevenus(String newValue) {
        RevenusServiceImpl revenusService = new RevenusServiceImpl();
        RevenusData = FXCollections.observableArrayList();
        List<Revenus> list;
        if(!(newValue.equals(""))){
            if(isAlpha(newValue)){
                //Source search
                list = revenusService.selectLike("SOURCE_REV","'"+newValue+"%'");
                //Date search
                if (list.isEmpty()){
                    list = revenusService.selectLike("DATE_REV","'"+newValue+"%'");
                    for(Revenus e : list){
                        RevenusData.add(e);
                    }
                }
                for(Revenus e : list){
                    RevenusData.add(e);
                }
            }
            //id search
            else {
                list = revenusService.selectLike("id", newValue);
                for(Revenus e : list){
                    RevenusData.add(e);
                }
            }
            revenusTable.setItems(RevenusData);
        }
        else{
            list = revenusService.selectAll();
            for(Revenus r : list){
                RevenusData.add(r);
            }
            revenusTable.setItems(RevenusData);
        }
    }
    private void DepListenerSearch(String newValue) {
        DepensesServiceImpl depensesService = new DepensesServiceImpl();
        DepData = FXCollections.observableArrayList();
        List<Depenses> list;
        if(!(newValue.equals(""))){
            if(isAlpha(newValue)){
                //secteur search
                list = depensesService.selectLike("SECTEUR_DEP","'"+newValue+"%'");
                //year search
                if (list.isEmpty()){
                    list = depensesService.selectLike("DATE_DEP","'"+newValue+"%'");
                    for(Depenses e : list){
                        DepData.add(e);
                    }
                }
                for(Depenses e : list){
                    DepData.add(e);
                }
            }
            //id search
            else {
                list = depensesService.selectLike("id", newValue);
                for(Depenses e : list){
                    DepData.add(e);
                }
            }
            depensesTable.setItems(DepData);
        }else{
            list = depensesService.selectAll();
            for(Depenses d : list){
                DepData.add(d);
            }
            depensesTable.setItems(DepData);
        }
    }
    private void ListenerSearchBudgets(String newValue) {
        BudgetServiceImpl budgetService = new BudgetServiceImpl();
        BudgetsData = FXCollections.observableArrayList();
        List<Budget> list;
        if(!(newValue.equals(""))){
            if(isAlpha(newValue)){
                //secteur search
                list = budgetService.selectLike("secteur","'"+newValue+"%'");
                //year search
                if (list.isEmpty()){
                    list = budgetService.selectLike("year","'"+newValue+"%'");
                    for(Budget e : list){
                        BudgetsData.add(e);
                    }
                }
                for(Budget e : list){
                    BudgetsData.add(e);
                }
            }
            //id search
            else {
                list = budgetService.selectLike("id", newValue);
                for(Budget e : list){
                    BudgetsData.add(e);
                }
            }
            budgetTable.setItems(BudgetsData);
        }
        else{
            list = budgetService.selectAll();
            for(Budget e : list){
                BudgetsData.add(e);
            }
            budgetTable.setItems(BudgetsData);
        }
    }

    public void refreshRev(ActionEvent event) {
        revSearchField.clear();
        revenusTable.getItems().clear();
        RevenusServiceImpl revenusService = new RevenusServiceImpl();
        List<Revenus> listR = revenusService.selectAll();
        RevenusData = FXCollections.observableArrayList();

        for(Revenus e : listR){
            RevenusData.add(e);
        }

        revenusTable.setItems(RevenusData);

    }
    public void refreshBud(ActionEvent event) {
        budSearchField.clear();
        budgetTable.getItems().clear();
        BudgetServiceImpl BudgetService = new BudgetServiceImpl();
        List<Budget> list = BudgetService.selectAll();
        BudgetsData = FXCollections.observableArrayList();

        for(Budget e : list){
            BudgetsData.add(e);
        }

        budgetTable.setItems(BudgetsData);

    }
    public void refreshDep(ActionEvent event) {
        depSearchField.clear();
        depensesTable.getItems().clear();
        DepensesServiceImpl depensesService = new DepensesServiceImpl();
        List<Depenses> list = depensesService.selectAll();
        DepData = FXCollections.observableArrayList();

        for(Depenses e : list){
            DepData.add(e);
        }
        depensesTable.setItems(DepData);

    }

    public void addBud(ActionEvent event) {
        try{
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("/interfaces/BudgetDialog.fxml"));
            Pane budgetDialogPane = f.load();
            BudgetDialogController edc = f.getController();

            Dialog<ButtonType> d = new Dialog<>();


            d.setDialogPane((DialogPane) budgetDialogPane);
            d.setTitle("Add budget");
            d.setResizable(false);
            d.initStyle(StageStyle.UNDECORATED);

            budgetDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            budgetDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    d.setX(event.getScreenX() - xOffset);
                    d.setY(event.getScreenY() - yOffset);
                }
            });

            edc.secteur.requestFocus();

            //to apply css on the dialog pane buttons
            d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
            d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");

            //listeners
            edc.secteur.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!isAlpha(newValue)) {
                    edc.inv_sec.setVisible(true);
                }else
                    edc.inv_sec.setVisible(false);
            });


            edc.budget.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!isNumeric(newValue)) {
                    edc.inv_budget.setVisible(true);
                }else
                    edc.inv_budget.setVisible(false);
            });

            edc.year.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != null) {
                    if (newValue.isBefore(LocalDate.now())) {
                        edc.inv_budgetdate.setVisible(true);
                    } else {
                        edc.inv_budgetdate.setVisible(false);
                    }
                }
            });



            //apply button binder
            d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                            edc.secteur.getText().isEmpty() || edc.budget.getText().isEmpty() || edc.year.getValue()==null ||
                                    !isNumeric(edc.budget.getText()) || !isAlpha(edc.secteur.getText()) || edc.year.getValue().isBefore(LocalDate.now()),
                    edc.secteur.textProperty(),  edc.budget.textProperty(), edc.year.valueProperty()));

            Optional<ButtonType> clickedButton = d.showAndWait();

            if (clickedButton.get() == ButtonType.APPLY){
                BudgetServiceImpl budgetService = new BudgetServiceImpl();
                Budget budget = new Budget();
                edc.setCurrentBudget(budget);
                budgetService.create(budget);
                refreshBud(event);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updateBud(ActionEvent event) {
        try {
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("/interfaces/BudgetDialog.fxml"));
            Pane budgetDialogPane = f.load();
            BudgetDialogController bud = f.getController();

            if (budgetTable.getSelectionModel().getSelectedItem() != null) {
                BudgetServiceImpl budgetService = new BudgetServiceImpl();
                Budget selectedEvent = (Budget) budgetTable.getSelectionModel().getSelectedItem();
                Budget budget = budgetService.getById(selectedEvent.getId());

                bud.setBudgetDialogPane(budget);
                bud.titleLabel.setText("Update budget");

                Dialog<ButtonType> d = new Dialog<>();

                d.setDialogPane((DialogPane) budgetDialogPane);
                d.setTitle("Add budget");
                d.setResizable(false);
                d.initStyle(StageStyle.UNDECORATED);

                budgetDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = event.getSceneX();
                        yOffset = event.getSceneY();
                    }
                });

                budgetDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        d.setX(event.getScreenX() - xOffset);
                        d.setY(event.getScreenY() - yOffset);
                    }
                });

                bud.secteur.requestFocus();

                //to apply css on the dialog pane buttons
                d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
                d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");

                //listeners
                bud.secteur.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!isAlpha(newValue)) {
                        bud.inv_sec.setVisible(true);
                    } else
                        bud.inv_sec.setVisible(false);
                });

                bud.budget.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!isNumeric(newValue)) {
                        bud.inv_budget.setVisible(false);
                    } else
                        bud.inv_budget.setVisible(true);
                });

                bud.year.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                            if (newValue.isBefore(LocalDate.now())) {
                                bud.inv_budgetdate.setVisible(true);
                            }
                            else {
                                bud.inv_budgetdate.setVisible(false);
                            }
                    }
                });


                    //apply button binder
                    d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                                    bud.secteur.getText().isEmpty() || bud.budget.getText().isEmpty()
                                            || bud.year.getValue() == null
                                            || !isAlpha(bud.secteur.getText())
                                            || !isAlpha(bud.budget.getText())
                                            || bud.year.getValue().isBefore(LocalDate.now()),
                            bud.secteur.textProperty(), bud.budget.textProperty(), bud.year.valueProperty()));


                    Optional<ButtonType> clickedButton = d.showAndWait();

                    if (clickedButton.get() == ButtonType.APPLY) {
                        bud.setCurrentBudget(budget);
                        budgetService.update(budget);
                        refreshBud(event);
                    }
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }

    public void deleteBud(ActionEvent event) {
        if(budgetTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            //this is just for adding an icon to the dialog pane
            stage.getIcons().add(new Image("/assets/img/icon.png"));
            alert.setTitle("Delete budget?");
            alert.setHeaderText(null);
            alert.setContentText("Are you Sure You Want to Delete Selected budget(s) ?");
            Optional <ButtonType> action = alert.showAndWait();
            if(action.get() == ButtonType.OK) {
                BudgetsData = budgetTable.getSelectionModel().getSelectedItems();
                for(Budget e : BudgetsData){
                    BudgetServiceImpl budgetService = new BudgetServiceImpl();
                    budgetService.remove(e.getId());
                }
                refreshBud(event);
            }
        }
    }

    public void addRev(ActionEvent event) {
        try{
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("/interfaces/RevenusDialog.fxml"));
            Pane revDialogPane = f.load();
            RevenusDialogController edc = f.getController();

            Dialog<ButtonType> d = new Dialog<>();


            d.setDialogPane((DialogPane) revDialogPane);
            d.setTitle("Add revenu");
            d.setResizable(false);
            d.initStyle(StageStyle.UNDECORATED);

            revDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            revDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    d.setX(event.getScreenX() - xOffset);
                    d.setY(event.getScreenY() - yOffset);
                }
            });

            //listeners
            edc.source_rev.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!isAlpha(newValue)) {
                    edc.inv_source.setVisible(true);
                }else
                    edc.inv_source.setVisible(false);
            });


            edc.somme_rev.textProperty().addListener((observable, oldValue, newValue) -> {
                if(isNumeric(newValue)) {
                    edc.inv_somme.setVisible(false);
                }else
                    edc.inv_somme.setVisible(true);
            });

            edc.date_rev.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != null) {
                    if(newValue.isBefore(LocalDate.now())) {
                        edc.inv_revdate.setVisible(false);
                    }
                }else
                    edc.inv_revdate.setVisible(true);

            });


            //to apply css on the dialog pane buttons
            d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
            d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");

            //make date field first to be selected
            edc.source_rev.requestFocus();
            //apply button binder
            d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                            edc.source_rev.getText().isEmpty() || edc.somme_rev.getText().isEmpty() || edc.date_rev.getValue() == null ||
                                    !isNumeric(edc.somme_rev.getText()) || !isAlpha(edc.source_rev.getText()) || edc.date_rev.getValue().isBefore(LocalDate.now()),
                    edc.source_rev.textProperty(),  edc.somme_rev.textProperty(), edc.date_rev.valueProperty()));


            Optional<ButtonType> clickedButton = d.showAndWait();
            //new revenu creation and addition
            if (clickedButton.get() == ButtonType.APPLY){
                RevenusServiceImpl revenusService = new RevenusServiceImpl();
                Revenus revenus = new Revenus();
                edc.setCurrentRevenus(revenus);
                System.out.println(revenus);
                revenusService.create(revenus);
                refreshRev(event);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updateRev(ActionEvent event) {
        try{
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("/interfaces/RevenusDialog.fxml"));
            Pane RevDialogPane = f.load();
            RevenusDialogController edc = f.getController();

            if(revenusTable.getSelectionModel().getSelectedItem() != null){
                RevenusServiceImpl revenusService = new RevenusServiceImpl();
                Revenus selectedRevenus = (Revenus) revenusTable.getSelectionModel().getSelectedItem();
                Revenus revenus = revenusService.getById(selectedRevenus.getId());

                edc.setRevenusDialogPane(revenus);
                edc.titleLabel.setText("Update revenus");

                Dialog<ButtonType> d = new Dialog<>();

                d.setDialogPane((DialogPane) RevDialogPane);
                d.setTitle("Add revenus");
                d.setResizable(false);
                d.initStyle(StageStyle.UNDECORATED);

                RevDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = event.getSceneX();
                        yOffset = event.getSceneY();
                    }
                });

                RevDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        d.setX(event.getScreenX() - xOffset);
                        d.setY(event.getScreenY() - yOffset);
                    }
                });

                edc.source_rev.requestFocus();

                //to apply css on the dialog pane buttons
                d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
                d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");

                //listeners
                edc.source_rev.textProperty().addListener((observable, oldValue, newValue) -> {
                    if(!isAlpha(newValue)) {
                        edc.inv_source.setVisible(true);
                    }else
                        edc.inv_source.setVisible(false);
                });

                edc.somme_rev.textProperty().addListener((observable, oldValue, newValue) -> {
                    if(!isNumeric(newValue)) {
                        edc.inv_somme.setVisible(false);
                    }else
                        edc.inv_somme.setVisible(true);
                });

                edc.date_rev.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                            if(newValue.isBefore(LocalDate.now())) {
                            edc.inv_revdate.setVisible(true);
                        }
                        else {
                            edc.inv_revdate.setVisible(false);
                        }
                    }
                });

                //apply button binder
                d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                                edc.source_rev.getText().isEmpty() || edc.somme_rev.getText().isEmpty() || edc.date_rev.getValue() == null ||
                                        !isNumeric(edc.somme_rev.getText()) || !isAlpha(edc.source_rev.getText()) || edc.date_rev.getValue().isBefore(LocalDate.now()),
                        edc.source_rev.textProperty(),  edc.somme_rev.textProperty(), edc.date_rev.valueProperty()));


                Optional<ButtonType> clickedButton = d.showAndWait();

                if (clickedButton.get() == ButtonType.APPLY){
                    edc.setCurrentRevenus(revenus);
                    revenusService.update(revenus);
                    refreshRev(event);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void deleteRev(ActionEvent event) {
        if(revenusTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/assets/img/icon.png"));
            alert.setTitle("Delete revenus?");
            alert.setHeaderText(null);
            alert.setContentText("Are you Sure You Want to Delete Selected revenu(s) ?");
            Optional <ButtonType> action = alert.showAndWait();
            if(action.get() == ButtonType.OK) {
                RevenusData = revenusTable.getSelectionModel().getSelectedItems();
                for(Revenus e : RevenusData){
                    RevenusServiceImpl revenusService = new RevenusServiceImpl();
                    revenusService.remove(e.getId());
                }
                refreshRev(event);
            }
        }
    }

    public void addDep(ActionEvent event) {
        try{
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("/interfaces/DepensesDialog.fxml"));
            Pane depDialogPane = f.load();
            DepensesDialogController edc = f.getController();

            Dialog<ButtonType> d = new Dialog<>();


            d.setDialogPane((DialogPane) depDialogPane);
            d.setTitle("Add Depenses");
            d.setResizable(false);
            d.initStyle(StageStyle.UNDECORATED);

            depDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            depDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    d.setX(event.getScreenX() - xOffset);
                    d.setY(event.getScreenY() - yOffset);
                }
            });

            edc.secteur_dep.requestFocus();

            //to apply css on the dialog pane buttons
            d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
            d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");

            //listeners
            edc.secteur_dep.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!isAlpha(newValue)) {
                    edc.inv_depsec.setVisible(true);
                }else
                    edc.inv_depsec.setVisible(false);
            });


            edc.somme_dep.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!isNumeric(newValue)) {
                    edc.inv_depsomme.setVisible(true);
                }else
                    edc.inv_depsomme.setVisible(false);
            });

            edc.date_dep.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != null) {
                    if(newValue.isBefore(LocalDate.now())) {
                        edc.inv_depdate.setVisible(true);
                    }
                    else{
                        edc.inv_depdate.setVisible(false);
                    }

                }});

            //apply button binder
            d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                            edc.secteur_dep.getText().isEmpty() || edc.somme_dep.getText().isEmpty() || edc.date_dep.getValue() == null ||
                                    !isNumeric(edc.somme_dep.getText()) || !isAlpha(edc.secteur_dep.getText()) || edc.date_dep.getValue().isBefore(LocalDate.now()) ,
                    edc.secteur_dep.textProperty(),  edc.somme_dep.textProperty(), edc.date_dep.valueProperty()));

            Optional<ButtonType> clickedButton = d.showAndWait();

            if (clickedButton.get() == ButtonType.APPLY){
                DepensesServiceImpl depensesService = new DepensesServiceImpl();
                Depenses depenses = new Depenses();
                edc.setCurrentDepenses(depenses);

                depensesService.create(depenses);
                refreshDep(event);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updateDep(ActionEvent event) {
        try{
            FXMLLoader f = new FXMLLoader();
            f.setLocation(getClass().getResource("/interfaces/DepensesDialog.fxml"));
            Pane depDialogPane = f.load();
            DepensesDialogController edc = f.getController();

            if(depensesTable.getSelectionModel().getSelectedItem() != null){
                DepensesServiceImpl depensesService = new DepensesServiceImpl();
                Depenses selectedEvent = (Depenses) depensesTable.getSelectionModel().getSelectedItem();
                Depenses depenses = depensesService.getById(selectedEvent.getId());

                edc.setDepensesDialogPane(depenses);
                edc.titleLabel.setText("Update Depenses");

                Dialog<ButtonType> d = new Dialog<>();

                d.setDialogPane((DialogPane) depDialogPane);
                d.setTitle("Add Depenses");
                d.setResizable(false);
                d.initStyle(StageStyle.UNDECORATED);

                depDialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = event.getSceneX();
                        yOffset = event.getSceneY();
                    }
                });

               depDialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        d.setX(event.getScreenX() - xOffset);
                        d.setY(event.getScreenY() - yOffset);
                    }
                });

                edc.secteur_dep.requestFocus();

                //to apply css on the dialog pane buttons
                d.getDialogPane().lookupButton(ButtonType.APPLY).getStyleClass().add("dialogButtons");
                d.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("dialogButtons");

                //listeners
                edc.secteur_dep.textProperty().addListener((observable, oldValue, newValue) -> {
                    if(!isAlpha(newValue)) {
                        edc.inv_depsec.setVisible(true);
                    }else
                        edc.inv_depsec.setVisible(false);
                });

                edc.somme_dep.textProperty().addListener((observable, oldValue, newValue) -> {
                    if(!isNumeric(newValue)) {
                        edc.inv_depsomme.setVisible(false);
                    }else
                        edc.inv_depsomme.setVisible(true);
                });

                edc.date_dep.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        if(newValue.isBefore(LocalDate.now())) {
                            edc.inv_depdate.setVisible(true);
                        }
                        else {
                            edc.inv_depdate.setVisible(false);
                        }
                    }
                });

                //apply button binder
                d.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(Bindings.createBooleanBinding(() ->
                                edc.secteur_dep.getText().isEmpty() || edc.somme_dep.getText().isEmpty() || edc.date_dep.getValue() == null ||
                                        !isNumeric(edc.somme_dep.getText()) || !isAlpha(edc.secteur_dep.getText()) || edc.date_dep.getValue().isBefore(LocalDate.now()),
                        edc.secteur_dep.textProperty(),  edc.somme_dep.textProperty(), edc.date_dep.valueProperty()));


                Optional<ButtonType> clickedButton = d.showAndWait();

                if (clickedButton.get() == ButtonType.APPLY){
                    edc.setCurrentDepenses(depenses);
                    depensesService.update(depenses);
                    refreshDep(event);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void removeDep(ActionEvent event) {
        if(depensesTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            //this is just for adding an icon to the dialog pane
            stage.getIcons().add(new Image("/assets/img/icon.png"));
            alert.setTitle("Delete Depenses?");
            alert.setHeaderText(null);
            alert.setContentText("Are you Sure You Want to Delete Selected Depenses ?");
            Optional <ButtonType> action = alert.showAndWait();
            if(action.get() == ButtonType.OK) {
                DepData = depensesTable.getSelectionModel().getSelectedItems();
                for(Depenses e : DepData){
                    DepensesServiceImpl depenseService = new DepensesServiceImpl();
                    depenseService.remove(e.getId());
                }
                refreshDep(event);
            }
        }
    }


    //*******
    public boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z ]+");
    }
//*********
          @FXML
          void selectAllB(ActionEvent event) {
           this.budgetTable.getSelectionModel().selectAll();
}
        @FXML
        void selectAllD(ActionEvent event) {
        this.depensesTable.getSelectionModel().selectAll();
    }

         @FXML
         void selectAllR(ActionEvent event) {
        this.revenusTable.getSelectionModel().selectAll();
    }


}








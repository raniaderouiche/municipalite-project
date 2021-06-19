package org.fsb.municipalite.controllers;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import org.fsb.municipalite.entities.Budget;
import org.fsb.municipalite.entities.Depenses;
import org.fsb.municipalite.entities.Revenus;
import org.fsb.municipalite.services.impl.*;


import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;



public class DashboardFController implements Initializable {
    @FXML
    PieChart budgetChart;
    @FXML
    PieChart depenseChart;
    @FXML
    PieChart revenuChart;
    @FXML
    BarChart profitChart;

    LocalDate currentdate = LocalDate.now();
    int currentYear = currentdate.getYear();
    double ecartM = 0L;
    double ecartAut = 0L;
    double ecartT = 0L;
    double ecartcom = 0L;
    double ecartPro = 0L;
    double ecarttas = 0L;
    double ecartEvn = 0L;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Budgetload();
        Revenusload();
        Depensesload();
        profitload();

    }

    public void Budgetload() {
        BudgetServiceImpl budgetService = new BudgetServiceImpl();
        ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
        List<Budget> budlistt = budgetService.selectBy("SECTEUR", "'Teams'");
        double budT = 0L;
        for (Budget b : budlistt) {
            if (b.getYear().getYear() == currentYear) {
                budT = budT + b.getBudget();


            }
        }
        ecartT = budT;
        List<Budget> budlista = budgetService.selectBy("SECTEUR", "'Authorization'");
        double budAut = 0L;
        for (Budget b : budlista) {
            if (b.getYear().getYear() == currentYear) {
                budAut = budAut + b.getBudget();


            }
        }
        ecartAut = budAut;
        List<Budget> budlistp = budgetService.selectBy("SECTEUR", "'Projects'");
        double budPro = 0L;
        for (Budget b : budlistp) {
            if (b.getYear().getYear() == currentYear) {
                budPro = budPro + b.getBudget();


            }
        }
        ecartPro = budPro;
        List<Budget> budlistc = budgetService.selectBy("SECTEUR", "'Complaints'");
        double budcom = 0L;
        for (Budget b : budlistc) {
            if (b.getYear().getYear() == currentYear) {
                budcom = budcom + b.getBudget();


            }
        }
        ecartcom = budcom;

        List<Budget> budliste = budgetService.selectBy("SECTEUR", "'Events'");
        double budEvn = 0L;
        for (Budget b : budliste) {
            if (b.getYear().getYear() == currentYear) {
                budEvn = budEvn + b.getBudget();


            }
        }
        ecartEvn = budEvn;
        List<Budget> budlistm = budgetService.selectBy("SECTEUR", "'Tools'");
        double budM = 0L;
        for (Budget b : budlistm) {
            if (b.getYear().getYear() == currentYear) {
                budM = budM + b.getBudget();


            }
        }
        ecartM = budM;
        List<Budget> budlisttas = budgetService.selectBy("SECTEUR", "'Tasks'");
        double budtas = 0L;
        for (Budget b : budlisttas) {
            if (b.getYear().getYear() == currentYear) {
                budtas = budtas + b.getBudget();


            }
        }
        ecarttas = budtas;

        pie.add(new PieChart.Data("Authorization", budAut));
        pie.add(new PieChart.Data("Complaints", budcom));
        pie.add(new PieChart.Data("Teams", budT));
        pie.add(new PieChart.Data("Tasks", budtas));
        pie.add(new PieChart.Data("Tools", budM));
        pie.add(new PieChart.Data("Projects", budPro));
        pie.add(new PieChart.Data("Events", budEvn));
        pie.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName()," sum: ",data.pieValueProperty()
                        )
                ));
        budgetChart.setData(pie);
    }


    public void Depensesload() {
        DepensesServiceImpl depenseService = new DepensesServiceImpl();
        ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
        List<Depenses> deplisttas = depenseService.selectBy("SECTEUR_DEP", "'Tasks'");
        double deptas = 0L;
        for (Depenses d : deplisttas) {
            if (d.getDate_dep().getYear() == currentYear) {
                deptas = deptas + d.getSomme_dep();


            }
        }
        ecarttas = ecarttas - deptas;

        List<Depenses> deplistt = depenseService.selectBy("SECTEUR_DEP", "'Teams'");
        double depT = 0L;
        for (Depenses d : deplistt) {
            if (d.getDate_dep().getYear() == currentYear) {
                depT = depT + d.getSomme_dep();


            }
        }
        ecartT = ecartT - depT;

        List<Depenses> deplistm = depenseService.selectBy("SECTEUR_DEP", "'Tools'");
        double depM = 0L;
        for (Depenses d : deplistm) {
            if (d.getDate_dep().getYear() == currentYear) {
                depM = depM + d.getSomme_dep();


            }
        }
        ecartM = ecartM - depM;
        List<Depenses> deplista = depenseService.selectBy("SECTEUR_DEP", "'Authorization'");
        double depAut = 0L;
        for (Depenses d : deplista) {
            if (d.getDate_dep().getYear() == currentYear) {
                depAut = depAut + d.getSomme_dep();


            }
        }
        ecartAut = ecartAut - depAut;
        List<Depenses> deplistc = depenseService.selectBy("SECTEUR_DEP", "'Complaints'");
        double depcom = 0L;
        for (Depenses d : deplistc) {
            if (d.getDate_dep().getYear() == currentYear) {
                depcom = depcom + d.getSomme_dep();


            }
        }
        ecartcom = ecartcom - depcom;
        List<Depenses> depliste = depenseService.selectBy("SECTEUR_DEP", "'Events'");
        double depEvn = 0L;
        for (Depenses d : depliste) {
            if (d.getDate_dep().getYear() == currentYear) {
                depEvn = depEvn + d.getSomme_dep();


            }
        }
        ecartEvn = ecartEvn - depEvn;
        List<Depenses> deplistp = depenseService.selectBy("SECTEUR_DEP", "'Projects'");
        double depPro = 0L;
        for (Depenses d : deplistp) {
            if (d.getDate_dep().getYear() == currentYear) {
                depPro = depPro + d.getSomme_dep();


            }
        }
        ecartPro = ecartPro - depPro;
        pie.add(new PieChart.Data("Authorization", depAut));
        pie.add(new PieChart.Data("Complaints", depcom));
        pie.add(new PieChart.Data("Teams", depT));
        pie.add(new PieChart.Data("Tasks", deptas));
        pie.add(new PieChart.Data("Tools", depM));
        pie.add(new PieChart.Data("Projects", depPro));
        pie.add(new PieChart.Data("Events", depEvn));
        pie.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), "sum:", data.pieValueProperty()
                        )
                ));
        depenseChart.setData(pie);
    }

    public void Revenusload() {
        RevenusServiceImpl revenuService = new RevenusServiceImpl();

        ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();

        List<Revenus> revlisttas = revenuService.selectBy("SOURCE_REV", "'Tasks'");
        double revtas = 0L;
        for (Revenus r : revlisttas) {
            if (r.getDate_rev().getYear() == currentYear) {
                revtas = revtas + r.getSomme_rev();


            }
        }
        ecarttas = ecarttas + revtas;
        List<Revenus> revlistt = revenuService.selectBy("SOURCE_REV", "'Teams'");
        double revT = 0L;
        for (Revenus r : revlistt) {
            if (r.getDate_rev().getYear() == currentYear) {
                revT = revT + r.getSomme_rev();


            }
        }
        ecartT = ecartT + revT;
        List<Revenus> revlistm = revenuService.selectBy("SOURCE_REV", "'Tools'");
        double revM = 0L;
        for (Revenus r : revlistm) {
            if (r.getDate_rev().getYear() == currentYear) {
                revM = revM + r.getSomme_rev();


            }
        }
        ecartM = ecartM + revM;
        List<Revenus> revlista = revenuService.selectBy("SOURCE_REV", "'Authorization'");
        double revAut = 0L;
        for (Revenus r : revlista) {
            if (r.getDate_rev().getYear() == currentYear) {
                revAut = revAut + r.getSomme_rev();


            }
        }
        ecartAut = ecartAut + revAut;
        List<Revenus> revlistp = revenuService.selectBy("SOURCE_REV", "'Projects'");
        double revPro = 0L;
        for (Revenus r : revlistp) {
            if (r.getDate_rev().getYear() == currentYear) {
                revPro = revPro + r.getSomme_rev();


            }
        }
        ecartPro = ecartPro + revPro;
        List<Revenus> revliste = revenuService.selectBy("SOURCE_REV", "'Events'");
        double revEvn = 0L;
        for (Revenus r : revliste) {
            if (r.getDate_rev().getYear() == currentYear) {
                revEvn = revEvn + r.getSomme_rev();


            }
        }
        ecartEvn = ecartEvn + revEvn;
        List<Revenus> revlistc = revenuService.selectBy("SOURCE_REV", "'Complaints'");
        double revcom = 0L;
        for (Revenus r : revlistc) {
            if (r.getDate_rev().getYear() == currentYear) {
                revcom = revcom + r.getSomme_rev();


            }
        }
        ecartcom = ecartcom + revcom;

        pie.add(new PieChart.Data("Authorizations", revAut));
        pie.add(new PieChart.Data("Complaints", revcom));
        pie.add(new PieChart.Data("Teams", revT));
        pie.add(new PieChart.Data("Tasks", revtas));
        pie.add(new PieChart.Data("Tools", revM));
        pie.add(new PieChart.Data("Projects", revPro));
        pie.add(new PieChart.Data("Events", revEvn));

        pie.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), "sum:", data.pieValueProperty()
                        )
                ));
        revenuChart.setData(pie);
    }

    public void profitload() {
        XYChart.Series profit = new XYChart.Series();

        profit.setName("current year profit");
        profit.getData().add(new XYChart.Data(ecartAut,"Authorization"));
        profit.getData().add(new XYChart.Data(ecartcom,"Complaints"));
        profit.getData().add(new XYChart.Data(ecarttas,"Tasks"));
        profit.getData().add(new XYChart.Data(ecartT,"Teams"));
        profit.getData().add(new XYChart.Data(ecartPro,"Projects"));
        profit.getData().add(new XYChart.Data(ecartEvn,"Events"));
        profit.getData().add(new XYChart.Data(ecartM,"Tools"));
        profitChart.getData().addAll(profit);





    }
}

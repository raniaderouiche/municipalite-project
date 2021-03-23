package org.fsb.municipalite;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.fsb.municipalite.entities.Employee;
import org.fsb.municipalite.entities.Projet;
import org.fsb.municipalite.services.impl.EmployeeServiceImpl;
import org.fsb.municipalite.services.impl.ProjetServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class App extends Application {



    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/HomePage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        //create entity manager instance and run it for a first time to migrate changes
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("todo");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.close();

      /*  Employee employee = new Employee();
        employee.setNom("Rania");
        employee.setPrenom("Derouiche");


        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        System.out.print(employeeService.create(employee));*/

    /*
        Projet projet = new Projet();
        projet.setBudget(100);

        ProjetServiceImpl projetService = new ProjetServiceImpl();
        System.out.println(projetService.create(projet));
    */
        //launch app window
        launch();
    }
}
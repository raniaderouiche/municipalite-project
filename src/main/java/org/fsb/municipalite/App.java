package org.fsb.municipalite;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class App extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/LoginScene.fxml"));
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

        /*Materiel materiel = new Materiel();
        materiel.setReference(Long.valueOf(100000));
        materiel.setNom("TEST");
        MaterielServiceImpl materielService = new MaterielServiceImpl();
        System.out.println(materielService.create(materiel));*/

      /*  Employee employee = new Employee();
        employee.setNom("Rania");
        employee.setPrenom("Derouiche");


        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        System.out.print(employeeService.create(employee));*/


       /* Projet projet = new Projet();
        projet.setBudget(300);

        ProjetServiceImpl projetService = new ProjetServiceImpl();
        System.out.println(projetService.create(projet));*/

        //launch app window
        launch();
    }
}
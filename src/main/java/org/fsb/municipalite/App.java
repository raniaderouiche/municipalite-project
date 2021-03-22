package org.fsb.municipalite;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.IOException;


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

        //launch app window
        launch(args);
    }
}
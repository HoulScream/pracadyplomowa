package common;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static common.ConfigurationBuilder.loadConfiguration;


public class Main extends Application
{
    public static EntityManagerFactory emf;

    public static void main(String[] args)
    {
        launch(args);
        emf.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        emf = Persistence.createEntityManagerFactory("appdb");

        loadConfiguration();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/LoginScene.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Wypożyczalnia 1.0 - Logowanie");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}

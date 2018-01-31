package controllers;

import common.Main;

import entity.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;



public class LoginSceneController {

    @FXML
    private Label infoLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    void login(ActionEvent event) {
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            Query q = manager.createQuery("select u from UserEntity u where u.login = ?1 and u.password = ?2", UserEntity.class);
            q.setParameter(1, loginField.getText());
            q.setParameter(2, passwordField.getText());
            UserEntity ue = (UserEntity) q.getSingleResult();
            if (loginField.getText().equals(ue.getLogin()) && passwordField.getText().equals(ue.getPassword())) {
                if (ue.getAdmin() == 1) {
                    Stage dialogStage;
                    Scene scene;
                    Node source = (Node) event.getSource();
                    dialogStage = (Stage) source.getScene().getWindow();
                    dialogStage.close();
                    scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/AdminWindowScene.fxml")));
                    dialogStage.setScene(scene);
                    dialogStage.setTitle("Wypożyczalnia 1.0 - Strona główna");
                    dialogStage.setResizable(false);
                    dialogStage.show();
                } else {
                    Stage dialogStage;
                    Scene scene;
                    Node source = (Node) event.getSource();
                    dialogStage = (Stage) source.getScene().getWindow();
                    dialogStage.close();
                    scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/UserWindowScene.fxml")));
                    dialogStage.setScene(scene);
                    dialogStage.setResizable(false);
                    dialogStage.show();
                }

            } else {
                infoLabel.setText("Niepoprawny login lub hasło");
            }

        } catch (NoResultException nre) {
            infoLabel.setText("Niepoprawny login lub hasło");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    @FXML
    void clean(ActionEvent event) {
        loginField.clear();
        passwordField.clear();
        infoLabel.setText("");
    }

}
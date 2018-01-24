package controllers.common;

import connection.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LoginSceneController
{

    @FXML
    private Label infoLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    void login(ActionEvent event)
    {
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        String login = loginField.getText().toString();
        String password = passwordField.getText().toString();

        String sql = "SELECT * FROM user WHERE login = ? and password = ?";

        try
        {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
            {
                infoLabel.setTextFill(Paint.valueOf("RED"));
                infoLabel.setText("Wprowadzono niepoprawny login lub hasło");
            }
            else
            {
                infoLabel.setTextFill(Paint.valueOf("GREEN"));
                infoLabel.setText("Logowanie udane");

                if(resultSet.getInt(4) == 1)
                {
                    Stage dialogStage;
                    Scene scene;
                    Node source = (Node) event.getSource();
                    dialogStage = (Stage) source.getScene().getWindow();
                    dialogStage.close();
                    scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/admin/AdminWindowScene.fxml")));
                    dialogStage.setScene(scene);
                    dialogStage.setTitle("Wypożyczalnia 1.0 - Strona główna");
                    dialogStage.setResizable(false);
                    dialogStage.show();
                }
                else
                {
                    Stage dialogStage;
                    Scene scene;
                    Node source = (Node) event.getSource();
                    dialogStage = (Stage) source.getScene().getWindow();
                    dialogStage.close();
                    scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/user/UserMainScene.fxml")));
                    dialogStage.setScene(scene);
                    dialogStage.setResizable(false);
                    dialogStage.show();
                }

            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    void clean(ActionEvent event)
    {
        loginField.clear();
        passwordField.clear();
        infoLabel.setText("");
    }

}
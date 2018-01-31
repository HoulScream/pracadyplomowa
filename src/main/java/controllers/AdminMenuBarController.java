package controllers;

import common.DialogBoxBuilder;
import common.FxmlPathBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Optional;

public class AdminMenuBarController {

    @FXML
    private Button logoutButton;

    @FXML
    private Button rentalSceneButton;

    @FXML
    private Button itemSceneButton;

    @FXML
    private Button mainSceneButton;

    @FXML
    private Button manageSceneButton;

    @FXML
    private Button settingsSceneButton;

    @FXML
    private Button clientSceneButton;

    @FXML
    private Button returnSceneButton;

    private AdminWindowController adminWindowController;
    private static final String ADMIN_MAIN_SCENE = "/fxml/AdminMainScene.fxml";
    private static final String ADMIN_CLIENT_SCENE = "/fxml/ClientScene.fxml";
    private static final String ADMIN_ITEM_SCENE = "/fxml/ItemScene.fxml";
    private static final String ADMIN_RENTAL_SCENE = "/fxml/RentalScene.fxml";
    private static final String ADMIN_RETURN_SCENE = "/fxml/ReturnScene.fxml";
    private static final String ADMIN_OUTDATED_SCENE = "/fxml/OutdatedScene.fxml";
    private static final String ADMIN_SETTINGS_SCENE = "/fxml/SettingsScene.fxml";
    private static final String LOGIN_SCENE = "/fxml/LoginScene.fxml";

    @FXML
    void showAdminMainScene(ActionEvent event) {
        adminWindowController.selectScene(ADMIN_MAIN_SCENE);
        Stage dialogStage;
        Node source = (Node) event.getSource();
        dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.setTitle("Wypożyczalnia 1.0 - Strona główna");
    }

    @FXML
    void showAdminClientScene(ActionEvent event) {
        adminWindowController.selectScene(ADMIN_CLIENT_SCENE);
        Stage dialogStage;
        Node source = (Node) event.getSource();
        dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.setTitle("Wypożyczalnia 1.0 - Klient");
    }

    @FXML
    void showAdminItemScene(ActionEvent event) {
        adminWindowController.selectScene(ADMIN_ITEM_SCENE);
        Stage dialogStage;
        Node source = (Node) event.getSource();
        dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.setTitle("Wypożyczalnia 1.0 - Przedmiot");
    }

    @FXML
    void showAdminRentalScene(ActionEvent event) {
        adminWindowController.selectScene(ADMIN_RENTAL_SCENE);
        Stage dialogStage;
        Node source = (Node) event.getSource();
        dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.setTitle("Wypożyczalnia 1.0 - Wypożycz przedmiot");
    }

    @FXML
    void showAdminReturnScene(ActionEvent event) {
        adminWindowController.selectScene(ADMIN_RETURN_SCENE);
        Stage dialogStage;
        Node source = (Node) event.getSource();
        dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.setTitle("Wypożyczalnia 1.0 - Zwrot przedmiotu");
    }

    @FXML
    void showAdminOutdatedScene(ActionEvent event) {
        adminWindowController.selectScene(ADMIN_OUTDATED_SCENE);
        Stage dialogStage;
        Node source = (Node) event.getSource();
        dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.setTitle("Wypożyczalnia 1.0 - Przetrzymane przedmioty");
    }

    @FXML
    void showAdminSettingsScene(ActionEvent event) {
        adminWindowController.selectScene(ADMIN_SETTINGS_SCENE);
        Stage dialogStage;
        Node source = (Node) event.getSource();
        dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.setTitle("Wypożyczalnia 1.0 - Ustawienia");
    }

    @FXML
    void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wylogowywanie");
        alert.setHeaderText(null);
        alert.setContentText("Czy napewno chcesz się wylogować ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) this.logoutButton.getScene().getWindow();
            stage.close();
            Pane root = FxmlPathBuilder.fxmlLoader(LOGIN_SCENE);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Wypożyczalnia 1.0 - Logowanie");
            stage.show();
        } else {
            DialogBoxBuilder.displayInfoDialogBox("Wylogowywanie", "Wylogowywanie przerwane");
        }
    }

    public void setAdminMenuBarController(AdminWindowController adminWindowController) {
        this.adminWindowController = adminWindowController;
    }

    @FXML
    void initialize()
    {
        Image home  = new Image(getClass().getResourceAsStream("/images/home.png"));
        mainSceneButton.setGraphic(new ImageView(home));
        mainSceneButton.setContentDisplay(ContentDisplay.LEFT);

        Image client  = new Image(getClass().getResourceAsStream("/images/client.png"));
        clientSceneButton.setGraphic(new ImageView(client));
        clientSceneButton.setContentDisplay(ContentDisplay.LEFT);

        Image item  = new Image(getClass().getResourceAsStream("/images/item.png"));
        itemSceneButton.setGraphic(new ImageView(item));
        itemSceneButton.setContentDisplay(ContentDisplay.LEFT);

        Image rental  = new Image(getClass().getResourceAsStream("/images/rental.png"));
        rentalSceneButton.setGraphic(new ImageView(rental));
        rentalSceneButton.setContentDisplay(ContentDisplay.LEFT);

        Image ret  = new Image(getClass().getResourceAsStream("/images/return.png"));
        returnSceneButton.setGraphic(new ImageView(ret));
        returnSceneButton.setContentDisplay(ContentDisplay.LEFT);

        Image outdated  = new Image(getClass().getResourceAsStream("/images/outdated.png"));
        manageSceneButton.setGraphic(new ImageView(outdated));
        manageSceneButton.setContentDisplay(ContentDisplay.LEFT);

        Image settings  = new Image(getClass().getResourceAsStream("/images/settings.png"));
        settingsSceneButton.setGraphic(new ImageView(settings));
        settingsSceneButton.setContentDisplay(ContentDisplay.LEFT);

        Image logout  = new Image(getClass().getResourceAsStream("/images/logout.png"));
        logoutButton.setGraphic(new ImageView(logout));
        logoutButton.setContentDisplay(ContentDisplay.LEFT);
    }

}

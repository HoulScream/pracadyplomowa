package controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminMenuBarController
{
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
    private Button logoutButton;

    @FXML
    private Button clientSceneButton;

    @FXML
    private Button returnSceneButton;

    private AdminWindowController adminWindowController;
    private static final String ADMIN_MAIN_SCENE = "/fxml/admin/AdminMainScene.fxml";
    private static final String ADMIN_CLIENT_SCENE = "/fxml/admin/AdminClientScene.fxml";
    private static final String ADMIN_ITEM_SCENE = "/fxml/admin/AdminItemScene.fxml";
    private static final String ADMIN_RENTAL_SCENE = "/fxml/admin/AdminRentalScene.fxml";
    private static final String ADMIN_RETURN_SCENE = "/fxml/admin/AdminReturnScene.fxml";
    private static final String ADMIN_MANAGE_SCENE = "/fxml/admin/AdminManageScene.fxml";
    private static final String ADMIN_SETTINGS_SCENE = "/fxml/admin/AdminSettingsScene.fxml";

    @FXML
    void showAdminMainScene(ActionEvent event)
    {
        adminWindowController.selectScene(ADMIN_MAIN_SCENE);
        Stage dialogStage;
        Node source = (Node) event.getSource();
        dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.setTitle("Wypożyczalnia 1.0 - Strona główna");
    }

    @FXML
    void showAdminClientScene(ActionEvent event)
    {
        adminWindowController.selectScene(ADMIN_CLIENT_SCENE);
        Stage dialogStage;
        Node source = (Node) event.getSource();
        dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.setTitle("Wypożyczalnia 1.0 - Klient");
    }

    @FXML
    void showAdminItemScene(ActionEvent event)
    {
        adminWindowController.selectScene(ADMIN_ITEM_SCENE);
        Stage dialogStage;
        Node source = (Node) event.getSource();
        dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.setTitle("Wypożyczalnia 1.0 - Przedmiot");
    }

    @FXML
    void showAdminRentalScene(ActionEvent event)
    {
        adminWindowController.selectScene(ADMIN_RENTAL_SCENE);
        Stage dialogStage;
        Node source = (Node) event.getSource();
        dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.setTitle("Wypożyczalnia 1.0 - Wypożycz przedmiot");
    }

    @FXML
    void showAdminReturnScene(ActionEvent event)
    {
        adminWindowController.selectScene(ADMIN_RETURN_SCENE);
        Stage dialogStage;
        Node source = (Node) event.getSource();
        dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.setTitle("Wypożyczalnia 1.0 - Zwrot przedmiotu");
    }

    @FXML
    void showAdminManageScene(ActionEvent event)
    {
        adminWindowController.selectScene(ADMIN_MANAGE_SCENE);
        Stage dialogStage;
        Node source = (Node) event.getSource();
        dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.setTitle("Wypożyczalnia 1.0 - Zarządzaj");
    }

    @FXML
    void showAdminSettingsScene(ActionEvent event)
    {
        adminWindowController.selectScene(ADMIN_SETTINGS_SCENE);
        Stage dialogStage;
        Node source = (Node) event.getSource();
        dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.setTitle("Wypożyczalnia 1.0 - Ustawienia");
    }

    @FXML
    void logout(ActionEvent event)
    {

    }

    public void setAdminMenuBarController(AdminWindowController adminWindowController)
    {
        this.adminWindowController = adminWindowController;
    }

}

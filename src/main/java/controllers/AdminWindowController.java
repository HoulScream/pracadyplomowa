package controllers;

import common.FxmlPathBuilder;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class AdminWindowController
{
    @FXML
    private BorderPane adminWindowScene;

    @FXML
    private AdminMenuBarController adminMenuBarController;

    @FXML
    private void initialize()
    {
        adminMenuBarController.setAdminMenuBarController(this);

    }

    public void selectScene(String fxmlFilePatch)
    {
        adminWindowScene.setCenter(FxmlPathBuilder.fxmlLoader(fxmlFilePatch));
    }
}

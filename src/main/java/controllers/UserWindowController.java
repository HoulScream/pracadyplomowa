package controllers;

import common.FxmlPathBuilder;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class UserWindowController
{
    @FXML
    private BorderPane userWindowScene;

    @FXML
    private UserMenuBarController userMenuBarController;

    @FXML
    void initialize()
    {
        userMenuBarController.setUserMenuBarController(this);

    }

    public void selectScene(String fxmlFilePatch)
    {
        userWindowScene.setCenter(FxmlPathBuilder.fxmlLoader(fxmlFilePatch));
    }
}

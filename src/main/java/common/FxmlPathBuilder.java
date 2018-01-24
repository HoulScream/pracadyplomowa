package common;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class FxmlPathBuilder
{

    public static Pane fxmlLoader(String fxmlPath)
    {
        FXMLLoader loader = new FXMLLoader(FxmlPathBuilder.class.getResource(fxmlPath));
        try
        {
            return loader.load();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

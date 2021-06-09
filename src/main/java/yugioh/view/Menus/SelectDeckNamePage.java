package yugioh.view.Menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

public class SelectDeckNamePage extends WelcomeMenu{
    public static Stage page;
    @Override
    public void execute() throws Exception {
        start(WelcomeMenu.stage);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlAddress = getClass().getResource("/yugioh/fxml/SelectDeckNamePage.fxml");
        Parent pane = FXMLLoader.load(fxmlAddress);
        page=new Stage();
        Scene scene = new Scene(pane);
        page.setScene(scene);
        page.initModality(Modality.WINDOW_MODAL);
        page.show();
    }
}

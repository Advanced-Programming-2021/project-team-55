package yugioh.controller.menucontroller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import yugioh.view.Responses;
import yugioh.view.menus.PopUpWindow;
import yugioh.view.menus.SelectDeckNamePage;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectDeckNamePageController extends MenuController implements Initializable {
    public Button selectionOkButton;
    public Button selectionBackButton;
    public TextField deckNameField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectionOkButton.setDisable(true);
        deckNameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                selectionOkButton.setDisable(t1.equals(""));
            }
        });
        selectionOkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    DeckMenuController.getInstance().createDeck(deckNameField.getText());
                    SelectDeckNamePage.page.close();
                    deckMenu.execute();
                    new PopUpWindow(Responses.DECK_CREATED_SUCCESSFULLY.response).start(SelectDeckNamePage.page);
                } catch (Exception e) {
                    deckNameField.setText("");
                    try {
                        new PopUpWindow(e.getMessage()).start(SelectDeckNamePage.page);
                    } catch (Exception er) {
                    }
                }
            }
        });
        selectionBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SelectDeckNamePage.page.close();
                try {
                    deckMenu.execute();
                } catch (Exception e) {
                }
            }
        });
    }
}

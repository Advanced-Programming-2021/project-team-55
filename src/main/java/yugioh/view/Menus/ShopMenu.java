package yugioh.view.Menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import yugioh.controller.CheatController;
import yugioh.controller.menucontroller.ShopMenuController;
import yugioh.model.User;
import yugioh.model.cards.Card;
import yugioh.model.exceptions.MenuException;
import yugioh.view.Regexes;
import yugioh.view.Responses;
import yugioh.view.ViewInterface;
import yugioh.view.transitions.CoinTransition;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ShopMenu extends WelcomeMenu {
    private static final ShopMenuController shopMenuController = ShopMenuController.getInstance();
    private static final CheatController cheatController = CheatController.getInstance();


    @Override
    public void execute() throws Exception {
        start(stage);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (command.matches(Regexes.BUY_SHOP.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.BUY_SHOP.regex);
//            try {
//                shopMenuController.buyCard(matcher.group(1));
//                response = Responses.CARD_BOUGHT_SUCCESSFULLY.response;
//            } catch (MenuException e) {
//                response = e.toString();
//            }
        } else if (command.matches(Regexes.SHOW_SHOP.regex)) {
            showAllCards(shopMenuController.getAllCards());
//        } else if (command.matches(Regexes.ENTER_MENU.regex)) {
//            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.ENTER_MENU.regex);
//            try {
//                shopMenuController.enterMenu(matcher.group(1));
//            } catch (MenuException e) {
//                response = e.toString();
//            }
        } else if (command.matches(Regexes.INCREASE_MONEY.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.INCREASE_MONEY.regex);
            cheatController.increaseMoney(Integer.parseInt(matcher.group(1)), User.loggedInUser);
            response = Responses.CHEAT_ACTIVATED_MONEY_INCREASED.response;
//        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
//            shopMenuController.exitMenu();
        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
            response = getCurrentMenu();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }

    private void showAllCards(ArrayList<Card> cards) {
        for (Card card : cards) {
            ViewInterface.showResult(card.toString());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/ShopMenu.fxml");
        Pane pane= FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        ImageView coinImage=(ImageView)pane.getChildren().get(8);
        new CoinTransition(coinImage).play();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}

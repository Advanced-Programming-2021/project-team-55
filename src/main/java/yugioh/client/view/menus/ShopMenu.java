package yugioh.client.view.menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import yugioh.client.controller.CheatController;
import yugioh.client.controller.menucontroller.ShopMenuController;
import yugioh.client.model.User;
import yugioh.client.model.cards.Card;
import yugioh.client.view.Regexes;
import yugioh.client.view.Responses;
import yugioh.client.view.ViewInterface;
import yugioh.client.view.transitions.CoinTransition;

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
        Pane pane = FXMLLoader.load(url);
        Scene scene = WelcomeMenu.createScene(pane);
        ImageView coinImage = (ImageView) pane.getChildren().get(10);
        new CoinTransition(coinImage).play();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
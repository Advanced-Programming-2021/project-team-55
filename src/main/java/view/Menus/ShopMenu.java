package view.Menus;

import controller.menucontroller.ShopMenuController;
import exceptions.MenuException;
import model.Card;
import view.Regexes;
import view.ViewInterface;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ShopMenu extends Menu{
    private static final ShopMenuController shopMenuController = ShopMenuController.getInstance();

    @Override
    protected void execute() {
        String response=processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response="";
        if(command.matches(Regexes.BUY_SHOP.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,Regexes.BUY_SHOP.regex) ;
            try {
                shopMenuController.buyCard(matcher.group(1));
            }
            catch (MenuException e){
                response=e.toString();
            }
        }
        else if(command.matches(Regexes.SHOW_SHOP.regex)){
            showAllCards(shopMenuController.getAllCards());
        }
        else if(command.matches(Regexes.ENTER_MENU.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,Regexes.ENTER_MENU.regex);
            try {
                shopMenuController.enterMenu(matcher.group(1));
            }
            catch (MenuException e){
                response=e.toString();
            }
        }
        else if(command.matches(Regexes.EXIT_MENU.regex)){
            shopMenuController.exitMenu();
        }
        else if(command.matches(Regexes.SHOW_MENU.regex)){
            response=getCurrentMenu();
        }
        return response;
    }
    private void showAllCards(ArrayList<Card>cards){
        for(Card card:cards){
            System.out.println(card);
        }
    }
}

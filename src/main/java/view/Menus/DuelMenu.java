package view.Menus;

import controller.gamephasescontrollers.GameController;
import controller.menucontroller.DuelMenuController;
import exceptions.MenuException;
import model.Player;
import model.User;
import model.board.Game;
import model.cards.Deck;
import view.gamephases.Duel;
import view.Regexes;
import view.Responses;
import view.ViewInterface;

import java.util.regex.Matcher;

public class DuelMenu extends Menu {

    private static final DuelMenuController duelMenuController = DuelMenuController.getInstance();

    @Override
    protected void execute() {
        String response =processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response="";
        if(command.matches(Regexes.DUEL_PLAYER.regex)){
            //todo this is for testing the game
           Matcher matcher=ViewInterface.getCommandMatcher(command,Regexes.DUEL_PLAYER.regex);
            Duel.runGame(new GameController(new Game(new Player(new User("sdfd","sdf","sdfdf"),new Deck("sdfd", User.loggedInUser)),
                    new Player(new User("eter","sdf","sdf"),new Deck("sdfdf",User.loggedInUser)),3)));
           /*try {
               Duel.runGame(duelMenuController.newPVPDuel(matcher.group(2),Integer.parseInt(matcher.group(1))));
           }
           catch (MenuException e){
               response=e.toString();
           }*/
        }
        else if(command.matches(Regexes.DUEL_AI.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,Regexes.DUEL_AI.regex);
            try {
                duelMenuController.newAIDuel(Integer.parseInt(matcher.group(1)));
            }
            catch (MenuException e){
                response=e.toString();
            }
        }
        else if(command.matches(Regexes.ENTER_MENU.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,Regexes.ENTER_MENU.regex);
            try {
                duelMenuController.enterMenu(matcher.group(1));
            }
            catch (MenuException e){
                response=e.toString();
            }
        }
        else if(command.matches(Regexes.EXIT_MENU.regex)){
            duelMenuController.exitMenu();
        }
        else if(command.matches(Regexes.SHOW_MENU.regex)){
            response=getCurrentMenu();
        }
        else{
            response= Responses.INVALID_COMMAND.response;
        }
        return response;
    }

}

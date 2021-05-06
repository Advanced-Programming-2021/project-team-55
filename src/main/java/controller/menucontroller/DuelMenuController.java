package controller.menucontroller;

import controller.gamephasescontrollers.GameController;
import exceptions.MenuException;
import model.Player;
import model.User;
import model.board.Game;
import model.cards.Deck;
import view.Menus.Menu;
import view.Menus.MenuType;
import view.Responses;

public class DuelMenuController extends MenuController {

    private static DuelMenuController duelMenuController;

    private DuelMenuController() {

    }

    public static DuelMenuController getInstance() {
        if (duelMenuController == null) {
            duelMenuController = new DuelMenuController();
        }
        return duelMenuController;
    }

    @Override
    public void enterMenu(String menu) throws MenuException {
        throw new MenuException(Responses.MENU_NAVIGATION_NOT_POSSIBLE.response);
    }

    @Override
    public void exitMenu() {
        Menu.setCurrentMenu(MenuType.MAIN);
    }

    public GameController newPVPDuel(String secondPlayer, int rounds) throws MenuException {
        User rival = User.getUserByUsername(secondPlayer);
        if (rival == null) {
            throw new MenuException(Responses.NO_PLAYER_EXISTS.response);
        } else if (User.loggedInUser.getActiveDeck() == null) {
            throw new MenuException("Error: " + User.loggedInUser.getUsername() + " has no active deck");
        } else if (rival.getActiveDeck() == null) {
            throw new MenuException("Error: " + rival.getUsername() + " has no active deck");
        } else {
            Deck player1Deck = User.loggedInUser.getActiveDeck();
            Deck player2Deck = rival.getActiveDeck();
            if (!player1Deck.isDeckValid()) {
                throw new MenuException("Error: " + User.loggedInUser.getUsername() + "’s deck is invalid");
            } else if (!player2Deck.isDeckValid()) {
                throw new MenuException("Error: " + rival.getUsername() + "’s deck is invalid");
            } else if (rounds != 1 && rounds != 3) {
                throw new MenuException(Responses.NUMBER_OF_ROUNDS_NOT_SUPPORTED.response);
            } else {
                Player player1 = new Player(User.loggedInUser, player1Deck.clone());//todo tribute
                Player player2 = new Player(rival, player2Deck.clone());
                return new GameController(new Game(player1, player2, rounds));
            }
        }

    }

    public void newAIDuel(int rounds) throws MenuException {
        if (rounds != 1 && rounds != 3) {
            throw new MenuException(Responses.NUMBER_OF_ROUNDS_NOT_SUPPORTED.response);
        } else {
            //TODO -->>Start ai game
        }
    }

    private boolean checkOpponentExists(String secondPlayer) {
        return false;
    }

    private boolean hasActiveDeck(Player player) {
        return false;
    }

    private boolean isRoundValid(int round) {
        return false;
    }

    public void checkWholeMatchWinner() {

    }

    private void calculateGameWinnerScore() {

    }

    private void rewardPlayers() {

    }


}

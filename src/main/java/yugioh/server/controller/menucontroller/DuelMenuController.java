package yugioh.server.controller.menucontroller;

import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.UserHolder;
import yugioh.server.model.exceptions.MenuException;
import yugioh.server.model.Player;
import yugioh.server.model.User;
import yugioh.server.model.board.Game;
import yugioh.server.model.cards.Deck;
import yugioh.server.view.Menus.Menu;
import yugioh.server.view.Menus.MenuType;
import yugioh.server.view.Responses;

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

    public GameController newPVPDuel(String secondPlayer, int rounds, UserHolder currentUser) throws MenuException {
        User rival = User.getUserByUsername(secondPlayer);
        if (rival == null) {
            throw new MenuException(Responses.NO_PLAYER_EXISTS.response);
        } else if (currentUser.getUser().getActiveDeck() == null) {
            throw new MenuException("Error: " + currentUser.getUser().getUsername() + " has no active deck");
        } else if (rival.getActiveDeck() == null) {
            throw new MenuException("Error: " + rival.getUsername() + " has no active deck");
        } else {
            Deck player1Deck = currentUser.getUser().getActiveDeck();
            Deck player2Deck = rival.getActiveDeck();
            if (!player1Deck.isDeckValid()) {
                throw new MenuException("Error: " + currentUser.getUser().getUsername() + "’s deck is invalid");
            } else if (!player2Deck.isDeckValid()) {
                throw new MenuException("Error: " + rival.getUsername() + "’s deck is invalid");
            } else if (rounds != 1 && rounds != 3) {
                throw new MenuException(Responses.NUMBER_OF_ROUNDS_NOT_SUPPORTED.response);
            } else {
                Player player1 = new Player(currentUser.getUser(), player1Deck.clone(), false);
                Player player2 = new Player(rival, player2Deck.clone(), false);
                return new GameController(new Game(player1, player2, rounds));
            }
        }

    }

    public GameController newAIDuel(int rounds, UserHolder currentUser) throws MenuException {
        if (rounds != 1 && rounds != 3) {
            throw new MenuException(Responses.NUMBER_OF_ROUNDS_NOT_SUPPORTED.response);
        } else {
            if (currentUser.getUser().getActiveDeck() == null) {
                throw new MenuException("Error: " + currentUser.getUser().getUsername() + " has no active deck");
            } else {
                Deck player1Deck = currentUser.getUser().getActiveDeck();
                if (!player1Deck.isDeckValid()) {
                    throw new MenuException("Error: " + currentUser.getUser().getUsername() + "’s deck is invalid");
                } else {
                    Player player1 = new Player(currentUser.getUser(), player1Deck.clone(), false);
                    Player player2 = new Player(new User("ai", "ai", "ai"), player1Deck.clone(), true);
                    return new GameController(new Game(player1, player2, rounds));
                }
            }
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

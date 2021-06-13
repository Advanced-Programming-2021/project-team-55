package yugioh.controller.menucontroller;

import javafx.scene.input.MouseEvent;
import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.Player;
import yugioh.model.User;
import yugioh.model.board.Game;
import yugioh.model.cards.Deck;
import yugioh.model.exceptions.MenuException;
import yugioh.view.Menus.DetermineStarterMenu;
import yugioh.view.Menus.RivalSelectionMenu;
import yugioh.view.Responses;
import yugioh.view.gamephases.Duel;

public class DuelMenuController extends MenuController {

    public static DuelMenuController duelMenuController;

    public DuelMenuController() {

    }

    public static DuelMenuController getInstance() {
        if (duelMenuController == null) {
            duelMenuController = new DuelMenuController();
        }
        return duelMenuController;
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
                Player player1 = new Player(User.loggedInUser, player1Deck.clone(), false);
                Player player2 = new Player(rival, player2Deck.clone(), false);
                return new GameController(new Game(player1, player2, rounds));
            }
        }

    }

    public GameController newAIDuel(int rounds) throws MenuException {
        if (rounds != 1 && rounds != 3) {
            throw new MenuException(Responses.NUMBER_OF_ROUNDS_NOT_SUPPORTED.response);
        } else {
            if (User.loggedInUser.getActiveDeck() == null) {
                throw new MenuException("Error: " + User.loggedInUser.getUsername() + " has no active deck");
            } else {
                Deck player1Deck = User.loggedInUser.getActiveDeck();
                if (!player1Deck.isDeckValid()) {
                    throw new MenuException("Error: " + User.loggedInUser.getUsername() + "’s deck is invalid");
                } else {
                    Player player1 = new Player(User.loggedInUser, player1Deck.clone(), false);
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


    public void backClicked() throws Exception {
        playButtonSound();
        mainMenu.execute();
    }

    public void newPVPDuel() {
        try {
            new RivalSelectionMenu().execute();
            if (RivalSelectionMenu.isDoCancel()) return;
            Duel.runGame(newPVPDuel(RivalSelectionMenu.getRival().getUsername(), RivalSelectionMenu.getRounds()));
        } catch (Exception ignored) {
        }
    }
}

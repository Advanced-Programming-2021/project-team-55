package yugioh.client.controller.menucontroller;

import javafx.scene.media.MediaView;
import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.model.Player;
import yugioh.client.model.User;
import yugioh.client.model.board.Game;
import yugioh.client.model.cards.Deck;
import yugioh.client.model.exceptions.MenuException;
import yugioh.client.view.Responses;
import yugioh.client.view.SoundPlayable;
import yugioh.client.view.gamephases.Duel;
import yugioh.client.view.menus.RivalSelectionMenu;

public class DuelMenuController extends MenuController {

    public static DuelMenuController duelMenuController;
    public MediaView duelMenuBackground;

    public DuelMenuController() {

    }

    public static DuelMenuController getInstance() {
        if (duelMenuController == null) {
            duelMenuController = new DuelMenuController();
        }
        return duelMenuController;
    }


    public GameController newPVPDuel(String secondPlayer, int rounds) throws Exception {
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

    public GameController newAIDuel(int rounds) throws Exception {
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
        SoundPlayable.playButtonSound("backButton");
        mainMenu.execute();
    }

    public void newPVPDuel() {
        try {
            SoundPlayable.playButtonSound("enterButton");
            new RivalSelectionMenu().execute();
            if (RivalSelectionMenu.isDoCancel()) return;
            Duel.runGame(newPVPDuel(RivalSelectionMenu.getRival().getUsername(), RivalSelectionMenu.getRounds()));
        } catch (Exception e) {
//            try {
//                new PopUpWindow(e.getMessage()).start(WelcomeMenu.getStage());
//            } catch (Exception ignored) {
//            }
            e.printStackTrace();
        }
    }
}

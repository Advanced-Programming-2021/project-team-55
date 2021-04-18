package controller.menucontroller;

import exceptions.MenuException;
import model.Player;

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

    }

    @Override
    public void exitMenu() {

    }

    public void newPVPDuel(String secondPlayer, int rounds) {

    }

    public void newAIDuel(int rounds) {

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

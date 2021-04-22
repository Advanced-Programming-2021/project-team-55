package controller.menucontroller;

import controller.gamephasescontrollers.GameController;
import exceptions.MenuException;
import model.board.Game;
import model.cards.Deck;
import model.Player;
import model.User;
import view.Menus.Menu;
import view.Menus.MenuType;

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
        throw new MenuException("menu navigation is not possible");
    }

    @Override
    public void exitMenu() {
        Menu.setCurrentMenu(MenuType.MAIN);
    }

    public GameController newPVPDuel(String secondPlayer, int rounds) throws MenuException{
        User rival=User.getUserByUsername(secondPlayer);
        if(rival==null){
            throw new MenuException("there is no player with this username");
        }
        else if(User.loggedInUser.getActiveDeck()==null){
            throw new MenuException(User.loggedInUser.getUsername()+" has no active deck");
        }
        else if(rival.getActiveDeck()==null){
            throw new MenuException(rival.getUsername()+" has no active deck");
        }
        else{
            Deck player1Deck=User.loggedInUser.getActiveDeck();
            Deck player2Deck=rival.getActiveDeck();
            if(!player1Deck.isDeckValid()){
                throw new MenuException(User.loggedInUser.getUsername()+"’s deck is invalid");
            }
            else if(!player2Deck.isDeckValid()){
                throw new MenuException(rival.getUsername()+"’s deck is invalid");
            }
            else if(rounds!=1&&rounds!=3){
                throw new MenuException("number of rounds is not supported");
            }
            else{
                //TODO -->>Start pvp game
                Player player1=new Player(User.loggedInUser,player1Deck);
                Player player2=new Player(rival,player2Deck);
                return new GameController(new Game(player1,player2,rounds));
            }
        }

    }

    public void newAIDuel(int rounds)throws MenuException {
        if(rounds!=1&&rounds!=3){
            throw new MenuException("number of rounds is not supported");
        }
        else{
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

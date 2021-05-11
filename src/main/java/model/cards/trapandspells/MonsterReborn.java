package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.Cell;
import model.board.Game;
import model.board.GameBoard;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import view.ViewInterface;
import view.gamephases.Duel;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MonsterReborn extends SpellAndTrap {

    public MonsterReborn() {
        super("Monster Reborn", "Target 1 monster in either GY; Special Summon it.",
                2500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.LIMITED);
    }
    public static void handleEffect(GameController gameController){
        ViewInterface.showResult("your graveyard:");
        ArrayList<Cell>playerGraveyard=gameController.currentTurnPlayer.getGameBoard().getGraveyard();
        ArrayList<Cell>opponentGraveyard =gameController.currentTurnOpponentPlayer.getGameBoard().getGraveyard();
        ViewInterface.showResult(gameController.showGraveyard(gameController.currentTurnPlayer));
        ViewInterface.showResult("your opponent graveyard:");
        ViewInterface.showResult(gameController.showGraveyard(gameController.currentTurnOpponentPlayer));
        ViewInterface.showResult("choose a monster from your or your opponents graveyard: (me (number)/opponent (number))\n");
        String input=ViewInterface.getInput();
        boolean isInputCorrect=false;
        while(!isInputCorrect||(!input.matches("me (\\d+)")&&!input.matches("opponent (\\d+)"))){
            ViewInterface.showResult("invalid format! try again:");
            input=ViewInterface.getInput();
        }
        Matcher matcher;
        if(input.matches("me (\\d+)")) {
            matcher=ViewInterface.getCommandMatcher(input, "me (\\d+)");
            int choice=Integer.parseInt(matcher.group(1));
            if(playerGraveyard.size()>=choice){
                if(!playerGraveyard.get(choice-1).getCellCard().isMonster()){

                }
            }
        }
        else{
            matcher=ViewInterface.getCommandMatcher(input,"opponent (\\d+)");
        }
        if(Integer.parseInt(matcher.group(1))>)
    }

}
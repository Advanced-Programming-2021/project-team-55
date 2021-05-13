package controller;

import model.CoinDice;

import java.util.ArrayList;
import java.util.Collections;

public class AIPlayerController {

    private static String lastResponse = "";

    private static boolean isGameEnded = false;

   public enum orderKind {ORDINARY, RANDOM }

    ArrayList<String> battleSelectionCommands = new ArrayList<>();
    ArrayList<String> battlePhaseCommands = new ArrayList<>();
    ArrayList<String> mainPhaseCommands = new ArrayList<>();
    ArrayList<String> mainPhaseSelectionCommands = new ArrayList<>();

    public AIPlayerController(orderKind selectionOrder, orderKind commandsOrder) {
        isGameEnded = false;
        for (int i = 1; i <= 5; i++) {
            mainPhaseSelectionCommands.add("select --hand " + i);
        }
        for (int i = 1; i <= 5; i++) {
            mainPhaseSelectionCommands.add("select --monster " + i);
        }
        for (int i = 1; i <= 5; i++) {
            mainPhaseSelectionCommands.add("select --spell " + i);
        }

        for (int i = 1; i <= 5; i++) {
            battleSelectionCommands.add("select --monster " + i);
        }

        mainPhaseCommands.add("activate effect");
        mainPhaseCommands.add("summon");
        mainPhaseCommands.add("set");
        mainPhaseCommands.add("set --position attack");
        mainPhaseCommands.add("set --position defense");
        mainPhaseCommands.add("flip-summon");

        battlePhaseCommands.add("attack direct");
        battlePhaseCommands.add("attack direct");
        battlePhaseCommands.add("attack direct");
        battlePhaseCommands.add("attack direct");
        battlePhaseCommands.add("attack direct");
        for (int i = 1; i <= 5; i++) {
            battlePhaseCommands.add("attack " + i);
        }

        if (selectionOrder == orderKind.RANDOM) Collections.shuffle(battleSelectionCommands);
        if (selectionOrder == orderKind.RANDOM) Collections.shuffle(mainPhaseSelectionCommands);
        if (commandsOrder == orderKind.RANDOM) Collections.shuffle(battlePhaseCommands);
        if (commandsOrder == orderKind.RANDOM) Collections.shuffle(mainPhaseCommands);

//next phase
//        for (int i = 1; i <= 5; i++) {
//            selectionCommands.add("select --field " + i);
//        }
//        for (int i = 1; i <= 5; i++) {
//            selectionCommands.add("select --monster " + i + " --opponent");
//        }
    }

    public String getSelectCommandForMainPhases() {
       String string;
       try{
           string = mainPhaseSelectionCommands.get(0);
           mainPhaseSelectionCommands.remove(0);
       }catch(Exception e){
           string = "next phase";
       }
       return string;
    }

    public static boolean isIsGameEnded() {
        return isGameEnded;
    }

    public static void setIsGameEnded(boolean isGameEnded) {
        AIPlayerController.isGameEnded = isGameEnded;
    }

    public String getSelectCommandForBattlePhase() {
        String string;
        try{
            string = battleSelectionCommands.get(0);
            battleSelectionCommands.remove(0);
        }catch(Exception e){
            string = "next phase";
        }
        return string;
    }

    public String getMainCommandForBattlePhase() {
        String string;
        try{
            string = battlePhaseCommands.get(0);
            battlePhaseCommands.remove(0);
        }catch(Exception e){
            string = "next phase";
        }
        return string;
    }

    public String getMainCommandForMainPhases() {
        String string;
        try{
            string = mainPhaseCommands.get(0);
            mainPhaseCommands.remove(0);
        }catch(Exception e){
            string = "next phase";
        }
        return string;
    }


    public String getSpecialCommand() {
        if (lastResponse.contains("yes/no")){
            lastResponse = "";
            return randomYesNo();
        }
        if (lastResponse.contains("number")){
            lastResponse = "";
            return (CoinDice.rollDice() + CoinDice.rollDice() - 1) + "";
        }
        if (lastResponse.contains("you should special summon right now")){
            lastResponse = "";
            return "summon";
        }

        return getSelectCommandForMainPhases();
    }

    private String randomYesNo() {
        if (CoinDice.tossCoin() == 1) {
            return "yes";
        }
        return "no";
    }

    public static String getLastResponse() {
        return lastResponse;
    }

    public static void setLastResponse(String lastResponse) {
        AIPlayerController.lastResponse = lastResponse;
    }


    //    private static AIPlayerController aiPlayerController;
//
//    public static AIPlayerController getInstance() {
//        return null;
//    }
//
//    public boolean isMovementBeneficial(Card selectedCard) {
//        return false;
//    }
//
//    public boolean setSpell() {
//        return false;
//    }
//
//    public boolean setTrap() {
//        return false;
//    }
//
//    public boolean setMonster() {
//        return false;
//    }

}

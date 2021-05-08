package controller;

import java.util.ArrayList;
import java.util.Collections;

public class AIPlayerController {

   private static boolean hadSelectedBefore = false;

   public enum orderKind {ORDINARY, RANDOM }

    ArrayList<String> selectionCommands = new ArrayList<>();
    ArrayList<String> mainCommands = new ArrayList<>();

    boolean shouldSelect = true;

    public AIPlayerController(orderKind selectionOrder, orderKind commandsOrder) {
        for (int i = 1; i <= 5; i++) {
            selectionCommands.add("select --hand " + i);
        }
        for (int i = 1; i <= 5; i++) {
            selectionCommands.add("select --field " + i);
        }
        for (int i = 1; i <= 5; i++) {
            selectionCommands.add("select --monster " + i);
        }
        for (int i = 1; i <= 5; i++) {
            selectionCommands.add("select --spell " + i);
        }
        mainCommands.add("summon");
        mainCommands.add("set");
        mainCommands.add("set --position attack");
        mainCommands.add("set --position defense");
        mainCommands.add("flip-summon");
        mainCommands.add("attack direct");
        mainCommands.add("activate effect");
        for (int i = 1; i <= 5; i++) {
            mainCommands.add("attack " + i);
        }
        if (selectionOrder == orderKind.RANDOM) Collections.shuffle(selectionCommands);
        if (commandsOrder == orderKind.RANDOM) Collections.shuffle(mainCommands);
//next phase
//        for (int i = 1; i <= 5; i++) {
//            selectionCommands.add("select --monster " + i + " --opponent");
//        }
    }

    public String getSelectCommand() {
       String string;
       try{
           string = selectionCommands.get(0);
           selectionCommands.remove(0);
       }catch(Exception e){
           string = "next phase";
       }
       return string;
    }

    public String getMainCommand() {
        String string;
        try{
            string = mainCommands.get(0);
            mainCommands.remove(0);
        }catch(Exception e){
            string = "next phase";
        }
        return string;
    }

    public String getAICommand(){
        if (shouldSelect && !hadSelectedBefore) {
            shouldSelect = false;
            hadSelectedBefore = true;
            return getMainCommand();
        }else{
            hadSelectedBefore = false;
            shouldSelect = true;
            return getSelectCommand();
        }
    }

    public boolean isShouldSelect() {
        return shouldSelect;
    }

    public void setShouldSelect(boolean shouldSelect) {
        this.shouldSelect = shouldSelect;
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

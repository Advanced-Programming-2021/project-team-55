package view.Menus;

import controller.MenuController.ScoreBoardMenuController;
import view.Regexes;
import view.Responses;
import view.ViewInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreBoardMenu extends Menu {
    private static ScoreBoardMenuController scoreBoardMenuController=ScoreBoardMenuController.getInstance();


    @Override
    protected void execute() {
        String response = processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response="";
        if(command.matches(Regexes.SHOWSCOREBOARD.regex)){
            showScoreBoard(scoreBoardMenuController.getScoreBoard());
        }
        else{
            response= Responses.INVALIDCOMMAND.response;
        }
        return response;
    }
    private void showScoreBoard(HashMap<Integer,String>scoreBoard){
        for (int i = 0; i < scoreBoard.size(); i++) {
            for(int rank : scoreBoard.keySet()){
                System.out.println(rank+scoreBoard.get(rank));
            }
        }
    }
}

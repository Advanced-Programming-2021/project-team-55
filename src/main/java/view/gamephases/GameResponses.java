package view.gamephases;

import view.Responses;

public enum GameResponses {
    INVALID_COMMAND("Error: invalid command"),
    INVALID_SELECTION("Error: invalid selection"),
    NO_CARDS_FOUND("Error: no card found in the given position"),
    NO_CARDS_SELECTED("Error: no card is selected yet"),
    MONSTER_ZONE_IS_FULL("Error: monster card zone is full"),
    CANT_SUMMON_CARD("Error: you can’t summon this card"),
    ALREADY_SUMMONED_SET_IN_THIS_TURN("Error: you already summoned/set on this turn"),
    CARD_SELECTED("card selected"),
    CARD_DESELECTED("card deselected"),
    SUMMONED_SUCCESSFULLY("summoned successfully"),
    SET_SUCCESSFULLY("set successfully"),
    SET_POSITION_SUCCESSFULLY("monster card position changed successfully"),
    FLIP_SUMMONED_SUCCESSFULLY("flip summoned successfully"),
    SPELL_ACTIVATED("spell activated");


    public final String response;

    GameResponses(String label) {
        this.response = label;
    }
    public static boolean responseExists(String response){
        for(GameResponses responses:GameResponses.values()){
            if(responses.response.equals(response)){
                return true;
            }
        }
        return false;
    }
}

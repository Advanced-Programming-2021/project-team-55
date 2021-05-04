package view.gamephases;

public enum GameResponses {

    INVALID_COMMAND("Error: invalid command"),
    INVALID_SELECTION("Error: invalid selection"),
    NO_CARDS_FOUND("Error: no card found in the given position"),
    NO_CARDS_SELECTED("Error: no card is selected yet"),
    MONSTER_ZONE_IS_FULL("Error: monster card zone is full"),
    SPELL_ZONE_IS_FULL("Error: spell card zone is full"),
    CANT_SET_CARD("Error: you can’t set this card"),
    CANT_SUMMON_CARD("Error: you can’t summon this card"),
    CANT_CHANGE_CARD_POSITION("Error: you can’t change this card position"),
    CARD_IS_ALREADY_IN_WANTED_POSITION("Error: this card is already in the wanted position"),
    ALREADY_CHANGED_CARD_POSITION_IN_THIS_TURN("Error: you already changed this card position in this turn"),
    ALREADY_SUMMONED_SET_IN_THIS_TURN("Error: you already summoned/set on this turn"),
    GRAVEYARD_EMPTY("Error: graveyard empty"),
    CARD_IS_NOT_VISIBLE("Error: card is not visible"),
    CARD_SELECTED("card selected"),
    CARD_DESELECTED("card deselected"),
    SUMMONED_SUCCESSFULLY("summoned successfully"),
    SET_SUCCESSFULLY("set successfully"),
    SET_POSITION_SUCCESSFULLY("monster card position changed successfully"),
    FLIP_SUMMONED_SUCCESSFULLY("flip summoned successfully"),
    SPELL_ACTIVATED("spell activated"),
    ACTION_NOT_ALLOWED_FOR_THIS_PHASE("Error: action not allowed in this phase"),
    CAN_NOT_ATTACK_WITH_THIS_CARD("Error: you can’t attack with this card"),
    CARD_ALREADY_ATTACKED("Error: this card already attacked"),
    CAN_NOT_DIRECT_ATTACK("Error: you can’t attack the opponent directly"),
    CAN_NOT_CHANGE_CARD_POSITION("Error: you can’t change this card position"),
    CAN_NOT_FLIP_SUMMON("Error: you can’t flip summon this card");


    public final String response;

    GameResponses(String label) {
        this.response = label;
    }

    public static boolean responseExists(String response) {
        for (GameResponses responses : GameResponses.values()) {
            if (responses.response.equals(response)) {
                return true;
            }
        }
        return false;
    }
}

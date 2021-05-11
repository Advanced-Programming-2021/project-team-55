package view.gamephases;

public enum GameResponses {

    INVALID_COMMAND("Error: invalid command"),
    INVALID_SELECTION("Error: invalid selection"),
    NO_CARDS_FOUND("Error: no card found in the given position"),
    NO_CARDS_SELECTED("Error: no card is selected yet"),
    NO_CARD_TO_ATTACK("Error: there is no card to attack here"),
    MONSTER_ZONE_IS_FULL("Error: monster card zone is full"),
    SPELL_ZONE_IS_FULL("Error: spell card zone is full"),
    HAND_DECK_IS_FULL("Error: hand deck is full ->> no card added"),
    CANT_SET_CARD("Error: you can’t set this card"),
    CANT_SUMMON_CARD("Error: you can’t summon this card"),
    CANT_ATTACK_CARD("Error: you can’t attack with this card"),
    CANT_CHANGE_CARD_POSITION("Error: you can’t change this card position"),
    CARD_IS_ALREADY_IN_WANTED_POSITION("Error: this card is already in the wanted position"),
    ALREADY_CHANGED_CARD_POSITION_IN_THIS_TURN("Error: you already changed this card position in this turn"),
    ALREADY_SUMMONED_SET_IN_THIS_TURN("Error: you already summoned/set on this turn"),
    ALREADY_ATTACKED_CARD("Error: this card already attacked"),
    GRAVEYARD_EMPTY("Error: graveyard empty"),
    CARD_IS_NOT_VISIBLE("Error: card is not visible"),
    ACTIVATION_ONLY_FOR_SPELL("Error: activate effect is only for spell cards"),
    ALREADY_ACTIVATED("Error: you have already activated this card"),
    YOU_SHOULD_RITUAL_SUMMON_NOW("Error: you should ritual summon right now"),
    YOU_SHOULD_SPECIAL_SUMMON_NOW("Error: you should special summon right now"),
    SELECTED_MONSTERS_DONT_MATCH("Error: selected monsters levels don’t match with ritual monster"),
    NO_WAY_TO_RITUAL_SUMMON("Error: there is no way you could ritual summon a monster"),
    PREPARATION_NOT_DONE("Error: preparations of this spell are not done yet"),
    SPELL_CANT_BE_ACTIVATED_THIS_TURN("Error: spell can’t be activated this turn"),
    CHEAT_ACTIVATED_LP_INCREASED("cheat activated: LP increased!"),
    CHEAT_ACTIVATED_WINNER_SET("cheat activated: winner set!"),
    CHEAT_ACTIVATED_SELECT_FORCE("cheat activated: card force selected!"),
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
    CAN_NOT_FLIP_SUMMON("Error: you can’t flip summon this card"),
    NOT_ENOUGH_CARDS_FOR_TRIBUTE("Error: there are not enough cards for tribute"),
    NO_MONSTER_ON_CELL("Error: there no monsters one this address"),
    CAN_NOT_SET_OR_SUMMON("Error: you can’t set/summon this card");


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

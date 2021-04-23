package view.gamephases;

public enum GameResponses {
    INVALID_COMMAND("invalid command"),
    CARD_SELECTED("card selected"),
    SUMMONED_SUCCESSFULLY("summoned successfully"),
    SET_SUCCESSFULLY("set successfully"),
    SET_POSITION_SUCCESSFULLY("monster card position changed successfully"),
    FLIP_SUMMONED_SUCCESSFULLY("flip summoned successfully"),
    SPELL_ACTIVATED("spell activated");


    public final String response;

    GameResponses(String label) {
        this.response = label;
    }
}

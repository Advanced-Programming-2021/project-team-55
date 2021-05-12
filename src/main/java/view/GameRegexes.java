package view;

public enum GameRegexes {
    SELECT("^select (.*)"),
    SELECT_MONSTER("^select --monster (\\d+)( --opponent)?$"),
    SELECT_SPELL("^select --spell (\\d+)$"),
    SELECT_OPPONENT_SPELL("^select --opponent --spell (\\d+)$"),
    SELECT_FIELDZONE("^select --field( --opponent)?$"),
    SELECT_HAND("^select --hand (\\d+)$"),
    DESELECT("^select -d$"),
    NEXT_PHASE("^next phase$"),
    SUMMON("^summon$"),
    SET("^set$"),
    SET_POSITION("^set --position (attack|defense)$"),
    FLIP_SUMMON("^flip-summon$"),
    ATTACK("^attack (\\d+)$"),
    ATTACK_DIRECT("^attack direct$"),
    ACTIVATE_EFFECT("^activate effect$"),
    SHOW_GRAVEYARD("^show graveyard$"),
    SHOW_CARD_SELECTED("^card show --selected$"),
    SURRENDER("^surrender$"),
    INCREASE_LP("^increase --LP (\\d+)$"),
    SET_WINNER("^duel set-winner$"),
    SELECT_CARD_FORCE("^select --force --hand (\\d+)$"),
    CHEAT_ADD_OPTIONAL_CARD("^add --force (.+)$"),
    MAKE_ME_AI("^make me ai$");

    public final String regex;

    GameRegexes(String label) {
        this.regex = label;
    }
}

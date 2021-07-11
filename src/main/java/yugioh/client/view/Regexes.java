package yugioh.client.view;

public enum Regexes {

    LOGIN_USER("^user login --password (.*) --username (.*)$"),
    CREATE_USER("^user create --nickname (.*) --password (.*) --username (.*)$"),
    LOGOUT_USER("^user logout$"),
    ENTER_MENU("^menu enter (\\S+)$"),
    EXIT_MENU("^menu exit$"),
    SHOW_MENU("^menu show-current$"),
    SHOW_SCOREBOARD("^scoreboard show$"),
    CHANGE_NICKNAME("^profile change --nickname (.*)$"),
    CHANGE_PASSWORD("^profile change --current (.*) --new (.*) --password$"),
    BUY_SHOP("^shop buy (.+)$"),
    SHOW_SHOP("^shop show --all$"),
    CREATE_DECK("^deck create (\\S+)$"),
    DELETE_DECK("^deck delete (\\S+)$"),
    SET_DECK_ACTIVE("^deck set-activate (\\S+)$"),
    ADD_CARD_TO_DECK("^deck add-card --card (.+) --deck (\\S+)( --side)?$"),
    REMOVE_CARD_FROM_CARD("^deck rm-card --card (.+) --deck (\\S+)( --side)?$"),
    SHOW_ALL_DECKS("^deck show --all$"),
    SHOW_DECK("^deck show --deck-name (\\S+)( --side)?$"),
    SHOW_DECK_CARDS("^deck show --cards$"),
    IMPORT_CARD("^import card (.*)$"),
    EXPORT_CARD("^export card (.*)$"),
    DUEL_PLAYER("^duel --new --rounds (\\d+) --second-player (\\S+)$"),
    DUEL_AI("^duel --ai --new --rounds (\\d+)$"),
    INCREASE_MONEY("^increase --money (\\d+)$");
    public final String regex;

    Regexes(String label) {
        this.regex = label;
    }
}

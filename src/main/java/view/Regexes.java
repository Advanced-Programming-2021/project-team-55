package view;

public enum Regexes {
    LOGIN_USER("^user login --password (.*) --username (.*)$"),
    CREATE_USER("^user create --nickname (.*) --password (.*) --username (.*)$"),
    LOGOUT_USER("^user logout$"),
    ENTER_MENU("^menu enter (.*)$"),
    EXIT_MENU("^menu exit$"),
    SHOW_MENU("^menu show-current$"),
    SHOW_SCOREBOARD("^scoreboard show$");
    public final String regex;

    Regexes(String label) {
        this.regex = label;
    }
}
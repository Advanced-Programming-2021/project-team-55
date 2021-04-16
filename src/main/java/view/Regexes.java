package view;

public enum Regexes {
    LOGINUSER("^user login --password (.*) --username (.*)$"),
    CREATEUSER("^user create --nickname (.*) --password (.*) --username (.*)$"),
    LOGOUTUSER("^user logout$"),
    ENTERMENU("^menu enter (.*)$"),
    EXITMENU("^menu exit$"),
    SHOWMENU("^menu show-current$"),
    SHOWSCOREBOARD("^scoreboard show$");
    public final String regex;

    private Regexes(String label) {
        this.regex = label;
    }
}

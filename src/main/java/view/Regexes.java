package view;

public enum Regexes {
    LOGINUSER("^user login --password (.*) --username (.*)$"),
    CREATEUSER("^user create --nickname (.*) --password (.*) --username (.*)$"),
    ENTERMENU("^menu enter (.*)$"),
    EXITMENU("^menu exit$");
    public final String regex;

    private Regexes(String label) {
        this.regex = label;
    }
}

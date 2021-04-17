package view;

public enum Responses {
    INVALID_COMMAND("invalid command"),
    LOGIN_SUCCESSFUL("user logged in successfully"),
    CREATE_SUCCESSFULLY("user created successfully!");
    public final String response;

    Responses(String label) {
        this.response = label;
    }
}

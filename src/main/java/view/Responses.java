package view;

public enum Responses {
    INVALID_COMMAND("invalid command"),
    LOGIN_SUCCESSFULLY("user logged in successfully"),
    CREATE_SUCCESSFULLY("user created successfully!"),
    NICKNAME_CHANGED_SUCCESSFULLY("nickname changed successfully!"),
    PASSWORD_CHANGED_SUCCESSFULLY("password changed successfully!");
    public final String response;

    Responses(String label) {
        this.response = label;
    }
}

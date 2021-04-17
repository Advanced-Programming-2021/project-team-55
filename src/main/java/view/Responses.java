package view;

public enum Responses {
    INVALIDCOMMAND("invalid command"),
    LOGINSUCCESSFULL("user logged in successfully"),
    CREATESUCCESSFULLY("user created successfully!");
    public final String response;

    Responses(String label) {
        this.response = label;
    }
}

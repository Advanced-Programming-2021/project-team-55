package controller;

public enum Responses {
    LOGINSUCCESSFULL("user logged in successfully"),
    LOGINFAILED("Username and password didn't match!"),
    CREATESUCCESSFULLY("user created successfully!");
    public final String response;

    private Responses(String label) {
        this.response = label;
    }
}

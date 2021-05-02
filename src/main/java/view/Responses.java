package view;

public enum Responses {
    INVALID_COMMAND("Error: invalid command"),
    LOGIN_SUCCESSFULLY("user logged in successfully"),
    CREATE_SUCCESSFULLY("user created successfully!"),
    LOGOUT_SUCCESSFULLY("user logged out successfully!"),
    NICKNAME_CHANGED_SUCCESSFULLY("nickname changed successfully!"),
    PASSWORD_CHANGED_SUCCESSFULLY("password changed successfully!"),
    CARD_BOUGHT_SUCCESSFULLY("card bought successfully!"),
    DECK_CREATED_SUCCESSFULLY("deck created successfully!"),
    DECK_DELETED_SUCCESSFULLY("deck deleted successfully!"),
    DECK_ACTIVATED_SUCCESSFULLY("deck activated successfully!"),
    CARD_ADDED_TO_DECK_SUCCESSFULLY("card added to deck successfully!"),
    CARD_REMOVED_FROM_DECK_SUCCESSFULLY("card removed from deck successfully!");




    public final String response;

    Responses(String label) {
        this.response = label;
    }
    public static boolean responseExists(String response){
        for(Responses responses:Responses.values()){
            if(responses.response.equals(response)){
                return true;
            }
        }
        return false;
    }
}

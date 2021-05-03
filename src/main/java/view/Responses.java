package view;

public enum Responses {
    INVALID_COMMAND("Error: invalid command"),
    USERNAME_AND_PASSWORD_DIDNT_MATCH("Error: Username and password didn't match!"),
    MENU_NAVIGATION_NOT_POSSIBLE("Error: menu navigation is not possible"),
    LOGIN_FIRST("Error: please login first!"),
    MAIN_DECK_FULL("Error: main deck is full"),
    SIDE_DECK_FULL("Error: side deck is full"),
    NO_PLAYER_EXISTS("Error: there is no player with this username"),
    NUMBER_OF_ROUNDS_NOT_SUPPORTED("Error: number of rounds is not supported"),
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

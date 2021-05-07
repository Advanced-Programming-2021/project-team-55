package model.exceptions;

public class GameException extends Exception {
    public GameException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
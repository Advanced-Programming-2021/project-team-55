package yugioh.model.exceptions;

public class MenuException extends Exception {
    public MenuException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}

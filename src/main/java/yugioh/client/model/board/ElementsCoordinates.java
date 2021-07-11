package yugioh.client.model.board;

import java.util.ArrayList;

public class ElementsCoordinates {
    private static final ArrayList<Coordinate> userHandCards = new ArrayList<>();

    static {
        userHandCards.add(new Coordinate(20, 30));
        userHandCards.add(new Coordinate(100, 680));
        userHandCards.add(new Coordinate(120, 650));
        userHandCards.add(new Coordinate(180, 620));
        userHandCards.add(new Coordinate(200, 600));
        userHandCards.add(new Coordinate(250, 580));
    }

    public static ArrayList<Coordinate> getUserHandCards() {
        return userHandCards;
    }
}

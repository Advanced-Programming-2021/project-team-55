package yugioh.model.board;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.controller.menucontroller.GameMenuController;
import yugioh.model.cards.Card;
import yugioh.model.cards.Deck;
import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterType;
import yugioh.model.exceptions.GameException;
import yugioh.view.gamephases.CardActionsMenu;
import yugioh.view.gamephases.Duel;
import yugioh.view.gamephases.GameResponses;

import java.util.ArrayList;
import java.util.Collections;

public class GameBoard {

    private static Pane gamePane;
    private final Cell[] monsterCardZone;
    private final ArrayList<Cell> graveyard;
    private final Cell[] spellAndTrapCardZone;
    private final ArrayList<Cell> deckZone;
    private final ArrayList<Cell> handCards;
    private final Cell fieldZone;
    private transient HBox handDeck;

    {
        monsterCardZone = new Cell[5];

        spellAndTrapCardZone = new Cell[5];
        for (int i = 0; i < 5; i++) {
            monsterCardZone[i] = new Cell();
            spellAndTrapCardZone[i] = new Cell();
        }
        deckZone = new ArrayList<>();
        handCards = new ArrayList<>();
        fieldZone = new Cell();
        fieldZone.setCardStatus(CardStatus.OCCUPIED);
        graveyard = new ArrayList<>();
    }

    public GameBoard(Deck deck) {
        ArrayList<Card> mainDeck = new ArrayList<>(deck.getMainDeck());
        Collections.shuffle(mainDeck);
        for (Card card : mainDeck) {
            Cell cell = new Cell();
            Rectangle rectangle = new Rectangle();
            cell.addCardToCell(card);
            cell.setCellRectangle(rectangle);
            deckZone.add(cell);
        }
        //todo i changed:
        //addCardsToHandDeck(5);
    }

    public void setBoardRectangles(Pane gamePane, boolean isOpponent) {
        this.gamePane = gamePane;
        if (!isOpponent) {
            monsterCardZone[0].setCellRectangle((Rectangle) gamePane.getChildren().get(8));
            monsterCardZone[1].setCellRectangle((Rectangle) gamePane.getChildren().get(9));
            monsterCardZone[2].setCellRectangle((Rectangle) gamePane.getChildren().get(7));
            monsterCardZone[3].setCellRectangle((Rectangle) gamePane.getChildren().get(10));
            monsterCardZone[4].setCellRectangle((Rectangle) gamePane.getChildren().get(6));

            spellAndTrapCardZone[0].setCellRectangle((Rectangle) gamePane.getChildren().get(13));
            spellAndTrapCardZone[1].setCellRectangle((Rectangle) gamePane.getChildren().get(14));
            spellAndTrapCardZone[2].setCellRectangle((Rectangle) gamePane.getChildren().get(12));
            spellAndTrapCardZone[3].setCellRectangle((Rectangle) gamePane.getChildren().get(15));
            spellAndTrapCardZone[4].setCellRectangle((Rectangle) gamePane.getChildren().get(11));
        } else {
            Rectangle rectangle1 = (Rectangle) gamePane.getChildren().get(18);
            rectangle1.rotateProperty().set(180);
            Rectangle rectangle2 = (Rectangle) gamePane.getChildren().get(19);
            rectangle2.rotateProperty().set(180);
            Rectangle rectangle3 = (Rectangle) gamePane.getChildren().get(17);
            rectangle3.rotateProperty().set(180);
            Rectangle rectangle4 = (Rectangle) gamePane.getChildren().get(20);
            rectangle4.rotateProperty().set(180);
            Rectangle rectangle5 = (Rectangle) gamePane.getChildren().get(16);
            rectangle5.rotateProperty().set(180);
            monsterCardZone[0].setCellRectangle(rectangle1);
            monsterCardZone[1].setCellRectangle(rectangle2);
            monsterCardZone[2].setCellRectangle(rectangle3);
            monsterCardZone[3].setCellRectangle(rectangle4);
            monsterCardZone[4].setCellRectangle(rectangle5);

            Rectangle rectangle6 = (Rectangle) gamePane.getChildren().get(23);
            rectangle6.rotateProperty().set(180);
            Rectangle rectangle7 = (Rectangle) gamePane.getChildren().get(24);
            rectangle7.rotateProperty().set(180);
            Rectangle rectangle8 = (Rectangle) gamePane.getChildren().get(22);
            rectangle8.rotateProperty().set(180);
            Rectangle rectangle9 = (Rectangle) gamePane.getChildren().get(25);
            rectangle9.rotateProperty().set(180);
            Rectangle rectangle10 = (Rectangle) gamePane.getChildren().get(21);
            rectangle10.rotateProperty().set(180);


            spellAndTrapCardZone[0].setCellRectangle(rectangle6);
            spellAndTrapCardZone[1].setCellRectangle(rectangle7);
            spellAndTrapCardZone[2].setCellRectangle(rectangle8);
            spellAndTrapCardZone[3].setCellRectangle(rectangle9);
            spellAndTrapCardZone[4].setCellRectangle(rectangle10);
        }
    }

    public void setCellsLabels(boolean isOpponent) {
        if (!isOpponent) {
            monsterCardZone[0].setCellInfo((Label) gamePane.getChildren().get(28));
            monsterCardZone[1].setCellInfo((Label) gamePane.getChildren().get(29));
            monsterCardZone[2].setCellInfo((Label) gamePane.getChildren().get(27));
            monsterCardZone[3].setCellInfo((Label) gamePane.getChildren().get(30));
            monsterCardZone[4].setCellInfo((Label) gamePane.getChildren().get(26));
        } else {
            Label label1 = (Label) gamePane.getChildren().get(33);
            label1.rotateProperty().set(180);
            Label label2 = (Label) gamePane.getChildren().get(34);
            label2.rotateProperty().set(180);
            Label label3 = (Label) gamePane.getChildren().get(32);
            label3.rotateProperty().set(180);
            Label label4 = (Label) gamePane.getChildren().get(35);
            label4.rotateProperty().set(180);
            Label label5 = (Label) gamePane.getChildren().get(31);
            label5.rotateProperty().set(180);
            monsterCardZone[0].setCellInfo(label1);
            monsterCardZone[1].setCellInfo(label2);
            monsterCardZone[2].setCellInfo(label3);
            monsterCardZone[3].setCellInfo(label4);
            monsterCardZone[4].setCellInfo(label5);
        }
    }

    public Cell[] getMonsterCardZone() {
        return monsterCardZone;
    }

    public Cell getFirstNotEmptyCell() {
        for (int i = 0; i < 5; i++) {
            if (!monsterCardZone[i].isEmpty()) return monsterCardZone[i];
        }
        return null;
    }

    public ArrayList<Cell> getGraveyard() {
        return graveyard;
    }

    public Cell[] getSpellAndTrapCardZone() {
        return spellAndTrapCardZone;
    }

    public ArrayList<Cell> getDeckZone() {
        return deckZone;
    }

    public ArrayList<Cell> getHandCards() {
        return handCards;
    }


    public Cell getFieldZone() {
        return fieldZone;
    }

    public boolean isCellInMonsterZone(Cell cell) {
        for (int i = 0; i < 5; i++) {
            if (monsterCardZone[i] == cell) {
                return true;
            }
        }
        return false;
    }

    public boolean isCellInHandZone(Cell cell) {
        for (int i = 0; i < handCards.size(); i++) {
            if (handCards.get(i) == cell) {
                return true;
            }
        }
        return false;
    }

    public boolean isCellInSpellAndTrapZone(Cell cell) {
        for (int i = 0; i < 5; i++) {
            if (spellAndTrapCardZone[i] == cell) {
                return true;
            }
        }
        return false;
    }

    public void addCardToMonsterCardZone(Card card, CardStatus cardStatus, GameController gameController) throws GameException {
        if (isMonsterCardZoneFull())
            throw new GameException(GameResponses.MONSTER_ZONE_IS_FULL.response);

        for (int i = 0; i < 5; i++) {
            if (monsterCardZone[i].isEmpty()) {
                monsterCardZone[i].addCardToCell(card);
                monsterCardZone[i].setCardStatus(cardStatus);
                ImagePattern imagePattern = card.getCardImagePattern();
                Rectangle rectangle = monsterCardZone[i].getCellRectangle();
                setTranslationAnimation(imagePattern, rectangle, card);
                setFlipTransition(card, rectangle);
                rectangle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    rectangle.requestFocus();
                    ImageView sword = new ImageView(new Image("/yugioh/PNG/icon/sword2.png"));
                    GameMenuController.getGameMenuController().gameBoardPane.getChildren().add(sword);
                    sword.setX(rectangle.getLayoutX() + 21);
                    sword.setY(rectangle.getLayoutY() + 15);
                    sword.addEventHandler(MouseEvent.MOUSE_MOVED, event2 -> {
                        sword.rotateProperty().setValue(Math.toDegrees(Math.atan((event2.getY() - rectangle.getLayoutY()) / (event2.getX() - rectangle.getLayoutX()))));
                        System.out.println(Math.atan((event2.getY() - rectangle.getLayoutY()) / (event2.getX() - rectangle.getLayoutX())));
                        event2.consume();
                    });
                    event.consume();
                });
                for (double j = 0; j <= 1; j += 0.05) {
                    rectangle.opacityProperty().set(j);
                }
                monsterCardZone[i].getCellInfo().setText(((Monster) card).getAtk() + "/" + ((Monster) card).getDef());
                gameController.getGameMenuController().addEventForCardImageRectangle(rectangle, card);
                gameController.changedPositionCells.add(monsterCardZone[i]);
                return;
            }
        }
    }

    private boolean isMonsterCardZoneFull() {
        for (int i = 0; i < 5; i++) {
            if (monsterCardZone[i].isEmpty()) return false;
        }
        return true;
    }

    public boolean isMonsterCardZoneEmpty() {
        for (int i = 0; i < 5; i++) {
            if (!monsterCardZone[i].isEmpty()) return false;
        }
        return true;
    }

    public boolean isHandCardEmpty() {
        return handCards.size() == 0;
    }

    public int getNumberOfMonstersOnMonsterCardZone() {
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            if (!monsterCardZone[i].isEmpty()) counter++;
        }
        return counter;
    }

    public int getNumberOfCardsOnHandCardZone() {
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            if (!handCards.get(i).isEmpty()) counter++;
        }
        return counter;
    }

    public Cell getMonsterByIndex(int number) {
        return monsterCardZone[number - 1];
    }

    public boolean isCellVisibleToOpponent(Cell cell) {
        for (Cell cell1 : monsterCardZone) {
            if (cell == cell1 && cell.getCardStatus() == CardStatus.DEFENSIVE_HIDDEN) {
                return false;
            }
        }
        for (Cell cell1 : spellAndTrapCardZone) {
            if (cell == cell1 && cell.getCardStatus() == CardStatus.HIDDEN) {
                return false;
            }
        }
        return true;
    }

    public boolean isSpellAndTrapCardZoneFull() {
        for (int i = 0; i < 5; i++) {
            if (spellAndTrapCardZone[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean isSpellAndTrapCardZoneEmpty() {
        for (int i = 0; i < 5; i++) {
            if (!spellAndTrapCardZone[i].isEmpty()) return false;
        }
        return true;
    }


    public void addCardToGraveyard(Card card) {
        graveyard.add(new Cell(card));
    }

    public void addCardToSpellAndTrapCardZone(Card card, CardStatus cardStatus, GameController gameController) throws GameException {
        if (isSpellAndTrapCardZoneFull())
            throw new GameException(GameResponses.SPELL_ZONE_IS_FULL.response);

        for (int i = 0; i < 5; i++) {
            if (spellAndTrapCardZone[i].isEmpty()) {
                spellAndTrapCardZone[i].addCardToCell(card);
                spellAndTrapCardZone[i].setCardStatus(cardStatus);
                ImagePattern imagePattern = card.getCardImagePattern();
                Rectangle rectangle = spellAndTrapCardZone[i].getCellRectangle();
                setTranslationAnimation(imagePattern, rectangle, card);
                setFlipTransition(card, rectangle);
                for (double j = 0; j <= 1; j += 0.05) {
                    rectangle.opacityProperty().set(j);
                }
                gameController.getGameMenuController().addEventForCardImageRectangle(rectangle, card);
                gameController.changedPositionCells.add(spellAndTrapCardZone[i]);

                return;
            }
        }
    }

    private void setFlipTransition(Card card, Rectangle rectangle) {
        ScaleTransition hideFront = new ScaleTransition(Duration.millis(1000), rectangle);
        hideFront.setFromX(1);
        hideFront.setToX(0);
        hideFront.setInterpolator(Interpolator.EASE_IN);

        ScaleTransition showBack = new ScaleTransition(Duration.millis(1000), rectangle);
        showBack.setInterpolator(Interpolator.EASE_OUT);
        showBack.setFromX(0);
        showBack.setToX(1);
        hideFront.setOnFinished(t -> {
            rectangle.setFill(card.getCardBackImagePattern());
            showBack.play();
        });
        hideFront.play();
    }

    private void setTranslationAnimation(ImagePattern imagePattern, Rectangle rectangle, Card card) {
        TranslateTransition trans = new TranslateTransition(Duration.seconds(2), rectangle);
        trans.setToX(rectangle.getX());
        double constant = 1;
        double rotationValue = GameMenuController.getGameMenuController().background.rotateProperty().getValue() % 360;
        if (rotationValue > 179 && rotationValue < 181) constant = -1;
        if (!card.isMonster()) constant /= 2;
        trans.setFromX(constant * CardActionsMenu.getLastMousePositionX());
        trans.setToY(rectangle.getY());
        trans.setFromY(constant * CardActionsMenu.getLastMousePositionY());
        rectangle.setFill(imagePattern);
        trans.play();
    }

    public void addCardsToHandDeck(int countCard, boolean isToCurrentPlayer) {
        for (int i = 0; i < countCard; i++) {
            Card card = deckZone.get(0).getCellCard();
            Cell cell = new Cell(card);
            Rectangle rectangle = new Rectangle();
            GameMenuController.getGameMenuController().addEventForCardImageRectangle(rectangle, card);
            rectangle.setWidth(90);
            rectangle.setHeight(120);

            if (isToCurrentPlayer) {
                double rotationValue = GameMenuController.getGameMenuController().background.rotateProperty().getValue() % 360;
                if (rotationValue > 179 && rotationValue < 181 || countCard == 5) rectangle.rotateProperty().set(0);
                else {
                    rectangle.rotateProperty().set(180);
                }
                rectangle.setFill(card.getCardImagePattern());
            } else {
                rectangle.rotateProperty().set(180);
                rectangle.setFill(card.getCardBackImagePattern());
            }
            setTransitionForHandDeck(countCard, rectangle);
            cell.setCellRectangle(rectangle);
            handDeck.getChildren().add(rectangle);
            handCards.add(cell);


            //todo : remove deckzone card in graphic
            deckZone.remove(0);
        }
    }

    private void setTransitionForHandDeck(int countCard, Rectangle rectangle) {
        TranslateTransition trans = new TranslateTransition(Duration.seconds(2), rectangle);
        trans.setToX(rectangle.getX());
        double constant = -1;
        double rotationValue = GameMenuController.getGameMenuController().background.rotateProperty().getValue() % 360;
        if (rotationValue > 179 && rotationValue < 181) constant = 1;
        if (countCard > 1) constant = 1;
        trans.setFromX(constant * GameMenuController.getGameMenuController().userDeckZoneContainer.getLayoutX());
        trans.setToY(rectangle.getY());
        trans.setFromY(0);
        trans.play();
    }

    public void addCardToHandDeck(String cardName) {
        for (int i = 0; i < deckZone.size(); i++) {
            if (deckZone.get(i).getCellCard().getName().equals(cardName)) {
                Card card = deckZone.get(i).getCellCard();
                Cell cell = new Cell(card);
                Rectangle rectangle = new Rectangle();
                Duel.getGameController().getGameMenuController().addEventForCardImageRectangle(rectangle, card);
                rectangle.setWidth(90);
                rectangle.setHeight(120);
                rectangle.setFill(card.getCardImagePattern());
                cell.setCellRectangle(rectangle);
                handDeck.getChildren().add(rectangle);
                handCards.add(cell);
                ((Pane) gamePane.getChildren().get(3)).getChildren().remove(i);
                deckZone.remove(i);
            }
        }
    }

    public void addCardToFieldZone(Card card) {
        if (!fieldZone.isEmpty()) {
            addCardToGraveyard(fieldZone.getCellCard());
        }
        fieldZone.addCardToCell(card);

    }

    public boolean doesMonsterZoneHaveMonsters(int number) {
        int countMonsters = 0;
        for (Cell cell : monsterCardZone) {
            if (!cell.isEmpty()) {
                countMonsters++;
            }
        }
        return countMonsters >= number;
    }

    public boolean doesHandDeckHaveCard(int maxLevel, CardType cardType) {
        for (int i = 0; i < 5; i++) {
            try {
                handCards.get(i).getCellCard();
            } catch (Exception e) {
                continue;
            }
            if (handCards.get(i).isEmpty() || handCards.get(i).getCellCard().getCardKind() != Card.Kind.MONSTER)
                continue;
            Monster monster = (Monster) handCards.get(i).getCellCard();
            if (monster.getLevel() <= maxLevel && monster.getCardType() == cardType) return true;
        }
        return false;
    }

    public boolean doesMonsterZoneHaveOccupiedMonsters() {
        for (int i = 0; i < 5; i++) {
            if (!monsterCardZone[i].isEmpty() && monsterCardZone[i].getCardStatus() == CardStatus.DEFENSIVE_OCCUPIED ||
                    monsterCardZone[i].getCardStatus() == CardStatus.OFFENSIVE_OCCUPIED) {
                return true;
            }
        }
        return false;
    }

    public int getCountOccupiedMonstersInMonsterZone() {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            if (!monsterCardZone[i].isEmpty() && monsterCardZone[i].getCardStatus() == CardStatus.DEFENSIVE_OCCUPIED ||
                    monsterCardZone[i].getCardStatus() == CardStatus.OFFENSIVE_OCCUPIED) {
                count++;
            }
        }
        return count;
    }

    public Cell getMonsterZoneCardByMonsterName(String cardName) {
        for (int i = 0; i < 5; i++) {
            if (!monsterCardZone[i].isEmpty() && monsterCardZone[i].getCellCard().getName().equals(cardName))
                return monsterCardZone[i];
        }
        return null;
    }

    public Cell getSpellZoneCardByName(String cardName) {
        for (int i = 0; i < 5; i++) {
            if (!spellAndTrapCardZone[i].isEmpty() && spellAndTrapCardZone[i].getCellCard().getName().equals(cardName))
                return spellAndTrapCardZone[i];
        }
        return null;
    }

    public ArrayList<Card> getGraveyardMonsters() {
        ArrayList<Card> graveyardMonsters = new ArrayList<>();
        for (Cell cell : graveyard) {
            if (cell.getCellCard().isMonster()) graveyardMonsters.add(cell.getCellCard());
        }
        return graveyardMonsters;
    }

    public ArrayList<Cell> getGraveyardMonstersCell() {
        ArrayList<Cell> graveyardMonstersCell = new ArrayList<>();
        for (Cell cell : graveyard) {
            try {
                if (cell.getCellCard().isMonster()) graveyardMonstersCell.add(cell);
            } catch (Exception ignored) {
            }
        }
        return graveyardMonstersCell;
    }

    public void addAllMonstersATK(int amount) {
        for (int i = 0; i < 5; i++) {
            try {
                ((Monster) monsterCardZone[i].getCellCard()).addATK(amount);
            } catch (Exception ignored) {
            }
        }
    }

    public ArrayList<Card> getAllMonstersFromAllFieldsWithType(MonsterType monsterType) {
        ArrayList<Card> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            try {
                Monster monster = (Monster) monsterCardZone[i].getCellCard();
                if (monster.getMonsterType() == monsterType) result.add(monster);
            } catch (Exception ignored) {
            }
        }
        for (Cell cell : handCards) {
            try {
                Monster monster = (Monster) cell.getCellCard();
                if (monster.getMonsterType() == monsterType) result.add(monster);
            } catch (Exception ignored) {
            }
        }
        for (Cell cell : graveyard) {
            try {
                Monster monster = (Monster) cell.getCellCard();
                if (monster.getMonsterType() == monsterType) result.add(monster);
            } catch (Exception ignored) {
            }
        }
        return result;
    }

    public ArrayList<Cell> getMonsterZoneCells() {
        ArrayList<Cell> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            result.add(monsterCardZone[i]);
        }
        return result;
    }

    public boolean isCellInDeckZone(Cell cell) {
        for (Cell cell1 : deckZone) {
            if (cell1 == cell) {
                return true;
            }
        }
        return false;
    }

    public boolean isCellInGameBoard(Cell cell) {
        return isCellInHandZone(cell) || isCellInMonsterZone(cell) || isCellInSpellAndTrapZone(cell) || isCellInDeckZone(cell);
    }

    public void setGamePane(Pane pane, boolean isOpponent) {
        gamePane = (Pane) pane.getChildren().get(16);
        if (isOpponent)
            handDeck = (HBox) gamePane.getChildren().get(0);
        else {
            handDeck = (HBox) gamePane.getChildren().get(1);
        }

    }

    public Pane getGamePane() {
        return gamePane;
    }

    public void removeCardFromHand(Cell selectedCell) {
        handCards.remove(selectedCell);
        handDeck.getChildren().remove(selectedCell.getCellRectangle());
    }
}

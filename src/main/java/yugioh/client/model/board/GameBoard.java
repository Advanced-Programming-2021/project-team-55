package yugioh.client.model.board;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.controller.menucontroller.GameMenuController;
import yugioh.client.model.User;
import yugioh.client.model.cards.Card;
import yugioh.client.model.cards.Deck;
import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;
import yugioh.client.model.exceptions.GameException;
import yugioh.client.view.NetAdapter;
import yugioh.client.view.gamephases.CardActionsMenu;
import yugioh.client.view.gamephases.Duel;
import yugioh.client.view.gamephases.GameResponses;
import yugioh.client.view.menus.WelcomeMenu;
import yugioh.client.view.transitions.ExplodeAnimation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static yugioh.client.view.SoundPlayable.playButtonSound;

public class GameBoard {

    private static Pane gamePane;
    private final Cell[] monsterCardZone;
    private final ArrayList<Cell> graveyard;
    private final Cell[] spellAndTrapCardZone;
    private final ArrayList<Cell> deckZone;
    private final ArrayList<Cell> handCards;
    private final Cell fieldZone;
    public transient HashMap<Rectangle, Double> rectanglesInitRotateValues = new HashMap<>();
    private transient Rectangle graveyardPlace;
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
//        Collections.shuffle(mainDeck);
        for (Card card : mainDeck) {
            Cell cell = new Cell();
            Rectangle rectangle = new Rectangle();
            cell.addCardToCell(card);
            cell.setCellRectangle(rectangle);
            deckZone.add(cell);
        }
    }

    public int getMonsterNumberByCell(Cell cell) {
        for (int i = 0; i < monsterCardZone.length; i++) {
            if (monsterCardZone[i] == cell) return i;
        }
        return -1;
    }

    public void setBoardRectangles(Pane gamePane, boolean isOpponent) {
        GameBoard.gamePane = gamePane;
        fieldZone.setCellRectangle((Rectangle) gamePane.getChildren().get(36));
        rectanglesInitRotateValues.put(fieldZone.getCellRectangle(), fieldZone.getCellRectangle().rotateProperty().get());
        if (!isOpponent) {
            graveyardPlace = (Rectangle) gamePane.getChildren().get(4);
            rectanglesInitRotateValues.put(graveyardPlace, graveyardPlace.rotateProperty().get());
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
            graveyardPlace = (Rectangle) gamePane.getChildren().get(5);
            rectanglesInitRotateValues.put(graveyardPlace, graveyardPlace.rotateProperty().get());
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
        for (Cell cell : monsterCardZone) {
            rectanglesInitRotateValues.put(cell.getCellRectangle(), cell.getCellRectangle().rotateProperty().get());
        }
        for (Cell cell : spellAndTrapCardZone) {
            rectanglesInitRotateValues.put(cell.getCellRectangle(), cell.getCellRectangle().rotateProperty().get());
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
                //todo commented the deep way
                //NetAdapter.sendForwardRequestForGame("add card to monster zone "+card.getName()+" "+cardStatus.name());
                monsterCardZone[i].addCardToCell(card);
                monsterCardZone[i].setCardStatus(cardStatus);
                ImagePattern imagePattern ;
                if(cardStatus==CardStatus.DEFENSIVE_HIDDEN) {
                    if (User.loggedInUser.equals(gameController.currentTurnPlayer.getUser()))
                        imagePattern = card.getCardImagePattern();
                    else imagePattern = card.getCardBackImagePattern();
                }else imagePattern=card.getCardImagePattern();
                Label cellInfo = monsterCardZone[i].getCellInfo();
                Rectangle rectangle = monsterCardZone[i].getCellRectangle();
                setTranslationAnimation(imagePattern, monsterCardZone[i], card);
                if (cardStatus == CardStatus.DEFENSIVE_HIDDEN) {
                    if(User.loggedInUser.equals(gameController.currentTurnPlayer.getUser()))
                    setFlipTransition(card, rectangle, true, false);
                    setFlipZTransition(rectangle, true);
                } else{
                    CardActionsMenu.makeSwordEventForSummonedMonsters(rectangle);
                    if(!User.loggedInUser.equals(gameController.currentTurnPlayer.getUser())){
                        setFlipTransition(card,rectangle,false,false);
                    }
                }
                Duel.getGameController().currentTurnPlayer.getGameBoard().setFadeTransition(rectangle, 0, 1);
                if (CardActionsMenu.isBoardInverse()) {
                    monsterCardZone[i].getCellInfo().rotateProperty().set(180);
                }
                if (!monsterCardZone[i].isEventSet) {
                    gameController.getGameMenuController().addEventForCardImageRectangle(rectangle, monsterCardZone[i]);
                    monsterCardZone[i].setEventIsSet();
                }
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


    public void addCardToGraveyard(Cell cell) {
        Card card = cell.getCellCard();

        graveyard.add(new Cell(card));
        moveCardToGraveyard(cell);
    }

    public void moveCardToGraveyard(Cell cell) {
        playButtonSound("graveYard");
//        Rectangle graveyard;
//        if (CardActionsMenu.isBoardInverse()) graveyard = GameMenuController.getGameMenuController().rivalGraveyard;
//        else graveyard = GameMenuController.getGameMenuController().userGraveyard;
        try {
            graveyardPlace.fillProperty().setValue(cell.getCellCard().getCardBackImagePattern());
        } catch (Exception ignored) {
        }
        setFadeTransition(graveyardPlace, 0, 1);
    }
    public String getCellInfo(Cell cell){
        //todo handle selecting from other player hand
        String info="";
        int number=1;
        for(Cell cell1:monsterCardZone){
            if(cell1.equals(cell)){
                info+="--monster "+number;
            }
            number++;
        }
        number=1;
        for(Cell cell1:spellAndTrapCardZone){
            if(cell1.equals(cell)){
                info+="--spell "+number;
            }
            number++;
        }
        number=1;
        for(Cell cell1:handCards){
            if(cell1.equals(cell)){
                info+="--hand "+number;
            }
            number++;
        }
        return info;
    }

    public void addCardToSpellAndTrapCardZone(Card card, CardStatus cardStatus, GameController gameController, boolean hasToBeRemoved) throws GameException {
        if (isSpellAndTrapCardZoneFull())
            throw new GameException(GameResponses.SPELL_ZONE_IS_FULL.response);

        for (int i = 0; i < 5; i++) {
            if (spellAndTrapCardZone[i].isEmpty()) {
                //todo commented the deep way
                //NetAdapter.sendForwardRequestForGame("add card to spell zone "+card.getName()+" "+cardStatus.name()+" "+hasToBeRemoved);
                Cell cell = spellAndTrapCardZone[i];
                cell.addCardToCell(card);
                cell.setCardStatus(cardStatus);
                ImagePattern imagePattern ;
                Rectangle rectangle = cell.getCellRectangle();
                if(cardStatus==CardStatus.HIDDEN) {
                    if (User.loggedInUser.equals(gameController.currentTurnPlayer.getUser())) {
                        imagePattern = card.getCardImagePattern();
                        setFlipTransition(card, rectangle, true, false);
                    }
                    else imagePattern = card.getCardBackImagePattern();
                }else {
                    if(!User.loggedInUser.equals(gameController.currentTurnPlayer.getUser())){
                        setFlipTransition(card,rectangle,false,false);
                    }
                    imagePattern=card.getCardImagePattern();
                }
                setTranslationAnimation(imagePattern, spellAndTrapCardZone[i], card);
                for (double j = 0; j <= 1; j += 0.05) {
                    rectangle.opacityProperty().set(j);
                }
                if (hasToBeRemoved) {
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> cell.removeCardFromCell(this)));
                    timeline.play();
                    return;
                }
//                for (double j = 0; j <= 1; j += 0.05) {
//                    rectangle.opacityProperty().set(j);
//                }
                if (!cell.isEventSet) {
                    gameController.getGameMenuController().addEventForCardImageRectangle(rectangle, spellAndTrapCardZone[i]);
                    cell.setEventIsSet();
                }
                gameController.changedPositionCells.add(cell);
                return;
            }
        }
    }

    public void setFlipZTransition(Rectangle rectangle, boolean toHorizontal) {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(rectangle);
        rotateTransition.setAxis(Rotate.Z_AXIS);
        if (toHorizontal) {
            rotateTransition.setByAngle(-90);
        } else {
            rotateTransition.setByAngle(90);
        }
        rotateTransition.setDuration(Duration.millis(250));
        rotateTransition.play();
//
//        ScaleTransition hideFront = new ScaleTransition(Duration.millis(250), rectangle);
//        hideFront.setFromX(1);
//        hideFront.setToX(0);
//        hideFront.setInterpolator(Interpolator.EASE_IN);
//
//        ScaleTransition showBack = new ScaleTransition(Duration.millis(250), rectangle);
//        showBack.setInterpolator(Interpolator.EASE_OUT);
//        showBack.setFromX(0);
//        showBack.setToX(1);
//        hideFront.setOnFinished(t -> {
//
//            rectangle.rotateProperty().set(angle);
//            showBack.play();
//        });
//        hideFront.play();
    }

    public void setFlipTransition(Card card, Rectangle rectangle, boolean isToBack, boolean hasToBeRemoved) {
        ScaleTransition hideFront = new ScaleTransition(Duration.millis(1000), rectangle);
        hideFront.setFromX(1);
        hideFront.setToX(0);
        hideFront.setInterpolator(Interpolator.EASE_IN);

        ScaleTransition showBack = new ScaleTransition(Duration.millis(1000), rectangle);
        showBack.setInterpolator(Interpolator.EASE_OUT);
        showBack.setFromX(0);
        showBack.setToX(1);
        hideFront.setOnFinished(t -> {
            if (isToBack)
                rectangle.setFill(card.getCardBackImagePattern());
            else rectangle.setFill(card.getCardImagePattern());
            showBack.play();

        });
        showBack.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (hasToBeRemoved) {
                    Cell.getSelectedCellByRectangle(rectangle).removeCardFromCell(GameBoard.this);
                }
            }
        });
        hideFront.play();
    }

    private void setTranslationAnimation(ImagePattern imagePattern, Cell cell, Card card) {
        Rectangle rectangle = cell.getCellRectangle();
        TranslateTransition trans = new TranslateTransition(Duration.seconds(2), rectangle);
        trans.setToX(rectangle.getX());
        double constant = 1;
        double rotationValue = GameMenuController.getGameMenuController().background.rotateProperty().getValue() % 360;
        if (rotationValue > 179 && rotationValue < 181) constant = -1;
        if (!card.isMonster()) constant /= 2;
        trans.setFromX(constant * CardActionsMenu.getLastMousePositionX());
        trans.setToY(rectangle.getY());
        trans.setFromY(constant * CardActionsMenu.getLastMousePositionY());
        if (card.isMonster())
            if(cell.getCardStatus()==CardStatus.DEFENSIVE_HIDDEN) {
                if (User.loggedInUser.equals(Duel.getGameController().currentTurnPlayer.getUser())) {
                    cell.getCellInfo().setText(((Monster) card).getAtk() + "/" + ((Monster) card).getDef());
                }
            }else
            cell.getCellInfo().setText(((Monster) card).getAtk() + "/" + ((Monster) card).getDef());
        rectangle.setFill(imagePattern);
        trans.play();
    }

    public void setTranslationAnimation(ImageView imageView, Rectangle rectangle) {
        TranslateTransition trans = new TranslateTransition(Duration.seconds(0.5), imageView);
        trans.setByX(rectangle.getLayoutX() - imageView.getX());
        trans.setByY(rectangle.getLayoutY() - imageView.getY());
        trans.setInterpolator(Interpolator.EASE_IN);
        trans.play();
        trans.setOnFinished(event -> new ExplodeAnimation(imageView));
    }

    public void setFadeTransition(Node node, double fromValue, double toValue) {
        FadeTransition ft = new FadeTransition(Duration.millis(2000), node);
        ft.setFromValue(fromValue);
        ft.setToValue(toValue);
        ft.play();
    }
    public ArrayList<Cell> getAllCellsInBoard(){
        ArrayList<Cell>cells=new ArrayList<>();
        for(Cell cell:monsterCardZone){
            cells.add(cell);
        }
        for(Cell cell:spellAndTrapCardZone){
            cells.add(cell);
        }
        for(Cell cell:handCards){
            cells.add(cell);
        }
        return cells;

    }

    public void addCardsToHandDeck(int countCard, boolean isToCurrentPlayer) {
        for (int i = 0; i < countCard; i++) {
            Card card = deckZone.get(0).getCellCard();
            Cell cell = new Cell(card);
            Rectangle rectangle = new Rectangle();
            GameMenuController.getGameMenuController().addEventForCardImageRectangle(rectangle, cell);
            rectangle.setWidth(90);
            rectangle.setHeight(120);

            if (isToCurrentPlayer) {
                double rotationValue = GameMenuController.getGameMenuController().background.rotateProperty().getValue() % 360;
                if (rotationValue > 179 && rotationValue < 181 || countCard == 5) rectangle.rotateProperty().set(0);
                else {
                    rectangle.rotateProperty().set(180);
                }
                if(User.loggedInUser.equals(Duel
                .getGameController().currentTurnPlayer.getUser())){
                    rectangle.setFill(card.getCardImagePattern());
                }
                else rectangle.setFill(card.getCardBackImagePattern());

            } else {
                rectangle.rotateProperty().set(180);
                if(User.loggedInUser.equals(Duel
                        .getGameController().currentTurnPlayer.getUser())){
                    rectangle.setFill(card.getCardBackImagePattern());
                }
                else rectangle.setFill(card.getCardImagePattern());
                rectangle.setFill(card.getCardBackImagePattern());
            }
            setTransitionForHandDeck(countCard, rectangle);
            cell.setCellRectangle(rectangle);
            handDeck.getChildren().add(rectangle);
            handCards.add(cell);

            try {
                ((Pane) gamePane.getChildren().get(3)).getChildren().remove(0);
            } catch (Exception ignored) {
            }
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

    public void addCardToHandDeck(Card cardToAdd, boolean isForce) {
        playButtonSound("card");
        //todo commented the deep way
       // NetAdapter.sendForwardRequestForGame("add card to hand "+cardToAdd+" "+isForce);
        if (isForce) {
            Cell cell = new Cell(cardToAdd);
            Rectangle rectangle = new Rectangle();
            Duel.getGameController().getGameMenuController().addEventForCardImageRectangle(rectangle, cell);
            rectangle.setWidth(90);
            rectangle.setHeight(120);
            //rectangle.setFill(cardToAdd.getCardImagePattern());
            cell.setCellRectangle(rectangle);
            if(User.loggedInUser.equals(Duel
                    .getGameController().currentTurnPlayer.getUser())){
                rectangle.setFill(cardToAdd.getCardImagePattern());
            }
            else rectangle.setFill(cardToAdd.getCardBackImagePattern());
            double rotationValue = GameMenuController.getGameMenuController().background.rotateProperty().getValue() % 360;
            if (rotationValue > 179 && rotationValue < 181) {
                rectangle.rotateProperty().set(180);
                handDeck.getChildren().add(0, rectangle);

            } else {
                rectangle.rotateProperty().set(0);
                handDeck.getChildren().add(rectangle);
            }

            setTransitionForHandDeck(1, rectangle);
            handCards.add(cell);
            return;
        }
        String cardName = cardToAdd.getName();
        for (int i = 0; i < deckZone.size(); i++) {
            if (deckZone.get(i).getCellCard().getName().equals(cardName)) {
                Card card = deckZone.get(i).getCellCard();
                Cell cell = new Cell(card);
                Rectangle rectangle = new Rectangle();
                Duel.getGameController().getGameMenuController().addEventForCardImageRectangle(rectangle, cell);
                rectangle.setWidth(90);
                rectangle.setHeight(120);

                rectangle.setFill(card.getCardImagePattern());
                cell.setCellRectangle(rectangle);
                double rotationValue = GameMenuController.getGameMenuController().background.rotateProperty().getValue() % 360;
                if (rotationValue > 179 && rotationValue < 181) {
                    rectangle.rotateProperty().set(180);
                    handDeck.getChildren().add(0, rectangle);

                } else {
                    rectangle.rotateProperty().set(0);
                    handDeck.getChildren().add(rectangle);
                }
                handCards.add(cell);
                ((Pane) gamePane.getChildren().get(3)).getChildren().remove(i);
                deckZone.remove(i);
            }
        }
    }

    public void addCardToFieldZone(Cell cell) {
        GameMenuController gameMenuController = GameMenuController.getGameMenuController();
        if (!fieldZone.isEmpty()) {
            Rectangle graveyard = gameMenuController.userGraveyard;
//            gameMenuController.gameController.getBattlePhaseController().moveCardToGraveyard(cell, graveyard, gameMenuController.gameController.currentTurnPlayer);
            fieldZone.removeCardFromCell(gameMenuController.gameController.currentTurnPlayer.getGameBoard());
        }
        setTranslationAnimation(cell.getCellCard().getCardImagePattern(), fieldZone, cell.getCellCard());
        fieldZone.addCardToCell(cell.getCellCard());
        gameMenuController.gameController.currentTurnPlayer.getGameBoard().setFadeTransition(fieldZone.getCellRectangle(), 0, 1);
        if (!fieldZone.isEventSet) {
            gameMenuController.addEventForCardImageRectangle(fieldZone.getCellRectangle(), fieldZone);
            fieldZone.setEventIsSet();
        }
        try {
            Image backGround = new Image("/yugioh/PNG/Field/fie_normal.jpg");
            switch (cell.getCellCard().getName()) {
                case "Forest":
                    backGround = new Image("/yugioh/PNG/Field/fie_gaia.jpg");
                    break;
                case "Yami":
                    backGround = new Image("/yugioh/PNG/Field/fie_yami.jpg");
                    break;
                case "Closed Forest":
                    backGround = new Image("/yugioh/PNG/Field/fie_mori.jpg");
                    break;
                case "Umiiruka":
                    backGround = new Image("/yugioh/PNG/Field/fie_umi.jpg");
                    break;
            }
            GameMenuController.getGameMenuController().background.setImage(backGround);
        } catch (Exception ignored) {
        }
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

    public ArrayList<Cell> getAllMonstersFromAllFieldsWithType(MonsterType monsterType) {
        ArrayList<Cell> result = new ArrayList<>();
        for (Cell cell : deckZone) {
            try {
                Monster monster = (Monster) cell.getCellCard();
                if (monster.getMonsterType() == monsterType) result.add(cell);
            } catch (Exception e) {

            }
        }
        for (Cell cell : handCards) {
            try {
                Monster monster = (Monster) cell.getCellCard();
                if (monster.getMonsterType() == monsterType) result.add(cell);
            } catch (Exception ignored) {
            }
        }
        for (Cell cell : graveyard) {
            try {
                Monster monster = (Monster) cell.getCellCard();
                if (monster.getMonsterType() == monsterType) result.add(cell);
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
        //todo commented the deep way
       // NetAdapter.sendForwardRequestForGame("remove card from hand");
        handCards.remove(selectedCell);
        handDeck.getChildren().remove(selectedCell.getCellRectangle());
    }

    public boolean isMonsterZoneFull() {
        for (int i = 0; i < 5; i++) {
            if (monsterCardZone[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void handleTribute(GameController gameController, int countTributes, boolean isSummon) {
        Stage tributeStage = new Stage();
        tributeStage.initOwner(WelcomeMenu.stage);
        tributeStage.initStyle(StageStyle.UNDECORATED);
        tributeStage.initModality(Modality.NONE);
        CardActionsMenu.setToBeSummonedCell(Cell.getSelectedCell());
        URL url = getClass().getResource("/yugioh/fxml/TributeMenu.fxml");
        try {
            Pane pane = FXMLLoader.load(url);
            Scene scene = WelcomeMenu.createScene(pane);
            tributeStage.setScene(scene);
            Button yesButton = (Button) ((HBox) ((VBox) pane.getChildren().get(0)).getChildren().get(1)).getChildren().get(0);
            Button noButton = (Button) ((HBox) ((VBox) pane.getChildren().get(0)).getChildren().get(1)).getChildren().get(1);
            yesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    tributeStage.close();
                    playButtonSound("tribute");
                    gameController.getGameMenuController().shouldSelectTributesNow = true;
                    gameController.getGameMenuController().neededTributes = countTributes;
                    gameController.getGameMenuController().isTributeForSummon = isSummon;
                }
            });
            noButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    tributeStage.close();
                }
            });
            tributeStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCardToSpellAndTrapCardZoneActivated() {

    }
}

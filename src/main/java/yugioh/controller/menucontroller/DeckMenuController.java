package yugioh.controller.menucontroller;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import yugioh.model.User;
import yugioh.model.cards.Card;
import yugioh.model.cards.Deck;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.exceptions.MenuException;
import yugioh.view.Responses;
import yugioh.view.SoundPlayable;
import yugioh.view.gamephases.Graveyard;
import yugioh.view.menus.DeckMenu;
import yugioh.view.menus.EditDeckMenu;
import yugioh.view.menus.PopUpWindow;
import yugioh.view.menus.WelcomeMenu;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

import static yugioh.model.cards.cardfeaturesenums.EffectiveTerm.LIMITED;

public class DeckMenuController extends MenuController implements Initializable {

    public static DeckMenuController deckMenuController;
    public MenuButton decksBox;
    public Pane deckPane;
    public Label deckInfo;
    public Button deleteDeckButton;
    public Button editDeckButton;
    public Button activateDeckButton;

    private MenuItem selectedMenuItem;

    public DeckMenuController() {
    }

    public static DeckMenuController getInstance() {
        if (deckMenuController == null) {
            return new DeckMenuController();
        } else {
            return deckMenuController;
        }
    }

    public void createDeck(String deckName) throws MenuException {
        if (Deck.deckNameExists(deckName, User.loggedInUser)) {
            throw new MenuException("Error: deck with name " + deckName + " already exists");
        } else {
            User.loggedInUser.addDeck(new Deck(deckName));
        }

    }

    public void deleteDeck(String deckName) throws MenuException {
        Deck deck = User.loggedInUser.getDeckByName(deckName);
        if (deck == null) {
            throw new MenuException("Error: deck with name " + deckName + " does not exist");
        } else {
            User.loggedInUser.removeDeck(deck);
            User.loggedInUser.addCardsToInventory(deck.getMainDeck());
            User.loggedInUser.addCardsToInventory(deck.getSideDeck());
        }
    }

    public void activeDeck(String deckName) throws MenuException {
        Deck deck = User.loggedInUser.getDeckByName(deckName);
        if (deck == null) {
            throw new MenuException("Error: deck with name " + deckName + " does not exist");
        } else {
            User.loggedInUser.setActiveDeck(deck);
        }

    }

    public void addCardToDeck(String cardName, String deckName, boolean isSide) throws MenuException {
        Card card = Card.getCardByName(cardName);
        Deck deck = User.loggedInUser.getDeckByName(deckName);
        if (!User.loggedInUser.cardExistsInInventory(cardName)) {
            throw new MenuException("Error: card with name " + cardName + " does not exist");
        } else if (deck == null) {
            throw new MenuException("Error: deck with name " + deckName + " does not exist");
        } else if (deck.isMainDeckFull()) {
            throw new MenuException(Responses.MAIN_DECK_FULL.response);
        } else if (deck.isSideDeckFull()) {
            throw new MenuException(Responses.SIDE_DECK_FULL.response);
        } else if (deck.getCardCountInDeck(cardName) == 3) {
            throw new MenuException("Error: there are already three cards with name " + cardName + " in deck " + deckName);
        } else if (card.isSpellAndTrap() &&
                ((SpellAndTrap) card).getStatus() == LIMITED &&
                deck.isCardInDeck(cardName)) {
            throw new MenuException("Error: card " + cardName +
                    " frequency is limited by one and there is already a card with this name in deck " + deckName);
        } else {
            User.loggedInUser.removeCardFromInventory(card);
            if (isSide) {
                deck.addCardToSideDeck(card);
            } else {
                deck.addCardToMainDeck(card);
            }
        }
    }

    public void removeCardFromDeck(String cardName, String deckName, boolean isSide) throws MenuException {
        Deck deck = User.loggedInUser.getDeckByName(deckName);
        if (deck == null) {
            throw new MenuException("Error: deck with name " + deckName + " does not exist");
        } else if (isSide && !deck.cardExistsInSideDeck(cardName)) {
            throw new MenuException("Error: card with name " + cardName + " does not exist in side deck");
        } else if (!isSide & !deck.cardExistsInMainDeck(cardName)) {
            throw new MenuException("Error: card with name " + cardName + " does not exist in main deck");
        } else {
            if (isSide) {
                deck.removeCardFromSideDeck(cardName);
            } else {
                deck.removeCardFromMainDeck(cardName);
            }
        }
    }

    public ArrayList<Deck> getAllDecks() {
        ArrayList<Deck> decksToShow = User.loggedInUser.getDecks();
        Deck activeDeck = User.loggedInUser.getActiveDeck();
        boolean activeDeckRemoved = false;
        if (activeDeck != null) {
            decksToShow.remove(activeDeck);
            activeDeckRemoved = true;
        }
        Collections.sort(decksToShow, new Comparator<Deck>() {
            @Override
            public int compare(Deck deck1, Deck deck2) {
                return deck1.getName().compareTo(deck2.getName());
            }
        });
        if (activeDeckRemoved) {
            decksToShow.add(0, activeDeck);
        }
        return decksToShow;
    }

    public String getADeck(String deckName, boolean isSide) throws MenuException {
        for (Deck deck : User.loggedInUser.getDecks()) {
            if (deck.getName().equals(deckName)) {
                String deckInfo = "Deck: " + deckName + "\n";
                if (isSide) {
                    ArrayList<Card> monsters = Card.getMonstersSorted(deck.getSideDeck());
                    ArrayList<Card> spellAndTraps = Card.getMagicsSorted(deck.getSideDeck());
                    deckInfo += "Side deck:\nMonsters:";
                    for (Card card : monsters) {
                        deckInfo += "\n" + card;
                    }
                    deckInfo += "\nSpell and Traps:";
                    for (Card card : spellAndTraps) {
                        deckInfo += "\n" + card;
                    }
                } else {
                    ArrayList<Card> monsters = Card.getMonstersSorted(deck.getMainDeck());
                    ArrayList<Card> spellAndTraps = Card.getMagicsSorted(deck.getMainDeck());
                    deckInfo += "yugioh.Main deck:\nMonsters:";
                    for (Card card : monsters) {
                        deckInfo += "\n" + card;
                    }
                    deckInfo += "\nSpell and Traps:";
                    for (Card card : spellAndTraps) {
                        deckInfo += "\n" + card;
                    }
                }
                return deckInfo;
            }
        }
        throw new MenuException("Error: deck with name " + deckName + " does not exist");
    }

    public ArrayList<Card> getCards() {
        return Card.sortCards(User.loggedInUser.getCardsInventory());
    }

    public void backClicked() throws Exception {
        SoundPlayable.playButtonSound("backButton");
        mainMenu.execute();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editDeckButton.setDisable(true);
        deleteDeckButton.setDisable(true);
        activateDeckButton.setDisable(true);
        decksBox.textProperty().addListener((observableValue, s, t1) -> {

            activateDeckButton.setDisable(t1.contains("Active deck"));
            if (t1.equals("Decks")) {
                deleteDeckButton.setDisable(true);
                editDeckButton.setDisable(true);
                activateDeckButton.setDisable(true);
            } else {
                deleteDeckButton.setDisable(false);
                editDeckButton.setDisable(false);
            }
        });
        updateMenuItems();
    }

    private void setDeckView(Deck deck) {
        try {
            deckPane.getChildren().clear();
        } catch (Exception ignored) {
        }
        ArrayList<Card> mainCards = new ArrayList<>(Card.sortCards(deck.getMainDeck()));
        ArrayList<Card> sideCards = new ArrayList<>(Card.sortCards(deck.getSideDeck()));
        int cardsPerRow = 9;
        int columnCounter = 0;
        GridPane cardsPane = new GridPane();
        outer:
        while (mainCards.size() > 0) {
            for (int j = 0; j < cardsPerRow; j++) {
                Card card = mainCards.get(mainCards.size() - 1);
                ImageView cardImage = card.getCardImageForDeck(40);
                cardsPane.add(cardImage, j, columnCounter);
                mainCards.remove(card);
                if (mainCards.size() == 0) break outer;
            }
            columnCounter++;
        }
        columnCounter++;
        ImageView space = new ImageView(new Image("/yugioh/PNG/icon/icon.png"));
        cardsPane.add(space, 0, columnCounter);
        space.setOpacity(0);
        space.setPreserveRatio(true);
        space.setFitWidth(10);
        columnCounter++;
        outer:
        while (sideCards.size() > 0) {
            for (int j = 0; j < cardsPerRow; j++) {
                Card card = sideCards.get(sideCards.size() - 1);
                ImageView cardImage = card.getCardImageForDeck(40);
                cardsPane.add(cardImage, j, columnCounter);
                sideCards.remove(card);
                if (sideCards.size() == 0) break outer;
            }
            columnCounter++;
        }
        deckPane.getChildren().add(cardsPane);
        cardsPane.setAlignment(Pos.CENTER);
    }

    public void deleteDeckClicked() {
        SoundPlayable.playButtonSound("backButton");
        Deck deck = (Deck) selectedMenuItem.getUserData();
        User.loggedInUser.removeDeck(deck);
        deckPane.getChildren().clear();
        deckInfo.setText("");
        decksBox.getItems().remove(selectedMenuItem);
        selectedMenuItem = null;
        decksBox.setText("Decks");
    }

    public void editDeckClicked() throws Exception {
        SoundPlayable.playButtonSound("enterButton");
        if (selectedMenuItem == null || selectedMenuItem.getUserData() == null) {
            new PopUpWindow("Error: select a Deck from menu bar first!").start(DeckMenu.stage);
            return;
        }
        new EditDeckMenu((Deck) selectedMenuItem.getUserData()).start(WelcomeMenu.stage);

    }

    public void newDeckClicked() throws Exception {
        SoundPlayable.playButtonSound("enterButton");
        selectDeckNamePage.execute();
    }

    public void activateDeckClicked() {
        SoundPlayable.playButtonSound("enterButton");
        Deck deck = (Deck) selectedMenuItem.getUserData();
        selectedMenuItem.setText(selectedMenuItem.getText() + " (Active deck)");
        decksBox.setText(decksBox.getText() + " (Active deck)");
        User.loggedInUser.setActiveDeck(deck);
        updateMenuItems();
    }

    public void updateMenuItems() {
        decksBox.getItems().clear();
        for (Deck deck : User.loggedInUser.getDecks()) {
            MenuItem deckItem = new MenuItem();
            deckItem.setText(deck.getName());
            if (deck.isActive()) {
                deckItem.setText(deck.getName() + " (Active deck)");
                deckItem.setStyle("-fx-background-color: green;");
            }
            deckItem.setUserData(deck);
            deckItem.setOnAction(actionEvent -> {
                selectedMenuItem = deckItem;
                Deck selectedDeck = (Deck) deckItem.getUserData();
                decksBox.setText(deckItem.getText());
                deckInfo.setText(selectedDeck.toString());
                setDeckView(selectedDeck);
                try {
                    Graveyard.getGraveyardStage().close();
                } catch (Exception ignored) {
                }
            });
            Rectangle rectangle = new Rectangle();
            rectangle.setWidth(10);
            rectangle.setHeight(10);
            deckItem.setGraphic(rectangle);
            rectangle.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> new Graveyard().executeFloatStage(deck.getMainDeck()));
            decksBox.getItems().add(deckItem);
        }

        if (selectedMenuItem != null) {
            decksBox.setText(selectedMenuItem.getText());
        }
    }

}

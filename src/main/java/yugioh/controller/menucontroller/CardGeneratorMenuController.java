package yugioh.controller.menucontroller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import yugioh.model.User;
import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;
import yugioh.view.SoundPlayable;
import yugioh.view.menus.CardGeneratorMenu;
import yugioh.view.menus.PopUpWindow;
import yugioh.view.menus.WelcomeMenu;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;


public class CardGeneratorMenuController extends MenuController implements Initializable {
    public TextField attackField;
    public TextField defenseField;
    public TextField cardName;
    public ImageView cardImage;


    public MenuButton monsterAttribute;
    public MenuButton monsterType;
    public MenuButton cardType;
    public Slider level;

    public TextArea descriptionBox;
    public Button generateButton;

    public Label usersCoin;

    public Label price;
    public MediaView cardGeneratorMenuBackground;


    public void backToShop(MouseEvent mouseEvent) throws Exception {
        SoundPlayable.playButtonSound("backButton");
        shopMenu.execute();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src\\resources\\yugioh\\Backgrounds\\main.mp4").toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        cardGeneratorMenuBackground.setMediaPlayer(mediaPlayer);
        handleAllMenuItems();
        attackField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                Platform.runLater(() -> {
                    int priceValue = 0;
                    if (attackField.getText().matches("^(\\d+)$"))
                        priceValue += Integer.parseInt(attackField.getText()) * 10;
                    if (defenseField.getText().matches("^(\\d+)$"))
                        priceValue += Integer.parseInt(defenseField.getText()) * 5;
                    priceValue += level.getValue() * 100;
                    price.setText(String.valueOf(priceValue));
                });
            }
        });
        defenseField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                Platform.runLater(() -> {
                    int priceValue = 0;
                    if (attackField.getText().matches("^(\\d+)$"))
                        priceValue += Integer.parseInt(attackField.getText()) * 10;
                    if (defenseField.getText().matches("^(\\d+)$"))
                        priceValue += Integer.parseInt(defenseField.getText()) * 5;
                    priceValue += level.getValue() * 100;
                    price.setText(String.valueOf(priceValue));
                });
            }
        });
        level.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Platform.runLater(() -> {
                    int priceValue = 0;
                    if (attackField.getText().matches("^(\\d+)$"))
                        priceValue += Integer.parseInt(attackField.getText()) * 10;
                    if (defenseField.getText().matches("^(\\d+)$"))
                        priceValue += Integer.parseInt(defenseField.getText()) * 5;
                    priceValue += level.getValue() * 100;
                    price.setText(String.valueOf(priceValue));
                });
            }
        });

        generateButton.setDisable(true);
        setMenuItemsValues();
        cardImage.setImage(new Image(new File("src\\resources\\yugioh\\PNG\\cardsImages\\questionMark.jpg")
                .toURI().toString()));
        Platform.runLater(() -> usersCoin.setText(String.valueOf(User.loggedInUser.getMoney())));
        price.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                generateButton.setDisable(Integer.parseInt(t1) > User.loggedInUser.getMoney());
            }
        });
    }

    private void handleAllMenuItems() {
        for (MenuItem menuItem : cardType.getItems()) {
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    cardType.setText(menuItem.getText());
                }
            });
        }
        for (MenuItem menuItem : monsterType.getItems()) {
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    monsterType.setText(menuItem.getText());
                }
            });
        }
        for (MenuItem menuItem : monsterAttribute.getItems()) {
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    monsterAttribute.setText(menuItem.getText());
                }
            });
        }
    }

    private void setMenuItemsValues() {
        ObservableList<MenuItem> attributes = monsterAttribute.getItems();
        attributes.get(0).setUserData(MonsterAttribute.DARK);
        attributes.get(1).setUserData(MonsterAttribute.EARTH);
        attributes.get(2).setUserData(MonsterAttribute.FIRE);
        attributes.get(3).setUserData(MonsterAttribute.LIGHT);
        attributes.get(4).setUserData(MonsterAttribute.WATER);
        attributes.get(5).setUserData(MonsterAttribute.WIND);

        ObservableList<MenuItem> monsterTypes = monsterType.getItems();
        monsterTypes.get(0).setUserData(MonsterType.BEAST_WARRIOR);
        monsterTypes.get(1).setUserData(MonsterType.WARRIOR);
        monsterTypes.get(2).setUserData(MonsterType.AQUA);
        monsterTypes.get(3).setUserData(MonsterType.FIEND);
        monsterTypes.get(4).setUserData(MonsterType.BEAST);
        monsterTypes.get(5).setUserData(MonsterType.PYRO);
        monsterTypes.get(6).setUserData(MonsterType.SPELLCASTER);
        monsterTypes.get(7).setUserData(MonsterType.THUNDER);
        monsterTypes.get(8).setUserData(MonsterType.DRAGON);
        monsterTypes.get(9).setUserData(MonsterType.MACHINE);
        monsterTypes.get(10).setUserData(MonsterType.ROCK);
        monsterTypes.get(11).setUserData(MonsterType.INSECT);
        monsterTypes.get(12).setUserData(MonsterType.CYBERSE);
        monsterTypes.get(13).setUserData(MonsterType.FAIRY);
        monsterTypes.get(14).setUserData(MonsterType.SEA_SERPENT);

        ObservableList<MenuItem> cardTypes = cardType.getItems();
        cardTypes.get(0).setUserData(CardType.NORMAL);
        cardTypes.get(1).setUserData(CardType.EFFECTIVE);
        cardTypes.get(2).setUserData(CardType.RITUAL);


    }

    public void generateClicked(MouseEvent mouseEvent) throws Exception {
        if (
                monsterType.getText().equals("Monster type") || cardType.getText().equals("Card type")
                        || monsterAttribute.getText().equals("Monster attribute") || attackField.getText().equals("") ||
                        !attackField.getText().matches("^(\\d+)$") || defenseField.getText().equals("") ||
                        !defenseField.getText().matches("^(\\d+)$") || descriptionBox.getText().equals("") || cardName.getText().equals("")) {
            new PopUpWindow("Error: fill all the fields!").start(WelcomeMenu.stage);
        } else {
            User.loggedInUser.changeMoney((int) (-Integer.parseInt(price.getText()) * 0.1));
            Monster monster = new Monster(cardName.getText(), descriptionBox.getText(), Integer.parseInt(price.getText()),
                    Integer.parseInt(attackField.getText()), Integer.parseInt(defenseField.getText()), (int) level.getValue(),
                    (MonsterAttribute) getMenuItem(monsterAttribute.getText(), "Monster attribute"), (MonsterType) getMenuItem(monsterType.getText(),
                    "Monster type"), (CardType) getMenuItem(cardType.getText(), "Card type"));
            monster.setIsCustom();
            monster.setImage(cardImage);
            monster.setImage();

            Path sourceDirectory = Paths.get("src\\resources\\yugioh\\PNG\\cardsImages\\questionMark.jpg");
            Path targetDirectory = Paths.get("src\\resources\\yugioh\\PNG\\cardsImages\\" + cardName.getText() + ".jpg");
            try {
                Files.copy(sourceDirectory, targetDirectory);
            } catch (Exception e) {
                new PopUpWindow("Error: card with this name already exists!").start(WelcomeMenu.stage);
            }
            new CardGeneratorMenu().execute();
            new PopUpWindow("card generated successfully!").start(WelcomeMenu.stage);

        }

    }

    private Object getMenuItem(String name, String from) {
        if (from.equals("Monster attribute")) {
            for (MenuItem menuItem : monsterAttribute.getItems()) {
                if (menuItem.getText().equals(name)) {
                    return menuItem.getUserData();
                }
            }
        } else if (from.equals("Card type")) {
            for (MenuItem menuItem : cardType.getItems()) {
                if (menuItem.getText().equals(name)) {
                    return menuItem.getUserData();
                }
            }
        } else if (from.equals("Monster type")) {
            for (MenuItem menuItem : monsterType.getItems()) {
                if (menuItem.getText().equals(name)) {
                    return menuItem.getUserData();
                }
            }
        }
        return null;
    }
}

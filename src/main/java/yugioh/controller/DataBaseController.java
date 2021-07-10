package yugioh.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import yugioh.controller.menucontroller.MenuController;
import yugioh.model.User;
import yugioh.model.cards.Card;
import yugioh.model.cards.Monster;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.monsters.*;
import yugioh.model.cards.trapandspells.*;
import yugioh.view.SoundPlayable;
import yugioh.view.menus.PopUpWindow;
import yugioh.view.menus.WelcomeMenu;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static javafx.scene.paint.Color.GREEN;

public class DataBaseController extends MenuController {

    public static DataBaseController dataBaseController;
    public static ImageView selectedImage;
    public static ImageView previousImage;
    public static Stage exportStage;
    public ScrollPane cardInfoBox;
    public Text cardInfo;
    public ScrollPane exportCardsPane;
    public ImageView importedCard;
    public Button okButton;
    public RadioButton jsonCheck;
    public RadioButton csvCheck;


    public DataBaseController() {
    }

    public static DataBaseController getInstance() {
        if (dataBaseController == null) dataBaseController = new DataBaseController();
        return dataBaseController;
    }

    public static void writeJSON(Object object, String fileAddress) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        writeFile(fileAddress, gson.toJson(object));
    }

    public static void saveUserInfo(User user) throws IOException {
        writeJSON(user, "src\\resources\\users\\" + user.getUsername() + ".json");
    }

    public static void saveCardInfo(Card card) throws IOException {
   //     writeJSON(card,"src\\resources\\cards\\" + card.getName() + ".json");
        if (card.getCardKind() == Card.Kind.MAGIC) {
            SpellAndTrap spellAndTrap = (SpellAndTrap) card;
            writeJSON(spellAndTrap, "src\\resources\\cards\\SpellAndTraps\\" + card.getName() + ".json");
        } else {
            Monster monster = (Monster) card;
            if(!card.isCustom)
            writeJSON(monster, "src\\resources\\cards\\Monsters\\" + card.getName() + ".json");
            else{
                writeJSON(monster, "src\\resources\\cards\\Monsters\\Customs\\" + card.getName() + ".json");
            }
        }
    }

    public static void writeFile(String fileAddress, String content) throws IOException {
        FileWriter writer = new FileWriter(fileAddress);
        writer.write(content);
        writer.close();
    }

    public static void usersDataBaseInitialization() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        File directoryPath = new File("src\\resources\\users");
        File[] filesList = directoryPath.listFiles();
        ArrayList<User> dataBaseUsers = new ArrayList<>();
        for (File file : filesList) {
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(file.getPath())
            );
            User user = gson.fromJson(bufferedReader, User.class);
            dataBaseUsers.add(user);
        }
        User.setAllUsers(dataBaseUsers);
    }

    public static void cardsDataBaseInitialization()throws FileNotFoundException {
        initializeMonsterCards();
        initializeMagicCards();
    }

    private static void initializeMonsterCards() throws FileNotFoundException{
        File cardsFolder=new File("src\\resources\\cards\\Monsters\\Customs");
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        for(File file:cardsFolder.listFiles()){
            if(file.getPath().endsWith(".json")){
                BufferedReader bufferedReader = new BufferedReader(
                        new FileReader(file.getPath())
                );
                Monster monster=gson.fromJson(bufferedReader,Monster.class);
                Card.allCards.add(monster);
            }
        }
        Card BattleOX = new BattleOX();
        Card AxeRaider = new AxeRaider();
        Card YomiShip = new YomiShip();
        Card HornImp = new HornImp();
        Card SilverFang = new SilverFang();
        Card Suijin = new Suijin();
        Card Fireyarou = new Fireyarou();
        Card Curtainofthedarkones = new Curtainofthedarkones();
        Card FeralImp = new FeralImp();
        Card Darkmagician = new Darkmagician();
        Card Wattkid = new Wattkid();
        Card Babydragon = new Babydragon();
        Card Herooftheeast = new Herooftheeast();
        Card Battlewarrior = new Battlewarrior();
        Card Crawlingdragon = new Crawlingdragon();
        Card Flamemanipulator = new Flamemanipulator();
        Card BlueEyeswhitedragon = new BlueEyeswhitedragon();
        Card CrabTurtle = new CrabTurtle();
        Card SkullGuardian = new SkullGuardian();
        Card SlotMachine = new SlotMachine();
        Card Haniwa = new Haniwa();
        Card ManEaterBug = new ManEaterBug();
        Card GateGuardian = new GateGuardian();
        Card Scanner = new yugioh.model.cards.monsters.Scanner();
        Card Bitron = new Bitron();
        Card Marshmallon = new Marshmallon();
        Card BeastKingBarbaros = new BeastKingBarbaros();
        Card Texchanger = new Texchanger();
        Card Leotron = new Leotron();
        Card TheCalculator = new TheCalculator();
        Card AlexandriteDragon = new AlexandriteDragon();
        Card MirageDragon = new MirageDragon();
        Card HeraldofCreation = new HeraldofCreation();
        Card ExploderDragon = new ExploderDragon();
        Card WarriorDaiGrepher = new WarriorDaiGrepher();
        Card DarkBlade = new DarkBlade();
        Card Wattaildragon = new Wattaildragon();
        Card TerratigertheEmpoweredWarrior = new TerratigertheEmpoweredWarrior();
        Card TheTricky = new TheTricky();
        Card SpiralSerpent = new SpiralSerpent();
        Card CommandKnight = new CommandKnight();
    }

    private static void initializeMagicCards()throws FileNotFoundException {
//        File cardsFolder=new File("src\\resources\\cards\\SpellAndTraps");
//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();
//        for(File file:cardsFolder.listFiles()){
//            if(file.getPath().endsWith(".json")){
//                BufferedReader bufferedReader = new BufferedReader(
//                        new FileReader(file.getPath())
//                );
//                SpellAndTrap spellAndTrap=gson.fromJson(bufferedReader,SpellAndTrap.class);
//                Card.allCards.add(spellAndTrap);
//            }
//        }
      Card TrapHole = new TrapHole();
      Card MirrorForce = new MirrorForce();
      Card MagicCylinder = new MagicCylinder();
      Card MindCrush = new MindCrush();
      Card TorrentialTribute = new TorrentialTribute();
      Card TimeSeal = new TimeSeal();
      Card NegateAttack = new NegateAttack();
      Card SolemnWarning = new SolemnWarning();
      Card MagicJamamer = new MagicJamamer();
      Card CallofTheHaunted = new CallofTheHaunted();
      Card VanitysEmptiness = new VanitysEmptiness();
      Card WallofRevealingLight = new WallofRevealingLight();
      Card MonsterReborn = new MonsterReborn();
      Card Terraforming = new Terraforming();
      Card PotofGreed = new PotofGreed();
      Card Raigeki = new Raigeki();
      Card ChangeofHeart = new ChangeofHeart();
      Card SwordsofRevealingLight = new SwordsofRevealingLight();
      Card HarpiesFeatherDuster = new HarpiesFeatherDuster();
      Card DarkHole = new DarkHole();
      Card SupplySquad = new SupplySquad();
      Card SpellAbsorption = new SpellAbsorption();
      Card Messengerofpeace = new Messengerofpeace();
      Card TwinTwisters = new TwinTwisters();
      Card Mysticalspacetyphoon = new Mysticalspacetyphoon();
      Card Ringofdefense = new Ringofdefense();
      Card Yami = new Yami();
      Card Forest = new Forest();
      Card ClosedForest = new ClosedForest();
      Card Umiiruka = new Umiiruka();
      Card Swordofdarkdestruction = new Swordofdarkdestruction();
      Card BlackPendant = new BlackPendant();
      Card UnitedWeStand = new UnitedWeStand();
      Card MagnumShield = new MagnumShield();
      Card AdvancedRitualArt = new AdvancedRitualArt();
    }

    public String readFileContent(String address) {
        StringBuilder output = new StringBuilder();
        try {
            File file = new File(address);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                output.append(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            return null;
        }
        return output.toString();
    }

    public void backClicked(MouseEvent mouseEvent) throws Exception {
        SoundPlayable.playButtonSound("backButton");
        mainMenu.execute();
    }

    public void importCard() {
        SoundPlayable.playButtonSound("enterButton");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select a card");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("text files", "*.json", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File("src\\resources\\cards"));
        File file = fileChooser.showOpenDialog(WelcomeMenu.stage);
        try {
            GsonBuilder monsterBuilder = new GsonBuilder();
            Gson monsterGson = monsterBuilder.create();
            GsonBuilder magicBuilder = new GsonBuilder();
            Gson magicGson = magicBuilder.create();
            if (file != null) {
                BufferedReader monsterBufferedReader = new BufferedReader(new FileReader(file));
                BufferedReader magicBufferedReader = new BufferedReader(new FileReader(file));
                Monster monster = monsterGson.fromJson(monsterBufferedReader, Monster.class);
                SpellAndTrap spellAndTrap = magicGson.fromJson(magicBufferedReader, SpellAndTrap.class);
                String info = "";
                info += "Name: " + monster.getName();
                if (monster.getCardKind() == Card.Kind.MAGIC) {
                    Card.addCardToAllCards(spellAndTrap);
                    info += "\nType: Magic";
                    info += "\nAttribute: " + spellAndTrap.getAttribute().toString();
                    info += "\nDescription: " + spellAndTrap.getDescription();
                    info += "\nStatus: " + spellAndTrap.getStatus().toString();

                } else {
                    Card.addCardToAllCards(monster);
                    info += ("\nType: Monster");
                    info += ("\nLevel: " + monster.getLevel());
                    info += ("\nAttribute: " + monster.getAttribute().toString());
                    info += ("\nMonster Type: " + monster.getMonsterType().toString());
                    info += ("\nCard Type: " + monster.getCardType().toString());
                    info += ("\nAttack: " + monster.getAtk());
                    info += ("\nDefense: " + monster.getDef());
                    info += ("\nDescription: " + monster.getDescription());
                }
                info += ("\nPrice: " + monster.getPrice());
                importedCard.setImage(new Image(new File(monster.getImage()).toURI().toString()));
                cardInfo.wrappingWidthProperty().bind(cardInfoBox.widthProperty().add(-15));
                cardInfo.setText(info);
                cardInfoBox.setFitToWidth(true);
            }
        } catch (Exception e) {
            cardInfo.setText("can not read data from this file");
        }
    }

    public void openExportPage() throws Exception {
        URL fxmlAddress = getClass().getResource("/yugioh/fxml/ExportMenu.fxml");
        Parent pane = FXMLLoader.load(fxmlAddress);
        this.exportCardsPane = (ScrollPane) pane.getChildrenUnmodifiable().get(0);
        this.okButton = (Button) pane.getChildrenUnmodifiable().get(2);
        exportStage = new Stage();
        exportStage.initModality(Modality.APPLICATION_MODAL);
        ArrayList<Card> allCards = new ArrayList<>(Card.getCards());
        int cardsPerRow = 6;
        int columnCounter = 0;
        GridPane cardsPane = new GridPane();
        cardsPane.setHgap(10);
        cardsPane.setVgap(10);
        okButton.setDisable(true);
        outer:
        while (allCards.size() > 0) {
            for (int j = 0; j < cardsPerRow; j++) {
                Card card = allCards.get(allCards.size() - 1);
                ImageView cardImage = Card.getCardImage(card, 86);
                DropShadow selectEffect = new DropShadow(BlurType.values()[1],
                        GREEN, 10, 2.0f, 0, 0);
                selectEffect.setBlurType(BlurType.ONE_PASS_BOX);
                cardImage.focusedProperty().addListener((ObservableValue<? extends Boolean> observable,
                                                         Boolean oldValue, Boolean newValue) -> {
                    if (newValue) {
                        selectedImage = cardImage;
                        cardImage.setEffect(selectEffect);
                        okButton.setDisable(false);
                    } else {
                        cardImage.setEffect(null);
                        if (selectedImage != null) {
                            previousImage = new ImageView(selectedImage.getImage());

                        } else {
                            okButton.setDisable(true);
                            previousImage = null;
                        }

                        selectedImage = null;
                    }
                });
                cardImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    cardImage.requestFocus();
                    event.consume();
                });
                cardsPane.add(cardImage, j, columnCounter);
                allCards.remove(card);
                if (allCards.size() == 0) break outer;
            }
            columnCounter++;
        }
        exportCardsPane.contentProperty().set(cardsPane);
        Scene scene = WelcomeMenu.createScene(pane);
        exportStage.setScene(scene);
        scene.getStylesheets().add(
                getClass().getResource("/yugioh/CSS/Menu.css").toExternalForm());
        exportStage.show();
    }

    public void exportClicked() throws Exception {
        SoundPlayable.playButtonSound("enterButton");
        openExportPage();
    }

    public void exportCard() throws Exception {
        SoundPlayable.playButtonSound("enterButton");
        if (selectedImage == null && previousImage == null) {
            openExportPage();
            new PopUpWindow("Error: no cards selected").start(WelcomeMenu.stage);
            return;
        }
        String imageAddress = previousImage.getImage().getUrl().substring(previousImage.getImage().getUrl().lastIndexOf("src"));
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("select a directory");
        File file = directoryChooser.showDialog(WelcomeMenu.stage);
        Card card = Card.getCardNameByImage(imageAddress);
        if (file != null) {
            String format = ".json";
            if (csvCheck.isSelected()) format = ".csv";
            writeJSON(card, file.getPath() + "/" + card.getName() + format);
            exportStage.close();
            new PopUpWindow("Card exported successfully!").start(WelcomeMenu.stage);
        }
        exportStage.close();
        openExportPage();
    }

    public void backToImportClicked() throws Exception {
        SoundPlayable.playButtonSound("backButton");
        exportStage.close();
        importExportMenu.execute();
    }

    public void switchToJson(ActionEvent event) {
        jsonCheck.setSelected(true);
        csvCheck.setSelected(false);
    }

    public void switchToCsv(ActionEvent event) {
        jsonCheck.setSelected(false);
        csvCheck.setSelected(true);
    }
}

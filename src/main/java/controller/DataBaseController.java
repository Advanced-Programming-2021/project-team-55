package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.menucontroller.MenuController;
import model.User;
import model.cards.Card;
import model.cards.monsters.*;
import model.cards.trapandspells.*;
import model.exceptions.MenuException;
import view.Menus.Menu;
import view.Menus.MenuType;
import view.Responses;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DataBaseController extends MenuController {

    private static DataBaseController dataBaseController;

    private DataBaseController() {

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

    public static void cardsDataBaseInitialization() {
        initializeMonsterCards();
        initializeMagicCards();
    }

    private static void initializeMonsterCards() {
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
        Card Scanner = new model.cards.monsters.Scanner();
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

    private static void initializeMagicCards() {
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

    @Override
    public void enterMenu(String menu) throws MenuException {
        throw new MenuException(Responses.MENU_NAVIGATION_NOT_POSSIBLE.response);
    }

    @Override
    public void exitMenu() {
        Menu.setCurrentMenu(MenuType.MAIN);
    }

    public String importCard(String cardName) {
        try {
            String cardJson = readFileContent("src\\resources\\cards\\" + cardName + ".json");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Card card = gson.fromJson(cardJson, Card.class);
            Card.getAllCards().add(card);
            return card.getName() + " successfully imported";
        } catch (Exception e) {
            return "there is no card exported at \"cards\" directory with this name";
        }
    }

    public String exportCard(String cardName) {
        if (cardName == null || cardName.equals("")) {
            return "Error: no cards selected";
        }
        Card card = Card.getNewCardObjectByName(cardName);
        try {
            writeJSON(card, "src\\resources\\cards\\" + card.getName() + ".json");
        } catch (IOException ignored) {
        }
        return "card successfully exported at \"cards\" directory";
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

}

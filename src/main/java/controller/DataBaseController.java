package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import controller.menucontroller.MenuController;
import exceptions.MenuException;
import model.User;
import model.cards.Card;
import model.cards.MonsterCardDetails;
import model.cards.TrapAndSpellCardDetails;
import model.cards.monsters.*;
import model.cards.trapandspells.*;
import view.Menus.Menu;
import view.Menus.MenuType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
       /* BufferedReader bufferedReader = new BufferedReader(
                new FileReader("resources\\users\\"+username+".json"));
        User user= gson.fromJson(bufferedReader, User.class);*/
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


    public List<MonsterCardDetails> importMonstersDetails() throws FileNotFoundException {//todo save the list in model
        List<MonsterCardDetails> monsterCardsDetailsList = new CsvToBeanBuilder(
                new FileReader("src/resources/cards details/Monster.csv"))
                .withType(MonsterCardDetails.class).build().parse();

        return monsterCardsDetailsList;
    }

    public List<TrapAndSpellCardDetails> importTrapAndSpellsDetails() throws FileNotFoundException {//todo save the list in model
        List<TrapAndSpellCardDetails> trapAndSpellCardDetailsList = new CsvToBeanBuilder(
                new FileReader("src/resources/cards details/SpellTrap.csv"))
                .withType(TrapAndSpellCardDetails.class).build().parse();

        return trapAndSpellCardDetailsList;
    }

    @Override
    public void enterMenu(String menu) throws MenuException {
        throw new MenuException("menu navigation is not possible");
    }

    @Override
    public void exitMenu() {
        Menu.setCurrentMenu(MenuType.MAIN);
    }

    public void importDeck(String cardName) {

    }

    public static void initializeCards(){
        initializeMonsterCards();
        initializeMagicCards();
    }

    private static void  initializeMonsterCards() {
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


    public String exportCard(String cardName) {
        return null;
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
            System.out.println("read file error");
            e.printStackTrace();
        }
        return output.toString();
    }

//    public static void main(String[] args) throws IOException {
//        List<MonsterCardDetails> monsters = DataBaseController.getInstance().importMonstersDetails();
//        for (MonsterCardDetails monsterCardsDetails: monsters){
//            String className = monsterCardsDetails.getName().trim()
//                    .replaceAll(" ","").replaceAll("-", "")
//                    .replaceAll(",","").replaceAll("'", "");
//            String fileContent = "package model.cards.monsters;\n" +
//                    "\n" +
//                    "import model.cards.cardfeaturesenums.MonsterAttribute;\n" +
//                    "import model.cards.cardfeaturesenum.CardType;\n\n" +
//                    "\n" +
//                    "public class " + className + " {\n" +
//                    "\n" +
//                    "    private String name = \"" + monsterCardsDetails.getName() + "\";\n" +
//                    "    private int level = " + monsterCardsDetails.getLevel() + ";\n" +
//                    "    private MonsterAttribute attribute = MonsterAttribute." + monsterCardsDetails.getAttribute().toUpperCase() + ";\n" +
//                    "    private String monsterType = \"" + monsterCardsDetails.getMonsterType() + "\";\n" +
//                    "    private CardType cardType = CardType." + monsterCardsDetails.getCardType().toUpperCase() + ";\n" +
//                    "    private int atk = " + monsterCardsDetails.getAtk() + ";\n" +
//                    "    private int def = " + monsterCardsDetails.getDef() + ";\n" +
//                    "    private static final String description = \"" + monsterCardsDetails.getDescription() + "\";\n" +
//                    "    private int price = " + monsterCardsDetails.getPrice() + ";\n" +
//                    "\n" +
//                    "\n" +
//                    "}\n";
//            writeFile("src\\main\\java\\model\\cards\\monsters\\" + className + ".java", fileContent);
//
//        }
//    }

//    public static void main(String[] args) throws IOException {
//        List<TrapAndSpellCardDetails> trapAndSpellCardDetailsList = DataBaseController.getInstance().importTrapAndSpellsDetails();
//        for (TrapAndSpellCardDetails trapAndSpellCardDetails: trapAndSpellCardDetailsList){
//            String className = trapAndSpellCardDetails.getName().trim()
//                    .replaceAll(" ","").replaceAll("-", "")
//                    .replaceAll(",","").replaceAll("'", "");
//            String fileContent = "package model.cards.trapandspells;\n" +
//                    "\n" +
//                    "import model.cards.cardfeaturesenums.SpellOrTrap;\n" +
//                    "import model.cards.cardfeaturesenums.SpellOrTrapAttribute;\n" +
//                    "import model.cards.cardfeaturesenums.Status;\n" +
//                    "\n" +
//                    "public class " + className +" {\n" +
//                    "\n" +
//                    "    private boolean isActive = false;\n" +
//                    "\n" +
//                    "    private String name = \"" + trapAndSpellCardDetails.getName() + "\";\n" +
//                    "    private SpellOrTrap type = SpellOrTrap." + trapAndSpellCardDetails.getType().toUpperCase() + ";\n" +
//                    "    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute." + trapAndSpellCardDetails.getIconOrProperty().toUpperCase() + ";\n" +
//                    "    private static final String description = \"" + trapAndSpellCardDetails.getDescription() + "\";\n" +
//                    "    private Status status = Status." + trapAndSpellCardDetails.getStatus().toUpperCase() + ";\n" +
//                    "    private int price = " + trapAndSpellCardDetails.getPrice() + ";\n" +
//                    "    \n" +
//                    "}";
//            writeFile("src\\main\\java\\model\\cards\\trapandspells\\" + className + ".java", fileContent);
//
//        }
//    }

//    public static void main(String[] args) throws FileNotFoundException {
//        List<MonsterCardDetails> monsters = DataBaseController.getInstance().importMonstersDetails();
//        for (MonsterCardDetails monsterCardsDetails: monsters) {
//            System.out.println(monsterCardsDetails.getMonsterType().toUpperCase() + ",");
//        }
//    }


//    public static void main(String[] args) throws IOException {
//        List<MonsterCardDetails> monsters = DataBaseController.getInstance().importMonstersDetails();
//        for (MonsterCardDetails monsterCardsDetails: monsters){
//            String className = monsterCardsDetails.getName().trim()
//                    .replaceAll(" ","").replaceAll("-", "")
//                    .replaceAll(",","").replaceAll("'", "");
//            String fileContent = "package model.cards.monsters;\n" +
//                    "\n" +
//                    "import model.cards.Monster;\n" +
//                    "import model.cards.cardfeaturesenums.CardType;\n" +
//                    "import model.cards.cardfeaturesenums.MonsterAttribute;\n" +
//                    "import model.cards.cardfeaturesenums.MonsterType;\n" +
//                    "\n" +
//                    "public class " + className + " extends Monster {\n" +
//                    "\n" +
//                    "    public " + className + "() {\n" +
//                    "        super(\"" + monsterCardsDetails.getName() + "\", \"" + monsterCardsDetails.getDescription() + "\"\n" +
//                    "                , " + monsterCardsDetails.getPrice() + ", " + monsterCardsDetails.getAtk() + ", " + monsterCardsDetails.getDef() + ", " + monsterCardsDetails.getLevel() + ", MonsterAttribute." + monsterCardsDetails.getAttribute().toUpperCase() + ", MonsterType." + monsterCardsDetails.getMonsterType().toUpperCase() + ", CardType." + monsterCardsDetails.getCardType().toUpperCase() + ");\n" +
//                    "    }\n" +
//                    "\n" +
//                    "}\n";
//            writeFile("src\\main\\java\\model\\cards\\monsters\\" + className + ".java", fileContent);
//
//        }
//    }
//    public static void main(String[] args) throws IOException {
//        List<TrapAndSpellCardDetails> trapAndSpellCardDetailsList = DataBaseController.getInstance().importTrapAndSpellsDetails();
//        for (TrapAndSpellCardDetails trapAndSpellCardDetails : trapAndSpellCardDetailsList) {
//            String className = trapAndSpellCardDetails.getName().trim()
//                    .replaceAll(" ", "").replaceAll("-", "")
//                    .replaceAll(",", "").replaceAll("'", "");
//            String fileContent = "package model.cards.trapandspells;\n" +
//                    "\n" +
//                    "import model.cards.SpellAndTrap;\n" +
//                    "import model.cards.cardfeaturesenums.SpellOrTrap;\n" +
//                    "import model.cards.cardfeaturesenums.SpellOrTrapAttribute;\n" +
//                    "import model.cards.cardfeaturesenums.Status;\n" +
//                    "\n" +
//                    "public class " + className + " extends SpellAndTrap {\n" +
//                    "    \n" +
//                    "    public " + className + "() {\n" +
//                    "        super(\"" + trapAndSpellCardDetails.getName() + "\", \"" + trapAndSpellCardDetails.getDescription() + "\",\n" +
//                    "                " + trapAndSpellCardDetails.getPrice() + ", false, SpellOrTrap." + trapAndSpellCardDetails.getType().toUpperCase() +
//                    ", SpellOrTrapAttribute." + trapAndSpellCardDetails.getIconOrProperty().toUpperCase() + ", Status." + trapAndSpellCardDetails.getStatus().toUpperCase() +
//                    ");\n" +
//                    "    }\n" +
//                    "    \n" +
//                    "}";
//            writeFile("src\\main\\java\\model\\cards\\trapandspells\\" + className + ".java", fileContent);
//
//        }
//    }

}

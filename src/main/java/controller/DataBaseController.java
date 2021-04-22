package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import controller.menucontroller.MenuController;
import exceptions.MenuException;
import model.cards.MonsterCardDetails;
import model.cards.TrapAndSpellCardDetails;
import model.User;

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

    //todo parham chera inaro static kardi? mage getInstance nazashtim??
    //todo: bebin serfan goftam chon ma ino tu Menu seda mikonim behtare static bashe serfan ye tabe ejra
    //      she dige khod object ro nemikhaim ama mishe ba getInstance karo jelo bord farqhi nemikone

    public static void usersDataBaseInitialization() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
       /* BufferedReader bufferedReader = new BufferedReader(
                new FileReader("resources\\users\\"+username+".json"));
        User user= gson.fromJson(bufferedReader, User.class);*/
        File directoryPath = new File("src\\resources\\users");
        File filesList[] = directoryPath.listFiles();
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

    @Override
    public void enterMenu(String menu) throws MenuException {

    }

    @Override
    public void exitMenu() {

    }

    public void importCard(String cardName) {
        ;
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

    public static void main(String[] args) throws IOException {
        List<MonsterCardDetails> monsters = DataBaseController.getInstance().importMonstersDetails();
        for (MonsterCardDetails monsterCardsDetails: monsters){
            String className = monsterCardsDetails.getName().trim()
                    .replaceAll(" ","").replaceAll("-", "")
                    .replaceAll(",","").replaceAll("'", "");
            String fileContent = "package model.cards.monsters;\n" +
                    "\n" +
                    "import model.cards.MonsterAttribute;\n" +
                    "import model.cards.KindOfMonster;\n" +
                    "\n" +
                    "import static model.cards.MonsterAttribute.*;\n" +
                    "import static model.cards.KindOfMonster.*;\n" +
                    "\n" +
                    "public class " + className + " {\n" +
                    "\n" +
                    "    private String name = \"" + monsterCardsDetails.getName() + "\";\n" +
                    "    private int level = " + monsterCardsDetails.getLevel() + ";\n" +
                    "    private MonsterAttribute attribute = " + monsterCardsDetails.getAttribute() + ";\n" +
                    "    private String monsterType = \"" + monsterCardsDetails.getMonsterType() + "\";\n" +
                    "    private KindOfMonster kindOfMonster = " + monsterCardsDetails.getCardType() + ";\n" +
                    "    private int atk = " + monsterCardsDetails.getAtk() + ";\n" +
                    "    private int def = " + monsterCardsDetails.getDef() + ";\n" +
                    "    private static final String description = \"" + monsterCardsDetails.getDescription() + "\";\n" +
                    "    private int price = " + monsterCardsDetails.getPrice() + ";\n" +
                    "\n" +
                    "\n" +
                    "}\n";
            writeFile("src\\main\\java\\model\\cards\\monsters\\" + className + ".java", fileContent);

        }
    }
}

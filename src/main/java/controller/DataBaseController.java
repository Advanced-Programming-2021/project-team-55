package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import controller.menucontroller.MenuController;
import exceptions.MenuException;
import model.MonsterCardDetails;
import model.TrapAndSpellCardDetails;
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

    @Override
    public void enterMenu(String menu) throws MenuException {

    }

    @Override
    public void exitMenu() {

    }
    //TODO there should be a method which imports users and puts them in users array in model
    public List<MonsterCardDetails> importMonsterDetails() throws FileNotFoundException {//todo save the list in model
        List<MonsterCardDetails> monsterCardsDetailsList = new CsvToBeanBuilder(
                new FileReader("src/resources/cards details/Monster.csv"))
                .withType(MonsterCardDetails.class).build().parse();

        if (MonsterCardDetails.getMonsterCardsDetailsList() == null)
            MonsterCardDetails.setMonsterCardsDetailsList(monsterCardsDetailsList);

        return monsterCardsDetailsList;
    }

    public List<TrapAndSpellCardDetails> importTrapAndSpellDetails() throws FileNotFoundException {//todo save the list in model
        List<TrapAndSpellCardDetails> trapAndSpellCardDetailsList = new CsvToBeanBuilder(
                new FileReader("src/resources/cards details/SpellTrap.csv"))
                .withType(TrapAndSpellCardDetails.class).build().parse();

        if (TrapAndSpellCardDetails.getTrapAndSpellCardDetailsList() == null)
            TrapAndSpellCardDetails.setTrapAndSpellCardDetailsList(trapAndSpellCardDetailsList);

        return trapAndSpellCardDetailsList;
    }

    public String importCard(String cardName) {
        return null;
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
    public static void writeJSON(User user) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter("src\\resources\\users\\"+user.getUsername()+".json");
        writer.write(gson.toJson(user));
        writer.close();
    }

    public static void usersDataBaseInitialization() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
       /* BufferedReader bufferedReader = new BufferedReader(
                new FileReader("resources\\users\\"+username+".json"));
        User user= gson.fromJson(bufferedReader, User.class);*/
        File directoryPath =new File("src\\resources\\users");
        File filesList[] = directoryPath.listFiles();
        ArrayList<User>dataBaseUsers=new ArrayList<>();
        for(File file:filesList){
            BufferedReader bufferedReader=new BufferedReader(
                    new FileReader(file.getPath())
            );
            User user=gson.fromJson(bufferedReader,User.class);
            dataBaseUsers.add(user);
        }
        User.setAllUsers(dataBaseUsers);
    }

}

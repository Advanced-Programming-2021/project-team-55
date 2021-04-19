package controller;

import com.opencsv.bean.CsvToBeanBuilder;
import controller.menucontroller.MenuController;
import exceptions.MenuException;
import model.MonsterCardDetails;
import model.TrapAndSpellCardDetails;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Locale;

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

    public static void main(String[] args) throws FileNotFoundException {
        List<TrapAndSpellCardDetails> s = (new DataBaseController()).importTrapAndSpellDetails();
        int counter = 0;
        for (TrapAndSpellCardDetails trapAndSpellCardDetails: s){
//            counter++;
//            System.out.println(counter + " :" +trapAndSpellCardDetails.getName());
            String x = trapAndSpellCardDetails.getName().trim().toUpperCase(Locale.ROOT).replace(' ', '_');
            System.out.println(x + ',');
        }
    }
}

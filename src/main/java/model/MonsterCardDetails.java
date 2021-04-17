package model;

import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class MonsterCardDetails {//temporary class for csv files import understanding

    private static List<MonsterCardDetails> monsterCardsDetailsList;

    @CsvBindByName
    private String Name;

    @CsvBindByName
    private String level;

    @CsvBindByName
    private String attribute;

    @CsvBindByName
    private String monsterType;

    @CsvBindByName
    private String cardType;

    @CsvBindByName
    private String Atk;

    @CsvBindByName
    private String Def;

    @CsvBindByName
    private String description;

    @CsvBindByName
    private String price;

    public String getName() {
        return Name;
    }

    public String getLevel() {
        return level;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public String getCardType() {
        return cardType;
    }

    public String getAtk() {
        return Atk;
    }

    public String getDef() {
        return Def;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public static List<MonsterCardDetails> getMonsterCardsDetailsList() {
        return monsterCardsDetailsList;
    }

    public static void setMonsterCardsDetailsList(List<MonsterCardDetails> monsterCardsDetailsList) {
        MonsterCardDetails.monsterCardsDetailsList = monsterCardsDetailsList;
    }

}

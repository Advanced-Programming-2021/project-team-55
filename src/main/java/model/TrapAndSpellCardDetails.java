package model;

import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class TrapAndSpellCardDetails {

    private static List<TrapAndSpellCardDetails> trapAndSpellCardDetailsList;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String type;
    @CsvBindByName
    private String iconOrProperty;
    @CsvBindByName
    private String description;
    @CsvBindByName
    private String status;
    @CsvBindByName
    private String price;

    public static List<TrapAndSpellCardDetails> getTrapAndSpellCardDetailsList() {
        return trapAndSpellCardDetailsList;
    }

    public static void setTrapAndSpellCardDetailsList(List<TrapAndSpellCardDetails> trapAndSpellCardDetailsList) {
        TrapAndSpellCardDetails.trapAndSpellCardDetailsList = trapAndSpellCardDetailsList;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getIconOrProperty() {
        return iconOrProperty;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getPrice() {
        return price;
    }
}

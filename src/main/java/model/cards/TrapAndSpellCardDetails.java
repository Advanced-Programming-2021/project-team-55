package model.cards;

import com.opencsv.bean.CsvBindByName;

public class TrapAndSpellCardDetails {

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

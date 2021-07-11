package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.cards.Card;
import model.cards.CardsInventory;
import model.cards.Monster;
import model.cards.SpellAndTrap;

import java.io.*;
import java.util.ArrayList;

public class DatabaseController {

    public static void writeJSON(Object object, String fileAddress) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        writeFile(fileAddress, gson.toJson(object));
    } public static void writeFile(String fileAddress, String content) throws IOException {
        FileWriter writer = new FileWriter(fileAddress);
        writer.write(content);
        writer.close();
    } public static void cardsDataBaseInitialization() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        File file = new File("src\\main\\resources\\Database\\cards\\inventory.json");
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(file.getPath())
            );
            CardsInventory inventory = gson.fromJson(bufferedReader,CardsInventory.class);
            CardsInventory.setInventory(inventory);
        initializeMonsterCards();
        initializeMagicCards();
    }

    private static void initializeMonsterCards() throws FileNotFoundException {
        File cardsFolder = new File("src\\main\\resources\\Database\\cards\\Monsters");
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        for (File file : cardsFolder.listFiles()) {
            if (file.getPath().endsWith(".json")) {
                BufferedReader bufferedReader = new BufferedReader(
                        new FileReader(file.getPath())
                );
                Monster monster = gson.fromJson(bufferedReader, Monster.class);
                Card.allCards.add(monster);
            }
        }

    }

    private static void initializeMagicCards() throws FileNotFoundException {
        File cardsFolder=new File("src\\main\\resources\\Database\\cards\\SpellAndTraps");
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        for(File file:cardsFolder.listFiles()){
            if(file.getPath().endsWith(".json")){
                BufferedReader bufferedReader = new BufferedReader(
                        new FileReader(file.getPath())
                );
                SpellAndTrap spellAndTrap=gson.fromJson(bufferedReader,SpellAndTrap.class);
                Card.allCards.add(spellAndTrap);
            }
        }

    }
    public static void saveCardInfo(Card card) throws IOException {
            if (card.getCardKind() == Card.Kind.MAGIC) {
                SpellAndTrap spellAndTrap = (SpellAndTrap) card;
                writeJSON(spellAndTrap, "src\\main\\resources\\Database\\cards\\SpellAndTraps\\" + card.getName() + ".json");
            } else {
                Monster monster = (Monster) card;
                if (!card.isCustom)
                    writeJSON(monster, "src\\main\\resources\\Database\\cards\\Monsters\\" + card.getName() + ".json");
                else {
                    writeJSON(monster, "src\\main\\resources\\Database\\cards\\Monsters\\Customs\\" + card.getName() + ".json");
                }
        }
    }
    public static void updateCardsInventory() {
        try {
            writeJSON(CardsInventory.inventory,"src\\main\\resources\\Database\\cards\\inventory.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

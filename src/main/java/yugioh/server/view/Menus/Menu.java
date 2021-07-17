package yugioh.server.view.Menus;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.embed.swing.JFXPanel;
import yugioh.server.controller.DataBaseController;
import yugioh.server.model.User;
import yugioh.server.model.UserHolder;
import yugioh.server.model.cards.Card;
import yugioh.server.model.cards.CardsInventory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;

import static yugioh.server.view.ViewInterface.getCommandMatcher;

abstract public class Menu {
    private static final LoginMenu loginMenu = new LoginMenu();
    private static final MainMenu mainMenu = new MainMenu();
    private static final ScoreBoardMenu scoreBoardMenu = new ScoreBoardMenu();
    private static final ProfileMenu profileMenu = new ProfileMenu();
    private static final ShopMenu shopMenu = new ShopMenu();
    private static final DeckMenu deckMenu = new DeckMenu();
    private static final DuelMenu duelMenu = new DuelMenu();
    public static MenuType currentMenu = MenuType.LOGIN;

    public static void run() {
        try {
            JFXPanel jfxPanel = new JFXPanel();
            jfxPanel.getScene();
            DataBaseController.usersDataBaseInitialization();
            DataBaseController.cardsDataBaseInitialization();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            new AdminWelcomeMenu().execute();
        }).start();
//        while (true) {
//            switch (currentMenu) {
//                case LOGIN: {
//                    loginMenu.execute();
//                    break;
//                }
//                case MAIN: {
//                    mainMenu.execute();
//                    break;
//                }
//                case PROFILE: {
//                    profileMenu.execute();
//                    break;
//                }
//                case SCOREBOARD: {
//                    scoreBoardMenu.execute();
//                    break;
//                }
//                case SHOP: {
//                    shopMenu.execute();
//                    break;
//                }
//                case DECK: {
//                    deckMenu.execute();
//                    break;
//                }
//                case DUEL: {
//                    duelMenu.execute();
//                    break;
//                }
//            }
//        }

    }

    public static String getCurrentMenu() {
        return currentMenu.menuName;
    }

    public static void setCurrentMenu(MenuType currentMenu) {
        Menu.currentMenu = currentMenu;
    }

    abstract protected void execute();

    abstract protected String processCommand(String command, UserHolder currentUser);

    public static String handleCommand(String command, UserHolder currentUser) {
        String result = loginMenu.processCommand(command, currentUser);
        if(command.startsWith("get count ")){
            result= getCountCard(command);
        }
       else  if(command.startsWith("reduce card ")){
            result=reduceCard(command);
        }
        else if(command.startsWith("save user ")){
                Matcher matcher = getCommandMatcher(command, "save user address: (.*) content: (.*)");
                String address = matcher.group(1);
                String content = matcher.group(2);
                try {
                    DataBaseController.writeFile(address, content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        else if(command.startsWith("add card ")){
           result=addCard(command);
        }
        else if(command.startsWith("is card forbid ")){
            Matcher matcher=getCommandMatcher(command,"is card forbid (.*)");
            String cardName=matcher.group(1);
            if(Card.getCardByName(cardName).isForbid()){
                return "true";
            }
            else{
                return "false";
            }
        } else if(command.startsWith("get online users")){
            StringBuilder users= new StringBuilder();
            for(UserHolder userHolder:User.getLoggedInUsers()){
                users.append("\"").append(userHolder.getUser().getUsername()).append("\" ");
            }
            result= users.toString().trim();
        } else if(command.equals("stop my thread")){
            result = "forward: stop receiving";
        }
        else{
            if (result.equals("Error: invalid command"))
                result = mainMenu.processCommand(command, currentUser);
            if (result.equals("Error: invalid command"))
                result = scoreBoardMenu.processCommand(command, currentUser);
            if (result.equals("Error: invalid command"))
                result = profileMenu.processCommand(command, currentUser);
            if (result.equals("Error: invalid command"))
                result = shopMenu.processCommand(command, currentUser);
            if (result.equals("Error: invalid command"))
                result = deckMenu.processCommand(command, currentUser);
            if (result.equals("Error: invalid command"))
                result = duelMenu.processCommand(command, currentUser);
            if (command.startsWith("chat ")) {
                result = command;
            }
        }
        return result;
    }

    private static synchronized String reduceCard(String command) {
        CardsInventory.inventory.removeCardFromInventory(Card.getCardByName(command.replace("reduce card ","")),1);
        return "card "+command.replace("reduce card ","")+" reduced";
    }

    private static synchronized String getCountCard(String command){
       return String.valueOf(CardsInventory.inventory.cardsInventory.get(command.replace("get count ","")));
    }
    private static synchronized String addCard(String command){
        Matcher matcher=getCommandMatcher(command,"add card address: (.*) content: (.*)");
        String address=matcher.group(1);
        String content=matcher.group(2);
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            DataBaseController.writeFile(address,content);
            File file=new File(address);
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(file.getPath())
            );
            Card card=gson.fromJson(bufferedReader,Card.class);
            if(Card.getCardByName(card.getName())!=null){
                return "false";
            }
            else {
                Card.addCardToAllCards(card);
                CardsInventory.inventory.addCardToInventory(card, 1);
                return "true";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";
    }

}

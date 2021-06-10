package yugioh.model;

import yugioh.model.cards.Card;
import yugioh.model.cards.Deck;
import yugioh.model.cards.monsters.*;
import yugioh.model.cards.trapandspells.*;

import java.util.*;

public class User {
    public static User loggedInUser;
    private static ArrayList<User> allUsers;

    static {
        allUsers = new ArrayList<>();
    }

    private final ArrayList<Deck> decks;
    private final ArrayList<Card> cardsInventory;
    private String username;
    private String password;
    private String nickname;
    private int score;
    private int money;
    private String profileImage;
    private Deck activeDeck;

    {
        decks = new ArrayList<>();
        cardsInventory = new ArrayList<>();
        money = 100000;
    }

    public User(String username, String nickname, String password) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        allUsers.add(this);
        setProfileImage();
        initializeCards();

    }

    public void setProfileImage() {
        Random random=new Random();
        int randomNumber=random.nextInt(7)+1;
        this.profileImage="/yugioh/PNG/UsersImage/"+randomNumber+".png";
    }

    public String getProfileImage() {
        return profileImage;
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static boolean usernameExists(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean nicknameExists(String nickname) {
        for (User user : allUsers) {
            if (user.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static LinkedHashMap<Integer, HashMap<Integer, String>> getScoreBoardUsers() {
        allUsers.sort((user1, user2) -> {
            Integer score1 = user1.score;
            Integer score2 = user2.score;
            if (score1.equals(score2)) {
                return user1.nickname.compareTo(user2.nickname);
            }
            return score2.compareTo(score1);
        });
        LinkedHashMap<Integer, HashMap<Integer, String>> scoreBoard = new LinkedHashMap<>();
        int rank = 1;
        int sameNumbers = 1;
        for (int i = 0; i < allUsers.size(); i++) {
            HashMap<Integer, String> userInfo = new HashMap<>();
            userInfo.put(rank, allUsers.get(i).toString());
            scoreBoard.put(i, userInfo);
            if (i + 1 < allUsers.size() && allUsers.get(i).score != allUsers.get(i + 1).score) {
                rank += sameNumbers;
                sameNumbers = 1;
            } else {
                sameNumbers++;
            }

        }
        return scoreBoard;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ArrayList<User> allUsers) {
        User.allUsers = allUsers;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getMoney() {
        return money;
    }

    public void changeMoney(int money) {
        this.money += money;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public Deck getDeckByName(String deckName) {
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName)) {
                return deck;
            }
        }
        return null;
    }

    public ArrayList<Card> getCardsInventory() {
        return cardsInventory;
    }

    public void addDeck(Deck deck) {
        decks.add(deck);
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    public int getNumberOfSpecificCard(String cardName){
        int counter = 0;
        for (Card card : cardsInventory){
            if (card.getName().equals(cardName)) counter++;
        }
        return counter;
    }

    public void setActiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }

    public boolean cardExistsInInventory(String cardName) {
        for (Card card : cardsInventory) {
            if (card.getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }

    public void addCardsToInventory(ArrayList<Card> cards) {
        cardsInventory.addAll(cards);
    }

    public void removeCardFromInventory(Card card) {
        cardsInventory.remove(card);
    }

    public void changeScore(int amount) {
        this.score += amount;
    }

    public void removeDeck(Deck deck) {
        decks.remove(deck);
        if (activeDeck == deck) {
            setActiveDeck(null);
        }
    }

    private void initializeCards() {
        ArrayList<Card> initCards = new ArrayList<>();
        //game init cards:
        initCards.add(new AxeRaider());
        initCards.add(new Babydragon());
        initCards.add(new CallofTheHaunted());
        initCards.add(new ChangeofHeart());
        initCards.add(new Flamemanipulator());
        initCards.add(new HarpiesFeatherDuster());
        initCards.add(new MagicCylinder());
        initCards.add(new MagicJamamer());
        initCards.add(new MonsterReborn());
        initCards.add(new Mysticalspacetyphoon());
        initCards.add(new SlotMachine());
        initCards.add(new TorrentialTribute());

        //other cards added by me (parham)

        initCards.add(new BattleOX());
        initCards.add(new BeastKingBarbaros());
        initCards.add(new CrabTurtle());
        initCards.add(new DarkBlade());
        initCards.add(new ExploderDragon());
        initCards.add(new Haniwa());
        initCards.add(new MirageDragon());
        initCards.add(new Suijin());
        initCards.add(new WarriorDaiGrepher());
        initCards.add(new YomiShip());
        initCards.add(new Wattaildragon());
        initCards.add(new Texchanger());
        initCards.add(new Forest());
        initCards.add(new MindCrush());
        initCards.add(new NegateAttack());
        initCards.add(new SpellAbsorption());
        initCards.add(new TwinTwisters());
        initCards.add(new Yami());
        initCards.add(new Terraforming());
        initCards.add(new SupplySquad());
        initCards.add(new TerratigertheEmpoweredWarrior());
        initCards.add(new WallofRevealingLight());
        initCards.add(new UnitedWeStand());
        initCards.add(new Ringofdefense());
        initCards.add(new Leotron());
        initCards.add(new HornImp());
        initCards.add(new Haniwa());
        initCards.add(new CommandKnight());
        initCards.add(new BlackPendant());
        addCardsToInventory(initCards);
        Deck deckInit = new Deck("default");
        deckInit.addCardsToMainDeck(initCards);
        deckInit.addCardToSideDeck(new Crawlingdragon());
        deckInit.addCardToSideDeck(new ClosedForest());
        deckInit.setActive(true);
        this.activeDeck = deckInit;
        this.addDeck(deckInit);
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "- " + nickname + ": " + score;
    }
}

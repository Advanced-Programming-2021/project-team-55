package model;

import model.cards.Card;
import model.cards.Deck;

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
    private Deck activeDeck;

    {
        decks = new ArrayList<>();
        cardsInventory = new ArrayList<>();
    }

    public User(String username, String nickname, String password) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        allUsers.add(this);
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static User getUserbyNickname(String nickname) {
        for (User user : allUsers) {
            if (user.getNickname().equals(nickname)) {
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
        Collections.sort(allUsers, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                Integer score1 = user1.score;
                Integer score2 = user2.score;
                if (score1.equals(score2)) {
                    return user1.nickname.compareTo(user2.nickname);
                }
                return score2.compareTo(score1);
            }
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

    public int getScore() {
        return score;
    }

    public int getMoney() {
        return money;
    }

    public void changeMoney(int money) {
        this.money = money;
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

    public void setActiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }

    public boolean cardExistsInInventory(Card card) {
        return cardsInventory.contains(card);
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

    @Override
    public String toString() {
        return "- " + nickname + ": " + score;
    }
}

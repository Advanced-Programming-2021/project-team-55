package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

public class User {
    public static User loggedInUser;
    private static final ArrayList<User> allUsers;

    static {
        allUsers = new ArrayList<>();
    }

    private String username;
    private String password;
    private String nickname;
    private int score;

    public User(String username, String nickname, String password) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        allUsers.add(this);
    }

    public static User getUserbyUsername(String username) {
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

    public static LinkedHashMap<Integer, String> getScoreBoardUsers() {
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
        LinkedHashMap<Integer, String> scoreBoard = new LinkedHashMap<>();
        int rank = 1;
        int sameNumbers = 1;
        for (int i = 0; i < allUsers.size(); i++) {
            scoreBoard.put(rank, allUsers.get(i).toString());
            if (i + 1 < allUsers.size() && allUsers.get(i).score != allUsers.get(i + 1).score) {
                rank += sameNumbers;
                sameNumbers = 1;
            } else {
                sameNumbers++;
            }

        }
        return scoreBoard;
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

    public void changeScore(int amount) {
        this.score += amount;
    }

    @Override
    public String toString() {
        return "- " + nickname + ": " + score;
    }
}

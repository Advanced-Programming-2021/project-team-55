package model;

import java.util.ArrayList;

public class User {
    private static ArrayList<User>allUsers;
    private String username;
    private String password;
    private String nickname;
    static {
        allUsers=new ArrayList<>();
    }
    public User(String username,String nickname,String password) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        allUsers.add(this);
    }
    public static User getUserbyUsername(String username){
        for(User user :allUsers){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
    public static boolean usernameExists(String username){
        for(User user:allUsers){
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
    public static boolean nicknameExists(String nickname){
        for(User user:allUsers){
            if(user.getNickname().equals(nickname)){
                return true;
            }
        }
        return false;
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

    public String getNickname() {
        return nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

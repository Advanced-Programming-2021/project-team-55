package controller;


import model.User;
import view.Menus.Menu;
import view.Menus.MenuType;

import java.util.regex.Matcher;

public class LoginMenuController {
    private static LoginMenuController loginMenuController;

    private LoginMenuController() {

    }
    /*public String checkCommand(String command){
        String response ="invalid";
        if(command.matches("^user login --password (.*) --username (.*)$")){
            return loginUser()
        }
        else{
            return response;
        }
    }*/
    public String loginUser(Matcher matcher){
        String username=matcher.group(2);
        String password=matcher.group(1);
        User user=User.getUserbyUsername(username);
        if(user==null||!user.getPassword().equals(password)){
            return Responses.LOGINFAILED.response;
        }
        else{
            Menu.currentMenu=MenuType.MAINMENU;
            return Responses.LOGINSUCCESSFULL.response;
        }
    }
    public String createUser(Matcher matcher){
        String username=matcher.group(3);
        String password=matcher.group(2);
        String nickname=matcher.group(1);
        if(User.usernameExists(username)){
            return "user with username "+username+" exists";
        }
        else if(User.nicknameExists(nickname)){
            return "user with nickname "+nickname+" already exists";
        }
        else{
            new User(username,nickname,password);
            return Responses.CREATESUCCESSFULLY.response;
        }
    }
    public static LoginMenuController getInstance(){
        if(loginMenuController==null){
            loginMenuController=new LoginMenuController();
        }
        return loginMenuController;
    }







}

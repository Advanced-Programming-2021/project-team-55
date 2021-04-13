package controller;


import view.Menus.Menu;
import view.Menus.MenuType;

public class LoginMenuController {
    private static LoginMenuController loginMenuController;

    private LoginMenuController() {

    }
    public String checkCommand(String command){
        String response ="invalid";
        if(command.equals("login")){
            Menu.currentMenu= MenuType.MAINMENU;
            return "successfull";
        }
        else{
            return response;
        }
    }
    public static LoginMenuController getInstance(){
        if(loginMenuController==null){
            loginMenuController=new LoginMenuController();
        }
        return loginMenuController;
    }








}

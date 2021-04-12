package controller;


public class LoginMenuController {
    private static LoginMenuController loginMenuController;

    private LoginMenuController() {

    }
    public String checkCommand(String command){
        return "Done";
    }
    public static LoginMenuController getInstance(){
        if(loginMenuController==null){
            loginMenuController=new LoginMenuController();
        }
        return loginMenuController;
    }








}

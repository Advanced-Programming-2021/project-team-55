package yugioh.controller.menucontroller;

import yugioh.model.exceptions.MenuException;
import yugioh.view.Menus.LoginMenu;
import yugioh.view.Menus.LoginMenu;
import yugioh.view.Menus.RegisterMenu;
import yugioh.view.Menus.WelcomeMenu;

abstract public class MenuController {
    protected WelcomeMenu welcomeMenu=new WelcomeMenu();
    protected LoginMenu loginMenu=new LoginMenu();
    protected RegisterMenu registerMenu=new RegisterMenu();


}

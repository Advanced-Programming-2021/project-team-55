package yugioh.controller.menucontroller;

import yugioh.Main;
import yugioh.model.exceptions.MenuException;
import yugioh.view.Menus.*;
import yugioh.view.Menus.LoginMenu;

abstract public class MenuController {
    protected WelcomeMenu welcomeMenu=new WelcomeMenu();
    protected LoginMenu loginMenu=new LoginMenu();
    protected RegisterMenu registerMenu=new RegisterMenu();
    protected MainMenu mainMenu=new MainMenu();
    protected DuelMenu duelMenu=new DuelMenu();
    protected DeckMenu deckMenu=new DeckMenu();
    protected ScoreBoardMenu scoreBoardMenu=new ScoreBoardMenu();
    protected ProfileMenu profileMenu=new ProfileMenu();
    protected ShopMenu shopMenu=new ShopMenu();
    protected ImportExportMenu importExportMenu=new ImportExportMenu();


}

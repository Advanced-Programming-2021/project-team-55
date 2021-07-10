package yugioh.controller.menucontroller;

import yugioh.view.SoundPlayable;
import yugioh.view.menus.*;

abstract public class MenuController implements SoundPlayable {
    protected WelcomeMenu welcomeMenu = new WelcomeMenu();
    protected LoginMenu loginMenu = new LoginMenu();
    protected RegisterMenu registerMenu = new RegisterMenu();
    protected MainMenu mainMenu = new MainMenu();
    protected DuelMenu duelMenu = new DuelMenu();
    protected DeckMenu deckMenu = new DeckMenu();
    protected ScoreBoardMenu scoreBoardMenu = new ScoreBoardMenu();
    protected ProfileMenu profileMenu = new ProfileMenu();
    protected ShopMenu shopMenu = new ShopMenu();
    protected ImportExportMenu importExportMenu = new ImportExportMenu();
    protected SelectDeckNamePage selectDeckNamePage = new SelectDeckNamePage();
    protected CardGeneratorMenu cardGeneratorMenu = new CardGeneratorMenu();

}

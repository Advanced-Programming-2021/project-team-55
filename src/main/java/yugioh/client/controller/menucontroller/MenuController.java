package yugioh.client.controller.menucontroller;

import yugioh.client.view.NetAdapter;
import yugioh.client.view.SoundPlayable;
import yugioh.client.view.menus.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;

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
    protected ChatRoom chatRoom=new ChatRoom();
    protected DataOutputStream dataOutputStream= NetAdapter.dataOutputStream;
    protected DataInputStream dataInputStream=NetAdapter.dataInputStream;
    protected static DataOutputStream dataOutputStreamForSaving=NetAdapter.dataOutputStreamForSaving;
    protected DataInputStream dataInputStreamForChat=NetAdapter.dataInputStreamForChat;
    protected DataOutputStream dataOutputStreamForChat=NetAdapter.dataOutputStreamForChat;
    protected DataOutputStream dataOutputStreamForGettingOnlineUsers=NetAdapter.dataOutputStreamForGettingOnlineUsers;
    protected DataInputStream dataInputStreamForGettingOnlineUsers=NetAdapter.dataInputStreamForGettingOnlineUsere;


}

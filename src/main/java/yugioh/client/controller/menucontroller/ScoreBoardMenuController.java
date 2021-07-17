package yugioh.client.controller.menucontroller;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import yugioh.client.controller.DataBaseController;
import yugioh.client.model.ScoreBoardItem;
import yugioh.client.model.TableItem;
import yugioh.client.model.User;
import yugioh.client.view.NetAdapter;
import yugioh.client.view.SoundPlayable;
import yugioh.client.view.ViewInterface;

import java.io.File;
import java.net.URL;
import java.util.*;

public class ScoreBoardMenuController extends MenuController implements Initializable {

    public static ScoreBoardMenuController scoreBoardMenuController;
    public TableView<TableRow> scoreBoard;
    public MediaView scoreBoardMenuBackground;
    public AnchorPane scoreBoardPane;

    private static Thread autoUpdateTableThread;
    private static boolean doStopUpdating = false;

    public ScoreBoardMenuController() {
    }

    public static ScoreBoardMenuController getInstance() {
        if (scoreBoardMenuController == null) {
            scoreBoardMenuController = new ScoreBoardMenuController();
        }
        return scoreBoardMenuController;
    }

//    public LinkedHashMap<Integer, HashMap<Integer, String>> getScoreBoard() {
//        return User.getScoreBoardUsers();
//    }

    public void backClicked() throws Exception {
        doStopUpdating = true;
        autoUpdateTableThread.stop();
        autoUpdateTableThread = null;
        ViewInterface.showResult("menu exit");
        SoundPlayable.playButtonSound("backButton");
        mainMenu.execute();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        doStopUpdating = false;
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src\\resources\\yugioh\\Backgrounds\\main.mp4").toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        scoreBoardMenuBackground.setMediaPlayer(mediaPlayer);
        initializeScoreBoard();

        autoUpdateTableThread = new Thread(() -> {
            while (true){
                updateScoreBoardFromServer();
                if (doStopUpdating) return;
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ignored) {
                }
            }
        });
        autoUpdateTableThread.start();
    }

    private void updateScoreBoardFromServer() {
        String scoreboardItemsSting = NetAdapter.sendRequest("scoreboard show");
        ScoreBoardItem[] scoreBoardItems = DataBaseController.getObjectByString(scoreboardItemsSting);
        ArrayList<TableItem> tableItems = makeTableItemsFromUsers(scoreBoardItems);
        sortUsers(tableItems);

        scoreBoard.getItems().clear();
        scoreBoard.getStyleClass().clear();

        int toBeSelected = 0;
        int counter = 0;
        for (int i = 0; i < tableItems.size(); i++) {
            TableItem tableItem = tableItems.get(i);
            counter++;
            if (counter > 10) break;
            if (tableItem.getUsername().equals(User.loggedInUser.getUsername() + " (online)")) {
                toBeSelected = i;
            }
            scoreBoard.getStyleClass().add("simpleText");
            scoreBoard.getItems().add(tableItem);
        }

        scoreBoard.requestFocus();
        scoreBoard.getSelectionModel().clearAndSelect(toBeSelected);
        scoreBoard.getFocusModel().focus(toBeSelected);
    }

    private ArrayList<TableItem> makeTableItemsFromUsers(ScoreBoardItem[] scoreBoardItems) {
        String onlineUsers="";
        try {
            dataOutputStream.writeUTF("get online users");
            onlineUsers = dataInputStream.readUTF();
        }catch (Exception e){
            e.printStackTrace();
        }
        ArrayList<TableItem> tableItems = new ArrayList<>();
        for (int i = 0; i < scoreBoardItems.length; i++) {
            ScoreBoardItem scoreBoardItem = scoreBoardItems[i];
            ImageView imageView;
            TableItem tableItem;
            if(onlineUsers.contains("\"" + scoreBoardItem.getUsername() + "\"")){
                imageView = new ImageView(new Image(new File("src\\resources\\yugioh\\PNG\\icon\\greenCircle.png").toURI().toString()));
                tableItem = new TableItem(i + 1, scoreBoardItem.getUsername() + " (online)", scoreBoardItem.getScore());
            }
            else {
                imageView = new ImageView(new Image(new File("src\\resources\\yugioh\\PNG\\icon\\redCircle.png").toURI().toString()));
                tableItem = new TableItem(i + 1, scoreBoardItem.getUsername(), scoreBoardItem.getScore());
            }
            tableItems.add(tableItem);
        }
        return tableItems;
    }

    private void initializeScoreBoard() {
        TableColumn<TableRow, Object> column1 = new TableColumn<>("Rank");
        column1.setCellValueFactory(new PropertyValueFactory<>("rank"));
        column1.setStyle("-fx-alignment: CENTER;");
        TableColumn<TableRow, Object> column2 = new TableColumn<>("Username");
        column2.setCellValueFactory(new PropertyValueFactory<>("username"));
        column2.setStyle("-fx-alignment: CENTER;");
        TableColumn<TableRow, Object> column3 = new TableColumn<>("Max Score");
        column3.setCellValueFactory(new PropertyValueFactory<>("score"));
        column3.setStyle("-fx-alignment: CENTER;");
        scoreBoard.getColumns().add(column1);
        scoreBoard.getColumns().add(column2);
        scoreBoard.getColumns().add(column3);

    }

    private void sortUsers(ArrayList<TableItem> tableItems) {
        tableItems.sort(new SortByScore());
        tableItems.sort(new SortByName());
        for (int i = 0; i < tableItems.size(); i++) {
            TableItem tableItem = tableItems.get(i);
            tableItem.setRank(i + 1);
            if (i == 0) continue;
            if (tableItems.get(i - 1).getScore() == tableItem.getScore())
                tableItem.setRank(tableItems.get(i - 1).getRank());
        }
    }

}

class SortByScore implements Comparator<TableItem> {

    @Override
    public int compare(TableItem user1, TableItem user2) {
        return (user2.getScore() - user1.getScore());
    }

}

class SortByName implements Comparator<TableItem> {

    @Override
    public int compare(TableItem user1, TableItem user2) {
        return (user1.getUsername().compareTo(user2.getUsername()));
    }

}

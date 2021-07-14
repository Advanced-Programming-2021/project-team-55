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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import yugioh.client.controller.DataBaseController;
import yugioh.client.model.ScoreBoardItem;
import yugioh.client.model.TableItem;
import yugioh.client.model.User;
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
        ViewInterface.showResult("menu exit");
        SoundPlayable.playButtonSound("backButton");
        mainMenu.execute();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src\\resources\\yugioh\\Backgrounds\\main.mp4").toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        scoreBoardMenuBackground.setMediaPlayer(mediaPlayer);
        initializeScoreBoard();

        String scoreboardItemsSting = ViewInterface.showResult("scoreboard show");
        ScoreBoardItem[] scoreBoardItems = DataBaseController.getObjectByString(scoreboardItemsSting);
        ArrayList<TableItem> tableItems = makeTableItemsFromUsers(scoreBoardItems);
        sortUsers(tableItems);

        int toBeSelected = 0;
        int counter = 0;
        for (int i = 0; i < tableItems.size(); i++) {
            TableItem tableItem = tableItems.get(i);
            counter++;
            if (counter > 10) break;
            if (tableItem.getUsername().equals(User.loggedInUser.getUsername())) {
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
        }catch (Exception e){}
//        Circle onlineCircle=new Circle(10, Color.GREEN);
//        Circle offlineCircle=new Circle(10, Color.RED);
        ArrayList<TableItem> tableItems = new ArrayList<>();
        for (int i = 0; i < scoreBoardItems.length; i++) {
            ScoreBoardItem scoreBoardItem = scoreBoardItems[i];
            if(onlineUsers.contains(scoreBoardItem.getNickname())){
                ImageView imageView=new ImageView(new Image(new File("src\\resources\\yugioh\\PNG\\icon\\greenCircle.png").toURI().toString()));
                TableItem tableItem = new TableItem("yes",i + 1, scoreBoardItem.getNickname(), scoreBoardItem.getScore());
                tableItems.add(tableItem);
            }
            else {
                ImageView imageView=new ImageView(new Image(new File("src\\resources\\yugioh\\PNG\\icon\\redCircle.png").toURI().toString()));
                TableItem tableItem = new TableItem("no", i + 1, scoreBoardItem.getNickname(), scoreBoardItem.getScore());
                tableItems.add(tableItem);
            }
        }
        return tableItems;
    }

    private void initializeScoreBoard() {
        TableColumn<TableRow, Object> column0 = new TableColumn<>("Online Status");
        column0.setCellValueFactory(new PropertyValueFactory<>("online"));
        column0.setStyle("-fx-alignment: CENTER;");
        TableColumn<TableRow, Object> column1 = new TableColumn<>("Rank");
        column1.setCellValueFactory(new PropertyValueFactory<>("rank"));
        column1.setStyle("-fx-alignment: CENTER;");
        TableColumn<TableRow, Object> column2 = new TableColumn<>("Nickname");
        column2.setCellValueFactory(new PropertyValueFactory<>("username"));
        column2.setStyle("-fx-alignment: CENTER;");
        TableColumn<TableRow, Object> column3 = new TableColumn<>("Max Score");
        column3.setCellValueFactory(new PropertyValueFactory<>("score"));
        column3.setStyle("-fx-alignment: CENTER;");
        scoreBoard.getColumns().add(column0);
        scoreBoard.getColumns().add(column1);
        scoreBoard.getColumns().add(column2);
        scoreBoard.getColumns().add(column3);

    }

    private void sortUsers(ArrayList<TableItem> tableItems) {
        tableItems.sort(new SortByScore());
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

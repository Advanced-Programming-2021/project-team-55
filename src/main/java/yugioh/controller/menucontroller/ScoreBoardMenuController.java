package yugioh.controller.menucontroller;

import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Color;
//import org.jetbrains.annotations.NotNull;
import yugioh.model.TableItem;
import yugioh.model.User;

import java.net.URL;
import java.util.*;

public class ScoreBoardMenuController extends MenuController implements Initializable {

    public static ScoreBoardMenuController scoreBoardMenuController;
    public TableView<TableRow> scoreBoard;

    public ScoreBoardMenuController() {
    }

    public static ScoreBoardMenuController getInstance() {
        if (scoreBoardMenuController == null) {
            scoreBoardMenuController = new ScoreBoardMenuController();
        }
        return scoreBoardMenuController;
    }

    public LinkedHashMap<Integer, HashMap<Integer, String>> getScoreBoard() {
        return User.getScoreBoardUsers();
    }

    public void backClicked() throws Exception {
        playButtonSound();
        mainMenu.execute();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeScoreBoard();

        ArrayList<User> users = User.getAllUsers();
        ArrayList<TableItem> tableItems = makeTableItemsFromUsers(users);
        sortUsers(tableItems);

        int counter = 0;
        for (TableItem tableItem : tableItems) {
            counter++;
            if (counter > 10) break;
            if(tableItem.getUsername().equals(User.loggedInUser.getUsername())){
                tableItem.setStyle("-fx-background-color:green;");
            }
            scoreBoard.getStyleClass().add("simpleText");
            scoreBoard.getItems().add(tableItem);
        }
    }

   // @NotNull
    private ArrayList<TableItem> makeTableItemsFromUsers(ArrayList<User> users) {
        ArrayList<TableItem> tableItems = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            TableItem tableItem=new TableItem(i + 1, user.getNickname(), user.getScore());
            tableItems.add(tableItem);
        }
        return tableItems;
    }

    private void initializeScoreBoard() {
        TableColumn<TableRow,Object> column0 = new TableColumn<>("Rank");
        column0.setCellValueFactory(new PropertyValueFactory<>("rank"));
        column0.setStyle("-fx-alignment: CENTER;");
        TableColumn<TableRow, Object> column1 = new TableColumn<>("Nickname");
        column1.setCellValueFactory(new PropertyValueFactory<>("username"));
        column1.setStyle("-fx-alignment: CENTER;");
        TableColumn<TableRow, Object> column2 = new TableColumn<>("Max Score");
        column2.setCellValueFactory(new PropertyValueFactory<>("score"));
        column2.setStyle("-fx-alignment: CENTER;");
        scoreBoard.getColumns().add(column0);
        scoreBoard.getColumns().add(column1);
        scoreBoard.getColumns().add(column2);

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

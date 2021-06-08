package yugioh.controller.menucontroller;

import yugioh.model.User;

import java.util.*;

public class ScoreBoardMenuController extends MenuController {

    public static ScoreBoardMenuController scoreBoardMenuController;

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
        mainMenu.execute();
    }
}

//todo use this for scoreboard

//        import javafx.fxml.Initializable;
//        import javafx.scene.control.TableColumn;
//        import javafx.scene.control.TableView;
//        import javafx.scene.control.cell.PropertyValueFactory;
//        import javafx.scene.text.Text;
//        import pacman.Model.User;
//        import pacman.View.MainMenu;
//        import pacman.View.ScoreBoardMenu;
//
//        import java.io.File;
//        import java.net.URL;
//        import java.util.ArrayList;
//        import java.util.Comparator;
//        import java.util.ResourceBundle;
//
//public class ScoreBoardMenuController implements Initializable {
//
//    public TableView<User> scoreBoard;
//    public Text myScore;
//
//    private ArrayList<User> importUsers() {
//        ArrayList<User> users = new ArrayList<>();
//        File f = new File("src\\main\\resources\\pacman\\users\\");
//        String[] pathNames = f.list();
//        for (String name : pathNames) {
//            users.add(UserController.getSavedUser(name.replaceAll(".json", "")));
//        }
//        return users;
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        myScore.setText(ScoreBoardMenu.getLoggedUser().getMaxScore() + "");
//        initializeScoreBoard();
//
//        ArrayList<User> users = importUsers();
//        sortUsers(users);
//
//        int counter = 0;
//        for (User user : users) {
//            counter++;
//            if (counter > 10) break;
//            scoreBoard.getItems().add(user);
//        }
//    }
//
//    private void initializeScoreBoard() {
//        TableColumn<User, Object> column0 = new TableColumn<>("Rank");
//        column0.setCellValueFactory(new PropertyValueFactory<>("lastRank"));
//
//        TableColumn<User, Object> column1 = new TableColumn<>("Username");
//        column1.setCellValueFactory(new PropertyValueFactory<>("username"));
//
//        TableColumn<User, Object> column2 = new TableColumn<>("Max Score");
//        column2.setCellValueFactory(new PropertyValueFactory<>("maxScore"));
//
//        scoreBoard.getColumns().add(column0);
//        scoreBoard.getColumns().add(column1);
//        scoreBoard.getColumns().add(column2);
//    }
//
//    private void sortUsers(ArrayList<User> users) {
//        users.sort(new SortByScore());
//        for (int i = 0; i < users.size(); i++) {
//            User user = users.get(i);
//            user.setLastRank(i + 1);
//            if (i == 0) continue;
//            if (users.get(i - 1).getMaxScore() == user.getMaxScore())
//                user.setLastRank(users.get(i - 1).getLastRank());
//        }
//    }

//}
//
//class SortByScore implements Comparator<User> {
//
//    @Override
//    public int compare(User user1, User user2) {
//        return (user2.getMaxScore() - user1.getMaxScore());
//    }
//}
//

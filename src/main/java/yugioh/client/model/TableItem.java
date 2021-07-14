package yugioh.client.model;

import javafx.scene.control.TableRow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.File;

public class TableItem extends TableRow {

    protected String text;
    int rank;
    String username;
    int score;
    //ImageView imageView;
    String online;

    public TableItem(String online,int rank, String username, int score) {
        this.rank = rank;
        this.username = username;
        this.score = score;
        this.online=online;
        //this.imageView=imageView;

    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}


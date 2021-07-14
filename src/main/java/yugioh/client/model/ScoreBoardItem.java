package yugioh.client.model;

public class ScoreBoardItem {
    String username;
    int score;

    public ScoreBoardItem(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}

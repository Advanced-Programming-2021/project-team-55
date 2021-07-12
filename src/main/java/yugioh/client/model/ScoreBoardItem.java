package yugioh.client.model;

public class ScoreBoardItem {
    String nickname;
    int score;

    public ScoreBoardItem(String nickname, int score) {
        this.nickname = nickname;
        this.score = score;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }
}

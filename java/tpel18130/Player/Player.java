package tpel18130.Player;

public class Player {
    private int hp;
    private int score;

    Player() {
        this.hp = 6;
        this.score = 0;
    }

    public boolean hasLost() {
        return hp == 0;
    }

    //getters
    public int getHp() {
        return hp;
    }
    public int getScore() {
        return score;
    }
}

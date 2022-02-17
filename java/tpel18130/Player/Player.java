package tpel18130.Player;

import java.util.Scanner;

public class Player {
    private int hp;
    private int score;

    public Player() {
        this.hp = 6;
        this.score = 0;
    }

    public void addToScore(int a) {
        score += a;
    }
    public void removeHp() {
        hp--;
    }
    public boolean hasLost() {
        return hp == 0;
    }

    public String giveInput() throws InvalidInput {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        //TODO check if those errors are needed
        if(s.length() != 1) throw new InvalidInput();
        if(s.replaceAll("[^A-Z]", "").equals("")) throw new InvalidInput();
        //TODO make user choose a valid letter
        return s;
    }

    //getters
    public int getHp() {
        return hp;
    }
    public int getScore() {
        return score;
    }

}

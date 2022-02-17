package tpel18130.Player;

import java.util.Scanner;
import java.util.Set;

public class Player {
    private int hp;
    private int score;
    private String chosenWord;

    public Player(String chosenWord) {
        this.hp = 6;
        this.score = 0;
        this.chosenWord = chosenWord;
    }

    //add score based on char probability
    public void addToScore(float p) {
        if(p >= 0.6f)
            score += 5;
        else if(p >= 0.4f)
            score += 10;
        else if(p >= 0.25f)
            score += 15;
        else
            score += 30;
    }
    public void removeHp() {
        hp--;
    }
    public boolean hasLost() {
        return hp == 0;
    }

    //give char input on given index
    public Character giveInput(Set<Character> set) throws InvalidInput {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();

        if(s.length() != 1) throw new InvalidInput();
        if(s.replaceAll("[^A-Z]", "").equals("")) throw new InvalidInput();

        Character c = s.charAt(0);
        if(!set.contains(c)) throw new InvalidInput();

        return c;
    }

    //getters
    public int getHp() {
        return hp;
    }
    public int getScore() {
        return score;
    }
    public String getChosenWord() {
        return chosenWord;
    }
}

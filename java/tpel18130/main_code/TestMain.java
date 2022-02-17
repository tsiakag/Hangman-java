package tpel18130.main_code;

import tpel18130.Dictionary.*;
import tpel18130.Dictionary.Dictionary;
import tpel18130.WordProbability.*;
import tpel18130.Player.Player;

import java.io.IOException;
import java.util.*;


public class TestMain {
    public static void main(String[] args) throws UndersizeException, UnbalancedException, IOException {
        Dictionary d = new Dictionary("OL31390631M");
        d.CreateDictionary();
        //word is chosen by word-probability
        //TODO maybe change it later?
        WordProbability prob = new WordProbability(d, d.getRandomWord());
        Player player = new Player(prob.getChosenWord());

        Set<Integer> foundIndexes = new HashSet<>();
        Map<Integer, Map<Character, Float>> probList = prob.probalitiesList(foundIndexes);

        while(true) {
            //word found
            if (prob.getValidWords().size() == 1) {

                for(int i=0; i<(player.getChosenWord().length() - foundIndexes.size() - 1); ++i)
                    player.addToScore(1.0f);

                System.out.print("Word found!!!: ");
                System.out.println(player.getChosenWord());
                System.out.print("Player won a score of: ");
                System.out.println(player.getScore());
                return;
            }
            if(player.getHp() < 0) {
                System.out.println("LOST");
                System.out.print("The word was: ");
                System.out.println(player.getChosenWord());
                return;
            }

            //print possible
            for (Map.Entry<Integer, Map<Character, Float>> entry : probList.entrySet())
                System.out.println(entry.getKey() + ":" + entry.getValue());

            Scanner scan = new Scanner(System.in);
            int letter = scan.nextInt();

            Character choice = player.giveInput(probList.get(letter).keySet());

            //wrong answer
            if(player.getChosenWord().charAt(letter) != choice) {
                System.out.println("wrong, try again");
                player.removeHp();
            }
            //correct answer
            else{
                System.out.println("Success");
                player.addToScore(probList.get(letter).get(choice));

                foundIndexes.add(letter);
                prob.updateValidWords(letter);
                probList = prob.probalitiesList(foundIndexes);
            }



        }
    }
}

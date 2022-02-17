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
        Player player = new Player();
        WordProbability prob = new WordProbability(d, d.getRandomWord());

        Set<Integer> s = new HashSet<>();
        Map<Integer, Map<Character, Float>> m = prob.probalitiesList(s);

        for(String word : prob.getValidWords())
        System.out.println(word);

        for (Map.Entry<Integer, Map<Character, Float>> entry : m.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
            System.out.println("--------------------------");
        }
    }
}

package tpel18130.WordProbability;

import tpel18130.Dictionary.Dictionary;

import java.io.IOException;
import java.util.*;


public class WordProbability {
    private final String chosenWord;
    private List<String> validWords;

    public WordProbability(Dictionary d, String chosenWord) throws IOException {
        this.chosenWord = chosenWord;
        validWords = d.getDictionary();

        validWords.removeIf(word -> word.length() != chosenWord.length());
    }

    //update the validWords by removing words that dont contain given char at index
    public void updateValidWords(int index) {
        validWords.removeIf(word -> word.charAt(index) != chosenWord.charAt(index));
    }
    //calculate probability for each char
    private HashMap<Character, Float> updateCharProbabilty(int index) {
        //init to zero at start (done from algorithm)
        HashMap<Character, Float> m = new HashMap<Character, Float>();
        int count;
        for(char c = 'A'; c <= 'Z'; ++c) {
            count = 0;
            for(String word:validWords)
                if(word.charAt(index) == c) count++;
            m.put(c, (float)count / (float)validWords.size());
        }
        return m;
    }

    //TODO remove after debug
    public List<String> getValidWords() {
        return validWords;
    }
    public String getChosenWord() {
        return chosenWord;
    }

    //TODO check if this is correct representation
    public Map<Integer, Map<Character, Float>> probalitiesList(Set<Integer> s) {
        Map<Integer, Map<Character, Float>> temp = new HashMap<Integer, Map<Character, Float>>();

        for (int i = 0; i < chosenWord.length()-1; i++)
            //don't create for index we found the word
            if (!s.contains(i)) {
                HashMap<Character, Float> m = updateCharProbabilty(i);
                m.values().removeIf(value -> value <= 0.0f); //keep only those who are not 0
                temp.put(i, sortByValue(m));
            }
        return temp;
    }

    //sort the map based on highest probability
    private Map<Character, Float> sortByValue(HashMap<Character, Float> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<Character, Float> > list
                = new LinkedList<Map.Entry<Character, Float>>(
                hm.entrySet());

        // Sort the list using lambda expression
        list.sort((i1,
                   i2) -> i1.getValue().compareTo(i2.getValue()));

        // put data from sorted list to hashmap
        HashMap<Character, Float> temp
                = new LinkedHashMap<Character, Float>();
        for (Map.Entry<Character, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}

package tpel18130.WordProbability;

import tpel18130.Dictionary.Dictionary;

import java.io.IOException;
import java.util.*;


public class WordProbability {
    private final String chosenWord;
    private List<String> validWords;
    private Map<Character, Float> charProbability;

    public WordProbability(Dictionary d, String chosenWord) throws IOException {
        this.chosenWord = chosenWord;
        validWords = d.getDictionary();

        //keep only words equal of length with thew chosen one
        validWords.remove(chosenWord);
        validWords.removeIf(word -> word.length() != chosenWord.length());

        //TODO use it on counter
    }

    //update the validWords by removing words that dont contain given char at index
    public void updateValidWords(int index, char x) {
        validWords.removeIf(word -> word.charAt(index) != x);
    }
    //calculate probability for each char
    private Map<Character, Float> updateCharProbabilty(int index) {
        //init to zero at start (done from algorithm)
        Map<Character, Float> m = new HashMap<Character, Float>();
        int count;
        for(char c = 'A'; c <= 'Z'; ++c) {
            count = 0;
            for(String word:validWords)
                if(word.charAt(index) == c) count++;
            m.put(c, (float)count / (float) validWords.size());
        }
        return m;
    }

    //TODO remove after debug
    public List<String> getValidWords() {
        return validWords;
    }

    //TODO check if this is correct representation
    public Map<Integer, Map<Character, Float>> probalitiesList(Set<Integer> s) {
        Map<Integer, Map<Character, Float>> temp = new HashMap<Integer, Map<Character, Float>>();

        for (int i = 0; i < chosenWord.length()-1; i++)
            //don't create for index we found the word
            if (!s.contains(i)) {
                Map<Character, Float> m = updateCharProbabilty(i);
                m.values().removeIf(value -> value <= 0.0f); //keep only those who are not 0
                temp.put(i, m);
            }

        return temp;
    }

    //TODO create the probability counter
}

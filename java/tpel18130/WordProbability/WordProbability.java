package tpel18130.WordProbability;

import tpel18130.Dictionary.Dictionary;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class WordProbability {
    private String chosenWord;
    private List<String> validWords;
    private Map<Character, Float> charProbability;

    public WordProbability(Dictionary d, String chosenWord) throws IOException {
        this.chosenWord = chosenWord;
        validWords = d.getDictionary();
        validWords.remove(chosenWord);

        //TODO use it on counter
        for(char c = 'A'; c <= 'Z'; ++c)
            charProbability.put(c, 0.0f);
    }
    //TODO make it in consctructor
//    private List<String> updateValidWords() {}

    //TODO create the probability counter
}

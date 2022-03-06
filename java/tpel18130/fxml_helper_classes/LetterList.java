package tpel18130.fxml_helper_classes;

public class LetterList {
    private int index;
    private String letters;

    public LetterList(Integer i, String l) {
        index = i;
        letters = l;
    }

    public int getIndex() {
        return index;
    }

    public String getLetters() {
        return letters;
    }
}

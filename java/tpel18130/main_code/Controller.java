package tpel18130.main_code;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tpel18130.Dictionary.Dictionary;
import tpel18130.Dictionary.InvalidCountException;
import tpel18130.Dictionary.UnbalancedException;
import tpel18130.Dictionary.UndersizeException;
import tpel18130.Player.Player;
import tpel18130.WordProbability.WordProbability;
import tpel18130.fxml_helper_classes.LetterList;
import tpel18130.fxml_helper_classes.SaveFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Controller {
    public TableView LetterTableView;
    public TableColumn table_int;
    public TableColumn table_letters;
    public Text titleWords;
    public Text titleScore;
    public Text titleSuccess;
    public ImageView hangmanImage;
    public Text hagmanWord;
    public ChoiceBox<Integer> selectNum;
    public ChoiceBox<Character> selectLetter;
    public Button OKbutton;

    //non-fxml properties
    private Dictionary d;
    private WordProbability prob;
    private Player player;

    //those two are only used for calulating success percentage
    private int successCounter = 0;
    private int round = 0;

    Set<Integer> foundIndexes = new HashSet<>();
    Set<Character> foundletters = new HashSet<>();
    Map<Integer, Map<Character, Float>> probList;


    @FXML
    private MenuItem Quit;
    @FXML
    private VBox vbox;

    //basicMethods for the game to work
    @FXML
    void Reload() throws IOException {
        RefreshLetters();
        SetTitleText();
        LoadHangmanImage();
        createWordArray();

        //check if you lose here for the picture to show
        if(player.hasLost())
            ShowDefeatScreen();
    }
    @FXML
    void RestartGame() throws IOException {
        //basically clears everything in order for the game to work again
        selectLetter.getItems().clear();
        round = 0; successCounter = 0;
        prob = null;
        player = null;
        foundletters.clear();
        foundIndexes.clear();
        probList.clear();
        Start();
    }

    //menu methods
    @FXML
    void Start() throws IOException {
        prob = new WordProbability(d, d.getRandomWord());
        player = new Player(prob.getChosenWord());
        RefreshLetters();
        SetTitleText();
        LoadHangmanImage();
        createWordArray();

        selectNum.getItems().clear();
        SelectNumInit();
    }
    @FXML
    void Exit() {
        Platform.exit();
    }
    @FXML
    void Load() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Load Dictionary");
        window.setMinWidth(300);
        window.setMinHeight(300);

        Label label = new Label();
        label.setText("Enter Dictionary ID to load");

        TextField textfield = new TextField();
        textfield.setPromptText("Enter Dictionary ID");

        Button enterButton = new Button("Load");
        enterButton.setOnAction(e -> {
            try {
                d = new Dictionary(textfield.getText());
                d.getDictionary(); //check if exists else returns error
                window.close();
            } catch (IOException  | NullPointerException ex) {
                label.setText("This Dictionary does not exist, try again");
                label.setTextFill(Color.RED);
            }
        }); //TODO implement correct

        VBox layout = new VBox(3);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(label, textfield,enterButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
    @FXML
    void Create() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Create new Dictionary");
        window.setMinWidth(300);
        window.setMinHeight(300);

        Label label = new Label();
        label.setText("Enter Dictionary ID to create");

        TextField textfield = new TextField();
        textfield.setPromptText("Enter Dictionary ID");

        Button enterButton = new Button("Create");
        enterButton.setOnAction(e -> {
            try {
            d = new Dictionary(textfield.getText());
                d.CreateDictionary();
                window.close(); //TODO may need better implementation
            } catch (IOException  | UndersizeException | UnbalancedException | InvalidCountException | NullPointerException ex) {
                label.setText("Incorrect ID, try again");
                label.setTextFill(Color.RED);
            }
        }); //TODO implement correct

        VBox layout = new VBox(3);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(label, textfield,enterButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
    @FXML
    void DictionaryDetails() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Dictionary Details");
        window.setMinWidth(300);
        window.setMinHeight(200);

        Label label1 = new Label();
        label1.setFont(new Font("Segoe UI", 20));
        label1.setText("Percentage of 6-letter words: " + d.getSixLetters());

        Label label2 = new Label();
        label2.setFont(new Font("Segoe UI", 20));
        label2.setText("Percentage of words with letters between 7 and 9: " + d.getSevenToNineLetters());

        Label label3 = new Label();
        label3.setFont(new Font("Segoe UI", 20));
        label3.setText("Percentage of words with more or 10 letters: " + d.getMoreThanTenLetters());

        VBox layout = new VBox(3);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(label1, label2, label3);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
    @FXML
    void ShowSolution() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Solution");
        window.setMinWidth(200);
        window.setMinHeight(200);

        Label label = new Label();
        label.setFont(new Font("Segoe UI", 16));
        label.setText("The solution was: " + player.getChosenWord());

        Label label2 = new Label();
        label2.setFont(new Font("Segoe UI", 20));
        label2.setText("Try Again :)");

        VBox layout = new VBox(3);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(label, label2);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        SaveFile sv = new SaveFile();
        try {
            sv.WriteToSave(player.getChosenWord(), round, "Computer");
            RestartGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ShowRounds () throws FileNotFoundException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Rounds Statistics");
        window.setMinWidth(300);
        window.setMinHeight(200);

        SaveFile sv = new SaveFile();

        Label label = new Label();
        label.setFont(new Font("Segoe UI", 20));
        label.setText("The Statistics for the last 5 rounds are: \n" + sv.ReadFromSave());

        VBox layout = new VBox(3);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(label);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    //table method
    @FXML
    void RefreshLetters() throws IOException {

        probList = prob.getProbalitiesList(foundIndexes);

        List<LetterList> temp= new ArrayList<>();
        for (Map.Entry<Integer, Map<Character, Float>> entry : probList.entrySet())
          temp.add(new LetterList(entry.getKey()+1, String.valueOf(entry.getValue().keySet())));


        final ObservableList<LetterList> data = FXCollections.observableArrayList(temp);

        table_int.setCellValueFactory(new PropertyValueFactory<LetterList, Integer>("index"));
        table_letters.setCellValueFactory(new PropertyValueFactory<LetterList, String>("letters"));

        LetterTableView.setItems(data);

    }

    //title method
    @FXML
    void SetTitleText() throws IOException {
        //TODO implement the rest of the titles
        titleWords.setText("Words: " + String.valueOf(d.getDictionary().size()));
        titleScore.setText("Score: " + String.valueOf(player.getScore()));
        float temp = (float) successCounter / (float) round;
        temp *= 100;
        if(round != 0)
            titleSuccess.setText("Success: " + String.valueOf(temp)+"%");
        else
            titleSuccess.setText("Success: 0.0%");
    }

    //main game methods
    @FXML
    void LoadHangmanImage() {
        Image bufferedImage = new Image("file:imgs/hanged" + String.valueOf(player.getHp()) + ".jpg");
        hangmanImage.setImage(bufferedImage);
    }
    @FXML
    void createWordArray() {
        //TODO make found letters have a bigger scope
        String result = "";
        String word = player.getChosenWord();

        for(Integer i:foundIndexes)
            foundletters.add(word.charAt(i));

        for(int i = 0; i < word.length()-1; i++) {
            if (foundletters.contains(word.charAt(i)))
                result += word.charAt(i);
            else
                result += "_ ";
        }
        hagmanWord.setText(result);
    }

    //selection methods
    @FXML
    void SelectNumInit () {
        String word = player.getChosenWord();

        for(int i = 0; i < word.length()-1; i++)
            if(!foundIndexes.contains(i))
                selectNum.getItems().add(i+1);

        //inits the letter choicebox
        selectNum.setOnAction(event -> {
            selectLetter.getItems().clear();
            if(probList.get(selectNum.getValue()-1).keySet().size() != 0)
                selectLetter.getItems().addAll(probList.get(selectNum.getValue()-1).keySet());
        });
    }
    @FXML
    void ChooseLetter () throws IOException {
        //TODO do most of the implementation
        round++;
        int index = selectNum.getValue()-1;
        Character letter = selectLetter.getValue();
        String word = player.getChosenWord();

        //word found
        if(prob.getValidWords().size() == 1) {
            for(int i=0; i<(player.getChosenWord().length() - foundIndexes.size() - 1); ++i)
                player.addToScore(1.0f);
            SaveFile sv = new SaveFile();
            sv.WriteToSave(player.getChosenWord(), round, "Player");
            Reload();
            ShowWinningScreen();
        }

        //wrong answer
        if(word.charAt(index) != letter) {
            player.removeFromScore();
            player.removeHp();
            Reload();
        }
        //correct answer
        else {
            successCounter++;
            player.addToScore(probList.get(index).get(letter));

            foundIndexes.add(index);
            foundletters.add(letter);
            prob.updateValidWords(index);
            probList = prob.getProbalitiesList(foundIndexes);
            Reload();
        }
        CheckIfWon();
    }

    //win game
    @FXML
    void ShowWinningScreen() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);


        window.setTitle("You won!!!");
        window.setMinWidth(640);
        window.setMinHeight(400);

        Label label = new Label();
        label.setText("Word Found!!! The word was: " + player.getChosenWord());

        Label label2 = new Label();
        label2.setText("Player won with a score of: " + String.valueOf(player.getScore()));

        Button button = new Button("Play again");
        button.setOnAction(event -> {
            try {
                RestartGame();
                window.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        VBox layout = new VBox(3);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(label, label2, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    //lose the game
    @FXML
    void ShowDefeatScreen() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);


        window.setTitle("You lost :(");
        window.setMinWidth(640);
        window.setMinHeight(400);

        Label label = new Label();
        label.setText("The word you were looking for was: " + player.getChosenWord());

        Label label2 = new Label();
        label2.setText("Better luck next time!!!");

        Button button = new Button("Try again");
        button.setOnAction(event -> {
            SaveFile sv = new SaveFile();
            try {
                sv.WriteToSave(player.getChosenWord(), round, "Computer");
                RestartGame();
                window.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        VBox layout = new VBox(3);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(label, label2, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    @FXML
    void CheckIfWon() throws IOException {
        //check if word found in here because in the input method it requires one more round
        if(prob.getValidWords().size() == 1) {
            for(int i=0; i<(player.getChosenWord().length() - foundIndexes.size() - 1); ++i)
                player.addToScore(1.0f);
            SaveFile sv = new SaveFile();
            sv.WriteToSave(player.getChosenWord(), round, "Player");
            Reload();
            ShowWinningScreen();
        }
    }
}











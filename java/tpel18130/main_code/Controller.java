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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tpel18130.Dictionary.Dictionary;
import tpel18130.Dictionary.InvalidCountException;
import tpel18130.Dictionary.UnbalancedException;
import tpel18130.Dictionary.UndersizeException;
import tpel18130.Player.Player;
import tpel18130.WordProbability.WordProbability;
import tpel18130.fxml_helper_classes.LetterList;

import java.io.IOException;
import java.util.*;

public class Controller {
    public TableView LetterTableView;
    public TableColumn table_int;
    public TableColumn table_letters;
    //non-fxml properties
    private Dictionary d;
    private WordProbability prob;
    private Player player;
    Set<Integer> foundIndexes = new HashSet<>();

    @FXML
    private MenuItem Quit;
    @FXML
    private VBox vbox;

    @FXML
    void Start() throws IOException {
        prob = new WordProbability(d, d.getRandomWord());
        player = new Player(prob.getChosenWord());
        RefreshLetters();
        System.out.println("Start Success");
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
        window.setMinWidth(300);
        window.setMinHeight(200);

        Label label = new Label();
        label.setFont(new Font("Segoe UI", 20));
        label.setText("The solution is: " + player.getChosenWord());

        VBox layout = new VBox(3);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(label);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    @FXML
    void RefreshLetters() throws IOException {

        Map<Integer, Map<Character, Float>> probList = prob.getProbalitiesList(foundIndexes);

        List<LetterList> temp= new ArrayList<>();
        for (Map.Entry<Integer, Map<Character, Float>> entry : probList.entrySet())
          temp.add(new LetterList(entry.getKey(), String.valueOf(entry.getValue().keySet())));


        final ObservableList<LetterList> data = FXCollections.observableArrayList(temp);

        table_int.setCellValueFactory(new PropertyValueFactory<LetterList, Integer>("index"));
        table_letters.setCellValueFactory(new PropertyValueFactory<LetterList, String>("letters"));

        LetterTableView.setItems(data);



    }
}



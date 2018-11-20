package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main extends Application {

    public static final String SPLASH_SCREEN = "Splash Screen";
    public static final String TEST_SCREEN = "Test Screen";
    public static final String SINGLE_CHOICE_TEST_SCREEN = "Test Screen";
    public static final String MULTI_CHOICE_TEST_SCREEN = "Test Screen";

    private ScreenController screenController;


    Question currentQuestion;

    private HashMap<RadioButton, Integer> radioButtonIntegerHashMap;

    private int currentNumber = 0;
    private int rightAnswers = 0;
    private long startTime = 0;


    private VBox mainContainer;
    private Label questionLabel;
    private ArrayList<HBox> hBoxes;
    private Button nextQuestionButton;

    private Label hashLabel;
    private Label numberLabel;

    private String hashCode;

    private ArrayList<Question> questions;

    private ToggleGroup toggleGroup;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("/res/xml/sample.fxml"));

        primaryStage.getIcons().add(new Image(getClass().getResource("/res/image/ic_launcher.png").toExternalForm()));

        //initQuestions();
        GridPane root = FXMLLoader.load(getClass().getResource("/res/xml/sample.fxml"));

        Scene scene = new Scene(root);

        mainContainer = ((VBox) scene.lookup("#mainCointainer"));
        questionLabel = ((Label) scene.lookup("#question"));
        nextQuestionButton = ((Button) scene.lookup("#nextQuestion"));

        hashLabel = ((Label) scene.lookup("#hash_label"));
        numberLabel = ((Label) scene.lookup("#number_label"));

        numberLabel.setText(Integer.toString(currentNumber + 1));

        nextQuestionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Question question = getQuestion();
                if(question.isSingle() && toggleGroup.getSelectedToggle() != null) {
                    if (radioButtonIntegerHashMap.get((RadioButton) toggleGroup.getSelectedToggle()) == question.getRightAnswer())
                        rightAnswers++;

                    currentNumber++;
                    if (currentNumber > questions.size() - 1) {
                        createAndShowResultScreen();
                    } else {
                        prepareScreenForQuestion(getQuestion());
                        numberLabel.setText(Integer.toString(currentNumber + 1));
                    }
                }
            }
        });

        radioButtonIntegerHashMap = new HashMap<>();

        initHboxes();

        questions = new ArrayList<>(Other.getQuestions());
        Collections.shuffle(questions);

        generateHash();
        
        startTime = System.currentTimeMillis();
        

        prepareScreenForQuestion(getQuestion());

        primaryStage.setScene(scene);

        primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean onHidden, Boolean onShown) {
                System.out.println("onHidden: " + onHidden + "; onShown: " + onShown);
                //if(onHidden)
                    //System.exit(0);
            }
        });

        primaryStage.show();


    }

    private void createAndShowResultScreen(){



        //initQuestions();
        //GridPane root = FXMLLoader.load(getClass().getResource("/res/xml/sample.fxml"));

        try {
            GridPane root = FXMLLoader.load(getClass().getResource("/res/xml/results_screen.fxml"));
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResource("/res/image/ic_launcher.png").toExternalForm()));
            stage.setTitle("My New Stage Title");
            Scene scene = new Scene(root, 300,400);

            Label hashLabel = ((Label) scene.lookup("#hashCode"));
            hashLabel.setText("HashCode: " + hashCode);

            Label timeLabel = ((Label) scene.lookup("#timeLabel"));
            String timeStampStart = new SimpleDateFormat("HH:mm:ss").format(new Date(startTime));
            String timeStampEnd = new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
            timeLabel.setText("Start Time: " + timeStampStart + "\nEnd Time: " + timeStampEnd);

            Label resultLabel = ((Label) scene.lookup("#resultLabel"));
            resultLabel.setText("Result: " + rightAnswers + " / " + questions.size());

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateHash(){
        hashCode = md5(Arrays.toString(questions.toArray()));
        hashLabel.setText("Хэш сессии: " + hashCode);
    }

    private static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return "";
    }

    private void initHboxes(){
        hBoxes = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            HBox hBox = new HBox();
            hBoxes.add(hBox);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(0,100,0,30));
            mainContainer.getChildren().add(hBox);
            VBox.setVgrow(hBox,Priority.ALWAYS);
        }
    }

    private void prepareScreenForQuestion(Question question) {
        questionLabel.setText(question.getQuestion());

        int answersCount = question.getAnswers().size();
        prepareNodesForQuestion(answersCount);
        ArrayList<Node> nodes = fillWithNodes(question.isSingle());
        List<String> answers = question.getAnswers();
        for (int i = 0; i < nodes.size(); i++) {
            if(question.isSingle())
                ((RadioButton) nodes.get(i)).setText(answers.get(i));
            else
                ((CheckBox) nodes.get(i)).setText(answers.get(i));

        }
    }

    private void prepareNodesForQuestion(int answersCount) {
        if (hBoxes.size() == answersCount)
            return;
        else if (hBoxes.size() < answersCount)
            for (int i = hBoxes.size(); i < answersCount; i++) {
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(0,100,0,30));
                hBoxes.add(hBox);
                mainContainer.getChildren().add(hBox);
                VBox.setVgrow(hBox,Priority.ALWAYS);
            }
        else
            for (int i = hBoxes.size()-1; i >= answersCount; i--) {
                mainContainer.getChildren().remove(hBoxes.get(i));
                hBoxes.remove(i);
            }
    }

    //private void remove

    private ArrayList<Node> fillWithNodes(boolean isSingle) {
        ArrayList<Node> nodes = new ArrayList<>();
        clearHboxes();

        if (isSingle) {
            ToggleGroup radioGroup = new ToggleGroup();
            for (HBox hbox : hBoxes) {
                hbox.setAlignment(Pos.CENTER_LEFT);
                RadioButton button = new RadioButton("Question");
                button.setPadding(new Insets(0, 0, 0, 30));
                button.setToggleGroup(radioGroup);
                button.setSelected(false);
                hbox.getChildren().add(button);
                nodes.add(button);
            }

            toggleGroup = radioGroup;
            radioButtonIntegerHashMap.clear();

            for (int i = 0; i < nodes.size(); i++) {
                radioButtonIntegerHashMap.put(((RadioButton) nodes.get(i)),i);
            }

        } else {
            for (HBox hbox : hBoxes) {
                hbox.setAlignment(Pos.CENTER_LEFT);
                CheckBox button = new CheckBox("Question");
                button.setPadding(new Insets(0, 0, 0, 30));
                hbox.getChildren().add(button);
                nodes.add(button);
            }
        }

        return nodes;
    }

    private void clearHboxes(){
        for(HBox hBox: hBoxes){
            if(hBox.getChildren().size() != 0)
                hBox.getChildren().remove(0);
        }
    }

    private Question getQuestion() {
        return questions.get(currentNumber);
    }
}

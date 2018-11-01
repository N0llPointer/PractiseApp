package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    public static final String SPLASH_SCREEN = "Splash Screen";
    public static final String TEST_SCREEN = "Test Screen";
    public static final String SINGLE_CHOICE_TEST_SCREEN = "Test Screen";
    public static final String MULTI_CHOICE_TEST_SCREEN = "Test Screen";

    private ScreenController screenController;


    private ArrayList<Question> questions;
    Question currentQuestion;

    private HashMap<RadioButton,Integer> radioButtonIntegerHashMap;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("/res/xml/sample.fxml"));

        primaryStage.getIcons().add(new Image(getClass().getResource("/res/image/ic_launcher.png").toExternalForm()));

        initQuestions();

        VBox root = new VBox();
        primaryStage.setTitle("Practise Application");
        Scene scene = new Scene(root);
        screenController = new ScreenController(scene);
        initializeScreens();
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void initQuestions() {
        questions = new ArrayList<>();
        ArrayList<String> answers = new ArrayList<>();
        answers.add("1");
        answers.add("2");
        answers.add("3");
        answers.add("4");
        questions.add(new Question("Как ваше имя?", answers, 3, true));
        questions.add(new Question("Как ваше настроение?", answers, 2, true));
        questions.add(new Question("Какой ваш любимый цвет?", answers, 1, true));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), null));
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                screenController.activate(SINGLE_CHOICE_TEST_SCREEN);
            }
        });
        timeline.play();

    }

    private void initializeScreens() {
        initializeSplashScreen();
        initializeTestScreen();
        initializeSingleChoiceTestScreen();

        screenController.activate(SPLASH_SCREEN);
    }

    private void initializeSplashScreen() {
        VBox root = new VBox(30);
        root.setPadding(new Insets(20));
        ImageView image = new ImageView(new Image(getClass().getResource("/res/image/ic_launcher_foreground.png").toExternalForm()));
        root.setBackground(new Background(new BackgroundFill(Color.rgb(2, 119, 189), CornerRadii.EMPTY, Insets.EMPTY)));
        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(image);


        screenController.addScreen(SPLASH_SCREEN, root);
    }

    private void initializeTestScreen() {
        VBox box = new VBox(10);
        box.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        screenController.addScreen(TEST_SCREEN, box);
    }

    private void initializeSingleChoiceTestScreen() {
        Question question = getQuestion();
        currentQuestion = question;
        radioButtonIntegerHashMap = new HashMap<>();

        VBox container = new VBox(15);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(25, 25, 25, 25));
        container.setFillWidth(true);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_CENTER);

        Text text = new Text("Some good old question!");
        text.setFont(Font.font(20));
        text.setTextAlignment(TextAlignment.CENTER);
        hbox.getChildren().add(text);
        container.getChildren().add(hbox);

        ToggleGroup radioGroup = new ToggleGroup();

        hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);

        RadioButton button = new RadioButton("Q1");
        button.setToggleGroup(radioGroup);
        button.setSelected(false);
        hbox.getChildren().add(button);
        container.getChildren().add(hbox);

        hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        //hbox.setAlignment(Pos.CENTER);

        RadioButton button1 = new RadioButton("Q2");
        button1.setToggleGroup(radioGroup);
        button1.setSelected(false);
        hbox.getChildren().add(button1);
        container.getChildren().add(hbox);

        hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);

        RadioButton button2 = new RadioButton("Q3");
        button2.setToggleGroup(radioGroup);
        button2.setSelected(false);

        hbox.getChildren().add(button2);
        container.getChildren().add(hbox);

        hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);

        RadioButton button3 = new RadioButton("Q4");
        button3.setToggleGroup(radioGroup);
        button3.setSelected(false);

        hbox.getChildren().add(button3);
        container.getChildren().add(hbox);


        hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);

        Button button4 = new Button("Next");
        hbox.getChildren().add(button4);
        container.getChildren().add(hbox);
        button4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                RadioButton btn = ((RadioButton) radioGroup.getSelectedToggle());
                int number = radioButtonIntegerHashMap.get(btn);

                if(number == currentQuestion.getRightAnswer()){
                    btn.setStyle("-fx-text-fill: green");
                }else
                    btn.setStyle("-fx-text-fill: red");


                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), null));
                timeline.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Question question = getQuestion();
                        currentQuestion = question;
                        text.setText(question.getQuestion());
                        List<String> answers = question.getAnswers();
                        button.setText(answers.get(0));
                        button.setSelected(false);

                        button1.setText(answers.get(1));
                        button1.setSelected(false);

                        button2.setText(answers.get(2));
                        button2.setSelected(false);

                        button3.setText(answers.get(3));
                        button3.setSelected(false);

                        button.setStyle("-fx-text-fill: black");
                        button1.setStyle("-fx-text-fill: black");
                        button2.setStyle("-fx-text-fill: black");
                        button3.setStyle("-fx-text-fill: black");
                    }
                });
                timeline.play();
            }
        });

        hbox = new HBox();
        hbox.setAlignment(Pos.BOTTOM_CENTER);
//
//        container.getChildren().add(button);
//        container.getChildren().add(button1);
//        container.getChildren().add(button2);
//        container.getChildren().add(button3);

        radioButtonIntegerHashMap.put(button,0);
        radioButtonIntegerHashMap.put(button1,1);
        radioButtonIntegerHashMap.put(button2,2);
        radioButtonIntegerHashMap.put(button3,3);

        text.setText(question.getQuestion());

        List<String> answers = question.getAnswers();
        button.setText(answers.get(0));
        button.setSelected(false);

        button1.setText(answers.get(1));
        button1.setSelected(false);

        button2.setText(answers.get(2));
        button2.setSelected(false);

        button3.setText(answers.get(3));
        button3.setSelected(false);

        screenController.addScreen(SINGLE_CHOICE_TEST_SCREEN, container);
    }

    private void initializeMultiChoiceTestScreen() {

    }

    private Question getQuestion(){
        Random random = new Random();
        return questions.get(random.nextInt(questions.size()));
    }
}

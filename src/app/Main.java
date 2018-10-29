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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    public static final String SPLASH_SCREEN = "Splash Screen";
    public static final String TEST_SCREEN = "Test Screen";
    public static final String SINGLE_CHOICE_TEST_SCREEN = "Test Screen";
    public static final String MULTI_CHOICE_TEST_SCREEN = "Test Screen";

    private ScreenController screenController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("/res/xml/sample.fxml"));

        primaryStage.getIcons().add(new Image(getClass().getResource("/res/image/ic_launcher.png").toExternalForm()));

        VBox root = new VBox();
        primaryStage.setTitle("Practise Application");
        Scene scene = new Scene(root);
        screenController = new ScreenController(scene);
        initializeScreens();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeScreens(){
        initializeSplashScreen();
        initializeTestScreen();
        initializeSingleChoiceTestScreen();

        screenController.activate(SPLASH_SCREEN);
    }

    private void initializeSplashScreen(){
        VBox root = new VBox(30);
        root.setPadding(new Insets(20));
        ImageView image = new ImageView(new Image(getClass().getResource("/res/image/ic_launcher_foreground.png").toExternalForm()));
        root.setBackground(new Background(new BackgroundFill(Color.rgb(2,119,189),CornerRadii.EMPTY,Insets.EMPTY)));
        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(image);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        }));
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                screenController.activate(SINGLE_CHOICE_TEST_SCREEN);
            }
        });
        timeline.play();

        screenController.addScreen(SPLASH_SCREEN,root);
    }

    private void initializeTestScreen(){
        VBox box = new VBox(10);
        box.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        screenController.addScreen(TEST_SCREEN,box);
    }

    private void initializeSingleChoiceTestScreen(){
        HBox textHbox = new HBox();
        textHbox.setAlignment(Pos.TOP_CENTER);

        Text text = new Text("Some good old question!");
        text.setFont(Font.font(20));
        text.setTextAlignment(TextAlignment.CENTER);
        textHbox.getChildren().add(text);
//        HBox radioGroupHbox = new HBox();
//        radioGroupHbox.setAlignment(Pos.BOTTOM_CENTER);

        GridPane grid = new GridPane();
        GridPane.setHgrow(textHbox,Priority.ALWAYS);
        GridPane.setVgrow(textHbox,Priority.ALWAYS);
        //GridPane.setHgrow(textHbox,Priority.ALWAYS);
        //grid.setAlignment(Pos.);
        grid.setHgap(10);
        grid.setVgap(30);
        grid.setPadding(new Insets(25,25,25,25));
        grid.setGridLinesVisible(true);

        grid.add(textHbox,0,0,2,2);

        ToggleGroup radioGroup = new ToggleGroup();

        RadioButton button = new RadioButton("Q1");
        button.setToggleGroup(radioGroup);
        button.setSelected(true);

        RadioButton button1 = new RadioButton("Q2");
        button1.setToggleGroup(radioGroup);
        button1.setSelected(true);

        RadioButton button2 = new RadioButton("Q3");
        button2.setToggleGroup(radioGroup);
        button2.setSelected(true);

        grid.add(button,0,2,2,1);
        grid.add(button1,0,3,2,1);
        grid.add(button2,0,4,2,1);


        screenController.addScreen(SINGLE_CHOICE_TEST_SCREEN,grid);
    }

    private void initializeMultiChoiceTestScreen(){

    }
}

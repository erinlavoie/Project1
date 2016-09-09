/*
 * File: Main.java
 * Author: Erin Lavoie and Tiffany Lam
 * Course: CS361
 * Project: 1
 * Date: Sept. 6, 2016
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import javafx.stage.WindowEvent;


public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) {


        BorderPane pane = new BorderPane();
        MenuBar menu = new MenuBar();
        HBox buttons = new HBox();

        // File menu with one menu item "Exit"
        Menu file = new Menu("File");
        MenuItem exit = new MenuItem("Exit");

        // window disappears and the application quits when exit clicked
        exit.setOnAction(event -> System.exit(0));

        file.getItems().addAll(exit);
        menu.getMenus().addAll(file);

        // add buttons
        Button playButton = new Button();
        Button stopButton = new Button();

        playButton.setText("Play scale");
        stopButton.setText("Stop playing");

        playButton.setStyle("-fx-base: lightgreen;" + "-fx-border-color: black;" + "-fx-background-radius: 5.0;" + "-fx-border-radius: 5.0");
        stopButton.setStyle("-fx-base: pink;" + "-fx-border-color: black;" + "-fx-background-radius: 5.0;" + "-fx-border-radius: 5.0");

        // dialog appear asking user for note number
        playButton.setOnAction(event -> getStartingPitch());

        buttons.setSpacing(10.0);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(playButton, stopButton);

        pane.setTop(menu);
        pane.setCenter(buttons);

        Scene scene = new Scene(pane, 300, 250);

        primaryStage.setTitle("Scale Player");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });

    }

    public void getStartingPitch() {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Starting note");
        dialog.setHeaderText("Give me a starting note (0-115): ");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            playScale( Integer.parseInt(result.get()) );
        }

    }

    public void playScale(int starting_pitch ) {

        // Settings for MidiPlayer and Scale
        int resolution = 5;
        int beatsPerMinute = 60;
        int volume = 60;
        int startTick = 0;

        // creating MidiPlayer
        MidiPlayer midi = new MidiPlayer(resolution, beatsPerMinute);

        int[] major_scale = new int[]{0, 2, 2, 1, 2, 2, 2, 1};

        for (int i = 0; i < major_scale.length; i++) {
            midi.addNote(starting_pitch += major_scale[i], volume, startTick += 1, 1, 0, 0);
        }

        for (int j = 7; j > 0; j--) {
            midi.addNote(starting_pitch -= major_scale[j], volume, startTick += 1, 1, 0, 0);
        }

        midi.play();

    }

    public static void main(String[] args) {
        launch(args);
    }
}


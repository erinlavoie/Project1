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
import javafx.application.Platform;
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
        exit.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

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
        playButton.addEventHandler(ActionEvent.ACTION, new playScaleEventHandler() );

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
                Platform.exit();
                System.exit(0);
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}

class playScaleEventHandler implements EventHandler<ActionEvent> {

    public void handle(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Starting note");
        dialog.setHeaderText("Give me a starting note (0-115): ");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {

            MidiPlayer midi = new MidiPlayer(1, 60);

            int volume = 60;

            int starting_pitch = Integer.parseInt(result.get());

//            for (int i = 0; i < 8; i++) {
//                midi.addNote( starting_pitch + i*2, volume, 1*i + 1, 1, 0, 0 );
//            }

            midi.addNote( starting_pitch + 0, volume, 1, 1, 0, 0 );
            midi.addNote( starting_pitch + 2, volume, 2, 1, 0, 0 );
            midi.addNote( starting_pitch + 4, volume, 3, 1, 0, 0 );
            midi.addNote( starting_pitch + 5, volume, 4, 1, 0, 0 );
            midi.addNote( starting_pitch + 7, volume, 5, 1, 0, 0 );
            midi.addNote( starting_pitch + 9, volume, 6, 1, 0, 0 );
            midi.addNote( starting_pitch + 11, volume, 7, 1, 0, 0 );
            midi.addNote( starting_pitch + 12, volume, 8, 1, 0, 0 );


            midi.play();

        }


    }




}
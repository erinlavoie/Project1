/**
 * File: Main.java
 * Author: Erin Lavoie and Tiffany Lam
 * Course: CS361
 * Project: 1
 * Date: Sept. 15, 2016
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import java.util.Optional;

public class Main extends Application
{
    //fields
    /**
     * MidiPlayer object that plays and stops scales
     */
    private MidiPlayer midi;

    @Override
    public void start(Stage primaryStage) {

        // initializing the midi player
        this.midi = new MidiPlayer(5, 60);

        // buidling the GUI
        BorderPane pane = new BorderPane();
        MenuBar menu = new MenuBar();
        HBox buttons = new HBox();

        // File menu with one menu item "Exit"
        Menu file = new Menu("File");
        MenuItem exit = new MenuItem("Exit");

        // window disappears and the application quits when exit clicked
        exit.setOnAction(event -> System.exit(0));

        // adding exit to file and file to menu
        file.getItems().addAll(exit);
        menu.getMenus().addAll(file);

        // creating the play and stop button
        Button playButton = new Button();
        Button stopButton = new Button();

        // setting the text for the play and stop buttons
        playButton.setText("Play scale");
        stopButton.setText("Stop playing");

        // setting the style for the play and stop buttons
        playButton.setStyle("-fx-base: lightgreen;" + "-fx-border-color: black;"
                          + "-fx-background-radius: 5.0;" + "-fx-border-radius: 5.0");
        stopButton.setStyle("-fx-base: pink;" + "-fx-border-color: black;"
                          + "-fx-background-radius: 5.0;" + "-fx-border-radius: 5.0");

        // when playButton is clicked, call getStartingPitch to open dialog box and get starting pitch
        // when stopButton is clicked, call stopScale to stop the MidiPlayer from playing
        playButton.setOnAction(event -> getStartingPitch());
        stopButton.setOnAction(event -> stopScale());

        // styling the hbox (buttons) and adding the buttons to the hbox
        buttons.setSpacing(10.0);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(playButton, stopButton);

        // placing the menu and buttons on the pane
        pane.setTop(menu);
        pane.setCenter(buttons);

        // creating a new scene
        Scene scene = new Scene(pane, 300, 250);

        // formatting the primaryStage, closing program when primary stage is closed
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

    /**
     * Opens a Dialog to get starting pitch val
     *
     * User has two options, OK and Cancel. They should only be clicked once the
     * user has input their starting pitch val in the textbox. Cancel closes the
     * Dialog and OK calls playScale with the input int pitch value
     *
     */
    public void getStartingPitch() {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Starting note");
        dialog.setHeaderText("Give me a starting note (0-115): ");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            playScale( Integer.parseInt(result.get() ) );
        }

    }

    /**
     * Takes in an int starting pitch val, builds a major 8-note scale, and plays it.
     *
     * @param starting_pitch an integer value that determines the scale's starting point.
     */
    public void playScale(int starting_pitch) {

        // if there is a scale being played, stop it. Clear the "note cache"
        this.midi.stop();
        this.midi.clear();

        // Settings for Scale
        int volume = 60;
        int startTick = 0;

        // the pitch intervals for a major scale
        int[] major_scale = new int[]{0, 2, 2, 1, 2, 2, 2, 1, 0};

        // plays the scale going up in pitch
        for (int i = 0; i < major_scale.length; i++) {
            this.midi.addNote(starting_pitch += major_scale[i], volume, startTick += 1, 1, 0, 0);
        }

        // plays the scale going down in pitch
        for (int j = 7; j > 0; j--) {
            this.midi.addNote(starting_pitch -= major_scale[j], volume, startTick += 1, 1, 0, 0);
        }

        // plays scale
        this.midi.play();

    }

    /**
     *
     */
    public void stopScale() {
        this.midi.stop();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

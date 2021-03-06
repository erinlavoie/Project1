/**
 * File: Main.java
 * Author: Erin Lavoie and Tiffany Lam
 * Course: CS361
 * Project: 2
 * Date: Sept. 22, 2016
 */

package proj2LamLavoie;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

import javafx.fxml.FXMLLoader;

/**
 * <P>Runs a program with a JavaFX GUI that upon user input will
 * play an 8-note major scale. Uses MidiPlayer for audio.</P>
 *
 * @author Tiffany Lam, Erin Lavoie
 */
public class Main extends Application {
    //fields
    /**
     * MidiPlayer object that plays and stops scales
     */
    private MidiPlayer midi;


    @Override
    public void start(Stage primaryStage) throws Exception {

        // initializing the midi player
        this.midi = new MidiPlayer(5, 60);

        // layout for the GUI using FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
        fxmlLoader.setController(this);
        BorderPane pane = fxmlLoader.load();

        // creating a new scene
        Scene scene = new Scene(pane, 300, 250);

        // styling with CSS
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());

        // formatting the primaryStage
        primaryStage.setTitle("Scale Player");
        primaryStage.setScene(scene);

        primaryStage.show();

        // closing program when primary stage is closed
        primaryStage.setOnCloseRequest(event -> {
            exitWindow();
        });
    }

    /**
     * <P>Is called when the user clicks the playButton. Assigns the result of getStartingPitch to an
     * Optional, checks if there is an integer value. If there is an Integer val inside the Optional, it then
     * calls playScale with the Integer val. Otherwise it does nothing and returns. </P>
     */
    public void handlePlayButton() {

        // Assigns result of getStartingPitch to userInputPitch
        Optional<Integer> userInputPitch = getStartingPitch();

        // Check if an Integer values is present. If yet, calls playScale with UserInputPitch
        if (userInputPitch.isPresent()) {
            playScale(userInputPitch.get());
        }

        return;
    }

    /**
     * <P>Opens a Dialog to get starting pitch val</P>
     * <P>User has two options, OK and Cancel. They should only be clicked once the
     * user has input their starting pitch val in the textbox. Cancel closes the
     * Dialog and OK calls playScale with the input int pitch value. This program crashes
     * when the user inputs a non-Integer value. </P>
     *
     * @return Optional<Integer> this program will return an Optional Integer if the user has given an
     * input and will otherwise return an empty Optional.
     */

    public Optional<Integer> getStartingPitch() {

        // initializing the dialog
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Starting note");
        dialog.setHeaderText("Give me a starting note (0-115): ");

        // Displays the dialog. Will wait until user responds.
        // result will store the results
        Optional<String> result = dialog.showAndWait();

        // check if the user has given an input
        if (result.isPresent()) {
            //we assign starting_pitch to an Optional of result cast as an Integer
            Optional<Integer> starting_pitch = Optional.of(Integer.valueOf(result.get()));
            return starting_pitch;
        }
        // otherwise return an empty optional
        else {
            return Optional.empty();
        }


    }

    /**
     * <P>Takes in an int starting pitch val, builds a major 8-note scale, and plays it up and down.
     * The program crashes if the input value is less than 0 or greater than 115.
     * </P>
     *
     * @param starting_pitch an integer value
     */
    public void playScale(Integer starting_pitch) {

        // if there is a scale being played, stop it. Clear the "note cache"
        stopScale();

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
     * <P>Stops the MidiPlayer from playing the current scale and clears the note cache</P>
     */
    public void stopScale() {
        this.midi.stop();
        this.midi.clear();
    }

    /**
     * <P>Window disappears and the application quits when exit clicked</P>
     */
    public void exitWindow() {
        System.exit(0);
    }

    /**
     * <P>main method. calls launch method.</P>
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

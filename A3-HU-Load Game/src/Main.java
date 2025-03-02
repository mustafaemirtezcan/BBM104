import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * Main class for the game.
 */
public class Main extends Application {

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched from a browser.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("HU-Load"); // Set the title of the window.0
        Pane pane = new Pane(); // Create a new Pane layout.
        Scene scene = new Scene(pane, 900, 800); // Create a new Scene with the pane layout and a size of 900x800.
        GameOperations.startGame(pane, scene); // It starts the game with drill and map.
        primaryStage.setScene(scene); // Set the scene of the stage.
        primaryStage.centerOnScreen(); // Center the stage on the screen.
        primaryStage.show();
    }

    /**
     * The main method for the Java application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application.
    }
}
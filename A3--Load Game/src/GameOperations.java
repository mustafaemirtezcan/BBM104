import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Random;

/**
 * The GameOperations class contains methods for creating and managing the game screen,
 * generating random numbers, creating a drill info table, and handling game over scenarios.
 */
public class GameOperations {
    /**
     * This method is used to start the game.
     *
     * @param pane  This is the pane on which the game screen is drawn.
     * @param scene This is the scene that contains the game screen.
     */
    public static void startGame(Pane pane, Scene scene) {
        GameOperations.createGameScreen(pane, 18, 16); // Create the game screen with the specified number of blocks
        Label drillInfoTable = GameOperations.createDrillInfoTable(pane); // Create the drill info table and store the returned label.
        Drill drill = Drill.startTheDrill(drillInfoTable, pane); // Start the drill and store the returned Drill object.
        pane.getChildren().add(drill.getImageView()); // Add the drill's image view to the pane.
        drill.startGravity(Underground.listOfMap); // Start the gravity for the drill.
        drill.move(scene, Underground.listOfMap, pane); // Enable the drill to move.
    }

    /**
     * This method is used to create the game screen.
     *
     * @param pane                    This is the pane on which the game screen is shown.
     * @param numberOfHorizontalBlock This is the number of horizontal blocks.
     * @param numberOfVerticalBlock   This is the number of vertical blocks.
     */
    public static void createGameScreen(Pane pane, int numberOfHorizontalBlock, int numberOfVerticalBlock) {
        // Create a rectangle to represent the sky.
        Rectangle sky = new Rectangle();
        sky.setWidth(numberOfHorizontalBlock * 50);
        sky.setHeight(105);
        sky.setFill(Color.DARKBLUE);
        pane.getChildren().add(sky);

        // Create a rectangle to represent the earth crust.
        Rectangle earthCrust = new Rectangle();
        earthCrust.setWidth(numberOfHorizontalBlock * 50); // Set the width of the rectangle
        earthCrust.setHeight(695);// Set the height of the rectangle
        earthCrust.setY(105); // Set the position of the rectangle
        Color undergroundColor = Color.web("#a3522e");
        earthCrust.setFill(undergroundColor); // Set the color of the rectangle
        pane.getChildren().add(earthCrust);

        List<String> valuableElements = Underground.chooseListOfValuableElement(); // Generate a list of valuable elements in the game.
        List<Block> listOfMap = Underground.createListOfUnderground(valuableElements, numberOfHorizontalBlock, numberOfVerticalBlock); // // Create a list of blocks for the underground in order of placement.
        Underground.createUnderGround(numberOfHorizontalBlock, numberOfVerticalBlock, pane, listOfMap); // Create the visual representation of the underground

    }

    /**
     * This method is used to create a drill info table.
     *
     * @param pane This is the pane on which the drill info table is drawn.
     * @return Label This returns a label that represents the drill info table.
     */
    public static Label createDrillInfoTable(Pane pane) {
        // Initialize ınformation screen containing numerical information of the drill.
        Label label = new Label();
        label.setFont(Font.font(23));
        label.setTranslateY(0);
        label.setTextFill(Color.WHITE);
        pane.getChildren().add(label);
        return label;
    }

    /**
     * This method is used to create the game over screen with some properties.
     *
     * @param pane  This is the pane on which the game over screen is drawn.
     * @param color This is the color of the game over screen.
     */
    public static void gameOverScreen(Pane pane, Color color) {
        // Adjusting the properties of the end-of-game screen with GameOver text.
        pane.getChildren().clear();
        Rectangle gameOverScreen = new Rectangle();
        gameOverScreen.setWidth(900); // Set the width of the rectangle
        gameOverScreen.setHeight(800);
        gameOverScreen.setFill(color); // Set the color of the rectangle
        pane.getChildren().add(gameOverScreen);
        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.font(50));
        gameOverText.setFill(Color.WHITE);
        // Some special adjustments to center the screen.
        gameOverText.setTranslateX(pane.getWidth() / 2 - gameOverText.getLayoutBounds().getWidth() / 2);
        gameOverText.setTranslateY(pane.getHeight() / 2 - gameOverText.getLayoutBounds().getHeight() / 2);
        pane.getChildren().add(gameOverText);
    }

    /**
     * This method is used to handle the game over scenario when the game is won.
     *
     * @param pane  This is the pane on which the game over screen is drawn.
     * @param drill This is the drill object that contains the money collected during the game.
     */
    public static void gameWon(Pane pane, Drill drill) {
        gameOverScreen(pane, Color.DARKGREEN); // Create the game over screen with the green color.
        // Show off the loot obtained at the end of the game.
        Text collectedMoneyText = new Text("Collected Money: " + drill.getMoney());
        collectedMoneyText.setFont(Font.font(50));
        collectedMoneyText.setFill(Color.WHITE);
        // Some special adjustments to center the screen.
        collectedMoneyText.setTranslateX(pane.getWidth() / 2 - collectedMoneyText.getLayoutBounds().getWidth() / 2);
        collectedMoneyText.setTranslateY(pane.getHeight() / 2 - collectedMoneyText.getLayoutBounds().getHeight() / 2 + 67);
        pane.getChildren().add(collectedMoneyText);
    }

    /**
     * This method is used to handle the game over scenario when the game is lost.
     *
     * @param pane This is the pane on which the game over screen is drawn.
     */
    public static void gameLost(Pane pane) {
        gameOverScreen(pane, Color.DARKRED); // Create the game over screen with the red color.
    }

    /**
     * This method is used to generate a random number within a given range.
     *
     * @param lowerNumber This is the lower bound of the range.
     * @param upperNumber This is the upper bound of the range.
     * @return int This returns a random number within the given range.
     */
    public static int randomNumberGenerator(int lowerNumber, int upperNumber) {
        Random rand = new Random(); // Create a new Random object for generating random numbers.
        int lowerBound = lowerNumber;
        int upperBound = upperNumber;
        int randomNum = rand.nextInt(upperBound - lowerBound) + lowerBound; // Generate a random number within the range.
        return randomNum; // Return the generated random number.
    }

}

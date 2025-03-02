import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Drill class represents the drill in the game.
 * It contains fields for the position, fuel, haul, money, images and image view of the drill.
 */
public class Drill {
    private double x;
    private double y;
    private double fuel;
    private int haul;
    private int money;
    private ImageView imageView;
    private Image right;
    private Image left;
    private Image up;
    private Image down;
    private Timeline timeLineOfFuelSpending; // Timeline for fuel spending.

    /**
     * Constructor for the Drill class.
     *
     * @param x         the x-coordinate of the drill.
     * @param y         the y-coordinate of the drill.
     * @param fuel      the fuel of the drill.
     * @param haul      the haul of the drill.
     * @param money     the money of the drill.
     * @param imageView the image view of the drill.
     * @param right     the image of the drill facing right.
     * @param left      the image of the drill facing left.
     * @param up        the image of the drill facing up.
     * @param down      the image of the drill facing down.
     */
    public Drill(double x, double y, double fuel, int haul, int money, ImageView imageView, Image right, Image left, Image up, Image down) {
        this.x = x;
        this.y = y;
        this.fuel = fuel;
        this.haul = haul;
        this.money = money;
        this.imageView = imageView;
        this.right = right;
        this.left = left;
        this.up = up;
        this.down = down;
    }

    /**
     * Method to start the drill.
     *
     * @param drillInfoTable the label that displays the drill's quantitative information(amount of fuel,haul,money).
     * @param pane           the pane where the drill is displayed.
     * @return the drill.
     */
    public static Drill startTheDrill(Label drillInfoTable, Pane pane) {
        // Load the images for the drill facing different directions
        Image leftImageOfDrill = new Image("/assets/drill/drill_01.png");
        Image rightImageOfDrill = new Image("/assets/drill/drill_55.png");
        Image upImageOfDrill = new Image("/assets/drill/drill_30.png");
        Image downImageOfDrill = new Image("/assets/drill/drill_40.png");

        // Create an ImageView for the drill and set its size
        ImageView imageViewOfDrill = new ImageView(leftImageOfDrill);
        imageViewOfDrill.setFitWidth(65);
        imageViewOfDrill.setFitHeight(65);
        int amountOfFuel = 5000;// Initialize the drill's fuel
        // Create the drill
        Drill drill = new Drill(0, 0, amountOfFuel, 0, 0, imageViewOfDrill, rightImageOfDrill, leftImageOfDrill, upImageOfDrill, downImageOfDrill);
        drill.setX(700);
        drill.setY(0);
        drill.spendFuel(drillInfoTable, pane); // Start the fuel spending of the drill.
        return drill;
    }

    /**
     * Method to spend fuel.
     *
     * @param drillInfoTable the label that displays the drill's quantitative information(amount of fuel,haul,money).
     * @param pane           the pane where the drill is displayed.
     */
    public void spendFuel(Label drillInfoTable, Pane pane) {

        // Create a timeline  and event handler via lambda function for fuel spending.
        timeLineOfFuelSpending = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            if (this.fuel > 0) {
                this.fuel -= 0.0005; // Decrease the fuel
                // Update the drill information table
                drillInfoTable.setText(String.format("fuel:" + String.format("%.4f", this.getFuel()) + "\nhaul:" + this.getHaul() + "\nmoney:" + this.getMoney()));
            } else {
                // If there is no fuel left, finish the game.
                GameOperations.gameWon(pane, this);
                timeLineOfFuelSpending.stop(); // Stop the fuel spending.
            }
        }));
        timeLineOfFuelSpending.setCycleCount(Timeline.INDEFINITE); // Set the timeline to repeat indefinitely
        timeLineOfFuelSpending.play(); // Start the timeline
    }


    /**
     * Method to check if a move is valid.
     *
     * @param listOfMap the list of blocks in the map.
     * @param pane      the pane where the blocks are displayed.
     * @param direction the direction of the move.
     * @return true if the move is valid, false otherwise.
     */
    public Boolean validMove(ArrayList<Block> listOfMap, Pane pane, Image direction) {

        // Check if the drill is within the game area
        if (this.getX() < 0 || this.getX() > 850 || this.getY() < 0 || this.getY() > 750) {
            return false;
        }

        Image emptyBlock = new Image("/assets/underground/empty_15.png");
        for (Block block : listOfMap) { // The loop that returns all blocks in the map.
            double xPosition = block.getImageView().getTranslateX();
            double yPosition = block.getImageView().getTranslateY();
            // It checks the new position of the drill to be moved.
            if (xPosition == this.getX() && yPosition == this.getY()) {
                if (!block.getBlockName().equals("empty")) { // If the block is not empty and the drill is moving upwards, the move is invalid
                    if (direction.equals(up)) {
                        return false;
                    }
                }
                if (block instanceof ValuableElement) { //// If the block is a valuable element, increase the drill's money and haul
                    this.money += ((ValuableElement) block).getMonetaryValueOfElement();
                    this.haul += ((ValuableElement) block).getWeightOfElement();
                    block.getImageView().setImage(emptyBlock); //Replace the mined block with an empty block
                    listOfMap.set(listOfMap.indexOf(block), new Block(new ImageView(emptyBlock), "empty")); // Update the list of blocks with the new empty block.
                    this.imageView.setImage(direction);
                } else {
                    if (block.getBlockName().equals("obstacle")) { // If the block is an obstacle, the move is invalid.
                        return false;
                    } else if (block.getBlockName().equals("lava")) { // If the block is lava, the game lost.
                        GameOperations.gameLost(pane);
                    } else if (block.getBlockName().equals("empty")) // If the block is empty, the move is valid.
                    {
                        return true;
                    } else { // If the block is soil, replace the mined soil block with an empty block and update the image of the drill.
                        if (block.getBlockName().equals("soil") || block.getBlockName().equals("top soil")) {
                            block.getImageView().setImage(emptyBlock);
                            listOfMap.set(listOfMap.indexOf(block), new Block(new ImageView(emptyBlock), "empty"));
                            this.imageView.setImage(direction);
                        }
                        return true; // Its a valid move.
                    }
                }
            }
        }
        return true; // If there is no block, this move is valid because it flies.
    }


    /**
     * The method responsible for each movement of the drill based on keyboard input.
     *
     * @param scene     the scene where the drill is displayed.
     * @param listOfMap the list of blocks in the map.
     * @param pane      the pane and drill where the blocks are displayed.
     */
    public void move(Scene scene, ArrayList<Block> listOfMap, Pane pane) {
        // It performs the movement and necessary operations according to the values entered from the direction arrow keys on the keyboard.
        scene.setOnKeyPressed(event -> { // Event handler for key press via lambda function.
            switch (event.getCode()) {
                case UP:
                    this.imageView.setImage(up); // Set the image of the drill by direction.
                    this.setY(this.getY() - 50); // Move the drill up.
                    if (!this.validMove(listOfMap, pane, up)) { // If the move is not valid, move the drill back down.
                        this.setY(this.getY() + 50);
                        break;
                    }
                    this.setFuel(this.getFuel() - 100); // Decrease the fuel
                    break;
                case DOWN:
                    this.imageView.setImage(down);
                    this.setY(this.getY() + 50);
                    if (!this.validMove(listOfMap, pane, down)) {
                        this.setY(this.getY() - 50);
                        break;
                    }
                    this.setFuel(this.getFuel() - 100);
                    break;
                case LEFT:
                    this.imageView.setImage(left);
                    this.setX(this.getX() - 50);
                    if (!this.validMove(listOfMap, pane, left)) {
                        this.setX(this.getX() + 50);
                        break;
                    }
                    this.setFuel(this.getFuel() - 100);
                    break;
                case RIGHT:
                    this.imageView.setImage(right);
                    this.setX(this.getX() + 50);
                    if (!this.validMove(listOfMap, pane, right)) {
                        this.setX(this.getX() - 50);
                        break;
                    }
                    this.setFuel(this.getFuel() - 150);
                    break;
            }
        });


    }

    /**
     * Method to check if there is a block under the drill for gravity.
     *
     * @param listOfMap the list of blocks in the map.
     * @return true if above ground, otherwise false
     */
    public Boolean blockControl(ArrayList<Block> listOfMap) {
        for (Block block : listOfMap) { // The loop that returns all blocks in the map.
            // Check if there is a block directly under the drill
            if (this.getY() + 50 == block.getImageView().getTranslateY() && this.getX() == block.getImageView().getTranslateX() && !(block.getBlockName().equals("empty"))) {
                return false; // If there is a non-empty block under the drill, return false.
            }
        }
        return true; // If there is no block or an empty block under the drill, return true
    }

    /**
     * Method to apply gravity.
     *
     * @param listOfMap the list of blocks in the map.
     */
    public void gravity(ArrayList<Block> listOfMap) {
        if (this.blockControl(listOfMap)) {
            // If there is no block or an empty block under the drill, drill falls due to gravity.
            this.setY(this.getY() + 50);
        }
    }

    /**
     * Method to start gravity.
     *
     * @param listOfMap the list of blocks in the map.
     */
    public void startGravity(ArrayList<Block> listOfMap) {
        // Create a timeline to apply gravity at regular intervals with event handler via lambda function.
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(700), event -> gravity(listOfMap)));
        timeline.setCycleCount(Timeline.INDEFINITE); // Set the timeline to repeat indefinitely
        timeline.play(); // Start the timeline
    }

    // Getter and setter methods.

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        imageView.setTranslateX(x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        imageView.setTranslateY(y);

    }

    public ImageView getImageView() {
        return imageView;
    }


    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public int getHaul() {
        return haul;
    }

    public int getMoney() {
        return money;
    }

}

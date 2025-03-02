import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * The Underground class represents the underground environment in the game.
 * It is responsible for creating and managing the blocks that make up the underground.
 */
public class Underground {

    public static ArrayList<Block> listOfMap = new ArrayList<>();

    /**
     * This method is used to select a list of valuable elements randomly.
     *
     * @return ArrayList<String> This returns a list of valuable elements.
     */
    public static List<String> chooseListOfValuableElement() {
        // An array of image names representing valuable elements
        String[] imageNames = {"valuable_amazonite", "valuable_bronzium", "valuable_diamond", "valuable_einsteinium", "valuable_emerald", "valuable_goldium", "valuable_ironium", "valuable_platinum", "valuable_ruby", "valuable_silverium"};
        HashSet<String> setOfValuableElements = new HashSet<>(); // A HashSet to store the selected different valuable elements.
        // Keep adding random elements from the imageNames array to the set until it contains 3 unique elements.
        while (setOfValuableElements.size() < 3) {
            String randomElement = imageNames[GameOperations.randomNumberGenerator(0, imageNames.length)];
            setOfValuableElements.add(randomElement);
        }
        return new ArrayList<>(setOfValuableElements); // Return the set of selected 3 valuable elements as a list
    }

    /**
     * This method is used to create a list of blocks that make up the underground.
     *
     * @param valuableElements        This is the list of valuable elements.
     * @param numberOfHorizontalBlock This is the number of horizontal blocks.
     * @param numberOfVerticalBlock   This is the number of vertical blocks.
     * @return List<Block> This returns a list of blocks that make up the underground.
     */
    public static List<Block> createListOfUnderground(List<String> valuableElements, int numberOfHorizontalBlock, int numberOfVerticalBlock) {
        // Determine the number of soil blocks to be created.
        int numberOfSoil = GameOperations.randomNumberGenerator(numberOfHorizontalBlock * (numberOfVerticalBlock - 2) / 2 + 10, numberOfHorizontalBlock * (numberOfVerticalBlock - 2) / 2 + 40);
        for (int i = 0; i < numberOfSoil; i++) { // Create the soil block objects from Block class and add them to the listOfMap list.
            Image image = new Image("/assets/underground/soil_03.png");
            ImageView imageView = new ImageView(image); // An ImageView object to add the image to the pane.
            Block block = new Block(imageView, "soil");
            listOfMap.add(block);
        }

        // Determine the number of lava blocks to be created
        int numberOfLava = GameOperations.randomNumberGenerator(13, 20);
        for (int i = 0; i < numberOfLava; i++) { // Create the lava block objects from Block class and add them to the listOfMap list.
            Image image = new Image("/assets/underground/lava_03.png");
            ImageView imageView = new ImageView(image);
            Block block = new Block(imageView, "lava");
            listOfMap.add(block);
        }

        // Determine the number of obstacle blocks to be created
        int numberOfObstacle = GameOperations.randomNumberGenerator(1, 3);
        for (int i = 0; i < numberOfObstacle; i++) { // Create the obstacle block objects from Block class and add them to the listOfMap list.
            Image image = new Image("/assets/underground/obstacle_02.png");
            ImageView imageView = new ImageView(image);
            Block block = new Block(imageView, "obstacle");
            listOfMap.add(block);
        }

        // Ensure that each valuable element appears at least once.
        for (String obligatoryElement : valuableElements) {
            Image image = new Image("/assets/underground/" + obligatoryElement + ".png");
            ImageView imageView = new ImageView(image);
            ValuableElement valuableElement = new ValuableElement(imageView, obligatoryElement);
            listOfMap.add(valuableElement);
        }

        // Create the remaining blocks as valuable elements.
        for (int i = 0; i < 189 - numberOfLava - numberOfSoil - numberOfObstacle; i++) {
            // Determine which valuable element block to add to the list for underground of the game.
            String valuableElementName = valuableElements.get(GameOperations.randomNumberGenerator(0, 3));
            Image image = new Image("/assets/underground/" + valuableElementName + ".png");
            ImageView imageView = new ImageView(image);
            ValuableElement valuableElement = new ValuableElement(imageView, valuableElementName);
            listOfMap.add(valuableElement);
        }

        // Shuffle the list to randomize the block positions.
        Collections.shuffle(listOfMap);

        // Adding the top grassy soil blocks.
        for (int i = 0; i < numberOfHorizontalBlock; i++) {
            Image image = new Image("/assets/underground/top_01.png");
            ImageView imageView = new ImageView(image);
            Block block = new Block(imageView, "top soil");
            listOfMap.add(i, block);
        }

        // Adding the obstacle blocks at the borders and the bottom (around the underground).
        for (int j = 1; j < numberOfVerticalBlock - 2; j++) {
            for (int i = 0; i < numberOfHorizontalBlock; i++) {
                if (i == 0 || i == numberOfHorizontalBlock - 1 || j == numberOfVerticalBlock - 3) {
                    Image image = new Image("/assets/underground/obstacle_02.png");
                    ImageView imageView = new ImageView(image);
                    Block block = new Block(imageView, "obstacle");
                    listOfMap.add(j * numberOfHorizontalBlock + i, block);

                }
            }
        }
        return listOfMap; // Return the list of blocks in order of placement in the underground.
    }

    /**
     * This method is used to create the visual representation of the underground in the game.
     *
     * @param numberOfHorizontalBlock It is the number of horizontal blocks.
     * @param numberOfVerticalBlock   It is the number of vertical blocks.
     * @param pane                    It is the pane on which the underground is drawn.
     * @param listOfMap               It is a pane where the underground is visually shown.
     */
    public static void createUnderGround(int numberOfHorizontalBlock, int numberOfVerticalBlock, Pane pane, List<Block> listOfMap) {
        int index = 0; // Initialize the index for the list of blocks.
        for (int j = 2; j < numberOfVerticalBlock; j++) { // Loop through each row of blocks in the underground.
            for (int i = 0; i < numberOfHorizontalBlock; i++) { // Loop for each block in a row.
                // Set the X and Y coordinates of the block's image view for the underground of the game.
                listOfMap.get(index).getImageView().setTranslateX(50 * i); // Set the X coordinate of the image view of block.
                listOfMap.get(index).getImageView().setTranslateY(50 * j); // Set the Y coordinate of the image view of block.
                pane.getChildren().add(listOfMap.get(index).getImageView()); // Adding the block's image view to the pane.
                index++; // Increment the index for the next block in the list.
            }
        }
    }

}

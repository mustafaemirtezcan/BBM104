import javafx.scene.image.ImageView;

/**
 * ValuableElement class represents a valuable element in the game.
 * It extends the Block class and contains additional fields for the monetary value and haul of the element.
 */
public class ValuableElement extends Block {

    private int monetaryValueOfElement; // Monetary value of the element.
    private int weightOfElement; // Weight of the element.

    /**
     * Constructor for the ValuableElement class.
     *
     * @param imageView the ImageView that visually represents the block.
     * @param blockName the name of the block, which identifies its type.
     */
    public ValuableElement(ImageView imageView, String blockName) {
        super(imageView, blockName);
        this.setElementInfos(); // Set the information of the valuable element's numerical values.
    }

    /**
     * Method to set the information of the valuable element's numerical values based on its block name.
     */
    public void setElementInfos() {
        // Set the monetary value and weight of the element based on its block name
        switch (this.getBlockName()) {
            // Cases for each element type.
            case "valuable_amazonite":
                this.monetaryValueOfElement = 500000;
                this.weightOfElement = 120;
                break;
            case "valuable_bronzium":
                this.monetaryValueOfElement = 60;
                this.weightOfElement = 10;
                break;
            case "valuable_diamond":
                this.monetaryValueOfElement = 100000;
                this.weightOfElement = 100;
                break;
            case "valuable_einsteinium":
                this.monetaryValueOfElement = 2000;
                this.weightOfElement = 40;
                break;
            case "valuable_emerald":
                this.monetaryValueOfElement = 5000;
                this.weightOfElement = 60;
                break;
            case "valuable_goldium":
                this.monetaryValueOfElement = 250;
                this.weightOfElement = 20;
                break;
            case "valuable_ironium":
                this.monetaryValueOfElement = 30;
                this.weightOfElement = 10;
                break;
            case "valuable_platinum":
                this.monetaryValueOfElement = 750;
                this.weightOfElement = 30;
                break;
            case "valuable_ruby":
                this.monetaryValueOfElement = 20000;
                this.weightOfElement = 80;
                break;
            case "valuable_silverium":
                this.monetaryValueOfElement = 100;
                this.weightOfElement = 10;
        }
    }

    /**
     * Getter for the monetary value of the element.
     *
     * @return the monetary value of the element.
     */
    public int getMonetaryValueOfElement() {
        return monetaryValueOfElement;
    }

    /**
     * Getter for the weight of the element.
     *
     * @return the weight of the element.
     */
    public int getWeightOfElement() {
        return weightOfElement;
    }

}

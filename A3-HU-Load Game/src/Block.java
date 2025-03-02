import javafx.scene.image.ImageView;

/**
 * Block class represents a block in the game(soil,lava,obstacle,valuable element).
 * Contains imageView and BlockName variables to visually represent the block and identify the type of the block, respectively.
 */
public class Block {

    private ImageView imageView;
    private String blockName;

    /**
     * Constructor for the Block class.
     *
     * @param imageView the ImageView that visually represents the block.
     * @param blockName the name of the block, which identifies its type.
     */
    public Block(ImageView imageView, String blockName) {
        this.imageView = imageView;
        this.blockName = blockName;
    }

    /**
     * Getter for the imageView.
     *
     * @return the ImageView that visually represents the block.
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Getter for the blockName.
     *
     * @return the name of the block.
     */
    public String getBlockName() {
        return blockName;
    }

}

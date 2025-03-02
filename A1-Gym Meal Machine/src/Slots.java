public class Slots {
    //Attribute definitions.
    private String productName;
    private int amountOfProduct;
    private int productPrice;
    private double amountOfFat;
    private double amountOfCarbohydrates;
    private double amountOfProtein;
    private int amountOfCalorie;

    /**
     * Prepares machine slots for filling. It creates objects for each slot and returns an array of objects consisting of slots.
     *
     * @return Object array containing slots.
     */
    static Slots[] slotPreparation() {
        Slots[] slots = new Slots[24];
        for (int i = 0; i < slots.length; i++) {
            Slots slot = new Slots("___", 0);
            slots[i] = slot;
        }
        return slots;
    }

    /**
     * Checks whether the product is in stock. Returned value If true, it is in stock.
     *
     * @param slotInAction The slot on which the machine operates (checks).
     * @return Boolean value (true or false).
     */
    static boolean isItInStock(Slots slotInAction) {
        if (slotInAction.amountOfProduct == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Resets information about completely empty slots.
     *
     * @param slot Each object representing machine slots.
     */
    public static void resetSlotInfo(Slots slot) {
        slot.productName = "___";
        slot.amountOfCalorie = 0;
        slot.amountOfProtein = 0;
        slot.amountOfFat = 0;
        slot.amountOfCarbohydrates = 0;
        slot.productPrice = 0;
    }

    /**
     * It receives the input to fill the machine, organizes it and starts the filling.
     *
     * @param slots        Object array containing slots.
     * @param args         Command line argument to write machine output to file.
     * @param productInput Array containing the input lines required for machine filling.
     */
    public static void fillingTheSlots(String[] args, String[] productInput, Slots[] slots) {
        for (String productLine : productInput) { //Sends each product line to the fill function
            String[] productInfo = productLine.split("\\t");
            if (Slots.fill(slots, productInfo, args) == -1)  //It is a call to fill.
            {
                break;
            }
        }
    }

    /**
     * It fills the machine. Sets product information. Writes output messages and returns some values during filling.
     *
     * @param slots       Object array containing slots.
     * @param args        Command line argument to write machine output to file.
     * @param productInfo Information of the product to be placed.
     * @return Meaningful values of the result of the placement process (-1,0,1).
     */
    static byte fill(Slots[] slots, String[] productInfo, String[] args) {
        for (Slots slot : slots) {
            if (productInfo[0].equals(slot.productName)) { //Filling to a slot consisting of the same product.
                if (slot.amountOfProduct < 10) {
                    slot.amountOfProduct++;
                    return 1;
                }
            } else {
                if (slot.productName.equals("___")) { //Filling a product into an empty slot.
                    slot.productName = productInfo[0];
                    slot.amountOfProduct++;
                    Products.productInfoCalculator(slot, productInfo);
                    return 1;
                }
            }
        }
        for (Slots slot : slots) { //If filling could not be done.
            if (slot.amountOfProduct == 10) {
                continue;
            } else {
                FileOutput.writeToFile(args[2], "INFO: There is no available place to put " + productInfo[0], true, true);
                return 0;
            }
        }
        FileOutput.writeToFile(args[2], "INFO: There is no available place to put " + productInfo[0], true, true);
        FileOutput.writeToFile(args[2], "INFO: The machine is full!", true, true);// If the machine is fully loaded.
        return -1;
    }

    /**
     * Creates objects belonging to slots.
     *
     * @param productName     Name of the product in the slot.
     * @param amountOfProduct Amount of products in the slot.
     */
    public Slots(String productName, int amountOfProduct) {
        this.productName = productName;
        this.amountOfProduct = amountOfProduct;
    }

    /**
     * Gets the private value of the name of the product in the slot.
     *
     * @return Name of the product in the slot.
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the private value of the name of the product in the slot.
     *
     * @param productName Name of the product in the slot.
     */

    //Other getter and setter methods also have the same mission.
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getAmountOfProduct() {
        return amountOfProduct;
    }

    public void setAmountOfProduct(int amountOfProduct) {
        this.amountOfProduct = amountOfProduct;
    }

    public double getAmountOfProtein() {
        return amountOfProtein;
    }

    public void setAmountOfProtein(double amountOfProtein) {
        this.amountOfProtein = amountOfProtein;
    }

    public double getAmountOfCarbohydrates() {
        return amountOfCarbohydrates;
    }

    public void setAmountOfCarbohydrates(double amountOfCarbohydrates) {
        this.amountOfCarbohydrates = amountOfCarbohydrates;
    }

    public double getAmountOfFat() {
        return amountOfFat;
    }

    public void setAmountOfFat(double amountOfFat) {
        this.amountOfFat = amountOfFat;
    }

    public int getAmountOfCalorie() {
        return amountOfCalorie;
    }

    public void setAmountOfCalorie(int amountOfCalorie) {
        this.amountOfCalorie = amountOfCalorie;
    }
}

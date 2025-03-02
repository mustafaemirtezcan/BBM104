public class Products {
    /**
     * It calculates the information regarding the product content and assigns it to the slot attributes.
     *
     * @param slot        Each object representing machine slots.
     * @param productInfo Information of the placed product.
     */
    public static void productInfoCalculator(Slots slot, String[] productInfo) {
        String[] productContent = productInfo[2].split(" ");
        slot.setProductPrice(Integer.parseInt(productInfo[1])); //Assign product content values.
        slot.setAmountOfProtein(Double.parseDouble(productContent[0]));
        slot.setAmountOfCarbohydrates(Double.parseDouble(productContent[1]));
        slot.setAmountOfFat(Double.parseDouble(productContent[2]));
        slot.setAmountOfCalorie((int) Math.round(4 * slot.getAmountOfProtein() + 4 * slot.getAmountOfCarbohydrates() + 9 * slot.getAmountOfFat())); //It calculates calories and assigns them.
    }

}

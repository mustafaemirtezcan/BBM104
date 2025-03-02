public class PurchaseManager {
    /**
     * Makes the basic purchase. It produces the necessary output messages.
     *
     * @param totalMoney   The total amount of money loaded into the machine.
     * @param args         Command line argument to write machine output to file.
     * @param slotInAction The slot on which the machine operates (purchasing process).
     */
    public static void purchase(int totalMoney, Slots slotInAction, String[] args) {
        if (slotInAction.getProductPrice() > totalMoney) { //If money is enough.
            FileOutput.writeToFile(args[2], "INFO: Insufficient money, try again with more money.", true, true);
            changeWithoutPayment(args, totalMoney);
        } else {
            FileOutput.writeToFile(args[2], "PURCHASE: You have bought one " + slotInAction.getProductName(), true, true);
            changeAfterPayment(args, totalMoney, slotInAction);
            slotInAction.setAmountOfProduct(slotInAction.getAmountOfProduct() - 1);
            if (slotInAction.getAmountOfProduct() == 0) {
                Slots.resetSlotInfo(slotInAction); //If the slot is completely empty.
            }
        }
    }

    /**
     * Manages the purchase made with the slot number. It produces the necessary output messages.
     *
     * @param slots        Object array containing slots.
     * @param args         Command line argument to write machine output to file.
     * @param totalMoney   The total amount of money loaded into the machine.
     * @param enteredValue Meaningful value from the input. The slot number for this operation.
     */
    static void purchaseWithSlotNumber(int enteredValue, Slots[] slots, int totalMoney, String[] args) {
        if (enteredValue < slots.length) { //Checking the entered slot number value.
            Slots selectedSlot = slots[enteredValue];
            if (Slots.isItInStock(selectedSlot)) { //Stock control
                PurchaseManager.purchase(totalMoney, selectedSlot, args);
            } else {
                FileOutput.writeToFile(args[2], "INFO: This slot is empty, your money will be returned.", true, true);
                changeWithoutPayment(args, totalMoney);
            }
        } else {
            FileOutput.writeToFile(args[2], "INFO: Number cannot be accepted. Please try again with another number.", true, true);
            changeWithoutPayment(args, totalMoney);
        }
    }

    /**
     * Manages the purchase made with product content information. Returns meaningful values about the result of the operation (true or false).
     *
     * @param slots         Object array containing slots.
     * @param args          Command line argument to write machine output to file.
     * @param selectionType The type of product content value with which the purchase will be made.
     * @param totalMoney    The total amount of money loaded into the machine.
     * @param enteredValue  Meaningful value from the input. The value for the product content for this operation.
     */
    static boolean purchaseWithProductContent(String selectionType, Slots[] slots, int enteredValue, int totalMoney, String[] args) {
        for (Slots slot : slots) { //Slot control one by one.
            if (selectionType.equals("CARB")) {
                if (enteredValue - (slot.getAmountOfCarbohydrates()) >= -5 && enteredValue - (slot.getAmountOfCarbohydrates()) <= 5)  //Is there a value suitable for the entered value?
                {
                    if (Slots.isItInStock(slot)) {
                        PurchaseManager.purchase(totalMoney, slot, args); //If in stock.
                        return true; //The purchase is successful.
                    }
                }
            } else if (selectionType.equals("PROTEIN")) {
                if (enteredValue - (slot.getAmountOfProtein()) >= -5 && enteredValue - (slot.getAmountOfProtein()) <= 5) {
                    if (Slots.isItInStock(slot)) {
                        PurchaseManager.purchase(totalMoney, slot, args);
                        return true;
                    }
                }
            } else if (selectionType.equals("FAT")) {
                if (enteredValue - (slot.getAmountOfFat()) >= -5 && enteredValue - (slot.getAmountOfFat()) <= 5) {
                    if (Slots.isItInStock(slot)) {
                        PurchaseManager.purchase(totalMoney, slot, args);
                        return true;
                    }
                }
            } else { //If purchasing by amount of calorie is selected.
                if (enteredValue - (slot.getAmountOfCalorie()) >= -5 && enteredValue - (slot.getAmountOfCalorie()) <= 5) {
                    if (Slots.isItInStock(slot)) {
                        PurchaseManager.purchase(totalMoney, slot, args);
                        return true;
                    }
                }
            }
        }
        return false; //The purchase is unsuccessful.
    }

    /**
     * Manages all purchasing steps. It produces the necessary output messages.
     *
     * @param slots         Object array containing slots.
     * @param args          Command line argument to write machine output to file.
     * @param purchaseInput A String array of input information containing the products to be purchased.
     */
    public static void purchaseOperations(String[] args, Slots[] slots, String[] purchaseInput) {
        for (String purchaseLine : purchaseInput) {
            String[] purchaseDetail = purchaseLine.split("\\t"); //Details of each purchase.
            String paymentType = purchaseDetail[0];
            String loadedMoney = purchaseDetail[1];
            String selectionType = purchaseDetail[2];
            int enteredValue = Integer.parseInt(purchaseDetail[3]);
            FileOutput.writeToFile(args[2], "INPUT: " + paymentType + "\t" + loadedMoney + "\t" + selectionType + "\t" + enteredValue, true, true); //Purchase information text.
            int totalMoney = totalMoneyCalculator(args, purchaseDetail); //Value assignments for payment details.
            if (selectionType.equals("NUMBER")) { //Purchasing with slot number.
                purchaseWithSlotNumber(enteredValue, slots, totalMoney, args);
            } else { //Purchasing with product content information.
                if (PurchaseManager.purchaseWithProductContent(selectionType, slots, enteredValue, totalMoney, args)) {
                    continue;
                } else { //If the purchase does not go through.
                    FileOutput.writeToFile(args[2], "INFO: Product not found, your money will be returned.", true, true);
                    changeWithoutPayment(args, totalMoney);
                }
            }
        }
    }

    /**
     * Calculates the sum of each valid money from the input.
     *
     * @param args           Command line argument to write machine output to file.
     * @param purchaseDetail A String array containing details of each purchase.
     * @return The total amount of valid money loaded into the machine.
     */
    static int totalMoneyCalculator(String[] args, String[] purchaseDetail) {
        String[] loadedMoneyArray = purchaseDetail[1].split(" ");
        int totalMoney = 0;
        for (String eachMoney : loadedMoneyArray) {
            if (isItValidMoney(eachMoney)) {
                totalMoney += Integer.parseInt(eachMoney);
            } else {
                FileOutput.writeToFile(args[2], "INFO: The money attempted to be loaded is invalid.", true, true);
            }
        }
        return totalMoney;
    }

    /**
     * Checks whether the money attempted to be loaded is valid or not.
     *
     * @param eachMoney Each money attempted to be loaded.
     * @return Boolean value representing whether the money is valid or not (true or false).
     */
    static boolean isItValidMoney(String eachMoney) {
        String[] validMoneys = {"1", "5", "10", "20", "50", "100", "200"};
        for (String validMoney : validMoneys) {
            if (validMoney.equals(eachMoney)) {
                return true;
            }
        }
        return false;
    }

    /**
     * If the payment has not been made, it  writes the message to return the change.
     *
     * @param args       Command line argument to write machine output to file.
     * @param totalMoney A String array containing details of each purchase.
     * @return A meaningful number representing that the operation failed (-1).
     */
    static byte changeWithoutPayment(String[] args, int totalMoney) {
        FileOutput.writeToFile(args[2], "RETURN: Returning your change: " + totalMoney + " TL", true, true);
        return -1;
    }

    /**
     * If the payment has been made, it  writes the message to return the change.
     *
     * @param args         Command line argument to write machine output to file.
     * @param totalMoney   A String array containing details of each purchase.
     * @param slotInAction The slot on which the machine operates (purchasing process).
     * @return A meaningful number representing that the operation was successful (+1).
     */
    static byte changeAfterPayment(String[] args, int totalMoney, Slots slotInAction) {
        FileOutput.writeToFile(args[2], "RETURN: Returning your change: " + (totalMoney - slotInAction.getProductPrice()) + " TL", true, true);
        return +1;
    }
}

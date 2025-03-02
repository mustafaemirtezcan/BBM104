public class GmmInfo {
    /**
     * Writes the current status of the "GMM".
     *
     * @param slots Object array containing slots.
     * @param args  Command line argument to write machine output to file.
     */
    public static void machineInfo(String[] args, Slots[] slots) {
        FileOutput.writeToFile(args[2], "-----Gym Meal Machine-----", true, true);
        byte endLineTempNumber = 0; //It only takes values from 0 to 4. Therefore, the byte data type is suitable.
        //This loop is just to create a smooth output.
        for (Slots slot : slots) {
            FileOutput.writeToFile(args[2], slot.getProductName() + "(" + slot.getAmountOfCalorie() + ", " + slot.getAmountOfProduct() + ")___", true, false); //Slot information(product name,amount of calorie)
            endLineTempNumber++;
            if (endLineTempNumber == 4) {
                endLineTempNumber = 0;
                FileOutput.writeToFile(args[2], "", true, true);
            }
        }
        FileOutput.writeToFile(args[2], "----------", true, true);
    }

}

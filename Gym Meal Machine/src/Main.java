public class Main {
    public static void main(String[] args) {
        String[] productInput = FileInput.readFile(args[0], false, false); // Reads the file as it is without discarding or trimming anything and stores it in string array namely productInput.
        String[] purchaseInput = FileInput.readFile(args[1], false, false); // Reads the file as it is without discarding or trimming anything and stores it in string array namely purchaseInput.
        FileOutput.writeToFile(args[2], "", false, false); //For reinitializing the file, it is not necessary but good practice for making sure about there is no leftover.
        Slots[] slots = Slots.slotPreparation(); //Starts slot preparation.
        Slots.fillingTheSlots(args, productInput, slots); //Starts slot filling.
        GmmInfo.machineInfo(args, slots); //Writes machine information.
        PurchaseManager.purchaseOperations(args, slots, purchaseInput); //Starts purchasing and information operations.
        GmmInfo.machineInfo(args, slots); //Writes machine information.
    }
}
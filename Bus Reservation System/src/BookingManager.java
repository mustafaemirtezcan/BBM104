import java.util.*;
/**
 * This class manages the booking operations for buses.
 */
public class BookingManager {
    /**
     * A list of all buses in the system.
     */
    public static List<Buses> busesList = new ArrayList<Buses>();

    /**
     * This method controls the commands given to the system.
     *
     * @param args        The command line arguments.
     * @param command     The command to be executed.
     * @param commandText The text of the command.
     */
    public static void commandControl(String[] args,String command, String commandText) {
        String[] commandInfo = commandText.split("\t");
        if (command.equals("INIT_VOYAGE")) {
            busesList = initVoyage(args,commandInfo, busesList, commandText);
        } else if (command.equals("Z_REPORT")) {
            if (commandInfo.length != 1) {
                FileOutput.writeToFile(args[1], "ERROR: Erroneous usage of \"" + commandInfo[0] + "\" command!\n", true, false);
                return;
            }
            printZReport(args);
        } else if (command.equals("SELL_TICKET")) {
            if (commandInfo.length != 3) {
                FileOutput.writeToFile(args[1], "ERROR: Erroneous usage of \"" + commandInfo[0] + "\" command!\n", true, false);
                return;
            }
            salesOperations(args,commandInfo, commandText);
        } else if (command.equals("REFUND_TICKET")) {
            if (commandInfo.length != 3) {
                FileOutput.writeToFile(args[1], "ERROR: Erroneous usage of \"" + commandInfo[0] + "\" command!\n", true, false);
                return;
            }
            refundOperations(args,commandInfo, commandText);
        } else if (command.equals("CANCEL_VOYAGE")) {
            if (commandInfo.length != 2) {
                FileOutput.writeToFile(args[1], "ERROR: Erroneous usage of \"" + commandInfo[0] + "\" command!\n", true, false);
                return;
            }
            cancelOperations(args,commandInfo, commandText);
        } else if (command.equals("PRINT_VOYAGE")) {
            if (commandInfo.length != 2) {
                FileOutput.writeToFile(args[1], "ERROR: Erroneous usage of \"" + commandInfo[0] + "\" command!\n", true, false);
                return;
            }
            searchVoyage(args,Integer.parseInt(commandInfo[1]), busesList);
        } else {
            FileOutput.writeToFile(args[1], "ERROR: There is no command namely " + command + "!\n", true, false);
        }
    }

    /**
     * This method initializes a voyage.
     *
     * @param args        The command line arguments.
     * @param voyageInfo  The information about the voyage.
     * @param busesList   The list of buses.
     * @param commandText The text of the command.
     * @return The updated list of buses.
     */
    public static List<Buses> initVoyage(String[] args,String[] voyageInfo, List<Buses> busesList, String commandText) {
        String voyageType = voyageInfo[1];
        int busId = Integer.parseInt(voyageInfo[2]);
        String from = voyageInfo[3];
        String to = voyageInfo[4];
        int numberOfRows = Integer.parseInt(voyageInfo[5]);
        float price = Float.parseFloat(voyageInfo[6]);
        if (busId <= 0) {
            FileOutput.writeToFile(args[1], "ERROR: " + busId + " is not a positive integer, ID of a voyage must be a positive integer!\n", true, false);
            return busesList;
        }
        if (numberOfRows <= 0) {
            FileOutput.writeToFile(args[1], "ERROR: " + numberOfRows + " is not a positive integer, number of seat rows of a voyage must be a positive integer!\n", true, false);
            return busesList;
        }
        if (price <= 0) {
            FileOutput.writeToFile(args[1], "ERROR: " + (int) price + " is not a positive number, price must be a positive number!\n", true, false);
            return busesList;
        }
        for (Buses bus : busesList) {
            if (bus.getBusId() == busId) {
                FileOutput.writeToFile(args[1], "ERROR: There is already a voyage with ID of 10!\n", true, false);
                return busesList;
            }
        }
        if (voyageType.equals("Standard")) {
            int refundCut = Integer.parseInt(voyageInfo[7]);
            if ((refundCut < 0) || (refundCut > 100)) {
                FileOutput.writeToFile(args[1], "ERROR: " + refundCut + " is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!\n", true, false);
                return busesList;
            }
            Standard standardVoyage = new Standard(busId, from, to, numberOfRows, price, refundCut);
            busesList.add(standardVoyage);
            FileOutput.writeToFile(args[1], "Voyage " + busId + " was initialized as a standard (2+2) voyage from " + from + " to " + to + " with " + String.format(Locale.US, "%.2f", price) + " TL priced " + numberOfRows * 4 + " regular seats. Note that refunds will be " + refundCut + "% less than the paid amount.\n", true, false);
        } else if (voyageType.equals("Premium")) {
            int refundCut = Integer.parseInt(voyageInfo[7]);
            int premiumFee = Integer.parseInt(voyageInfo[8]);
            if ((refundCut < 0) || (refundCut > 100)) {
                FileOutput.writeToFile(args[1], "ERROR: " + refundCut + " is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!\n", true, false);
                return busesList;
            }
            if (premiumFee < 0) {
                FileOutput.writeToFile(args[1], "ERROR: " + premiumFee + " is not a non-negative integer, premium fee must be a non-negative integer!\n", true, false);
                return busesList;
            }
            Premium premiumVoyage = new Premium(busId, from, to, numberOfRows, price, refundCut, premiumFee);
            busesList.add(premiumVoyage);
            FileOutput.writeToFile(args[1], "Voyage " + busId + " was initialized as a premium (1+2) voyage from " + from + " to " + to + " with " + String.format(Locale.US, "%.2f", price) + " TL priced " + numberOfRows * 2 + " regular seats and " + String.format(Locale.US, "%.2f", (price + (price * premiumFee / 100))) + " TL priced " + numberOfRows + " premium seats. Note that refunds will be " + refundCut + "% less than the paid amount.\n", true, false);
        } else if (voyageType.equals("Minibus")) {
            Minibus minibusVoyage = new Minibus(busId, from, to, numberOfRows, price);
            busesList.add(minibusVoyage);
            FileOutput.writeToFile(args[1], "Voyage " + busId + " was initialized as a minibus (2) voyage from " + from + " to " + to + " with " + String.format(Locale.US, "%.2f", price) + " TL priced " + numberOfRows * 2 + " regular seats. Note that minibus tickets are not refundable.\n", true, false);
        } else {
            FileOutput.writeToFile(args[1], "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!\n", true, false);
        }
        return busesList;
    }

    /**
     * This method handles the sales operations.
     *
     * @param args        The command line arguments.
     * @param ticketInfo  The information about the ticket.
     * @param commandText The text of the command.
     */
    public static void salesOperations(String[] args,String[] ticketInfo, String commandText) {
        int busId = Integer.parseInt(ticketInfo[1]);
        Integer[] seatNumbers = seatArrayMaker(ticketInfo);
        for (Buses bus : busesList) {
            if (bus.getBusId() == busId) {
                for (int seatNumber : seatNumbers) {
                    if (seatNumber > bus.getSeats().length) {
                        FileOutput.writeToFile(args[1], "ERROR: There is no such a seat!\n", true, false);
                        return;
                    }
                    if ((seatNumber <= 0)) {
                        FileOutput.writeToFile(args[1], "ERROR: " + seatNumber + " is not a positive integer, seat number must be a positive integer!\n", true, false);
                        return;
                    }
                    if (!(bus.getSeats()[seatNumber - 1].equals("*"))) {
                        FileOutput.writeToFile(args[1], "ERROR: One or more seats already sold!\n", true, false);
                        return;
                    }
                }
                bus.sellTicket(seatNumbers);
                bus.printSellingInfo(args,seatNumbers);
                return;
            }
        }
        FileOutput.writeToFile(args[1], "ERROR: There is no voyage with ID of " + busId + "!\n", true, false);
    }

    /**
     * This method handles the refund operations.
     *
     * @param args        The command line arguments.
     * @param refundInfo  The information about the refund.
     * @param commandText The text of the command.
     */
    public static void refundOperations(String[] args,String[] refundInfo, String commandText) {
        int busId = Integer.parseInt(refundInfo[1]);
        Integer[] seatNumbers = seatArrayMaker(refundInfo);
        for (Buses bus : busesList) {
            if (bus.getBusId() == busId) {
                for (int seatNumber : seatNumbers) {
                    if (seatNumber <= 0) {
                        FileOutput.writeToFile(args[1], "ERROR: " + seatNumber + " is not a positive integer, seat number must be a positive integer!\n", true, false);
                        return;
                    }
                    if (seatNumber > bus.getSeats().length) {
                        FileOutput.writeToFile(args[1], "ERROR: There is no such a seat!\n", true, false);
                        return;
                    }
                    if (bus instanceof Minibus) {
                        bus.printRefundingInfo(args,seatNumbers);
                        return;
                    } else {
                        if (!(bus.getSeats()[seatNumber - 1].equals("X"))) {
                            FileOutput.writeToFile(args[1], "ERROR: One or more seats are already empty!\n", true, false);
                            return;
                        }
                    }
                }
                bus.refundTicket(seatNumbers);
                bus.printRefundingInfo(args,seatNumbers);
                return;
            }
        }
        FileOutput.writeToFile(args[1], "ERROR: There is no voyage with ID of " + busId + "!\n", true, false);
    }

    /**
     * This method handles the cancel operations.
     *
     * @param args        The command line arguments.
     * @param cancelInfo  The information about the cancellation.
     * @param commandText The text of the command.
     */
    public static void cancelOperations(String[] args,String[] cancelInfo, String commandText) {
        int busId = Integer.parseInt(cancelInfo[1]);
        if (busId <= 0) {
            FileOutput.writeToFile(args[1], "ERROR: " + busId + " is not a positive integer, ID of a voyage must be a positive integer!\n", true, false);
            return;
        }
        for (Buses bus : busesList) {
            if (bus.getBusId() == busId) {
                FileOutput.writeToFile(args[1], "Voyage " + busId + " was successfully cancelled!\n" + "Voyage details can be found below:\n", true, false);
                FileOutput.writeToFile(args[1], bus.writeVoyageInfo(args), true, false);
                bus.cancelVoyage();
                bus.printRevenue(args);
                busesList.remove(bus);
                return;
            }
        }
        FileOutput.writeToFile(args[1], "ERROR: There is no voyage with ID of " + busId + "!\n", true, false);
    }

    /**
     * This method searches for a voyage and write it.
     *
     * @param args      The command line arguments.
     * @param busId     The ID of the bus.
     * @param busesList The list of buses.
     */
    public static void searchVoyage(String[] args,int busId, List<Buses> busesList) {
        if (busId <= 0) {
            FileOutput.writeToFile(args[1], "ERROR: " + busId + " is not a positive integer, ID of a voyage must be a positive integer!\n", true, false);
            return;
        }
        for (Buses bus : busesList) {
            if (bus.getBusId() == busId) {
                FileOutput.writeToFile(args[1], bus.writeVoyageInfo(args), true, false);
                bus.printRevenue(args);
                return;
            }
        }
        FileOutput.writeToFile(args[1], "ERROR: There is no voyage with ID of " + busId + "!\n", true, false);
    }

    /**
     * This method prints the Z report.
     *
     * @param args The command line arguments.
     */
    public static void printZReport(String[] args) {
        FileOutput.writeToFile(args[1], "Z Report:\n" + "----------------\n", true, false);
        if (busesList.isEmpty()) {
            FileOutput.writeToFile(args[1], "No Voyages Available!\n", true, false);
            FileOutput.writeToFile(args[1], "----------------\n", true, false);
            return;
        }
        busesList.sort(Comparator.comparing(Buses::getBusId));
        for (int i = 0; i < busesList.size(); i++) {
            FileOutput.writeToFile(args[1], busesList.get(i).writeVoyageInfo(args), true, false);
            busesList.get(i).printRevenue(args);
            FileOutput.writeToFile(args[1], "----------------\n", true, false);
        }
    }

    /**
     * This method prints the last Z report.
     *
     * @param args The command line arguments.
     */
    public static void printLastZReport(String[] args) {
        FileOutput.writeToFile(args[1], "Z Report:\n" + "----------------\n", true, false);
        if (busesList.isEmpty()) {
            FileOutput.writeToFile(args[1], "No Voyages Available!\n", true, false);
            FileOutput.writeToFile(args[1], "----------------", true, false);
            return;
        }
        busesList.sort(Comparator.comparing(Buses::getBusId));
        for (int i = 0; i < busesList.size() - 1; i++) {
            FileOutput.writeToFile(args[1], busesList.get(i).writeVoyageInfo(args), true, false);
            busesList.get(i).printRevenue(args);
            FileOutput.writeToFile(args[1], "----------------\n", true, false);
        }
        FileOutput.writeToFile(args[1], busesList.get(busesList.size()-1).writeVoyageInfo(args), true, false);
        busesList.get(busesList.size()-1).printRevenue(args);
        FileOutput.writeToFile(args[1], "----------------", true, false);
    }

    /**
     * This method creates an array of seat numbers.
     *
     * @param ticketInfo The information about the ticket.
     * @return An array of seat numbers.
     */
    public static Integer[] seatArrayMaker(String[] ticketInfo) {
        Integer[] seatNumbers = new Integer[ticketInfo[2].split("_").length];
        int index = 0;
        for (String seat : ticketInfo[2].split("_")) {
            seatNumbers[index] = Integer.parseInt(seat);
            index++;
        }
        return seatNumbers;
    }



}

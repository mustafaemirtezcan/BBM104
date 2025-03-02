import java.util.Arrays;

public class Minibus extends Buses {

    /**
     * Constructor for Minibus class.
     * @param busID ID of the bus.
     * @param from Starting location of the bus.
     * @param to Destination of the bus.
     * @param numberOfRows Number of rows in the bus.
     * @param price Price of the bus ticket.
     */
    public Minibus(int busID, String from, String to, int numberOfRows, float price) {
        super(busID, from, to, numberOfRows, price);
        this.setSeats(new String[numberOfRows*2]);
        Arrays.fill(getSeats(), "*");
    }
    /**
     * Method to print the refunding information.
     * @param args Arguments passed to the method.
     * @param seatNumbers Array of seat numbers for which the information is to be printed.
     */
    public void printRefundingInfo(String[] args,Integer[] seatNumbers){
        FileOutput.writeToFile(args[1],"ERROR: Minibus tickets are not refundable!\n",true,false);
    }}

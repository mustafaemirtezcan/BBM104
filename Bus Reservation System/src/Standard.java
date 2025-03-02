import java.util.Arrays;
import java.util.Locale;

public class Standard extends Buses{
    private int refundCut;

    /**
     * Constructor for Standard class.
     * @param busID ID of the bus.
     * @param from Starting location of the bus.
     * @param to Destination of the bus.
     * @param numberOfRows Number of rows in the bus.
     * @param price Price of the bus ticket.
     * @param refundCut Percentage of refund cut.
     */
    public Standard(int busID, String from, String to, int numberOfRows, float price, int refundCut) {
        super(busID, from, to, numberOfRows, price);
        this.refundCut = refundCut;
        this.setSeats(new String[numberOfRows*4]);
        Arrays.fill(getSeats(), "*");
    }

    /**
     * Method to get the status of the seats.
     * @param args Arguments passed to the method.
     * @return Empty string.
     */
    public String seatStatus(String[] args){
        int seatPerRow=4;
        for (String seat : getSeats()) {
            if (seatPerRow==1){
                FileOutput.writeToFile(args[1],seat,true,true);
                seatPerRow=4;
                continue;
            }
            FileOutput.writeToFile(args[1],seat+" ",true,false);
            seatPerRow--;
            if (seatPerRow==2) {
                FileOutput.writeToFile(args[1],"|" + " ",true,false);
            }
        }
        return "";
    }

    /**
     * Method to refund the ticket.
     * @param seatNumbers Array of seat numbers for which the ticket is to be refunded.
     */
    public void refundTicket(Integer[] seatNumbers){
        for (int seatNumber:seatNumbers){
            getSeats()[seatNumber-1]="*";
            this.setRevenue(getRevenue()-getPrice()*(100-refundCut)/100);
        }
    }

    /**
     * Method to print the refunding information.
     * @param args Arguments passed to the method.
     * @param seatNumbers Array of seat numbers for which the information is to be printed.
     */
    public void printRefundingInfo(String[] args,Integer[] seatNumbers){
        FileOutput.writeToFile(args[1],"Seat ",true,false);
        for(int seatNumber:seatNumbers){
            if(seatNumber!=seatNumbers[seatNumbers.length-1]){
                FileOutput.writeToFile(args[1],seatNumber+"-",true,false);
            }
            else{
                FileOutput.writeToFile(args[1],String.valueOf(seatNumber),true,false);
            }
        }
        FileOutput.writeToFile(args[1]," of the Voyage "+getBusId()+" from "+getFrom()+" to "+getTo()+" was successfully refunded for "+String.format(Locale.US,"%.2f",(getPrice()*(100-refundCut)/100)*seatNumbers.length)+" TL.\n",true,false);
    }

}

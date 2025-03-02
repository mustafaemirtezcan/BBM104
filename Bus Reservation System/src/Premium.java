import java.util.Arrays;
import java.util.Locale;

/**
 * Premium class extends Buses class and represents a premium bus.
 */
public class Premium extends Buses{
    private int refundCut;
    private int premiumFee;

    /**
     * Constructor for Premium class.
     * @param busID ID of the bus.
     * @param from Starting location of the bus.
     * @param to Destination of the bus.
     * @param numberOfRows Number of rows in the bus.
     * @param price Price of the bus ticket.
     * @param refundCut Percentage of refund cut.
     * @param premiumFee Additional fee for premium seats.
     */
    public Premium(int busID, String from, String to, int numberOfRows, float price, int refundCut, int premiumFee) {
        super(busID, from, to, numberOfRows, price);
        this.refundCut = refundCut;
        this.premiumFee = premiumFee;
        this.setSeats(new String[numberOfRows*3]);
        Arrays.fill(getSeats(), "*");
    }

    /**
     * Method to get the status of the seats.
     * @param args Arguments passed to the method.
     * @return Empty string.
     */
    public String seatStatus(String[] args){
        int seatPerRow=3;
        for (String seat : getSeats()) {
            if (seatPerRow==1){
                FileOutput.writeToFile(args[1],seat+"\n",true,false);
                seatPerRow=3;
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
     * Method to process the payment for a seat.
     * @param seatNumber Seat number for which the payment is made.
     */
    public void payment(int seatNumber){
        if(seatNumber%3==1){
            this.setRevenue(getRevenue()+(getPrice()+getPrice()*premiumFee/100));
        }
        else{
            this.setRevenue(getRevenue()+getPrice());
        }
    }

    /**
     * Method to cancel the voyage.
     */
    public void cancelVoyage(){
        for(int i=0;i<getSeats().length;i++){
            if(getSeats()[i].equals("X")){
                if(i%3==0){
                    this.setRevenue(getRevenue()-(getPrice()+getPrice()*premiumFee/100));
                }
                else{
                    this.setRevenue(getRevenue()-getPrice());
                }
                getSeats()[i]="*";
            }
        }
    }


    /**
     * Method to refund the ticket.
     * @param seatNumbers Array of seat numbers for which the ticket is to be refunded.
     */
    public void refundTicket(Integer[] seatNumbers){
        for(int seatNumber:seatNumbers){
            getSeats()[seatNumber-1]="*";
            if(seatNumber%3==1){
                this.setRevenue(getRevenue()-((getPrice()+getPrice()*premiumFee/100)*(100-refundCut)/100));
            }
            else{
                this.setRevenue(getRevenue()-getPrice()*(100-refundCut)/100);}
        }
    }

    /**
     * Method to print the selling information.
     * @param args Arguments passed to the method.
     * @param seatNumbers Array of seat numbers for which the information is to be printed.
     */
    public void printSellingInfo(String[] args,Integer[] seatNumbers){
        float sellAmount=0;
        FileOutput.writeToFile(args[1],"Seat ",true,false);
        for(int seatNumber:seatNumbers){
            if (seatNumber%3==1){
                sellAmount+=(getPrice()+getPrice()*premiumFee/100);
            }
            else{
                sellAmount+=getPrice();
            }
            if(seatNumber!=seatNumbers[seatNumbers.length-1]){
                FileOutput.writeToFile(args[1],seatNumber+"-",true,false);
            }
            else{
                FileOutput.writeToFile(args[1],String.valueOf(seatNumber),true,false);
            }
        }
        FileOutput.writeToFile(args[1]," of the Voyage "+getBusId()+" from "+getFrom()+" to "+getTo()+" was successfully sold for "+String.format(Locale.US,"%.2f",sellAmount)+" TL.\n",true,false);
    }

    /**
     * Method to print the refunding information.
     * @param args Arguments passed to the method.
     * @param seatNumbers Array of seat numbers for which the information is to be printed.
     */
    public void printRefundingInfo(String[] args,Integer[] seatNumbers){
        float refundAmount=0;
        FileOutput.writeToFile(args[1],"Seat ",true,false);
        for(int seatNumber:seatNumbers){
            if (seatNumber%3==1){
                refundAmount+=(getPrice()+getPrice()*premiumFee/100)*(100-refundCut)/100;
            }
            else{
                refundAmount+=getPrice()*(100-refundCut)/100;
            }
            if(seatNumber!=seatNumbers[seatNumbers.length-1]){
                FileOutput.writeToFile(args[1],seatNumber+"-",true,false);
            }
            else{
                FileOutput.writeToFile(args[1],String.valueOf(seatNumber),true,false);
            }
        }
        FileOutput.writeToFile(args[1]," of the Voyage "+getBusId()+" from "+getFrom()+" to "+getTo()+" was successfully refunded for "+String.format(Locale.US,"%.2f",refundAmount)+" TL.\n",true,false);
    }
}

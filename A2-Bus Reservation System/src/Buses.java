import java.util.Locale;

/**
 * Abstract class Buses implementing BookingOperations interface.
 * This class represents a bus with its properties and operations.
 */
public abstract class Buses implements BookingOperations {
    private int busId;
    private String from;
    private String to;
    private int numberOfRows;
    private float price;
    private String[] seats;
    private float revenue;

    /**
     * Constructor for Buses class.
     * @param busId ID of the bus.
     * @param from Starting location of the bus.
     * @param to Destination of the bus.
     * @param numberOfRows Number of rows in the bus.
     * @param price Price of the bus ticket.
     */
    public Buses(int busId, String from, String to, int numberOfRows, float price) {
        this.busId = busId;
        this.from = from;
        this.to = to;
        this.numberOfRows = numberOfRows;
        this.price = price;
    }

    // Getters and Setters for the class attributes.

    public int getBusId() {
        return busId;
    }

    public void setBusID(int busID) {
        this.busId = busID;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String[] getSeats() {
        return seats;
    }

    public void setSeats(String[] seats) {
        this.seats = seats;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    /**
     * Method to sell tickets.
     * It marks the seats as sold and makes the payment.
     */
    public void sellTicket(Integer[] seatNumbers) {
        for (int seatNumber:seatNumbers){
            getSeats()[seatNumber-1]="X";
            payment(seatNumber);
        }
    }

    /**
     * Method to make payment.
     * It increases the revenue by the price of the ticket.
     */
    public void payment(int seatNumber) {
        revenue += price;
    }

    /**
     * Abstract method to refund ticket.
     */
    public void refundTicket(Integer[] seatNumber){}

    /**
     * Method to cancel voyage.
     * It refunds the tickets and marks the seats as available.
     */
    public void cancelVoyage() {
        for (int i = 0; i < getSeats().length; i++) {
            if (getSeats()[i].equals("X")) {
                this.setRevenue(getRevenue() - getPrice());
                getSeats()[i] = "*";
            }
        }
    }

    /**
     * Method to get the status of the seats.
     */
    public String seatStatus(String[] args) {
        int seatPerRow = 2;
        for (String seat : getSeats()) {
            if (seatPerRow == 1) {
                FileOutput.writeToFile(args[1],seat + "\n",true, false);
                seatPerRow = 2;
                continue;
            }
            FileOutput.writeToFile(args[1],seat + " ",true, false);
            seatPerRow--;
        }
        return "";
    }

    /**
     * Method to write voyage information to a file.
     */
    public String writeVoyageInfo(String[] args) {
        FileOutput.writeToFile(args[1],"Voyage " + busId + "\n" + from + "-" + to + "\n",true,false);
        seatStatus(args);
        return "";
    }

    /**
     * Method to print the revenue to a file.
     */
    public void printRevenue(String[] args){
        FileOutput.writeToFile(args[1],"Revenue: " + String.format(Locale.US,"%.2f", revenue)+"\n",true,false);
    }

    /**
     * Method to print the selling information to a file.
     */
    public void printSellingInfo(String[] args,Integer[] seatNumbers){
        FileOutput.writeToFile(args[1],"Seat ",true,false);
        for(int i=0;i<seatNumbers.length-1;i++) {
            FileOutput.writeToFile(args[1],seatNumbers[i]+"-",true,false);
        }
        FileOutput.writeToFile(args[1],String.valueOf(seatNumbers[seatNumbers.length-1]),true,false);
        FileOutput.writeToFile(args[1]," of the Voyage "+busId+" from "+from+" to "+to+" was successfully sold for "+String.format(Locale.US,"%.2f",price*seatNumbers.length)+" TL.\n",true,false);
    }
    /**
     * Abstract method to print refunding information.
     */
    public abstract void printRefundingInfo(String[] args,Integer[] seatNumbers);
}

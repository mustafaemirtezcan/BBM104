/**
 * This interface defines the operations that can be performed on a booking.
 */
public interface BookingOperations {

    /**
     * Returns the status of a seat.
     *
     * @param args The arguments used to determine the seat status.
     * @return The status of the seat.
     */
    public String seatStatus(String[] args);

    /**
     * Sells a ticket for the given seat numbers.
     *
     * @param seatNumbers The seat numbers for which to sell the ticket.
     */
    public void sellTicket(Integer[] seatNumbers);

    /**
     * Processes the payment for a seat.
     *
     * @param seatNumber The number of the seat for which to process the payment.
     */
    public void payment(int seatNumber);

    /**
     * Refunds the ticket for a seat.
     *
     * @param seatNumber The number of the seat for which to refund the ticket.
     */
    public void refundTicket(Integer[] seatNumber);
}

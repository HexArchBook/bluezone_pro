package io.github.hexarchbook.bluezone.app.ports.driving.foradministering;

import io.github.hexarchbook.bluezone.app.ports.Ticket;
import io.github.hexarchbook.bluezone.app.ports.Rate;
import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.PayRequest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DRIVING PORT (Provided Interface)
 * For doing administration tasks like initializing, load data in the repositories,
 * configuring the services used by the app, etc.
 * Typically, it is used by:
 *      - Tests (driving actors) for setting up the test-fixture (driven actors).
 *      - The start-up for initializing the app.
 */
public interface ForAdministering {

    /**
     * Load the given rates into the data repository,
     * deleting previously existing rates if any.
     */
    public void initializeRates(List<Rate> newRates);

    /**
     * Load the given tickets into the data repository,
     * deleting previously existing tickets if any.
     */
    public void initializeTickets(List<Ticket> newTickets);

    /**
     * Make the given ticket code the next to be returned when asking for it.
     */
    public void changeNextTicketCode(String newNextTicketCode);

    /**
     * Return the ticket stored in the repository with the given code
     */
    public Ticket getStoredTicket(String ticketCode);

    /**
     * Return the last request done to the "pay" method
     */
    public PayRequest getLastPayRequest();

    /**
     * Return the last response returned by the "pay" method.
     * It is an identifier of the payment made.
     */
    public String getLastPayResponse();

    /**
     * Make the probability of a payment error the "percentage" given as a parameter
     */
    public void setPaymentErrorPercent(int percentage);

    /**
     * Return the code of the error that occurred when running the "pay" method
     */
    public String getErrorCodeOccurred();

    /**
     * Set the given date-time as the current date-time
     */
    public void changeCurrentDateTime(LocalDateTime newCurrentDateTime);

}

package io.github.hexarchbook.bluezone.app.ports.driven.forstoringdata;

import io.github.hexarchbook.bluezone.app.ports.Rate;
import io.github.hexarchbook.bluezone.app.ports.Ticket;
import java.util.List;
import java.util.Set;

/**
 * DRIVEN PORT (Required Interface)
 * Port to interact with a repository, where the app stores and retrieves rates and tickets.
 * The repository also manages a sequence to get ticket codes incrementally.
 */
public interface ForStoringData {

    /**
     * Store the given rates in the repository, deleting previously the existing ones if any
     */
    public void loadInitialRates(List<Rate> newRates);

    /**
     * Return all the rates stored in the repository
     */
    public Set<Rate> getAllRates();

    /**
     * Return the rate with the given name.
     */
    public Rate getRateByName(String rateName);

    /**
     * Store the given tickets in the repository, deleting previously the existing ones if any
     */
    public void loadInitialTickets(List<Ticket> newTickets);

    /**
     * Store the given ticket in the repository.
     * If another ticket with the same code exists in the repository already,
     * it is overwriting by the given ticket.
     */
    public void saveTicket(Ticket ticket);

    /**
     * Return the ticket with the given code
     */
    public Ticket getTicketByCode(String ticketCode);

    /**
     * Return the stored tickets to park the given car at zones with the given rate.
     */
    public Set<Ticket> getTicketsByCarAndRate(String carPlate, String rateName);

    /**
     * Returns and increments, atomically, the next value of the ticket code sequence.
     */
    public String nextTicketCode();

    /**
     * Configure the given ticket code as the next one to be returned by the sequence.
     */
    public void setNextTicketCode(String newNextTicketCode);

}

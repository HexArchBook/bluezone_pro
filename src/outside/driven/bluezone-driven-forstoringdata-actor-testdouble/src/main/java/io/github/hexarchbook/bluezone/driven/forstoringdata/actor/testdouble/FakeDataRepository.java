package io.github.hexarchbook.bluezone.driven.forstoringdata.actor.testdouble;

import io.github.hexarchbook.bluezone.app.ports.driven.forstoringdata.ForStoringData;
import io.github.hexarchbook.bluezone.app.ports.Rate;
import io.github.hexarchbook.bluezone.app.ports.Ticket;
import io.github.hexarchbook.bluezone.lib.hexagonal.DrivenActor;
import io.github.hexarchbook.bluezone.lib.javautils.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Driven actor that implements "forstoringdata" port with a "fake" (in-memory db) test-double.
 */
@DrivenActor(name="test-double")
public class FakeDataRepository implements ForStoringData {

    private static final int MAX_TICKET_CODE_LENGTH = 10;

    private Map<String, Rate> ratesByName;
    private Map<String, Ticket> ticketsByCode;
	private AtomicLong nextTicketNumber;

    public FakeDataRepository() {
        this.ratesByName = new HashMap<String,Rate>();
        this.ticketsByCode = new HashMap<String,Ticket>();
        this.nextTicketNumber = new AtomicLong(1);
    }

    @Override
    public void loadInitialRates(List<Rate> newRates) {
        this.ratesByName = new HashMap<String,Rate>();
        if ( newRates==null || newRates.isEmpty() ) {
            return;
        }
        for ( Rate rate : newRates ) {
            this.ratesByName.put(rate.getName(),rate);
        }
    }

    @Override
    public Set<Rate> getAllRates() {
        Set<Rate> rates = new HashSet<Rate>();
        if ( this.ratesByName.isEmpty() ) {
            return rates;
        }
        Iterator<String> rateNameIterator = this.ratesByName.keySet().iterator();
        while ( rateNameIterator.hasNext() ) {
            String rateName = rateNameIterator.next();
            Rate rate = this.ratesByName.get(rateName);
            rates.add(rate);
        }
        return rates;
    }

    @Override
    public Rate getRateByName(String rateName) {
        return this.ratesByName.get(rateName);
    }

    @Override
    public void loadInitialTickets(List<Ticket> newTickets) {
        this.ticketsByCode = new HashMap<String,Ticket>();
        if ( newTickets==null || newTickets.isEmpty() ) {
            return;
        }
        for ( Ticket ticket : newTickets ) {
            this.ticketsByCode.put(ticket.getTicketCode(),ticket);
        }
    }

    @Override
    public void saveTicket(Ticket ticket) {
        this.ticketsByCode.put(ticket.getTicketCode(),ticket);
    }

    @Override
    public Ticket getTicketByCode(String ticketCode) {
        return this.ticketsByCode.get(ticketCode);
    }

    @Override
    public Set<Ticket> getTicketsByCarAndRate(String carPlate, String rateName) {
        Set<Ticket> tickets = new HashSet<Ticket>();
        if ( this.ticketsByCode.isEmpty() ) {
            return tickets;
        }
        Iterator<String> ticketCodeIterator = this.ticketsByCode.keySet().iterator();
        while ( ticketCodeIterator.hasNext() ) {
            String ticketCode = ticketCodeIterator.next();
            Ticket ticket = this.ticketsByCode.get(ticketCode);
            if ( Objects.equals(ticket.getCarPlate(),carPlate) && Objects.equals(ticket.getRateName(),rateName) ) {
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    @Override
    public String nextTicketCode() {
        long ticketNumber = this.nextTicketNumber.getAndIncrement();
        String ticketCode = "" + ticketNumber;
        if ( ticketCode.length() > MAX_TICKET_CODE_LENGTH ) {
            throw new RuntimeException("Ticket code overflow");
        }
        return StringUtils.leftPad(ticketCode,MAX_TICKET_CODE_LENGTH,'0');
    }

    @Override
    public void setNextTicketCode(String newNextTicketCode) {
        if ( newNextTicketCode==null ) {
            throw new RuntimeException("New next ticket code cannot be null");
        }
        if ( newNextTicketCode.length() > MAX_TICKET_CODE_LENGTH ) {
            throw new RuntimeException("Ticket code overflow");
        }
        long newNextTicketNumber = Long.parseLong(newNextTicketCode);
        this.nextTicketNumber = new AtomicLong( newNextTicketNumber );
    }

}

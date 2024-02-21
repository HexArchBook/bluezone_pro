package io.github.hexarchbook.bluezone.app.usecases;

import io.github.hexarchbook.bluezone.app.ports.Rate;
import io.github.hexarchbook.bluezone.app.ports.Ticket;
import io.github.hexarchbook.bluezone.app.ports.driven.forobtainingdatetime.ForObtainingDateTime;
import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.ForPaying;
import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.PayRequest;
import io.github.hexarchbook.bluezone.app.ports.driven.forstoringdata.ForStoringData;
import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.BuyTicketRequest;
import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.BuyTicketRequestException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class BuyTicket implements Function<BuyTicketRequest, Ticket> {

    private static final BigDecimal MINUTES_PER_HOUR = new BigDecimal("60.00");

    private final ForStoringData dataRepository;
    private final ForPaying paymentService;
    private final ForObtainingDateTime dateTimeService;

    public BuyTicket ( ForStoringData dataRepository, ForPaying paymentService, ForObtainingDateTime dateTimeService ) {
        this.dataRepository = dataRepository;
        this.paymentService = paymentService;
        this.dateTimeService = dateTimeService;
    }

    @Override
    public Ticket apply(BuyTicketRequest request) {
        LocalDateTime currentDateTime = this.dateTimeService.getCurrentDateTime();
        // Current date-time is needed to validate the card expiration
        List<String> requestErrors = request.validate(currentDateTime);
        if ( ! requestErrors.isEmpty() ) {
            throw new BuyTicketRequestException(requestErrors);
        }
        BigDecimal eurosToPay = new BigDecimal ( request.getEuros() );
        String paymentCard = request.getCard();
        PayRequest payRequest = new PayRequest(eurosToPay,paymentCard);
        // Pay for the ticket
        String paymentId = this.paymentService.pay(payRequest);
        // Build the ticket
        String ticketCode = this.dataRepository.nextTicketCode();
        String carPlate = request.getCarPlate();
        String rateName = request.getRateName();
        Rate rate = this.dataRepository.getRateByName(rateName);
        LocalDateTime endingDateTime = calculateEndingDateTime(currentDateTime,eurosToPay,rate.getEurosPerHour());
        Ticket ticket = new Ticket(ticketCode,carPlate,rateName,currentDateTime,endingDateTime,eurosToPay,paymentId);
        // Store the ticket
        this.dataRepository.saveTicket(ticket);
        return ticket;
    }

    /**
     * minutes = (euros * minutesPerHour) / eurosPerHour
     * endingDateTime = startingDateTime + minutes
     */
    private LocalDateTime calculateEndingDateTime (LocalDateTime startingDateTime, BigDecimal euros, BigDecimal eurosPerHour ) {
        BigDecimal minutes = euros.multiply(MINUTES_PER_HOUR).divide(eurosPerHour,0, RoundingMode.HALF_UP);
        return startingDateTime.plusMinutes(minutes.longValue());
    }

}

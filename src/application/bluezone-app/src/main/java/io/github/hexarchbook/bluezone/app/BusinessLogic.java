package io.github.hexarchbook.bluezone.app;

import io.github.hexarchbook.bluezone.app.ports.Rate;
import io.github.hexarchbook.bluezone.app.ports.Ticket;
import io.github.hexarchbook.bluezone.app.ports.driven.forobtainingdatetime.ForObtainingDateTime;
import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.ForPaying;
import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.PayRequest;
import io.github.hexarchbook.bluezone.app.ports.driven.forstoringdata.ForStoringData;
import io.github.hexarchbook.bluezone.app.ports.driving.BlueZoneApp;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarResult;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarRequest;
import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.BuyTicketRequest;
import io.github.hexarchbook.bluezone.app.usecases.BuyTicket;
import io.github.hexarchbook.bluezone.app.usecases.CheckCar;
import io.github.hexarchbook.bluezone.app.usecases.GetAvailableRates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class BusinessLogic implements BlueZoneApp {

    private final Logger logger = LoggerFactory.getLogger(BusinessLogic.class);

    private final ForStoringData dataRepository;
    private final ForPaying paymentService;
    private final ForObtainingDateTime dateTimeService;

    public BusinessLogic(ForStoringData dataRepository, ForPaying paymentService, ForObtainingDateTime dateTimeService) {
        if ( dataRepository==null || paymentService==null || dateTimeService==null ) {
            throw new RuntimeException("Driven ports cannot be null");
        }
        this.dataRepository = dataRepository;
        this.paymentService = paymentService;
        this.dateTimeService = dateTimeService;
        logger.info("Application(Hexagon) with business logic instantiated.");
    }

    @Override
    public Set<Rate> getAvailableRates() {
        GetAvailableRates getAvailableRates = new GetAvailableRates(this.dataRepository);
        return getAvailableRates.get();
    }

    @Override
    public Ticket buyTicket (BuyTicketRequest request) {
        BuyTicket buyTicket = new BuyTicket(this.dataRepository,this.paymentService,this.dateTimeService);
        return buyTicket.apply(request);
    }

    @Override
    public CheckCarResult checkCar (CheckCarRequest request ) {
        CheckCar checkCar = new CheckCar(this.dataRepository,this.dateTimeService);
        return checkCar.apply(request);
    }

    @Override
    public void initializeRates(List<Rate> newRates) {
        this.dataRepository.loadInitialRates(newRates);
    }

    @Override
    public void initializeTickets(List<Ticket> newTickets) {
        this.dataRepository.loadInitialTickets(newTickets);
    }

    @Override
    public void changeNextTicketCode(String newNextTicketCode) {
        this.dataRepository.setNextTicketCode(newNextTicketCode);
    }

    @Override
    public Ticket getStoredTicket(String ticketCode) {
        return this.dataRepository.getTicketByCode(ticketCode);
    }

    @Override
    public PayRequest getLastPayRequest() {
        return this.paymentService.getPayRequest();
    }

    @Override
    public String getLastPayResponse() {
        return this.paymentService.getPayResponse();
    }

    @Override
    public void setPaymentErrorPercent(int percentage) {
        this.paymentService.setErrorPercentage(percentage);
    }

    @Override
    public String getErrorCodeOccurred() {
        return this.paymentService.getErrorCode();
    }

    @Override
    public void changeCurrentDateTime(LocalDateTime newCurrentDateTime) {
        this.dateTimeService.setCurrentDateTime(newCurrentDateTime);
    }

}

package io.github.hexarchbook.bluezone.app.usecases;

import io.github.hexarchbook.bluezone.app.ports.Ticket;
import io.github.hexarchbook.bluezone.app.ports.driven.forobtainingdatetime.ForObtainingDateTime;
import io.github.hexarchbook.bluezone.app.ports.driven.forstoringdata.ForStoringData;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarResult;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarRequest;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarRequestException;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class CheckCar implements Function<CheckCarRequest, CheckCarResult> {

    private final ForStoringData dataRepository;
    private final ForObtainingDateTime dateTimeService;

    public CheckCar(ForStoringData dataRepository, ForObtainingDateTime dateTimeService) {
        this.dataRepository = dataRepository;
        this.dateTimeService = dateTimeService;
    }

    @Override
    public CheckCarResult apply(CheckCarRequest request) {
        List<String> requestErrors = request.validate();
        if ( ! requestErrors.isEmpty() ) {
            throw new CheckCarRequestException(requestErrors);
        }
        String carPlate = request.getCarPlate();
        String rateName = request.getRateName();
        LocalDateTime currentDateTime = this.dateTimeService.getCurrentDateTime();
        Set<Ticket> ticketsForCarAndRate = this.dataRepository.getTicketsByCarAndRate(carPlate,rateName);
        Iterator<Ticket> ticketsIterator = ticketsForCarAndRate.iterator();
        boolean activeTicketFound = false;
        while ( !activeTicketFound && ticketsIterator.hasNext() ) {
            Ticket ticket = ticketsIterator.next();
            if (ticket.isValidAt(currentDateTime)) {
                activeTicketFound = true;
            }
        }
        return new CheckCarResult(carPlate,rateName,currentDateTime,activeTicketFound);
    }

}

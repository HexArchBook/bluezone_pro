package io.github.hexarchbook.bluezone.startup;

import io.github.hexarchbook.bluezone.app.ports.Rate;
import io.github.hexarchbook.bluezone.app.ports.Ticket;
import io.github.hexarchbook.bluezone.app.ports.driven.forobtainingdatetime.ForObtainingDateTime;
import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.ForPaying;
import io.github.hexarchbook.bluezone.app.ports.driven.forstoringdata.ForStoringData;
import io.github.hexarchbook.bluezone.app.ports.driving.BlueZoneApp;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.ForIssuingFines;
import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.ForParkingCars;
import io.github.hexarchbook.bluezone.driving.forissuingfines.actor.test.ForIssuingFinesTestRunner;
import io.github.hexarchbook.bluezone.driving.forissuingfines.adapter.cli.ForIssuingFinesCliRunner;
import io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test.ForParkingCarsTestRunner;
import io.github.hexarchbook.bluezone.driving.forparkingcars.adapter.webui.ForParkingCarsWebUIRunner;
import io.github.hexarchbook.bluezone.lib.hexagonal.DrivenActor;
import io.github.hexarchbook.bluezone.lib.hexagonal.DrivingActor;
import io.github.hexarchbook.bluezone.lib.javautils.CollectionUtils;
import io.github.hexarchbook.bluezone.lib.javautils.DateTimeUtils;
import io.github.hexarchbook.bluezone.lib.javautils.FileUtils;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

/**
 * Element that does the wiring of the adapters and the app.
 *
 */
public class Configurator {

    private static final String FOR_PARKING_CARS = ForParkingCars.class.getSimpleName();
    private static final String FOR_ISSUING_FINES = ForIssuingFines.class.getSimpleName();
    private static final String NULL = "null";
    private static final String TEST = "test";
    private static final String WEB_UI = "web-ui";
    private static final String CLI = "cli";
    private static final String RATES_FILE_PATH_KEY = "ForStoringData.rates.file";
    private static final String TICKETS_FILE_PATH_KEY = "ForStoringData.tickets.file";
    private static final String NEXT_TICKET_CODE_KEY = "ForStoringData.ticket.code.next";
    private static final String PAYMENT_ERROR_PERCENT_KEY = "ForPaying.error.percentage";
    private static final String CURRENT_DATE_TIME_KEY = "ForObtainingDateTime.current";

    private final Properties portsAdaptersProperties;
    private final Properties drivenActorsProperties;

    public Configurator(Properties portsAdaptersProperties, Properties drivenActorsProperties ) {
        this.portsAdaptersProperties = portsAdaptersProperties;
        this.drivenActorsProperties = drivenActorsProperties;
    }

    public DrivingActor buildDrivingActor ( String drivingPortName ) {
        String drivingActorName = this.portsAdaptersProperties.getProperty(drivingPortName);
        BlueZoneApp blueZoneApp = this.createApp();
        if ( !TEST.equals(drivingActorName) ) {
            this.init(blueZoneApp);
        }
        if (FOR_PARKING_CARS.equals(drivingPortName)) {
            if ( TEST.equals(drivingActorName) ) {
                return new ForParkingCarsTestRunner(blueZoneApp,blueZoneApp);
            }
            if ( WEB_UI.equals(drivingActorName) ) {
                return new ForParkingCarsWebUIRunner(blueZoneApp);
            }
            throw new RuntimeException("No actor/adapter with name '" + drivingActorName + "' found for driving port '" + FOR_PARKING_CARS + "'");
        }
        if (FOR_ISSUING_FINES.equals(drivingPortName)) {
            if ( TEST.equals(drivingActorName) ) {
                return new ForIssuingFinesTestRunner(blueZoneApp,blueZoneApp);
            }
            if ( CLI.equals(drivingActorName) ) {
                return new ForIssuingFinesCliRunner(blueZoneApp);
            }
            throw new RuntimeException("No actor/adapter with name '" + drivingActorName + "' found for driving port '" + FOR_ISSUING_FINES + "'");
        }
        throw new RuntimeException("No actor/adapter found for driving port '" + drivingPortName + "'");
    }

    /**
     * Return an instance of the app applying the "configurable receiver" pattern,
     * injecting the driven adapters as parameters in the constructor.
     */
    private BlueZoneApp createApp() {
        if ( noDrivenActorsSelected() ) {
            return BlueZoneApp.getInstance();
        }
        ForStoringData dataRepository = lookupDrivenActor(ForStoringData.class);
        ForPaying paymentService = lookupDrivenActor(ForPaying.class);
        ForObtainingDateTime dateTimeService = lookupDrivenActor(ForObtainingDateTime.class);
        return BlueZoneApp.getInstance(dataRepository,paymentService,dateTimeService);
    }

    private boolean noDrivenActorsSelected() {
        return
                (
                        NULL.equals(selectedActor(ForStoringData.class)) &&
                                NULL.equals(selectedActor(ForPaying.class)) &&
                                NULL.equals(selectedActor(ForObtainingDateTime.class))
                );
    }

    private void init(BlueZoneApp blueZoneApp) {
        initDataRepository(blueZoneApp);
        initPaymentService(blueZoneApp);
        initDateTimeService(blueZoneApp);
    }

    private void initDataRepository(BlueZoneApp blueZoneApp) {
        if ( this.drivenActorsProperties.containsKey(RATES_FILE_PATH_KEY) ) {
            String ratesFilePath = this.drivenActorsProperties.getProperty(RATES_FILE_PATH_KEY);
            List<String[]> rawRates = FileUtils.readColumnsIgnoringHeader(Paths.get(ratesFilePath) );
            List<Rate> rates = rawRates.stream().map(rawRate -> new Rate(rawRate[0], new BigDecimal(rawRate[1]))).collect(Collectors.toList());
            blueZoneApp.initializeRates(rates);
        }
        if ( this.drivenActorsProperties.containsKey(TICKETS_FILE_PATH_KEY) ) {
            String ticketsFilePath = this.drivenActorsProperties.getProperty(TICKETS_FILE_PATH_KEY);
            List<String[]> rawTickets = FileUtils.readColumnsIgnoringHeader(Paths.get(ticketsFilePath) );
            List<Ticket> tickets = rawTickets.stream().map(rawTicket -> new Ticket(rawTicket[0],rawTicket[1],rawTicket[2], DateTimeUtils.parseDateTime(rawTicket[3],DateTimeUtils.YYYYMMDDHHMM_FORMAT),DateTimeUtils.parseDateTime(rawTicket[4],DateTimeUtils.YYYYMMDDHHMM_FORMAT),new BigDecimal(rawTicket[5]),rawTicket[6])).collect(Collectors.toList());
            blueZoneApp.initializeTickets(tickets);
        }
        if ( this.drivenActorsProperties.containsKey(NEXT_TICKET_CODE_KEY) ) {
            String nextTicketCode = this.drivenActorsProperties.getProperty(NEXT_TICKET_CODE_KEY);
            blueZoneApp.changeNextTicketCode(nextTicketCode);
        }
    }

    private void initPaymentService(BlueZoneApp blueZoneApp) {
        if ( this.drivenActorsProperties.containsKey(PAYMENT_ERROR_PERCENT_KEY) ) {
            String paymentErrorPercent = this.drivenActorsProperties.getProperty(PAYMENT_ERROR_PERCENT_KEY);
            blueZoneApp.setPaymentErrorPercent(Integer.parseInt(paymentErrorPercent));
        }
    }

    private void initDateTimeService(BlueZoneApp blueZoneApp) {
        if ( this.drivenActorsProperties.containsKey(CURRENT_DATE_TIME_KEY) ) {
            String currentDateTime = this.drivenActorsProperties.getProperty(CURRENT_DATE_TIME_KEY);
            blueZoneApp.changeCurrentDateTime ( DateTimeUtils.parseDateTime(currentDateTime,DateTimeUtils.YYYYMMDD_HHMM_FORMAT) );
        }
    }

    private String selectedActor(Class<?> portType) {
        return this.portsAdaptersProperties.getProperty(portType.getSimpleName());
    }

    private  <T> T lookupDrivenActor ( Class<T> drivenPortType ) {
        String drivenActorName = selectedActor(drivenPortType);
        List<Provider<T>> drivenActors = ServiceLoader.load(drivenPortType).stream().filter(p -> isDrivenActorOfName(p.type(),drivenActorName)).collect(Collectors.toList());
        if ( CollectionUtils.isNullOrEmpty(drivenActors) ) {
            throw new RuntimeException("No actor/adapter with name '" + drivenActorName + "' found for driven port '" + drivenPortType.getSimpleName() + "'");
        }
        if (drivenActors.size() > 1) {
            throw new RuntimeException("Many actors/adapters with name '" + drivenActorName + "' found for driven port '" + drivenPortType.getSimpleName() + "'");
        }
        return drivenActors.get(0).get();
    }

    private <T> boolean isDrivenActorOfName(Class<? extends T> actorType, String actorName) {
        DrivenActor actorAnnotation = actorType.getAnnotation(DrivenActor.class);
        if ((actorAnnotation == null) || (actorAnnotation.name() == null)) {
            return false;
        }
        return actorAnnotation.name().equals(actorName);
    }

}

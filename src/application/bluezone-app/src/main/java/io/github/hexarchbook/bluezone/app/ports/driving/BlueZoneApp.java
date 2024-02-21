package io.github.hexarchbook.bluezone.app.ports.driving;

import io.github.hexarchbook.bluezone.app.BusinessLogic;
import io.github.hexarchbook.bluezone.app.ports.driven.forobtainingdatetime.ForObtainingDateTime;
import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.ForPaying;
import io.github.hexarchbook.bluezone.app.ports.driven.forstoringdata.ForStoringData;
import io.github.hexarchbook.bluezone.app.ports.driving.foradministering.ForAdministering;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.ForIssuingFines;
import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.ForParkingCars;

/**
 * Interface collecting all driving ports.
 * It allows to get an instance of the application from the driven ports.
 */
public interface BlueZoneApp extends ForParkingCars, ForIssuingFines, ForAdministering {

    public static BlueZoneApp getInstance(ForStoringData dataRepository, ForPaying paymentService, ForObtainingDateTime dateTimeService) {
        return new BusinessLogic(dataRepository,paymentService,dateTimeService);
    }

    static BlueZoneApp getInstance() {
        return null;
    }
}

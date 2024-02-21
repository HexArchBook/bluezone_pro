package io.github.hexarchbook.bluezone.driven.forobtainingdatetime.actor.testdouble;

import io.github.hexarchbook.bluezone.app.ports.driven.forobtainingdatetime.ForObtainingDateTime;
import io.github.hexarchbook.bluezone.lib.hexagonal.DrivenActor;
import io.github.hexarchbook.bluezone.lib.javautils.DateTimeUtils;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * Driven actor that implements "for obtaining date-time" port with a "stub" test-double.
 * It holds a configurable date-time, to be returned as the current.
 * By default, this date-time is configured with the current date-time of the system clock.
 */
@DrivenActor(name="test-double")
public class StubDateTimeService implements ForObtainingDateTime {

    private LocalDateTime currentDateTime;

    public StubDateTimeService() {
        this.currentDateTime = LocalDateTime.now(Clock.systemDefaultZone());
    }

    @Override
    public LocalDateTime getCurrentDateTime() {
        return this.currentDateTime;
    }

    @Override
    public void setCurrentDateTime(LocalDateTime dateTime) {
        this.currentDateTime = dateTime;
    }

}

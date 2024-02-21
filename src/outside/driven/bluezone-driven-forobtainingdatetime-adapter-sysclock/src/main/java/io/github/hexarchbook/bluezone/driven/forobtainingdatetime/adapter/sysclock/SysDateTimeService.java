package io.github.hexarchbook.bluezone.driven.forobtainingdatetime.adapter.sysclock;

import io.github.hexarchbook.bluezone.app.ports.driven.forobtainingdatetime.ForObtainingDateTime;
import io.github.hexarchbook.bluezone.lib.hexagonal.DrivenActor;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Driven adapter for adapting the system clock actor to the port "for obtaining date-time"
 */
@DrivenActor(name="sys-clock")
public class SysDateTimeService implements ForObtainingDateTime {

    private Clock clock;

    public SysDateTimeService() {
        this.clock = Clock.systemDefaultZone();
    }

    @Override
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now(this.clock);
    }

    /**
     * Turns the system clock back or forward to set the given date-time as the current.
     */
    @Override
    public void setCurrentDateTime(LocalDateTime dateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now(this.clock);
        Duration duration = Duration.between(currentDateTime,dateTime);
        this.clock = Clock.offset(this.clock,duration);
    }

}

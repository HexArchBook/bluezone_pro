package io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines;

import io.github.hexarchbook.bluezone.lib.javautils.DateTimeUtils;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Output data returned after checking a parked car:
 *      - Car plate that has been checked.
 *      - Rate name of the zone where the checked car is parked.
 *      - Date-time at which the checking was done.
 *      - Status of the checked car: Legally or illegally parked.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckCarResult {

    private String carPlate;
    private String rateName;
    private LocalDateTime dateTime;
    private Boolean legallyParked;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckCarResult that = (CheckCarResult) o;
        return Objects.equals(this.carPlate, that.carPlate) &&
                Objects.equals(this.rateName, that.rateName) &&
                Objects.equals(this.dateTime, that.dateTime) &&
                Objects.equals(this.legallyParked, that.legallyParked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.carPlate,this.rateName,this.dateTime,this.legallyParked);
    }

    @Override
    public String toString() {
        return String.format("Check Car Result: [ Car:'%s' , Rate:'%s' , Date-time:'%s' , Status:'%s' ]",
                this.carPlate,
                this.rateName,
                DateTimeUtils.formatDateTime(this.dateTime,DateTimeUtils.YYYYMMDD_HHMM_FORMAT),
                (this.legallyParked) ? ("Legally parked") : ("Illegally parked")
        );
    }

}

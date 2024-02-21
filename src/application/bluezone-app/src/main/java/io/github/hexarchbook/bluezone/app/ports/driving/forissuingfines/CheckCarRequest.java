package io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines;

import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.BuyTicketRequestException;
import io.github.hexarchbook.bluezone.lib.javautils.StringUtils;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Input data needed for checking a parked car:
 *      - Car plate.
 *      - Rate name of the zone where the car is parked.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CheckCarRequest {

    private String      carPlate;
    private String      rateName;

    public List<String> validate() {
        List<String> errorMessages = new ArrayList<String>();
        if (StringUtils.isBlank(this.carPlate)) {
            errorMessages.add("Car plate must be provided");
        }
        if (StringUtils.isBlank(this.rateName)) {
            errorMessages.add("Rate must be provided");
        }
        return errorMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckCarRequest that = (CheckCarRequest) o;
        return Objects.equals(this.carPlate, that.carPlate) && Objects.equals(this.rateName, that.rateName) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash ( this.carPlate, this.rateName );
    }

}

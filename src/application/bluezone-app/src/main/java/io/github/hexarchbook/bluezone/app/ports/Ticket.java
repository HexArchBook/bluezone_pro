package io.github.hexarchbook.bluezone.app.ports;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class representing objects with the data of a parking ticket:
 * 		code				Unique identifier of the ticket
 * 							It is a 10-digit number with leading zeros if necessary
 * 		carPlate			Plate of the car that has been parked
 * 		rateName			Rate name of the zone where the car is parked at
 * 		startingDateTime	When the parking period begins
 * 		endingDateTime		When the parking period expires
 * 		price				Amount of euros paid for the ticket
 * 	    paymentId           Unique identifier of the payment made to get the ticket.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Ticket {

    private String          ticketCode;
    private String          carPlate;
    private String          rateName;
    private LocalDateTime   startingDateTime;
    private LocalDateTime   endingDateTime;
    private BigDecimal      price;
    private String          paymentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket that = (Ticket) o;
        return Objects.equals(this.ticketCode, that.ticketCode) &&
                Objects.equals(this.carPlate, that.carPlate) &&
                Objects.equals(this.rateName, that.rateName) &&
                Objects.equals(this.startingDateTime, that.startingDateTime) &&
                Objects.equals(this.endingDateTime, that.endingDateTime) &&
                Objects.equals(this.price, that.price) &&
                Objects.equals(this.paymentId, that.paymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.ticketCode, this.carPlate, this.rateName,
                this.startingDateTime, this.endingDateTime,
                this.price, this.paymentId);
    }

    /**
     * A ticket is valid if the given date-time is between
     * the starting and ending date-times of the ticket, both included.
     */
    public boolean isValidAt(LocalDateTime dateTime) {
        if ( dateTime.isBefore(this.startingDateTime) ) {
            return false;
        }
        if ( dateTime.isAfter(this.endingDateTime) ) {
            return false;
        }
        return true;
    }

}

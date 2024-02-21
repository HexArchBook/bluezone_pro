package io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars;

import io.github.hexarchbook.bluezone.lib.javautils.DateTimeUtils;
import io.github.hexarchbook.bluezone.lib.javautils.StringUtils;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Input data needed for buying a ticket to park a car:
 *      - Car plate.
 *      - Rate name of the zone where the car is parked.
 *      - Euros amount to be paid.
 *      - Card used for paying, in the format 'n-c-mmyyyy', where
 *              'n' is the card number (16 digits)
 *              'c' is the verification code (3 digits),
 *              'mmyyyy' is the expiration month and year (6 digits)
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BuyTicketRequest {

    private String carPlate;
    private String rateName;
    private String euros;
    private String card;

    public List<String> validate (LocalDateTime currentDateTime) {
        List<String> errorMessages = new ArrayList<String>();
        if (StringUtils.isBlank(this.carPlate)) {
            errorMessages.add("Car plate must be provided");
        }
        if (StringUtils.isBlank(this.rateName)) {
            errorMessages.add("Rate must be provided");
        }
        String eurosErrorMessage = validateEurosAmount(this.euros);
        if (!eurosErrorMessage.isEmpty()) {
            errorMessages.add(eurosErrorMessage);
        }
        String cardErrorMessage = validateCardData(this.card,currentDateTime);
        if (!cardErrorMessage.isEmpty()) {
            errorMessages.add(cardErrorMessage);
        }
        return errorMessages;
    }

    private String validateEurosAmount(String eurosAmount) {
        if ( StringUtils.isBlank(eurosAmount) ) {
            return "Euros amount must be provided";
        }
        String regExp = "[0-9]+"+ Pattern.quote(".")+"[0-9][0-9]";
        if ( ! Pattern.matches(regExp,eurosAmount) ) {
            return ("Euros amount ("+eurosAmount+") must be a 2 decimal places positive number");
        }
        if ( (new BigDecimal(eurosAmount)).compareTo(BigDecimal.ZERO) <= 0 ) {
            return ("Euros amount ("+eurosAmount+") must be greater than zero");
        }
        return "";
    }

    private String validateCardData ( String cardData, LocalDateTime currentDateTime ) {
        if ( StringUtils.isBlank(cardData) ) {
            return "Card data must be provided";
        }
        if ( ! validCardDataFormat(cardData) ) {
            return ("Card data ("+cardData+") must be in the format 'n-c-mmyyyy', where 'n' is the card number (16 digits), 'c' is the verification code (3 digits), and 'mmyyyy' is the expiration month and year (6 digits)");
        }
        if ( expiredCard(cardData,currentDateTime) ) {
            String cardMonthYear = cardData.substring(cardData.length()-6);
            String currentMonthYear = DateTimeUtils.formatDateTime(currentDateTime,DateTimeUtils.MMYYYY);
            return ("Card expiration month and year ("+cardMonthYear+") must be equal or after current month and year ("+currentMonthYear+")");
        }
        return "";
    }

    private boolean expiredCard ( String cardData, LocalDateTime currentDateTime ) {
        int currentYear = currentDateTime.getYear();
        int cardYear = Integer.parseInt ( cardData.substring(cardData.length()-4) );
        if ( currentYear > cardYear ) {
            return true;
        }
        if ( currentYear < cardYear ) {
            return false;
        }
        int currentMonth = currentDateTime.getMonthValue();
        int cardMonth = Integer.parseInt ( cardData.substring(cardData.length()-6, cardData.length()-4) ) ;
        return ( currentMonth > cardMonth );
    }

    /**
     * The format is: 'n-c-mmyyyy'
     * Where:
     *      'n' is the card number (16 digits)
     *      'c' is the card verification code (3 digits)
     *      'mmyyyy' is the expiration month and year (6 digits)
     */
    private boolean validCardDataFormat ( String cardData ) {
        String regExp = "[0-9]{16}"+ Pattern.quote("-")+"[0-9]{3}"+Pattern.quote("-")+"[0-9]{6}";
        if ( ! Pattern.matches(regExp,cardData) ) {
            return false;
        }
        String month = cardData.substring(cardData.length()-6, cardData.length()-4);
        if ( (Integer.parseInt(month) < 1) || (Integer.parseInt(month) > 12) ) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyTicketRequest that = (BuyTicketRequest) o;
        return Objects.equals(this.carPlate, that.carPlate) && Objects.equals(this.rateName, that.rateName) && Objects.equals(this.euros, that.euros) && Objects.equals(this.card, that.card) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash ( this.carPlate, this.rateName, this.euros, this.card);
    }

}

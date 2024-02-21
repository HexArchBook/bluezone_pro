package io.github.hexarchbook.bluezone.app.ports.driven.forpaying;

import lombok.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Input data needed for the 'pay' method:
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
public class PayRequest {

    private BigDecimal  euros;
    private String      card;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayRequest that = (PayRequest) o;
        return Objects.equals(this.euros, that.euros) && Objects.equals(this.card, that.card) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash ( this.euros, this.card );
    }

}

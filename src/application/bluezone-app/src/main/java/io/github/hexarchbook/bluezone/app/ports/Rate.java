package io.github.hexarchbook.bluezone.app.ports;

import lombok.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Class representing objects with the data of a rate:
 * 		name			Unique identifier of the rate.
 * 	 	eurosPerHour	Cost of parking a car during 60 minutes.
 * 	 					Price of a ticket is calculated based on this value,
 * 	 					depending on the number of minutes of the ticket time-period.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rate {

	private String		name;
	private BigDecimal	eurosPerHour;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Rate that = (Rate) o;
		return Objects.equals(this.name, that.name) && Objects.equals(this.eurosPerHour, that.eurosPerHour);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.eurosPerHour);
	}

	@Override
	public String toString() {
		return this.name + " ( " + this.eurosPerHour + "€ / hour )";
	}

}

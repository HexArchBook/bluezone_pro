package io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars;

import io.github.hexarchbook.bluezone.app.ports.Rate;
import io.github.hexarchbook.bluezone.app.ports.Ticket;
import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.PayErrorException;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DRIVING PORT (Provided Interface)
 */
public interface ForParkingCars {

	/**
	 * @return	A set with the existing rates for parking a car in regulated zones of the city.
	 * If no rates exist, an empty set is returned.
	 */
	public Set<Rate> getAvailableRates();

	/**
	 * Pay for a ticket to park a car at a zone regulated by a rate,
	 * and save the ticket in the repository.
	 * The validity period of the ticket begins at the current date-time,
	 * and its duration is calculated in minutes by applying the rate,
	 * based on the amount of euros paid.
	 * @param request	Input data needed for buying a ticket.
	 * 					@see BuyTicketRequest
	 * @return	A ticket valid for parking the car at a zone regulated by the rate,
	 * 			paying the euros amount using the card.
	 * 			The ticket holds a reference to the identifier of the payment that was done.
	 * @throws	BuyTicketRequestException
	 * 			If any input data in the request is not valid.
	 * @throws	PayErrorException
	 * 			If any error occurred while paying.
	 */
	public Ticket buyTicket (BuyTicketRequest request);

}

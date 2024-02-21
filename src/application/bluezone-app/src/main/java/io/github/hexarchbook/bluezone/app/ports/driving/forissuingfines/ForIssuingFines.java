package io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines;

/**
 * DRIVING PORT (Provided Interface)
 */
public interface ForIssuingFines {

	/**
	 * Check the status of a car parked at a zone regulated by a rate.
	 * The status is 'illegally parked' when there is no active ticket in the repository, for the car and rate given in the request.
	 * Otherwise, if there is at least one such a ticket in the repository, then the status would be 'legally parked'.
	 * A ticket is active if the current date-time is between the ticket starting and ending date-times, both included.
	 * @param request	Input data needed for checking a car.
	 * 					@see CheckCarRequest
	 * @return	An object with the checking outcome, telling if the car is legally parked of not.
	 * 			@see CheckCarResult
	 * @throws	CheckCarRequestException
	 * 			If any input data in the request is not valid.
	 */
	public CheckCarResult checkCar (CheckCarRequest request );

}

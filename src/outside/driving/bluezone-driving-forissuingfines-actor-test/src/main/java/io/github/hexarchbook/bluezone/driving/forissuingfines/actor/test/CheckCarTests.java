package io.github.hexarchbook.bluezone.driving.forissuingfines.actor.test;

import io.github.hexarchbook.bluezone.app.ports.Rate;
import io.github.hexarchbook.bluezone.app.ports.Ticket;
import io.github.hexarchbook.bluezone.app.ports.driving.foradministering.ForAdministering;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarResult;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarRequest;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.ForIssuingFines;
import io.github.hexarchbook.bluezone.lib.javautils.DateTimeUtils;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests for the "check car" use case
 */
public class CheckCarTests {

	private static final Boolean LEGALLY_PARKED = true;
	private static final Boolean NOT_LEGALLY_PARKED = false;

	private ForIssuingFines systemUnderTest;
	private ForAdministering testFixture;

	@BeforeMethod
	public void initialize() {
		Reporter.log ("Initializing app ..." );
		this.systemUnderTest = SystemUnderTest.getInstance();
		this.testFixture = TestFixture.getInstance();
	}

	@Test
	public void withNonActiveTickets_carStatus_shouldBe_NotLegallyParked() {
		// GIVEN current date-time
		String formattedCurrentDateTime = "2023/10/27 11:30";
		LocalDateTime currentDateTime = DateTimeUtils.parseDateTime(formattedCurrentDateTime,DateTimeUtils.YYYYMMDD_HHMM_FORMAT);
		Reporter.log ("GIVEN current date-time is: " + formattedCurrentDateTime );
		this.testFixture.changeCurrentDateTime( currentDateTime );
		// GIVEN rates and tickets
		String[][] initialRates = {{"Blue","0.80"},{"Orange","0.70"}};
		String[][] initialTickets = {
				{"0000000001","5383JPV","Blue","2023/10/27 09:00","2023/10/27 10:30","1.20","9994125b-935e-4d6f-a2ae-115dbdc6b791"},
				{"0000000002","5383JPV","Orange","2023/10/27 10:45","2023/10/27 11:45","0.70","673e7cfc-814b-4d46-923a-79213952e7aa"},
				{"0000000003","4244KBS","Blue","2023/10/27 10:00","2023/10/27 12:00","1.60","df5f60ec-aa92-4764-add4-2ccc598141d4"},
				{"0000000004","7386LVK","Orange","2023/10/27 11:00","2023/10/27 13:30","1.75","ea51f919-558b-4c00-95cf-7ed1bc4600a5"}
		};
		List<Rate> rates = parseRates(initialRates);
		List<Ticket> tickets = parseTickets(initialTickets);
		Reporter.log ("GIVEN the existing rates in the repository are: " + rates );
		Reporter.log ("GIVEN the existing tickets in the repository are: " + tickets );
		this.testFixture.initializeRates(rates);
		this.testFixture.initializeTickets(tickets);
		// WHEN "check car" request to the port "for issuing fines"
		String carPlate = "5383JPV";
		String rateName = "Blue";
		CheckCarRequest request = new CheckCarRequest(carPlate,rateName);
		Reporter.log ("WHEN I do this 'check car' request: " + request );
		CheckCarResult checkCarResult = this.systemUnderTest.checkCar(request);
		// THEN compare current vs expected "check car" result
		CheckCarResult expectedCheckCarResult = new CheckCarResult(carPlate,rateName,currentDateTime,NOT_LEGALLY_PARKED);
		Reporter.log ("THEN I should obtain this 'check car' result: " + expectedCheckCarResult);
		assertThat(checkCarResult, is(expectedCheckCarResult));
	}

	@Test
	public void withAnActiveTicket_carStatus_shouldBe_LegallyParked() {
		// GIVEN current date-time
		String formattedCurrentDateTime = "2023/10/30 18:00";
		LocalDateTime currentDateTime = DateTimeUtils.parseDateTime(formattedCurrentDateTime,DateTimeUtils.YYYYMMDD_HHMM_FORMAT);
		Reporter.log ("GIVEN current date-time is: " + formattedCurrentDateTime );
		this.testFixture.changeCurrentDateTime( currentDateTime );
		// GIVEN rates and tickets
		String[][] initialRates = {{"Green","0.85"}};
		String[][] initialTickets = {
				{"0000000001","2142HXG","Green","2023/10/30 17:00","2023/10/30 19:00","1.70","71b3816f-6fa1-4d69-b4e6-4c7ed53f8dd8"}
		};
		List<Rate> rates = parseRates(initialRates);
		List<Ticket> tickets = parseTickets(initialTickets);
		Reporter.log ("GIVEN the existing rates in the repository are: " + rates );
		Reporter.log ("GIVEN the existing tickets in the repository are: " + tickets );
		this.testFixture.initializeRates(rates);
		this.testFixture.initializeTickets(tickets);
		// WHEN "check car" request to the port "for issuing fines"
		String carPlate = "2142HXG";
		String rateName = "Green";
		CheckCarRequest request = new CheckCarRequest(carPlate,rateName);
		Reporter.log ("WHEN I do this 'check car' request: " + request );
		CheckCarResult checkCarResult = this.systemUnderTest.checkCar(request);
		// THEN compare current vs expected "check car" result
		CheckCarResult expectedCheckCarResult = new CheckCarResult(carPlate,rateName,currentDateTime,LEGALLY_PARKED);
		Reporter.log ("THEN I should obtain this 'check car' result: " + expectedCheckCarResult);
		assertThat(checkCarResult, is(expectedCheckCarResult));
	}

	private static List<Rate> parseRates(String[][] formattedRates) {
		List<Rate> rates = new ArrayList<Rate>();
		for ( String[] formattedRate : formattedRates ) {
			Rate rate = new Rate ( formattedRate[0], new BigDecimal(formattedRate[1]) );
			rates.add(rate);
		}
		return rates;
	}

	private static List<Ticket> parseTickets(String[][] formattedTickets) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		for ( String[] formattedTicket : formattedTickets ) {
			Ticket ticket = new Ticket(formattedTicket[0],formattedTicket[1],formattedTicket[2],DateTimeUtils.parseDateTime(formattedTicket[3],DateTimeUtils.YYYYMMDD_HHMM_FORMAT),DateTimeUtils.parseDateTime(formattedTicket[4],DateTimeUtils.YYYYMMDD_HHMM_FORMAT),new BigDecimal(formattedTicket[5]),formattedTicket[6]);
			tickets.add(ticket);
		}
		return tickets;
	}

}

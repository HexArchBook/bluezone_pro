package io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test.stepdefs;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.hexarchbook.bluezone.app.ports.Ticket;
import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.BuyTicketRequest;
import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.ForParkingCars;
import io.github.hexarchbook.bluezone.lib.javautils.DateTimeUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class TicketStepDefinitions {

    private final ScenarioContext scenarioContext;

    public TicketStepDefinitions(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @DataTableType
    public BuyTicketRequest buyTicketRequestEntry (Map<String,String> row ) {
        String carPlate = row.get("carPlate");
        String rateName = row.get("rateName");
        String euros = row.get("euros");
        String card = row.get("card");
        return new BuyTicketRequest(carPlate,rateName,euros,card);
    }

    @DataTableType
    public Ticket ticketEntry (Map<String,String> row ) {
        String ticketCode = row.get("ticketCode");
        String carPlate = row.get("carPlate");
        String rateName = row.get("rateName");
        String formattedStartingDateTime = row.get("startingDateTime");
        LocalDateTime startingDateTime = DateTimeUtils.parseDateTime(formattedStartingDateTime,DateTimeUtils.YYYYMMDD_HHMM_FORMAT);
        String formattedEndingDateTime = row.get("endingDateTime");
        LocalDateTime endingDateTime = DateTimeUtils.parseDateTime(formattedEndingDateTime,DateTimeUtils.YYYYMMDD_HHMM_FORMAT);
        BigDecimal price = new BigDecimal(row.get("price"));
        String paymentId = row.get("paymentId");
        return new Ticket(ticketCode,carPlate,rateName,startingDateTime,endingDateTime,price,paymentId);
    }

    @Given("next ticket code is {string}")
    public void nextTicketCodeIs(String ticketCode) {
        this.scenarioContext.getTestFixture().changeNextTicketCode(ticketCode);
    }

    @When("this 'buy ticket' request is done")
    public void thisBuyTicketRequestIsDone(List<BuyTicketRequest> buyTicketRequests) {
        try {
            BuyTicketRequest buyTicketRequest = buyTicketRequests.get(0);
            Ticket ticket = this.scenarioContext.getSystemUnderTest().buyTicket(buyTicketRequest);
            this.scenarioContext.setCurrentBoughtTicket(ticket);
        } catch ( Exception e ) {
            this.scenarioContext.setCurrentThrownException(e);
        }
    }

    @Then("this ticket with the 'pay' response as 'payment id' should have been stored")
    public void thisTicketWithThePayResponseAsPaymentIdShouldHaveBeenStored(List<Map<String,String>> ticketData) {
        String ticketCode = ticketData.get(0).get("ticketCode");
        String carPlate = ticketData.get(0).get("carPlate");
        String rateName = ticketData.get(0).get("rateName");
        String formattedStartingDateTime = ticketData.get(0).get("startingDateTime");
        LocalDateTime startingDateTime = DateTimeUtils.parseDateTime(formattedStartingDateTime,DateTimeUtils.YYYYMMDD_HHMM_FORMAT);
        String formattedEndingDateTime = ticketData.get(0).get("endingDateTime");
        LocalDateTime endingDateTime = DateTimeUtils.parseDateTime(formattedEndingDateTime,DateTimeUtils.YYYYMMDD_HHMM_FORMAT);
        BigDecimal price = new BigDecimal(ticketData.get(0).get("price"));
        String paymentId = this.scenarioContext.getTestFixture().getLastPayResponse();
        Ticket expectedStoredTicket = new Ticket(ticketCode,carPlate,rateName,startingDateTime,endingDateTime,price,paymentId);
        Ticket currentStoredTicket = this.scenarioContext.getTestFixture().getStoredTicket(expectedStoredTicket.getTicketCode());
        assertThat(currentStoredTicket, is(expectedStoredTicket));
    }

    @Then("the 'buy ticket' response should be the ticket stored with code {string}")
    public void theBuyTicketResponseShouldBeTheTicketStoredWithCode(String ticketCode) {
        Ticket expectedBoughtTicket = this.scenarioContext.getTestFixture().getStoredTicket(ticketCode);
        Ticket currentBoughtTicket = this.scenarioContext.getCurrentBoughtTicket();
        assertThat(currentBoughtTicket, is(expectedBoughtTicket));
    }

    @Then("no ticket with code {string} should have been stored")
    public void noTicketWithCodeShouldHaveBeenStored(String ticketCode) {
        Ticket currentStoredTicket = this.scenarioContext.getTestFixture().getStoredTicket(ticketCode);
        assertThat(currentStoredTicket, is(nullValue()));
    }

}

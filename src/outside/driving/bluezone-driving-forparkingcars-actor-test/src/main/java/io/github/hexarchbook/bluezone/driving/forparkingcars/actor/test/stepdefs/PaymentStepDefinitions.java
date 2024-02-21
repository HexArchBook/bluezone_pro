package io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test.stepdefs;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.PayRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import static io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test.matchers.IsAPayErrorExceptionWith.aPayErrorExceptionWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PaymentStepDefinitions {

    private final ScenarioContext scenarioContext;

    public PaymentStepDefinitions(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @DataTableType
    public PayRequest payRequestEntry (Map<String,String> row ) {
        BigDecimal euros = new BigDecimal(row.get("euros"));
        String card = row.get("card");
        return new PayRequest(euros,card);
    }

    @Given("an error occurs while paying")
    public void anErrorOccursWhilePaying() {
        this.scenarioContext.getTestFixture().setPaymentErrorPercent(100);
    }

    @Given("no error occurs while paying")
    public void noErrorOccursWhilePaying() {
        this.scenarioContext.getTestFixture().setPaymentErrorPercent(0);
    }

    @Then("this 'pay' request should have been done")
    public void thisPayRequestShouldHaveBeenDone(List<PayRequest> payRequests) {
        PayRequest currentPayRequest = this.scenarioContext.getTestFixture().getLastPayRequest();
        PayRequest expectedPayRequest = payRequests.get(0);
        assertThat(currentPayRequest, is(expectedPayRequest));
    }

    @Then("a PayErrorException with the error code that occurred should have been thrown")
    public void aPayErrorExceptionWithTheErrorCodeThatOccurredShouldHaveBeenThrown() {
        Exception currentThrownException = this.scenarioContext.getCurrentThrownException();
        String expectedErrorCode = this.scenarioContext.getTestFixture().getErrorCodeOccurred();
        assertThat(currentThrownException,is(aPayErrorExceptionWith(expectedErrorCode)));
    }

}

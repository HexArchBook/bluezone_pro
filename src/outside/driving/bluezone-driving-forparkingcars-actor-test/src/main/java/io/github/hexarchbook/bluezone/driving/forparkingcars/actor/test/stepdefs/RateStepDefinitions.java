package io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test.stepdefs;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.hexarchbook.bluezone.app.ports.Rate;
import io.github.hexarchbook.bluezone.app.ports.driving.foradministering.ForAdministering;
import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.ForParkingCars;
import java.math.BigDecimal;
import java.util.*;
import static io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test.matchers.IsTheSameSetAs.theSameSetAs;
import static io.github.hexarchbook.bluezone.lib.javautils.CollectionUtils.listToSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RateStepDefinitions {

    private final ScenarioContext scenarioContext;

    public RateStepDefinitions(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @DataTableType
    public Rate rateEntry(Map<String, String> row) {
        String name = row.get("name");
        BigDecimal eurosPerHour = new BigDecimal(row.get("eurosPerHour"));
        return new Rate(name, eurosPerHour);
    }

    @Given("the existing rates in the repository are")
    public void theExistingRatesInTheRepositoryAre (List<Rate> rates ) {
        this.scenarioContext.getTestFixture().initializeRates(rates);
    }

    @Given("there are no rates in the repository")
    public void thereAreNoRatesInTheRepository() {
        this.scenarioContext.getTestFixture().initializeRates ( new ArrayList<Rate>() );
    }

    @When("I do a 'get available rates' request")
    public void iDoAGetAvailableRatesRequest() {
        Set<Rate> availableRates = this.scenarioContext.getSystemUnderTest().getAvailableRates();
        this.scenarioContext.setCurrentAvailableRates(availableRates);
    }

    @Then("I should obtain these rates")
    public void iShouldObtainTheseRates ( List<Rate> rates ) {
        Set<Rate> expectedAvailableRates = listToSet(rates);
        Set<Rate> currentAvailableRates = this.scenarioContext.getCurrentAvailableRates();
        assertThat ( currentAvailableRates, is(theSameSetAs(expectedAvailableRates)));
    }

    @Then("I should obtain no rate")
    public void iShouldObtainNoRate() {
        Set<Rate> expectedAvailableRates = new HashSet<Rate>();
        Set<Rate> currentAvailableRates = this.scenarioContext.getCurrentAvailableRates();
        assertThat ( currentAvailableRates, is(theSameSetAs(expectedAvailableRates)));
    }

}

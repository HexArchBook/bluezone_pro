package io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test.stepdefs;

import io.cucumber.java.en.Given;
import io.github.hexarchbook.bluezone.lib.javautils.DateTimeUtils;
import java.time.LocalDateTime;

public class DateTimeStepDefinitions {

    private final ScenarioContext scenarioContext;

    public DateTimeStepDefinitions(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Given("current date-time is {string}")
    public void currentDateTimeIs ( String formattedDateTime ) {
        LocalDateTime dateTime = DateTimeUtils.parseDateTime(formattedDateTime,DateTimeUtils.YYYYMMDD_HHMM_FORMAT);
        this.scenarioContext.getTestFixture().changeCurrentDateTime(dateTime);
    }

}

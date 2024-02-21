package io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test.stepdefs;

import io.github.hexarchbook.bluezone.app.ports.Rate;
import io.github.hexarchbook.bluezone.app.ports.Ticket;
import io.github.hexarchbook.bluezone.app.ports.driving.foradministering.ForAdministering;
import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.ForParkingCars;
import io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test.SystemUnderTest;
import io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test.TestFixture;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

/**
 * Class for sharing state between different steps files in one scenario.
 * It is instantiated and injected into the "step definitions" by PicoContainer (DI framework).
 * Put this class in stepdefs package to be recreated for every scenario.
 * So that there won't be state leakage between scenarios.
 */
@Setter
@Getter
public class ScenarioContext {

    private final ForParkingCars systemUnderTest;
    private final ForAdministering testFixture;

    private Set<Rate> currentAvailableRates;
    private Ticket currentBoughtTicket;
    private Exception currentThrownException;

    public ScenarioContext() {
        this.systemUnderTest = SystemUnderTest.getInstance();
        this.testFixture = TestFixture.getInstance();
    }

}

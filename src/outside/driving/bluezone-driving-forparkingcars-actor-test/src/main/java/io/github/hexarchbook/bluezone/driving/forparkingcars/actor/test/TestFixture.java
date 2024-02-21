package io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test;

import io.github.hexarchbook.bluezone.app.ports.driving.foradministering.ForAdministering;

/**
 * Singleton for holding the instance of the port "for administering"
 * The test fixture is the environment of the system under test.
 * Test cases:
 *          - Configure the test fixture in the GIVEN phase to set up the pre-conditions.
 *          - Read the test fixture in the THEN phase to check the outcome.
 */
public enum TestFixture {

    INSTANCE;

    private ForAdministering testFixture;

    public void set ( ForAdministering testFixture ) {
        this.testFixture = testFixture;
    }

    private ForAdministering get() {
        return this.testFixture;
    }

    public static ForAdministering getInstance() {
        return TestFixture.INSTANCE.get();
    }

}

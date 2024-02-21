package io.github.hexarchbook.bluezone.driving.forissuingfines.actor.test;

import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.ForIssuingFines;

/**
 * Singleton for holding the instance of the port "for issuing fines"
 */
public enum SystemUnderTest {

    INSTANCE;

    private ForIssuingFines systemUnderTest;

    public void set ( ForIssuingFines systemUnderTest ) {
        this.systemUnderTest = systemUnderTest;
    }

    private ForIssuingFines get() {
        return this.systemUnderTest;
    }

    public static ForIssuingFines getInstance() {
        return SystemUnderTest.INSTANCE.get();
    }

}

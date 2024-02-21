package io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test;

import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.ForParkingCars;

/**
 * Singleton for holding the instance of the port "for parking cars"
 */
public enum SystemUnderTest {

    INSTANCE;

    private ForParkingCars systemUnderTest;

    public void set ( ForParkingCars systemUnderTest ) {
        this.systemUnderTest = systemUnderTest;
    }

    private ForParkingCars get() {
        return this.systemUnderTest;
    }

    public static ForParkingCars getInstance() {
        return SystemUnderTest.INSTANCE.get();
    }

}

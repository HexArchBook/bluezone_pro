package io.github.hexarchbook.bluezone.driving.forissuingfines.actor.test;

import io.github.hexarchbook.bluezone.app.ports.driving.foradministering.ForAdministering;

/**
 * Singleton for holding the instance of the port "for administering"
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

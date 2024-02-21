package io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test;

import io.github.hexarchbook.bluezone.app.ports.driving.foradministering.ForAdministering;
import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.ForParkingCars;
import io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test.stepdefs.ScenarioContext;
import io.github.hexarchbook.bluezone.lib.hexagonal.DrivingActor;

/**
 * Driving actor.
 * It tests the use cases of the port "for parking cars".
 * It uses Cucumber testing automation tool.
 */
public class ForParkingCarsTestRunner implements DrivingActor {

	private final ForParkingCars systemUnderTest;
	private final ForAdministering testFixture;

	public ForParkingCarsTestRunner (ForParkingCars systemUnderTest, ForAdministering testFixture) {
		this.systemUnderTest = systemUnderTest;
		this.testFixture = testFixture;
	}

	/**
	 * Launches Cucumber
	 */
	@Override
	public void run(String... args) {
		SystemUnderTest.INSTANCE.set (this.systemUnderTest);
		TestFixture.INSTANCE.set (this.testFixture);
		String[] cucumberArgs = new String[]
				{
				"classpath:testcases",
				"--glue",				ScenarioContext.class.getPackageName(),
				"--snippets",			"camelcase",
				"--plugin",				"pretty",			
				"--publish"
				};
		io.cucumber.core.cli.Main.main ( cucumberArgs );
	}

}

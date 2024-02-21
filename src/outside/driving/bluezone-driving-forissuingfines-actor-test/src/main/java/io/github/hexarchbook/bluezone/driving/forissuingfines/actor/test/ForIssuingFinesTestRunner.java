package io.github.hexarchbook.bluezone.driving.forissuingfines.actor.test;

import io.github.hexarchbook.bluezone.app.ports.driving.foradministering.ForAdministering;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.ForIssuingFines;
import io.github.hexarchbook.bluezone.lib.hexagonal.DrivingActor;
import org.testng.TestNG;
import java.io.File;
import java.time.Instant;

/**
 * Driving actor.
 * It tests the use cases of the port "for issuing fines".
 * It uses TestNG framework.
 */
public class ForIssuingFinesTestRunner implements DrivingActor {

	private static final Class[] TEST_CLASSES_TO_RUN = new Class[] { CheckCarTests.class };
	private static final String REPORTS_DIRECTORY = System.getProperty("user.home") + File.separator + 
													".bluezoneapp" + File.separator +
													"forissuingfines" + File.separator +
													"testng-reports" + File.separator +
													Instant.now().toEpochMilli();
	private static final String TEST_RESULTS_REPORT = REPORTS_DIRECTORY + File.separator + "index.html";
	private static final String EMAILABLE_REPORT = REPORTS_DIRECTORY + File.separator + "emailable-report.html";

	private final ForIssuingFines fineIssuerPort;
	private final ForAdministering administrationPort;

	public ForIssuingFinesTestRunner (ForIssuingFines fineIssuerPort, ForAdministering administrationPort) {
		this.fineIssuerPort = fineIssuerPort;
		this.administrationPort = administrationPort;
	}

	/**
	 * Launches TestNG
	 */
	@Override
	public void run ( String... args ) {
		SystemUnderTest.INSTANCE.set (this.fineIssuerPort);
		TestFixture.INSTANCE.set (this.administrationPort);
		System.out.println("===========================================================");
		System.out.println("Testing the port 'for issuing fines' using TestNG framework");
		System.out.println("===========================================================");
		TestNG testNG = new TestNG();
		testNG.setOutputDirectory ( REPORTS_DIRECTORY );
		testNG.setTestClasses ( TEST_CLASSES_TO_RUN );
		testNG.run();
		System.out.println("===========================================================");
		System.out.println("These HTML reports were generated:");
		System.out.println(TEST_RESULTS_REPORT);
		System.out.println(EMAILABLE_REPORT);
		System.out.println("===========================================================");
	}
	
}

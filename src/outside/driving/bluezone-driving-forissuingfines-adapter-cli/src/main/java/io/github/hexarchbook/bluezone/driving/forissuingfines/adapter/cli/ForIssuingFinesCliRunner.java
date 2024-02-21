package io.github.hexarchbook.bluezone.driving.forissuingfines.adapter.cli;

import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarResult;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarRequest;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarRequestException;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.ForIssuingFines;
import io.github.hexarchbook.bluezone.lib.hexagonal.DrivingActor;
import java.util.Scanner;

/**
 * Driving adapter.
 * It allows actor "parking inspector" to interact with the app on port "for issuing fines".
 * It runs a CLI ("Command Line Interface").
 */
public class ForIssuingFinesCliRunner implements DrivingActor {

	private final ForIssuingFines app;

	public ForIssuingFinesCliRunner(ForIssuingFines app ) {
		this.app = app;
	}

	@Override
	public void run ( String... args ) {
		Scanner scanner = new Scanner(System.in);
		String mainMenuTitle = "FOR ISSUING FINES";
		MenuOption[] mainMenuOptions = {new CheckCarOption(scanner,app)};
		Menu mainMenu = Menu.createWithExitOption(scanner,mainMenuTitle,mainMenuOptions);
		mainMenu.run();
		scanner.close();
	}

}

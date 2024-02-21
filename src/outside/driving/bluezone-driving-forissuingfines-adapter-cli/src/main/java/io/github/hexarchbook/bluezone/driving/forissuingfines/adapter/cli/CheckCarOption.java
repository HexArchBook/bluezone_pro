package io.github.hexarchbook.bluezone.driving.forissuingfines.adapter.cli;

import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarRequest;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarRequestException;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarResult;
import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.ForIssuingFines;
import java.util.Scanner;

public class CheckCarOption implements MenuOption {

	private final Scanner scanner;
	private final ForIssuingFines app;

	public CheckCarOption ( Scanner scanner, ForIssuingFines app ) {
		this.scanner = scanner;
		this.app = app;
	}

	@Override
	public String description() {
		return "Check car";
	}

	@Override
	public void run() {
		System.out.println("");
		System.out.println("---------------");
		System.out.println("   Check car   ");
		System.out.println("---------------");
		System.out.println("Enter the plate of the car to check:");
		String carPlate = this.scanner.next();
		System.out.println("Enter the rate name of the zone where the car is parked at:");
		String rateName = this.scanner.next();
		System.out.println("");
		System.out.println("Checking the car '"+carPlate+"' parked at a zone regulated by the rate '"+rateName+"'...");
		try {
			CheckCarResult checkCarResult = this.app.checkCar(new CheckCarRequest(carPlate,rateName));
			System.out.println("");
			System.out.println(checkCarResult);
		} catch ( CheckCarRequestException checkCarRequestException ) {
			System.out.println(checkCarRequestException.getMessage());
			checkCarRequestException.getErrorMessages().forEach(
					message -> System.out.println(message)
			);
		} catch ( Exception exception ) {
			System.out.println("-------------------------------------------------------------------");
			System.out.println("An unexpected error occurred. Contact the application administrator");
			System.out.println("-------------------------------------------------------------------");
			System.out.println(exception.getMessage());
		}
	}

}

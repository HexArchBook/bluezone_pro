//package io.github.hexarchbook.bluezone.driving.forissuingfines.adapter.cli;
//
//import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarRequest;
//import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarRequestException;
//import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.CheckCarResult;
//import io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines.ForIssuingFines;
//import io.github.hexarchbook.bluezone.lib.hexagonal.DrivingActor;
//
//import java.util.Scanner;
//
///**
// * Driving adapter.
// * It allows actor "parking inspector" to interact with the app on port "for issuing fines".
// * It runs a CLI ("Command Line Interface").
// */
//public class ForIssuingFinesCliRunner implements DrivingActor {
//
//	private final ForIssuingFines app;
//
//	public ForIssuingFinesCliRunner(ForIssuingFines app ) {
//		this.app = app;
//	}
//
//	@Override
//	public void run ( String... args ) {
//		Scanner scanner = new Scanner(System.in);
//		boolean exit = false;
//		while (!exit) {
//			MenuOption selectedOption = showMainMenu(scanner);
//			switch (selectedOption) {
//				case CHECK_CAR:
//					checkCar(scanner);
//					break;
//				case EXIT:
//					exit = true;
//					break;
//				default:
//					System.out.println("");
//					System.out.println("Invalid option");
//					break;
//			}
//		}
//	}
//
//	private MenuOption showMainMenu ( Scanner scanner ) {
//		System.out.println("");
//		System.out.println("");
//		System.out.println("");
//		System.out.println("");
//		System.out.println("");
//		System.out.println("------------------------");
//		System.out.println(" MENU FOR ISSUING FINES ");
//		System.out.println("------------------------");
//		System.out.println("(1) Check car.");
//		System.out.println("(2) Exit.");
//		System.out.println("------------------------");
//		System.out.println("");
//		System.out.println("Choose an option (enter the option number):");
//		String option = scanner.next();
//		if ( "1".equals(option) ) {
//			return MenuOption.CHECK_CAR;
//		}
//		if ( "2".equals(option) ) {
//			return MenuOption.EXIT;
//		}
//		return MenuOption.NONE;
//	}
//
//	private void checkCar ( Scanner scanner ) {
//		System.out.println("");
//		System.out.println("---------------");
//		System.out.println("   Check car   ");
//		System.out.println("---------------");
//		System.out.println("Enter the plate of the car to check:");
//		String carPlate = scanner.next();
//		System.out.println("Enter the rate name of the zone where the car is parked at:");
//		String rateName = scanner.next();
//		System.out.println("");
//		System.out.println("Checking the car '"+carPlate+"' parked at a zone regulated by the rate '"+rateName+"'...");
//		try {
//			CheckCarResult checkCarResult = this.app.checkCar(new CheckCarRequest(carPlate,rateName));
//			System.out.println("");
//			System.out.println("Checking result:");
//			System.out.println(checkCarResult);
//		} catch ( CheckCarRequestException checkCarRequestException ) {
//			System.out.println(checkCarRequestException.getMessage());
//			checkCarRequestException.getErrorMessages().forEach(
//					message -> System.out.println(message)
//			);
//		} catch ( Exception exception ) {
//			System.out.println(exception.getMessage());
//		}
//	}
//
//}

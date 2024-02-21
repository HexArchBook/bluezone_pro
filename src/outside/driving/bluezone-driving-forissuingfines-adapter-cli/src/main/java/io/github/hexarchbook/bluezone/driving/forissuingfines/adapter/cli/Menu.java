package io.github.hexarchbook.bluezone.driving.forissuingfines.adapter.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner;
    private final String title;
    private final List<MenuOption> options;
    private final boolean hasExitOption;
    private int selectedOptionNumber;

    public static Menu createWithExitOption(Scanner scanner, String title, MenuOption[] options) {
        return new Menu(scanner,title,options,true);
    }

    private Menu ( Scanner scanner, String title, MenuOption[] options, boolean addExitOption ) {
        this.scanner = scanner;
        this.title = title;
        this.options = new ArrayList<MenuOption>();
        for ( MenuOption option : options ) {
            this.options.add(option);
        }
        if ( addExitOption ) {
            this.options.add(new ExitOption());
        }
        this.hasExitOption = addExitOption;
        this.selectedOptionNumber = -1;
    }

    public void run() {
        do {
            showOptions();
            this.selectedOptionNumber = enterOptionNumber();
            runSelectedOption();
        } while ( this.hasExitOption && (this.selectedOptionNumber!=exitOptionNumber()) );
    }

    private int exitOptionNumber() {
        if ( this.hasExitOption ) {
            return this.options.size();
        }
        return (-1);
    }

    private void runSelectedOption() {
        this.options.get(this.selectedOptionNumber-1).run();
    }

    private int enterOptionNumber() {
        String enteredOption;
        boolean isEnteredOptionValid;
        do {
            System.out.println("Enter an option number ( 1 - " + this.options.size() + " ): ");
            enteredOption = this.scanner.next();
            isEnteredOptionValid = isValidOption(enteredOption);
            if ( !isEnteredOptionValid ) {
                System.out.println("Invalid option.");
            }
        } while (!isEnteredOptionValid);
        return Integer.valueOf(enteredOption);
    }

    private boolean isValidOption ( String option ) {
        for (int optionNumber = 1; optionNumber <= this.options.size(); optionNumber++) {
            String optionNumberAsString = ""+optionNumber;
            if ( optionNumberAsString.equals(option) ) {
                return true;
            }
        }
        return false;
    }

    private void showOptions() {
        System.out.println("-----------------------------");
        System.out.println(this.title);
        System.out.println("-----------------------------");
        for ( int optionNumber=1; optionNumber <= this.options.size(); optionNumber++ ) {
            System.out.println("("+optionNumber+") "+this.options.get(optionNumber-1).description()+".");
        }
        System.out.println("-----------------------------");

    }

}

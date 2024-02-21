package io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars;

import java.util.List;

/**
 * Exception thrown if any input data in the "buy ticket" request is not valid.
 * There might be multiple validation errors, hence the list of error messages.
 */
public class BuyTicketRequestException extends RuntimeException {

    private final List<String> errorMessages;

    public BuyTicketRequestException(List<String> errorMessages) {
        super("Buy ticket request is not valid");
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return this.errorMessages;
    }

}

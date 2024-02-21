package io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines;

import java.util.List;

/**
 * Exception thrown if any input data in the "check car" request is not valid.
 * There might be multiple validation errors, hence the list of error messages.
 */
public class CheckCarRequestException extends RuntimeException {

    private final List<String> errorMessages;

    public CheckCarRequestException(List<String> errorMessages) {
        super("Check car request is not valid");
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return this.errorMessages;
    }

}

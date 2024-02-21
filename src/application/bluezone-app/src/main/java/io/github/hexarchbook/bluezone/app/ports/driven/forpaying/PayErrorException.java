package io.github.hexarchbook.bluezone.app.ports.driven.forpaying;

/**
 * Exception thrown by the "pay" method if an error occurs.
 * It holds the code of the error, and its description as the message of the exception.
 */
public class PayErrorException extends RuntimeException {

    private final String errorCode;

    public PayErrorException ( PayError payError ) {
        super(payError.getDescription());
        this.errorCode = payError.getCode();
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String toString() {
        return "PayErrorException { errorCode = " + this.errorCode + " }";
    }

}

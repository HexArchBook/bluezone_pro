package io.github.hexarchbook.bluezone.app.ports.driven.forpaying;

/**
 * DRIVEN PORT (Required Interface)
 */
public interface ForPaying {

    /**
     * Charge the euros amount to the card,
     * and return the identifier of the payment made.
     * If an error occurs, a {@link PayErrorException} is thrown
     * with information about the error.
     * Possible errors that could occur are listed in {@link PayError}.
     */
    public String pay ( PayRequest request );

    public PayRequest getPayRequest();

    public String getPayResponse();

    public void setErrorPercentage(int percentage);

    public String getErrorCode();

}

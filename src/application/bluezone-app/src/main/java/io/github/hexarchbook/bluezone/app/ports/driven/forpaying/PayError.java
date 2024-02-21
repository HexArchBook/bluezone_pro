package io.github.hexarchbook.bluezone.app.ports.driven.forpaying;

import java.util.Random;

/**
 * Possible errors that may occur when paying.
 */
public enum PayError {

    GENERIC_ERROR ("An error occurred while paying. Try it again later."),
    CARD_DECLINED ("Card was declined. Check the card number or use a different card.");

    private final String description;

    PayError(String description) {
        this.description = description;
    }

    public static PayError random() {
        PayError[] payErrors = PayError.values();
        int minIndex = 0;
        int maxIndeex = (payErrors.length - 1);
        int randomIndex = (new Random()).nextInt(maxIndeex) + minIndex;
        return payErrors[randomIndex];
    }

    public String getCode() {
        return this.name();
    }

    public String getDescription() {
        return this.description;
    }

}

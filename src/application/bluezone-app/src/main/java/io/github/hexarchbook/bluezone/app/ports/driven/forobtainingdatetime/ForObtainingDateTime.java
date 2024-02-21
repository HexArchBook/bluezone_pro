package io.github.hexarchbook.bluezone.app.ports.driven.forobtainingdatetime;

import java.time.LocalDateTime;

/**
 * DRIVEN PORT (Required Interface)
 */
public interface ForObtainingDateTime {

    /**
     * Return the current date and time
     */
    public LocalDateTime getCurrentDateTime();

    /**
     * Set the given date and time as the current ones
     */
    public void setCurrentDateTime(LocalDateTime dateTime);

}

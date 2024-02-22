package io.github.hexarchbook.bluezone.startup;

import org.slf4j.Logger;

public class BlueZoneLogger {

    private final Logger logger;

    public BlueZoneLogger(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return this.logger;
    }
}

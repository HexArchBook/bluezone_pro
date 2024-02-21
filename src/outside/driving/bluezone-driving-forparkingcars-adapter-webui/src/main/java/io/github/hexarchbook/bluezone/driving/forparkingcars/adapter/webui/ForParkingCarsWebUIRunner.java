package io.github.hexarchbook.bluezone.driving.forparkingcars.adapter.webui;

import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.ForParkingCars;
import io.github.hexarchbook.bluezone.lib.hexagonal.DrivingActor;

public class ForParkingCarsWebUIRunner implements DrivingActor {

    private final ForParkingCars app;

    public ForParkingCarsWebUIRunner(ForParkingCars app ) {
        this.app = app;
    }

    @Override
    public void run(String... args) {
        SpringBootWebUI.forParkingCars = this.app;
        SpringBootWebUI.main(args);
    }

}

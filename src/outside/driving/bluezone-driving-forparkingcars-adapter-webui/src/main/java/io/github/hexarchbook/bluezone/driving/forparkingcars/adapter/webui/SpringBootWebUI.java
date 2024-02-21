package io.github.hexarchbook.bluezone.driving.forparkingcars.adapter.webui;

import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.ForParkingCars;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootWebUI {

    public static ForParkingCars forParkingCars;

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringBootWebUI.class).web(WebApplicationType.SERVLET).run(args);
    }

    @Bean
    public ForParkingCars app() {
        return SpringBootWebUI.forParkingCars;
    }

}

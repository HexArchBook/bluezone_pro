package io.github.hexarchbook.bluezone.driving.forparkingcars.adapter.webui;

import io.github.hexarchbook.bluezone.app.ports.Rate;
import io.github.hexarchbook.bluezone.app.ports.Ticket;
import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.BuyTicketRequest;
import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.ForParkingCars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Set;

@Controller
public class BuyTicketController {

    private final ForParkingCars app;

    @Autowired
    public BuyTicketController(ForParkingCars app) {
        this.app = app;
    }

    @GetMapping("/buyticket")
    public String showForm ( Model model ) {
        Set<Rate> rates = this.app.getAvailableRates();
        model.addAttribute("rates", rates);
        model.addAttribute("request", new BuyTicketRequest() );
        return "buyTicketIn";
    }

    @PostMapping("/buyticket")
    public String submitForm ( Model model, @ModelAttribute("request") BuyTicketRequest request ) {
        Ticket ticket = this.app.buyTicket(request);
        model.addAttribute("ticket", ticket);
        return "buyTicketOut";
    }

}

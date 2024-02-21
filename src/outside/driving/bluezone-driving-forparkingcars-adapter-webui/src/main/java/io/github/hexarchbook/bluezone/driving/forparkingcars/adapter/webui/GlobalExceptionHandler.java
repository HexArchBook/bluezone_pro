package io.github.hexarchbook.bluezone.driving.forparkingcars.adapter.webui;

import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.PayErrorException;
import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.BuyTicketRequest;
import io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars.BuyTicketRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BuyTicketRequestException.class)
    public String handleBuyTicketRequestException (Model model, Exception exception ) {
        BuyTicketRequestException buyTicketRequestException = (BuyTicketRequestException) exception;
        LOGGER.error("A wrong 'BuyTicket' request was made...",buyTicketRequestException);
        String globalErrorMessage = buyTicketRequestException.getMessage();
        List<String> errorMessages = buyTicketRequestException.getErrorMessages();
        model.addAttribute ("globalErrorMessage", globalErrorMessage );
        model.addAttribute ("errorMessages", errorMessages );
        return "error";
    }

    @ExceptionHandler(PayErrorException.class)
    public String handlePayErrorException (Model model, Exception exception ) {
        PayErrorException payErrorException = (PayErrorException) exception;
        LOGGER.error("An error occurred while paying...",payErrorException);
        String globalErrorMessage = payErrorException.getMessage();
        model.addAttribute ("globalErrorMessage", globalErrorMessage );
        model.addAttribute ("errorMessages", new ArrayList<String>() );
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleUnexpectedException (Model model, Exception exception ) {
        LOGGER.error("An unknown error occurred...",exception);
        String globalErrorMessage = "An unexpected error occurred. Contact the application administrator";
        model.addAttribute ("globalErrorMessage", globalErrorMessage );
        model.addAttribute ("errorMessages", new ArrayList<String>() );
        return "error";
    }

}

package io.github.hexarchbook.bluezone.driven.forstoringdata.adapter.mongodb;

import io.github.hexarchbook.bluezone.app.ports.Rate;
import io.github.hexarchbook.bluezone.app.ports.Ticket;
import io.github.hexarchbook.bluezone.lib.javautils.DateTimeUtils;
import org.bson.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DocumentMapper {

    public static Document fromRate(Rate rate) {
        if ( rate==null ) {
            return null;
        }
        return (new Document("name", rate.getName())).append("eurosPerHour",rate.getEurosPerHour().toString());
    }

    public static Rate toRate(Document document) {
        if ( document==null ) {
            return null;
        }
        return new Rate(document.getString("name"), new BigDecimal(document.getString("eurosPerHour")));
    }

    public static Document fromTicket(Ticket ticket) {
        if ( ticket==null ) {
            return null;
        }
        return (new Document("ticketCode", ticket.getTicketCode()))
                 .append("carPlate", ticket.getCarPlate())
                .append("rateName", ticket.getRateName())
                .append("startingDateTime", DateTimeUtils.formatDateTime(ticket.getStartingDateTime(),DateTimeUtils.YYYYMMDD_HHMM_FORMAT))
                .append("endingDateTime", DateTimeUtils.formatDateTime(ticket.getEndingDateTime(),DateTimeUtils.YYYYMMDD_HHMM_FORMAT))
                .append("price", ticket.getPrice().toString())
                .append("paymentId", ticket.getPaymentId());
    }

    public static Ticket toTicket(Document document) {
        if ( document==null ) {
            return null;
        }
        return new Ticket(
                document.getString("ticketCode"),
                document.getString("carPlate"),
                document.getString("rateName"),
                DateTimeUtils.parseDateTime ( document.getString("startingDateTime"), DateTimeUtils.YYYYMMDD_HHMM_FORMAT ),
                DateTimeUtils.parseDateTime ( document.getString("endingDateTime"), DateTimeUtils.YYYYMMDD_HHMM_FORMAT ),
                new BigDecimal(document.getString("price")),
                document.getString("paymentId")
        );
    }

}

package io.github.hexarchbook.bluezone.driven.forstoringdata.adapter.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import io.github.hexarchbook.bluezone.app.ports.Rate;
import io.github.hexarchbook.bluezone.app.ports.Ticket;
import io.github.hexarchbook.bluezone.app.ports.driven.forstoringdata.ForStoringData;
import io.github.hexarchbook.bluezone.lib.hexagonal.DrivenActor;
import io.github.hexarchbook.bluezone.lib.javautils.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Driven adapter for the port "forstoringdata" to use a mongodb database.
 */
@DrivenActor(name="mongodb")
public class MongodbAdapter implements ForStoringData {

    private static final String MONGODB_PROPERTIES = "mongodb.properties";
    private static final String RATE_COLLECTION = "rates";
    private static final String TICKET_COLLECTION = "tickets";
    private static final String SEQUENCE_COLLECTION = "sequences";
    private static final String TICKET_CODE_SEQUENCE_NAME = "ticket-code";
    private static final int MAX_TICKET_CODE_LENGTH = 10;

    private final MongoDatabase bluezoneDatabase;

    public MongodbAdapter() {
        this.bluezoneDatabase = MongoDb.fromConnection(MONGODB_PROPERTIES);
    }

    @Override
    public void loadInitialRates(List<Rate> newRates) {
        MongoCollection<Document> rateCollection = bluezoneDatabase.getCollection(RATE_COLLECTION);
        rateCollection.drop();
        if ( newRates==null || newRates.isEmpty() ) {
            return;
        }
        List<Document> documents = new ArrayList<Document>();
        for ( Rate rate : newRates ) {
            Document document = DocumentMapper.fromRate(rate);
            documents.add(document);
        }
        rateCollection.insertMany(documents);
    }

    @Override
    public Set<Rate> getAllRates() {
        MongoCollection<Document> rateCollection = bluezoneDatabase.getCollection(RATE_COLLECTION);
        Set<Document> documents = rateCollection.find().into(new HashSet<Document>());
        Set<Rate> rates = new HashSet<Rate>();
        for ( Document document : documents ) {
            Rate rate = DocumentMapper.toRate(document);
            rates.add(rate);
        }
        return rates;
    }

    @Override
    public Rate getRateByName(String rateName) {
        MongoCollection<Document> rateCollection = bluezoneDatabase.getCollection(RATE_COLLECTION);
        Bson queryFilter = Filters.eq("name", rateName);
        Document document = rateCollection.find(queryFilter).first();
        return DocumentMapper.toRate(document);
    }

    @Override
    public void loadInitialTickets(List<Ticket> newTickets) {
        MongoCollection<Document> ticketCollection = bluezoneDatabase.getCollection(TICKET_COLLECTION);
        ticketCollection.drop();
        if ( newTickets==null || newTickets.isEmpty() ) {
            return;
        }
        List<Document> documents = new ArrayList<Document>();
        for ( Ticket ticket : newTickets ) {
            Document document = DocumentMapper.fromTicket(ticket);
            documents.add(document);
        }
        ticketCollection.insertMany(documents);
    }

    @Override
    public void saveTicket(Ticket ticket) {
        if ( ticket==null ) {
            return;
        }
        MongoCollection<Document> ticketCollection = bluezoneDatabase.getCollection(TICKET_COLLECTION);
        Bson queryFilter = Filters.eq("ticketCode",ticket.getTicketCode());
        Document document = ticketCollection.find(queryFilter).first();
        if ( document != null ) {
            ticketCollection.deleteOne(document);
        }
        ticketCollection.insertOne(DocumentMapper.fromTicket(ticket));
    }

    @Override
    public Ticket getTicketByCode(String ticketCode) {
        MongoCollection<Document> ticketCollection = bluezoneDatabase.getCollection(TICKET_COLLECTION);
        Bson queryFilter = Filters.eq("ticketCode", ticketCode);
        Document document = ticketCollection.find(queryFilter).first();
        return DocumentMapper.toTicket(document);
    }

    @Override
    public Set<Ticket> getTicketsByCarAndRate(String carPlate, String rateName) {
        MongoCollection<Document> ticketCollection = bluezoneDatabase.getCollection(TICKET_COLLECTION);
        Bson queryFilter = Filters.and(
                Filters.eq("carPlate",carPlate),
                Filters.eq("rateName",rateName)
        );
        Set<Document> documents = ticketCollection.find(queryFilter).into(new HashSet<Document>());
        Set<Ticket> tickets = new HashSet<Ticket>();
        for ( Document document : documents ) {
            Ticket ticket = DocumentMapper.toTicket(document);
            tickets.add(ticket);
        }
        return tickets;
    }

    @Override
    public String nextTicketCode() {
        MongoCollection<Document> sequenceCollection = bluezoneDatabase.getCollection(SEQUENCE_COLLECTION);
        Bson queryFilter = Filters.eq("name", TICKET_CODE_SEQUENCE_NAME);
        Document ticketCodeSequence = sequenceCollection.findOneAndUpdate(
                queryFilter,
                new Document("$inc", new Document("nextValue", 1L)),
                (new FindOneAndUpdateOptions()).upsert(true).returnDocument(ReturnDocument.BEFORE)
        );
        long ticketNumber = ticketCodeSequence.get("nextValue",Long.class);
        String ticketCode = "" + ticketNumber;
        if ( ticketCode.length() > MAX_TICKET_CODE_LENGTH ) {
            throw new RuntimeException("Ticket code overflow");
        }
        return StringUtils.leftPad(ticketCode,MAX_TICKET_CODE_LENGTH,'0');
    }

    @Override
    public void setNextTicketCode(String newNextTicketCode) {
        if ( newNextTicketCode==null ) {
            throw new RuntimeException("New next ticket code cannot be null");
        }
        if ( newNextTicketCode.length() > MAX_TICKET_CODE_LENGTH ) {
            throw new RuntimeException("Ticket code overflow");
        }
        long newNextTicketNumber = Long.parseLong(newNextTicketCode);
        MongoCollection<Document> sequenceCollection = bluezoneDatabase.getCollection(SEQUENCE_COLLECTION);
        Bson queryFilter = Filters.eq("name", TICKET_CODE_SEQUENCE_NAME);
        sequenceCollection.updateOne(
                queryFilter,
                new Document("$set", new Document("nextValue", newNextTicketNumber))
        );
    }

}

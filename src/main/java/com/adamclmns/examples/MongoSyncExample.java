package com.adamclmns.examples;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Using the Samples provided in Mongo-Sync's documentation
 * - http://mongodb.github.io/mongo-java-driver/3.9/driver/getting-started/quick-start/
 */
public class MongoSyncExample {

    private static final Logger logger = LoggerFactory.getLogger(MongoSyncExample.class);
    // MongoDB Credentials are managed here for manual connection (No SpringBoot Magic Here.)-
    private final String MONGO_HOST = "localhost";
    private final String MONGO_PORT = "27017";
    private final String MONGO_USER = "";
    private final String MONGO_PASS = "";
    private final String MONGO_PREFIX = "mongodb://";
    private final String MONGO_COLLECTION = "mongoSyncExample";
    private final String MONGO_DATABASE = "candyGram";

    /**
     * Creating a list of "Document" types that we can insert into MongoDB
     *
     * @return
     */
    private List<Document> createDocumentsForInsert() {
        Document kitkat = new Document("name", "kit-kat").append("id", 1L)
                .append("tags", Arrays.asList("chocolate", "shareable", "crunchy"))
                .append("price", 1.25).append("rating", 4.0 / 5.0);

        Document twix = new Document("name", "twix").append("id", 2L)
                .append("tags", Arrays.asList("chocolate", "caramel", "shareable", "cruncy"))
                .append("price", 1.25).append("rating", 5.0 / 5.0);

        Document snickers = new Document("name", "snickers").append("id", 3L)
                .append("tags", Arrays.asList("chocolate", "caramel", "nuts", "chewy"))
                .append("price", 1.25).append("rating", 4.0 / 5.0);

        Document gummyWorms = new Document("name", "gummy worms").append("id", 4L)
                .append("tag", Arrays.asList("fruity", "sour", "shareable", "chewy", "large-size"))
                .append("price", 2.50).append("rating", 3.0 / 5.0);

        return Arrays.asList(kitkat, twix, snickers, gummyWorms);
    }

    /**
     * This method will connect to the MongoDatabase instance, and then use the configured database name,
     * and then return the configured collection from that database. If the database or collection do not exist,
     * and the user connected has Admin rights, the database and collection will be created automatically.
     * @return
     */
    private MongoCollection<Document> getConnectionToCollection() {
        MongoClient mongoClient = MongoClients.create(getConnectionString());
        logger.info("Connected to Mongo Instance");
        MongoDatabase database = mongoClient.getDatabase(MONGO_DATABASE);
        return database.getCollection(MONGO_COLLECTION);
    }

    public void run() {

        MongoCollection<Document> collection = getConnectionToCollection();

        collection.insertMany(createDocumentsForInsert());
        //collection.insertOne();

        long count = collection.countDocuments();
        logger.info("Inserted " + count + " Documents");
        Document queryResult = collection.find().first();
        logger.info("FindFirst - " + queryResult.toJson());


        logger.info("Complex query");
        FindIterable<Document> selection = collection.find(
                Filters.and(
                        Filters.in("tags", "chewy"),
                        Filters.lt("price", 2.00)
                )
        );

        logger.info("Results from Query: ");
        for (Document document : selection) {
            logger.info(document.toJson());
        }

        logger.info("Deleting \"twix\"...");
        collection.deleteOne(Filters.eq("name", "twix"));
        logger.info("Deleted a Document");
    }

    public void deleteAll() {
        MongoCollection<Document> collection = getConnectionToCollection();

        collection.deleteMany(Filters.ne("name", null));
        logger.info("Deleted All");
    }
    private String getConnectionString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MONGO_PREFIX);
        if (MONGO_USER != null && !MONGO_USER.isEmpty()) {
            sb.append(MONGO_USER);
            if (MONGO_PASS != null && !MONGO_PASS.isEmpty()) {
                sb.append(":").append(MONGO_PASS).append("@");
            }
        }
        sb.append(MONGO_HOST);
        sb.append(":").append(MONGO_PORT);


        return sb.toString();
    }
}

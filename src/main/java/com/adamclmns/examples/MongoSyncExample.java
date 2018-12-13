package com.adamclmns.examples;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Using the Samples provided in Mongo-Sync's documentation
 * - http://mongodb.github.io/mongo-java-driver/3.9/driver/getting-started/quick-start/
 */
public class MongoSyncExample implements AbstractExample {

    private static final Logger logger = LoggerFactory.getLogger(MongoJackExample.class);

    public void run() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("candyGram");

        MongoCollection<Document> collection = database.getCollection("candy");
        logger.info("Connected to Mongo Instance");

        Document kitkat = new Document("name", "kit-kat")
                .append("tags", Arrays.asList("chocolate", "shareable", "crunchy"))
                .append("price", 1.25).append("rating", 4 / 5);

        Document twix = new Document("name", "twix")
                .append("tags", Arrays.asList("chocolate", "caramel", "shareable", "cruncy"))
                .append("price", 1.25).append("rating", 5 / 5);

        Document snickers = new Document("name", "snickers")
                .append("tags", Arrays.asList("chocolate", "caramel", "nuts", "chewy"))
                .append("price", 1.25).append("rating", 4 / 5);

        Document gummyWorms = new Document("name", "gummy worms")
                .append("tag", Arrays.asList("fruity", "sour", "shareable", "chewy", "large-size"))
                .append("price", 2.50).append("rating", 3 / 5);

        collection.insertMany(Arrays.asList(snickers, kitkat, twix));
        collection.insertOne(gummyWorms);

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
        MongoCursor<Document> cursor = selection.iterator();
        while (cursor.hasNext()) {
            logger.info(cursor.next().toJson());
        }

        logger.info("Deleting \"twix\"...");
        collection.deleteOne(Filters.eq("name", "twix"));
        logger.info("Deleted a Document");
    }
}

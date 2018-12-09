package com.adamclmns;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoSyncDemo {
    public static void main(String[] args) {
        MongoSyncDemo app = new MongoSyncDemo();
        MongoCollection<Document> coll = app.connectToCollection();

        // http://mongodb.github.io/mongo-java-driver/3.9/driver/getting-started/quick-start-pojo/

    }


    private MongoCollection<Document> connectToCollection() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("cadyGramDB");
        MongoCollection<Document> coll = database.getCollection("mongosyncCollection");
        return coll;
    }

    private void registerPojoCode() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    }
}

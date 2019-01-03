# CandyGram - No-SQL By Example with MongoDB
Little Rock Java User Group - January 3rd, 2018 [www.meetup.com](https://www.meetup.com/Little-Rock-Java-Meetup/events/257667580/)
___

This 50 minute presentation will explain what the differences are
between No-SQL and Traditional database solutions, discuss the pros
and cons of a No-SQL database. At the end of the presentation, we'll
take a look at a few of the different ways Java can interface with
MongoDB, a leading No-SQL Database.


CandyGram is a demo application used with [these presentation slides](https://docs.google.com/presentation/d/1tJqxocHCExDypKLAWP1dq7bLlho4xRCry2qTM81MbJo/edit?usp=sharing)


## Running the Application
This application runs with maven
```shell
mvn clean -e spring-boot:run
```
You can also pass arguments to the SpringBoot application to enable
different startup behavior.

* --sync  - This will just run the MongoSync example class at startup,
and then the rest of the SpringBoot application will continue to run.
* --wipeAllData - This option deletes all data in the collections used
by the Sync example and the Spring Data examples.
* --loadCsv - This option will load the CSV candyBar data into the
SpringData Collection.

All of these options can be run together, or seperately as needed. To
pass these arguments, you can run the compiled JAR file and pass the
arguments, or pass the arguments via Maven by following this guide.

https://www.baeldung.com/spring-boot-command-line-argumentsb

## MongoSync Example

This example uses some hard-coded database information to connect to a
MongoDB instance and insert and query some raw BSON Documents.
This is the lowest level example of using MongoDB with Java.

Dependencies needed for this example
```xml
        <dependency>
            <groupId>org.mongodb</groupId>
            <artifactId>mongodb-driver-sync</artifactId>
            <version>${mongodb-driver-sync.version}</version>
        </dependency>
```

Creating the Connection to Mongo and getting a Collection to interact with
```java
    ...
    private MongoCollection<Document> getConnectionToCollection() {
        MongoClient mongoClient = MongoClients.create(MONGO_CONNECTION_STRING);
        logger.info("Connected to Mongo Instance");
        MongoDatabase database = mongoClient.getDatabase(MONGO_DATABASE);
        return database.getCollection(MONGO_COLLECTION);
    }
    ...

     MongoCollection<Document> collection = getConnectionToCollection();
```

Create a List of Documents
```java
    ...
     private List<Document> createDocumentsForInsert() {
            Document kitkat = new Document("name", "kit-kat").append("id", 1L)
                    .append("tags", Arrays.asList("chocolate", "shareable", "crunchy"))
                    .append("price", 1.25).append("rating", 4 / 5);

            Document twix = new Document("name", "twix").append("id", 2L)
                    .append("tags", Arrays.asList("chocolate", "caramel", "shareable", "cruncy"))
                    .append("price", 1.25).append("rating", 5 / 5);

            Document snickers = new Document("name", "snickers").append("id", 3L)
                    .append("tags", Arrays.asList("chocolate", "caramel", "nuts", "chewy"))
                    .append("price", 1.25).append("rating", 4 / 5);

            Document gummyWorms = new Document("name", "gummy worms").append("id", 4L)
                    .append("tag", Arrays.asList("fruity", "sour", "shareable", "chewy", "large-size"))
                    .append("price", 2.50).append("rating", 3 / 5);

            return Arrays.asList(kitkat, twix, snickers, gummyWorms);
        }
        ...
```

Insert Documents
```
    ...
    collection.insertMany(createDocumentsForInsert());
    ...
    collection.insertOne(twix);
    ...
```

Query Documents
```java
    ...
    Document queryResult = collection.find().first();
    ...
    FindIterable<Document> selection = collection.find(
        Filters.and(
            Filters.in("tags", "chewy"),
            Filters.lt("price", 2.00)
        )
    );
    for (Document document : selection) {
        System.out.println(document.toJson());
    }
    ...
```

Delete Documents
```java
    collection.deleteOne(Filters.eq("name", "twix"));
```
## Spring Data Mongo Example

This example uses a properties file to configure MongoDB, a Java class
annotated @Document to define our Collection Data, and an Interface
extending Repository<> to create our query methods. For accessibility,
all of this is wrapped up in a RestController and documented by
SwaggerFox Swagger UI.

Dependencies needed for this example
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```

Dependencies for SwaggerFox
```xml
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>${springfox-swagger2.version}</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>${springfox-swagger2.version}</version>
        </dependency>
```


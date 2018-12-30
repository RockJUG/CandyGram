# CandyGram - A MongoDB and Java Example Application

This is an example application to show two ways of using MongoDB from Java.

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


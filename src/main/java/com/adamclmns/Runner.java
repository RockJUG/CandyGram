package com.adamclmns;

import com.adamclmns.examples.MongoJackExample;
import com.adamclmns.examples.MongoSyncExample;
import com.adamclmns.examples.SpringMongoTemplateExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Runs a set of examples
 */
public class Runner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.runAll();
    }

    private void runAll() {
        this.runMongoJackExample();
        this.runMongoSyncExample();
        this.runSpringMongoTemplateExample();
    }

    private void runMongoJackExample() {
        logger.info(" MongoJack Example");
        MongoJackExample example = new MongoJackExample();
        example.run();
    }

    private void runMongoSyncExample() {
        logger.info(" MongoSync Example");
        MongoSyncExample example = new MongoSyncExample();
        example.run();
    }

    private void runSpringMongoTemplateExample() {
        logger.info(" SpringMongoTemplate Example");
        SpringMongoTemplateExample example = new SpringMongoTemplateExample();
        example.run();

    }

}

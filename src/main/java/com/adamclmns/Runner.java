package com.adamclmns;

import com.adamclmns.data.CandyBar;
import com.adamclmns.data.CandyBarRepository;
import com.adamclmns.examples.MongoSyncExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/**
 * Runs a set of examples
 */
@EnableSwagger2
@SpringBootApplication
public class Runner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);
    /**
     * The Candy bar repository.
     */
    @Autowired
    CandyBarRepository candyBarRepository;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);

    }

    /**
     * This configures the Generated Swagger File that Swagger UI uses to generate the
     * API Interface
     *
     * @return the docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.adamclmns.examples"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * This method is run on startup by SpringBoot Framework. This can be thought of as a "main(String[] args)" method
     * that gets called as your app starts up.
     *
     * @param args
     */
    @Override
    public void run(String[] args) {
        if ((Arrays.asList(args).contains("--wipeAllData"))) {
            this.candyBarRepository.deleteAll();
            MongoSyncExample ex = new MongoSyncExample();
            ex.deleteAll();
        }
        if ((Arrays.asList(args).contains("--sync"))) {
            this.runMongoSyncExample();
        }
        if ((Arrays.asList(args).contains("--loadCsv"))) {
            List<CandyBar> allBars = candyBarRepository.findAll();
            if (allBars.isEmpty()) {
                logger.warn("Populating Database");
                // Loads a CSV pulled from WikiPedia into the Database
                CsvCandyBarAdapter csvCandyBarAdapter = new CsvCandyBarAdapter("candyData.csv");
                List<CandyBar> candyBars = csvCandyBarAdapter.parseCSVToCandyBarList();
                for (CandyBar candyBar : candyBars) {
                    candyBarRepository.save(candyBar);
                    logger.warn("SAVED::" + candyBar.toString());
                }
            }
        }

    }

    /**
     * Runs the MongoSync Example program
     */
    private void runMongoSyncExample() {
        logger.info(" MongoSync Example");
        MongoSyncExample example = new MongoSyncExample();
        example.run();
    }


}

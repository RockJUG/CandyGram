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
    @Autowired
    CandyBarRepository candyBarRepository;

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);

    }

    /**
     * This configures the Generated Swagger File that Swagger UI uses to generate the
     * API Interface
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.adamclmns.examples"))
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void run(String[] args) {
        if ((Arrays.asList(args).contains("sync"))) {
            this.runMongoSyncExample();
        } else {
            List<CandyBar> allBars = candyBarRepository.findAll();
            if (allBars.isEmpty()) {
                logger.warn("Populating Database");
                // Loads a CSV pulled from WikiPedia into the Database
                CsvCandyBarAdapter csvCandyBarAdapter = new CsvCandyBarAdapter("candyData.csv");
                List<CandyBar> candyBars = csvCandyBarAdapter.parseCSVToCandyBarList();
                for (CandyBar candyBar : candyBars) {
                    candyBarRepository.save(candyBar);
                }
            }
        }
    }

    private void runMongoSyncExample() {
        logger.info(" MongoSync Example");
        MongoSyncExample example = new MongoSyncExample();
        example.run();
    }


}
